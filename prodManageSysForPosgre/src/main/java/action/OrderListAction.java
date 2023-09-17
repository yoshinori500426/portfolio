package action;

import java.util.ArrayList;
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
		List<G_OrderList> G_OrderListAllBySearchConditions = null;
		// テーブル｢OrderTable｣の全レコード取得
		List<OrderTable> OrderTableListWithProductNameAndSupplierName = (List<OrderTable>) session
				.getAttribute("OrderTableListWithProductNameAndSupplierName");
		if (OrderTableListWithProductNameAndSupplierName == null) {
			OrderTableListWithProductNameAndSupplierName = otDAO.searchAllWithProductNameAndSupplierName();
			session.setAttribute("OrderTableListWithProductNameAndSupplierName", OrderTableListWithProductNameAndSupplierName);
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
		G_OrderList.setStartDate(request.getParameter("startDate") == null ? "" : request.getParameter("startDate"));
		G_OrderList.setEndDate(request.getParameter("endDate") == null ? "" : request.getParameter("endDate"));
		G_OrderList.setAlreadyInStock(request.getParameter("alreadyInStock") == null ? "" : request.getParameter("alreadyInStock"));
		G_OrderList.setNotInStock(request.getParameter("notInStock") == null ? "" : request.getParameter("notInStock"));
		G_OrderList.setSort(request.getParameter("sort") == null ? "" : request.getParameter("sort"));
		session.setAttribute("G_OrderList", G_OrderList);
		switch (toAction) {
		case "searchProductMasterList":
			// ｢ProductMasterList｣取得のみの為､caseでは処理を行わない
			break;
		case "searchProductNo":
			// ProductNoのクリア動作
			if (G_OrderList.getProductNo().isEmpty()) {
				// チェックボックスの初期化
				G_OrderList = new G_OrderList();
				G_OrderList.setAlreadyInStock("alreadyInStock");
				G_OrderList.setNotInStock("notInStock");
				session.setAttribute("ProductMaster", null);
				session.setAttribute("G_OrderListAllBySearchConditions", null);
				break;
			} else if (!G_OrderList.getProductNo().isEmpty()) {
				// テーブル検索
				ProductMaster = pmDAO.searchByProNo(G_OrderList);
				if (ProductMaster == null) {
					session.setAttribute("message", "入力値に該当する品番は存在しません。\\n入力内容を確認ください。");
					G_OrderList = new G_OrderList();
					G_OrderList.setProductNo(request.getParameter("productNo"));
					// チェックボックスの初期化
					G_OrderList.setAlreadyInStock("alreadyInStock");
					G_OrderList.setNotInStock("notInStock");
				} else if (ProductMaster != null) {
					G_OrderList.setProductName(ProductMaster.getProductName());
					G_OrderListAllBySearchConditions = searchBySearchConditions(G_OrderList, request);
				}
				session.setAttribute("ProductMaster", ProductMaster);
				session.setAttribute("G_OrderListAllBySearchConditions", G_OrderListAllBySearchConditions);
			}
			session.setAttribute("G_OrderList", G_OrderList);
			break;
		case "dummy":
			session.setAttribute("message", null);
			break;
		case "cancel":
		default:
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			// チェックボックスの初期化
			G_OrderList = new G_OrderList();
			G_OrderList.setAlreadyInStock("alreadyInStock");
			G_OrderList.setNotInStock("notInStock");
			session.setAttribute("G_OrderList", G_OrderList);
			break;
		}

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

	/**
	 * テーブル｢OrderTable｣のListから条件に合致するレコードを抽出するメソッド
	 * →セッション属性にUpしたListから条件に合致するレコードを抽出するメソッド
	 * 
	 * @param String productNo, HttpServletRequest request
	 * @return List<G_OrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	@SuppressWarnings("unchecked")
	public List<G_OrderList> searchBySearchConditions(String productNo, String startDate, String endDate,
			String alreadyInStock, String notInStock, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// テーブル｢OrderTable｣の全レコード取得
		List<OrderTable> OrderTableListWithProductNameAndSupplierName = (List<OrderTable>) session.getAttribute("OrderTableListWithProductNameAndSupplierName");
		if (OrderTableListWithProductNameAndSupplierName == null) {
			OrderTableDAO otDAO = new OrderTableDAO();
			OrderTableListWithProductNameAndSupplierName = otDAO.searchAllWithProductNameAndSupplierName();
			session.setAttribute("OrderTableListWithProductNameAndSupplierName", OrderTableListWithProductNameAndSupplierName);
		}
		// レコード抽出に必要な変数宣言
		Boolean judgeProductNo = false, judgeStartAndEndDate = false, judgeFinFlg = false;
		String StartDate = "", EndDate = "";
		// 抽出レコード格納用変数宣言
		G_OrderList G_OrderList = null;
		List<G_OrderList> G_OrderListAllBySearchConditions = null;
		// テーブル｢OrderTable｣の全レコードから､条件に合致するレコードを抽出
		for (OrderTable OrderTable : OrderTableListWithProductNameAndSupplierName) {
			// 品番確認
			judgeProductNo = (OrderTable.getProductNo().equals(productNo));
			// 発注日確認
			StartDate = (startDate == "" ? "1970-01-01" : startDate);
			EndDate = (endDate == "" ? "9999-12-31" : endDate);
			judgeStartAndEndDate = (OrderTable.getOrderDate().compareTo(StartDate) >= 0 && OrderTable.getOrderDate().compareTo(EndDate) <= 0);
			// 入荷済み/未入荷確認
			if (alreadyInStock.equals("alreadyInStock") && notInStock.equals("notInStock")) {
				judgeFinFlg = true;
			} else if (alreadyInStock.equals("alreadyInStock") && !notInStock.equals("notInStock")) {
				judgeFinFlg = OrderTable.getFinFlg().equals("1");
			} else if (!alreadyInStock.equals("alreadyInStock") && notInStock.equals("notInStock")) {
				judgeFinFlg = OrderTable.getFinFlg().equals("0");
			} else if (!alreadyInStock.equals("alreadyInStock") && !notInStock.equals("notInStock")) {
				judgeFinFlg = false;
			}
			// 抽出処理
			if ((judgeProductNo && judgeStartAndEndDate && judgeFinFlg) == true) {
				if (G_OrderListAllBySearchConditions == null) {
					G_OrderListAllBySearchConditions = new ArrayList<>();
				}
				G_OrderList = new G_OrderList();
				G_OrderList.setProductNo(OrderTable.getProductNo());
				G_OrderList.setProductName(OrderTable.getProductName());
				G_OrderList.setStartDate(startDate);
				G_OrderList.setEndDate(endDate);
				G_OrderList.setAlreadyInStock(alreadyInStock);
				G_OrderList.setNotInStock(notInStock);
				G_OrderList.setOrderDate(OrderTable.getOrderDate());
				G_OrderList.setDeliveryDate(OrderTable.getDeliveryDate());
				G_OrderList.setDueDate(OrderTable.getDueDate());
				G_OrderList.setOrderQty(OrderTable.getOrderQty());
				G_OrderList.setSupplierNo(OrderTable.getSupplierNo());
				G_OrderList.setSupplierName(OrderTable.getSupplierName());
				G_OrderListAllBySearchConditions.add(G_OrderList);
			}
		}
		return G_OrderListAllBySearchConditions;
	}

	/**
	 * テーブル｢OrderTable｣のListから条件に合致するレコードを抽出するメソッド
	 * →セッション属性にUpしたListから条件に合致するレコードを抽出するメソッド
	 * 
	 * @param G_OrderList G_OrderList, HttpServletRequest request
	 * @return List<G_OrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	public List<G_OrderList> searchBySearchConditions(G_OrderList G_OrderList, HttpServletRequest request) {
		String productNo = G_OrderList.getProductNo();
		String startDate = G_OrderList.getStartDate();
		String endDate = G_OrderList.getEndDate();
		String alreadyInStock = G_OrderList.getAlreadyInStock();
		String notInStock = G_OrderList.getNotInStock();
		return searchBySearchConditions(productNo, startDate, endDate, alreadyInStock, notInStock, request);
	}
}
