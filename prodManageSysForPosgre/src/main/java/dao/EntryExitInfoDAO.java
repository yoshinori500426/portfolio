package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import action.MainAction;
import bean.EntryExitInfo;
import bean.G_EntryExitInfo;
import bean.UserMaster;

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
				EntryExitInfo.setEnExDate(new MainAction().dateChangeForHTML(rs.getString("EN_EX_DATE")));
				EntryExitInfo.setProductNo(rs.getString("PRODUCT_NO"));
				EntryExitInfo.setNyukoQty(rs.getInt("NYUKO_QTY"));
				EntryExitInfo.setSyukoQty(rs.getInt("SYUKO_QTY"));
				EntryExitInfo.setReason(rs.getString("REASON"));
				EntryExitInfo.setRegistDate(new MainAction().dateChangeForHTML(rs.getString("REGIST_DATE")));
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
	 * ENTRY_EXIT_INFOテーブル取得メソッド(検索条件なし)
	 *  →EN_EX_IDリスト取得用メソッド
	 *
	 * @param 引数無し
	 * @return List<EntryExitInfo> 「null：失敗」「null以外：成功」
	 */
	public List<EntryExitInfo> searchAll() {
		// 戻り値用の変数宣言
		List<EntryExitInfo> EntryExitInfoList = new ArrayList<>();
		EntryExitInfo EntryExitInfo = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO ORDER BY EN_EX_ID DESC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				EntryExitInfo = new EntryExitInfo();
				EntryExitInfo.setEnExId(rs.getString("EN_EX_ID"));
				EntryExitInfo.setEnExDate(new MainAction().dateChangeForHTML(rs.getString("EN_EX_DATE")));
				EntryExitInfo.setProductNo(rs.getString("PRODUCT_NO"));
				EntryExitInfo.setNyukoQty(rs.getInt("NYUKO_QTY"));
				EntryExitInfo.setSyukoQty(rs.getInt("SYUKO_QTY"));
				EntryExitInfo.setReason(rs.getString("REASON"));
				EntryExitInfo.setRegistDate(new MainAction().dateChangeForHTML(rs.getString("REGIST_DATE")));
				EntryExitInfo.setRegistUser(rs.getString("REGIST_USER"));
				EntryExitInfoList.add(EntryExitInfo);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return EntryExitInfoList;
	}

	/**
	 * ENTRY_EXIT_INFOテーブルへの新規登録メソッド
	 * 
	 * @param G_EntryExitInfo
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insert(G_EntryExitInfo G_EntryExitInfo, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// EN_EX_ID取得用カウンタ
		int count = 0;
		String EN_EX_ID = "";
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
			st.setString(2, G_EntryExitInfo.getEnExDate()); // 入出庫日
			st.setString(3, G_EntryExitInfo.getProductNo());
			if (!G_EntryExitInfo.getNyukoQty().equals("") && G_EntryExitInfo.getSyukoQty().equals("")) {
				st.setInt(4, Integer.parseInt(G_EntryExitInfo.getNyukoQty()));
				st.setNull(5, java.sql.Types.INTEGER);
			} else if (G_EntryExitInfo.getNyukoQty().equals("") && !G_EntryExitInfo.getSyukoQty().equals("")) {
				st.setNull(4, java.sql.Types.INTEGER);
				st.setInt(5, Integer.parseInt(G_EntryExitInfo.getSyukoQty()));
			} else {
				return line;
			}
			st.setString(6, G_EntryExitInfo.getReason());
			st.setString(7, G_EntryExitInfo.getRegistDate()); // 登録日
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
	 * ENTRY_EXIT_INFOテーブルへの更新メソッド
	 * 
	 * @param G_EntryExitInfo
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int update(G_EntryExitInfo G_EntryExitInfo) {
		// 戻り値用変数
		int line = 0;
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			st = con.prepareStatement("UPDATE ENTRY_EXIT_INFO SET EN_EX_DATE=?, NYUKO_QTY=?, SYUKO_QTY=?, REASON=? WHERE EN_EX_ID=?");
			st.setString(1, G_EntryExitInfo.getEnExDate());
			if (!G_EntryExitInfo.getNyukoQty().equals("") && G_EntryExitInfo.getSyukoQty().equals("")) {
				st.setInt(2, Integer.parseInt(G_EntryExitInfo.getNyukoQty()));
				st.setNull(3, java.sql.Types.INTEGER);
			} else if (G_EntryExitInfo.getNyukoQty().equals("") && !G_EntryExitInfo.getSyukoQty().equals("")) {
				st.setNull(2, java.sql.Types.INTEGER);
				st.setInt(3, Integer.parseInt(G_EntryExitInfo.getSyukoQty()));
			} else {
				return line;
			}
			st.setString(4, G_EntryExitInfo.getReason());
			st.setString(5, G_EntryExitInfo.getEnExId());
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
	 * ENTRY_EXIT_INFOテーブルへの更新メソッド
	 * 
	 * @param G_EntryExitInfo
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int delete(G_EntryExitInfo G_EntryExitInfo) {
		// 戻り値用変数
		int line = 0;
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			st = con.prepareStatement("DELETE FROM ENTRY_EXIT_INFO WHERE EN_EX_ID=?");
			st.setString(1, G_EntryExitInfo.getEnExId());
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
	 * ENTRY_EXIT_INFOテーブル取得メソッド(検索条件なし)
	 *  →動作確認用メソッド(=portfolioとして成立させる為､テーブル情報を公開する事が目的)
	 *
	 * @param 引数無し
	 * @return List<EntryExitInfo> 「null：失敗」「null以外：成功」
	 */
	public List<EntryExitInfo> searchAllForPortfolio() {
		// 戻り値用の変数宣言
		List<EntryExitInfo> EntryExitInfoList = new ArrayList<>();
		EntryExitInfo EntryExitInfo = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM ENTRY_EXIT_INFO ORDER BY EN_EX_ID DESC");
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
				EntryExitInfoList.add(EntryExitInfo);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return EntryExitInfoList;
	}
}
