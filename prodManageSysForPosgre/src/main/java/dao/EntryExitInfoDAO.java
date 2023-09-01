package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.EntryExitInfo;
import bean.G_EntryExitInfo;


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








	public List<EntryExitInfo> execution(String productNo) throws Exception {
		List<EntryExitInfo> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ?");
		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			EntryExitInfo enEx = new EntryExitInfo();
			enEx.setEnExId(rs.getString("EN_EX_ID"));
			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
			if (rs.getInt("NYUKO_QTY") >=1) {
				enEx.setJudge("入");
				enEx.setCount(rs.getInt("NYUKO_QTY"));
			} else if (rs.getInt("SYUKO_QTY") >=1) {
				enEx.setCount(rs.getInt("SYUKO_QTY"));
				enEx.setJudge("出");
			}
			enEx.setReason(rs.getString("REASON"));
			list.add(enEx);
		}
		st.close();
		con.close();

		return list;
	}
	public List<EntryExitInfo> searchEntry(String productNo) throws Exception {
		List<EntryExitInfo> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ? AND SYUKO_QTY >= 1");
		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			EntryExitInfo enEx = new EntryExitInfo();
			enEx.setEnExId(rs.getString("EN_EX_ID"));
			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
			enEx.setJudge("出");
			enEx.setCount(rs.getInt("SYUKO_QTY"));
			enEx.setReason(rs.getString("REASON"));
			list.add(enEx);
		}
		st.close();
		con.close();

		return list;
	}

	public List<EntryExitInfo> searchIssue(String productNo) throws Exception {
		List<EntryExitInfo> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ? AND NYUKO_QTY >= 1");
		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			EntryExitInfo enEx = new EntryExitInfo();
			enEx.setEnExId(rs.getString("EN_EX_ID"));
			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
			enEx.setJudge("入");
			enEx.setCount(rs.getInt("NYUKO_QTY"));
			enEx.setReason(rs.getString("REASON"));
			list.add(enEx);
		}
		st.close();
		con.close();

		return list;
	}
	public List<EntryExitInfo> searchStDate(EntryExitInfo eei) throws Exception {
		List<EntryExitInfo> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ? AND EN_EX_DATE >= ?");
		st.setString(1, eei.getProductNo());//(何番目の?か,置き換えるキーワード)
		st.setString(2, eei.getEnExDate());//(何番目の?か,置き換えるキーワード)
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			EntryExitInfo enEx = new EntryExitInfo();
			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
			if (rs.getInt("NYUKO_QTY") >= 1) {
				enEx.setJudge("入");
				enEx.setCount(rs.getInt("NYUKO_QTY"));
			}else {
				enEx.setJudge("出");
				enEx.setCount(rs.getInt("SYUKO_QTY"));
			}
			enEx.setReason(rs.getString("REASON"));
			list.add(enEx);
		}
		st.close();
		con.close();

		return list;
	}

	public List<EntryExitInfo> searchEdDate(EntryExitInfo eei) throws Exception {
		List<EntryExitInfo> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO WHERE PRODUCT_NO = ? AND EN_EX_DATE <= ?");
		st.setString(1, eei.getProductNo());
		st.setString(2, eei.getEnExDate());
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			EntryExitInfo enEx = new EntryExitInfo();
			enEx.setEnExDate(rs.getString("EN_EX_DATE"));
			if (rs.getInt("NYUKO_QTY") >= 1) {
				enEx.setJudge("入");
				enEx.setCount(rs.getInt("NYUKO_QTY"));
			}else {
				enEx.setJudge("出");
				enEx.setCount(rs.getInt("SYUKO_QTY"));
			}
			enEx.setReason(rs.getString("REASON"));
			list.add(enEx);
		}
		st.close();
		con.close();

		return list;
	}

	public int updateByEnEx(EntryExitInfo enEx) {
		int result = 0;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement(
					"UPDATE ENTRY_EXIT_INFO "
							+ "SET EN_EX_DATE= ?,"
							+ " PRODUCT_NO = ?,"
							+ " NYUKO_QTY = ?,"
							+ " SYUKO_QTY = ?,"
							+ " REASON = ?,"
							+ " REGIST_DATE = ?,"
							+ " REGIST_USER = ? WHERE EN_EX_ID = ? ");

			st.setString(1, enEx.getEnExDate());
			st.setString(2, enEx.getProductNo());
			st.setInt(3, enEx.getNyukoQty());
			st.setInt(4, enEx.getSyukoQty());
			st.setString(5, enEx.getReason());
			st.setString(6, enEx.getRegistDate());
			st.setString(7, enEx.getRegistUser());
			st.setString(8, enEx.getEnExId());
			result = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public int deleteByEnEx(EntryExitInfo enEx) {
		int result = 0;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("DELETE FROM ENTRY_EXIT_INFO WHERE EN_EX_ID = ?");
			st.setString(1, enEx.getEnExId());
			result = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public int insertByEnExNyuko(EntryExitInfo enEx) {
		int result = 0;
		try {
			//登録日用にCalendarクラスのオブジェクトを生成する
			Calendar cl = Calendar.getInstance();
			//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement(
					"INSERT INTO ENTRY_EXIT_INFO VALUES(LPAD(EN_EX_ID.NEXTVAL,8,'0'),?,?,?,?,?,?,?)");
			st.setString(1, sdf.format(cl.getTime()));
			st.setString(2, enEx.getProductNo());
			st.setInt(3, enEx.getNyukoQty());
			st.setString(4, null);
			st.setString(5, enEx.getReason());
			st.setString(6, sdf.format(cl.getTime()));
			st.setString(7,enEx.getRegistUser());

			result = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public int insertByEnExSyuko(EntryExitInfo enEx) {
		int result = 0;
		try {
			//登録日用にCalendarクラスのオブジェクトを生成する
			Calendar cl = Calendar.getInstance();
			//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement(
					"INSERT INTO ENTRY_EXIT_INFO VALUES(LPAD(EN_EX_ID.NEXTVAL,8,'0'),?,?,?,?,?,?,?)");
			st.setString(1, sdf.format(cl.getTime()));
			st.setString(2, enEx.getProductNo());
			st.setString(3, null);
			st.setInt(4, enEx.getSyukoQty());
			st.setString(5, enEx.getReason());
			st.setString(6, sdf.format(cl.getTime()));
			st.setString(7, enEx.getRegistUser());
			result = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
