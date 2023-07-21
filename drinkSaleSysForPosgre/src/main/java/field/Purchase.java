package field;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_purchase;
import bean.GoodsReceipt;
import bean.StoreProduct;
import dao.DAO;
import dao.GoodsReceiptDAO;
import dao.StoreProductDAO;
import tool.CommonMethod;

/**
 * Servlet implementation class Purchase
 */
@WebServlet("/Purchase")
public class Purchase extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 処理結果アウトプット準備
		HttpSession session = request.getSession();

		// ログイン後の処理前用メソッド(①不要セッション属性nullクリア②ログイン状態確認)
		new CommonMethod().actionBefProc(request, response);

		// 画面入力値をString形式でbeanに格納し､セッション属性｢G_purchase｣へ格納
		G_purchase G_purchase = new G_purchase();
		G_purchase.setJanCode(request.getParameter("jan"));
		G_purchase.setPcs(request.getParameter("pcs"));
		G_purchase.setPrice(request.getParameter("price"));
		session.setAttribute("G_purchase", G_purchase);

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		// セッション属性｢process｣に｢getParameter("toAction")｣の値を格納する
		// →セッション属性｢toAction｣は､メソッド｢new
		// CommonMethod().dispatchToActionByInputOutputScreenClass()｣で､
		// ｢main/〇〇.jsp｣の値が格納される
		session.setAttribute("process", toAction);

		// 入力値チェック用変数定義
		boolean judgeJanCode = false;
		boolean judgePcs = false;
		boolean judgePrice = false;
		int reqPara = 0;

		// 処理種により､処理を分岐
		switch (toAction) {
		case "janCodeCheck":
			judgePrice = purchasePriceCheck(request, response, "price", 3);
			judgePcs = purchasePcsCheck(request, response, "pcs", 2);
			judgeJanCode = new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new CommonMethod().searchStock(request, response, "jan");
			}
			break;
		case "pcsCheck":
			judgePrice = purchasePriceCheck(request, response, "price", 3);
			judgeJanCode = new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new CommonMethod().searchStock(request, response, "jan");
			}
			judgePcs = purchasePcsCheck(request, response, "pcs", 2);
			break;

		case "priceCheck":
			judgePcs = purchasePcsCheck(request, response, "pcs", 2);
			judgeJanCode = new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new CommonMethod().searchStock(request, response, "jan");
			}
			judgePrice = purchasePriceCheck(request, response, "price", 3);
			break;
		case "storingProcess":
			// 入力有無確認
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

			// 入力値最終確認
			judgePrice = purchasePriceCheck(request, response, "price", 3);
			judgePcs = purchasePcsCheck(request, response, "pcs", 2);
			judgeJanCode = new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new CommonMethod().searchStock(request, response, "jan");
			}
			// 入力値判定
			if (!(judgePrice == true && judgePcs == true && judgeJanCode == true)) {
				break;
			}

			PrintWriter out = response.getWriter();
			try {
				// D/B処理
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
						// new CommonMethod().clearAttribute(request, response);
						session.setAttribute("state", "処理が正常に終了しました｡");
						new CommonMethod().searchStock(request, response, "jan");
					} else {
						con.rollback();
						session.setAttribute("state", "処理中に異常が発生しました｡\\n処理は行われていません｡");
					}
					// トランザクション処理終了
					con.setAutoCommit(true);
				}
			} catch (Exception e) {
				e.printStackTrace(out);
			}
			break;
		case "storingProcessAft":
			judgeJanCode = new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new CommonMethod().searchStock(request, response, "jan");
			}
			// 個数欄/仕入単価欄を空欄にする
			G_purchase = (G_purchase) session.getAttribute("G_purchase");
			G_purchase.setPcs("");
			G_purchase.setPrice("");
			session.setAttribute("G_purchase", G_purchase);
			break;
		case "cancel":
			// セッション属性｢session｣「loginState」以外を全てリセット
			new CommonMethod().clearAttribute(request, response);
			break;
		default:
			// セッション属性｢session｣「loginState」以外を全てリセット
			new CommonMethod().clearAttribute(request, response);
		}
		// クラス｢InputOutputScreen｣を使用した画面遷移
		new CommonMethod().dispatchToActionByInputOutputScreenClass("main/purchase.jsp", request, response);
	}

	// 以下､メソッド=================================================================
	private boolean purchasePcsCheck(HttpServletRequest request, HttpServletResponse response, String reqPara, int i) {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();
		boolean judgePcs = new CommonMethod().quantityCheck(request, response, reqPara, i);
		if (judgePcs == true) {
			String jan = request.getParameter("jan");
			int pcs = Integer.parseInt(request.getParameter(reqPara));
			if (jan == null || jan.equals("")) {
				if (pcs > 99999999) {
					judgePcs = false;
					session.setAttribute("state", "現在の入力値が､入力値の上限､99999999を超えています｡\\n99999999以下の整数値を入力してください｡");
				}
			} else {
				// セッション属性｢sp｣のインスタンス作成
				// リクエストパラメータ格納後､セッション属性｢sp｣に格納
				StoreProduct sp = new StoreProduct();
				sp.setJanCode(jan);
				sp.setTnyukosu(pcs);
				session.setAttribute("sp", sp);
				// DAOインスタンス作成
				StoreProductDAO spDAO = new StoreProductDAO();
				// Janコードでの登録有無確認
				StoreProduct storeProduct = spDAO.searchStoreProductByJanCode(sp);
				if (storeProduct != null) {
					// セッション属性｢storeProduct｣にDB戻り値を格納
					session.setAttribute("storeProduct", storeProduct);
					if (storeProduct.getStockPCS() >= storeProduct.getTnyukosu()) {
						if (pcs > 99999999 - storeProduct.getStockPCS()) {
							judgePcs = false;
							session.setAttribute("state", "現在の入力値と現在庫数の合計が入力値の上限､99999999を超えています｡\\n"
									+ (99999999 - storeProduct.getStockPCS()) + "以下の整数値を入力してください｡");
						}
					} else if (storeProduct.getStockPCS() < storeProduct.getTnyukosu()) {
						if (pcs > 99999999 - storeProduct.getTnyukosu()) {
							judgePcs = false;
							session.setAttribute("state", "現在の入力値と､現時点の当月入庫数の合計が入力値の上限､99999999を超えています｡\\n"
									+ (99999999 - storeProduct.getTnyukosu()) + "以下の整数値を入力してください｡");
						}
					}
				} else {
					if (pcs > 99999999) {
						judgePcs = false;
						session.setAttribute("state", "現在の入力値が､入力値の上限､99999999を超えています｡\\n99999999以下の整数値を入力してください｡");
					}
				}
			}
		}
		return judgePcs;
	}

	private boolean purchasePriceCheck(HttpServletRequest request, HttpServletResponse response, String reqPara,
			int i) {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();
		boolean judgePrice = new CommonMethod().quantityCheck(request, response, reqPara, i);
		if (judgePrice == true) {
			int price = Integer.parseInt(request.getParameter(reqPara));
			if (price > 999999) {
				int[] alartNum = new int[10];
				alartNum[i - 1] = i;
				// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
				// 正常終了で｢0｣異常終了で｢1｣を返す
				int result = new CommonMethod().setAlart(request, response, alartNum);
				if (result == 0) {
					session.setAttribute("state", "規定以上の整数値が入力されています｡\\n入力内容をお確かめ下さい｡");
				} else {
					session.setAttribute("state", "アラートセットが異常終了しました｡");
				}
				judgePrice = false;
			}
		}
		return judgePrice;
	}

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
			// →入庫数｢pcs｣の値の妥当性は､
			// StoreProductDAOでのテーブル｢StoreProduct｣への登録の際に判定済みの為､この登録では判定しない｡
			// →テーブル｢GoodsReceipt｣は､入庫数を登録の為､入力最大値が｢99999999｣の為
			line += grDAO.updateToGoodsReceipt(gr, goodsReceipt);
		} else if (goodsReceipt == null) {
			// 日付･Janコードが一致するレコードがない場合､新規追加
			line += grDAO.insertToGoodsReceipt(gr);
		}
		return line;
	}

	public Purchase() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
