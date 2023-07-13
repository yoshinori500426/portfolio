package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Earnings;
import bean.EarningsEtc;
import bean.GoodsIssue;

public class GoodsIssueDAO extends DAO {
	/**
	 * GoodsIssueテーブル参照メソッド
	 * @param GoodsIssueビーン
	 * @return GoodsIssueビーン 「null：失敗」「インスタンス有：成功」
	 */
	public GoodsIssue searchGoodsIssueByJanCodeAndSyukoYMD(GoodsIssue goodsissue) {
		GoodsIssue gi = null;
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Connection con = getConnection();

			PreparedStatement st = con.prepareStatement("SELECT * FROM GOODS_ISSUE WHERE JAN_CODE = ?");
			st.setString(1, goodsissue.getJanCode());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if (rs.getString("SYUKO_YMD").equals(sdf.format(cl.getTime()))) {
					gi = new GoodsIssue();
					gi.setJanCode(rs.getString("JAN_CODE"));
					gi.setSyukoYMD(rs.getString("SYUKO_YMD"));
					gi.setSyukoPCS(rs.getInt("SYUKO_PCS"));
					gi.setEtc(rs.getString("ETC"));
				}
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return gi;
	}

	/**
	 * GoodsIssueテーブル参照メソッド(売上画面リスト作成用)
	 * @param response
	 * @param request
	 * @param GoodsIssueビーン
	 * @return GoodsIssueビーン 「null：失敗」「インスタンス有：成功」←出庫リストを元にしたテーブル用リスト
	 *               リストの合計個数/合計額は、セッション属性の「totalSyukoPcs」「totalSyukoPrice」で渡す
	 */
	public List<Earnings>  searchGoodsIssueBySyukoYM(HttpServletRequest request, HttpServletResponse response, EarningsEtc earningsEtc) {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// 戻り値用の変数(リスト型)を宣言
		List<Earnings> earningsList = new ArrayList<>();

		//年月処理
		String yymm = "";
		if (earningsEtc.getMonth().length()==1) {
			yymm = earningsEtc.getYear() +"/0" + earningsEtc.getMonth() + "%";
		}else if(earningsEtc.getMonth().length()==2){
			yymm = earningsEtc.getYear() +"/" + earningsEtc.getMonth() + "%";
		}

		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			//年・月に応じた、以下の2種の処理を行う
			//➀リスト取得
			st = con.prepareStatement("SELECT "
																+ "GI.JAN_CODE, "
																+ "GI.SYUKO_YMD, "
																+ "GI.SYUKO_PCS, "
																+ "PD.UNIT_PRICE, "
																+ "GI.SYUKO_PCS*PD.UNIT_PRICE AS \"TOTAL_PRICE\" "
														+ "FROM GOODS_ISSUE GI "
																+ "INNER JOIN PRODUCT_DRINK PD "
																+ "ON GI.JAN_CODE = PD.JAN_CODE "
														+ "WHERE SYUKO_YMD LIKE ?");
			st.setString(1, yymm);
			rs = st.executeQuery();
			while (rs.next()) {
				Earnings earnings = new Earnings();
				earnings.setJanCode(rs.getString("JAN_CODE"));
				earnings.setSyukoYMD(rs.getString("SYUKO_YMD"));
				earnings.setSyukoPCS(rs.getInt("SYUKO_PCS"));
				earnings.setUnitPrice(rs.getInt("UNIT_PRICE"));
				earnings.setTotalPrice(rs.getInt("TOTAL_PRICE"));
				earningsList.add(earnings);
			}

			//➁リストの合計個数/金額取得
			st = con.prepareStatement("SELECT "
																	+ "SUM(SYUKO_PCS) AS \"SUM_SYUKO_PCS\", "
																	+ "SUM(TOTAL_PRICE) AS \"SUM_TOTAL_PRICE\" "
															+ "FROM (SELECT "
																					+ "GI.SYUKO_PCS, "
																					+ "GI.SYUKO_PCS*PD.UNIT_PRICE AS \"TOTAL_PRICE\" "
																			+ "FROM GOODS_ISSUE GI "
																					+ "INNER JOIN PRODUCT_DRINK PD "
																					+ "ON GI.JAN_CODE = PD.JAN_CODE "
																			+ "WHERE SYUKO_YMD LIKE ?)");
			st.setString(1, yymm);
			rs = st.executeQuery();
			//リストから取得した、合計個数/金額を参照渡ししたインスタンス「earningsEtc」に格納し、
			//セッション属性「earningsEtc」に格納する
			while (rs.next()) {
				earningsEtc.setSumSyukoPcs(rs.getInt("SUM_SYUKO_PCS"));
				earningsEtc.setSumTotalPirce(rs.getInt("SUM_TOTAL_PRICE"));
			}
			session.setAttribute("earningsEtc", earningsEtc);

			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return earningsList;
	}

	/**
	 * GoodsIssueテーブル登録メソッド
	 * @param GoodsIssueビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insertToGoodsIssue(GoodsIssue sp) {
		int line = 0;
		//登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Connection con = getConnection();
			PreparedStatement st = con
					.prepareStatement("INSERT INTO GOODS_ISSUE VALUES(? , ? , ? ,'NULL')");
			st.setString(1, sp.getJanCode());
			st.setString(2, sdf.format(cl.getTime()));
			st.setInt(3, sp.getSyukoPCS());

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
	 * GoodsIssueテーブル更新メソッド
	 * @param goodsIssue
	 * @param GoodsIssueビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int updateToGoodsIssue(GoodsIssue sp, GoodsIssue goodsIssue) {
		int line = 0;
		try {
			Connection con = getConnection();
			PreparedStatement st = con
					.prepareStatement(
							"UPDATE GOODS_ISSUE SET SYUKO_PCS = ? WHERE JAN_CODE = ? AND SYUKO_YMD = ?");
			st.setInt(1, goodsIssue.getSyukoPCS() + sp.getSyukoPCS());
			st.setString(2, sp.getJanCode());
			st.setString(3, sp.getSyukoYMD());

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
