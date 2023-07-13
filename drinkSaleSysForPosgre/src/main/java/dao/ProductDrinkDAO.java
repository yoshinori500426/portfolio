package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bean.ProductDrink;
import bean.Users;

public class ProductDrinkDAO extends DAO {

	/**
	 * ProductDrinkテーブル参照メソッド
	 * @param ProductDrinkビーン
	 * @return ProductDrinkビーン 「null：失敗」「インスタンス有：成功」
	 */
	public ProductDrink searchProductDrinkByJanCode(ProductDrink pd) {
		ProductDrink productDrink = null;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("SELECT * FROM PRODUCT_DRINK WHERE JAN_CODE = ?");
			st.setString(1, pd.getJanCode());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				productDrink = new ProductDrink();
				productDrink.setJanCode(rs.getString("JAN_CODE"));
				productDrink.setName(rs.getString("NAME"));
				productDrink.setMaker(rs.getString("MAKER"));
				productDrink.setBox(rs.getString("BOX"));
				productDrink.setContents(rs.getInt("CONTENS"));
				productDrink.setDept(rs.getString("DEPT"));
				productDrink.setUnit(rs.getString("UNIT"));
				productDrink.setUnitPrice(rs.getInt("UNIT_PRICE"));
				productDrink.setEtc(rs.getString("ETC"));
				productDrink.setRegistDate(rs.getString("REGIST_DATE"));
				productDrink.setRegistUser(rs.getString("REGIST_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return productDrink;
	}

	/**
	 * ProductDrinkテーブル登録メソッド
	 * @param ProductDrinkビーン、Usersビーン(=セッションに属性値として登録されているログイン者を格納したビーン)
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertToProductDrink(ProductDrink pd, Users users) {
		int line = 0;
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Connection con = getConnection();
			PreparedStatement st = con
					.prepareStatement("INSERT INTO PRODUCT_DRINK VALUES(? ,? ,? ,'NULL' ,? ,? ,? ,? ,'NULL' ,? ,? )");
			st.setString(1, pd.getJanCode());
			st.setString(2, pd.getName());
			st.setString(3, pd.getMaker());
			st.setInt(4, pd.getContents());
			st.setString(5, pd.getDept());
			st.setString(6, pd.getUnit());
			st.setInt(7, pd.getUnitPrice());
			st.setString(8, sdf.format(cl.getTime()));
			st.setString(9, users.getUserID());

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
	 * ProductDrinkテーブル更新メソッド
	 * @param ProductDrinkビーン、Usersビーン(=セッションに属性値として登録されているログイン者を格納したビーン)
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int updateToProductDrink(ProductDrink pd, Users users) {
		int line = 0;
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement(
					"UPDATE PRODUCT_DRINK SET NAME = ?, MAKER = ?, CONTENS = ?, DEPT = ?, UNIT = ?, UNIT_PRICE = ?, REGIST_DATE = ?, REGIST_USER = ?  WHERE JAN_CODE = ?");
			st.setString(1, pd.getName());
			st.setString(2, pd.getMaker());
			st.setInt(3, pd.getContents());
			st.setString(4, pd.getDept());
			st.setString(5, pd.getUnit());
			st.setInt(6, pd.getUnitPrice());
			st.setString(7, sdf.format(cl.getTime()));
			st.setString(8, users.getUserID());
			st.setString(9, pd.getJanCode());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
}
