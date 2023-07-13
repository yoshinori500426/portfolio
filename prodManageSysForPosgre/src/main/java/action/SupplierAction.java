package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Supplier;
import dao.OrderTableDAO;
import dao.PurchaseOrderDAO;
import tool.Action;

public class SupplierAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//session取得
		HttpSession session = request.getSession();

		//画面の指示を取得する。
		String getSiji = request.getParameter("siji");

		//画面の入力内容を取得する。ビーンに格納
		Supplier supp = new Supplier();

		supp.setOrderNo(request.getParameter("orderNo"));
		supp.setOrderDate(request.getParameter("orderDate"));
		supp.setProductNo(request.getParameter("productNo"));
		supp.setProductName(request.getParameter("productName"));
		if (request.getParameter("orderQty") != null && request.getParameter("orderQty") != "") {
			supp.setOrderQty(Integer.parseInt(request.getParameter("orderQty")));
		}
		if (request.getParameter("dueQty") != null && request.getParameter("dueQty") != "") {
			supp.setDueQty(Integer.parseInt(request.getParameter("dueQty")));
		}

		switch (getSiji) {
		case "orderNoSearch":
			//DAO,beanのインスタンス化
			OrderTableDAO otD = new OrderTableDAO();
			Supplier recvSup = otD.searchByOrdNo2(supp);


			//注文番号を探しに行って無かった時のエラーメッセージ
			if (recvSup == null) {
				request.setAttribute("message", "注文番号が存在しません。");
				break;

			} else {
				//納入数量がすでに更新されているときのエラーメッセージ
				if (recvSup.getFinFlg().equals("1")) {
					request.setAttribute("message", "すでに納入されています。");
					break;
				}
			}
			supp = recvSup;
			break;

		case "kakutei":


			//if (supp.getOrderQty() != supp.getOrderQty()) {
				//request.setAttribute("message", "納品数量を入力してください。");
				//break;
			//}

			if (supp.getOrderQty() != supp.getDueQty()) {
				request.setAttribute("message", "入力された数量と注文数量に差があります。");
				break;
			}

			PurchaseOrderDAO po = new PurchaseOrderDAO();

			//ユーザーＩＤを取得
			String us = (String) session.getAttribute("loginIdA");
			supp.setRegistUser(us);


			int result = po.updateByOrderNo(supp);

			if (result == 2) {
				request.setAttribute("message", "更新しました");
				supp = new Supplier();

			} else {
				request.setAttribute("message", "更新出来ませんでした。");
			}
			break;

		case "clear":
			supp = new Supplier();
			request.setAttribute("cancel", new Supplier());
			break;

		default:
		}//switch end

		request.setAttribute("orderNo", supp.getOrderNo());
		request.setAttribute("orderDate", supp.getOrderDate());
		request.setAttribute("productNo", supp.getProductNo());
		request.setAttribute("productName", supp.getProductName());
		request.setAttribute("orderQty", supp.getOrderQty());

		return "/WEB-INF/main/supplier.jsp";
	}
}