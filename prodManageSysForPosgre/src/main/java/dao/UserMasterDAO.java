package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.G_UserMaster;
import bean.UserMaster;

public class UserMasterDAO extends DAO {
	/**
	 * 引数のユーザID/パスワードに合致するレコード情報を取得するメソッド
	 * 
	 * @param G_UserMasterクラスのインスタンス
	 * @return @return 該当レコードあり:UserMasterクラスのインスタンス 無:null
	 */
	public UserMaster searchByUM(G_UserMaster G_UserMaster) {
		UserMaster UserMaster = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM USER_MASTER WHERE USER_ID=? AND PASSWORD=?");
			st.setString(1, G_UserMaster.getUserId());
			st.setString(2, G_UserMaster.getPassword());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				UserMaster = new UserMaster();
				UserMaster.setUserId(rs.getString("USER_ID"));
				UserMaster.setName(rs.getString("NAME"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return UserMaster;
	}

	/**
	 * 引数のユーザIDに合致するレコード情報を取得するメソッド
	 * 
	 * @param ユーザID/パスワードが格納されたG_UserMasterクラスのインスタンス
	 * @return 該当データあり:UserMasterBean、無:null
	 */
	public UserMaster searchByID(G_UserMaster G_UserMaster) {
		UserMaster UserMaster = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM USER_MASTER WHERE USER_ID=?");
			st.setString(1, G_UserMaster.getUserId());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				UserMaster = new UserMaster();
				UserMaster.setUserId(rs.getString("USER_ID"));
				UserMaster.setName(rs.getString("NAME"));
				UserMaster.setPassword(rs.getString("PASSWORD"));
				UserMaster.setDept(rs.getString("DEPT"));
				UserMaster.setEtc(rs.getString("ETC"));
				UserMaster.setHireDate(rs.getString("HIRE_DATE"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return UserMaster;
	}

	/**
	 * UserMasterテーブル取得メソッド(検索条件なし)
	 *  →USER_IDリスト取得用メソッド
	 *
	 * @param 引数無し
	 * @return List<UserMaster> 「null：失敗」「null以外：成功」
	 */
	public List<UserMaster> searchAll() {
		// 戻り値用の変数宣言
		List<UserMaster> UserMasterList = new ArrayList<>();
		UserMaster UserMaster = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM USER_MASTER ORDER BY USER_ID ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				UserMaster = new UserMaster();
				UserMaster.setUserId(rs.getString("USER_ID"));
				UserMaster.setName(rs.getString("NAME"));
				UserMasterList.add(UserMaster);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return UserMasterList;
	}

	/**
	 * 引数のユーザ情報を新規登録するメソッド
	 * 
	 * @param ユーザID/パスワードが格納されたG_UserMasterクラスのインスタンス
	 * @return 0:新規登録失敗 1:新規登録成功
	 */
	public int insertByUM(G_UserMaster G_UserMaster, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// USER_ID取得用カウンタ
		int count = 0;
		String USER_ID = "";
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
			UserMaster UserMaster = null;
			// USER_ID作成
			do {
				// 注文番号のシーケンス値取得
				st = con.prepareStatement("SELECT NEXTVAL('USER_ID')");
				rs = st.executeQuery();
				while (rs.next()) {
					USER_ID = String.valueOf(rs.getInt("NEXTVAL"));
				}
				// 注文番号を左ゼロ埋めで6桁にする処理
				do {
					if (USER_ID.length() < 6) {
						USER_ID = "0" + USER_ID;
					} else {
						break;
					}
				} while (true);
				// 作成した注文番号の未使用確認
				G_UserMaster G_um = new G_UserMaster();
				G_um.setUserId(USER_ID);
				UserMaster = searchByID(G_um);
				if (UserMaster == null) {
					// 使用可能
					break;
				} else if (count >= 200000) {
					session.setAttribute("state", "使用可能なユーザーIDがありません。\\n処理を終了します｡");
					return line;
				}
				// ORDER_NO取得用カウンタ、カウントアップ
				count++;
			} while (true);
			// 新規登録処理
			st = con.prepareStatement("INSERT INTO USER_MASTER VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, USER_ID);
			st.setString(2, G_UserMaster.getName());
			st.setString(3, G_UserMaster.getPassword());
			st.setString(4, G_UserMaster.getDept());
			st.setString(5, G_UserMaster.getEtc());
			st.setString(6, G_UserMaster.getHireDate());
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
	 * 引数のユーザIDに合致するレコードを更新するメソッド
	 * 
	 * @param ユーザID/パスワードが格納されたG_UserMasterクラスのインスタンス
	 * @return 0:更新失敗 1:更新成功
	 */
	public int updateByUM(G_UserMaster G_UserMaster, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("UPDATE USER_MASTER SET NAME=?, PASSWORD=?, DEPT=?, ETC=?, HIRE_DATE=?, REGIST_DATE=?, REGIST_USER=? WHERE USER_ID=?");
			st.setString(1, G_UserMaster.getName());
			st.setString(2, G_UserMaster.getPassword());
			st.setString(3, G_UserMaster.getDept());
			st.setString(4, G_UserMaster.getEtc());
			st.setString(5, G_UserMaster.getHireDate());
			st.setString(6, sdfymd.format(cl.getTime()));
			st.setString(7, user.getUserId());
			st.setString(8, G_UserMaster.getUserId());
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
	 * UserMasterテーブル取得メソッド(検索条件なし)
	 *  →動作確認用メソッド(=portfolioとして成立させる為､テーブル情報を公開する事が目的)
	 *
	 * @param 引数無し
	 * @return List<UserMaster> 「null：失敗」「null以外：成功」
	 */
	public List<UserMaster> searchAllForPortfolio() {
		// 戻り値用の変数宣言
		List<UserMaster> UserMasterList = new ArrayList<>();
		UserMaster UserMaster = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM USER_MASTER ORDER BY USER_ID ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				UserMaster = new UserMaster();
				UserMaster.setUserId(rs.getString("USER_ID"));
				UserMaster.setName(rs.getString("NAME"));
				UserMaster.setPassword(rs.getString("PASSWORD"));
				UserMaster.setDept(rs.getString("DEPT"));
				UserMaster.setEtc(rs.getString("ETC"));
				UserMaster.setHireDate(rs.getString("HIRE_DATE"));
				UserMaster.setRegistDate(rs.getString("REGIST_DATE"));
				UserMaster.setRegistUser(rs.getString("REGIST_USER"));
				UserMasterList.add(UserMaster);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return UserMasterList;
	}
}