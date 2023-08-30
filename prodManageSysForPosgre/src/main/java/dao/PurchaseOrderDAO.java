package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import action.MainAction;
import bean.G_PurchaseOrder;
import bean.G_Shipping;
import bean.PurchaseOrder;
import bean.UserMaster;

//// ishijima追加 shippingAction
//public int updateAftShipByPoNo(G_PurchaseOrder G_PurchaseOrder) {
//	int result = 0;
//	try {
//		Connection con = getConnection();
//
//		// 出荷日「yyyy-mm-dd」を、「yyyy/mm/dd」に変換
//		String[] S = G_PurchaseOrder.getShipDate().split("-");
//		String ShipDate = S[0] + "/" + S[1] + "/" + S[2];
//
//		PreparedStatement st;
//		st = con.prepareStatement("update purchase_order set SHIP_DATE = ?, FIN_FLG=? where po_no=? ");
//		st.setString(1, ShipDate);
//		st.setString(2, "1");
//		st.setString(3, G_PurchaseOrder.getPoNo());
//
//		result = st.executeUpdate();
//		st.close();
//		con.close();
//
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return result;
//
//}
//
//public int insertPurchaseOrder2(Order recv_data) {
//// 戻り値用変数
//int result = 0;
////
//String PO_NO = "";
//
//// 登録日用にCalendarクラスのオブジェクトを生成する
//Calendar cl = Calendar.getInstance();
//// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//try {
//	Connection con = getConnection();
//	PreparedStatement st = null;
//	ResultSet rs = null;
//	do {
//
//		// 注文番号のシーケンス値を取得
//		st = con.prepareStatement("SELECT PO_NO.NEXTVAL FROM DUAL");
//		rs = st.executeQuery();
//		while (rs.next()) {
//			PO_NO = String.valueOf(rs.getInt("NEXTVAL"));
//
//		}
//		// 注文番号を左ゼロうめで５桁にする
//		do {
//			if (PO_NO.length() < 5) {
//				PO_NO = "0" + PO_NO;
//
//			} else {
//				break;
//			}
//		} while (true);
//		// 新しいPO_NOの作成
//		PO_NO = "PO_" + PO_NO;
//
//		// テーブルに同じ受注番号がないか確認
//		st = con.prepareStatement("SELECT PO_NO FROM PURCHASE_ORDER WHERE PO_NO=?");
//		st.setString(1, PO_NO);
//
//		rs = st.executeQuery();
//		String pon = null;
//		while (rs.next()) {
//			pon = rs.getString("PO_NO");
//		}
//		if (pon == null) {
//			// 使用可能
//			break;
//		}
//	} while (true);
//	st = con.prepareStatement("INSERT INTO PURCHASE_ORDER VALUES (?,?,?,?,?,null,0,?,?)");
//
//	st.setString(1, PO_NO);
//	st.setString(2, recv_data.getCustomer_No());
//	st.setString(3, recv_data.getProduct_No());
//	st.setInt(4, recv_data.getOrder_Qty());
//	st.setString(5, recv_data.getDelivery_Date().replace("-", "/"));
//
//	st.setString(6, sdf.format(cl.getTime()));
//	// koga 変更↓
//	st.setString(7, recv_data.getRegist_user());
//	// st.setString(8, recv_data.getRegistUser());
//
//	result = st.executeUpdate();
//	st.close();
//	con.close();
//
//} catch (Exception e) {
//	e.printStackTrace();
//}
//return result;
//}
//
//public List<PurchaseOrder> PurchaseOrderList(String productNo) throws Exception {
//	List<PurchaseOrder> list = new ArrayList<>();
//
//	Connection con = getConnection();
//	PreparedStatement st = con.prepareStatement(
//			"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,PO.ORDER_DATE,PO.SHIP_DATE,PO.DELIVERY_DATE,PO.ORDER_QTY,CM.CUSTOMER_NAME,CM.CUSTOMER_NO FROM PURCHASE_ORDER PO INNER JOIN CUSTOMER_MASTER CM ON "
//					+ "PO.CUSTOMER_NO = CM.CUSTOMER_NO INNER JOIN PRODUCT_MASTER PR ON "
//					+ "PO.PRODUCT_NO = PR.PRODUCT_NO WHERE PR.PRODUCT_NO = ?");
//
//	st.setString(1, productNo);
//	ResultSet rs = st.executeQuery();
//
//	while (rs.next()) {
//		PurchaseOrder PurchaseOrder = new PurchaseOrder();
//		PurchaseOrder.setProductNo(rs.getString("PRODUCT_NO"));
//		PurchaseOrder.setProductName(rs.getString("PRODUCT_NAME"));
//		PurchaseOrder.setOrderDate(rs.getString("ORDER_DATE"));
//		if ((rs.getString("SHIP_DATE")) == null || (rs.getString("SHIP_DATE")).isEmpty()) {
//			PurchaseOrder.setShipDate("");
//		} else {
//			PurchaseOrder.setShipDate(rs.getString("SHIP_DATE"));
//		}
//		PurchaseOrder.setDeliveryDate(rs.getString("DELIVERY_DATE"));
//		PurchaseOrder.setOrderQty(rs.getInt("ORDER_QTY"));
//		PurchaseOrder.setCustomerName(rs.getString("CUSTOMER_NAME"));
//		PurchaseOrder.setCustomerNo(rs.getString("CUSTOMER_NO"));
//		list.add(PurchaseOrder);
//	}
//	st.close();
//	con.close();
//
//	return list;
//}
//
//public List<PurchaseOrder> PurchaseOrderShipNotNull(String productNo) throws Exception {
//	List<PurchaseOrder> list = new ArrayList<>();
//
//	Connection con = getConnection();
//	PreparedStatement st = con.prepareStatement(
//			"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,PO.ORDER_DATE,PO.SHIP_DATE,PO.DELIVERY_DATE,PO.ORDER_QTY,CM.CUSTOMER_NAME,CM.CUSTOMER_NO FROM PURCHASE_ORDER PO INNER JOIN CUSTOMER_MASTER CM ON "
//					+ "PO.CUSTOMER_NO = CM.CUSTOMER_NO INNER JOIN PRODUCT_MASTER PR ON "
//					+ "PO.PRODUCT_NO = PR.PRODUCT_NO WHERE PR.PRODUCT_NO = ? AND PO.SHIP_DATE IS NOT NULL ORDER BY PR.PRODUCT_NO");
//
//	st.setString(1, productNo);
//	ResultSet rs = st.executeQuery();
//
//	while (rs.next()) {
//		PurchaseOrder PurchaseOrder = new PurchaseOrder();
//		PurchaseOrder.setProductNo(rs.getString("PRODUCT_NO"));
//		PurchaseOrder.setProductName(rs.getString("PRODUCT_NAME"));
//		PurchaseOrder.setOrderDate(rs.getString("ORDER_DATE"));
//		if ((rs.getString("SHIP_DATE")) == null || (rs.getString("SHIP_DATE")).isEmpty()) {
//			PurchaseOrder.setShipDate("");
//		} else {
//			PurchaseOrder.setShipDate(rs.getString("SHIP_DATE"));
//		}
//		PurchaseOrder.setDeliveryDate(rs.getString("DELIVERY_DATE"));
//		PurchaseOrder.setOrderQty(rs.getInt("ORDER_QTY"));
//		PurchaseOrder.setCustomerName(rs.getString("CUSTOMER_NAME"));
//		PurchaseOrder.setCustomerNo(rs.getString("CUSTOMER_NO"));
//		list.add(PurchaseOrder);
//	}
//	st.close();
//	con.close();
//
//	return list;
//}
//
//public List<PurchaseOrder> PurchaseOrderShipNot(String productNo) throws Exception {
//	List<PurchaseOrder> list = new ArrayList<>();
//
//	Connection con = getConnection();
//	PreparedStatement st = con.prepareStatement(
//			"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,PO.ORDER_DATE,PO.SHIP_DATE,PO.DELIVERY_DATE,PO.ORDER_QTY,CM.CUSTOMER_NAME,CM.CUSTOMER_NO FROM PURCHASE_ORDER PO INNER JOIN CUSTOMER_MASTER CM ON "
//					+ "PO.CUSTOMER_NO = CM.CUSTOMER_NO INNER JOIN PRODUCT_MASTER PR ON "
//					+ "PO.PRODUCT_NO = PR.PRODUCT_NO WHERE PR.PRODUCT_NO = ? AND PO.SHIP_DATE IS NULL ORDER BY PR.PRODUCT_NO");
//
//	st.setString(1, productNo);
//	ResultSet rs = st.executeQuery();
//
//	while (rs.next()) {
//		PurchaseOrder PurchaseOrder = new PurchaseOrder();
//		PurchaseOrder.setProductNo(rs.getString("PRODUCT_NO"));
//		PurchaseOrder.setProductName(rs.getString("PRODUCT_NAME"));
//		PurchaseOrder.setOrderDate(rs.getString("ORDER_DATE"));
//		if ((rs.getString("SHIP_DATE")) == null || (rs.getString("SHIP_DATE")).isEmpty()) {
//			PurchaseOrder.setShipDate("");
//		} else {
//			PurchaseOrder.setShipDate(rs.getString("SHIP_DATE"));
//		}
//		PurchaseOrder.setDeliveryDate(rs.getString("DELIVERY_DATE"));
//		PurchaseOrder.setOrderQty(rs.getInt("ORDER_QTY"));
//		PurchaseOrder.setCustomerName(rs.getString("CUSTOMER_NAME"));
//		PurchaseOrder.setCustomerNo(rs.getString("CUSTOMER_NO"));
//		list.add(PurchaseOrder);
//	}
//	st.close();
//	con.close();
//
//	return list;
//}
//
//public int updateByOrderNo(Supplier supp) {
//
//	Calendar cl = Calendar.getInstance();
//	// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//
//	int result = 0;
//	try {
//
//		DAO ds = new DAO();
//		Connection con = ds.getConnection();
//
//		con.setAutoCommit(false);
//
//		int line1 = 0;
//		int line2 = 0;
//
//		PreparedStatement st = con.prepareStatement(
//				"UPDATE ORDER_TABLE SET DUE_DATE =?, DUE_QTY=?, FIN_FLG=1, REGIST_USER=? WHERE ORDER_NO=?");
//
//		st.setString(1, sdf.format(cl.getTime()));
//		st.setInt(2, supp.getDueQty());
//		st.setString(3, supp.getRegistUser());
//		st.setString(4, supp.getOrderNo());
//
//		line1 = st.executeUpdate();
//
//		st = con.prepareStatement(
//				"UPDATE PURODUCT_STOCK SET STOCK_QTY= STOCK_QTY+ ?, T_NYUKO =T_NYUKO+ ?, UP_DATE=TO_CHAR(SYSDATE,'YYYY/MM/DD') WHERE PRODUCT_NO=? AND STOCK_INFO_DATE = TO_CHAR(SYSDATE,'YYYY/MM')");
////				"UPDATE PURODUCT_STOCK SET STOCK_INFO_DATE =?, STOCK_QTY=? T_NYUKO =?, UP_DATE=? WHERE PRODUCT_NO=? AND STOCK_INFO_DATE = TO_CHAR(SYSDATE,'YYYY/MM')");
//
//		st.setInt(1, supp.getDueQty());
//		st.setInt(2, supp.getDueQty());
//		st.setString(3, supp.getProductNo());
//
//		line2 = line1 + st.executeUpdate();
//
//		if (line2 == 2) {
//			con.commit();
//
//		} else {
//			con.rollback();
//
//		}
//
//		con.setAutoCommit(true);
//
//		st.close();
//		con.close();
//		result = line2;
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return result;
//}

public class PurchaseOrderDAO extends DAO {
	/**
	 * 受注番号に合致するレコード情報を取得するメソッド
	 * 
	 * @param G_PurchaseOrderクラスのインスタンス
	 * @return 該当レコードあり:PurchaseOrderクラスのインスタンス 無:null
	 */
	public PurchaseOrder searchByPoNo(G_PurchaseOrder G_PurchaseOrder) {
		PurchaseOrder PurchaseOrder = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM PURCHASE_ORDER WHERE PO_NO=?");
			st.setString(1, G_PurchaseOrder.getPoNo());
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
	 * @param G_Shippingクラスのインスタンス
	 * @return 該当レコードあり:PurchaseOrderクラスのインスタンス 無:null
	 */
	public PurchaseOrder searchByPoNo(G_Shipping G_Shipping) {
		PurchaseOrder PurchaseOrder = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM PURCHASE_ORDER WHERE PO_NO=?");
			st.setString(1, G_Shipping.getPoNo());
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
	 * 
	 * @param G_PurchaseOrderクラスのインスタンス
	 * @return 0:変更失敗 1:変更成功
	 */
	public int updateByPoNo(G_PurchaseOrder G_PurchaseOrder, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement(
					"UPDATE PURCHASE_ORDER SET CUSTOMER_NO=?, PRODUCT_NO=?, ORDER_QTY=?, DELIVERY_DATE=?, ORDER_DATE=?, REGIST_USER=? WHERE PO_NO=?");
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
	 * 
	 * @param G_PurchaseOrderクラスのインスタンス
	 * @return 0:変更失敗 1:変更成功
	 */
	public int updateByPoNo(G_Shipping G_Shipping, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement(
					"UPDATE PURCHASE_ORDER SET CUSTOMER_NO=?, PRODUCT_NO=?, ORDER_QTY=?, DELIVERY_DATE=?, SHIP_DATE=?, FIN_FLG=?, ORDER_DATE=?, REGIST_USER=? WHERE PO_NO=?");
			st.setString(1, G_Shipping.getCustomerNo());
			st.setString(2, G_Shipping.getProductNo());
			st.setInt(3, Integer.parseInt(G_Shipping.getOrderQty()));
			st.setString(4, new MainAction().dateChangeForDB(G_Shipping.getDeliveryDate()));
			st.setString(5, new MainAction().dateChangeForDB(G_Shipping.getShipDate()));
			st.setString(6, "1"); // FIN_FLG
			st.setString(7, new MainAction().dateChangeForDB(G_Shipping.getOrderDate()));
			st.setString(8, user.getUserId());
			st.setString(9, G_Shipping.getPoNo());

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
}