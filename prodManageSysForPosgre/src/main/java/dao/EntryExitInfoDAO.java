package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.EntryExitInfo;
import bean.G_EntryExitInfo;
import bean.G_Order;
import bean.G_Shipping;
import bean.UserMaster;


//public EntryExitInfo searchByProNo(String proNo) throws Exception {
//	EntryExitInfo enEx = null;
//
//	Connection con = getConnection();
//
//	PreparedStatement st;
//	st = con.prepareStatement(
//			"SELECT EEI.EN_EX_ID, PM.PRODUCT_NAME, EEI.EN_EX_DATE, EEI.PRODUCT_NO, EEI.NYUKO_QTY, EEI.SYUKO_QTY, EEI.REASON FROM ENTRY_EXIT_INFO EEI INNER JOIN PRODUCT_MASTER PM ON EEI.PRODUCT_NO = PM.PRODUCT_NO WHERE EEI.PRODUCT_NO = ?");
//	st.setString(1, proNo);
//	ResultSet rs = st.executeQuery();
//
//	while (rs.next()) {
//		enEx = new EntryExitInfo();
//		enEx.setEnExId(rs.getString("EN_EX_ID"));
//		enEx.setEnExDate(rs.getString("EN_EX_DATE"));
//		enEx.setProductNo(rs.getString("PRODUCT_NO"));
//		enEx.setNyukoQty(rs.getInt("NYUKO_QTY"));
//		enEx.setSyukoQty(rs.getInt("SYUKO_QTY"));
//		enEx.setReason(rs.getString("REASON"));
//		enEx.setProductName(rs.getString("PRODUCT_NAME"));
//
//	}
//
//	st.close();
//	con.close();
//	return enEx;
//}
//
//	public List<EntryExitInfo> execution(String productNo) throws Exception {
//		List<EntryExitInfo> list = new ArrayList<>();
//
//		Connection con = getConnection();
//		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ?");
//		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
//		ResultSet rs = st.executeQuery();
//
//		while (rs.next()) {
//			EntryExitInfo enEx = new EntryExitInfo();
//			enEx.setEnExId(rs.getString("EN_EX_ID"));
//			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
//			if (rs.getInt("NYUKO_QTY") >=1) {
//				enEx.setJudge("入");
//				enEx.setCount(rs.getInt("NYUKO_QTY"));
//			} else if (rs.getInt("SYUKO_QTY") >=1) {
//				enEx.setCount(rs.getInt("SYUKO_QTY"));
//				enEx.setJudge("出");
//			}
//			enEx.setReason(rs.getString("REASON"));
//			list.add(enEx);
//		}
//		st.close();
//		con.close();
//
//		return list;
//	}
//	public List<EntryExitInfo> searchEntry(String productNo) throws Exception {
//		List<EntryExitInfo> list = new ArrayList<>();
//
//		Connection con = getConnection();
//		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ? AND SYUKO_QTY >= 1");
//		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
//		ResultSet rs = st.executeQuery();
//
//		while (rs.next()) {
//			EntryExitInfo enEx = new EntryExitInfo();
//			enEx.setEnExId(rs.getString("EN_EX_ID"));
//			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
//			enEx.setJudge("出");
//			enEx.setCount(rs.getInt("SYUKO_QTY"));
//			enEx.setReason(rs.getString("REASON"));
//			list.add(enEx);
//		}
//		st.close();
//		con.close();
//
//		return list;
//	}
//
//	public List<EntryExitInfo> searchIssue(String productNo) throws Exception {
//		List<EntryExitInfo> list = new ArrayList<>();
//
//		Connection con = getConnection();
//		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ? AND NYUKO_QTY >= 1");
//		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
//		ResultSet rs = st.executeQuery();
//
//		while (rs.next()) {
//			EntryExitInfo enEx = new EntryExitInfo();
//			enEx.setEnExId(rs.getString("EN_EX_ID"));
//			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
//			enEx.setJudge("入");
//			enEx.setCount(rs.getInt("NYUKO_QTY"));
//			enEx.setReason(rs.getString("REASON"));
//			list.add(enEx);
//		}
//		st.close();
//		con.close();
//
//		return list;
//	}
//	public List<EntryExitInfo> searchStDate(EntryExitInfo eei) throws Exception {
//		List<EntryExitInfo> list = new ArrayList<>();
//
//		Connection con = getConnection();
//		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ? AND EN_EX_DATE >= ?");
//		st.setString(1, eei.getProductNo());//(何番目の?か,置き換えるキーワード)
//		st.setString(2, eei.getEnExDate());//(何番目の?か,置き換えるキーワード)
//		ResultSet rs = st.executeQuery();
//
//		while (rs.next()) {
//			EntryExitInfo enEx = new EntryExitInfo();
//			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
//			if (rs.getInt("NYUKO_QTY") >= 1) {
//				enEx.setJudge("入");
//				enEx.setCount(rs.getInt("NYUKO_QTY"));
//			}else {
//				enEx.setJudge("出");
//				enEx.setCount(rs.getInt("SYUKO_QTY"));
//			}
//			enEx.setReason(rs.getString("REASON"));
//			list.add(enEx);
//		}
//		st.close();
//		con.close();
//
//		return list;
//	}
//
//	public List<EntryExitInfo> searchEdDate(EntryExitInfo eei) throws Exception {
//		List<EntryExitInfo> list = new ArrayList<>();
//
//		Connection con = getConnection();
//		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ? AND EN_EX_DATE <= ?");
//		st.setString(1, eei.getProductNo());
//		st.setString(2, eei.getEnExDate());
//		ResultSet rs = st.executeQuery();
//
//		while (rs.next()) {
//			EntryExitInfo enEx = new EntryExitInfo();
//			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
//			if (rs.getInt("NYUKO_QTY") >= 1) {
//				enEx.setJudge("入");
//				enEx.setCount(rs.getInt("NYUKO_QTY"));
//			}else {
//				enEx.setJudge("出");
//				enEx.setCount(rs.getInt("SYUKO_QTY"));
//			}
//			enEx.setReason(rs.getString("REASON"));
//			list.add(enEx);
//		}
//		st.close();
//		con.close();
//
//		return list;
//	}
//
//	public int updateByEnEx(EntryExitInfo enEx) {
//		int result = 0;
//		try {
//			Connection con = getConnection();
//			PreparedStatement st = con.prepareStatement(
//					"UPDATE ENTRY_EXIT_INFO "
//							+ "SET EN_EX_DATE= ?,"
//							+ " PRODUCT_NO = ?,"
//							+ " NYUKO_QTY = ?,"
//							+ " SYUKO_QTY = ?,"
//							+ " REASON = ?,"
//							+ " REGIST_DATE = ?,"
//							+ " REGIST_USER = ? WHERE EN_EX_ID = ? ");
//
//			st.setString(1, enEx.getEnExDate());
//			st.setString(2, enEx.getProductNo());
//			st.setInt(3, enEx.getNyukoQty());
//			st.setInt(4, enEx.getSyukoQty());
//			st.setString(5, enEx.getReason());
//			st.setString(6, enEx.getRegistDate());
//			st.setString(7, enEx.getRegistUser());
//			st.setString(8, enEx.getEnExId());
//			result = st.executeUpdate();
//
//			st.close();
//			con.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//
//	}
//
//	public int deleteByEnEx(EntryExitInfo enEx) {
//		int result = 0;
//		try {
//			Connection con = getConnection();
//			PreparedStatement st = con.prepareStatement("DELETE FROM ENTRY_EXIT_INFO WHERE EN_EX_ID = ?");
//			st.setString(1, enEx.getEnExId());
//			result = st.executeUpdate();
//
//			st.close();
//			con.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//
//	}
//

public class EntryExitInfoDAO extends DAO {
	/**
	 * ENTRY_EXIT_INFOテーブル参照メソッド
	 * 
	 * @param String orderNo
	 * @return EntryExitInfoビーン 「null：失敗」「インスタンス有：成功」
	 */
	public EntryExitInfo searchByEnId(String enExId) {
		EntryExitInfo EntryExitInfo = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE EN_EX_ID = ?");
			st.setString(1, enExId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				EntryExitInfo = new EntryExitInfo();
				EntryExitInfo.setEnExId(rs.getString("EN_EX_ID"));
				EntryExitInfo.setEnExDate(rs.getString("EN_EX_DATE"));
				EntryExitInfo.setProductNo(rs.getString("PRODUCT_NO"));
				EntryExitInfo.setNyukoQty(rs.getInt("NYUKO_QTY"));
				EntryExitInfo.setSyukoQty(rs.getInt("SYUKO_QTY"));
				EntryExitInfo.setReason(rs.getString("REASON"));
				EntryExitInfo.setRegistDate(rs.getString("REGIST_DATE"));
				EntryExitInfo.setRegistUser(rs.getString("REGIST_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return EntryExitInfo;
	}
	
	/**
	 * ENTRY_EXIT_INFOテーブル参照メソッド
	 * 
	 * @param G_EntryExitInfo
	 * @return EntryExitInfoビーン 「null：失敗」「インスタンス有：成功」
	 */
	public EntryExitInfo searchByEnId(G_EntryExitInfo G_EntryExitInfo) {
		return searchByEnId(G_EntryExitInfo.getEnExId());
	}
	
	/**
	 * ENTRY_EXIT_INFOテーブルへの入庫レコード新規登録メソッド
	 * 
	 * @param G_EntryExitInfo
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertForNyuko(G_EntryExitInfo G_EntryExitInfo, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// EN_EX_ID取得用カウンタ
		int count = 0;
		String EN_EX_ID = "";
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			EntryExitInfo enExIdCheck = new EntryExitInfo();
			// EN_EX_ID作成
			do {
				// 入出庫番号のシーケンス値取得
				st = con.prepareStatement("SELECT NEXTVAL('EN_EX_ID')");
				rs = st.executeQuery();
				while (rs.next()) {
					EN_EX_ID = String.valueOf(rs.getInt("NEXTVAL"));
				}
				// 入出庫番号を左ゼロ埋めで8桁にする処理
				do {
					if (EN_EX_ID.length() < 8) {
						EN_EX_ID = "0" + EN_EX_ID;
					} else {
						break;
					}
				} while (true);
				// 作成した入出庫番号の未使用確認
				G_EntryExitInfo G_eei = new G_EntryExitInfo();
				G_eei.setEnExId(EN_EX_ID);
				enExIdCheck = searchByEnId(G_eei);
				if (enExIdCheck == null) {
					// 使用可能
					break;
				} else if (count >= 25000000) {
					session.setAttribute("state", "使用可能な入出庫番号がありません。\\n処理を終了します｡");
					return line;
				}
				// EN_EX_ID取得用カウンタ、カウントアップ
				count++;
			} while (true);
			// 新規登録処理
			st = con.prepareStatement("INSERT INTO ENTRY_EXIT_INFO VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, EN_EX_ID);
			st.setString(2, sdfymd.format(cl.getTime()));
			st.setString(3, G_EntryExitInfo.getProductNo());
			st.setInt(4, Integer.parseInt(G_EntryExitInfo.getNyukoQty()));
			st.setString(5, null);
			st.setString(6, G_EntryExitInfo.getReason());
			st.setString(7, sdfymd.format(cl.getTime()));
			st.setString(8, user.getUserId());
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
	 * ENTRY_EXIT_INFOテーブルへの入庫レコード新規登録メソッド
	 * 
	 * @param G_Shipping
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertForNyuko(G_Shipping G_Shipping, HttpServletRequest request) {
		G_EntryExitInfo G_EntryExitInfo = new G_EntryExitInfo();
		G_EntryExitInfo.setProductNo(G_Shipping.getProductNo());
		G_EntryExitInfo.setNyukoQty(G_Shipping.getShipQty());
		G_EntryExitInfo.setSyukoQty("");
		G_EntryExitInfo.setReason("出荷処理(出庫処理)のキャンセル");
		return insertForNyuko(G_EntryExitInfo, request);
	}
	
	/**
	 * ENTRY_EXIT_INFOテーブルへの入庫レコード新規登録メソッド
	 * 
	 * @param G_Order
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertForNyuko(G_Order G_Order, HttpServletRequest request) {
		G_EntryExitInfo G_EntryExitInfo = new G_EntryExitInfo();
		G_EntryExitInfo.setProductNo(G_Order.getProductNo());
		G_EntryExitInfo.setNyukoQty(G_Order.getArrivalQty());
		G_EntryExitInfo.setSyukoQty("");
		G_EntryExitInfo.setReason("入荷処理");
		return insertForNyuko(G_EntryExitInfo, request);
	}
	
	/**
	 * ENTRY_EXIT_INFOテーブルへの出庫レコード新規登録メソッド
	 * 
	 * @param G_EntryExitInfo
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertForSyuko(G_EntryExitInfo G_EntryExitInfo, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// EN_EX_ID取得用カウンタ
		int count = 0;
		String EN_EX_ID = "";
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			EntryExitInfo enExIdCheck = new EntryExitInfo();
			// EN_EX_ID作成
			do {
				// 入出庫番号のシーケンス値取得
				st = con.prepareStatement("SELECT NEXTVAL('EN_EX_ID')");
				rs = st.executeQuery();
				while (rs.next()) {
					EN_EX_ID = String.valueOf(rs.getInt("NEXTVAL"));
				}
				// 入出庫番号を左ゼロ埋めで8桁にする処理
				do {
					if (EN_EX_ID.length() < 8) {
						EN_EX_ID = "0" + EN_EX_ID;
					} else {
						break;
					}
				} while (true);
				// 作成した入出庫番号の未使用確認
				G_EntryExitInfo G_eei = new G_EntryExitInfo();
				G_eei.setEnExId(EN_EX_ID);
				enExIdCheck = searchByEnId(G_eei);
				if (enExIdCheck == null) {
					// 使用可能
					break;
				} else if (count >= 25000000) {
					session.setAttribute("state", "使用可能な入出庫番号がありません。\\n処理を終了します｡");
					return line;
				}
				// EN_EX_ID取得用カウンタ、カウントアップ
				count++;
			} while (true);
			// 新規登録処理
			st = con.prepareStatement("INSERT INTO ENTRY_EXIT_INFO VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, EN_EX_ID);
			st.setString(2, sdfymd.format(cl.getTime()));
			st.setString(3, G_EntryExitInfo.getProductNo());
			st.setString(4, null);
			st.setInt(5, Integer.parseInt(G_EntryExitInfo.getSyukoQty()));
			st.setString(6, G_EntryExitInfo.getReason());
			st.setString(7, sdfymd.format(cl.getTime()));
			st.setString(8, user.getUserId());
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
	 * ENTRY_EXIT_INFOテーブルへの出庫レコード新規登録メソッド
	 * 
	 * @param G_Shipping
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertForSyuko(G_Shipping G_Shipping, HttpServletRequest request) {
		G_EntryExitInfo G_EntryExitInfo = new G_EntryExitInfo();
		G_EntryExitInfo.setProductNo(G_Shipping.getProductNo());
		G_EntryExitInfo.setNyukoQty("");
		G_EntryExitInfo.setSyukoQty(G_Shipping.getShipQty());
		G_EntryExitInfo.setReason("出荷処理");
		return insertForSyuko(G_EntryExitInfo, request);
	}
	
	/**
	 * ENTRY_EXIT_INFOテーブルへの出庫レコード新規登録メソッド
	 * 
	 * @param G_Order
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertForSyuko(G_Order G_Order, HttpServletRequest request) {
		G_EntryExitInfo G_EntryExitInfo = new G_EntryExitInfo();
		G_EntryExitInfo.setProductNo(G_Order.getProductNo());
		G_EntryExitInfo.setNyukoQty("");
		G_EntryExitInfo.setSyukoQty(G_Order.getArrivalQty());
		G_EntryExitInfo.setReason("入荷処理(入庫処理)のキャンセル");
		return insertForSyuko(G_EntryExitInfo, request);
	}
}
