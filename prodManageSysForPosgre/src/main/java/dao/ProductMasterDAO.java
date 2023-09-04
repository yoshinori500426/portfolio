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

import bean.G_Arrival;
import bean.G_EntryExitInfo;
import bean.G_Order;
import bean.G_ProductMaster;
import bean.G_PurchaseOrder;
import bean.G_Shipping;
import bean.ProductMaster;
import bean.UserMaster;

public class ProductMasterDAO extends DAO {
//	public ProductMaster searchByProNoS(String productNo) throws Exception {
//	ProductMaster ProductMaster = null;
//
//	Connection con = getConnection();
//
//	PreparedStatement st;
//	st = con.prepareStatement("SELECT * FROM PRODUCT_MASTER WHERE PRODUCT_NO=? ");
//	st.setString(1, productNo);
//	ResultSet rs = st.executeQuery();
//
//	while (rs.next()) {
//		ProductMaster = new ProductMaster();
//		ProductMaster.setProductNo(rs.getString("product_No"));
//		ProductMaster.setProductName(rs.getString("product_Name"));
//		ProductMaster.setSupplierNo(rs.getString("supplier_No"));
//		ProductMaster.setUnitPrice(rs.getInt("unit_Price"));
//		ProductMaster.setSellingPrice(rs.getInt("selling_Price"));
//		ProductMaster.setLeadTime(rs.getInt("leadtime"));
//		ProductMaster.setLot(rs.getInt("lot"));
//		ProductMaster.setLocation(rs.getString("location"));
//		ProductMaster.setEtc(rs.getString("etc"));
//		ProductMaster.setRegistDate(rs.getString("regist_Date"));
//		ProductMaster.setRegistUser(rs.getString("regist_User"));
//	}
//	st.close();
//	con.close();
//	return ProductMaster;
//}

//public ProductMaster searchBySireNo(String No) throws Exception {
//	ProductMaster ProductMaster = null;
//
//	Connection con = getConnection();
//
//	PreparedStatement st;
//	st = con.prepareStatement("SELECT * FROM PRODUCT_MASTER WHERE PRODUCT_NO  = ?");
//	st.setString(1, No);
//	ResultSet rs = st.executeQuery();
//
//	while (rs.next()) {
//		ProductMaster = new ProductMaster();
//		ProductMaster.setProductNo(rs.getString("PRODUCT_NO"));
//		ProductMaster.setProductName(rs.getString("PRODUCT_NAME"));
//		ProductMaster.setSupplierNo(rs.getString("SUPPLIER_NO"));
//		ProductMaster.setUnitPrice(rs.getInt("UNIT_PRICE"));
//		ProductMaster.setSellingPrice(rs.getInt("SELLING_PRICE"));
//		ProductMaster.setLeadTime(rs.getInt("LEADTIME"));
//		ProductMaster.setLot(rs.getInt("LOT"));
//		ProductMaster.setLocation(rs.getString("LOCATION"));
//		ProductMaster.setBaseStock(rs.getInt("BASESTOCK"));
//		ProductMaster.setEtc(rs.getString("ETC"));
//	}
//	st.close();
//	con.close();
//	return ProductMaster;
//}

	/**
	 * 品番に合致するレコード情報を取得するメソッド
	 * 
	 * @param String productNo
	 * @return 該当レコードあり:ProductMasterクラスのインスタンス 無:null
	 */
	public ProductMaster searchByProNo(String productNo) {
		ProductMaster ProductMaster = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM PRODUCT_MASTER WHERE PRODUCT_NO=?");
			st.setString(1, productNo);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ProductMaster = new ProductMaster();
				ProductMaster.setProductNo(rs.getString("PRODUCT_NO"));
				ProductMaster.setProductName(rs.getString("PRODUCT_NAME"));
				ProductMaster.setSupplierNo(rs.getString("SUPPLIER_NO"));
				ProductMaster.setUnitPrice(rs.getInt("UNIT_PRICE"));
				ProductMaster.setSellingPrice(rs.getInt("SELLING_PRICE"));
				ProductMaster.setLeadTime(rs.getInt("LEADTIME"));
				ProductMaster.setLot(rs.getInt("LOT"));
				ProductMaster.setLocation(rs.getString("LOCATION"));
				ProductMaster.setBaseStock(Integer.parseInt(rs.getString("BASESTOCK")));
				ProductMaster.setEtc(rs.getString("ETC"));
				ProductMaster.setRegistDate(rs.getString("REGIST_DATE"));
				ProductMaster.setRegistUser(rs.getString("REGIST_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return ProductMaster;
	}
	
	/**
	 * 品番に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_ProductMasterクラスのインスタンス
	 * @return 該当レコードあり:ProductMasterクラスのインスタンス 無:null
	 */
	public ProductMaster searchByProNo(G_ProductMaster G_ProductMaster) {
		return searchByProNo(G_ProductMaster.getProductNo());
	}

	/**
	 * 品番に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_PurchaseOrderクラスのインスタンス
	 * @return 該当レコードあり:ProductMasterクラスのインスタンス 無:null
	 */
	public ProductMaster searchByProNo(G_PurchaseOrder G_PurchaseOrder) {
		return searchByProNo(G_PurchaseOrder.getProductNo());
	}
	
	/**
	 * 品番に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_Orderのインスタンス
	 * @return 該当レコードあり:ProductMasterクラスのインスタンス 無:null
	 */
	public ProductMaster searchByProNo(G_Order G_Order) {
		return searchByProNo(G_Order.getProductNo());
	}
	
	/**
	 * 品番に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_Shippingのインスタンス
	 * @return 該当レコードあり:ProductMasterクラスのインスタンス 無:null
	 */
	public ProductMaster searchByProNo(G_Shipping G_Shipping) {
		return searchByProNo(G_Shipping.getProductNo());
	}
	
	/**
	 * 品番に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_Supplierのインスタンス
	 * @return 該当レコードあり:ProductMasterクラスのインスタンス 無:null
	 */
	public ProductMaster searchByProNo(G_Arrival G_Arrival) {
		return searchByProNo(G_Arrival.getProductNo());
	}
	
	/**
	 * 品番に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_EntryExitInfoのインスタンス
	 * @return 該当レコードあり:ProductMasterクラスのインスタンス 無:null
	 */
	public ProductMaster searchByProNo(G_EntryExitInfo G_EntryExitInfo) {
		return searchByProNo(G_EntryExitInfo.getProductNo());
	}

	/**
	 * ProductMasterテーブル取得メソッド(検索条件なし) →PRODUCT_NOリスト取得用メソッド
	 *
	 * @param 引数無し
	 * @return List<ProductMaster> 「null：失敗」「null以外：成功」
	 */
	public List<ProductMaster> searchAll() {
		// 戻り値用の変数宣言
		List<ProductMaster> ProductMasterList = new ArrayList<>();
		ProductMaster ProductMaster = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM PRODUCT_MASTER ORDER BY PRODUCT_NO ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ProductMaster = new ProductMaster();
				ProductMaster.setProductNo(rs.getString("PRODUCT_NO"));
				ProductMaster.setProductName(rs.getString("PRODUCT_NAME"));
				ProductMaster.setSupplierNo(rs.getString("SUPPLIER_NO"));
				ProductMaster.setUnitPrice(rs.getInt("UNIT_PRICE"));
				ProductMaster.setSellingPrice(rs.getInt("SELLING_PRICE"));
				ProductMaster.setLeadTime(rs.getInt("LEADTIME"));
				ProductMaster.setLot(rs.getInt("LOT"));
				ProductMaster.setLocation(rs.getString("LOCATION"));
				ProductMaster.setBaseStock(Integer.parseInt(rs.getString("BASESTOCK")));
				ProductMaster.setEtc(rs.getString("ETC"));
				ProductMaster.setRegistDate(rs.getString("REGIST_DATE"));
				ProductMaster.setRegistUser(rs.getString("REGIST_USER"));
				ProductMasterList.add(ProductMaster);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return ProductMasterList;
	}

	/**
	 * 引数の商品情報を新規登録するメソッド
	 * 
	 * @param G_ProductMasterクラスのインスタンス
	 * @return 0:新規登録失敗 1:新規登録成功
	 */
	public int insert(G_ProductMaster G_ProductMaster, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// PRODUCT_NO取得用カウンタ
		int count = 0;
		String PRODUCT_NO = "";
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
			ProductMaster ProductMaster = null;
			// PRODUCT_NO作成
			do {
				// 注文番号のシーケンス値取得
				st = con.prepareStatement("SELECT NEXTVAL('PRODUCT_NO')");
				rs = st.executeQuery();
				while (rs.next()) {
					PRODUCT_NO = String.valueOf(rs.getInt("NEXTVAL"));
				}
				// 注文番号を左ゼロ埋めで10桁にする処理
				do {
					if (PRODUCT_NO.length() < 10) {
						PRODUCT_NO = "0" + PRODUCT_NO;
					} else {
						break;
					}
				} while (true);
				// 作成した注文番号の未使用確認
				G_ProductMaster G_pm = new G_ProductMaster();
				G_pm.setProductNo(PRODUCT_NO);
				ProductMaster = searchByProNo(G_pm);
				if (ProductMaster == null) {
					// 使用可能
					break;
				} else if (count >= 12500000) {
					session.setAttribute("state", "使用可能な品番がありません。\\n処理を終了します｡");
					return line;
				}
				// ORDER_NO取得用カウンタ、カウントアップ
				count++;
			} while (true);
			// 新規登録処理
			st = con.prepareStatement("INSERT INTO PRODUCT_MASTER VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, PRODUCT_NO);
			st.setString(2, G_ProductMaster.getProductName());
			st.setString(3, G_ProductMaster.getSupplierNo());
			st.setInt(4, Integer.parseInt(G_ProductMaster.getUnitPrice()));
			st.setInt(5, Integer.parseInt(G_ProductMaster.getSellingPrice()));
			st.setInt(6, Integer.parseInt(G_ProductMaster.getLeadTime()));
			st.setInt(7, Integer.parseInt(G_ProductMaster.getLot()));
			st.setString(8, G_ProductMaster.getLocation());
			st.setInt(9, Integer.parseInt(G_ProductMaster.getBaseStock()));
			st.setString(10, G_ProductMaster.getEtc());
			st.setString(11, sdfymd.format(cl.getTime()));
			st.setString(12, user.getUserId());
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
	 * 引数の品番に合致するレコードを更新するメソッド
	 * 
	 * @param G_ProductMasterクラスのインスタンス
	 * @return 0:更新失敗 1:更新成功
	 */
	public int updateByPM(G_ProductMaster G_ProductMaster, HttpServletRequest request) {
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
			st = con.prepareStatement("UPDATE PRODUCT_MASTER SET PRODUCT_NAME=?, SUPPLIER_NO=?, UNIT_PRICE=?, SELLING_PRICE=?, LEADTIME=?,"
										+ " LOT=?, LOCATION=?, BASESTOCK=?, ETC=?, REGIST_DATE=?, REGIST_USER=? WHERE PRODUCT_NO=?");
			st.setString(1, G_ProductMaster.getProductName());
			st.setString(2, G_ProductMaster.getSupplierNo());
			st.setInt(3, Integer.parseInt(G_ProductMaster.getUnitPrice()));
			st.setInt(4, Integer.parseInt(G_ProductMaster.getSellingPrice()));
			st.setInt(5, Integer.parseInt(G_ProductMaster.getLeadTime()));
			st.setInt(6, Integer.parseInt(G_ProductMaster.getLot()));
			st.setString(7, G_ProductMaster.getLocation());
			st.setInt(8, Integer.parseInt(G_ProductMaster.getBaseStock()));
			st.setString(9, G_ProductMaster.getEtc());
			st.setString(10, sdfymd.format(cl.getTime()));
			st.setString(11, user.getUserId());
			st.setString(12, G_ProductMaster.getProductNo());
			line = st.executeUpdate();
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}
}
