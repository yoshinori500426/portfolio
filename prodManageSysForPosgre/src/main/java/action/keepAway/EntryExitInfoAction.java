package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.EntryExitInfo;
import bean.ProductMaster;
import bean.UserMaster;
import dao.EntryExitInfoDAO;
import dao.ProductMasterDAO;
import dao.PuroductStockDAO;
import tool.Action;

public class EntryExitInfoAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String siji = request.getParameter("siji");
		String ko = request.getParameter("ko");
		String qty = request.getParameter("qty");


		EntryExitInfo entryexitinfo = new EntryExitInfo();
		ProductMaster productmaster = new ProductMaster();


		System.out.println(request.getParameter("code"));
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("ko"));
		System.out.println(request.getParameter("loginname"));
		System.out.println(request.getParameter("qty"));
		System.out.println(request.getParameter("zero"));
		System.out.println(request.getParameter("review"));

		entryexitinfo.setProductNo(request.getParameter("code"));
		entryexitinfo.setProductName(request.getParameter("name"));
		productmaster.setProductNo(request.getParameter("code"));
		productmaster.setProductName(request.getParameter("name"));



		if (request.getParameter("qty") != null && !request.getParameter("qty").isEmpty()) {
			entryexitinfo.setNyukoQty(Integer.parseInt(request.getParameter("qty")));
		}
		if (request.getParameter("qty") != null && !request.getParameter("qty").isEmpty()) {
			entryexitinfo.setSyukoQty(Integer.parseInt(request.getParameter("qty")));
		}

		entryexitinfo.setReason(request.getParameter("review"));

		//login用のセッション属性取得（ログイン名取得）
				HttpSession session = request.getSession();
				session.getAttribute("user");
				UserMaster um = (UserMaster) session.getAttribute("user");
				entryexitinfo.setRegistUser(um.getUserId());

		if (request.getParameter("code") != null && !request.getParameter("code").isEmpty()) {
			productmaster.setProductNo(String.format("%10s",request.getParameter("code").replace(" ","")).replace(" ","0"));
		}

		switch (siji) {
		case "idSearch":
			ProductMasterDAO dao = new ProductMasterDAO();
			ProductMaster getProductMasterInfo = dao.searchByProNoS(productmaster.getProductNo());

			if (getProductMasterInfo == null) {
				request.setAttribute("message", "品番マスタに登録ありません。");
			} else {
				request.setAttribute("productmaster", getProductMasterInfo);

			}
			break;

		case "nyukoregist":
			int nyukoko = nyusyukocheck(request, qty);
			if (nyukoko == 1) {
				String result = nyuko(entryexitinfo);
				request.setAttribute("message", result);
			}
			break;

		case "syukoregist":

			int syukoko = nyusyukocheck(request, qty);
			if (syukoko == 1) {
				String result = syuko(entryexitinfo);
				request.setAttribute("message", result);
			}
			break;

		case "init":
			request.setAttribute("entryexitinfo", new EntryExitInfo());
			break;

		default:
			if (ko == "" || ko.isEmpty()) {
				request.setAttribute("message", "入庫・出庫選択してください。");
			}
			break;
		}
		request.setAttribute("dousa", "");
		return "/WEB-INF/main/entryExitInfo.jsp";
	}


	private int nyusyukocheck(HttpServletRequest request, String qty) {
		Number zero = 0;
		String val = Integer.toString((int) zero);
		if (qty == "" || qty.isEmpty() || qty == val  ) {
			request.setAttribute("message", "数量を入力してください。");
			return 0;
		}
		return 1;
	}

	private String nyuko(EntryExitInfo entryexitinfo) {
		EntryExitInfoDAO entrydao = new EntryExitInfoDAO();
		PuroductStockDAO zd = new PuroductStockDAO();
		int result = entrydao.insertByEnExNyuko(entryexitinfo);
		zd.kousinByNyukoStock(entryexitinfo);

		if (result == 1) {
			return "入庫登録完了";

		} else {
			return "全ての項目を入力してください。";
		}

	}

	private String syuko(EntryExitInfo entryexitinfo) {
		EntryExitInfoDAO entrydao = new EntryExitInfoDAO();
		PuroductStockDAO zd = new PuroductStockDAO();
		int result = entrydao.insertByEnExSyuko(entryexitinfo);
		zd.kousinBySyukoStock(entryexitinfo);

		if (result == 1) {
			return "出庫登録完了";

		} else {
			return "全ての項目を入力してください。";
		}
	}

}
