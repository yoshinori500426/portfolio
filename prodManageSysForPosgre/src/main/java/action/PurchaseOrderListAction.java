package action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_PurchaseOrderList;
import bean.ProductMaster;
import bean.PurchaseOrder;
import dao.ProductMasterDAO;
import dao.PurchaseOrderDAO;
import tool.Action;

public class PurchaseOrderListAction extends Action {
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
			return "/WEB-INF/main/login.jsp";
		}
		// 各メッセージリセット
		session.setAttribute("alert", null);
		session.setAttribute("message", null);
		session.setAttribute("state", null);
		// 使用DAOインスタンス取得
		ProductMasterDAO pmDAO = new ProductMasterDAO();
		PurchaseOrderDAO poDAO = new PurchaseOrderDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		ProductMaster ProductMaster = null;
		List<ProductMaster> ProductMasterList = null;
		List<G_PurchaseOrderList> G_PurchaseOrderListSearchByConditions = null;
		List<G_PurchaseOrderList> G_PurchaseOrderListSortByCondition = null;
		// テーブル｢PurchaseOrder｣の全レコード取得
		List<PurchaseOrder> PurchaseOrderListWithProNameAndCusName = (List<PurchaseOrder>) session
				.getAttribute("PurchaseOrderListWithProNameAndCusName");
		if (PurchaseOrderListWithProNameAndCusName == null) {
			PurchaseOrderListWithProNameAndCusName = poDAO.searchAllWithProductNameAndCustomerName();
			session.setAttribute("PurchaseOrderListWithProNameAndCusName", PurchaseOrderListWithProNameAndCusName);
		}
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面の入力内容取得
		G_PurchaseOrderList G_PurchaseOrderList = new G_PurchaseOrderList();
		G_PurchaseOrderList.setProductNo(request.getParameter("productNo"));
		G_PurchaseOrderList.setProductName(request.getParameter("productName"));
		G_PurchaseOrderList.setStartDate(request.getParameter("startDate") == null ? "" : request.getParameter("startDate"));
		G_PurchaseOrderList.setEndDate(request.getParameter("endDate") == null ? "" : request.getParameter("endDate"));
		G_PurchaseOrderList.setShipped(request.getParameter("shipped") == null ? "" : request.getParameter("shipped"));
		G_PurchaseOrderList.setNotShipped(request.getParameter("notShipped") == null ? "" : request.getParameter("notShipped"));
		G_PurchaseOrderList.setAscendingDescending(
				request.getParameter("ascendingDescending") == null ? "" : request.getParameter("ascendingDescending"));
		G_PurchaseOrderList.setSort(request.getParameter("sort") == null ? "" : request.getParameter("sort"));
		session.setAttribute("G_PurchaseOrderList", G_PurchaseOrderList);
		switch (toAction) {
		case "searchProductNo":
			// ProductNoのクリア動作
			if (G_PurchaseOrderList.getProductNo().isEmpty()) {
				// チェックボックスの初期化
				G_PurchaseOrderList = new G_PurchaseOrderList();
				G_PurchaseOrderList.setShipped("shipped");
				G_PurchaseOrderList.setNotShipped("notShipped");
				session.setAttribute("ProductMaster", null);
				session.setAttribute("G_PurchaseOrderListSearchByConditions", null);
				session.setAttribute("G_PurchaseOrderListSortByCondition", null);
				break;
			} else if (!G_PurchaseOrderList.getProductNo().isEmpty()) {
				// テーブル検索
				ProductMaster = pmDAO.searchByProNo(G_PurchaseOrderList);
				if (ProductMaster == null) {
					session.setAttribute("message", "入力値に該当する品番は存在しません。\\n入力内容を確認ください。");
					G_PurchaseOrderList = new G_PurchaseOrderList();
					G_PurchaseOrderList.setProductNo(request.getParameter("productNo"));
					// チェックボックスの初期化
					G_PurchaseOrderList.setShipped("shipped");
					G_PurchaseOrderList.setNotShipped("notShipped");
				} else if (ProductMaster != null) {
					G_PurchaseOrderList.setProductName(ProductMaster.getProductName());
					G_PurchaseOrderList.setShipped("shipped");
					G_PurchaseOrderList.setNotShipped("notShipped");
					G_PurchaseOrderListSearchByConditions = searchByConditions(G_PurchaseOrderList, request);
				}
				session.setAttribute("ProductMaster", ProductMaster);
				session.setAttribute("G_PurchaseOrderListSearchByConditions", G_PurchaseOrderListSearchByConditions);
				session.setAttribute("G_PurchaseOrderListSortByCondition", G_PurchaseOrderListSearchByConditions);
			}
			session.setAttribute("G_PurchaseOrderList", G_PurchaseOrderList);
			break;
		case "searchByConditions":
			G_PurchaseOrderListSearchByConditions = searchByConditions(G_PurchaseOrderList, request);
			session.setAttribute("G_PurchaseOrderListSearchByConditions", G_PurchaseOrderListSearchByConditions);
			// 引き続き､メソッド｢sortByConditions(G_PurchaseOrderList, request);｣で処理を行う為､｢break;｣を設けない
		case "sortByConditions":
			G_PurchaseOrderListSortByCondition = sortByConditions(G_PurchaseOrderList, request);
			session.setAttribute("G_PurchaseOrderListSortByCondition", G_PurchaseOrderListSortByCondition);
			break;
		case "dummy":
			session.setAttribute("message", null);
			break;
		case "conditionsClear":
			G_PurchaseOrderList = new G_PurchaseOrderList();
			G_PurchaseOrderList.setProductNo(request.getParameter("productNo"));
			G_PurchaseOrderList.setProductName(request.getParameter("productName"));
			G_PurchaseOrderList.setShipped("shipped");
			G_PurchaseOrderList.setNotShipped("notShipped");
			session.setAttribute("G_PurchaseOrderList", G_PurchaseOrderList);
			G_PurchaseOrderListSearchByConditions = searchByConditions(G_PurchaseOrderList, request);
			session.setAttribute("G_PurchaseOrderListSearchByConditions", G_PurchaseOrderListSearchByConditions);
			session.setAttribute("G_PurchaseOrderListSortByCondition", G_PurchaseOrderListSearchByConditions);
			break;
		case "cancel":
		default:
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			// チェックボックスの初期化
			G_PurchaseOrderList = new G_PurchaseOrderList();
			G_PurchaseOrderList.setShipped("shipped");
			G_PurchaseOrderList.setNotShipped("notShipped");
			session.setAttribute("G_PurchaseOrderList", G_PurchaseOrderList);
			break;
		}
		// プルダウン用リスト取得
		ProductMasterList = pmDAO.searchAll();
		session.setAttribute("ProductMasterList", ProductMasterList);
		return "/WEB-INF/main/purchaseOrderList.jsp";
	}

	/**
	 * テーブル｢PurchaseOrder｣のListから条件に合致するレコードを抽出するメソッド
	 * →セッション属性にUpしたListから条件に合致するレコードを抽出するメソッド
	 * 
	 * @param String productNo, String startDate, String endDate, String shipped,
	 *               String notShipped, HttpServletRequest request
	 * @return List<G_PurchaseOrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	@SuppressWarnings("unchecked")
	public List<G_PurchaseOrderList> searchByConditions(String productNo, String startDate, String endDate, String shipped,
			String notShipped, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 抽出対象のレコード取得(=テーブル｢PurchaseOrder｣の全レコード取得)
		List<PurchaseOrder> PurchaseOrderListWithProNameAndCusName = (List<PurchaseOrder>) session
				.getAttribute("PurchaseOrderListWithProNameAndCusName");
		if (PurchaseOrderListWithProNameAndCusName == null) {
			PurchaseOrderDAO poDAO = new PurchaseOrderDAO();
			PurchaseOrderListWithProNameAndCusName = poDAO.searchAllWithProductNameAndCustomerName();
			session.setAttribute("PurchaseOrderListWithProNameAndCusName", PurchaseOrderListWithProNameAndCusName);
		}
		// レコード抽出に必要な変数宣言
		Boolean judgeProductNo = false, judgeStartAndEndDate = false, judgeFinFlg = false;
		String StartDate = ((startDate == "" || startDate == null) ? "1970-01-01" : startDate);
		String EndDate = ((endDate == "" || endDate == null) ? "9999-12-31" : endDate);
		// 抽出レコード格納用変数宣言
		G_PurchaseOrderList G_PurchaseOrderList = null;
		List<G_PurchaseOrderList> G_PurchaseOrderListSearchByConditions = null;
		// テーブル｢PurchaseOrder｣の全レコードから､条件に合致するレコードを抽出
		for (PurchaseOrder PurchaseOrder : PurchaseOrderListWithProNameAndCusName) {
			// 品番確認
			judgeProductNo = (PurchaseOrder.getProductNo().equals(productNo));
			// 発注日確認
			judgeStartAndEndDate = (new MainAction().dateChangeForHTML(PurchaseOrder.getOrderDate())
					.compareTo(StartDate) >= 0
					&& new MainAction().dateChangeForHTML(PurchaseOrder.getOrderDate()).compareTo(EndDate) <= 0);
			// 入荷済み/未入荷確認
			if (shipped.equals("shipped") && notShipped.equals("notShipped")) {
				judgeFinFlg = true;
			} else if (shipped.equals("shipped") && !notShipped.equals("notShipped")) {
				judgeFinFlg = PurchaseOrder.getFinFlg().equals("1");
			} else if (!shipped.equals("shipped") && notShipped.equals("notShipped")) {
				judgeFinFlg = PurchaseOrder.getFinFlg().equals("0");
			} else if (!shipped.equals("shipped") && !notShipped.equals("notShipped")) {
				judgeFinFlg = false;
			}
			// 抽出処理
			if ((judgeProductNo && judgeStartAndEndDate && judgeFinFlg) == true) {
				if (G_PurchaseOrderListSearchByConditions == null) {
					G_PurchaseOrderListSearchByConditions = new ArrayList<>();
				}
				G_PurchaseOrderList = new G_PurchaseOrderList();
				G_PurchaseOrderList.setProductNo(PurchaseOrder.getProductNo());
				G_PurchaseOrderList.setProductName(PurchaseOrder.getProductName());
				G_PurchaseOrderList.setStartDate(startDate);
				G_PurchaseOrderList.setEndDate(endDate);
				G_PurchaseOrderList.setShipped(shipped);
				G_PurchaseOrderList.setNotShipped(notShipped);
				G_PurchaseOrderList.setOrderDate(PurchaseOrder.getOrderDate());
				G_PurchaseOrderList.setDeliveryDate(PurchaseOrder.getDeliveryDate());
				G_PurchaseOrderList.setShipDate(PurchaseOrder.getShipDate());
				G_PurchaseOrderList.setOrderQty(PurchaseOrder.getOrderQty());
				G_PurchaseOrderList.setCustomerNo(PurchaseOrder.getCustomerNo());
				G_PurchaseOrderList.setCustomerName(PurchaseOrder.getCustomerName());
				G_PurchaseOrderListSearchByConditions.add(G_PurchaseOrderList);
			}
		}
		return G_PurchaseOrderListSearchByConditions;
	}

	/**
	 * テーブル｢PurchaseOrder｣のListから条件に合致するレコードを抽出するメソッド
	 * →セッション属性にUpしたListから条件に合致するレコードを抽出するメソッド
	 * 
	 * @param G_PurchaseOrderList G_PurchaseOrderList, HttpServletRequest request
	 * @return List<G_PurchaseOrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	public List<G_PurchaseOrderList> searchByConditions(G_PurchaseOrderList G_PurchaseOrderList, HttpServletRequest request) {
		String productNo = G_PurchaseOrderList.getProductNo();
		String startDate = G_PurchaseOrderList.getStartDate();
		String endDate = G_PurchaseOrderList.getEndDate();
		String shipped = G_PurchaseOrderList.getShipped();
		String notShipped = G_PurchaseOrderList.getNotShipped();
		return searchByConditions(productNo, startDate, endDate, shipped, notShipped, request);
	}

	/**
	 * テーブル｢PurchaseOrder｣のListをStreamインタフェースのAPIを元に並び替えるメソッド
	 * →セッション属性にUpしたListの並び替えを行うメソッド
	 * 
	 * @param String sort, HttpServletRequest request
	 * @return List<G_PurchaseOrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	@SuppressWarnings("unchecked")
	public List<G_PurchaseOrderList> sortByConditions(String ascendingDescending, String sort, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 並び替え対象のList取得
		List<G_PurchaseOrderList> G_PurchaseOrderListSearchByConditions = (List<G_PurchaseOrderList>) session
				.getAttribute("G_PurchaseOrderListSearchByConditions");
		if (G_PurchaseOrderListSearchByConditions == null) {
			G_PurchaseOrderList G_PurchaseOrderList = (G_PurchaseOrderList) session.getAttribute("G_PurchaseOrderList");
			G_PurchaseOrderListSearchByConditions = searchByConditions(G_PurchaseOrderList, request);
			session.setAttribute("G_PurchaseOrderListSearchByConditions", G_PurchaseOrderListSearchByConditions);
		}
		// 並び替えレコード格納用変数宣言
		List<G_PurchaseOrderList> G_PurchaseOrderListSortByCondition = null;
		// 並び替え対象Listにレコードがない場合､ソート処理を行わない
		if (G_PurchaseOrderListSearchByConditions == null || G_PurchaseOrderListSearchByConditions.size() == 0) {
			return G_PurchaseOrderListSortByCondition;
		}
		// 並び替え
		switch (sort) {
		case "customerName":
			if (ascendingDescending.equals("ascending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_PurchaseOrderList::getCustomerName)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_PurchaseOrderList::getCustomerName).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "orderDate":
			if (ascendingDescending.equals("ascending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_PurchaseOrderList::getOrderDate)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_PurchaseOrderList::getOrderDate).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "orderQty":
			if (ascendingDescending.equals("ascending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparingInt(G_PurchaseOrderList::getOrderQty)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparingInt(G_PurchaseOrderList::getOrderQty).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "deliveryDate":
			if (ascendingDescending.equals("ascending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_PurchaseOrderList::getDeliveryDate)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_PurchaseOrderList::getDeliveryDate).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "shipDate":
			if (ascendingDescending.equals("ascending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_PurchaseOrderList::getShipDate)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_PurchaseOrderList::getShipDate).reversed()).collect(Collectors.toList());
			}
			break;
		default:
			// 並び替えしない
			G_PurchaseOrderListSortByCondition = G_PurchaseOrderListSearchByConditions;
			break;
		}
		return G_PurchaseOrderListSortByCondition;
	}

	/**
	 * テーブル｢PurchaseOrder｣のListをStreamインタフェースのAPIを元に並び替えるメソッド
	 * →セッション属性にUpしたListの並び替えを行うメソッド
	 * 
	 * @param G_PurchaseOrderList G_PurchaseOrderList, HttpServletRequest request
	 * @return List<G_PurchaseOrderList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	public List<G_PurchaseOrderList> sortByConditions(G_PurchaseOrderList G_PurchaseOrderList, HttpServletRequest request) {
		String ascendingDescending = G_PurchaseOrderList.getAscendingDescending();
		String sort = G_PurchaseOrderList.getSort();
		return sortByConditions(ascendingDescending, sort, request);
	}
}
