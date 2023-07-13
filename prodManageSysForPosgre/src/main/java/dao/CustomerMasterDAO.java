package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bean.CustomerMaster;

public class CustomerMasterDAO extends DAO {

	public int deleteByCstNo(String customerNo) {

		int result = 0;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("DELETE FROM CUSTOMER_MASTER WHERE CUSTOMER_NO=?");
			st.setString(1, customerNo);

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public int insertCustomerMaster(CustomerMaster recv_data) {

		int result = 0;
		String now_code = null; //テーブル内顧客NOの最大値
		int now_no = 0; //顧客NOの最大値の数値の部分
		char head_char = 0; //顧客NO最大値のアルファベットの部分
		int next_no = 0; //シーケンスで発行された次の数値
		char new_char = 0;//次に使用するアルファベット
		String new_customer_no = ""; //次にセットされる顧客NO


		try {
			//日付を取得する
			Calendar cl = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//			//ログイン中のユーザー情報を取得
//			UserMaster um = (UserMaster) request.getAttribute("user");



			//テーブルと接続
			Connection con = getConnection();

			//↓CUSTOMER_MASTAERテーブル CUSTOMERE_NOの最大値を取得
			PreparedStatement st = con.prepareStatement(
					"SELECT MAX(CUSTOMER_NO) AS MAXNO FROM CUSTOMER_MASTER ");
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				//now_codeに現在の最大値を代入
				now_code = rs.getString("MAXNO");

			}
			//テーブルが空の時の分岐
			//テーブルに既存のコードがあったとき
			if (now_code != null) {//最大値のアルファベットを取得
				head_char = now_code.charAt(0);
				//最大値のシーケンスナンバーを取得
				now_no = Integer.parseInt(now_code.substring(1));
			} //テーブルに登録がなかった時
			else {
				head_char = '@';
				now_no = 0;
			}
			do {

				//シーケンスで数値を取得
				PreparedStatement nv = con.prepareStatement("SELECT CUSTOMER_NO.NEXTVAL FROM DUAL");
				ResultSet rs2 = nv.executeQuery();
				while (rs2.next()) {
					//next_noに次発行されたシーケンスナンバーをセット
					next_no = rs2.getInt("NEXTVAL");
				}

				//アルファベットをそのままにするか否かの処理
				if (now_no >= next_no || next_no == 100) {
					if (head_char < 90) {
						new_char = (char) ((int) head_char + 1);
					} else {
						System.out.println("これ以上顧客マスターを登録できません");
					}
				} else {
					new_char = (char) ((int) head_char);
				}
				//新しい顧客NOを作成
				new_customer_no = new_char + String.format("%04d", next_no);

				//次に発行された顧客NOがテーブルに存在しないか確かめる

				st = con.prepareStatement("SELECT CUSTOMER_NO FROM CUSTOMER_MASTER WHERE CUSTOMER_NO=?");
				st.setString(1, new_customer_no);

				rs = st.executeQuery();
				String nn = null;//テーブルにあるか否かの戻り値を代入
				while (rs.next()) {
					nn = rs.getString("CUSTOMER_NO");
				}
				if (nn == null) {
					break;
				}

			} while (true);

			st = con.prepareStatement("INSERT INTO CUSTOMER_MASTER VALUES(?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?)");
			st.setString(1, new_customer_no);
			st.setString(2, recv_data.getCustomerName());
			st.setString(3, recv_data.getBranchName());
			st.setString(4, recv_data.getZipNo().replace("-", "").replace("ー", ""));
			st.setString(5, recv_data.getAddress1());
			st.setString(6, recv_data.getAddress2());
			st.setString(7, recv_data.getAddress3());
			st.setString(8, recv_data.getTel().replace("-", "").replace("ー", "").replace("(", "").replace(")", ""));
			st.setString(9, recv_data.getFax().replace("-", "").replace("ー", "").replace("(", "").replace(")", ""));
			st.setString(10, recv_data.getManager());
			st.setInt(11, recv_data.getDelivaryLeadtime());
			st.setString(12, recv_data.getEtc());
			st.setString(13, sdf.format(cl.getTime()));
			st.setString(14, recv_data.getRegistuser());

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public CustomerMaster searchByCusNo(String customer_no) {
		CustomerMaster getUserData = null;

		try {


			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement
					("SELECT * FROM CUSTOMER_MASTER WHERE CUSTOMER_NO= ?");
			st.setString(1, customer_no);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				getUserData = new CustomerMaster();
				getUserData.setCustomerNo(rs.getString("CUSTOMER_NO"));
				getUserData.setCustomerName(rs.getString("CUSTOMER_NAME"));
				getUserData.setBranchName(rs.getString("BRANCH_NAME"));
				getUserData.setZipNo(rs.getString("ZIP_NO"));
				getUserData.setAddress1(rs.getString("ADDRESS1"));
				getUserData.setAddress2(rs.getString("ADDRESS2"));
				getUserData.setAddress3(rs.getString("ADDRESS3"));
				getUserData.setTel(rs.getString("TEL"));
				getUserData.setFax(rs.getString("FAX"));
				getUserData.setManager(rs.getString("MANAGER"));
				getUserData.setDelivaryLeadtime(rs.getInt("DELIVARY_LEADTIME"));
				getUserData.setEtc(rs.getString("ETC"));
				getUserData.setRegistdate(rs.getString("REGIST_DATE"));
				getUserData.setRegistuser(rs.getString("REGIST_USER"));

			}
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました");
			e.printStackTrace();
		}
		return getUserData;

	}

	public int updateByCusNo(CustomerMaster cm) {
		int line = 0;
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		try {
			Connection con = getConnection();
			PreparedStatement st = con
					.prepareStatement("UPDATE CUSTOMER_MASTER  SET CUSTOMER_NAME = ?,"
							+ "BRANCH_NAME = ?, ZIP_NO = ? ,ADDRESS1 = ?,"
							+ "ADDRESS2 = ?, ADDRESS3 = ?, TEL = ?,"
							+ "FAX = ?, MANAGER = ?, DELIVARY_LEADTIME = ?,"
							+ "ETC = ?, REGIST_DATE = ?, REGIST_USER = ?"
							+ "WHERE CUSTOMER_NO = ? ");

			st.setString(1, cm.getCustomerName());
			st.setString(2, cm.getBranchName());
			st.setString(3, cm.getZipNo());
			st.setString(4, cm.getAddress1());
			st.setString(5, cm.getAddress2());
			st.setString(6, cm.getAddress3());
			st.setString(7, cm.getTel());
			st.setString(8, cm.getFax());
			st.setString(9, cm.getManager());
			st.setInt(10, cm.getDelivaryLeadtime());
			st.setString(11, cm.getEtc());
			st.setString(12, sdf.format(cl.getTime()));
			st.setString(13, cm.getRegistuser());
			st.setString(14, cm.getCustomerNo());

			line = st.executeUpdate();

			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました");
			e.printStackTrace();

			// TODO: handle exception
		}

		return line;

	}

}
