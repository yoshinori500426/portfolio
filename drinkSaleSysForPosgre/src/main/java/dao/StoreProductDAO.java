package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.StoreProduct;
import bean.StoreProductList;

public class StoreProductDAO extends DAO {
	/**
	 * StoreProductテーブル参照メソッド
	 *
	 * @param StoreProductビーン
	 * @return StoreProductビーン 「null：失敗」「インスタンス有：成功」
	 */
	public StoreProduct searchStoreProductByJanCode(StoreProduct storeproduct) {
		StoreProduct sp = null;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("SELECT * FROM STORE_PRODUCT WHERE JAN_CODE = ?");
			st.setString(1, storeproduct.getJanCode());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				sp = new StoreProduct();
				sp.setJanCode(rs.getString("JAN_CODE"));
				sp.setStockPCS(rs.getInt("STOCK_PCS"));
				sp.setTnyukosu(rs.getInt("TNYUKOSU"));
				sp.setTsyukosu(rs.getInt("TSYUKOSU"));
				sp.setEtc(rs.getString("ETC"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return sp;
	}

	/**
	 * StoreProductテーブル全レコード取得メソッド
	 *
	 * @param StoreProductビーン
	 * @return StoreProductビーン 「null：失敗/レコード無し」「インスタンス有：成功」
	 */
	public List<StoreProductList> storeProductAllRecords() {
		// 戻り値用の変数(リスト型)を宣言
		List<StoreProductList> storeProductLists = new ArrayList<>();

		try {
			Connection con = getConnection();

			//My SQL
//			PreparedStatement st = con.prepareStatement("SELECT "
//					+ "SP.JAN_CODE, "
//					+ "SP.STOCK_PCS, "
//					+ "PD.NAME, "
//					+ "PD.MAKER, "
//					+ "GR.UNIT_PRICE "
//				+ "FROM STORE_PRODUCT AS SP "
//					+ "INNER JOIN PRODUCT_DRINK AS PD "
//					+ "ON SP.JAN_CODE = PD.JAN_CODE "
//					+ "INNER JOIN GOODS_RECEIPT AS GR "
//					+ "ON SP.JAN_CODE = GR.JAN_CODE "
//				+ "ORDER BY JAN_CODE ASC");

			//ORACLE
			PreparedStatement st = con.prepareStatement("SELECT "
															+ "SP.JAN_CODE, "
															+ "SP.STOCK_PCS, "
															+ "PD.NAME, "
															+ "PD.MAKER, "
															+ "GR.UNIT_PRICE "
														+ "FROM STORE_PRODUCT SP "
															+ "INNER JOIN PRODUCT_DRINK PD "
															+ "ON SP.JAN_CODE = PD.JAN_CODE "
															+ "INNER JOIN GOODS_RECEIPT GR "
															+ "ON SP.JAN_CODE = GR.JAN_CODE "
														+ "ORDER BY JAN_CODE ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				StoreProductList storeProductList = new StoreProductList();
				storeProductList.setJanCode(rs.getString("JAN_CODE"));
				storeProductList.setStockPCS(rs.getInt("STOCK_PCS"));
				storeProductList.setName(rs.getString("NAME"));
				storeProductList.setMaker(rs.getString("MAKER"));
				storeProductList.setPrice(rs.getInt("UNIT_PRICE"));
				storeProductLists.add(storeProductList);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return storeProductLists;
	}

	/**
	 * StoreProductテーブル登録メソッド
	 *
	 * @param ProductDrinkビーン、Usersビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertToStoreProduct(StoreProduct sp) {
		int line = 0;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("INSERT INTO STORE_PRODUCT VALUES(? ,? ,? ,? ,'NULL' )");
			st.setString(1, sp.getJanCode());
			st.setInt(2, sp.getTnyukosu() - sp.getTsyukosu());
			st.setInt(3, sp.getTnyukosu());
			st.setInt(4, sp.getTsyukosu());

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
	 * StoreProductテーブル更新メソッド
	 *
	 * @param StoreProductビーン、「sp：画面情報」「storeproduct：メソッド「searchStoreProductByJanCode()」の戻り値」
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int updateToStoreProduct(StoreProduct sp, StoreProduct storeproduct) {
		int line = 0;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement(
					"UPDATE STORE_PRODUCT SET STOCK_PCS = ?, TNYUKOSU = ?, TSYUKOSU = ?  WHERE JAN_CODE = ?");
			st.setInt(1,
					(storeproduct.getTnyukosu() + sp.getTnyukosu()) - (storeproduct.getTsyukosu() + sp.getTsyukosu()));
			st.setInt(2, storeproduct.getTnyukosu() + sp.getTnyukosu());
			st.setInt(3, storeproduct.getTsyukosu() + sp.getTsyukosu());
			st.setString(4, storeproduct.getJanCode());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

}
