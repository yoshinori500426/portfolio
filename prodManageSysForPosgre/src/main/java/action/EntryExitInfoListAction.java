package action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.EntryExitInfo;
import bean.G_EntryExitInfoList;
import bean.ProductMaster;
import dao.EntryExitInfoDAO;
import dao.ProductMasterDAO;
import tool.Action;

public class EntryExitInfoListAction extends Action {
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
		EntryExitInfoDAO eeiDAO = new EntryExitInfoDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		ProductMaster ProductMaster = null;
		List<ProductMaster> ProductMasterList = null;
		List<G_EntryExitInfoList> G_EntryExitInfoListSearchByConditions = null;
		List<G_EntryExitInfoList> G_EntryExitInfoListSortByCondition = null;
		// テーブル｢EntryExitInfo｣の全レコード取得
		List<EntryExitInfo> EntryExitInfoList = (List<EntryExitInfo>) session.getAttribute("EntryExitInfoList");
		if (EntryExitInfoList == null) {
			EntryExitInfoList = eeiDAO.searchAll();
			session.setAttribute("EntryExitInfoList", EntryExitInfoList);
		}
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面の入力内容取得
		G_EntryExitInfoList G_EntryExitInfoList = new G_EntryExitInfoList();
		G_EntryExitInfoList.setProductNo(request.getParameter("productNo"));
		G_EntryExitInfoList.setProductName(request.getParameter("productName"));
		G_EntryExitInfoList
				.setStartDate(request.getParameter("startDate") == null ? "" : request.getParameter("startDate"));
		G_EntryExitInfoList.setEndDate(request.getParameter("endDate") == null ? "" : request.getParameter("endDate"));
		G_EntryExitInfoList.setStockIn(request.getParameter("stockIn") == null ? "" : request.getParameter("stockIn"));
		G_EntryExitInfoList
				.setStockOut(request.getParameter("stockOut") == null ? "" : request.getParameter("stockOut"));
		G_EntryExitInfoList.setAscendingDescending(
				request.getParameter("ascendingDescending") == null ? "" : request.getParameter("ascendingDescending"));
		G_EntryExitInfoList.setSort(request.getParameter("sort") == null ? "" : request.getParameter("sort"));
		session.setAttribute("G_EntryExitInfoList", G_EntryExitInfoList);
		switch (toAction) {
		case "searchProductMasterList":
			// ｢ProductMasterList｣取得のみの為､caseでは処理を行わない
			break;
		case "searchProductNo":
			// ProductNoのクリア動作
			if (G_EntryExitInfoList.getProductNo().isEmpty()) {
				// チェックボックスの初期化
				G_EntryExitInfoList = new G_EntryExitInfoList();
				G_EntryExitInfoList.setStockIn("stockIn");
				G_EntryExitInfoList.setStockOut("stockOut");
				session.setAttribute("ProductMaster", null);
				session.setAttribute("G_EntryExitInfoListSearchByConditions", null);
				session.setAttribute("G_EntryExitInfoListSortByCondition", null);
				break;
			} else if (!G_EntryExitInfoList.getProductNo().isEmpty()) {
				// テーブル検索
				ProductMaster = pmDAO.searchByProNo(G_EntryExitInfoList);
				if (ProductMaster == null) {
					session.setAttribute("message", "入力値に該当する品番は存在しません。\\n入力内容を確認ください。");
					G_EntryExitInfoList = new G_EntryExitInfoList();
					G_EntryExitInfoList.setProductNo(request.getParameter("productNo"));
					// チェックボックスの初期化
					G_EntryExitInfoList.setStockIn("stockIn");
					G_EntryExitInfoList.setStockOut("stockOut");
				} else if (ProductMaster != null) {
					G_EntryExitInfoList.setProductName(ProductMaster.getProductName());
					G_EntryExitInfoList.setStockIn("stockIn");
					G_EntryExitInfoList.setStockOut("stockOut");
					G_EntryExitInfoListSearchByConditions = searchByConditions(G_EntryExitInfoList, request);
				}
				session.setAttribute("ProductMaster", ProductMaster);
				session.setAttribute("G_EntryExitInfoListSearchByConditions", G_EntryExitInfoListSearchByConditions);
				session.setAttribute("G_EntryExitInfoListSortByCondition", G_EntryExitInfoListSearchByConditions);
			}
			session.setAttribute("G_EntryExitInfoList", G_EntryExitInfoList);
			break;
		case "searchByConditions":
			G_EntryExitInfoListSearchByConditions = searchByConditions(G_EntryExitInfoList, request);
			session.setAttribute("G_EntryExitInfoListSearchByConditions", G_EntryExitInfoListSearchByConditions);
			// 引き続き､メソッド｢sortByConditions(G_EntryExitInfoList,
			// request);｣で処理を行う為､｢break;｣を設けない
		case "sortByConditions":
			G_EntryExitInfoListSortByCondition = sortByConditions(G_EntryExitInfoList, request);
			session.setAttribute("G_EntryExitInfoListSortByCondition", G_EntryExitInfoListSortByCondition);
			break;
		case "dummy":
			session.setAttribute("message", null);
			break;
		case "conditionsClear":
			G_EntryExitInfoList = new G_EntryExitInfoList();
			G_EntryExitInfoList.setProductNo(request.getParameter("productNo"));
			G_EntryExitInfoList.setProductName(request.getParameter("productName"));
			G_EntryExitInfoList.setStockIn("stockIn");
			G_EntryExitInfoList.setStockOut("stockOut");
			session.setAttribute("G_EntryExitInfoList", G_EntryExitInfoList);
			G_EntryExitInfoListSearchByConditions = searchByConditions(G_EntryExitInfoList, request);
			session.setAttribute("G_EntryExitInfoListSearchByConditions", G_EntryExitInfoListSearchByConditions);
			session.setAttribute("G_EntryExitInfoListSortByCondition", G_EntryExitInfoListSearchByConditions);
			break;
		case "cancel":
		default:
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			// チェックボックスの初期化
			G_EntryExitInfoList = new G_EntryExitInfoList();
			G_EntryExitInfoList.setStockIn("stockIn");
			G_EntryExitInfoList.setStockOut("stockOut");
			session.setAttribute("G_EntryExitInfoList", G_EntryExitInfoList);
			break;
		}
		// プルダウン用リスト取得
		ProductMasterList = pmDAO.searchAll();
		session.setAttribute("ProductMasterList", ProductMasterList);
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/entryExitInfoList.jsp");
		return "/WEB-INF/main/entryExitInfoList.jsp";
	}

	/**
	 * テーブル｢EntryExitInfo｣のListから条件に合致するレコードを抽出するメソッド
	 * →セッション属性にUpしたListから条件に合致するレコードを抽出するメソッド
	 * 
	 * @param String productNo, String startDate, String endDate, String stockIn,
	 *               String stockOut, HttpServletRequest request
	 * @return List<G_EntryExitInfoList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	@SuppressWarnings("unchecked")
	public List<G_EntryExitInfoList> searchByConditions(String productNo, String startDate, String endDate,
			String stockIn, String stockOut, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 抽出対象のレコード取得(=テーブル｢EntryExitInfo｣の全レコード取得)
		List<EntryExitInfo> EntryExitInfoList = (List<EntryExitInfo>) session.getAttribute("EntryExitInfoList");
		if (EntryExitInfoList == null) {
			EntryExitInfoDAO eeiDAO = new EntryExitInfoDAO();
			EntryExitInfoList = eeiDAO.searchAll();
			session.setAttribute("EntryExitInfoList", EntryExitInfoList);
		}
		// レコード抽出に必要な変数宣言
		Boolean judgeProductNo = false, judgeStartAndEndDate = false, judgeStockInOut = false;
		String StartDate = ((startDate == "" || startDate == null) ? "1970-01-01" : startDate);
		String EndDate = ((endDate == "" || endDate == null) ? "9999-12-31" : endDate);
		// 抽出レコード格納用変数宣言
		G_EntryExitInfoList G_EntryExitInfoList = null;
		List<G_EntryExitInfoList> G_EntryExitInfoListSearchByConditions = null;
		// テーブル｢EntryExitInfo｣の全レコードから､条件に合致するレコードを抽出
		for (EntryExitInfo EntryExitInfo : EntryExitInfoList) {
			// 品番確認
			judgeProductNo = (EntryExitInfo.getProductNo().equals(productNo));
			// 発注日確認
			judgeStartAndEndDate = (new MainAction().dateChangeForHTML(EntryExitInfo.getEnExDate())
					.compareTo(StartDate) >= 0
					&& new MainAction().dateChangeForHTML(EntryExitInfo.getEnExDate()).compareTo(EndDate) <= 0);
			// 入庫/出庫確認
			if (stockIn.equals("stockIn") && stockOut.equals("stockOut")) {
				judgeStockInOut = true;
			} else if (stockIn.equals("stockIn") && !stockOut.equals("stockOut")) {
				judgeStockInOut = EntryExitInfo.getNyukoQty() > 0;
			} else if (!stockIn.equals("stockIn") && stockOut.equals("stockOut")) {
				judgeStockInOut = EntryExitInfo.getSyukoQty() > 0;
			} else if (!stockIn.equals("stockIn") && !stockOut.equals("stockOut")) {
				judgeStockInOut = false;
			}
			// 抽出処理
			if ((judgeProductNo && judgeStartAndEndDate && judgeStockInOut) == true) {
				if (G_EntryExitInfoListSearchByConditions == null) {
					G_EntryExitInfoListSearchByConditions = new ArrayList<>();
				}
				G_EntryExitInfoList = new G_EntryExitInfoList();
				G_EntryExitInfoList.setProductNo(EntryExitInfo.getProductNo());
				G_EntryExitInfoList.setStartDate(startDate);
				G_EntryExitInfoList.setEndDate(endDate);
				G_EntryExitInfoList.setStockIn(stockIn);
				G_EntryExitInfoList.setStockOut(stockOut);
				G_EntryExitInfoList.setEnExDate(new MainAction().dateChangeForDB(EntryExitInfo.getEnExDate()));
				if (EntryExitInfo.getNyukoQty() == 0 && EntryExitInfo.getSyukoQty() > 0) {
					G_EntryExitInfoList.setStockInOut("出庫");
				} else if (EntryExitInfo.getNyukoQty() > 0 && EntryExitInfo.getSyukoQty() == 0) {
					G_EntryExitInfoList.setStockInOut("入庫");
				}
				G_EntryExitInfoList.setNyukoSyukoQty(EntryExitInfo.getNyukoQty() + EntryExitInfo.getSyukoQty());
				G_EntryExitInfoList.setReason(EntryExitInfo.getReason());
				G_EntryExitInfoListSearchByConditions.add(G_EntryExitInfoList);
			}
		}
		return G_EntryExitInfoListSearchByConditions;
	}

	/**
	 * テーブル｢EntryExitInfo｣のListから条件に合致するレコードを抽出するメソッド
	 * →セッション属性にUpしたListから条件に合致するレコードを抽出するメソッド
	 * 
	 * @param G_EntryExitInfoList G_EntryExitInfoList, HttpServletRequest request
	 * @return List<G_EntryExitInfoList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	public List<G_EntryExitInfoList> searchByConditions(G_EntryExitInfoList G_EntryExitInfoList,
			HttpServletRequest request) {
		String productNo = G_EntryExitInfoList.getProductNo();
		String startDate = G_EntryExitInfoList.getStartDate();
		String endDate = G_EntryExitInfoList.getEndDate();
		String stockIn = G_EntryExitInfoList.getStockIn();
		String stockOut = G_EntryExitInfoList.getStockOut();
		return searchByConditions(productNo, startDate, endDate, stockIn, stockOut, request);
	}

	/**
	 * テーブル｢EntryExitInfo｣のListをStreamインタフェースのAPIを元に並び替えるメソッド
	 * →セッション属性にUpしたListの並び替えを行うメソッド
	 * 
	 * @param String sort, HttpServletRequest request
	 * @return List<G_EntryExitInfoList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	@SuppressWarnings("unchecked")
	public List<G_EntryExitInfoList> sortByConditions(String ascendingDescending, String sort,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 並び替え対象のList取得
		List<G_EntryExitInfoList> G_EntryExitInfoListSearchByConditions = (List<G_EntryExitInfoList>) session
				.getAttribute("G_EntryExitInfoListSearchByConditions");
		if (G_EntryExitInfoListSearchByConditions == null) {
			G_EntryExitInfoList G_EntryExitInfoList = (G_EntryExitInfoList) session.getAttribute("G_EntryExitInfoList");
			G_EntryExitInfoListSearchByConditions = searchByConditions(G_EntryExitInfoList, request);
			session.setAttribute("G_EntryExitInfoListSearchByConditions", G_EntryExitInfoListSearchByConditions);
		}
		// 並び替えレコード格納用変数宣言
		List<G_EntryExitInfoList> G_EntryExitInfoListSortByCondition = null;
		// 並び替え対象Listにレコードがない場合､ソート処理を行わない
		if (G_EntryExitInfoListSearchByConditions == null || G_EntryExitInfoListSearchByConditions.size() == 0) {
			return G_EntryExitInfoListSortByCondition;
		}
		// 並び替え
		switch (sort) {
		case "enExDate":
			if (ascendingDescending.equals("ascending")) {
				G_EntryExitInfoListSortByCondition = G_EntryExitInfoListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_EntryExitInfoList::getEnExDate)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_EntryExitInfoListSortByCondition = G_EntryExitInfoListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_EntryExitInfoList::getEnExDate).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "stockInOut":
			if (ascendingDescending.equals("ascending")) {
				G_EntryExitInfoListSortByCondition = G_EntryExitInfoListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_EntryExitInfoList::getStockInOut)).collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_EntryExitInfoListSortByCondition = G_EntryExitInfoListSearchByConditions.stream()
						.sorted(Comparator.comparing(G_EntryExitInfoList::getStockInOut).reversed())
						.collect(Collectors.toList());
			}
			break;
		case "nyukoSyukoQty":
			if (ascendingDescending.equals("ascending")) {
				G_EntryExitInfoListSortByCondition = G_EntryExitInfoListSearchByConditions.stream()
						.sorted(Comparator.comparingInt(G_EntryExitInfoList::getNyukoSyukoQty))
						.collect(Collectors.toList());
			} else if (ascendingDescending.equals("descending")) {
				G_EntryExitInfoListSortByCondition = G_EntryExitInfoListSearchByConditions.stream()
						.sorted(Comparator.comparingInt(G_EntryExitInfoList::getNyukoSyukoQty).reversed())
						.collect(Collectors.toList());
			}
			break;
		default:
			// 並び替えしない
			G_EntryExitInfoListSortByCondition = G_EntryExitInfoListSearchByConditions;
			break;
		}
		return G_EntryExitInfoListSortByCondition;
	}

	/**
	 * テーブル｢EntryExitInfo｣のListをStreamインタフェースのAPIを元に並び替えるメソッド
	 * →セッション属性にUpしたListの並び替えを行うメソッド
	 * 
	 * @param G_EntryExitInfoList G_EntryExitInfoList, HttpServletRequest request
	 * @return List<G_EntryExitInfoList> 「.size()==0：失敗」「.size()>0：成功」
	 */
	public List<G_EntryExitInfoList> sortByConditions(G_EntryExitInfoList G_EntryExitInfoList,
			HttpServletRequest request) {
		String ascendingDescending = G_EntryExitInfoList.getAscendingDescending();
		String sort = G_EntryExitInfoList.getSort();
		return sortByConditions(ascendingDescending, sort, request);
	}
}
