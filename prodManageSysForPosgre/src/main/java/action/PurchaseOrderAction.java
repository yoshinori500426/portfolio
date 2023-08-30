package action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CustomerMaster;
import bean.G_PurchaseOrder;
import bean.ProductMaster;
import bean.PurchaseOrder;
import dao.CustomerMasterDAO;
import dao.ProductMasterDAO;
import dao.PurchaseOrderDAO;
import tool.Action;

public class PurchaseOrderAction extends Action {
	@Override
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
		// 不要セッション属性のnullクリア
//		session.setAttribute("alert", null);
//		session.setAttribute("message", null);

		// 使用インスタンスの格納変数を参照先「null」で宣言
		PurchaseOrderDAO poDAO = null;
		PurchaseOrder purchaseOrderForChange = null;
		CustomerMasterDAO cmDAO = null;
		CustomerMaster CustomerMaster = null;
		ProductMasterDAO pmDAO = null;
		ProductMaster ProductMaster = null;

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);

		// 画面の入力内容を取得し order.jsp専用ビーンに格納
		G_PurchaseOrder G_PurchaseOrder = new G_PurchaseOrder();
		G_PurchaseOrder.setPoNo(request.getParameter("poNo"));
		G_PurchaseOrder.setCustomerNo(request.getParameter("customerNo"));
		G_PurchaseOrder.setCustomerName(request.getParameter("customerName"));
		G_PurchaseOrder.setProductNo(request.getParameter("productNo"));
		G_PurchaseOrder.setProductName(request.getParameter("productName"));
		G_PurchaseOrder.setOrderDate(request.getParameter("orderDate"));
		G_PurchaseOrder.setOrderQty(request.getParameter("orderQty"));
		G_PurchaseOrder.setDeliveryDate(request.getParameter("deliveryDate"));
		G_PurchaseOrder.setFinFlg(request.getParameter("finFlg"));
		session.setAttribute("G_PurchaseOrder", G_PurchaseOrder);

		// 処理種により､処理を分岐
		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			session.setAttribute("btnSelect", btnSelect);
			break;
		case "searchPoNo":
			// PoNoのクリア動作
			if (G_PurchaseOrder.getPoNo().isEmpty()) {
				// 各種セッション属性のnullクリア
				new MainAction().crearAttributeForScreenChange(session);
				break;
			}
			// テーブル検索
			poDAO = new PurchaseOrderDAO();
			purchaseOrderForChange = poDAO.searchByPoNo(G_PurchaseOrder);
			if (purchaseOrderForChange == null) {
				// 各種セッション属性のnullクリア
				new MainAction().crearAttributeForScreenChange(session);
				// PoNo以外の項目を空欄にする
				G_PurchaseOrder = new G_PurchaseOrder();
				G_PurchaseOrder.setPoNo(request.getParameter("poNo"));
				session.setAttribute("G_PurchaseOrder", G_PurchaseOrder);
				session.setAttribute("btnSelect", btnSelect);
				session.setAttribute("message", "入力値に該当する受注番号は存在しません。\\n入力内容を確認ください。");
				break;
			} else if (purchaseOrderForChange != null) {
				if (purchaseOrderForChange.getFinFlg().equals("1")) {
					// 出荷処理済みは､表示のみ行い､内容変更/削除不可とする
					session.setAttribute("message", "入力値に該当する受注番号は既に出荷処理済みの為､編集不可です。\\n入力内容を確認ください。");
				}
				G_PurchaseOrder.setPoNo(purchaseOrderForChange.getPoNo());
				G_PurchaseOrder.setCustomerNo(purchaseOrderForChange.getCustomerNo());
				G_PurchaseOrder.setProductNo(purchaseOrderForChange.getProductNo());
				G_PurchaseOrder.setOrderDate(purchaseOrderForChange.getOrderDate());
				G_PurchaseOrder.setOrderQty(String.valueOf(purchaseOrderForChange.getOrderQty()));
				G_PurchaseOrder.setDeliveryDate(purchaseOrderForChange.getDeliveryDate());
				G_PurchaseOrder.setFinFlg(purchaseOrderForChange.getFinFlg());
				session.setAttribute("G_PurchaseOrder", G_PurchaseOrder);
			}
			// ｢PoNo｣での検索結果に基づき､引き続き､｢CustomerNo｣｢ProductNo｣の検索を行う為､このStepでは｢break;｣の記述は行わない
		case "searchCustomerNo":
			// テーブル検索
			cmDAO = new CustomerMasterDAO();
			CustomerMaster = cmDAO.searchByCusNo(G_PurchaseOrder);
			if (!G_PurchaseOrder.getCustomerNo().isEmpty()) {
				if (CustomerMaster == null) {
					session.setAttribute("message", "入力値に該当する顧客コードは存在しません。\\n入力内容を確認ください。");
				}
			}
			session.setAttribute("CustomerMaster", CustomerMaster);
			// ｢toAction｣の値が｢searchPoNo｣の場合､次に｢searchProductNo｣を行う為､このStepで｢break;｣させない
			if (toAction.equals("searchCustomerNo")) {
				break;
			}
		case "searchProductNo":
			// テーブル検索
			pmDAO = new ProductMasterDAO();
			ProductMaster = pmDAO.searchByProNo(G_PurchaseOrder);
			if (!G_PurchaseOrder.getProductNo().isEmpty()) {
				if (ProductMaster == null) {
					session.setAttribute("message", "入力値に該当する品番は存在しません。\\n入力内容を確認ください。");
				}
			}
			session.setAttribute("ProductMaster", ProductMaster);
			break;
		case "doBTNExecute":
			int line = 0;
			// トランザクション処理準備
			poDAO = new PurchaseOrderDAO();
			Connection con = poDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				if (btnSelect.equals("insert")) {
					line = poDAO.insert(G_PurchaseOrder, request);
				} else if (btnSelect.equals("update")) {
					line = poDAO.updateByPoNo(G_PurchaseOrder, request);
				} else if (btnSelect.equals("delete")) {
					line = poDAO.delete(G_PurchaseOrder, request);
				}
				// 成功/失敗判定
				if (line == 1) {
					con.commit();
					// 各種セッション属性のnullクリア
					new MainAction().crearAttributeForScreenChange(session);
					session.setAttribute("message", "処理が正常に終了しました｡");
				} else {
					con.rollback();
					session.setAttribute("message", "処理中に異常が発生しました｡\\n処理は行われていません｡");
				}
				// トランザクション処理終了
				con.setAutoCommit(true);
			}
			break;
		case "dummy":
			session.setAttribute("message", null);
			break;
		case "cancel":
		default:
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			break;
		}
		session.setAttribute("nextJsp", "/WEB-INF/main/purchaseOrder.jsp");
		return "/WEB-INF/main/purchaseOrder.jsp";
	}
}
