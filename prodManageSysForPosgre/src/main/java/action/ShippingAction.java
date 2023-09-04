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
import dao.PuroductStockDAO;
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
		// 各メッセージリセット
		session.setAttribute("alert", null);
		session.setAttribute("message", null);
		session.setAttribute("state", null);
		// 使用DAOインスタンス取得
		PurchaseOrderDAO poDAO = new PurchaseOrderDAO();
		CustomerMasterDAO cmDAO = new CustomerMasterDAO();
		ProductMasterDAO pmDAO = new ProductMasterDAO();
		PuroductStockDAO psDAO = new PuroductStockDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		PurchaseOrder purchaseOrderForChange = null;
		List<PurchaseOrder> PurchaseOrderList = null;
		List<PurchaseOrder> PurchaseOrderListFinFlg0 = null;
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
		if (!btnSelect.equals("delete")) {
			G_Shipping.setShipQty(request.getParameter("shipQty"));
		} else if (btnSelect.equals("delete")) {
			G_Shipping.setShipQty("-" + request.getParameter("shipQty"));
		}
		G_Shipping.setShipDate(request.getParameter("shipDate"));
		G_Shipping.setFinFlg(request.getParameter("finFlg"));
		session.setAttribute("G_Shipping", G_Shipping);
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
			if (G_Shipping.getPoNo().isEmpty()) {
				// 各種セッション属性のnullクリア
				new MainAction().crearAttributeForScreenChange(session);
				session.setAttribute("btnSelect", btnSelect);
				break;
			}
			// テーブル検索
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
				if (btnSelect.equals("update") && purchaseOrderForChange.getFinFlg().equals("1")) {
					// 出荷処理済みは､表示のみ行い､内容変更/削除不可とする
					session.setAttribute("message", "入力値に該当する受注番号は既に出荷処理済みの為､編集不可です。\\n入力内容を確認ください。");
				} else if (btnSelect.equals("delete") && purchaseOrderForChange.getFinFlg().equals("0")) {
					// 出荷処理済みは､表示のみ行い､内容変更/削除不可とする
					session.setAttribute("message", "入力値に該当する受注番号は未だ出荷未処理の為､編集不可です。\\n入力内容を確認ください。");
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
			// 分納未対応の為のチェック
			// →受注数量=出荷数量になるようコードを書いている為､このifに該当するケースはありえないが､数値を取り扱うマナーとして記述する
			if (!G_Shipping.getOrderQty().equals(G_Shipping.getShipQty().replace("-", ""))) {
				session.setAttribute("message", "受注数量と出荷数量が一致しません｡\\n分納未対応の為､未処理で終了します｡");
				break;
			}
			int line = 0;
			// トランザクション処理準備
			Connection con = poDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				// テーブル｢ENTRY_EXIT_INFO｣｢PURODUCT_STOCK｣に対する処理方針
				// ･小売業/卸売業などの在庫管理を想定
				// →仕掛り在庫/半製品在庫/部品在庫など､細かな在庫分類は行わず､｢全在庫｣｢倉庫在庫｣のみ分類し管理する
				// ･出荷/入荷と入庫/出庫を区別し､出荷/入荷した商品は入庫/出庫するまで倉庫外在庫として扱う
				// →入荷/出荷のタイミングでは､テーブル｢PURODUCT_STOCK｣のみ変更する(←倉庫外在庫の増減として扱う=出荷/入荷では｢ENTRY_EXIT_INFO｣を変更しない)
				// ･テーブル｢PURODUCT_STOCK｣の｢現在在庫数｣は､倉庫在庫ではなく､倉庫外在庫/倉庫在庫の合計とする
				// ･テーブル｢PURODUCT_STOCK｣に新たに｢当月入荷数｣を追加し､｢現在在庫数｣は入荷/出荷のタイミングでのみ増減させ､入庫/出庫のタイミングでは増減させない
				// →入庫/出庫のタイミングで｢現在在庫数｣を増減させると､入荷/出荷と入庫/出庫でダブルカウントする事になり､正しい在庫数とならない為
				// ･テーブル｢PURODUCT_STOCK｣に新たに｢現在倉庫在庫数｣を追加し､
				// 入庫/出庫のタイミングでは､｢現在倉庫在庫数｣のみを増減させる(←その際､｢現在在庫数｣は増減させない=倉庫への出し入れは､在庫数全体の増減にはならない為)
				// ･出荷/入荷処理の取消処理時のテーブル｢PURODUCT_STOCK｣処理を以下とする
				// 出荷取消処理：テーブル｢PURODUCT_STOCK｣の出荷月の｢当月出荷数｣を取消数分マイナスし､出荷月以降の｢現在在庫数｣を取消数分プラスする
				// 入荷取消処理：テーブル｢PURODUCT_STOCK｣の入荷月の｢当月入荷数｣を取消数分マイナスし､入荷月以降の｢現在在庫数｣を取消数分マイナスする
				// ･入庫/出庫の更新/取消処理時のテーブル｢PURODUCT_STOCK｣処理を以下とする
				// 入庫の更新/取消処理：テーブル｢PURODUCT_STOCK｣の入出庫テーブルの日付月の｢当月入庫数｣を更新/取消数分増減させ､入出庫テーブルの日付月以降の｢現在倉庫在庫数｣を｢当月入庫数｣の増減と同じ符号の増減を行う
				// 出庫の更新/取消処理：テーブル｢PURODUCT_STOCK｣の入出庫テーブルの日付月の｢当月出庫数｣を更新/取消数分増減させ､入出庫テーブルの日付月以降の｢現在倉庫在庫数｣を｢当月出庫数｣の増減と逆の符号の増減を行う
				// ･DBで登録する日付は､テーブル毎に取得するのではなく､共通の日付を使用する
				// →このシステムでは､登録日等の｢PCの日時を取得する処理｣はDAOにて取得としているが､在庫テーブル処理は､Actionで取得した日時を処理を行う複数のテーブル全てに使用する動作とする
				// →稀なケースだが､テーブルを渡る処理が月またぎとなった場合､整合性が取れなくなる為､他のテーブルと年月を揃える事が目的
				if (btnSelect.equals("update")) {
					// テーブル｢PURCHASE_ORDER｣更新処理
					line += poDAO.updateForShipByPoNo(G_Shipping, "1", request);
				} else if (btnSelect.equals("delete")) {
					// テーブル｢PURCHASE_ORDER｣更新処理
					line += poDAO.updateForShipByPoNo(G_Shipping, "0", request);
				}
				// テーブル｢PURODUCT_STOCK(=在庫テーブル)｣処理(登録/更新)
				line += psDAO.productStockProcess(G_Shipping);
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
		PurchaseOrderListFinFlg0 = poDAO.searchAllFinFlg0();
		session.setAttribute("PurchaseOrderListFinFlg0", PurchaseOrderListFinFlg0);
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/shipping.jsp");
		return "/WEB-INF/main/shipping.jsp";
	}
}
