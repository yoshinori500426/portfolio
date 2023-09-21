package action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
		List<G_OrderList> G_OrderListSearchByConditions = null;
		List<G_OrderList> G_OrderListSortByCondition = null;
		// テーブル｢OrderTable｣の全レコード取得
		List<OrderTable> OrderTableListWithProNameAndSupName = (List<OrderTable>) session
				.getAttribute("OrderTableListWithProNameAndSupName");
		if (OrderTableListWithProNameAndSupName == null) {
			OrderTableListWithProNameAndSupName = otDAO.searchAllWithProductNameAndSupplierName();
			session.setAttribute("OrderTableListWithProNameAndSupName", OrderTableListWithProNameAndSupName);
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
		G_OrderList.setInStock(request.getParameter("inStock") == null ? "" : request.getParameter("inStock"));
		G_OrderList.setNotInStock(request.getParameter("notInStock") == null ? "" : request.getParameter("notInStock"));
		G_OrderList.setAscendingDescending(
				request.getParameter("ascendingDescending") == null ? "" : request.getParameter("ascendingDescending"));
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
				G_OrderList.setInStock("inStock");
				G_OrderList.setNotInStock("notInStock");
				session.setAttribute("ProductMaster", null);
				session.setAttribute("G_OrderListSearchByConditions", null);
				session.setAttribute("G_OrderListSortByCondition", null);
				break;
			} else if (!G_OrderList.getProductNo().isEmpty()) {
				// テーブル検索
				ProductMaster = pmDAO.searchByProNo(G_OrderList);
				if (ProductMaster == null) {
					session.setAttribute("message", "入力値に該当する品番は存在しません。\\n入力内容を確認ください。");
					G_OrderList = new G_OrderList();
					G_OrderList.setProductNo(request.getParameter("productNo"));
					// チェックボックスの初期化
					G_OrderList.setInStock("inStock");
					G_OrderList.setNotInStock("notInStock");
				} else if (ProductMaster != null) {
					G_OrderList.setProductName(ProductMaster.getProductName());
					G_OrderList.setInStock("inStock");
					G_OrderList.setNotInStock("notInStock");
					G_OrderListSearchByConditions = searchByConditions(G_OrderList, request);
				}
				session.setAttribute("ProductMaster", ProductMaster);
				session.setAttribute("G_OrderListSearchByConditions", G_OrderListSearchByConditions);
				session.setAttribute("G_OrderListSortByCondition", G_OrderListSearchByConditions);
			}
			session.setAttribute("G_OrderList", G_OrderList);
			break;
		case "searchByConditions":
			G_OrderListSearchByConditions = searchByConditions(G_OrderList, request);
			session.setAttribute("G_OrderListSearchByConditions", G_OrderListSearchByConditions);
			// 引き続き､メソッド｢sortByConditions(G_OrderList, request);｣で処理を行う為､｢break;｣を設けない
		case "sortByConditions":
			G_OrderListSortByCondition = sortByConditions(G_OrderList, request);
			session.setAttribute("G_OrderListSortByCondition", G_OrderListSortByCondition);
			break;
		case "dummy":
			session.setAttribute("message", null);
			break;
		case "conditionsClear":
			G_OrderList = new G_OrderList();
			G_OrderList.setProductNo(request.getParameter("productNo"));
			G_OrderList.setProductName(request.getParameter("productName"));
			G_OrderList.setInStock("inStock");
			G_OrderList.setNotInStock("notInStock");
			session.setAttribute("G_OrderList", G_OrderList);
			G_OrderListSearchByConditions = searchByConditions(G_OrderList, request);
			session.setAttribute("G_OrderListSearchByConditions", G_OrderListSearchByConditions);
			session.setAttribute("G_OrderListSortByCondition", G_OrderListSearchByConditions);
			break;
		case "cancel":
		default:
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			// チェックボックスの初期化
			G_OrderList = new G_OrderList();
			G_OrderList.setInStock("inStock");
			G_OrderList.setNotInStock("notInStock");
			session.setAttribute("G_OrderList", G_OrderList);
			break;
		}
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
	 * @param String productNo, String startDate, String endDate, String inStock,
	 *               String notInStock, HttpServletRequest request
	 * @return List<G_OrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	@SuppressWarnings("unchecked")
	public List<G_OrderList> searchByConditions(String productNo, String startDate, String endDate, String inStock,
			String notInStock, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 抽出対象のレコード取得(=テーブル｢OrderTable｣の全レコード取得)
		List<OrderTable> OrderTableListWithProNameAndSupName = (List<OrderTable>) session
				.getAttribute("OrderTableListWithProNameAndSupName");
		if (OrderTableListWithProNameAndSupName == null) {
			OrderTableDAO otDAO = new OrderTableDAO();
			OrderTableListWithProNameAndSupName = otDAO.searchAllWithProductNameAndSupplierName();
			session.setAttribute("OrderTableListWithProNameAndSupName", OrderTableListWithProNameAndSupName);
		}
		// レコード抽出に必要な変数宣言
		Boolean judgeProductNo = false, judgeStartAndEndDate = false, judgeFinFlg = false;
		String StartDate = "", EndDate = "";
		// 抽出レコード格納用変数宣言
		G_OrderList G_OrderList = null;
		List<G_OrderList> G_OrderListSearchByConditions = null;
		// テーブル｢OrderTable｣の全レコードから､条件に合致するレコードを抽出
		for (OrderTable OrderTable : OrderTableListWithProNameAndSupName) {
			// 品番確認
			judgeProductNo = (OrderTable.getProductNo().equals(productNo));
			// 発注日確認
			StartDate = ((startDate == "" || startDate == null)? "1970-01-01" : startDate);
			EndDate = ((endDate == "" || endDate ==null)? "9999-12-31" : endDate);
			judgeStartAndEndDate = (new MainAction().dateChangeForHTML(OrderTable.getOrderDate())
					.compareTo(StartDate) >= 0
					&& new MainAction().dateChangeForHTML(OrderTable.getOrderDate()).compareTo(EndDate) <= 0);
			// 入荷済み/未入荷確認
			if (inStock.equals("inStock") && notInStock.equals("notInStock")) {
				judgeFinFlg = true;
			} else if (inStock.equals("inStock") && !notInStock.equals("notInStock")) {
				judgeFinFlg = OrderTable.getFinFlg().equals("1");
			} else if (!inStock.equals("inStock") && notInStock.equals("notInStock")) {
				judgeFinFlg = OrderTable.getFinFlg().equals("0");
			} else if (!inStock.equals("inStock") && !notInStock.equals("notInStock")) {
				judgeFinFlg = false;
			}
			// 抽出処理
			if ((judgeProductNo && judgeStartAndEndDate && judgeFinFlg) == true) {
				if (G_OrderListSearchByConditions == null) {
					G_OrderListSearchByConditions = new ArrayList<>();
				}
				G_OrderList = new G_OrderList();
				G_OrderList.setProductNo(OrderTable.getProductNo());
				G_OrderList.setProductName(OrderTable.getProductName());
				G_OrderList.setStartDate(startDate);
				G_OrderList.setEndDate(endDate);
				G_OrderList.setInStock(inStock);
				G_OrderList.setNotInStock(notInStock);
				G_OrderList.setOrderDate(OrderTable.getOrderDate());
				G_OrderList.setDeliveryDate(OrderTable.getDeliveryDate());
				G_OrderList.setDueDate(OrderTable.getDueDate());
				G_OrderList.setOrderQty(OrderTable.getOrderQty());
				G_OrderList.setSupplierNo(OrderTable.getSupplierNo());
				G_OrderList.setSupplierName(OrderTable.getSupplierName());
				G_OrderListSearchByConditions.add(G_OrderList);
			}
		}
		return G_OrderListSearchByConditions;
	}

	/**
	 * テーブル｢OrderTable｣のListから条件に合致するレコードを抽出するメソッド
	 * →セッション属性にUpしたListから条件に合致するレコードを抽出するメソッド
	 * 
	 * @param G_OrderList G_OrderList, HttpServletRequest request
	 * @return List<G_OrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	public List<G_OrderList> searchByConditions(G_OrderList G_OrderList, HttpServletRequest request) {
		String productNo = G_OrderList.getProductNo();
		String startDate = G_OrderList.getStartDate();
		String endDate = G_OrderList.getEndDate();
		String inStock = G_OrderList.getInStock();
		String notInStock = G_OrderList.getNotInStock();
		return searchByConditions(productNo, startDate, endDate, inStock, notInStock, request);
	}

	/**
	 * テーブル｢OrderTable｣のListをStreamインタフェースのAPIを元に並び替えるメソッド
	 * →セッション属性にUpしたListの並び替えを行うメソッド
	 * 
	 * @param String sort, HttpServletRequest request
	 * @return List<G_OrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	@SuppressWarnings("unchecked")
	public List<G_OrderList> sortByConditions(String ascendingDescending, String sort, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 並び替え対象のList取得
		List<G_OrderList> G_OrderListSearchByConditions = (List<G_OrderList>) session
				.getAttribute("G_OrderListSearchByConditions");
		if (G_OrderListSearchByConditions == null) {
			G_OrderList G_OrderList = (G_OrderList) session.getAttribute("G_OrderList");
			G_OrderListSearchByConditions = searchByConditions(G_OrderList, request);
			session.setAttribute("G_OrderListSearchByConditions", G_OrderListSearchByConditions);
		}
		// 並び替えレコード格納用変数宣言
		List<G_OrderList> G_OrderListSortByCondition = null;
		// 並び替え対象Listにレコードがない場合､ソート処理を行わない
		if (G_OrderListSearchByConditions == null || G_OrderListSearchByConditions.size() == 0) {
			return G_OrderListSortByCondition;
		}
		// 並び替え
		switch (sort) {
		case "supplierName":
			if (ascendingDescending.equals("ascending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_OrderList::getSupplierName)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_OrderList::getSupplierName).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "orderDate":
			if (ascendingDescending.equals("ascending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_OrderList::getOrderDate)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_OrderList::getOrderDate).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "orderQty":
			if (ascendingDescending.equals("ascending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparingInt(G_OrderList::getOrderQty)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparingInt(G_OrderList::getOrderQty).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "deliveryDate":
			if (ascendingDescending.equals("ascending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_OrderList::getDeliveryDate)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_OrderList::getDeliveryDate).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "dueDate":
			if (ascendingDescending.equals("ascending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_OrderList::getDueDate)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_OrderListSortByCondition = G_OrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_OrderList::getDueDate).reversed()).collect(Collectors.toList());
			}
			break;
		default:
			// 並び替えしない
			G_OrderListSortByCondition = G_OrderListSearchByConditions;
			break;
		}
		return G_OrderListSortByCondition;
	}

	/**
	 * テーブル｢OrderTable｣のListをStreamインタフェースのAPIを元に並び替えるメソッド
	 * →セッション属性にUpしたListの並び替えを行うメソッド
	 * 
	 * @param G_OrderList G_OrderList, HttpServletRequest request
	 * @return List<G_OrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	public List<G_OrderList> sortByConditions(G_OrderList G_OrderList, HttpServletRequest request) {
		String ascendingDescending = G_OrderList.getAscendingDescending();
		String sort = G_OrderList.getSort();
		return sortByConditions(ascendingDescending, sort, request);
	}
}
