package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.EntryExitInfo;
import bean.PuroductStock;
import bean.Stock;

public class PuroductStockDAO extends DAO {

	public PuroductStock searchByPrNo(String productNo) throws Exception {
		PuroductStock prm = null;

		Connection con = getConnection();

		PreparedStatement st;
		st = con.prepareStatement(
				"SELECT * FROM PURODUCT_STOCK WHERE PRODUCT_NO=? ");
		st.setString(1, productNo);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			prm = new PuroductStock();
			prm.setStockInfoDate(rs.getString("stock_info_date"));
			prm.setProductNo(rs.getString("product_no"));
			prm.setStockQty(rs.getInt("stock_qty"));
			prm.settNyuko(rs.getInt("t_nyuko"));
			prm.settSyuko(rs.getInt("t_syuko"));
			prm.settSyuka(rs.getInt("t_syuka"));
			prm.setUpDate(rs.getString("up_date"));

		}
		st.close();
		con.close();
		return prm;

	}

	public PuroductStock searchByPrNoS(String productNo) throws Exception {
		PuroductStock prm = null;

		Connection con = getConnection();

		PreparedStatement st;
		st = con.prepareStatement(
				"SELECT * FROM PURODUCT_STOCK "
						+ "WHERE PRODUCT_NO= ? "
						+ "AND STOCK_INFO_DATE = TO_CHAR(SYSDATE,'YYYY/MM')");

		st.setString(1, productNo);

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			prm = new PuroductStock();
			prm.setStockInfoDate(rs.getString("stock_info_date"));
			prm.setProductNo(rs.getString("product_no"));
			prm.setStockQty(rs.getInt("stock_qty"));
			prm.settNyuko(rs.getInt("t_nyuko"));
			prm.settSyuko(rs.getInt("t_syuko"));
			prm.settSyuka(rs.getInt("t_syuka"));
			prm.setUpDate(rs.getString("up_date"));

		}
		st.close();
		con.close();
		return prm;

	}

	public List<Stock> searchBySchedule(String proNo) throws Exception {
		List<Stock> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"SELECT PO.PRODUCT_NO, PO.DELIVERY_DATE, PO.CUSTOMER_NO, CM.CUSTOMER_NAME, PO.PO_NO, PO.ORDER_QTY, PO.FIN_FLG \r\n"
						+
						"FROM PURCHASE_ORDER PO\r\n" +
						"INNER JOIN CUSTOMER_MASTER CM\r\n" +
						"ON PO.CUSTOMER_NO = CM.CUSTOMER_NO \r\n" +
						"WHERE PRODUCT_NO = ?");
		st.setString(1, proNo);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			if (rs.getString("FIN_FLG").equals("0")) {
				Stock stock = new Stock();
				stock.setProductNo(rs.getString("PRODUCT_NO"));
				stock.setDeliveryDate(rs.getString("DELIVERY_DATE").replace("-", "/"));
				stock.setCustomerNo(rs.getString("CUSTOMER_NO"));
				stock.setCustomerName(rs.getString("CUSTOMER_NAME"));
				stock.setPoNo(rs.getString("PO_NO"));
				stock.setPoQty(rs.getInt("ORDER_QTY"));
				list.add(stock);
			}
		}
		st = con.prepareStatement(
				"SELECT OT.PRODUCT_NO, OT.DELIVERY_DATE, OT.ORDER_NO, OT.SUPPLIER_NO, SM.SUPPLIER_NAME, OT.ORDER_QTY ,OT.FIN_FLG\r\n"
						+
						" FROM ORDER_TABLE OT\r\n" +
						" INNER JOIN SUPPLIER_MASTER SM\r\n" +
						" ON OT.SUPPLIER_NO = SM.SUPPLIER_NO\r\n" +
						" WHERE PRODUCT_NO = ?");
		st.setString(1, proNo);
		rs = st.executeQuery();

		while (rs.next()) {
			if (rs.getString("FIN_FLG").equals("0")) {
				Stock stock = new Stock();
				stock.setProductNo(rs.getString("PRODUCT_NO"));
				stock.setDeliveryDate(rs.getString("DELIVERY_DATE").replace("-", "/"));
				stock.setPoNo(rs.getString("ORDER_NO"));
				stock.setSupprierNo(rs.getString("SUPPLIER_NO"));
				stock.setSupprierName(rs.getString("SUPPLIER_NAME"));
				stock.setOrQty(rs.getInt("ORDER_QTY"));
				list.add(stock);
			}
		}
		st.close();
		con.close();
		return list;
	}

	public int updateByNyukoStock(EntryExitInfo recv_data) {

		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		int result = 0;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement(
					"UPDATE PURODUCT_STOCK SET STOCK_QTY=STOCK_QTY+? , T_NYUKO=T_NYUKO+? , T_SYUKO=T_SYUKO+? , T_SYUKA=T_SYUKA+? , UP_DATE=? WHERE PRODUCT_NO=? AND STOCK_INFO_DATE=TO_CHAR(SYSDATE,'YYYY/MM') ");
			st.setInt(1, recv_data.getNyukoQty());
			st.setInt(2, recv_data.getNyukoQty());
			st.setInt(3, 0);
			st.setInt(4, 0);
			st.setString(5, sdf.format(cl.getTime()));
			st.setString(6, recv_data.getProductNo());

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public int insertByNyukoStock(EntryExitInfo enEx) throws Exception {

		Connection con = getConnection();

		PreparedStatement st;
		st = con.prepareStatement(
				"SELECT STOCK_INFO_DATE ,PRODUCT_NO,STOCK_QTY "
						+ "FROM PURODUCT_STOCK "
						+ "WHERE PRODUCT_NO = ?"
						+ "ORDER BY  STOCK_INFO_DATE DESC");

		st.setString(1, enEx.getProductNo());

		ResultSet rs = st.executeQuery();

		int zaiko = 0;
		while (rs.next()) {
			zaiko = rs.getInt("STOCK_QTY");
			break;
		}

		int result = 0;
		try {
			//登録日用にCalendarクラスのオブジェクトを生成する
			Calendar cl = Calendar.getInstance();
			//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM");

			st = con.prepareStatement("INSERT INTO PURODUCT_STOCK VALUES(?,?,?,?,?,?,?)");
			st.setString(1, sd.format(cl.getTime()));
			st.setString(2, enEx.getProductNo());
			st.setInt(3, enEx.getNyukoQty() + zaiko);
			st.setInt(4, enEx.getNyukoQty());
			st.setInt(5, 0);
			st.setInt(6, 0);
			st.setString(7, sdf.format(cl.getTime()));
			result = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void kousinByNyukoStock(EntryExitInfo entryexitinfo) {

		try {
			PuroductStockDAO zd = new PuroductStockDAO();
			PuroductStock getPuroductMasterInfo = zd.searchByPrNoS(entryexitinfo.getProductNo());

			if (getPuroductMasterInfo == null) {
				zd.insertByNyukoStock(entryexitinfo);

			} else {
				zd.updateByNyukoStock(entryexitinfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int updateBySyukoStock(EntryExitInfo recv_data) {

		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM");

		int result = 0;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement(
					"UPDATE PURODUCT_STOCK SET STOCK_QTY=STOCK_QTY-? , T_NYUKO=T_NYUKO+? , T_SYUKO=T_SYUKO+? , T_SYUKA=T_SYUKA+? , UP_DATE=? WHERE PRODUCT_NO=? AND STOCK_INFO_DATE=?");
			st.setInt(1, recv_data.getSyukoQty());
			st.setInt(2, 0);
			st.setInt(3, recv_data.getSyukoQty());
			st.setInt(4, 0);
			st.setString(5, sdf.format(cl.getTime()));

			st.setString(6, recv_data.getProductNo());
			st.setString(7, sd.format(cl.getTime()));

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	public int updateBySyukaStock(EntryExitInfo recv_data) {

		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM");

		int result = 0;
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement(
					"UPDATE PURODUCT_STOCK SET STOCK_QTY=STOCK_QTY-? ,T_SYUKA=T_SYUKA+? , UP_DATE=? WHERE PRODUCT_NO=? AND STOCK_INFO_DATE=?");
			st.setInt(1, recv_data.getSyukoQty());
			st.setInt(2, recv_data.getSyukoQty());
			st.setString(3, sdf.format(cl.getTime()));
			st.setString(4, recv_data.getProductNo());
			st.setString(5, sd.format(cl.getTime()));

			result = st.executeUpdate();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public int insertBySyukoStock(EntryExitInfo enEx) throws Exception {

		Connection con = getConnection();

		PreparedStatement st;
		st = con.prepareStatement(
				"SELECT STOCK_INFO_DATE ,PRODUCT_NO,STOCK_QTY "
						+ "FROM PURODUCT_STOCK "
						+ "WHERE PRODUCT_NO = ?"
						+ "ORDER BY  STOCK_INFO_DATE DESC");

		st.setString(1, enEx.getProductNo());

		ResultSet rs = st.executeQuery();

		int zaiko = 0;
		while (rs.next()) {
			zaiko = rs.getInt("STOCK_QTY");
			break;
		}

		int result = 0;
		try {
			//登録日用にCalendarクラスのオブジェクトを生成する
			Calendar cl = Calendar.getInstance();
			//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM");

			st = con.prepareStatement("INSERT INTO PURODUCT_STOCK VALUES(?,?,?,?,?,?,?)");
			st.setString(1, sd.format(cl.getTime()));
			st.setString(2, enEx.getProductNo());
			st.setInt(3, enEx.getSyukoQty() - (enEx.getSyukoQty() * 2) + zaiko);
			st.setInt(4, 0);
			st.setInt(5, enEx.getSyukoQty());
			st.setInt(6, 0);
			st.setString(7, sdf.format(cl.getTime()));
			result = st.executeUpdate();

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

	public void kousinBySyukoStock(EntryExitInfo entryexitinfo) {

		try {
			PuroductStockDAO zd = new PuroductStockDAO();
			PuroductStock getPuroductMasterInfo = zd.searchByPrNoS(entryexitinfo.getProductNo());
			if (getPuroductMasterInfo == null) {
				zd.insertBySyukoStock(entryexitinfo);

			} else {
				zd.updateBySyukoStock(entryexitinfo);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	//年月日・顧客先・受注番号・受注数・発注先・発注数・在庫数
	//のSQL文はテーブル結合が必要なので、新たにBaenを作成しないと作成できない。
	//（例）・品番マスタの品番
	//・入出庫テーブルの顧客コード
	//・仕入先マスタの仕入先コード
	//・受注テーブルの品番と完了フラグ
	//・在庫テーブルの品番
	//・発注テーブルの品番と完了フラグ

}
