package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.G_Arrival;
import bean.G_EntryExitInfo;
import bean.G_Shipping;
import bean.G_StockList;
import bean.PuroductStock;

public class PuroductStockDAO extends DAO {
	/**
	 * PURODUCT_STOCKテーブル参照メソッド
	 *  →指定年月､品番のレコードを検索
	 * 
	 * @param String stockInfoDate, String productNo
	 * @return PuroductStockビーン 「null：失敗」「インスタンス有：成功」
	 */
	public PuroductStock searchByDateAndPrNo(String stockInfoDate, String productNo) {
		PuroductStock PuroductStock = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM PURODUCT_STOCK WHERE STOCK_INFO_DATE=? AND PRODUCT_NO=?");
			st.setString(1, stockInfoDate);
			st.setString(2, productNo);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				PuroductStock = new PuroductStock();
				PuroductStock.setStockInfoDate(rs.getString("STOCK_INFO_DATE"));
				PuroductStock.setProductNo(rs.getString("PRODUCT_NO"));
				PuroductStock.setStockQty(rs.getInt("STOCK_QTY"));
				PuroductStock.setwStockQty(rs.getInt("W_STOCK_QTY"));
				PuroductStock.settNyuko(rs.getInt("T_NYUKO"));
				PuroductStock.settSyuko(rs.getInt("T_SYUKO"));
				PuroductStock.settNyuka(rs.getInt("T_NYUKA"));
				PuroductStock.settSyuka(rs.getInt("T_SYUKA"));
				PuroductStock.setUpDate(rs.getString("UP_DATE"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PuroductStock;
	}

	/**
	 * PURODUCT_STOCKテーブル参照メソッド
	 *  →指定品番､指定年月以前の最も近いレコードを取得
	 * 
	 * @param String stockInfoDate, String productNo
	 * @return PuroductStockビーン 「null：失敗」「インスタンス有：成功」
	 */
	public PuroductStock searchBefByDateAndPrNo(String stockInfoDate, String productNo) {
		PuroductStock PuroductStock = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM PURODUCT_STOCK WHERE STOCK_INFO_DATE<? AND PRODUCT_NO=? ORDER BY STOCK_INFO_DATE DESC");
			st.setString(1, stockInfoDate);
			st.setString(2, productNo);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				PuroductStock = new PuroductStock();
				PuroductStock.setStockInfoDate(rs.getString("STOCK_INFO_DATE"));
				PuroductStock.setProductNo(rs.getString("PRODUCT_NO"));
				PuroductStock.setStockQty(rs.getInt("STOCK_QTY"));
				PuroductStock.setwStockQty(rs.getInt("W_STOCK_QTY"));
				PuroductStock.settNyuko(rs.getInt("T_NYUKO"));
				PuroductStock.settSyuko(rs.getInt("T_SYUKO"));
				PuroductStock.settNyuka(rs.getInt("T_NYUKA"));
				PuroductStock.settSyuka(rs.getInt("T_SYUKA"));
				PuroductStock.setUpDate(rs.getString("UP_DATE"));
				// ｢break;｣を入れることで､STOCK_INFO_DATE DESCで規定した指定年月以前の最新のレコードのみ取得
				break;
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PuroductStock;
	}

	/**
	 * PURODUCT_STOCKテーブル参照メソッド
	 *  →指定品番の最も新しい年月のレコードを取得
	 * 
	 * @param String productNo
	 * @return PuroductStockビーン 「null：失敗」「インスタンス有：成功」
	 */
	public PuroductStock searchByDate(String productNo) {
		PuroductStock PuroductStock = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM PURODUCT_STOCK WHERE PRODUCT_NO=? ORDER BY STOCK_INFO_DATE DESC");
			st.setString(1, productNo);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				PuroductStock = new PuroductStock();
				PuroductStock.setStockInfoDate(rs.getString("STOCK_INFO_DATE"));
				PuroductStock.setProductNo(rs.getString("PRODUCT_NO"));
				PuroductStock.setStockQty(rs.getInt("STOCK_QTY"));
				PuroductStock.setwStockQty(rs.getInt("W_STOCK_QTY"));
				PuroductStock.settNyuko(rs.getInt("T_NYUKO"));
				PuroductStock.settSyuko(rs.getInt("T_SYUKO"));
				PuroductStock.settNyuka(rs.getInt("T_NYUKA"));
				PuroductStock.settSyuka(rs.getInt("T_SYUKA"));
				PuroductStock.setUpDate(rs.getString("UP_DATE"));
				// ｢break;｣を入れることで､STOCK_INFO_DATE DESCで規定した指定年月以前の最新のレコードのみ取得
				break;
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PuroductStock;
	}

	/**
	 * PURODUCT_STOCKテーブル参照メソッド →指定品番の最も新しい年月のレコードを取得
	 * 
	 * @param G_EntryExitInfo
	 * @return PuroductStockビーン 「null：失敗」「インスタンス有：成功」
	 */
	public PuroductStock searchByDate(G_EntryExitInfo G_EntryExitInfo) {
		return searchByDate(G_EntryExitInfo.getProductNo());
	}

	/**
	 * PURODUCT_STOCKテーブル参照メソッド →指定品番の最も新しい年月のレコードを取得
	 * 
	 * @param G_StockList
	 * @return PuroductStockビーン 「null：失敗」「インスタンス有：成功」
	 */
	public PuroductStock searchByDate(G_StockList G_StockList) {
		return searchByDate(G_StockList.getProductNo());
	}

	/**
	 * PURODUCT_STOCKテーブル参照メソッド
	 *  →指定品番､年月以降のリストを取得
	 * 
	 * @param String stockInfoDate, String productNo
	 * @return PuroductStockビーン 「null：失敗」「インスタンス有：成功」
	 */
	public List<PuroductStock> searchListByDateAndPrNo(String stockInfoDate, String productNo) {
		PuroductStock PuroductStock = null;
		List<PuroductStock> PuroductStockList = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM PURODUCT_STOCK WHERE STOCK_INFO_DATE>=? AND PRODUCT_NO=? ORDER BY STOCK_INFO_DATE ASC");
			st.setString(1, stockInfoDate);
			st.setString(2, productNo);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				PuroductStock = new PuroductStock();
				PuroductStock.setStockInfoDate(rs.getString("STOCK_INFO_DATE"));
				PuroductStock.setProductNo(rs.getString("PRODUCT_NO"));
				PuroductStock.setStockQty(rs.getInt("STOCK_QTY"));
				PuroductStock.setwStockQty(rs.getInt("W_STOCK_QTY"));
				PuroductStock.settNyuko(rs.getInt("T_NYUKO"));
				PuroductStock.settSyuko(rs.getInt("T_SYUKO"));
				PuroductStock.settNyuka(rs.getInt("T_NYUKA"));
				PuroductStock.settSyuka(rs.getInt("T_SYUKA"));
				PuroductStock.setUpDate(rs.getString("UP_DATE"));
				PuroductStockList.add(PuroductStock);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return PuroductStockList;
	}

	/**
	 * PURODUCT_STOCKテーブル処理メソッド
	 * 
	 * @param String stockInfoDate, String productNo, int tNyuko, int tSyuko, int
	 *               tNyuka, int tSyuka
	 *               →｢tNyuko｣｢tSyuko｣｢tNyuka｣｢tSyuka｣は､通常の登録はプラスの数値､取消/変更は変更前の数値との差分(減少ならマイナス､増加ならプラス)の数値で渡す
	 * @return 0:処理失敗 1:処理成功
	 */
	public int productStockProcess(String stockInfoDate, String productNo, int tNyuko, int tSyuko, int tNyuka,
			int tSyuka) {
		// 戻り値用変数
		int line = 0;
		// 引数の妥当性確認(引数｢tNyuko｣｢tSyuko｣｢tNyuka｣｢tSyuka｣は､どれか1つのみ1以外の値でその他はゼロである必要あり)
		if (!((tNyuko != 0 && tSyuko == 0 && tNyuka == 0 && tSyuka == 0)
				|| (tNyuko == 0 && tSyuko != 0 && tNyuka == 0 && tSyuka == 0)
				|| (tNyuko == 0 && tSyuko == 0 && tNyuka != 0 && tSyuka == 0)
				|| (tNyuko == 0 && tSyuko == 0 && tNyuka == 0 && tSyuka != 0))) {
			return line;
		}
		// 使用変数宣言
		int count = 0;
		PuroductStock PuroductStock = null;
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy/MM/dd");
		// 指定の品番で且つ指定年月以降のリスト取得(リストの年月は昇順)
		List<PuroductStock> PuroductStockList = searchListByDateAndPrNo(stockInfoDate, productNo);
		try {
			Connection con = getConnection();
			// 新規登録
			if (PuroductStockList.size() == 0) {
				// 指定品番､指定年月以前の最も近いレコードを取得
				PuroductStock = searchBefByDateAndPrNo(stockInfoDate, productNo);
				PreparedStatement st = null;
				st = con.prepareStatement("INSERT INTO PURODUCT_STOCK VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				st.setString(1, stockInfoDate); // stockInfoDate
				st.setString(2, productNo); // productNo
				if (PuroductStock != null) {
					st.setInt(3, PuroductStock.getStockQty() + (tNyuka - tSyuka)); // stockQty
					st.setInt(4, PuroductStock.getwStockQty() + (tNyuko - tSyuko)); // wStockQty
				} else if (PuroductStock == null) {
					st.setInt(3, tNyuka - tSyuka); // stockQty
					st.setInt(4, tNyuko - tSyuko); // wStockQty
				}
				st.setInt(5, tNyuko); // tNyuko
				st.setInt(6, tSyuko); // tSyuko
				st.setInt(7, tNyuka); // tNyuka
				st.setInt(8, tSyuka); // tSyuka
				st.setString(9, sdfymd.format(cl.getTime())); // upDate
				line = st.executeUpdate();
				st.close();
				con.close();
				// 更新処理(削除なし←入力内容が空の月があっても問題なし(そういったレコードを許容))
			} else if (PuroductStockList.size() > 0) {
				for (PuroductStock ps : PuroductStockList) {
					PreparedStatement sta = null;
					sta = con.prepareStatement("UPDATE PURODUCT_STOCK SET STOCK_QTY=?, W_STOCK_QTY=?, T_NYUKO=?, T_SYUKO=?, T_NYUKA=?, T_SYUKA=?, UP_DATE=? WHERE STOCK_INFO_DATE=? AND PRODUCT_NO=?");
					sta.setInt(1, ps.getStockQty() + (tNyuka - tSyuka)); // stockQty
					sta.setInt(2, ps.getwStockQty() + (tNyuko - tSyuko)); // wStockQty
					// 入庫数/出庫数/入荷数/出荷数は､登録時の年月のレコードのみ変更し､それ以降は変更しない
					if (count == 0) {
						sta.setInt(3, ps.gettNyuko() + tNyuko); // tNyuko
						sta.setInt(4, ps.gettSyuko() + tSyuko); // tSyuko
						sta.setInt(5, ps.gettNyuka() + tNyuka - (tNyuko - tSyuko)); // tNyuka
						sta.setInt(6, ps.gettSyuka() + tSyuka); // tSyuka
					} else {
						sta.setInt(3, ps.gettNyuko()); // tNyuko
						sta.setInt(4, ps.gettSyuko()); // tSyuko
						sta.setInt(5, ps.gettNyuka()); // tNyuka
						sta.setInt(6, ps.gettSyuka()); // tSyuka
					}
					sta.setString(7, sdfymd.format(cl.getTime())); // upDate
					sta.setString(8, stockInfoDate); // stockInfoDate
					sta.setString(9, productNo); // productNo
					line += sta.executeUpdate();
					sta.close();
					count++;
				}
				line = (line == count) ? 1 : 0;
			}
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * PURODUCT_STOCKテーブル処理メソッド
	 * 
	 * @param G_Shipping
	 * @return 0:処理失敗 1:処理成功
	 */
	public int productStockProcess(G_Shipping G_Shipping) {
		String stockInfoDate = G_Shipping.getShipDate().substring(0, 7).replace("-", "/");
		String productNo = G_Shipping.getProductNo();
		int tNyuko = 0;
		int tSyuko = 0;
		int tNyuka = 0;
		int tSyuka = Integer.parseInt(G_Shipping.getShipQty());
		return productStockProcess(stockInfoDate, productNo, tNyuko, tSyuko, tNyuka, tSyuka);
	}

	/**
	 * PURODUCT_STOCKテーブル処理メソッド
	 * 
	 * @param G_Arrival
	 * @return 0:処理失敗 1:処理成功
	 */
	public int productStockProcess(G_Arrival G_Arrival) {
		String stockInfoDate = G_Arrival.getDueDate().substring(0, 7).replace("-", "/");
		String productNo = G_Arrival.getProductNo();
		int tNyuko = 0;
		int tSyuko = 0;
		int tNyuka = Integer.parseInt(G_Arrival.getDueQty());
		int tSyuka = 0;
		return productStockProcess(stockInfoDate, productNo, tNyuko, tSyuko, tNyuka, tSyuka);
	}

	/**
	 * PURODUCT_STOCKテーブル処理メソッド
	 * 
	 * @param G_EntryExitInfo
	 * @return 0:処理失敗 1:処理成功
	 */
	public int productStockProcess(G_EntryExitInfo G_EntryExitInfo) {
		String stockInfoDate = G_EntryExitInfo.getRegistDate().substring(0, 7).replace("-", "/");
		String productNo = G_EntryExitInfo.getProductNo();
		int tNyuko = 0;
		int tSyuko = 0;
		int tNyuka = 0;
		int tSyuka = 0;
		// tNyuko/tSyukoを規定
		int nyukoQty = Integer.parseInt(G_EntryExitInfo.getNyukoQty().equals("") ? "0" : G_EntryExitInfo.getNyukoQty());
		int syukoQty = Integer.parseInt(G_EntryExitInfo.getSyukoQty().equals("") ? "0" : G_EntryExitInfo.getSyukoQty());
		int befEnExNum = Integer.parseInt(G_EntryExitInfo.getBefEnExNum().equals("") ? "0" : G_EntryExitInfo.getBefEnExNum());
		if (!G_EntryExitInfo.getNyukoQty().equals("") && G_EntryExitInfo.getSyukoQty().equals("")) {
			tNyuko = nyukoQty - befEnExNum;
			tSyuko = 0;
		} else if (G_EntryExitInfo.getNyukoQty().equals("") && !G_EntryExitInfo.getSyukoQty().equals("")) {
			tNyuko = 0;
			tSyuko = syukoQty - befEnExNum;
		} else {
			return 0;
		}
		return productStockProcess(stockInfoDate, productNo, tNyuko, tSyuko, tNyuka, tSyuka);
	}

}
