package action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_purchase;
import bean.GoodsReceipt;
import bean.StoreProduct;
import dao.DAO;
import dao.GoodsReceiptDAO;
import dao.StoreProductDAO;
import tool.Action;

public class PurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 処理結果アウトプット準備
		HttpSession session = request.getSession();

		// DB操作前のログイン状態確認
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		//セッション切れ処理
		if (loginStatusCheck == 0) {
			session.setAttribute("loginState", "セッション切れの為､購入画面に移動しました｡");
			return "/WEB-INF/main/index.jsp";
		}

		// 画面入力値をString形式でbeanに格納し､セッション属性｢G_purchase｣へ格納
		G_purchase G_purchase = new G_purchase();
		G_purchase.setJanCode(request.getParameter("jan"));
		G_purchase.setPcs(request.getParameter("pcs"));
		G_purchase.setPrice(request.getParameter("price"));
		session.setAttribute("G_purchase", G_purchase);

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		session.setAttribute("toAction", toAction);

		//入力値チェック用変数定義
		boolean judgeJanCode = false;
		boolean judgePcs = false;
		boolean judgePrice = false;

		// 処理種により､処理を分岐
		switch (toAction) {
		case "janCodeCheck":
			judgePrice = new MainAction().quantityCheck(request, response, "price", 3);
			judgePcs = new MainAction().quantityCheck(request, response, "pcs", 2);
			judgeJanCode = new MainAction().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new MainAction().searchStock(request, response, "jan");
			}
			break;
		case "pcsCheck":
			judgePrice = new MainAction().quantityCheck(request, response, "price", 3);
			judgeJanCode = new MainAction().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			judgePcs = new MainAction().quantityCheck(request, response, "pcs", 2);
			if (judgeJanCode == true) {
				new MainAction().searchStock(request, response, "jan");
			}
			break;
		case "priceCheck":
			judgePcs = new MainAction().quantityCheck(request, response, "pcs", 2);
			judgeJanCode = new MainAction().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			judgePrice = new MainAction().quantityCheck(request, response, "price", 3);
			if (judgeJanCode == true) {
				new MainAction().searchStock(request, response, "jan");
			}
			break;
		case "storingProcess":
			//入力有無確認
			String jan = request.getParameter("jan");
			String pcs = request.getParameter("pcs");
			String price = request.getParameter("price");
			String MSG = "";
			if (jan.equals("") || pcs.equals("") || price.equals("")) {
				if (jan.equals("")) {
					MSG += "JANコード";
				}
				if (pcs.equals("")) {
					if (MSG.equals("")) {
						MSG += "個数";
					} else {
						MSG += "/個数";
					}
				}
				if (price.equals("")) {
					if (MSG.equals("")) {
						MSG += "仕入単価";
					} else {
						MSG += "/仕入単価";
					}
				}
				session.setAttribute("state", MSG + "が入力されていません。");
				break;
			}

			//入力値最終確認
			judgePrice = new MainAction().quantityCheck(request, response, "price", 3);
			judgePcs = new MainAction().quantityCheck(request, response, "pcs", 2);
			judgeJanCode = new MainAction().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new MainAction().searchStock(request, response, "jan");
			}
			if (!(judgePrice == true && judgePcs == true && judgeJanCode == true)) {
				break;
			}

			//D/B処理
			// トランザクション処理準備
			DAO dao = new DAO();
			Connection con = dao.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				int line = storingProcess(request, response);
				// 成功/失敗判定
				if (line == 2) {
					con.commit();
					//new MainAction().clearAttribute(request, response);
					session.setAttribute("state", "処理が正常に終了しました｡");
					new MainAction().searchStock(request, response, "jan");
				} else {
					con.rollback();
					session.setAttribute("state", "処理中に異常が発生しました｡\\n処理は行われていません｡");
				}
				// トランザクション処理終了
				con.setAutoCommit(true);
			}
			break;
		case "storingProcessAft":
			judgeJanCode = new MainAction().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new MainAction().searchStock(request, response, "jan");
			}
			//個数欄/仕入単価欄を空欄にする
			G_purchase = (G_purchase) session.getAttribute("G_purchase");
			G_purchase.setPcs("");
			G_purchase.setPrice("");
			session.setAttribute("G_purchase", G_purchase);
			break;
		case "cancel":
			// セッション属性｢session｣「loginState」以外を全てリセット
			new MainAction().clearAttribute(request, response);
			break;
		default:
			// セッション属性｢session｣「loginState」以外を全てリセット
			new MainAction().clearAttribute(request, response);
		}
		return "/WEB-INF/main/purchase.jsp";
	}

	// 以下､メソッド=================================================================
	private int storingProcess(HttpServletRequest request, HttpServletResponse response) {
		int line = -1;
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// リクエストパラメータ取得
		String janCode = request.getParameter("jan");
		int pcs = Integer.parseInt(request.getParameter("pcs"));
		int price = Integer.parseInt(request.getParameter("price"));

		// セッション属性｢sp｣のインスタンス作成
		// リクエストパラメータ格納後､セッション属性｢sp｣に格納
		StoreProduct sp = new StoreProduct();
		sp.setJanCode(janCode);
		sp.setTnyukosu(pcs);
		session.setAttribute("sp", sp);

		// DAOインスタンス作成
		StoreProductDAO spDAO = new StoreProductDAO();
		// Janコードでの登録有無確認
		StoreProduct storeProduct = spDAO.searchStoreProductByJanCode(sp);
		if (storeProduct != null) {
			// セッション属性｢storeProduct｣にDB戻り値を格納
			session.setAttribute("storeProduct", storeProduct);
			// 登録済みのJanコードの場合､更新
			line = spDAO.updateToStoreProduct(sp, storeProduct);
		} else if (storeProduct == null) {
			// 未登録のJanコードの場合､新規追加
			line = spDAO.insertToStoreProduct(sp);
		}

		// セッション属性｢gr｣のインスタンス作成
		// リクエストパラメータ格納後､セッション属性｢gr｣に格納
		GoodsReceipt gr = new GoodsReceipt();
		gr.setJanCode(janCode);
		gr.setNyukoPCS(pcs);
		gr.setUnitPrice(price);
		session.setAttribute("gr", gr);

		// DAOインスタンス作成
		GoodsReceiptDAO grDAO = new GoodsReceiptDAO();
		// Janコード/同日での登録有無確認
		GoodsReceipt goodsReceipt = grDAO.searchGoodsReceiptByJanCodeAndNyukoYMD(gr);
		if (goodsReceipt != null) {
			// セッション属性｢goodsReceipt｣にDB戻り値を格納
			session.setAttribute("goodsReceipt", goodsReceipt);
			// 日付･Janコードが一致するレコードがある場合､更新
			line += grDAO.updateToGoodsReceipt(gr, goodsReceipt);
		} else if (goodsReceipt == null) {
			// 日付･Janコードが一致するレコードがない場合､新規追加
			line += grDAO.insertToGoodsReceipt(gr);
		}
		return line;
	}
}
