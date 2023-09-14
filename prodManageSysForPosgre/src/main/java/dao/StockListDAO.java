package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.G_StockList;

public class StockListDAO extends DAO {
	/**
	 * VIEW ｢STOCK_LIST_ALL｣参照メソッド
	 * PurchaseOrderテーブル/OrderTableテーブルの情報(PRODUCT_NO)を元にしたテーブルの情報(PRODUCT_NO)毎のレコード参照メソッド
	 * 全PRODUCT_NOの検索を行うメソッド
	 *
	 * @return G_StockListビーンのList 「List.size()=0：失敗」「List.size()>0：成功」
	 */
	public List<G_StockList> searchAllByProductNo(G_StockList G_StockList) {
		// 在庫数格納用
		int stockQtyEstimate = G_StockList.getStockQty();
		// 戻り値用の変数(リスト型)を宣言
		List<G_StockList> G_StockListAllByProductNo = new ArrayList<>();
		// 使用するbeanの変数を宣言
		G_StockList G_StockListRecord = null;
		try {	
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM STOCK_LIST_ALL WHERE \"PRODUCT_NO\"=? ORDER BY \"DELIVERY_DATE\" ASC, \"ORDER_NO\" DESC");
			st.setString(1, G_StockList.getProductNo());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				stockQtyEstimate += (-1 * rs.getInt("PURCHASE_ORDER_QTY") + rs.getInt("ORDER_QTY"));
				G_StockListRecord = new G_StockList();
				G_StockListRecord.setProductNo(rs.getString("PRODUCT_NO"));
				G_StockListRecord.setDeliveryDate(rs.getString("DELIVERY_DATE"));
				G_StockListRecord.setOrderNo(rs.getString("ORDER_NO"));
				G_StockListRecord.setCustomerNo(rs.getString("CUSTOMER_NO"));
				G_StockListRecord.setCustomerName(rs.getString("CUSTOMER_NAME"));
				G_StockListRecord.setPurchaseOrderQty(rs.getInt("PURCHASE_ORDER_QTY"));
				G_StockListRecord.setSupplierNo(rs.getString("SUPPLIER_NO"));
				G_StockListRecord.setSupplierName(rs.getString("SUPPLIER_NAME"));
				G_StockListRecord.setOrderQty(rs.getInt("ORDER_QTY"));
				G_StockListRecord.setStockQtyEstimate(stockQtyEstimate);
				G_StockListAllByProductNo.add(G_StockListRecord);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return G_StockListAllByProductNo;
	}

}
