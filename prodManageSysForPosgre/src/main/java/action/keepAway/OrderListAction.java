package action;

import java.text.DateFormat;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.OrderListTable;
import bean.OrderTable;
import bean.ProductMaster;
import dao.OrderTableDAO;
import dao.ProductMasterDAO;
import tool.Action;
import tool.SortOrTa;
import tool.SortOrTaData2;
import tool.SortOrTaDate;
import tool.SortOrTaIssue;

public class OrderListAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProductMasterDAO dao = new ProductMasterDAO();
		OrderTableDAO odao = new OrderTableDAO();
		OrderListTable oList = new OrderListTable();
		List<OrderTable> list = null;

		String productNo = String.format("%10s", request.getParameter("productNo").replace(" ", "")).replace(" ", "0");
		if (productNo == null)
			productNo = "";
		if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
			oList.setProductNo(
					String.format("%10s", request.getParameter("productNo").replace(" ", "")).replace(" ", "0"));
		}

		String siji = request.getParameter("siji");
		String sort = null;
		String sibori = null;
		String sDate = null;
		String eDate = null;

		oList.setProductName(request.getParameter("productName"));
		sort = request.getParameter("sort");
		sibori = request.getParameter("sibori");

		//品番に対して品名とリストを表示
		ProductMaster pro = dao.searchByProNoS(oList.getProductNo());
		if (pro == null) {
			request.setAttribute("message", "入力された品番は登録されていません");
		} else {
			oList.setProductName(pro.getProductName());
			oList.setoOrder(odao.execution(oList.getProductNo()));
			list = odao.execution(productNo);
		}
		if (sibori != null) {
			switch (sibori) {
			//未納入
			case "mika":
				list = odao.searchEntry(productNo);
				break;
			case "nounyuzumi":
				//納入済み
				list = odao.searchNounyusumi(productNo);
				break;
			default:
			}
		}

		switch (siji) {

		case "searchDate"://日付に対してリストを並び替え

			if (request.getParameter("endDate") == "" && request.getParameter("startDate") != "") {
				if (checkDate(request.getParameter("startDate"))) {
					final String sDate2 = sDate = request.getParameter("startDate").replace("-", "/");
					list.removeIf(array -> array.getOrderDate().compareTo(sDate2) < 0);
				} else {
					request.setAttribute("message", "入力された日付は存在しません");
				}
			} else if (request.getParameter("startDate") == "" && request.getParameter("endDate") != "") {
				if (checkDate(request.getParameter("endDate"))) {
					final String eDate2 = eDate = request.getParameter("endDate").replace("-", "/");
					list.removeIf(array -> array.getOrderDate().compareTo(eDate2) > 0);
				} else {
					request.setAttribute("message", "入力された日付は存在しません");
				}
			} else if (request.getParameter("startDate") != "" && request.getParameter("endDate") != "") {
				if (checkDate(request.getParameter("startDate")) && (checkDate(request.getParameter("endDate")))) {
					final String sDate2 = sDate = request.getParameter("startDate").replace("-", "/");
					final String eDate2 = eDate = request.getParameter("endDate").replace("-", "/");
					list.removeIf(array -> array.getOrderDate().compareTo(sDate2) < 0);
					list.removeIf(array -> array.getOrderDate().compareTo(eDate2) > 0);
				} else {
					request.setAttribute("message", "入力された日付は存在しません");
				}
			}
			oList.setoOrder(list);

			break;

		case "sortDate":

			Collections.sort(list, new SortOrTaDate());

			break;

		case "sortdueDate":

			Collections.sort(list, new SortOrTaData2());

			break;

		case "sortCount":

			Collections.sort(list, new SortOrTa());

			break;

		case "issue":

			Collections.sort(list, new SortOrTaIssue());

			break;

		case "reset":

			oList = null;
			sort = null;
			sibori = null;
			list = null;
			siji = null;
			sDate = null;
			eDate = null;

			break;

		default:

		}

		if (list != null) {
			oList.setoOrder(list);
		}

		request.setAttribute("oList", oList);
		request.setAttribute("sort", sort);
		request.setAttribute("sibori", sibori);
		//入力された日付を残す
		request.setAttribute("startDate", sDate);
		request.setAttribute("endDate", eDate);

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
