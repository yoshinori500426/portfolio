package field;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_index;
import bean.GoodsIssue;
import bean.ProductDrink;
import bean.StoreProduct;
import dao.DAO;
import dao.GoodsIssueDAO;
import dao.StoreProductDAO;
import tool.CommonMethod;

/**
 * Servlet implementation class Salses
 */
@WebServlet("/Sales")
public class Sales extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 処理結果アウトプット準備
		HttpSession session = request.getSession();

		// 処理前に必要なセッション属性のクリア
		session.setAttribute("loginState", null);
		new CommonMethod().clearAttribute(request, response);

		// 画面入力値をString形式でbeanに格納し､セッション属性｢G_index｣へ格納
		G_index G_index = new G_index();
		G_index.setJanCode(request.getParameter("jan"));
		G_index.setPcs(request.getParameter("pcs"));
		session.setAttribute("G_index", G_index);

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		//セッション属性｢process｣に｢getParameter("toAction")｣の値を格納する
		//　→セッション属性｢toAction｣は､メソッド｢new CommonMethod().dispatchToActionByInputOutputScreenClass()｣で､
		//　　｢main/〇〇.jsp｣の値が格納される
		session.setAttribute("process", toAction);

		// 入力値チェック用変数定義
		boolean judgeJanCode = false;
		boolean judgeQuantity = false;

		// 処理種により､処理を分岐
		switch (toAction) {
		case "janCodeCheck":
			judgeQuantity = new CommonMethod().quantityCheck(request, response, "pcs", 2);
			judgeJanCode = new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new CommonMethod().searchStock(request, response, "jan");
			}
			if (judgeJanCode == true && judgeQuantity == true) {
				stockJudge(request, response, "pcs", 2);
			}
			break;
		case "calcPrice":
			judgeJanCode = new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new CommonMethod().searchStock(request, response, "jan");
			}
			judgeQuantity = new CommonMethod().quantityCheck(request, response, "pcs", 2);
			if (judgeJanCode == true && judgeQuantity == true) {
				stockJudge(request, response, "pcs", 2);
			}
			break;
		case "salesProcess":
			// 入力有無確認pcs
			String jan = request.getParameter("jan");
			String pcs = request.getParameter("pcs");
			if (jan.equals("") && pcs.equals("")) {
				session.setAttribute("state", "JANコード/個数が入力されていません。");
				break;
			} else if (jan.equals("")) {
				session.setAttribute("state", "JANコードが入力されていません。");
				break;
			}
			if (pcs.equals("")) {
				session.setAttribute("state", "個数が入力されていません。");
				break;
			}

			// 入力値確認
			judgeQuantity = new CommonMethod().quantityCheck(request, response, "pcs", 2);
			judgeJanCode = new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new CommonMethod().searchStock(request, response, "jan");
			}
			if (judgeJanCode == true && judgeQuantity == true) {
				boolean judge = stockJudge(request, response, "pcs", 2);
				if (judge == false) {
					break;
				}
			} else {
				break;
			}

			PrintWriter out = response.getWriter();
			try {
				// トランザクション処理準備
				DAO dao = new DAO();
				Connection con = dao.getConnection();
				// 排他制御
				synchronized (this) {
					// トランザクション処理開始
					con.setAutoCommit(false);
					// DB処理
					int line = salesProcess(request, response);

					// 成功/失敗判定
					if (line == 2) {
						con.commit();
						// new CommonMethod().clearAttribute(request, response);
						// 在庫数の変更
						new CommonMethod().searchStock(request, response, "jan");
						// 個数のクリア
						G_index.setPcs("");
						session.setAttribute("G_index", G_index);
						session.setAttribute("state", "処理が正常に終了しました｡");
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
		case "salesProcessAft":
			judgeJanCode = new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			if (judgeJanCode == true) {
				new CommonMethod().searchStock(request, response, "jan");
			}
			G_index = (G_index) session.getAttribute("G_index");
			G_index.setPcs("");
			session.setAttribute("G_index", G_index);
			break;
		case "cancel":
		default:
			// セッション属性｢session｣以外を全てリセット
			new CommonMethod().clearAttribute(request, response);
			break;
		}
		// クラス｢InputOutputScreen｣を使用した画面遷移
		new CommonMethod().dispatchToActionByInputOutputScreenClass("main/sales.jsp", request, response);
	}

	// 以下､メソッド=================================================================
	// 戻り値は､リクエストパラメータに記述の個数が存在する場合｢true｣､そうでない場合｢false｣
	private boolean stockJudge(HttpServletRequest request, HttpServletResponse response, String reqParaName,
			int alartNo) {
		boolean judge = true;

		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// リクエストパラメータ取得
		int reqPara = Integer.parseInt(request.getParameter(reqParaName));
		// セッション属性｢productDrink｣に格納しているインスタンスを取得
		ProductDrink productDrink = (ProductDrink) session.getAttribute("productDrink");

		// セッション属性｢sp｣に個数を格納する
		StoreProduct sp = (StoreProduct) session.getAttribute("sp");
		sp.setTsyukosu(reqPara);
		session.setAttribute("sp", sp);

		// 在庫数を取得
		StoreProduct storeProduct = (StoreProduct) session.getAttribute("storeProduct");
		int stockPCS = storeProduct.getStockPCS();

		// 在庫が不足している場合
		if (reqPara > stockPCS) {
			// アラートを出す項目番号を指定
			// →格納する値は1-10(使用時は､1差引いて使用)
			int[] alartNum = new int[10];
			alartNum[0] = alartNo;
			// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
			// 正常終了で｢0｣異常終了で｢1｣を返す
			int result = new CommonMethod().setAlart(request, response, alartNum);
			if (result == 0) {
				session.setAttribute("state", "在庫以上の数量が入力されています。\\n在庫は､" + stockPCS + "個です｡");
			} else {
				session.setAttribute("state", "アラートセットが異常終了しました｡");
			}
			judge = false;
		} else if (reqPara <= 0) {
			judge = false;
		} else {
			// セッション属性｢totalPrice｣に合計金額を格納する
			session.setAttribute("totalPrice", (productDrink.getUnitPrice() * reqPara));
		}
		return judge;
	}

	// 戻り値は､DB処理が「-1：失敗」「2：成功(テーブル｢STORE_PRODUCT｣の成功で｢+1｣､テーブル｢GOODS_ISSUE｣の成功で｢+1｣)」
	private int salesProcess(HttpServletRequest request, HttpServletResponse response) {
		int judge = -1;

		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// セッション属性｢productDrink｣｢storeProduct｣に格納しているDB戻り値のインスタンス取得
		ProductDrink productDrink = (ProductDrink) session.getAttribute("productDrink");
		StoreProduct storeProduct = (StoreProduct) session.getAttribute("storeProduct");

		// テーブル｢STORE_PRODUCT｣処理
		// セッション属性｢sp｣に格納しているインスタンス取得(calcPrice()で作成済み)
		StoreProduct sp = (StoreProduct) session.getAttribute("sp");
		// テーブル｢STORE_PRODUCT｣処理
		StoreProductDAO spDAO = new StoreProductDAO();
		// 処理成功で変数「judge」に「1」が格納
		judge = spDAO.updateToStoreProduct(sp, storeProduct);

		// テーブル｢GOODS_ISSUE｣処理
		// 今日の日付文字列取得
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String today = sdf.format(cl.getTime());
		// ｢GoodsIssue｣インスタンスに､リクエストパラメータを格納
		GoodsIssue gi = new GoodsIssue(); // リクエストパラメータ格納用bean
		gi.setJanCode(productDrink.getJanCode());
		gi.setSyukoYMD(today);
		gi.setSyukoPCS(sp.getTsyukosu());
		session.setAttribute("gi", gi);

		// ｢update｣｢insert｣処理
		// →メソッド｢searchGoodsIssueByJanCodeAndSyukoYMD()｣の戻り値が､
		// null以外で｢update｣､nullで｢insert｣
		GoodsIssueDAO giDAO = new GoodsIssueDAO();
		GoodsIssue goodsIssue = giDAO.searchGoodsIssueByJanCodeAndSyukoYMD(gi);
		if (goodsIssue != null) {
			session.setAttribute("goodsIssue", goodsIssue);
			judge += giDAO.updateToGoodsIssue(gi, goodsIssue);
		} else if (goodsIssue == null) {
			judge += giDAO.insertToGoodsIssue(gi);
		}
		return judge;
	}

	public Sales() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
