package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.MrpBean;
import bean.ProductMaster;
import dao.DAO;
import dao.ProductMasterDAO;
import tool.DateUtils;

@WebServlet("/action.MrpAction")
public class MrpAction extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//画面からの情報を取得する。
		String siji = request.getParameter("siji");
		String pro_no = request.getParameter("pro_no");
		String lot = request.getParameter("lot");
		String rt = request.getParameter("rt");
		String baseStock = request.getParameter("baseStock");
		List<MrpBean> mrp_list = null;
		//sijiの内容にしたがって処理を分岐する。
		switch (siji) {
		case "hin": //品番が入力されたとき。　必要情報を取得する。
			ProductMasterDAO pdao = new ProductMasterDAO();
			ProductMaster resultPM = null;
			try {
				resultPM = pdao.searchByProNoS(pro_no);
				if (resultPM == null) {
					request.setAttribute("message", "品番調べよう！！！！");
				} else {
					//在庫受発注情報を取得
					mrp_list = beforMrp(pro_no);
					mrp_list = stockCul(mrp_list, lot, rt, baseStock,siji);

					//計算用の値を送っておく
					lot = String.valueOf(resultPM.getLot());
					rt = String.valueOf(resultPM.getLeadtime());
					baseStock = String.valueOf(resultPM.getBaseStock());
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			break;
		case "mrp"://所要量ボタンがクリックされたとき。
			//各テーブルからデータ取得、ソートまで行う。
			mrp_list = beforMrp(pro_no);
			//在庫を計算する。&& 発注
			mrp_list = stockCul(mrp_list, lot, rt, baseStock,siji);
			//結果をソート
			mrp_list = mrp_list.stream().sorted(Comparator.comparing(MrpBean::getJuhaDate))
					.collect(Collectors.toList());
		}
		//画面へセット
		request.setAttribute("pro_no", pro_no);
		request.setAttribute("list", mrp_list);
		request.setAttribute("lot", lot);
		request.setAttribute("rt", rt);
		request.setAttribute("baseStock", baseStock);
		//画面遷移
		request.getRequestDispatcher("batch_menu.jsp").forward(request, response);
	}

	/**
	 * 在庫数量取得,受注情報取得,発注情報取得,集めた情報を日付でソートする
	 * @param pro_no
	 * @return List<MrpBean>
	 */
	private List<MrpBean> beforMrp(String pro_no) {
		List<MrpBean> mrp_list = null;
		DAO dao = new DAO();
		try {
			Connection con = dao.getConnection();
			mrp_list = new ArrayList<>();
			//在庫数量取得
			mrp_list = zaikoget(pro_no, mrp_list, con);
			//受注情報取得
			mrp_list = jutyuget(pro_no, mrp_list, con);
			//発注情報取得
			mrp_list = orderget(pro_no, mrp_list, con);
			//集めた情報を日付でソートする
			mrp_list = mrp_list.stream().sorted(Comparator.comparing(MrpBean::getJuhaDate))
					.collect(Collectors.toList());
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return mrp_list;
	}

	/**
	 * List<MrpBean>内の情報を基にオーダーを決める。
	 * @param mrp_list
	 * @param lot
	 * @param rt
	 * @param baseStock
	 * @param siji
	 * @return List<MrpBean>
	 */
	private List<MrpBean> stockCul(List<MrpBean> mrp_list, String lot, String rt, String baseStock,String siji) {
		int lot_v = Integer.parseInt(lot);
		int rt_v = Integer.parseInt(rt);
		int base = Integer.parseInt(baseStock);
		ArrayList<MrpBean> mrp_send_List = new ArrayList<MrpBean>(mrp_list);
		//現在庫取得
		int zaiko = mrp_list.get(0).getZaiko();
		int zaiko_sum = zaiko;
		int nextZaiko=0;

		//在庫計算
		for (int i = 0; i < mrp_list.size(); i++) {
			zaiko_sum = zaiko_sum + mrp_list.get(i).getOr_qty() -
					mrp_list.get(i).getPo_qty() - base;
			if (zaiko_sum < 0) {
				MrpBean mb = new MrpBean();
				//受注がなければ今日からＲＴ後の日付を入れる
				if (mrp_list.get(i).getPo_qty() == 0) {
					mb.setJuhaDate(DateUtils.getFormatDateWithSlash(DateUtils.addDay(DateUtils.getDate(), rt_v)));
				} else {//受注があれば出荷日のRT前の日付をセットする。
					Date cl = DateUtils.toDateWithSlash(mrp_list.get(i).getJuhaDate());
					mb.setJuhaDate(DateUtils.getFormatDateWithSlash(DateUtils.addDay(cl, rt_v * -1)));
				}
				//発注数量を計算する。
				int su = Math.abs(zaiko_sum / lot_v);
				int amari = Math.abs(zaiko_sum % lot_v);
				if (amari > 0) {
					mb.setOr_qty(su * lot_v + lot_v);
				} else {
					mb.setOr_qty(su * lot_v);
				}
				zaiko_sum = zaiko_sum + mb.getOr_qty();
				nextZaiko = zaiko_sum + mrp_list.get(i).getPo_qty();
				//sijiがmrpの時のみ新しいオーダーをリストに追加
				if(siji.equals("mrp")) {
				mb.setPo_od_No("xxxxx");
				mb.setZaiko(nextZaiko);
				mrp_send_List.add(mb);
				}

				mrp_list.get(i).setZaiko(nextZaiko - mrp_list.get(i).getPo_qty());
				zaiko_sum = nextZaiko - mrp_list.get(i).getPo_qty();
			}else {

				mrp_list.get(i).setZaiko(zaiko_sum);

			}


		}

		return mrp_send_List;
	}

	/**
	 * List<MrpBean> に現在庫数量を取得して、追加する。
	 * @param pro_no
	 * @param mrp
	 * @param con
	 * @return　List<MrpBean>
	 * @throws Exception
	 */
	private List<MrpBean> zaikoget(String pro_no, List<MrpBean> mrp, Connection con) throws Exception {
		PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM PURODUCT_STOCK WHERE STOCK_INFO_DATE=TO_CHAR(SYSDATE,'YYYY/MM') AND PRODUCT_NO=?");
		ps.setString(1, pro_no);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		MrpBean mrpB = new MrpBean();
		mrpB.setJuhaDate("0000/00/00");
		mrpB.setZaiko(rs.getInt("STOCK_QTY"));
		mrp.add(mrpB);
		}
		return mrp;
	}

	/**
	 * 受注情報（品番＆完了フラグ＝0）を　List<MrpBean>に追加
	 * @param pro_no
	 * @param mrp
	 * @param con
	 * @return　List<MrpBean>
	 * @throws Exception
	 */
	private List<MrpBean> jutyuget(String pro_no, List<MrpBean> mrp, Connection con) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM PURCHASE_ORDER WHERE PRODUCT_NO=? AND FIN_FLG=0");
		ps.setString(1, pro_no);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			MrpBean mrpB = new MrpBean();
			mrpB.setJuhaDate(rs.getString("DELIVERY_DATE"));
			mrpB.setPo_od_No(rs.getString("PO_NO"));
			mrpB.setPo_qty(rs.getInt("ORDER_QTY"));
			mrp.add(mrpB);
		}
		return mrp;
	}

	/**
	 * 注文情報（品番＆完了フラグ＝0）をList<MrpBean>に追加
	 * @param pro_no
	 * @param mrp
	 * @param con
	 * @return
	 * @throws Exception
	 */
	private List<MrpBean> orderget(String pro_no, List<MrpBean> mrp, Connection con) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM ORDER_TABLE WHERE PRODUCT_NO=? AND FIN_FLG=0");
		ps.setString(1, pro_no);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			MrpBean mrpB = new MrpBean();
			mrpB.setJuhaDate(rs.getString("DELIVERY_DATE"));
			mrpB.setPo_od_No(rs.getString("ORDER_NO"));
			mrpB.setOr_qty(rs.getInt("ORDER_QTY"));
			mrp.add(mrpB);
		}
		return mrp;
	}

}
