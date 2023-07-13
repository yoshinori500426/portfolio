package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bean.GoodsReceipt;

public class GoodsReceiptDAO extends DAO {
	/**
	 * GoodsReceiptテーブル参照メソッド
	 *
	 * @param GoodsReceiptビーン
	 * @return GoodsReceiptビーン 「null：失敗」「インスタンス有：成功」
	 */
	public GoodsReceipt searchGoodsReceiptByJanCodeAndNyukoYMD(GoodsReceipt goodsreceipt) {
		GoodsReceipt gr = null;
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Connection con = getConnection();

			PreparedStatement st = con
					.prepareStatement("SELECT * FROM GOODS_RECEIPT WHERE JAN_CODE = ? AND NYUKO_YMD = ?");
			st.setString(1, goodsreceipt.getJanCode());
			st.setString(2, sdf.format(cl.getTime()));
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				gr = new GoodsReceipt();
				gr.setJanCode(rs.getString("JAN_CODE"));
				gr.setNyukoYMD(sdf.format(cl.getTime()));
				gr.setNyukoPCS(rs.getInt("NYUKO_PCS"));
				gr.setUnitPrice(rs.getInt("UNIT_PRICE"));
				gr.setSellingPrice(rs.getInt("SELLING_PRICE"));
				gr.setEtc(rs.getString("ETC"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return gr;
	}

	/**
	 * GoodsReceiptテーブル登録メソッド
	 *
	 * @param GoodsReceiptビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertToGoodsReceipt(GoodsReceipt sp) {
		int line = 0;
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("INSERT INTO GOODS_RECEIPT VALUES(? ,? ,? ,? ,-1 ,'NULL')");
			st.setString(1, sp.getJanCode());
			st.setString(2, sdf.format(cl.getTime()));
			st.setInt(3, sp.getNyukoPCS());
			st.setInt(4, sp.getUnitPrice());

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
	 * GoodsReceiptテーブル更新メソッド
	 * @param goodsReceipt
	 *
	 * @param GoodsReceiptビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int updateToGoodsReceipt(GoodsReceipt sp, GoodsReceipt goodsReceipt) {
		int line = 0;
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement(
					"UPDATE GOODS_RECEIPT SET NYUKO_PCS = ?, UNIT_PRICE = ? WHERE JAN_CODE = ? AND NYUKO_YMD = ?");
			st.setInt(1, goodsReceipt.getNyukoPCS() +sp.getNyukoPCS());
			st.setInt(2, (goodsReceipt.getNyukoPCS()*goodsReceipt.getUnitPrice()+sp.getNyukoPCS()*sp.getUnitPrice())/(goodsReceipt.getNyukoPCS() +sp.getNyukoPCS()));
			st.setString(3, sp.getJanCode());
			st.setString(4, sdf.format(cl.getTime()));

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
