package action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CustomerMaster;
import bean.G_Shipping;
import bean.ProductMaster;
import bean.PurchaseOrder;
import dao.CustomerMasterDAO;
import dao.ProductMasterDAO;
import dao.PurchaseOrderDAO;
import tool.Action;

public class ShippingAction extends Action {
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
		// 使用DAOインスタンス取得
		PurchaseOrderDAO poDAO = new PurchaseOrderDAO();
		CustomerMasterDAO cmDAO = new CustomerMasterDAO();
		ProductMasterDAO pmDAO = new ProductMasterDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		PurchaseOrder purchaseOrderForChange = null;
		List<PurchaseOrder> PurchaseOrderList = null;
		CustomerMaster CustomerMaster = null;
		ProductMaster ProductMaster = null;
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面の入力内容取得
		G_Shipping G_Shipping = new G_Shipping();
		G_Shipping.setPoNo(request.getParameter("poNo"));
		G_Shipping.setCustomerNo(request.getParameter("customerNo"));
		G_Shipping.setCustomerName(request.getParameter("customerName"));
		G_Shipping.setProductNo(request.getParameter("productNo"));
		G_Shipping.setProductName(request.getParameter("productName"));
		G_Shipping.setOrderDate(request.getParameter("orderDate"));
		G_Shipping.setOrderQty(request.getParameter("orderQty"));
		G_Shipping.setDeliveryDate(request.getParameter("deliveryDate"));
		G_Shipping.setShipQty(request.getParameter("shipQty"));
		G_Shipping.setShipDate(request.getParameter("shipDate"));
		G_Shipping.setFinFlg(request.getParameter("finFlg"));
		session.setAttribute("G_Shipping", G_Shipping);
		// 処理種により､処理を分岐
		switch (toAction) {
		case "searchPoNo":
			// PoNoのクリア動作
			if (G_Shipping.getPoNo().isEmpty()) {
				// 各種セッション属性のnullクリア
				new MainAction().crearAttributeForScreenChange(session);
				session.setAttribute("btnSelect", btnSelect);
				break;
			}
			// テーブル検索
			poDAO = new PurchaseOrderDAO();
			purchaseOrderForChange = poDAO.searchByPoNo(G_Shipping);
			if (purchaseOrderForChange == null) {
				// 各種セッション属性のnullクリア
				new MainAction().crearAttributeForScreenChange(session);
				// PoNo以外の項目を空欄にする
				G_Shipping = new G_Shipping();
				G_Shipping.setPoNo(request.getParameter("poNo"));
				session.setAttribute("G_Shipping", G_Shipping);
				session.setAttribute("btnSelect", btnSelect);
				session.setAttribute("message", "入力値に該当する受注番号は存在しません。\\n入力内容を確認ください。");
				break;
			} else if (purchaseOrderForChange != null) {
				if (purchaseOrderForChange.getFinFlg().equals("1")) {
					// 出荷処理済みは､表示のみ行い､内容変更/削除不可とする
					session.setAttribute("message", "入力値に該当する受注番号は既に出荷処理済みの為､編集不可です。\\n入力内容を確認ください。");
				}
				G_Shipping.setPoNo(purchaseOrderForChange.getPoNo());
				G_Shipping.setCustomerNo(purchaseOrderForChange.getCustomerNo());
				G_Shipping.setProductNo(purchaseOrderForChange.getProductNo());
				G_Shipping.setOrderDate(purchaseOrderForChange.getOrderDate());
				G_Shipping.setOrderQty(String.valueOf(purchaseOrderForChange.getOrderQty()));
				G_Shipping.setDeliveryDate(purchaseOrderForChange.getDeliveryDate());
				// 分納機能未対応の為､出荷数量を受注数量とする
				G_Shipping.setShipQty(String.valueOf(purchaseOrderForChange.getOrderQty()));
				// 出荷済みの受注番号は出荷日が格納され､未出荷は空文字が格納される
				G_Shipping.setShipDate(purchaseOrderForChange.getShipDate());
				G_Shipping.setFinFlg(purchaseOrderForChange.getFinFlg());
				session.setAttribute("G_Shipping", G_Shipping);
			}
			// テーブル検索
			CustomerMaster = cmDAO.searchByCusNo(G_Shipping);
			ProductMaster = pmDAO.searchByProNo(G_Shipping);
			session.setAttribute("CustomerMaster", CustomerMaster);
			session.setAttribute("ProductMaster", ProductMaster);
			break;
		case "doBTNExecute":
			int line = 0;
			// トランザクション処理準備
			Connection con = poDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				line = poDAO.updateByPoNo(G_Shipping, request);

				// 成功/失敗判定
				if (line == 2) {
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
		// プルダウン用リスト取得
		PurchaseOrderList = poDAO.searchAll();
		session.setAttribute("PurchaseOrderList", PurchaseOrderList);
		CustomerMasterList = cmDAO.searchAll();
		session.setAttribute("CustomerMasterList", CustomerMasterList);
		ProductMasterList = pmDAO.searchAll();
		session.setAttribute("ProductMasterList", ProductMasterList);
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/shipping.jsp");
		return "/WEB-INF/main/shipping.jsp";
	}
}
