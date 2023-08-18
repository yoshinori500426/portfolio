package action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ProductMaster;
import bean.PuroductStock;
import bean.Stock;
import dao.ProductMasterDAO;
import dao.PuroductStockDAO;
import tool.Action;
import tool.SortDeliveryDate;

public class StockAction extends Action {
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
		String productNo = String.format("%10s",request.getParameter("productNo").replace(" ","")).replace(" ","0");
		if (productNo == null)
			productNo = "";
		ProductMaster pm = new ProductMaster();
		PuroductStock ps = new PuroductStock();
		ProductMasterDAO dao = new ProductMasterDAO();
		PuroductStockDAO stockDao = new PuroductStockDAO();
		if (request.getParameter("productNo") != null && !request.getParameter("productNo").isEmpty()) {
			pm.setProductNo(String.format("%10s",request.getParameter("productNo").replace(" ","")).replace(" ","0"));
		} else {
			return "/WEB-INF/main/stock.jsp";
		}
		ProductMaster getCheckinfo = dao.searchByProNo(pm);
		if (getCheckinfo == null) {
			request.setAttribute("message", "品番マスタに存在しません。");
			return "/WEB-INF/main/stock.jsp";
		} else {
			request.setAttribute("product", getCheckinfo);
		}
		ps = stockDao.searchByPrNo(productNo);
		if (ps == null) {
			request.setAttribute("message", "在庫データがありません。");
			return "/WEB-INF/main/stock.jsp";
		}
		request.setAttribute("ps", ps);
		List<Stock> list = stockDao.searchBySchedule(productNo);
		Collections.sort(list, new SortDeliveryDate());
		request.setAttribute("list", list );

		return "/WEB-INF/main/stock.jsp";
	}

}
