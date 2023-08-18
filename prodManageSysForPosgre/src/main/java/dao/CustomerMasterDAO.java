package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.CustomerMaster;
import bean.G_CustomerMaster;
import bean.UserMaster;

public class CustomerMasterDAO extends DAO {
//	public int deleteByCstNo(String customerNo) {
//	int result = 0;
//	try {
//		Connection con = getConnection();
//		PreparedStatement st;
//		st = con.prepareStatement("DELETE FROM CUSTOMER_MASTER WHERE CUSTOMER_NO=?");
//		st.setString(1, customerNo);
//
//		result = st.executeUpdate();
//		st.close();
//		con.close();
//
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return result;
//
//}
	
	/**
	 * 顧客先番号に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_CustomerMasterクラスのインスタンス
	 * @return 該当レコードあり:CustomerMasterクラスのインスタンス　無:null
	 */
	public CustomerMaster searchByCusNo(G_CustomerMaster G_CustomerMaster) {
		CustomerMaster CustomerMaster = null;
		try {
			Connection con = getConnection();
			
			PreparedStatement st = con.prepareStatement("SELECT * FROM CUSTOMER_MASTER WHERE CUSTOMER_NO=?");
			st.setString(1, G_CustomerMaster.getCustomerNo());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				CustomerMaster = new CustomerMaster();
				CustomerMaster.setCustomerNo(rs.getString("CUSTOMER_NO"));
				CustomerMaster.setCustomerName(rs.getString("CUSTOMER_NAME"));
				CustomerMaster.setBranchName(rs.getString("BRANCH_NAME"));
				CustomerMaster.setZipNo(rs.getString("ZIP_NO"));
				CustomerMaster.setAddress1(rs.getString("ADDRESS1"));
				CustomerMaster.setAddress2(rs.getString("ADDRESS2"));
				CustomerMaster.setAddress3(rs.getString("ADDRESS3"));
				CustomerMaster.setTel(rs.getString("TEL"));
				CustomerMaster.setFax(rs.getString("FAX"));
				CustomerMaster.setManager(rs.getString("MANAGER"));
				CustomerMaster.setDelivaryLeadtime(rs.getInt("DELIVARY_LEADTIME"));
				CustomerMaster.setEtc(rs.getString("ETC"));
				CustomerMaster.setRegistdate(rs.getString("REGIST_DATE"));
				CustomerMaster.setRegistuser(rs.getString("REGIST_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました");
			e.printStackTrace();
		}
		return CustomerMaster;
	}

	/**
	 * 引数の顧客先番号に合致するレコードを更新するメソッド
	 * 
	 * @param G_CustomerMasterクラスのインスタンス
	 * @return 0:更新失敗 1:更新成功
	 */
	public int updateByCusNo(G_CustomerMaster G_CustomerMaster, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");

		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("UPDATE CUSTOMER_MASTER SET CUSTOMER_NAME=?, BRANCH_NAME=?, ZIP_NO=? ,ADDRESS1=?, ADDRESS2=?, ADDRESS3=?, TEL=?, FAX=?, "
																	+ "MANAGER=?, DELIVARY_LEADTIME=?, ETC=?, REGIST_DATE=?, REGIST_USER=? WHERE CUSTOMER_NO=?");
			st.setString(1, G_CustomerMaster.getCustomerName());
			st.setString(2, G_CustomerMaster.getBranchName());
			st.setString(3, G_CustomerMaster.getZipNo());
			st.setString(4, G_CustomerMaster.getAddress1());
			st.setString(5, G_CustomerMaster.getAddress2());
			st.setString(6, G_CustomerMaster.getAddress3());
			st.setString(7, G_CustomerMaster.getTel());
			st.setString(8, G_CustomerMaster.getFax());
			st.setString(9, G_CustomerMaster.getManager());
			st.setInt(10, Integer.parseInt(G_CustomerMaster.getDelivaryLeadtime()));
			st.setString(11, G_CustomerMaster.getEtc());
			st.setString(12, sdfymd.format(cl.getTime()));
			st.setString(13, user.getUserId());
			st.setString(14, G_CustomerMaster.getCustomerNo());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * 引数の顧客情報を新規登録するメソッド
	 * 
	 * @param G_CustomerMasterクラスのインスタンス
	 * @return 0:新規登録失敗 1:新規登録成功
	 */
	@SuppressWarnings("resource")
	public int insert(G_CustomerMaster G_CustomerMaster, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// PRODUCT_NO取得用カウンタ
		int countAlphabet = 0;
		int countAll = 0;
		String MaxCustmerNo = "";
		char MaxCustmerNoAlphabet = 65; //仮で､｢A｣の65を格納(取れる範囲は､65-90)
		String CUSTOMER_NO = "";
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");
		
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			CustomerMaster CustomerMaster = null;
			
			//CUSTOMER_MASTAERテーブルのCUSTOMERE_NO最大値を変数｢MaxCustmerNo｣に格納
			st = con.prepareStatement("SELECT MAX(CUSTOMER_NO) AS MAXNO FROM CUSTOMER_MASTER");
			rs = st.executeQuery();
			while (rs.next()) {
				MaxCustmerNo = rs.getString("MAXNO");
			}
			// レコード1件目の処理
			if(MaxCustmerNo==null) {
				// 顧客コードのシーケンス値取得
				st = con.prepareStatement("SELECT NEXTVAL('CUSTOMER_NO')");
				rs = st.executeQuery();
				while (rs.next()) {
					CUSTOMER_NO = String.valueOf(rs.getInt("NEXTVAL"));
				}
				// 顧客コードを左ゼロ埋めで4桁にする処理
				do {
					if (CUSTOMER_NO.length() < 4) {
						CUSTOMER_NO = "0" + CUSTOMER_NO;
					} else {
						break;
					}
				} while (true);
				CUSTOMER_NO = String.valueOf(MaxCustmerNoAlphabet) + CUSTOMER_NO;
			// レコード2件目以降の処理
			// →このコードでは､以下動作とする
			//　　アルファベット｢A｣に対し､数字0100-9900までを漏れなく付与後に､次のアルファベット｢B｣に移行する
			//　　→理由は以下
			// 　　　　CUSTOMERE_NOの作成は､本来であれば登録日時が秒単位で記録されていれば､
			// 　　　　最新の登録日のレコードのCUSTOMERE_NOを起点に重複を検索し､最新のCUSTOMERE_NOに近い値をCUSTOMERE_NOとすべきだが､
			// 　　　　このテーブルは､登録日がYYYY/MM/DDまでの記録の為､最新のレコードが一意に決まらない
			// 　　　　従い､最新のCUSTOMERE_NOを起点にA0100からZ9900までを順にチェックする事が出来ない
			// 　　　　つまり､1周目のCUSTOMERE_NO付与は､アルファベット順にする事が可能だが､
			// 　　　　2周目以降のCUSTOMERE_NO付与がアルファベット順に制御できない為
			}else if(MaxCustmerNo!=null) {
				// CUSTOMER_MASTAERテーブルのCUSTOMERE_NO最大値の1文字目(アルファベット)をchar型で変数｢MaxCustmerNoAlphabet｣に格納
				MaxCustmerNoAlphabet = MaxCustmerNo.toCharArray()[0];
				// CUSTOMER_NO作成
				do {
					// 顧客コードのシーケンス値取得
					st = con.prepareStatement("SELECT NEXTVAL('CUSTOMER_NO')");
					rs = st.executeQuery();
					while (rs.next()) {
						CUSTOMER_NO = String.valueOf(rs.getInt("NEXTVAL"));
					}
					// 顧客コードを左ゼロ埋めで4桁にする処理
					do {
						if (CUSTOMER_NO.length() < 4) {
							CUSTOMER_NO = "0" + CUSTOMER_NO;
						} else {
							break;
						}
					} while (true);
					//　アルファベット付き顧客コード作成
					CUSTOMER_NO = String.valueOf(MaxCustmerNoAlphabet) + CUSTOMER_NO;
					// 作成した注文番号の未使用確認
					G_CustomerMaster G_cm = new G_CustomerMaster();
					G_cm.setCustomerNo(CUSTOMER_NO);
					CustomerMaster = searchByCusNo(G_cm);
					// ORDER_NO取得用カウンタ、カウントアップ
					countAlphabet++;
					countAll++;
					// 判定
					if (CustomerMaster == null) {
						// 使用可能
						break;
					} else {
						if(countAll < (99*26+1)) {
							if (countAlphabet >= 99 && CUSTOMER_NO.substring(1).equals("9900")) {
								if(MaxCustmerNoAlphabet < 90) {
									MaxCustmerNoAlphabet++;
									countAlphabet = 0;
								}else if(MaxCustmerNoAlphabet == 90) {
									MaxCustmerNoAlphabet = 65;
								}
							}
						}else {
							session.setAttribute("state", "使用可能な品番がありません。\\n処理を終了します｡");
							return line;
						}
					}
				} while (true);
			}
			
			st = con.prepareStatement("INSERT INTO CUSTOMER_MASTER VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, CUSTOMER_NO);
			st.setString(2, G_CustomerMaster.getCustomerName());
			st.setString(3, G_CustomerMaster.getBranchName());
			st.setString(4, G_CustomerMaster.getZipNo());
			st.setString(5, G_CustomerMaster.getAddress1());
			st.setString(6, G_CustomerMaster.getAddress2());
			st.setString(7, G_CustomerMaster.getAddress3());
			st.setString(8, G_CustomerMaster.getTel());
			st.setString(9, G_CustomerMaster.getFax());
			st.setString(10, G_CustomerMaster.getManager());
			st.setInt(11, Integer.parseInt(G_CustomerMaster.getDelivaryLeadtime()));
			st.setString(12, G_CustomerMaster.getEtc());
			st.setString(13, sdfymd.format(cl.getTime()));
			st.setString(14, user.getUserId());

			line = st.executeUpdate();
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
}
