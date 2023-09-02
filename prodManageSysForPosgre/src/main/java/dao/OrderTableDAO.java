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

import action.MainAction;
import bean.G_Order;
import bean.OrderTable;
import bean.UserMaster;

public class OrderTableDAO extends DAO {

	// ↓↓未完成===============================================================================================
	// 画面担当の西条さんとすり合わせ必要
	// ・引数をどうするか？
	// ・戻り値をどうするか
	/**
	 * OrderTableテーブル参照メソッド →ORDER_DATEによる検索 →発注一覧画面用
	 * 
	 * @param OrderTableビーン
	 * @return OrderTableビーン 「null：失敗」「インスタンス有：成功」
	 */
//	public List<OrderTable> searchByOrdDate(OrderTable ot) {
//		// 戻り値用の変数(リスト型)を宣言
//		List<OrderTable> OrderTableLists = new ArrayList<>();
//
//		try {
//			Connection con = getConnection();
//
//			PreparedStatement st = con.prepareStatement(
//					"SELECT "
//							+ "    OT.ORDER_DATE, "
//							+ "    OT.DELIVERY_DATE, "
//							+ "    OT.DUE_DATE, "
//							+ "    OT.ORDER_QTY, "
//							+ "    SM.SUPPLIER_NAME "
//							+ "FROM "
//							+ "    ORDER_TABLE \"OT\" "
//							+ "    INNER JOIN SUPPLIER_MASTER \"SM\" "
//							+ "    ON OT.SUPPLIER_NO = SM.SUPPLIER_NO "
//							+ "WHERE OT.ORDER_DATE  BETWEEN ? AND ? "
//							+ "ORDER BY ? ASC");
//			st.setString(1, ot.getOrderDate()); //文字列の「yyyy/mm/dd」をbetweenでそのまま使えるのか？？？
//			st.setString(2, ot.getOrderDate()); //文字列の「yyyy/mm/dd」をbetweenでそのまま使えるのか？？？
//			st.setString(3, "ラジオボタンに対応した列名");
//			ResultSet rs = st.executeQuery();
//
//			while (rs.next()) {
//				//専用のbeanが必要なのか？
//				OrderTable OrderTable = new OrderTable();
//
//				OrderTableLists.add(OrderTable);
//			}
//			st.close();
//			con.close();
//		} catch (Exception e) {
//			System.out.println("SQLでエラーが発生しました。");
//			e.printStackTrace();
//		}
//		return OrderTableLists;
//	}
	// ↑↑未完成===============================================================================================

	/**
	 * OrderTableテーブル参照メソッド →ORDER_NOによる検索
	 * 
	 * @param String orderNo
	 * @return OrderTableビーン 「null：失敗」「インスタンス有：成功」
	 */
	public OrderTable searchByOrdNo(String orderNo) {
		OrderTable OrderTable = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM ORDER_TABLE WHERE ORDER_NO=?");
			st.setString(1, orderNo);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				OrderTable = new OrderTable();
				OrderTable.setOrderNo(rs.getString("ORDER_NO"));
				OrderTable.setSupplierNo(rs.getString("SUPPLIER_NO"));
				OrderTable.setProductNo(rs.getString("PRODUCT_NO"));
				OrderTable.setOrderQty(rs.getInt("ORDER_QTY"));
				OrderTable.setDeliveryDate(new MainAction().dateChangeForHTML(rs.getString("DELIVERY_DATE")));
				OrderTable.setBiko(rs.getString("BIKO"));
				OrderTable.setDueDate(new MainAction().dateChangeForHTML(rs.getString("DUE_DATE")));
				OrderTable.setDueQty(rs.getInt("DUE_QTY"));
				OrderTable.setFinFlg(rs.getString("FIN_FLG"));
				OrderTable.setRegistUser(rs.getString("REGIST_USER"));
				OrderTable.setOrderDate(new MainAction().dateChangeForHTML(rs.getString("ORDER_DATE")));
				OrderTable.setOrderUser(rs.getString("ORDER_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return OrderTable;
	}
	
	/**
	 * OrderTableテーブル参照メソッド →ORDER_NOによる検索
	 * 
	 * @param OrderTableビーン
	 * @return OrderTableビーン 「null：失敗」「インスタンス有：成功」
	 */
	public OrderTable searchByOrdNo(OrderTable OrderTable) {
		return searchByOrdNo(OrderTable.getOrderNo());
	}

	/**
	 * OrderTableテーブル参照メソッド →ORDER_NOによる検索
	 * 
	 * @param G_Orderビーン
	 * @return OrderTableビーン 「null：失敗」「インスタンス有：成功」
	 */
	public OrderTable searchByOrdNo(G_Order G_Order) {
		return searchByOrdNo(G_Order.getOrderNo());
	}

	/**
	 * OrderTableテーブル取得メソッド(検索条件なし) →ORDER_NOリスト取得用メソッド
	 *
	 * @param 引数無し
	 * @return List<OrderTable> 「null：失敗」「null以外：成功」
	 */
	public List<OrderTable> searchAll() {
		// 戻り値用の変数宣言
		List<OrderTable> OrderTableList = new ArrayList<>();
		OrderTable OrderTable = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM ORDER_TABLE ORDER BY ORDER_NO ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (!rs.getString("FIN_FLG").equals("1")) {
					OrderTable = new OrderTable();
					OrderTable.setOrderNo(rs.getString("ORDER_NO"));
					OrderTable.setSupplierNo(rs.getString("SUPPLIER_NO"));
					OrderTable.setProductNo(rs.getString("PRODUCT_NO"));
					OrderTable.setOrderQty(rs.getInt("ORDER_QTY"));
					OrderTable.setDeliveryDate(new MainAction().dateChangeForHTML(rs.getString("DELIVERY_DATE")));
					OrderTable.setBiko(rs.getString("BIKO"));
					OrderTable.setDueDate(new MainAction().dateChangeForHTML(rs.getString("DUE_DATE")));
					OrderTable.setDueQty(rs.getInt("DUE_QTY"));
					OrderTable.setFinFlg(rs.getString("FIN_FLG"));
					OrderTable.setRegistUser(rs.getString("REGIST_USER"));
					OrderTable.setOrderDate(new MainAction().dateChangeForHTML(rs.getString("ORDER_DATE")));
					OrderTable.setOrderUser(rs.getString("ORDER_USER"));
					OrderTableList.add(OrderTable);
				}
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return OrderTableList;
	}
	
	/**
	 * OrderTableテーブル取得メソッド(検索条件なし) →ORDER_NOリスト取得用メソッド
	 *
	 * @param 引数無し
	 * @return List<OrderTable> 「null：失敗」「null以外：成功」
	 */
	public List<OrderTable> searchAllAddFinFlg1() {
		// 戻り値用の変数宣言
		List<OrderTable> OrderTableList = new ArrayList<>();
		OrderTable OrderTable = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM ORDER_TABLE ORDER BY ORDER_NO ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				OrderTable = new OrderTable();
				OrderTable.setOrderNo(rs.getString("ORDER_NO"));
				OrderTable.setSupplierNo(rs.getString("SUPPLIER_NO"));
				OrderTable.setProductNo(rs.getString("PRODUCT_NO"));
				OrderTable.setOrderQty(rs.getInt("ORDER_QTY"));
				OrderTable.setDeliveryDate(new MainAction().dateChangeForHTML(rs.getString("DELIVERY_DATE")));
				OrderTable.setBiko(rs.getString("BIKO"));
				OrderTable.setDueDate(new MainAction().dateChangeForHTML(rs.getString("DUE_DATE")));
				OrderTable.setDueQty(rs.getInt("DUE_QTY"));
				OrderTable.setFinFlg(rs.getString("FIN_FLG"));
				OrderTable.setRegistUser(rs.getString("REGIST_USER"));
				OrderTable.setOrderDate(new MainAction().dateChangeForHTML(rs.getString("ORDER_DATE")));
				OrderTable.setOrderUser(rs.getString("ORDER_USER"));
				OrderTableList.add(OrderTable);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return OrderTableList;
	}

	/**
	 * OrderTableテーブル登録メソッド
	 * 
	 * @param OrderTableビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insert(OrderTable ot, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		// ORDER_NO取得用カウンタ
		int count = 0;
		String ORDER_NO = "";
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfym = new SimpleDateFormat("yyMM");
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			OrderTable orderNoCheck = new OrderTable();
			OrderTable OrderTable = null;
			// ORDER_NO作成
			do {
				// 注文番号のシーケンス値取得
				st = con.prepareStatement("SELECT NEXTVAL('ORDER_NO')");
				rs = st.executeQuery();
				while (rs.next()) {
					ORDER_NO = String.valueOf(rs.getInt("NEXTVAL"));
				}
				// 注文番号を左ゼロ埋めで5桁にする処理
				do {
					if (ORDER_NO.length() < 5) {
						ORDER_NO = "0" + ORDER_NO;
					} else {
						break;
					}
				} while (true);
				// 注文番号の完成系作成
				ORDER_NO = "OD-" + sdfym.format(cl.getTime()) + "-" + ORDER_NO;
				// 作成した注文番号の未使用確認
				orderNoCheck.setOrderNo(ORDER_NO);
				OrderTable = searchByOrdNo(orderNoCheck);
				if (OrderTable == null) {
					// 使用可能
					break;
				} else if (count >= 100000) {
					session.setAttribute("state", "使用可能な注文番号がありません。\\n処理を終了します｡");
					return line;
				}
				// ORDER_NO取得用カウンタ、カウントアップ
				count++;
			} while (true);
			// 新規登録処理 13項目
			st = con.prepareStatement("INSERT INTO ORDER_TABLE VALUES(?, ?, ?, ?, ?, ?, NULL, 0, '0', ?, ?, ?)");
			st.setString(1, ORDER_NO);
			st.setString(2, ot.getSupplierNo());
			st.setString(3, ot.getProductNo());
			st.setInt(4, ot.getOrderQty());
			st.setString(5, new MainAction().dateChangeForDB(ot.getDeliveryDate()));
			st.setString(6, ot.getBiko());
			st.setString(7, user.getUserId());
			st.setString(8, sdfymd.format(cl.getTime()));
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
	 * OrderTableテーブル更新メソッド →発注画面用
	 * 
	 * @param OrderTable
	 * @param OrderTableビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int updateForOrder(G_Order G_Order, HttpServletRequest request) {
		int line = 0;
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("UPDATE ORDER_TABLE SET SUPPLIER_NO=?, PRODUCT_NO=?, ORDER_QTY=?, DELIVERY_DATE=?, BIKO=?, REGIST_USER=? WHERE ORDER_NO=?");
			st.setString(1, G_Order.getSupplierNo());
			st.setString(2, G_Order.getProductNo());
			st.setInt(3, Integer.parseInt(G_Order.getOrderQty()));
			st.setString(4, new MainAction().dateChangeForDB(G_Order.getDeliveryDate()));
			st.setString(5, G_Order.getBiko());
			st.setString(6, user.getUserId());
			st.setString(7, G_Order.getOrderNo());
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
	 * OrderTableテーブル更新メソッド →納入画面用
	 * 
	 * @param OrderTable
	 * @param OrderTableビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int updateForSupplier(OrderTable OrderTable, HttpServletRequest request) {
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
			PreparedStatement st = con.prepareStatement("UPDATE ORDER_TABLE SET DUE_DATE=?, DUE_QTY=?, FIN_FLG='1', REGIST_USER=? WHERE ORDER_NO=?");
			st.setString(1, sdfymd.format(cl.getTime()));
			st.setInt(2, OrderTable.getDueQty());
			st.setString(3, user.getUserId());
			st.setString(4, OrderTable.getOrderNo());
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
	 * 発注番号に合致するレコードを削除するメソッド
	 * 
	 * @param G_Orderクラスのインスタンス
	 * @return 0:削除失敗 1:削除成功
	 */
	public int delete(G_Order G_Order, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("DELETE FROM ORDER_TABLE WHERE ORDER_NO=?");
			st.setString(1, G_Order.getOrderNo());
			line = st.executeUpdate();
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

//	public List<OrderTable> execution(String productNo) throws Exception {
//		List<OrderTable> list = new ArrayList<>();
//
//		Connection con = getConnection();
//		PreparedStatement st = con.prepareStatement(
//				"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,OT.ORDER_DATE,OT.DUE_DATE,OT.DELIVERY_DATE,OT.ORDER_QTY,SU.SUPPLIER_NAME,SU.SUPPLIER_NO FROM ORDER_TABLE OT INNER JOIN SUPPLIER_MASTER SU ON "
//						+
//						"OT.SUPPLIER_NO = SU.SUPPLIER_NO INNER JOIN PRODUCT_MASTER PR ON " +
//						"OT.PRODUCT_NO = PR.PRODUCT_NO WHERE PR.PRODUCT_NO = ?");
//
//		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
//		ResultSet rs = st.executeQuery();
//
//		while (rs.next()) {
//			OrderTable orEx = new OrderTable();
//			orEx.setProductNo(rs.getString("PRODUCT_NO"));
//			orEx.setProductName(rs.getNString("PRODUCT_NAME"));
//			orEx.setOrderDate(rs.getString("ORDER_DATE"));
//			orEx.setDueDate(rs.getString("DUE_DATE"));
//			orEx.setDeliveryDate(rs.getNString("DELIVERY_DATE"));
//			orEx.setOrderQty(rs.getInt("ORDER_QTY"));
//			orEx.setSupplierName(rs.getString("SUPPLIER_NAME"));
//			orEx.setSupplierNo(rs.getString("SUPPLIER_NO"));
//			list.add(orEx);
//		}
//		st.close();
//		con.close();
//
//		return list;
//	}

//	public List<OrderTable> searchEntry(String productNo) throws Exception {
//		List<OrderTable> list = new ArrayList<>();
//
//		Connection con = getConnection();
//		PreparedStatement st = con.prepareStatement(
//				"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,OT.ORDER_DATE,OT.DUE_DATE,OT.DELIVERY_DATE,OT.ORDER_QTY,SU.SUPPLIER_NAME,SU.SUPPLIER_NO "
//						+ "FROM ORDER_TABLE OT "
//						+ "INNER JOIN SUPPLIER_MASTER SU "
//						+ "ON OT.SUPPLIER_NO = SU.SUPPLIER_NO "
//						+ "INNER JOIN PRODUCT_MASTER PR "
//						+ "ON OT.PRODUCT_NO = PR.PRODUCT_NO "
//						+ "WHERE PR.PRODUCT_NO = ?"
//						+ "AND OT.DUE_DATE IS  NULL ");
//
//		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
//		ResultSet rs = st.executeQuery();
//
//		while (rs.next()) {
//			OrderTable orEx = new OrderTable();
//
//			orEx.setProductNo(rs.getString("PRODUCT_NO"));
//			orEx.setProductName(rs.getNString("PRODUCT_NAME"));
//			orEx.setOrderDate(rs.getString("ORDER_DATE"));
//			orEx.setDueDate(rs.getString("DUE_DATE"));
//			orEx.setDeliveryDate(rs.getNString("DELIVERY_DATE"));
//			orEx.setOrderQty(rs.getInt("ORDER_QTY"));
//			orEx.setSupplierName(rs.getString("SUPPLIER_NAME"));
//			orEx.setSupplierNo(rs.getString("SUPPLIER_NO"));
//			list.add(orEx);
//		}
//		st.close();
//		con.close();
//
//		return list;
//	}

//	public List<OrderTable> searchNounyusumi(String productNo) throws Exception {
//		List<OrderTable> list = new ArrayList<>();
//
//		Connection con = getConnection();
//		PreparedStatement st = con.prepareStatement(
//				"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,OT.ORDER_DATE,OT.DUE_DATE,OT.DELIVERY_DATE,OT.ORDER_QTY,SU.SUPPLIER_NAME,SU.SUPPLIER_NO "
//						+ "FROM ORDER_TABLE OT "
//						+ "INNER JOIN SUPPLIER_MASTER SU "
//						+ "ON OT.SUPPLIER_NO = SU.SUPPLIER_NO "
//						+ "INNER JOIN PRODUCT_MASTER PR "
//						+ "ON OT.PRODUCT_NO = PR.PRODUCT_NO "
//						+ "WHERE PR.PRODUCT_NO = ? "
//						+ "AND OT.DUE_DATE IS NOT NULL");
//
//		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
//		ResultSet rs = st.executeQuery();
//
//		while (rs.next()) {
//			OrderTable orEx = new OrderTable();
//			orEx.setProductNo(rs.getString("PRODUCT_NO"));
//			orEx.setProductName(rs.getNString("PRODUCT_NAME"));
//			orEx.setOrderDate(rs.getString("ORDER_DATE"));
//			orEx.setDueDate(rs.getString("DUE_DATE"));
//			orEx.setDeliveryDate(rs.getNString("DELIVERY_DATE"));
//			orEx.setOrderQty(rs.getInt("ORDER_QTY"));
//			orEx.setSupplierName(rs.getString("SUPPLIER_NAME"));
//			orEx.setSupplierNo(rs.getString("SUPPLIER_NO"));
//			list.add(orEx);
//		}
//		st.close();
//		con.close();
//
//		return list;
//	}

//	public Supplier searchByOrdNo2(Supplier supp) {
//		Supplier OrderTable = null;
//		try {
//			Connection con = getConnection();
//
//			PreparedStatement st = con.prepareStatement("SELECT OT.ORDER_NO, OT.ORDER_DATE, OT.PRODUCT_NO, PM.PRODUCT_NAME, OT.ORDER_QTY, OT.DUE_QTY, OT.DUE_DATE, OT.FIN_FLG\r\n" +
//					"FROM ORDER_TABLE OT\r\n" +
//					"INNER JOIN PRODUCT_MASTER PM\r\n" +
//					"ON OT.PRODUCT_NO=PM.PRODUCT_NO\r\n" +
//					"WHERE OT.ORDER_NO= ?");
//			st.setString(1, supp.getOrderNo());
//			ResultSet rs = st.executeQuery();
//
//			while (rs.next()) {
//				OrderTable =new Supplier();
//				OrderTable.setOrderNo(rs.getString("ORDER_NO"));
//				OrderTable.setOrderDate(rs.getString("ORDER_DATE"));
//				OrderTable.setProductNo(rs.getString("PRODUCT_NO"));
//				OrderTable.setProductName(rs.getString("PRODUCT_NAME"));
//				OrderTable.setOrderQty(rs.getInt("ORDER_QTY"));
//				OrderTable.setDueQty(rs.getInt("DUE_QTY"));
//				OrderTable.setDueDate(rs.getString("DUE_DATE"));
//				OrderTable.setFinFlg(rs.getString("FIN_FLG"));
//			}
//			st.close();
//			con.close();
//		} catch (Exception e) {
//			System.out.println("SQLでエラーが発生しました。");
//			e.printStackTrace();
//		}
//		return OrderTable;
//	}
}