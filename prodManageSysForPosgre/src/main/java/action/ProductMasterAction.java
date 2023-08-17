package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_ProductMaster;
import bean.SupplierMaster;
import tool.Action;

public class ProductMasterAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 処理準備
		HttpSession session = request.getSession();

		// ログイン状態確認
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		// セッション切れ処理
		if (loginStatusCheck == 0) {
			request.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
			// 画面「userMaster.jsp」で使用したセッション属性のnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("btnSelect", null);
			session.setAttribute("G_UserMaster", null);

			session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
			return "/WEB-INF/main/login.jsp";
		}

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);

		// 画面情報取得
		G_ProductMaster G_ProductMaster = new G_ProductMaster();
		G_ProductMaster.setProductNo(request.getParameter("productNo"));
		G_ProductMaster.setProductName(request.getParameter("productName"));
		G_ProductMaster.setSupplierNo(request.getParameter("supplierNo"));
		G_ProductMaster.setUnitPrice(request.getParameter("unitPrice"));
		G_ProductMaster.setSellingPrice(request.getParameter("sellingPrice"));
		G_ProductMaster.setLeadTime(request.getParameter("leadTime"));
		G_ProductMaster.setLot(request.getParameter("lot"));
		G_ProductMaster.setLocation(request.getParameter("location"));
		G_ProductMaster.setBaseStock(request.getParameter("baseStock"));
		G_ProductMaster.setEtc(request.getParameter("etc"));
		session.setAttribute("G_ProductMaster", G_ProductMaster);
		//仕入先名は､画面に直接入力するのではなく､仕入先コードの検索結果を表示させるため､｢G_ProductMaster｣には含めない
		SupplierMaster SupplierMaster = new SupplierMaster();
		SupplierMaster.setSupplierName(request.getParameter("supplierName"));
		session.setAttribute("SupplierMaster", SupplierMaster);
		
		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 不要セッション属性をnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("message", null);
			session.setAttribute("G_ProductMaster", null);
			session.setAttribute("SupplierMaster", null);
			break;
		case "searchProductNo":
			break;
		case "searchSupplierNo":
			break;
		case "dummy":
			// 要削除
			// umDAO = new UserMasterDAO();
			// userForChange = umDAO.searchByID(G_UserMaster);
			// session.setAttribute("G_UserMaster", userForChange);
			session.setAttribute("message", null);
			break;
		case "cancel":
		default:
			// 参照用セッション属性をnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("message", null);
			session.setAttribute("btnSelect", null);
			session.setAttribute("G_ProductMaster", null);
			session.setAttribute("SupplierMaster", null);
			break;
		}
		session.setAttribute("nextJsp", "/WEB-INF/main/productMaster.jsp");
		return "/WEB-INF/main/productMaster.jsp";
	}

}
