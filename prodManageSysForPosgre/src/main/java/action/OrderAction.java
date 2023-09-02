package action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_Order;
import bean.OrderTable;
import bean.ProductMaster;
import bean.SupplierMaster;
import dao.OrderTableDAO;
import dao.ProductMasterDAO;
import dao.SupplierMasterDAO;
import tool.Action;

public class OrderAction extends Action {
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
		// 各メッセージリセット
		session.setAttribute("alert", null);
		session.setAttribute("message", null);
		session.setAttribute("state", null);
		// 使用DAOインスタンス取得
		OrderTableDAO otDAO = new OrderTableDAO();
		ProductMasterDAO pmDAO = new ProductMasterDAO();
		SupplierMasterDAO smDAO = new SupplierMasterDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		OrderTable orderTableForChange = null;
		List<OrderTable> OrderTableList = null;
		ProductMaster ProductMaster = null;
		List<ProductMaster> ProductMasterList = null;
		SupplierMaster SupplierMaster = null;
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面の入力内容取得
		G_Order G_Order = new G_Order();
		G_Order.setOrderNo(request.getParameter("orderNo"));
		G_Order.setProductNo(request.getParameter("productNo"));
		G_Order.setProductName(request.getParameter("productName"));
		G_Order.setSupplierNo(request.getParameter("supplierNo"));
		G_Order.setSupplierName(request.getParameter("supplierName"));
		G_Order.setLot(request.getParameter("lot"));
		G_Order.setOrderLot(request.getParameter("orderLot"));
		G_Order.setOrderQty(request.getParameter("orderQty"));
		G_Order.setDeliveryDate(request.getParameter("deliveryDate"));
		G_Order.setBiko(request.getParameter("biko"));
		G_Order.setFinFlg(request.getParameter("finFlg"));
		G_Order.setOrderDate(request.getParameter("orderDate"));
		session.setAttribute("G_Order", G_Order);
		// 処理種により､処理を分岐
		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			session.setAttribute("btnSelect", btnSelect);
			break;
		case "searchOrderNo":
			// PoNoのクリア動作
			if (G_Order.getOrderNo().isEmpty()) {
				// 各種セッション属性のnullクリア
				new MainAction().crearAttributeForScreenChange(session);
				session.setAttribute("btnSelect", btnSelect);
				break;
			}
			// テーブル検索
			orderTableForChange = otDAO.searchByOrdNo(G_Order);
			if (orderTableForChange == null) {
				// 各種セッション属性のnullクリア
				new MainAction().crearAttributeForScreenChange(session);
				// OrderNo以外の項目を空欄にする
				G_Order = new G_Order();
				G_Order.setOrderNo(request.getParameter("orderNo"));
				session.setAttribute("G_Order", G_Order);
				session.setAttribute("btnSelect", btnSelect);
				session.setAttribute("message", "入力値に該当する発注番号は存在しません。\\n入力内容を確認ください。");
				break;
			} else if (orderTableForChange != null) {
				if (orderTableForChange.getFinFlg().equals("1")) {
					// 納品処理済みは､表示のみ行い､内容変更/削除不可とする
					session.setAttribute("message", "入力値に該当する発注番号は既に入荷処理済みの為､編集不可です。\\n入力内容を確認ください。");
				}
				G_Order.setOrderNo(orderTableForChange.getOrderNo());
				G_Order.setProductNo(orderTableForChange.getProductNo());
				G_Order.setOrderQty(String.valueOf(orderTableForChange.getOrderQty()));
				G_Order.setDeliveryDate(orderTableForChange.getDeliveryDate());
				G_Order.setBiko(orderTableForChange.getBiko());
				G_Order.setFinFlg(orderTableForChange.getFinFlg());
				G_Order.setOrderDate(orderTableForChange.getOrderDate());
				session.setAttribute("G_Order", G_Order);
			}
			// ｢OrderNo｣での検索結果に基づき､引き続き､｢productName｣｢supplierNo｣｢supplierName｣の検索を行う為､このStepでは｢break;｣の記述は行わない
		case "searchProductNo":
			// テーブル検索
			ProductMaster = pmDAO.searchByProNo(G_Order);
			if (!G_Order.getProductNo().isEmpty()) {
				if (ProductMaster == null) {
					session.setAttribute("message", "入力値に該当する品番は存在しません。\\n入力内容を確認ください。");
				} else if (ProductMaster != null) {
					SupplierMaster = smDAO.searchBySupNo(ProductMaster.getSupplierNo());
				}
			}
			session.setAttribute("ProductMaster", ProductMaster);
			session.setAttribute("SupplierMaster", SupplierMaster);
			break;
		case "doBTNExecute":
			int line = 0;
			// トランザクション処理準備
			Connection con = otDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				if (btnSelect.equals("insert")) {
					OrderTable ot = new OrderTable();
					ot.setSupplierNo(G_Order.getSupplierNo());
					ot.setProductNo(G_Order.getProductNo());
					ot.setOrderQty(Integer.parseInt(G_Order.getOrderQty()));
					ot.setDeliveryDate(G_Order.getDeliveryDate());
					ot.setBiko(G_Order.getBiko());
					line = otDAO.insert(ot, request);
				} else if (btnSelect.equals("update")) {
					line = otDAO.updateForOrder(G_Order, request);
				} else if (btnSelect.equals("delete")) {
					line = otDAO.delete(G_Order, request);
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
		// プルダウン用リスト取得
		OrderTableList = otDAO.searchAll();
		session.setAttribute("OrderTableList", OrderTableList);
		ProductMasterList = pmDAO.searchAll();
		session.setAttribute("ProductMasterList", ProductMasterList);
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/order.jsp");
		return "/WEB-INF/main/order.jsp";
	}
}
