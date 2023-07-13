package action;

import java.text.DateFormat;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ProductMaster;
import bean.PurchaseListOrder;
import bean.PurchaseOrder;
import dao.ProductMasterDAO;
import dao.PurchaseOrderDAO;
import tool.Action;
import tool.purchaseOrderListTool;
import tool.purchaseOrderListToolCheck;
import tool.purchaseOrderListToolClient;
import tool.purchaseOrderListToolDate;
import tool.purchaseOrderListToolMash;

public class PurchaseOrderListAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProductMasterDAO proDao = new ProductMasterDAO();

		PurchaseOrderDAO OrderDao = new PurchaseOrderDAO();

		PurchaseListOrder puList = new PurchaseListOrder();

		List<PurchaseOrder> list = null;

		String productNo = String.format("%10s", request.getParameter("productNo").replace(" ", "")).replace(" ", "0");
		if (productNo == null)
			productNo = "";
		if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
			puList.setProductNo(
					String.format("%10s", request.getParameter("productNo").replace(" ", "")).replace(" ", "0"));
		}

		String nakami = request.getParameter("toAction");
		String sort = null;
		String kinds = null;
		String sibori = null;
		String sDate = null;
		String eDate = null;

		puList.setProductName(request.getParameter("productName"));
		sort = request.getParameter("sort");
		kinds = request.getParameter("kinds");
		sibori = request.getParameter("sibori");

		//品番に対して品名とリストを表示
		ProductMaster pro = proDao.searchBySireNo(puList.getProductNo());
		if (pro == null) {
			request.setAttribute("message", "入力された品番は登録されていません");
		} else {
			puList.setProductName(pro.getProductName());
			puList.setpOrder(OrderDao.PurchaseOrderList(puList.getProductNo()));
			list = OrderDao.PurchaseOrderList(productNo);
		}

		if (sibori != null) {
			//nullで無ければ動く
			switch (sibori) {
			//未納入
			case "mika":
				list = OrderDao.PurchaseOrderShipNot(productNo);
				break;
			case "nouryuzumi":
				//納入済み
				list = OrderDao.PurchaseOrderShipNotNull(productNo);
				break;
			default:
			}
		}
		switch (nakami) {
		//日付に対してリストを並び替え
		case "startdate":
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

			puList.setpOrder(list);

			break;
		//注文日に対してリストを表示
		case "sortdate":

			Collections.sort(list, new purchaseOrderListToolCheck());

			break;
		//納期に対してリストを表示
		case "sortDeadline":

			Collections.sort(list, new purchaseOrderListToolDate());

			break;
		//出荷日に対してリストを表示
		case "sortShipping":

			Collections.sort(list, new purchaseOrderListTool());

			break;
		//数量に対してリストを表示
		case "sortQuanitiy":

			Collections.sort(list, new purchaseOrderListToolMash());

			break;
		//顧客に対してリストを表示
		case "sortClient":

			Collections.sort(list, new purchaseOrderListToolClient());

			break;

		//中身消去
		case "cancel":

			puList = null;
			sort = null;
			kinds = null;
			sibori = null;
			list = null;
			nakami = null;
			sDate = null;
			eDate = null;
			break;
		default:

			//全部取得。
		}
		if (list != null) {
			puList.setpOrder(list);
		}

		request.setAttribute("pList", puList);
		request.setAttribute("sort", sort);
		request.setAttribute("kinds", kinds);
		request.setAttribute("sibori", sibori);
		//入力された日付を残す
		request.setAttribute("startDate", sDate);
		request.setAttribute("endDate", eDate);
		return "/WEB-INF/main/purchaseOrderList.jsp";
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
