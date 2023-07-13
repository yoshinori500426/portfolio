package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.SupplierMaster;
import bean.UserMaster;

public class SupplierMasterDAO extends DAO {
	/**
	 * SupplierMasterテーブル参照メソッド
	 * →SUPPLIER_NOでサーチ
	 * @param SupplierMasterビーン
	 * @return SupplierMasterビーン 「null：失敗」「インスタンス有：成功」
	 */
	public SupplierMaster searchBySupNo(SupplierMaster sm) {
		SupplierMaster supplierMaster = null;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("SELECT * FROM SUPPLIER_MASTER WHERE SUPPLIER_NO = ?");
			st.setString(1, sm.getSupplierNo());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				supplierMaster = new SupplierMaster();
				supplierMaster.setSupplierNo(rs.getString("SUPPLIER_NO"));
				supplierMaster.setSupplierName(rs.getString("SUPPLIER_NAME"));
				supplierMaster.setBranchName(rs.getString("BRANCH_NAME"));
				supplierMaster.setZipNo(rs.getString("ZIP_NO"));
				supplierMaster.setAddress1(rs.getString("ADDRESS1"));
				supplierMaster.setAddress2(rs.getString("ADDRESS2"));
				supplierMaster.setAddress3(rs.getString("ADDRESS3"));
				supplierMaster.setTel(rs.getString("TEL"));
				supplierMaster.setFax(rs.getString("FAX"));
				supplierMaster.setManager(rs.getString("MANAGER"));
				supplierMaster.setEtc(rs.getString("ETC"));
				supplierMaster.setRegistDate(rs.getString("REGIST_DATE"));
				supplierMaster.setRegistUser(rs.getString("REGIST_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return supplierMaster;
	}

	/**
	 * SupplierMasterテーブル登録メソッド
	 * @param SupplierMasterビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insert(SupplierMaster sm, HttpServletRequest request) {
		int line = 0;
		//SUPPLIER_NO取得用カウンタ
		int count = 0;
		String SUPPLIER_NO = "";
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		HttpSession session = request.getSession();
		UserMaster um = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			SupplierMaster supplierNoCheck = new SupplierMaster();
			SupplierMaster supplierMaster = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			//仕入先コード作成
			do {
				//仕入先コードのシーケンス値取得
				st = con.prepareStatement("SELECT SUPPLIER_NO.NEXTVAL FROM DUAL");
				rs = st.executeQuery();
				while (rs.next()) {
					SUPPLIER_NO = String.valueOf(rs.getInt("NEXTVAL"));
				}
				//仕入先コードを左ゼロ埋めで6桁にする処理
				do {
					if (SUPPLIER_NO.length() < 6) {
						SUPPLIER_NO = "0" + SUPPLIER_NO;
					} else {
						break;
					}
				} while (true);
				//作成した注文番号の未使用確認
				supplierNoCheck.setSupplierNo(SUPPLIER_NO);
				supplierMaster = searchBySupNo(supplierNoCheck);
				if (supplierMaster == null) {
					//使用可能
					break;
				} else if (count >= 100000) {
					session.setAttribute("state", "使用可能な仕入先コードがありません。\\n処理を終了します｡");
					return line;
				}
				//SUPPLIER_NO取得用カウンタ、カウントアップ
				count++;
			} while (true);

			//インサート処理 13項目
			st = con.prepareStatement("INSERT INTO SUPPLIER_MASTER VALUES(? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, SUPPLIER_NO);
			st.setString(2, sm.getSupplierName());
			st.setString(3, sm.getBranchName());
			st.setString(4, sm.getZipNo());
			st.setString(5, sm.getAddress1());
			st.setString(6, sm.getAddress2());
			st.setString(7, sm.getAddress3());
			st.setString(8, sm.getTel());
			st.setString(9, sm.getFax());
			st.setString(10, sm.getManager());
			st.setString(11, sm.getEtc());
			st.setString(12, sdf.format(cl.getTime()));
			st.setString(13, um.getUserId());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * SupplierMasterテーブル更新メソッド
	 *  →SUPPLIER_NOで更新
	 * @param SupplierMaster
	 * @param SupplierMasterビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int updateBySupNo(SupplierMaster sm, HttpServletRequest request) {
		int line = 0;
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		HttpSession session = request.getSession();
		UserMaster um = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = con
					.prepareStatement(
							"UPDATE "
									+ "    SUPPLIER_MASTER "
									+ "SET "
									+ "    SUPPLIER_NAME = ?, "
									+ "    BRANCH_NAME = ?, "
									+ "    ZIP_NO = ?, "
									+ "    ADDRESS1 = ?, "
									+ "    ADDRESS2 = ?, "
									+ "    ADDRESS3 = ?, "
									+ "    TEL = ?, "
									+ "    FAX = ?, "
									+ "    MANAGER = ?, "
									+ "    ETC = ?, "
									+ "    REGIST_DATE = ?, "
									+ "    REGIST_USER = ? "
									+ "WHERE "
									+ "    SUPPLIER_NO = ? ");
			st.setString(1, sm.getSupplierName());
			st.setString(2, sm.getBranchName());
			st.setString(3, sm.getZipNo());
			st.setString(4, sm.getAddress1());
			st.setString(5, sm.getAddress2());
			st.setString(6, sm.getAddress3());
			st.setString(7, sm.getTel());
			st.setString(8, sm.getFax());
			st.setString(9, sm.getManager());
			st.setString(10, sm.getEtc());
			st.setString(11, sdf.format(cl.getTime()));
			st.setString(12, um.getUserId());
			st.setString(13, sm.getSupplierNo());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

}
