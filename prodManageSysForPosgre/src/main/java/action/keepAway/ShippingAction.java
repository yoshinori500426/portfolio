package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.CustomerMaster;
import bean.EntryExitInfo;
import bean.Order;
import bean.ProductMaster;
import bean.PurchaseOrder;
import dao.CustomerMasterDAO;
import dao.ProductMasterDAO;
import dao.PurchaseOrderDAO;
import dao.PuroductStockDAO;
import tool.Action;

public class ShippingAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 画面の値を取得する。

		String gettoAction = request.getParameter("toAction");
		//jsp画面の入力内容を取得する。

		Order OB = new Order();
		OB.setPo_No(request.getParameter("Po_No"));

		PurchaseOrderDAO purDao = null;
		PurchaseOrder getPur = null;

		//指示内容で処理を分岐する
		switch (gettoAction) {
		case "poNoSeach":
			//PurchaseOrderDAOを使える様にインスタンス化する
			purDao = new PurchaseOrderDAO();
			//CustomerMasterDAOを使える様にインスタンス化する
			CustomerMasterDAO cusDao = new CustomerMasterDAO();
			//ProductMasterDAOを使える様にインスタンス化する
			ProductMasterDAO proDAO = new ProductMasterDAO();

			//PurchaseOrderDAO のメッソドを呼び出す。
			//呼び出した結果を取得する。getPur
			getPur = purDao.searchByPorder(OB.getPo_No());
			//取得した結果で処理を分ける。
			//結果がnullの場合はメッセージをセットする。

			if (getPur == null) {
				request.setAttribute("message", "入力された受注番号は存在しません。");
				//getPur(null)は元々送れないので
				//Order OB = new Order();
				//OB.setPo_No(request.getParameter("Po_No"));
				//のOBを入れる
				request.setAttribute("poNo", OB.getPo_No());
			} else if (getPur.getFinFlg().equals("1")) {
				request.setAttribute("message", "出荷完了しています。");
				request.setAttribute("poNo", getPur.getPoNo());
			} else {
				//結果がnullでなければ、受け取った値（ビーン）を
				//setAttribute（パラメータ名、値）でセットする。

				request.setAttribute("poNo", getPur.getPoNo());
				request.setAttribute("customerNo", getPur.getCustomerNo());
				request.setAttribute("productNo", getPur.getProductNo());
				request.setAttribute("orderQty", getPur.getOrderQty());
				request.setAttribute("shipDate", getPur.getShipDate());
				request.setAttribute("finFlg", getPur.getFinFlg());
				request.setAttribute("orderDate", getPur.getOrderDate());

				CustomerMaster cus = cusDao.searchByCusNo(getPur.getCustomerNo());
				request.setAttribute("customerName", cus.getCustomerName());
				ProductMaster pro = proDAO.searchByProNoS(getPur.getProductNo());
				request.setAttribute("productName", pro.getProductName());
			}
			break;

		case "kakutei":
			int result=0;
			String ship_date = request.getParameter("ship_date");
			if (ship_date == "") {
				request.setAttribute("message", "出荷日を選んで下さい。");
			} else {
				//PurchaseOrderDAOを使える様にインスタンス化する
				purDao = new PurchaseOrderDAO();
				//PurchaseOrderテーブル変更の為、bean「PurchaseOrder」のインスタンス作成
				getPur = new PurchaseOrder();

				if (OB.getPo_No() == null || OB.getPo_No().equals("")) {
					request.setAttribute("message", "受注番号を入力してください。");
					break;
				}else {

				}

				getPur.setPoNo(request.getParameter("Po_No"));
				getPur.setShipDate(ship_date);

				result =purDao.updateAftShipByPoNo(getPur);

				//PurchaseOrderDAOを使える様にインスタンス化する
				purDao = new PurchaseOrderDAO();
				//PurchaseOrderDAO のメッソドを呼び出す。
				//呼び出した結果を取得する。getPur
				getPur = purDao.searchByPorder(OB.getPo_No());

				EntryExitInfo eei = new EntryExitInfo();
				eei.setProductNo(getPur.getProductNo());
				eei.setSyukoQty(getPur.getOrderQty());

				PuroductStockDAO psDAO = new PuroductStockDAO();
				result =result +psDAO.updateBySyukaStock(eei);
				if (result == 2) {
					request.setAttribute("message", "登録が成功しました。");
				}
			}

			return "/WEB-INF/main/shipping.jsp";

		}
		return "/WEB-INF/main/shipping.jsp";

	}
}


