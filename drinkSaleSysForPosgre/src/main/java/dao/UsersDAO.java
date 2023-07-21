package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import bean.Users;

public class UsersDAO extends DAO {
	/**
	 * Usersテーブルログイン判定メソッド
	 * 
	 * @param Usersビーン
	 * @return Usersビーン 「null：ログイン不可」「インスタンス有：ログイン可」
	 */
	public Users searchUsersByUserIDAndPassword(Users users) {
		Users us = null;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ? AND PASSWORD = ?");
			st.setString(1, users.getUserID());
			st.setString(2, users.getPassword());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				us = new Users();
				us.setUserID(rs.getString("USER_ID"));
				// us.setPassword(rs.getString("PASSWORD"));
				us.setUserName(rs.getString("USER_NAME"));
				// us.setEtc(rs.getString("ETC"));
				// us.setRegistDate(rs.getString("REGIST_DATE"));
				// us.setRegistUser(rs.getString("REGIST_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return us;
	}

	/**
	 * Usersテーブル参照メソッド
	 * 
	 * @param Usersビーン
	 * @return Usersビーン 「null：失敗」「インスタンス有：成功」
	 */
	public Users searchUsersByUserID(Users users) {
		Users us = null;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?");
			st.setString(1, users.getUserID());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				us = new Users();
				us.setUserID(rs.getString("USER_ID"));
				// us.setPassword(rs.getString("PASSWORD"));
				us.setUserName(rs.getString("USER_NAME"));
				// us.setEtc(rs.getString("ETC"));
				// us.setRegistDate(rs.getString("REGIST_DATE"));
				// us.setRegistUser(rs.getString("REGIST_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return us;
	}

	/**
	 * Usersテーブル参照メソッド
	 * 
	 * @param Usersビーン
	 * @return Usersビーン 「null：失敗」「インスタンス有：成功」
	 */
	public boolean passwordCheckByUserID(HttpSession session, Users us) {
		boolean judge = false;
		int count = 0;
		Users user = (Users) session.getAttribute("user");
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?");
			st.setString(1, user.getUserID());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if (rs.getString("PASSWORD").equals(us.getPassword())) {
					judge = true;
				}
				count++;
			}
			if (count != 1) {
				judge = false;
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return judge;
	}

	/**
	 * Usersテーブル登録メソッド
	 * 
	 * @param Usersビーン、Usersビーン(=セッションに属性値として登録されているログイン者を格納したビーン)
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertToUsers(Users us, Users users) {
		int line = 0;
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("INSERT INTO USERS VALUES(?, ?, ?, ?, ?, ?)");
			st.setString(1, us.getUserID());
			st.setString(2, us.getPassword());
			st.setString(3, us.getUserName());
			st.setString(4, us.getEtc());
			st.setString(5, sdf.format(cl.getTime()));
			st.setString(6, users.getUserID());

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
	 * Usersテーブル更新メソッド
	 * 
	 * @param Usersビーン、Usersビーン(=セッションに属性値として登録されているログイン者を格納したビーン)
	 * @return 整数 「0：失敗」「1：成功」 「USER_ID」は更新しない(=出来ない=primary key)
	 */
	public int updateToUsers(Users us, Users users) {
		int line = 0;
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement(
					"UPDATE USERS SET PASSWORD = ?, USER_NAME = ?, ETC = ?, REGIST_DATE = ?, REGIST_USER = ?  WHERE USER_ID = ?");
			st.setString(1, us.getPassword());
			st.setString(2, us.getUserName());
			st.setString(3, us.getEtc());
			st.setString(4, sdf.format(cl.getTime()));
			st.setString(5, users.getUserID());
			st.setString(6, us.getUserID());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * Usersテーブル レコード削除メソッド*
	 * 
	 * @param Usersビーン、Usersビーン(=セッションに属性値として登録されているログイン者を格納したビーン)
	 * @return 整数 「0：失敗」「1：成功」 「USER_ID」は更新しない(=出来ない=primary key)
	 */
	public int deleteToUsers(Users us) {
		int line = 0;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?");
			st.setString(1, us.getUserID());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * Usersテーブル参照メソッド
	 * 
	 * @param なし
	 * @return Usersビーンが格納されたリスト 「null：失敗」「インスタンス有：成功」
	 */
	public List<Users> searchUsersAll() {
		// 戻り値用の変数(リスト型)を宣言
		List<Users> Users = new ArrayList<>();
		Users user = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM USERS ORDER BY USER_ID ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				user = new Users();
				user.setUserID(rs.getString("USER_ID"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setUserName(rs.getString("USER_NAME"));
				user.setEtc(rs.getString("ETC"));
				user.setRegistDate(rs.getString("REGIST_DATE"));
				user.setRegistUser(rs.getString("REGIST_USER"));
				Users.add(user);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return Users;
	}
}
