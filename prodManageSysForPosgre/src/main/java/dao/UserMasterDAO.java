package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.UserMaster;

/**
 * @author TRAINING-PC5
 * search文
 */

public class UserMasterDAO extends DAO {

	public boolean searchUserTrueFalse(String userId, String name) {

		//初期値はfalseでいい
		boolean result = false;

		try {

			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement(
					"SELECT * FROM USER_MASTER WHERE USER_ID=? AND PASSWORD=?");
			st.setString(1, userId);
			st.setString(2, name);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result = true;
			}

			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}

		return result;
	}

	//idSearch
	//UserMasterBeanのとこは１つしかできないので
	//Beanにつめて指定する

	/**
	 * searchByUM
	 * usermasterテーブルを引数の名前で検索した結果を戻す。
	 * @param user_id
	 * @return 該当データあり:UserMasterBean、無:null
	 */

	public UserMaster searchByUM(String user_id, String password) {

		//戻り値用のBeanを用意する。初期化はnull
		UserMaster getUserDate = null;
		try {

			Connection con = getConnection();

			//stにselect文を入れる。
			//テーブルを想像する
			PreparedStatement st = con.prepareStatement(
					"SELECT * FROM USER_MASTER WHERE USER_ID=? AND PASSWORD = ?");
			//SQLの結果をResultSetの変数に代入
			st.setString(1, user_id);
			st.setString(2, password);

			//selectの場合はquery
			//result
			ResultSet rs = st.executeQuery();

			//1つでもrsは必ず１回する
			while (rs.next()) {
				//先に用意していたgetUserDataを利用できるようにnewする。
				getUserDate = new UserMaster();
				//Beanに値をセットする。rs get型（カラム名）で取得
				//Beanのセッターでセットする。
				//インスタンス名.setBeanのフィールド名(ここにセットしたい値もしくはメソッドを記載)
				//idはテーブルの列名から取得
				getUserDate.setName(rs.getString("name"));
				//インスタンス名.setフィールド名(ここで値を渡している)
				getUserDate.setUserId(rs.getString("user_id"));

			}

			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}

		return getUserDate;
	}

	/**
	 * searchByID
	 * usermasterテーブルを引数のidで検索した結果を戻す。
	 * @param user_id
	 * @return 該当データあり:UserMasterBean、無:null
	 */

	public UserMaster searchByID(String user_id) {

		//戻り値用のBeanを用意する。初期化はnull
		UserMaster getUserDate = null;
		try {

			Connection con = getConnection();

			//stにselect文を入れる。
			//テーブルを想像する
			PreparedStatement st = con.prepareStatement(
					"SELECT * FROM USER_MASTER WHERE USER_ID=?");
			//SQLの結果をResultSetの変数に代入
			st.setString(1, user_id);

			//selectの場合はquery
			//result
			ResultSet rs = st.executeQuery();

			//1つでもrsは必ず１回する
			while (rs.next()) {
				//先に用意していたgetUserDataを利用できるようにnewする。
				getUserDate = new UserMaster();
				//Beanに値をセットする。rs get型（カラム名）で取得
				//Beanのセッターでセットする。
				//インスタンス名.setBeanのフィールド名(ここにセットしたい値もしくはメソッドを記載)
				//idはテーブルの列名から取得
				getUserDate.setUserId(rs.getString("user_id"));
				//インスタンス名.setフィールド名(ここで値を渡している)
				getUserDate.setName(rs.getString("name"));
				getUserDate.setPassword(rs.getString("password"));
				getUserDate.setDept(rs.getString("dept"));
				getUserDate.setEtc(rs.getString("etc"));
				getUserDate.setHireDate(rs.getString("hire_date"));

			}

			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}

		return getUserDate;
	}

	/**
	 * @author TRAINING-PC5
	 * update文
	 */
	public int updateByUM(UserMaster recv_date, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");

		HttpSession session = request.getSession();
		UserMaster um = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();

			PreparedStatement st;
			st = con.prepareStatement(
					"UPDATE USER_MASTER SET NAME =?, PASSWORD=?, DEPT=?, ETC=?, HIRE_DATE=?, REGIST_DATE=?, REGIST_USER=? WHERE USER_ID=?");

			st.setString(1, recv_date.getName());
			st.setString(2, recv_date.getPassword());
			st.setString(3, recv_date.getDept());
			st.setString(4, recv_date.getEtc());
			st.setString(5, recv_date.getHireDate());
			st.setString(6, sdfymd.format(cl.getTime()));
			st.setString(7, um.getUserId());
			st.setString(8, recv_date.getUserId());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return line;
	}

	//切断
	/**
	 * @author TRAINING-PC5
	 * insert文、
	 *
	 */
	public int insertByUM(UserMaster recv_date, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		//USER_ID取得用カウンタ
		int count = 0;
		String USER_ID = "";
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");

		HttpSession session = request.getSession();
		UserMaster um = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			UserMaster userMaster = null;

			//USER_ID作成
			do {
				//注文番号のシーケンス値取得
				st = con.prepareStatement("SELECT USER_ID.NEXTVAL FROM DUAL");
				rs = st.executeQuery();
				while (rs.next()) {
					USER_ID = String.valueOf(rs.getInt("NEXTVAL"));
				}
				//注文番号を左ゼロ埋めで6桁にする処理
				do {
					if (USER_ID.length() < 6) {
						USER_ID = "0" + USER_ID;
					} else {
						break;
					}
				} while (true);
				//作成した注文番号の未使用確認
				userMaster = searchByID(USER_ID);
				if (userMaster == null) {
					//使用可能
					break;
				} else if (count >= 200000) {
					session.setAttribute("state", "使用可能なユーザーIDがありません。\\n処理を終了します｡");
					return line;
				}
				//ORDER_NO取得用カウンタ、カウントアップ
				count++;
			} while (true);

			st = con.prepareStatement("INSERT INTO USER_MASTER VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, USER_ID);
			st.setString(2, recv_date.getName());
			st.setString(3, recv_date.getPassword());
			st.setString(4, recv_date.getDept());
			st.setString(5, recv_date.getEtc());
			st.setString(6, recv_date.getHireDate());
			st.setString(7, sdfymd.format(cl.getTime()));
			st.setString(8, um.getUserId());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
}