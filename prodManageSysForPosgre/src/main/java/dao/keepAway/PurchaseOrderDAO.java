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

import bean.Order;
import bean.PurchaseOrder;
import bean.Supplier;
import bean.UserMaster;

public class PurchaseOrderDAO extends DAO {

	public PurchaseOrder searchBypoNo(String poNo, String orderDate, String deliverryDate,
			String shipDate, int orderQty, String customerNo)
			throws Exception {
		PurchaseOrder purchaseorder = null;

		Connection con = getConnection();

		PreparedStatement st;
		st = con.prepareStatement(
				"select * from purchase_order where po_no=? and order_date=? and delivery_date=? "
						+ "and ship_date=? and order_qty=? and customer_no=?");

		st.setString(1, poNo);
		st.setString(2, orderDate);
		st.setString(3, deliverryDate);
		st.setString(4, shipDate);
		st.setInt(5, orderQty);
		st.setString(6, customerNo);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			purchaseorder = new PurchaseOrder();
			purchaseorder.setPoNo(rs.getString("poNo"));
			purchaseorder.setOrderDate(rs.getString("orderDate"));
			purchaseorder.setDeliveryDate(rs.getString("deliverryDate"));
			purchaseorder.setShipDate(rs.getString("shipDate"));
			purchaseorder.setOrderQty(rs.getInt("orderQty"));
			purchaseorder.setCustomerNo(rs.getString("customerNo"));
		}
		st.close();
		con.close();
		return purchaseorder;
	}

	//ishijima 追加
	public PurchaseOrder searchByPorder(String poNo)

			throws Exception {
		PurchaseOrder purchaseorder = null;

		Connection con = getConnection();

		PreparedStatement st;
		st = con.prepareStatement(
				"select * from purchase_order where po_no=?");

		st.setString(1, poNo);

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			purchaseorder = new PurchaseOrder();
			purchaseorder.setPoNo(rs.getString("po_No"));
			purchaseorder.setCustomerNo(rs.getString("customer_No"));
			purchaseorder.setProductNo(rs.getString("product_No"));
			purchaseorder.setOrderQty(rs.getInt("order_Qty"));
			purchaseorder.setDeliveryDate(rs.getString("delivery_Date"));
			purchaseorder.setShipDate(rs.getString("ship_Date"));
			purchaseorder.setFinFlg(rs.getString("fin_flg"));
			purchaseorder.setOrderDate(rs.getString("order_Date"));
			purchaseorder.setRegistUser(rs.getString("regist_user"));
		}
		st.close();
		con.close();
		return purchaseorder;
	}

	//ishijima追加　shippingAction
	public int updateAftShipByPoNo(PurchaseOrder recv_data) {
		int result = 0;
		try {
			Connection con = getConnection();

			//出荷日「yyyy-mm-dd」を、「yyyy/mm/dd」に変換
			String[] S = recv_data.getShipDate().split("-");
			String ShipDate = S[0] + "/" + S[1] + "/" + S[2];

			PreparedStatement st;
			st = con.prepareStatement(
					"update purchase_order set SHIP_DATE = ?, FIN_FLG=? where po_no=? ");
			st.setString(1, ShipDate);
			st.setString(2, "1");
			st.setString(3, recv_data.getPoNo());

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public int updateBypoNo(PurchaseOrder recv_data) {
		int result = 0;
		try {
			Connection con = getConnection();

			PreparedStatement st;
			st = con.prepareStatement(
					"update purchase_order set customer_no = ?, product_no=?,order_qty=?,delivery_date=? where po_no=? ");
			st.setString(1, recv_data.getCustomerNo());
			st.setString(2, recv_data.getProductNo());
			st.setInt(3, recv_data.getOrderQty());
			st.setString(4, recv_data.getDeliveryDate());
			st.setString(5, recv_data.getPoNo());

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public int deletePurchaseOrder(String poNo) {
		int result = 0;
		try {
			Connection con = getConnection();

			PreparedStatement st;
			st = con.prepareStatement(
					"delete from purchase_order where po_no=? ");
			st.setString(1, poNo);

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insertPurchaseOrder(PurchaseOrder recv_data, HttpServletRequest request) {
		int result = 0;
		String PO_NO = "";
		try {

			//登録日用にCalendarクラスのオブジェクトを生成する
			Calendar cl = Calendar.getInstance();
			//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			//koga追記　ログイン中のユーザー情報の取得
			HttpSession session = request.getSession();
			UserMaster um = (UserMaster) session.getAttribute("user");

			Connection con = getConnection();

			//koga追記注文番号のシーケンス値を取得
			PreparedStatement st = con.prepareStatement("SELECT PO_NO.NEXTVAL FROM DUAL");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				PO_NO = String.valueOf(rs.getInt("NEXTVAL"));

			}
			//古賀追記　注文番号を左ゼロ梅で５桁にする
			do {
				if (PO_NO.length() < 5) {
					PO_NO = "0" + PO_NO;

				} else {
					break;
				}
			} while (true);
			PO_NO = "PO-" + PO_NO;

			//  PreparedStatement st;
			//			st = con.prepareStatement(
			//					"insert into purchase_order values(po_no.nextval,?,?,?,?,?,?,?,?)");
			//st.setString(1, recv_data.getCustomerNo());
			//koga 追記　↓
			st = con.prepareStatement(
					"INSERT INTO PURCHASE_ORDER VALUES (?,?,?,?,?,?,?,?,?)");

			st.setString(1, PO_NO);
			st.setString(2, recv_data.getCustomerNo());
			st.setString(3, recv_data.getProductNo());
			st.setInt(4, recv_data.getOrderQty());
			st.setString(5, recv_data.getDeliveryDate());
			st.setString(6, recv_data.getShipDate());
			st.setString(7, recv_data.getFinFlg());
			st.setString(8, sdf.format(cl.getTime()));
			//koga 変更↓
			st.setString(9, um.getUserId());
			//st.setString(8, recv_data.getRegistUser());

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insertPurchaseOrder2(Order recv_data) {
		//戻り値用変数
		int result = 0;
		//
		String PO_NO = "";

		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			do {

				//注文番号のシーケンス値を取得
				st = con.prepareStatement("SELECT PO_NO.NEXTVAL FROM DUAL");
				rs = st.executeQuery();
				while (rs.next()) {
					PO_NO = String.valueOf(rs.getInt("NEXTVAL"));

				}
				//注文番号を左ゼロうめで５桁にする
				do {
					if (PO_NO.length() < 5) {
						PO_NO = "0" + PO_NO;

					} else {
						break;
					}
				} while (true);
				//新しいPO_NOの作成
				PO_NO = "PO_" + PO_NO;

				//テーブルに同じ受注番号がないか確認
				st = con.prepareStatement("SELECT PO_NO FROM PURCHASE_ORDER WHERE PO_NO=?");
				st.setString(1, PO_NO);

				rs = st.executeQuery();
				String pon = null;
				while (rs.next()) {
					pon = rs.getString("PO_NO");
				}
				if (pon == null) {
					//使用可能
					break;
				}
			} while (true);
			st = con.prepareStatement(
					"INSERT INTO PURCHASE_ORDER VALUES (?,?,?,?,?,null,0,?,?)");

			st.setString(1, PO_NO);
			st.setString(2, recv_data.getCustomer_No());
			st.setString(3, recv_data.getProduct_No());
			st.setInt(4, recv_data.getOrder_Qty());
			st.setString(5, recv_data.getDelivery_Date().replace("-", "/"));

			st.setString(6, sdf.format(cl.getTime()));
			//koga 変更↓
			st.setString(7, recv_data.getRegist_user());
			//st.setString(8, recv_data.getRegistUser());

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<PurchaseOrder> PurchaseOrderList(String productNo) throws Exception {
		List<PurchaseOrder> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,PO.ORDER_DATE,PO.SHIP_DATE,PO.DELIVERY_DATE,PO.ORDER_QTY,CM.CUSTOMER_NAME,CM.CUSTOMER_NO FROM PURCHASE_ORDER PO INNER JOIN CUSTOMER_MASTER CM ON "
						+
						"PO.CUSTOMER_NO = CM.CUSTOMER_NO INNER JOIN PRODUCT_MASTER PR ON " +
						"PO.PRODUCT_NO = PR.PRODUCT_NO WHERE PR.PRODUCT_NO = ?");

		st.setString(1, productNo);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			PurchaseOrder purchaseorder = new PurchaseOrder();
			purchaseorder.setProductNo(rs.getString("PRODUCT_NO"));
			purchaseorder.setProductName(rs.getString("PRODUCT_NAME"));
			purchaseorder.setOrderDate(rs.getString("ORDER_DATE"));
			if ((rs.getString("SHIP_DATE")) == null || (rs.getString("SHIP_DATE")).isEmpty()) {
				purchaseorder.setShipDate("");
			} else {
				purchaseorder.setShipDate(rs.getString("SHIP_DATE"));
			}
			purchaseorder.setDeliveryDate(rs.getString("DELIVERY_DATE"));
			purchaseorder.setOrderQty(rs.getInt("ORDER_QTY"));
			purchaseorder.setCustomerName(rs.getString("CUSTOMER_NAME"));
			purchaseorder.setCustomerNo(rs.getString("CUSTOMER_NO"));
			list.add(purchaseorder);
		}
		st.close();
		con.close();

		return list;
	}

	public List<PurchaseOrder> PurchaseOrderShipNotNull(String productNo) throws Exception {
		List<PurchaseOrder> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,PO.ORDER_DATE,PO.SHIP_DATE,PO.DELIVERY_DATE,PO.ORDER_QTY,CM.CUSTOMER_NAME,CM.CUSTOMER_NO FROM PURCHASE_ORDER PO INNER JOIN CUSTOMER_MASTER CM ON "
						+
						"PO.CUSTOMER_NO = CM.CUSTOMER_NO INNER JOIN PRODUCT_MASTER PR ON " +
						"PO.PRODUCT_NO = PR.PRODUCT_NO WHERE PR.PRODUCT_NO = ? AND PO.SHIP_DATE IS NOT NULL ORDER BY PR.PRODUCT_NO");

		st.setString(1, productNo);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			PurchaseOrder purchaseorder = new PurchaseOrder();
			purchaseorder.setProductNo(rs.getString("PRODUCT_NO"));
			purchaseorder.setProductName(rs.getString("PRODUCT_NAME"));
			purchaseorder.setOrderDate(rs.getString("ORDER_DATE"));
			if ((rs.getString("SHIP_DATE")) == null || (rs.getString("SHIP_DATE")).isEmpty()) {
				purchaseorder.setShipDate("");
			} else {
				purchaseorder.setShipDate(rs.getString("SHIP_DATE"));
			}
			purchaseorder.setDeliveryDate(rs.getString("DELIVERY_DATE"));
			purchaseorder.setOrderQty(rs.getInt("ORDER_QTY"));
			purchaseorder.setCustomerName(rs.getString("CUSTOMER_NAME"));
			purchaseorder.setCustomerNo(rs.getString("CUSTOMER_NO"));
			list.add(purchaseorder);
		}
		st.close();
		con.close();

		return list;
	}

	public List<PurchaseOrder> PurchaseOrderShipNot(String productNo) throws Exception {
		List<PurchaseOrder> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,PO.ORDER_DATE,PO.SHIP_DATE,PO.DELIVERY_DATE,PO.ORDER_QTY,CM.CUSTOMER_NAME,CM.CUSTOMER_NO FROM PURCHASE_ORDER PO INNER JOIN CUSTOMER_MASTER CM ON "
						+
						"PO.CUSTOMER_NO = CM.CUSTOMER_NO INNER JOIN PRODUCT_MASTER PR ON " +
						"PO.PRODUCT_NO = PR.PRODUCT_NO WHERE PR.PRODUCT_NO = ? AND PO.SHIP_DATE IS NULL ORDER BY PR.PRODUCT_NO");

		st.setString(1, productNo);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			PurchaseOrder purchaseorder = new PurchaseOrder();
			purchaseorder.setProductNo(rs.getString("PRODUCT_NO"));
			purchaseorder.setProductName(rs.getString("PRODUCT_NAME"));
			purchaseorder.setOrderDate(rs.getString("ORDER_DATE"));
			if ((rs.getString("SHIP_DATE")) == null || (rs.getString("SHIP_DATE")).isEmpty()) {
				purchaseorder.setShipDate("");
			} else {
				purchaseorder.setShipDate(rs.getString("SHIP_DATE"));
			}
			purchaseorder.setDeliveryDate(rs.getString("DELIVERY_DATE"));
			purchaseorder.setOrderQty(rs.getInt("ORDER_QTY"));
			purchaseorder.setCustomerName(rs.getString("CUSTOMER_NAME"));
			purchaseorder.setCustomerNo(rs.getString("CUSTOMER_NO"));
			list.add(purchaseorder);
		}
		st.close();
		con.close();

		return list;
	}

	public int updateByOrderNo(Supplier supp) {

		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		int result = 0;
		try {

			DAO ds = new DAO();
			Connection con = ds.getConnection();

			con.setAutoCommit(false);

			int line1 = 0;
			int line2 = 0;

			PreparedStatement st = con.prepareStatement(
					"UPDATE ORDER_TABLE SET DUE_DATE =?, DUE_QTY=?, FIN_FLG=1, REGIST_USER=? WHERE ORDER_NO=?");

			st.setString(1, sdf.format(cl.getTime()));
			st.setInt(2, supp.getDueQty());
			st.setString(3, supp.getRegistUser());
			st.setString(4, supp.getOrderNo());

			line1 = st.executeUpdate();


			st = con.prepareStatement(
		"UPDATE PURODUCT_STOCK SET STOCK_QTY= STOCK_QTY+ ?, T_NYUKO =T_NYUKO+ ?, UP_DATE=TO_CHAR(SYSDATE,'YYYY/MM/DD') WHERE PRODUCT_NO=? AND STOCK_INFO_DATE = TO_CHAR(SYSDATE,'YYYY/MM')");
//					"UPDATE PURODUCT_STOCK SET STOCK_INFO_DATE =?, STOCK_QTY=? T_NYUKO =?, UP_DATE=? WHERE PRODUCT_NO=? AND STOCK_INFO_DATE = TO_CHAR(SYSDATE,'YYYY/MM')");

			st.setInt(1,  supp.getDueQty());
			st.setInt(2,  supp.getDueQty());
			st.setString(3, supp.getProductNo());

			line2 = line1 + st.executeUpdate();

			if (line2 == 2) {
				con.commit();

			} else {
				con.rollback();

			}

			con.setAutoCommit(true);

			st.close();
			con.close();
			result = line2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}