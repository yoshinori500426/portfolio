package action;

import java.text.DateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_OrderList;
import bean.OrderTable;
import bean.ProductMaster;
import dao.OrderTableDAO;
import dao.ProductMasterDAO;
import tool.Action;

public class OrderListAction extends Action {
	@Override
	@SuppressWarnings("unchecked")
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// ログイン状態確認
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		// セッション切れ処理
		if (loginStatusCheck == 0) {
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			// メッセージ作成
			session.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
			// 画面遷移先登録
			session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
			return "/WEB-INF/main/login.jsp";
		}
		// 各メッセージリセット
		session.setAttribute("alert", null);
		session.setAttribute("message", null);
		session.setAttribute("state", null);
		// 使用DAOインスタンス取得
		ProductMasterDAO pmDAO = new ProductMasterDAO();
		OrderTableDAO otDAO = new OrderTableDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		ProductMaster ProductMaster = null;
		List<ProductMaster> ProductMasterList = null;
		List<G_OrderList> G_OrderListAll = null;
		// テーブル｢OrderTable｣の全レコード取得
		List<OrderTable> OrderTableList = (List<OrderTable>) session.getAttribute("OrderTableList");
		if (OrderTableList == null) {
			OrderTableList = otDAO.searchAll();
			session.setAttribute("OrderTableList", OrderTableList);
		}
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面の入力内容取得
		G_OrderList G_OrderList = new G_OrderList();
		G_OrderList.setProductNo(request.getParameter("productNo"));
		G_OrderList.setProductName(request.getParameter("productName"));
		G_OrderList.setStartDate(request.getParameter("startDate"));
		G_OrderList.setEndtDate(request.getParameter("endtDate"));
		G_OrderList.setAlreadyInStock(request.getParameter("alreadyInStock"));
		G_OrderList.setNotInStock(request.getParameter("notInStock"));
		G_OrderList.setSort(request.getParameter("sort"));
		session.setAttribute("G_OrderList", G_OrderList);

//ここから
//		OrderListTable oList = new OrderListTable();
//		List<OrderTable> list = null;
//
//		String productNo = String.format("%10s", request.getParameter("productNo").replace(" ", "")).replace(" ", "0");
//		if (productNo == null)
//			productNo = "";
//		if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
//			oList.setProductNo(
//					String.format("%10s", request.getParameter("productNo").replace(" ", "")).replace(" ", "0"));
//		}
//
//		String siji = request.getParameter("siji");
//		String sort = null;
//		String sibori = null;
//		String sDate = null;
//		String eDate = null;
//
//		oList.setProductName(request.getParameter("productName"));
//		sort = request.getParameter("sort");
//		sibori = request.getParameter("sibori");
//
//		// 品番に対して品名とリストを表示
//		ProductMaster pro = dao.searchByProNoS(oList.getProductNo());
//		if (pro == null) {
//			request.setAttribute("message", "入力された品番は登録されていません");
//		} else {
//			oList.setProductName(pro.getProductName());
//			oList.setoOrder(odao.execution(oList.getProductNo()));
//			list = odao.execution(productNo);
//		}
//		if (sibori != null) {
//			switch (sibori) {
//			// 未納入
//			case "mika":
//				list = odao.searchEntry(productNo);
//				break;
//			case "nounyuzumi":
//				// 納入済み
//				list = odao.searchNounyusumi(productNo);
//				break;
//			default:
//			}
//		}
//
//		switch (siji) {
//
//		case "searchDate":// 日付に対してリストを並び替え
//
//			if (request.getParameter("endDate") == "" && request.getParameter("startDate") != "") {
//				if (checkDate(request.getParameter("startDate"))) {
//					final String sDate2 = sDate = request.getParameter("startDate").replace("-", "/");
//					list.removeIf(array -> array.getOrderDate().compareTo(sDate2) < 0);
//				} else {
//					request.setAttribute("message", "入力された日付は存在しません");
//				}
//			} else if (request.getParameter("startDate") == "" && request.getParameter("endDate") != "") {
//				if (checkDate(request.getParameter("endDate"))) {
//					final String eDate2 = eDate = request.getParameter("endDate").replace("-", "/");
//					list.removeIf(array -> array.getOrderDate().compareTo(eDate2) > 0);
//				} else {
//					request.setAttribute("message", "入力された日付は存在しません");
//				}
//			} else if (request.getParameter("startDate") != "" && request.getParameter("endDate") != "") {
//				if (checkDate(request.getParameter("startDate")) && (checkDate(request.getParameter("endDate")))) {
//					final String sDate2 = sDate = request.getParameter("startDate").replace("-", "/");
//					final String eDate2 = eDate = request.getParameter("endDate").replace("-", "/");
//					list.removeIf(array -> array.getOrderDate().compareTo(sDate2) < 0);
//					list.removeIf(array -> array.getOrderDate().compareTo(eDate2) > 0);
//				} else {
//					request.setAttribute("message", "入力された日付は存在しません");
//				}
//			}
//			oList.setoOrder(list);
//
//			break;
//
//		case "sortDate":
//
//			Collections.sort(list, new SortOrTaDate());
//
//			break;
//
//		case "sortdueDate":
//
//			Collections.sort(list, new SortOrTaData2());
//
//			break;
//
//		case "sortCount":
//
//			Collections.sort(list, new SortOrTa());
//
//			break;
//
//		case "issue":
//
//			Collections.sort(list, new SortOrTaIssue());
//
//			break;
//
//		case "reset":
//
//			oList = null;
//			sort = null;
//			sibori = null;
//			list = null;
//			siji = null;
//			sDate = null;
//			eDate = null;
//
//			break;
//
//		default:
//
//		}
//
//		if (list != null) {
//			oList.setoOrder(list);
//		}
//
//		request.setAttribute("oList", oList);
//		request.setAttribute("sort", sort);
//		request.setAttribute("sibori", sibori);
//		// 入力された日付を残す
//		request.setAttribute("startDate", sDate);
//		request.setAttribute("endDate", eDate);

		// プルダウン用リスト取得
		ProductMasterList = pmDAO.searchAll();
		session.setAttribute("ProductMasterList", ProductMasterList);
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/orderList.jsp");
		return "/WEB-INF/main/orderList.jsp";
	}

	public static boolean checkDate(String strDate) {
		if (strDate == null || strDate.length() != 10) {
			return false;
		}
		DateFormat format = DateFormat.getDateInstance();
		format.setLenient(false);
		try {
			format.parse(strDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
