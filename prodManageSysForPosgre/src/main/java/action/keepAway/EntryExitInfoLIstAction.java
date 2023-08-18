package action;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.EntryExitInfo;
import bean.ProductMaster;
import dao.EntryExitInfoDAO;
import dao.ProductMasterDAO;
import tool.Action;
import tool.SortEnEx;
import tool.SortEnExDate;

public class EntryExitInfoLIstAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		if (loginStatusCheck == 0) {
			request.setAttribute("message", "セッション切れの為､ログイン画面に移動します｡");
			session.removeAttribute("table");
			session.removeAttribute("table2");
			session.removeAttribute("sort");
			session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
			return "/WEB-INF/main/login.jsp";
		}

		String siji = request.getParameter("siji");
		String productNo = String.format("%10s", request.getParameter("productNo").replace(" ", "")).replace(" ", "0");
		if (productNo == null)
			productNo = "";
		ProductMaster pm = new ProductMaster();
		ProductMasterDAO dao = new ProductMasterDAO();
		EntryExitInfoDAO eDao = new EntryExitInfoDAO();
		String sort = null;
		String kinds = null;
		if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
			pm.setProductNo(String.format("%10s", request.getParameter("productNo").replace(" ", "")).replace(" ", "0"));
		} else {
			return "/WEB-INF/main/entryExitInfoList.jsp";
		}

		switch (siji) {
		case "search"://品番から品名の検索をしてリストを表示
			ProductMaster getCheckinfo = dao.searchByProNo(pm);
			if (getCheckinfo == null) {
				request.setAttribute("message", "入力された品番は登録されていません");
				return "/WEB-INF/main/entryExitInfoList.jsp";
			} else {
				request.setAttribute("product", getCheckinfo);
			}
			List<EntryExitInfo> list = eDao.execution(productNo);
			if (list.isEmpty()) {
				request.setAttribute("message", "入出庫データは０件でした。");
			}
			session.setAttribute("table", list);
			List<EntryExitInfo> list2 = new ArrayList<>(list);
			session.setAttribute("table2", list2);
			return "/WEB-INF/main/entryExitInfoList.jsp";

		case "searchDate"://日付範囲のデータを表示
			if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
				pm.setProductNo(request.getParameter("productNo").replace(" ", ""));
			} else {
				return "/WEB-INF/main/entryExitInfoList.jsp";
			}
			getCheckinfo = dao.searchByProNo(pm);
			if (getCheckinfo == null) {
				request.setAttribute("message", "入力された品番は登録されていません");
			} else {
				request.setAttribute("product", getCheckinfo);
				request.setAttribute("startDate", request.getParameter("startDate").replace("-", "/"));
				request.setAttribute("endDate", request.getParameter("endDate").replace("-", "/"));
			}
			if (request.getParameter("endDate") == "" && request.getParameter("startDate") != "") {
				String sDay = request.getParameter("startDate").replace("-", "/");
				if(checkDate(sDay)) {
				list = (List<EntryExitInfo>) session.getAttribute("table");
				list2 = new ArrayList<>(list);
				list2.removeIf(array -> array.getEnExDate().compareTo(sDay) < 0);

				request.setAttribute("table", list2);
				session.setAttribute("table2", list2);
				}else {
					request.setAttribute("message", "入力された日付は存在しません");
				}
			} else if (request.getParameter("startDate") == "" && request.getParameter("endDate") != "") {
				String eDay = request.getParameter("endDate").replace("-", "/");
				if(checkDate(eDay)) {
				list = (List<EntryExitInfo>) session.getAttribute("table");
				list2 = new ArrayList<>(list);
				list2.removeIf(array -> array.getEnExDate().compareTo(eDay) > 0);

				request.setAttribute("table", list2);
				session.setAttribute("table2", list2);
				}else {
					request.setAttribute("message", "入力された日付は存在しません");
				}
			} else if (request.getParameter("startDate") != "" && request.getParameter("endDate") != "") {
				String sDay = request.getParameter("startDate").replace("-", "/");
				String eDay = request.getParameter("endDate").replace("-", "/");
				if(checkDate(sDay) || checkDate(eDay)) {
				list = (List<EntryExitInfo>) session.getAttribute("table");
				list2 = new ArrayList<>(list);
				list2.removeIf(array -> array.getEnExDate().compareTo(sDay) < 0);
				list2.removeIf(array -> array.getEnExDate().compareTo(eDay) > 0);
				request.setAttribute("table", list2);
				session.setAttribute("table2", list2);
				}else {
					request.setAttribute("message", "入力された日付は存在しません");
				}
			}
			sort = request.getParameter("sort");
			session.setAttribute("sort", sort);
			kinds = request.getParameter("kinds");
			request.setAttribute("kinds", kinds);
			return "/WEB-INF/main/entryExitInfoList.jsp";

		case "entry"://入庫のみを表示
			if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
				pm.setProductNo(request.getParameter("productNo").replace(" ", ""));
			} else {
				return "/WEB-INF/main/entryExitInfoList.jsp";
			}
			getCheckinfo = dao.searchByProNo(pm);
			if (getCheckinfo == null) {
				request.setAttribute("message", "入力された品番は登録されていません");
			} else {
				request.setAttribute("product", getCheckinfo);
			}
			productNo = request.getParameter("productNo").replace(" ", "");
			if (productNo == null)
				productNo = "";

			eDao = new EntryExitInfoDAO();
			list = eDao.searchEntry(productNo);

			session.setAttribute("table", list);
			sort = request.getParameter("sort");
			session.setAttribute("sort", sort);
			kinds = request.getParameter("kinds");
			request.setAttribute("kinds", kinds);

			return "/WEB-INF/main/entryExitInfoList.jsp";

		case "issue"://出庫のみを表示
			if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
				pm.setProductNo(request.getParameter("productNo").replace(" ", ""));
			} else {
				return "/WEB-INF/main/entryExitInfoList.jsp";
			}
			getCheckinfo = dao.searchByProNo(pm);
			if (getCheckinfo == null) {
				request.setAttribute("message", "入力された品番は登録されていません");
			} else {
				request.setAttribute("product", getCheckinfo);
			}
			productNo = request.getParameter("productNo").replace(" ", "");
			if (productNo == null)
				productNo = "";

			eDao = new EntryExitInfoDAO();
			list = eDao.searchIssue(productNo);

			session.setAttribute("table", list);
			sort = request.getParameter("sort");
			session.setAttribute("sort", sort);
			kinds = request.getParameter("kinds");
			request.setAttribute("kinds", kinds);
			return "/WEB-INF/main/entryExitInfoList.jsp";

		case "all"://全件を表示
			if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
				pm.setProductNo(request.getParameter("productNo").replace(" ", ""));
			} else {
				return "/WEB-INF/main/entryExitInfoList.jsp";
			}
			getCheckinfo = dao.searchByProNo(pm);
			if (getCheckinfo == null) {
				request.setAttribute("message", "入力された品番は登録されていません");
			} else {
				request.setAttribute("product", getCheckinfo);
			}
			productNo = request.getParameter("productNo").replace(" ", "");
			if (productNo == null)
				productNo = "";

			eDao = new EntryExitInfoDAO();
			list = eDao.execution(productNo);

			session.setAttribute("table", list);
			sort = request.getParameter("sort");
			session.setAttribute("sort", sort);
			kinds = request.getParameter("kinds");
			request.setAttribute("kinds", kinds);
			return "/WEB-INF/main/entryExitInfoList.jsp";

		case "sortDate"://日付昇順で並び替え
			if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
				pm.setProductNo(request.getParameter("productNo").replace(" ", ""));
			} else {
				return "/WEB-INF/main/entryExitInfoList.jsp";
			}
			getCheckinfo = dao.searchByProNo(pm);
			if (getCheckinfo == null) {
				request.setAttribute("message", "入力された品番は登録されていません");
			} else {
				request.setAttribute("product", getCheckinfo);
				request.setAttribute("startDate", request.getParameter("startDate").replace("-", "/"));
				request.setAttribute("endDate", request.getParameter("endDate").replace("-", "/"));
			}
			if (request.getParameter("startDate") == "" && request.getParameter("endDate") == "") {
				list = (List<EntryExitInfo>) session.getAttribute("table");
				Collections.sort(list, new SortEnExDate());
				session.setAttribute("table", list);
			} else {
				list2 = (List<EntryExitInfo>) session.getAttribute("table2");
				Collections.sort(list2, new SortEnExDate());
				session.setAttribute("table", list2);
			}
			sort = request.getParameter("sort");
			request.setAttribute("sort", sort);
			kinds = request.getParameter("kinds");
			request.setAttribute("kinds", kinds);

			return "/WEB-INF/main/entryExitInfoList.jsp";

		case "sortCount"://数量昇順で並び替え
			if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
				pm.setProductNo(request.getParameter("productNo").replace(" ", ""));
			} else {
				return "/WEB-INF/main/entryExitInfoList.jsp";
			}
			getCheckinfo = dao.searchByProNo(pm);
			if (getCheckinfo == null) {
				request.setAttribute("message", "入力された品番は登録されていません");
			} else {
				request.setAttribute("product", getCheckinfo);
				request.setAttribute("startDate", request.getParameter("startDate").replace("-", "/"));
				request.setAttribute("endDate", request.getParameter("endDate").replace("-", "/"));
			}
			if (request.getParameter("startDate") == "" && request.getParameter("endDate") == "") {
				list = (List<EntryExitInfo>) session.getAttribute("table");
				Collections.sort(list, new SortEnEx());
				session.setAttribute("table", list);
			} else {
				list2 = (List<EntryExitInfo>) session.getAttribute("table2");
				Collections.sort(list2, new SortEnEx());
				session.setAttribute("table", list2);
			}

			sort = request.getParameter("sort");
			request.setAttribute("sort", sort);
			kinds = request.getParameter("kinds");
			request.setAttribute("kinds", kinds);

			return "/WEB-INF/main/entryExitInfoList.jsp";

		case "reset"://画面リセット
			session.removeAttribute("table");
			session.removeAttribute("table2");
			request.removeAttribute("sort");
			session.removeAttribute("sort");
			request.setAttribute("clear","clear");
			return "/WEB-INF/main/entryExitInfoList.jsp";
		default:
		}
		return "/WEB-INF/main/entryExitInfoList.jsp";
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
		}catch (Exception e) {
			return false;
		}
	}
}