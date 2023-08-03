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

import bean.OrderTable;
import bean.Supplier;
import bean.UserMaster;

public class OrderTableDAO extends DAO {
	/**
	 * OrderTableテーブル参照メソッド
	 * →ORDER_NOによる検索
	 * @param OrderTableビーン
	 * @return OrderTableビーン 「null：失敗」「インスタンス有：成功」
	 */
	public OrderTable searchByOrdNo(OrderTable ot) {
		OrderTable orderTable = null;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("SELECT * FROM ORDER_TABLE WHERE ORDER_NO = ?");
			st.setString(1, ot.getOrderNo());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				orderTable = new OrderTable();
				orderTable.setOrderNo(rs.getString("ORDER_NO"));
				orderTable.setSupplierNo(rs.getString("SUPPLIER_NO"));
				orderTable.setProductNo(rs.getString("PRODUCT_NO"));
				orderTable.setOrderQty(rs.getInt("ORDER_QTY"));
				orderTable.setDeliveryDate(rs.getString("DELIVERY_DATE"));
				orderTable.setBiko(rs.getString("BIKO"));
				orderTable.setDueDate(rs.getString("DUE_DATE"));
				orderTable.setDueQty(rs.getInt("DUE_QTY"));
				orderTable.setFinFlg(rs.getString("FIN_FLG"));
				orderTable.setRegistUser(rs.getString("REGIST_USER"));
				orderTable.setOrderDate(rs.getString("ORDER_DATE"));
				orderTable.setOrderUser(rs.getString("ORDER_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return orderTable;
	}

	//↓↓未完成===============================================================================================
	// 画面担当の西条さんとすり合わせ必要
	//  ・引数をどうするか？
	//  ・戻り値をどうするか
	/**
	 * OrderTableテーブル参照メソッド
	 * →ORDER_DATEによる検索
	 *  →発注一覧画面用
	 * @param OrderTableビーン
	 * @return OrderTableビーン 「null：失敗」「インスタンス有：成功」
	 */
	public List<OrderTable> searchByOrdDate(OrderTable ot) {
		// 戻り値用の変数(リスト型)を宣言
		List<OrderTable> orderTableLists = new ArrayList<>();

		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement(
					"SELECT "
							+ "    OT.ORDER_DATE, "
							+ "    OT.DELIVERY_DATE, "
							+ "    OT.DUE_DATE, "
							+ "    OT.ORDER_QTY, "
							+ "    SM.SUPPLIER_NAME "
							+ "FROM "
							+ "    ORDER_TABLE \"OT\" "
							+ "    INNER JOIN SUPPLIER_MASTER \"SM\" "
							+ "    ON OT.SUPPLIER_NO = SM.SUPPLIER_NO "
							+ "WHERE OT.ORDER_DATE  BETWEEN ? AND ? "
							+ "ORDER BY ? ASC");
			st.setString(1, ot.getOrderDate()); //文字列の「yyyy/mm/dd」をbetweenでそのまま使えるのか？？？
			st.setString(2, ot.getOrderDate()); //文字列の「yyyy/mm/dd」をbetweenでそのまま使えるのか？？？
			st.setString(3, "ラジオボタンに対応した列名");
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//専用のbeanが必要なのか？
				OrderTable orderTable = new OrderTable();

				orderTableLists.add(orderTable);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return orderTableLists;
	}
	//↑↑未完成===============================================================================================

	/**
	 * OrderTableテーブル登録メソッド
	 * @param OrderTableビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insert(OrderTable ot, HttpServletRequest request) {
		// 戻り値用変数
		int line = 0;
		//ORDER_NO取得用カウンタ
		int count = 0;
		String ORDER_NO = "";
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfym = new SimpleDateFormat("yyMM");
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");

		HttpSession session = request.getSession();
		UserMaster um = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			OrderTable orderNoCheck = new OrderTable();
			OrderTable orderTable = null;

			//ORDER_NO作成
			do {
				//注文番号のシーケンス値取得
				st = con.prepareStatement("SELECT NEXTVAL('ORDER_NO')");
				rs = st.executeQuery();
				while (rs.next()) {
					ORDER_NO = String.valueOf(rs.getInt("NEXTVAL"));
				}
				//注文番号を左ゼロ埋めで5桁にする処理
				do {
					if (ORDER_NO.length() < 5) {
						ORDER_NO = "0" + ORDER_NO;
					} else {
						break;
					}
				} while (true);
				//注文番号の完成系作成
				ORDER_NO = "OD-" + sdfym.format(cl.getTime()) + "-" + ORDER_NO;
				//作成した注文番号の未使用確認
				orderNoCheck.setOrderNo(ORDER_NO);
				orderTable = searchByOrdNo(orderNoCheck);
				if (orderTable == null) {
					//使用可能
					break;
				} else if (count >= 100000) {
					session.setAttribute("state", "使用可能な注文番号がありません。\\n処理を終了します｡");
					return line;
				}
				//ORDER_NO取得用カウンタ、カウントアップ
				count++;
			} while (true);

			//インサート処理 13項目
			st = con.prepareStatement("INSERT INTO ORDER_TABLE VALUES(? ,? ,?, ?, ?, '', '', 0, ?, ?, ?, ?)");
			st.setString(1, ORDER_NO);
			st.setString(2, ot.getSupplierNo());
			st.setString(3, ot.getProductNo());
			st.setInt(4, ot.getOrderQty());
			st.setString(5, ot.getDeliveryDate());
			st.setString(6, ot.getFinFlg());
			st.setString(7, um.getUserId());
			st.setString(8, sdfymd.format(cl.getTime()));
			st.setString(9, um.getUserId());

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
	 * OrderTableテーブル更新メソッド
	 * →納入画面用
	 * @param OrderTable
	 * @param OrderTableビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int updateBySupNo(OrderTable ot, HttpServletRequest request) {
		int line = 0;
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		HttpSession session = request.getSession();
		UserMaster um = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = con
					.prepareStatement(
							"UPDATE "
									+ "    ORDER_TABLE "
									+ "SET "
									+ "    DUE_DATE = ?, "
									+ "    DUE_QTY = ?, "
									+ "    FIN_FLG = '1', "
									+ "    REGIST_USER = ? "
									+ "WHERE "
									+ "    ORDER_NO = ? ");
			st.setString(1, sdf.format(cl.getTime()));
			st.setInt(2, ot.getDueQty());
			st.setString(3, um.getUserId());
			st.setString(4, ot.getOrderNo());

			line = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	public List<OrderTable> execution(String productNo) throws Exception {
		List<OrderTable> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,OT.ORDER_DATE,OT.DUE_DATE,OT.DELIVERY_DATE,OT.ORDER_QTY,SU.SUPPLIER_NAME,SU.SUPPLIER_NO FROM ORDER_TABLE OT INNER JOIN SUPPLIER_MASTER SU ON "
						+
						"OT.SUPPLIER_NO = SU.SUPPLIER_NO INNER JOIN PRODUCT_MASTER PR ON " +
						"OT.PRODUCT_NO = PR.PRODUCT_NO WHERE PR.PRODUCT_NO = ?");

		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			OrderTable orEx = new OrderTable();
			orEx.setProductNo(rs.getString("PRODUCT_NO"));
			orEx.setProductName(rs.getNString("PRODUCT_NAME"));
			orEx.setOrderDate(rs.getString("ORDER_DATE"));
			orEx.setDueDate(rs.getString("DUE_DATE"));
			orEx.setDeliveryDate(rs.getNString("DELIVERY_DATE"));
			orEx.setOrderQty(rs.getInt("ORDER_QTY"));
			orEx.setSupplierName(rs.getString("SUPPLIER_NAME"));
			orEx.setSupplierNo(rs.getString("SUPPLIER_NO"));
			list.add(orEx);
		}
		st.close();
		con.close();

		return list;
	}

	public List<OrderTable> searchEntry(String productNo) throws Exception {
		List<OrderTable> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,OT.ORDER_DATE,OT.DUE_DATE,OT.DELIVERY_DATE,OT.ORDER_QTY,SU.SUPPLIER_NAME,SU.SUPPLIER_NO "
						+ "FROM ORDER_TABLE OT "
						+ "INNER JOIN SUPPLIER_MASTER SU "
						+ "ON OT.SUPPLIER_NO = SU.SUPPLIER_NO "
						+ "INNER JOIN PRODUCT_MASTER PR "
						+ "ON OT.PRODUCT_NO = PR.PRODUCT_NO "
						+ "WHERE PR.PRODUCT_NO = ?"
						+ "AND OT.DUE_DATE IS  NULL ");

		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			OrderTable orEx = new OrderTable();

			orEx.setProductNo(rs.getString("PRODUCT_NO"));
			orEx.setProductName(rs.getNString("PRODUCT_NAME"));
			orEx.setOrderDate(rs.getString("ORDER_DATE"));
			orEx.setDueDate(rs.getString("DUE_DATE"));
			orEx.setDeliveryDate(rs.getNString("DELIVERY_DATE"));
			orEx.setOrderQty(rs.getInt("ORDER_QTY"));
			orEx.setSupplierName(rs.getString("SUPPLIER_NAME"));
			orEx.setSupplierNo(rs.getString("SUPPLIER_NO"));
			list.add(orEx);
		}
		st.close();
		con.close();

		return list;
	}

	public List<OrderTable> searchNounyusumi(String productNo) throws Exception {
		List<OrderTable> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"SELECT PR.PRODUCT_NO,PR.PRODUCT_NAME,OT.ORDER_DATE,OT.DUE_DATE,OT.DELIVERY_DATE,OT.ORDER_QTY,SU.SUPPLIER_NAME,SU.SUPPLIER_NO "
						+ "FROM ORDER_TABLE OT "
						+ "INNER JOIN SUPPLIER_MASTER SU "
						+ "ON OT.SUPPLIER_NO = SU.SUPPLIER_NO "
						+ "INNER JOIN PRODUCT_MASTER PR "
						+ "ON OT.PRODUCT_NO = PR.PRODUCT_NO "
						+ "WHERE PR.PRODUCT_NO = ? "
						+ "AND OT.DUE_DATE IS NOT NULL");

		st.setString(1, productNo);//(何番目の?か,置き換えるキーワード)
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			OrderTable orEx = new OrderTable();
			orEx.setProductNo(rs.getString("PRODUCT_NO"));
			orEx.setProductName(rs.getNString("PRODUCT_NAME"));
			orEx.setOrderDate(rs.getString("ORDER_DATE"));
			orEx.setDueDate(rs.getString("DUE_DATE"));
			orEx.setDeliveryDate(rs.getNString("DELIVERY_DATE"));
			orEx.setOrderQty(rs.getInt("ORDER_QTY"));
			orEx.setSupplierName(rs.getString("SUPPLIER_NAME"));
			orEx.setSupplierNo(rs.getString("SUPPLIER_NO"));
			list.add(orEx);
		}
		st.close();
		con.close();

		return list;
	}
	public Supplier searchByOrdNo2(Supplier supp) {
		Supplier orderTable = null;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("SELECT OT.ORDER_NO, OT.ORDER_DATE, OT.PRODUCT_NO, PM.PRODUCT_NAME, OT.ORDER_QTY, OT.DUE_QTY, OT.DUE_DATE, OT.FIN_FLG\r\n" +
					"FROM ORDER_TABLE OT\r\n" +
					"INNER JOIN PRODUCT_MASTER PM\r\n" +
					"ON OT.PRODUCT_NO=PM.PRODUCT_NO\r\n" +
					"WHERE OT.ORDER_NO= ?");
			st.setString(1, supp.getOrderNo());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				orderTable =new Supplier();
				orderTable.setOrderNo(rs.getString("ORDER_NO"));
				orderTable.setOrderDate(rs.getString("ORDER_DATE"));
				orderTable.setProductNo(rs.getString("PRODUCT_NO"));
				orderTable.setProductName(rs.getString("PRODUCT_NAME"));
				orderTable.setOrderQty(rs.getInt("ORDER_QTY"));
				orderTable.setDueQty(rs.getInt("DUE_QTY"));
				orderTable.setDueDate(rs.getString("DUE_DATE"));
				orderTable.setFinFlg(rs.getString("FIN_FLG"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return orderTable;
	}
}