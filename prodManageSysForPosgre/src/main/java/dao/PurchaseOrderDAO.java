package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import action.MainAction;
import bean.G_PurchaseOrder;
import bean.G_Shipping;
import bean.PurchaseOrder;
import bean.UserMaster;

public class PurchaseOrderDAO extends DAO {
	/**
	 * 受注番号に合致するレコード情報を取得するメソッド
	 * 
	 * @param String poNo
	 * @return 該当レコードあり:PurchaseOrderクラスのインスタンス 無:null
	 */
	public PurchaseOrder searchByPoNo(String poNo) {
		PurchaseOrder PurchaseOrder = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM PURCHASE_ORDER WHERE PO_NO=?");
			st.setString(1, poNo);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				PurchaseOrder = new PurchaseOrder();
				PurchaseOrder.setPoNo(rs.getString("PO_NO"));
				PurchaseOrder.setCustomerNo(rs.getString("CUSTOMER_NO"));
				PurchaseOrder.setProductNo(rs.getString("PRODUCT_NO"));
				PurchaseOrder.setOrderQty(rs.getInt("ORDER_QTY"));
				PurchaseOrder.setDeliveryDate(new MainAction().dateChangeForHTML(rs.getString("DELIVERY_DATE")));
				PurchaseOrder.setShipDate(new MainAction().dateChangeForHTML(rs.getString("SHIP_DATE")));
				PurchaseOrder.setFinFlg(rs.getString("FIN_FLG"));
				PurchaseOrder.setOrderDate(new MainAction().dateChangeForHTML(rs.getString("ORDER_DATE")));
				PurchaseOrder.setRegistUser(rs.getString("REGIST_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PurchaseOrder;
	}

	/**
	 * 受注番号に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_PurchaseOrderクラスのインスタンス
	 * @return 該当レコードあり:PurchaseOrderクラスのインスタンス 無:null
	 */
	public PurchaseOrder searchByPoNo(G_PurchaseOrder G_PurchaseOrder) {
		return searchByPoNo(G_PurchaseOrder.getPoNo());
	}

	/**
	 * 受注番号に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_Shippingクラスのインスタンス
	 * @return 該当レコードあり:PurchaseOrderクラスのインスタンス 無:null
	 */
	public PurchaseOrder searchByPoNo(G_Shipping G_Shipping) {
		return searchByPoNo(G_Shipping.getPoNo());
	}

	/**
	 * PurchaseOrderテーブル取得メソッド(｢FIN_FLG!="1"｣のリスト)
	 *  →PO_NOリスト取得用メソッド
	 *
	 * @param 引数無し
	 * @return List<PurchaseOrder> 「null：失敗」「null以外：成功」
	 */
	public List<PurchaseOrder> searchAll() {
		// 戻り値用の変数宣言
		List<PurchaseOrder> PurchaseOrderList = new ArrayList<>();
		PurchaseOrder PurchaseOrder = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM PURCHASE_ORDER ORDER BY PO_NO ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (!rs.getString("FIN_FLG").equals("1")) {
					PurchaseOrder = new PurchaseOrder();
					PurchaseOrder.setPoNo(rs.getString("PO_NO"));
					PurchaseOrder.setCustomerNo(rs.getString("CUSTOMER_NO"));
					PurchaseOrder.setProductNo(rs.getString("PRODUCT_NO"));
					PurchaseOrder.setOrderQty(rs.getInt("ORDER_QTY"));
					PurchaseOrder.setDeliveryDate(new MainAction().dateChangeForHTML(rs.getString("DELIVERY_DATE")));
					PurchaseOrder.setShipDate(new MainAction().dateChangeForHTML(rs.getString("SHIP_DATE")));
					PurchaseOrder.setFinFlg(rs.getString("FIN_FLG"));
					PurchaseOrder.setOrderDate(new MainAction().dateChangeForHTML(rs.getString("ORDER_DATE")));
					PurchaseOrder.setRegistUser(rs.getString("REGIST_USER"));
					PurchaseOrderList.add(PurchaseOrder);
				}
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PurchaseOrderList;
	}

	/**
	 * PurchaseOrderList作成の元レコード取得メソッド(検索条件なし)
	 *
	 * @param 引数無し
	 * @return List<PurchaseOrder> 「null：失敗」「null以外：成功」
	 */
	public List<PurchaseOrder> searchAllWithProductNameAndCustomerName() {
		// 戻り値用の変数宣言
		List<PurchaseOrder> PurchaseOrderListWithProductNameAndSupplierName = new ArrayList<>();
		PurchaseOrder PurchaseOrder = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT PO.*, PM.*, CM.* FROM PURCHASE_ORDER AS PO INNER JOIN PRODUCT_MASTER AS PM ON PO.PRODUCT_NO=PM.PRODUCT_NO INNER JOIN CUSTOMER_MASTER AS CM ON PO.CUSTOMER_NO=CM.CUSTOMER_NO ORDER BY PO_NO ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				PurchaseOrder = new PurchaseOrder();
				PurchaseOrder.setPoNo(rs.getString("PO_NO"));
				PurchaseOrder.setCustomerNo(rs.getString("CUSTOMER_NO"));
				PurchaseOrder.setProductNo(rs.getString("PRODUCT_NO"));
				PurchaseOrder.setOrderQty(rs.getInt("ORDER_QTY"));
				PurchaseOrder.setDeliveryDate(rs.getString("DELIVERY_DATE"));
				PurchaseOrder.setShipDate(rs.getString("SHIP_DATE"));
				PurchaseOrder.setFinFlg(rs.getString("FIN_FLG"));
				PurchaseOrder.setRegistUser(rs.getString("REGIST_USER"));
				PurchaseOrder.setOrderDate(rs.getString("ORDER_DATE"));
				PurchaseOrder.setProductName(rs.getString("PRODUCT_NAME"));
				PurchaseOrder.setCustomerName(rs.getString("CUSTOMER_NAME"));
				PurchaseOrderListWithProductNameAndSupplierName.add(PurchaseOrder);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PurchaseOrderListWithProductNameAndSupplierName;
	}

	/**
	 * PurchaseOrderテーブル取得メソッド(｢FIN_FLG!="0"｣のリスト)
	 *  →PO_NOリスト取得用メソッド
	 *
	 * @param 引数無し
	 * @return List<PurchaseOrder> 「null：失敗」「null以外：成功」
	 */
	public List<PurchaseOrder> searchAllFinFlg0() {
		// 戻り値用の変数宣言
		List<PurchaseOrder> PurchaseOrderList = new ArrayList<>();
		PurchaseOrder PurchaseOrder = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM PURCHASE_ORDER ORDER BY PO_NO ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (!rs.getString("FIN_FLG").equals("0")) {
					PurchaseOrder = new PurchaseOrder();
					PurchaseOrder.setPoNo(rs.getString("PO_NO"));
					PurchaseOrder.setCustomerNo(rs.getString("CUSTOMER_NO"));
					PurchaseOrder.setProductNo(rs.getString("PRODUCT_NO"));
					PurchaseOrder.setOrderQty(rs.getInt("ORDER_QTY"));
					PurchaseOrder.setDeliveryDate(new MainAction().dateChangeForHTML(rs.getString("DELIVERY_DATE")));
					PurchaseOrder.setShipDate(new MainAction().dateChangeForHTML(rs.getString("SHIP_DATE")));
					PurchaseOrder.setFinFlg(rs.getString("FIN_FLG"));
					PurchaseOrder.setOrderDate(new MainAction().dateChangeForHTML(rs.getString("ORDER_DATE")));
					PurchaseOrder.setRegistUser(rs.getString("REGIST_USER"));
					PurchaseOrderList.add(PurchaseOrder);
				}
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PurchaseOrderList;
	}

	/**
	 * PurchaseOrderテーブル取得メソッド(｢FIN_FLG｣の制限なしリスト)
	 *  →PO_NOリスト取得用メソッド
	 *
	 * @param 引数無し
	 * @return List<PurchaseOrder> 「null：失敗」「null以外：成功」
	 */
	public List<PurchaseOrder> searchAllAddFinFlg1() {
		// 戻り値用の変数宣言
		List<PurchaseOrder> PurchaseOrderList = new ArrayList<>();
		PurchaseOrder PurchaseOrder = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM PURCHASE_ORDER ORDER BY PO_NO ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				PurchaseOrder = new PurchaseOrder();
				PurchaseOrder.setPoNo(rs.getString("PO_NO"));
				PurchaseOrder.setCustomerNo(rs.getString("CUSTOMER_NO"));
				PurchaseOrder.setProductNo(rs.getString("PRODUCT_NO"));
				PurchaseOrder.setOrderQty(rs.getInt("ORDER_QTY"));
				PurchaseOrder.setDeliveryDate(new MainAction().dateChangeForHTML(rs.getString("DELIVERY_DATE")));
				PurchaseOrder.setShipDate(new MainAction().dateChangeForHTML(rs.getString("SHIP_DATE")));
				PurchaseOrder.setFinFlg(rs.getString("FIN_FLG"));
				PurchaseOrder.setOrderDate(new MainAction().dateChangeForHTML(rs.getString("ORDER_DATE")));
				PurchaseOrder.setRegistUser(rs.getString("REGIST_USER"));
				PurchaseOrderList.add(PurchaseOrder);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PurchaseOrderList;
	}

	/**
	 * 引数の受注情報を新規登録するメソッド
	 * 
	 * @param G_PurchaseOrderクラスのインスタンス
	 * @return 0:新規登録失敗 1:新規登録成功
	 */
	public int insert(G_PurchaseOrder G_PurchaseOrder, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// PO_NO取得用カウンタ
		int count = 0;
		String PO_NO = "";
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			PurchaseOrder PurchaseOrder = null;
			// PO_NO作成
			do {
				// 受注番号のシーケンス値取得
				st = con.prepareStatement("SELECT NEXTVAL('PO_NO')");
				rs = st.executeQuery();
				while (rs.next()) {
					PO_NO = String.valueOf(rs.getInt("NEXTVAL"));
				}
				// 受注番号を左ゼロ埋めで5桁にする処理
				do {
					if (PO_NO.length() < 5) {
						PO_NO = "0" + PO_NO;
					} else {
						break;
					}
				} while (true);
				PO_NO = "PO-" + PO_NO;
				// 作成した受注番号の未使用確認
				G_PurchaseOrder G_po = new G_PurchaseOrder();
				G_po.setPoNo(PO_NO);
				PurchaseOrder = searchByPoNo(G_po);
				if (PurchaseOrder == null) {
					// 使用可能
					break;
				} else if (count >= 100000) {
					session.setAttribute("state", "使用可能な受注番号がありません。\\n処理を終了します｡");
					return line;
				}
				// PO_NO取得用カウンタ、カウントアップ
				count++;
			} while (true);
			// 新規登録処理
			st = con.prepareStatement("INSERT INTO PURCHASE_ORDER VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, PO_NO);
			st.setString(2, G_PurchaseOrder.getCustomerNo());
			st.setString(3, G_PurchaseOrder.getProductNo());
			st.setInt(4, Integer.parseInt(G_PurchaseOrder.getOrderQty()));
			st.setString(5, new MainAction().dateChangeForDB(G_PurchaseOrder.getDeliveryDate()));
			st.setString(6, null); // SHIP_DATE
			st.setString(7, "0"); // FIN_FLG
			st.setString(8, new MainAction().dateChangeForDB(G_PurchaseOrder.getOrderDate()));
			st.setString(9, user.getUserId());
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
	 * 受注番号に合致するレコード情報を変更するメソッド
	 *  →受注画面用
	 * 
	 * @param G_PurchaseOrderクラスのインスタンス
	 * @return 0:変更失敗 1:変更成功
	 */
	public int updateByPoNo(G_PurchaseOrder G_PurchaseOrder, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("UPDATE PURCHASE_ORDER SET CUSTOMER_NO=?, PRODUCT_NO=?, ORDER_QTY=?, DELIVERY_DATE=?, ORDER_DATE=?, REGIST_USER=? WHERE PO_NO=?");
			st.setString(1, G_PurchaseOrder.getCustomerNo());
			st.setString(2, G_PurchaseOrder.getProductNo());
			st.setInt(3, Integer.parseInt(G_PurchaseOrder.getOrderQty()));
			st.setString(4, new MainAction().dateChangeForDB(G_PurchaseOrder.getDeliveryDate()));
			st.setString(5, new MainAction().dateChangeForDB(G_PurchaseOrder.getOrderDate()));
			st.setString(6, user.getUserId());
			st.setString(7, G_PurchaseOrder.getPoNo());
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
	 * 受注番号に合致するレコード情報を変更するメソッド
	 *  →出荷画面用
	 * 
	 * @param G_Shipping
	 * @return 0:変更失敗 1:変更成功
	 */
	public int updateForShipByPoNo(G_Shipping G_Shipping, String finFlg, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("UPDATE PURCHASE_ORDER SET SHIP_DATE=?, FIN_FLG=?, REGIST_USER=? WHERE PO_NO=?");
			if (finFlg.equals("0")) {
				st.setString(1, "");
			} else if (finFlg.equals("1")) {
				st.setString(1, new MainAction().dateChangeForDB(G_Shipping.getShipDate()));
			}
			st.setString(2, finFlg); // FIN_FLG
			st.setString(3, user.getUserId());
			st.setString(4, G_Shipping.getPoNo());
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
	 * 受注番号に合致するレコードを削除するメソッド
	 * 
	 * @param G_PurchaseOrderクラスのインスタンス
	 * @return 0:削除失敗 1:削除成功
	 */
	public int delete(G_PurchaseOrder G_PurchaseOrder, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("DELETE FROM PURCHASE_ORDER WHERE PO_NO=?");
			st.setString(1, G_PurchaseOrder.getPoNo());
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
	 * PurchaseOrderテーブル取得メソッド(｢FIN_FLG｣の制限なしリスト)
	 *  →動作確認用メソッド(=portfolioとして成立させる為､テーブル情報を公開する事が目的)
	 *
	 * @param 引数無し
	 * @return List<PurchaseOrder> 「null：失敗」「null以外：成功」
	 */
	public List<PurchaseOrder> searchAllForPortfolio() {
		// 戻り値用の変数宣言
		List<PurchaseOrder> PurchaseOrderList = new ArrayList<>();
		PurchaseOrder PurchaseOrder = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM PURCHASE_ORDER ORDER BY PO_NO ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				PurchaseOrder = new PurchaseOrder();
				PurchaseOrder.setPoNo(rs.getString("PO_NO"));
				PurchaseOrder.setCustomerNo(rs.getString("CUSTOMER_NO"));
				PurchaseOrder.setProductNo(rs.getString("PRODUCT_NO"));
				PurchaseOrder.setOrderQty(rs.getInt("ORDER_QTY"));
				PurchaseOrder.setDeliveryDate(rs.getString("DELIVERY_DATE"));
				PurchaseOrder.setShipDate(rs.getString("SHIP_DATE"));
				PurchaseOrder.setFinFlg(rs.getString("FIN_FLG"));
				PurchaseOrder.setOrderDate(rs.getString("ORDER_DATE"));
				PurchaseOrder.setRegistUser(rs.getString("REGIST_USER"));
				PurchaseOrderList.add(PurchaseOrder);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PurchaseOrderList;
	}
}