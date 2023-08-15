package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ProductMaster;
import bean.SupplierMaster;
import bean.UserMaster;
import dao.ProductMasterDAO;
import dao.SupplierMasterDAO;
import tool.Action;

public class ProductMasterAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//jspのname="toAction"
		//jspのname="dousa"
		String nakami = request.getParameter("toAction");
		String dousa = request.getParameter("dousa");
		ProductMaster pro = new ProductMaster();
		SupplierMaster sum = new SupplierMaster();

		//追記★
				request.setAttribute("nakami", nakami);

		pro.setProductNo(request.getParameter("Number"));
		pro.setProductName(request.getParameter("name"));
		pro.setSupplierNo(request.getParameter("Code"));
		sum.setSupplierNo(request.getParameter("Code"));
		sum.setSupplierName(request.getParameter("CodeName"));
		if (request.getParameter("Price") != null && !request.getParameter("Price").isEmpty()) {
			pro.setUnitPrice(Integer.parseInt(request.getParameter("Price")));
		}
		if (request.getParameter("SellingPrice") != null && !request.getParameter("SellingPrice").isEmpty()) {
			pro.setSellingPrice(Integer.parseInt(request.getParameter("SellingPrice")));
		}
		if (request.getParameter("leadTime") != null && !request.getParameter("leadTime").isEmpty()) {
//			pro.setLeadtime(Integer.parseInt(request.getParameter("leadTime")));
		}
		if (request.getParameter("purchaseLot") != null && !request.getParameter("purchaseLot").isEmpty()) {
			pro.setLot(Integer.parseInt(request.getParameter("purchaseLot")));
		}
		pro.setLocation(request.getParameter("stockLocation"));

		if (request.getParameter("backStock") != null && !request.getParameter("backStock").isEmpty()) {
			pro.setBaseStock(Integer.parseInt(request.getParameter("backStock")));
		}

		pro.setEtc(request.getParameter("Etc"));

		//login用のセッション属性取得（ログイン名取得）
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserMaster um = (UserMaster) session.getAttribute("user");
//		pro.setRegistUser(um.getUserId());

		//EL式で処理
		switch (nakami) {
		//Noを確認して処理
		case "searchNo":
			ProductMasterDAO dao = new ProductMasterDAO();
			ProductMaster prod = dao.searchBySireNo(pro.getProductNo());
			if (prod == null) {
				request.setAttribute("message", "入力された番号は登録されていません。");
			} else {
				request.setAttribute("Number", prod.getProductNo());
				request.setAttribute("Name", prod.getProductName());
				request.setAttribute("Code", prod.getSupplierNo());
				request.setAttribute("Price", prod.getUnitPrice());
				request.setAttribute("SellingPrice", prod.getSellingPrice());
//				request.setAttribute("leadTime", prod.getLeadtime());
				request.setAttribute("purchaseLot", prod.getLot());
				request.setAttribute("stockLocation", prod.getLocation());
				request.setAttribute("backStock", prod.getBaseStock());
				request.setAttribute("Etc", prod.getEtc());

				SupplierMasterDAO SuDao = new SupplierMasterDAO();
				SupplierMaster sp = new SupplierMaster();
				sp.setSupplierNo(prod.getSupplierNo());
				SupplierMaster prosp = SuDao.searchBySupNo(sp);
				request.setAttribute("Code", prosp.getSupplierNo());
				request.setAttribute("CodeName", prosp.getSupplierName());

			}
			break;
		case "siire":
			SupplierMasterDAO Dao = new SupplierMasterDAO();
			SupplierMaster prosp = Dao.searchBySupNo(sum);
			if (prosp == null) {
				request.setAttribute("message", "入力された番号は登録されていません。");
			} else {
				request.setAttribute("Code", prosp.getSupplierNo());
				request.setAttribute("CodeName", prosp.getSupplierName());
			}
			break;

		//登録処理
		case "insert":
			ProductMasterDAO insertDao = new ProductMasterDAO();
			int insert = 0;
			try {
				insert = insertDao.insertByProNo(pro);
				if (insert > 0) {
					request.setAttribute("message", "登録に成功しました。");
				} else {
					request.setAttribute("message", "登録に失敗しました。");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		case "update":
			ProductMasterDAO updateDao = new ProductMasterDAO();
			int update = 0;
			try {
				update = updateDao.updatesearchByProNo(pro);
				if (update == 1) {
					request.setAttribute("message", "更新に成功しました。");
				} else {
					request.setAttribute("message", "更新に失敗しました。");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "AllOkEnd":
			pro = null;
			dousa = null;

			break;
		default:
			pro = null;
			dousa = null;
			break;

		}
		request.setAttribute("dousa", dousa);
		return "/WEB-INF/main/productMaster.jsp";
	}

}
