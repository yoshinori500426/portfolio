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

import bean.G_productMaster;
import bean.ProductDrink;
import bean.StoreProduct;
import bean.Users;
import dao.DAO;
import dao.ProductDrinkDAO;
import dao.StoreProductDAO;
import tool.CommonMethod;

/**
 * Servlet implementation class ProductMaster
 */
@WebServlet("/ProductMaster")
public class ProductMaster extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// ログイン後の処理前用メソッド(①不要セッション属性nullクリア②ログイン状態確認)
		new CommonMethod().actionBefProc(request, response);

		// 画面入力値をString形式でbeanに格納し､セッション属性｢G_productMaster｣へ格納
		G_productMaster G_productMaster = new G_productMaster();
		G_productMaster.setJanCode(request.getParameter("jan"));
		G_productMaster.setName(request.getParameter("name"));
		G_productMaster.setMaker(request.getParameter("maker"));
		G_productMaster.setContents(request.getParameter("contents"));
		G_productMaster.setDept(request.getParameter("dept"));
		G_productMaster.setUnit(request.getParameter("unit"));
		G_productMaster.setPrice(request.getParameter("price"));
		session.setAttribute("G_productMaster", G_productMaster);

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String register = request.getParameter("register");
		// セッション属性｢process｣に｢getParameter("toAction")｣の値を格納する
		// →セッション属性｢toAction｣は､メソッド｢new
		// CommonMethod().dispatchToActionByInputOutputScreenClass()｣で､
		// ｢main/〇〇.jsp｣の値が格納される
		session.setAttribute("process", toAction);
		session.setAttribute("register", register);

		// 入力値チェック用変数定義
		boolean judgeJanCodeCheckOnly = false;
		boolean judgeNumCheck = false;
		int reqPara = 0;

		// 処理種により､処理を分岐
		switch (toAction) {
		case "janCodeCheck":
			new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			String stateCheck = (String) session.getAttribute("state");
			if (stateCheck != null && stateCheck.equals("該当Janコードの商品は取り扱っておりません。\\nJanコードをお確かめください。")) {
				String[] alart = new String[10];
				session.setAttribute("alart", alart);
				session.setAttribute("state", null);
			}
			break;
		case "janCodeCheckOnly":
			// 入力したJanコードがテーブルに存在すれば更新(＝Update)、存在しなければ新規追加(＝insert)と判断する
			// Janコードが13桁の数値か否かを判定
			judgeJanCodeCheckOnly = new CommonMethod().inputValueCheck(request, response, "jan", "^\\d{13}$");
			if (judgeJanCodeCheckOnly == false) {
				session.setAttribute("state", "Janコードに異常値が入力されています｡\\n｢編集｣は､Janコード欄に13桁の数字が打ち込まれていないと有効になりません。");
				session.setAttribute("register", "NG");
			}
			break;
		case "nameCheck":
			new CommonMethod().blankCheck(request, response, "name", ".{1,100}", 2);
			break;
		case "makerCheck":
			new CommonMethod().blankCheck(request, response, "maker", ".{1,50}", 3);
			break;
		case "contentsCheck":
			judgeNumCheck = new CommonMethod().quantityCheck(request, response, "contents", 4);
			if (judgeNumCheck == true) {
				reqPara = Integer.parseInt(request.getParameter("contents"));
				if (reqPara > 99999) {
					int[] alartNum = new int[10];
					alartNum[4 - 1] = 4;
					// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
					// 正常終了で｢0｣異常終了で｢1｣を返す
					int result = new CommonMethod().setAlart(request, response, alartNum);
					if (result == 0) {
						session.setAttribute("state", "規定以上の整数値が入力されています｡\\n入力内容をお確かめ下さい｡");
					} else {
						session.setAttribute("state", "アラートセットが異常終了しました｡");
					}
				}
			}
			break;
		case "deptCheck":
			new CommonMethod().blankCheck(request, response, "dept", ".{1,50}", 5);
			break;
		case "unitCheck":
			new CommonMethod().blankCheck(request, response, "unit", ".{1,5}", 6);
			break;
		case "priceCheck":
			judgeNumCheck = new CommonMethod().quantityCheck(request, response, "price", 7);
			if (judgeNumCheck == true) {
				reqPara = Integer.parseInt(request.getParameter("price"));
				if (reqPara > 9999) {
					int[] alartNum = new int[10];
					alartNum[7 - 1] = 7;
					// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
					// 正常終了で｢0｣異常終了で｢1｣を返す
					int result = new CommonMethod().setAlart(request, response, alartNum);
					if (result == 0) {
						session.setAttribute("state", "規定以上の整数値が入力されています｡\\n入力内容をお確かめ下さい｡");
					} else {
						session.setAttribute("state", "アラートセットが異常終了しました｡");
					}
				}
			}
			break;
		case "productRecordProcess":
			// リクエストパラメータ｢register｣の値が｢NG｣の場合､更新/新規追加させない
			if (register.equals("NG")) {
				session.setAttribute("state", "編集可能状態ではありません｡\\nリンク｢編集｣を押下し､全ての項目入力後､リンク｢確定｣を押下して下さい｡");
				break;
			}
			// リクエストパラメータの妥当性を確認し､ProductDrinkクラスのインスタンスへ値を格納する
			boolean valueCheck = valueCheckAndInstall(request, response);
			if (valueCheck == false) {
				session.setAttribute("state", "入力値に異常があります｡\\n入力値を確認して下さい｡");
				break;
			}

			PrintWriter out = response.getWriter();
			try {
				// レコード更新/新規追加
				// トランザクション処理準備
				DAO dao = new DAO();
				Connection con = dao.getConnection();
				// 排他制御
				synchronized (this) {
					// トランザクション処理開始
					con.setAutoCommit(false);
					// DB処理
					int line = productRecordProcess(request, response);
					// 成功/失敗判定
					if (line == 2) {
						con.commit();
						// new CommonMethod().clearAttribute(request, response);
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
		case "productRecordProcessAft":
			new CommonMethod().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			session.setAttribute("register", "NG");
			stateCheck = (String) session.getAttribute("state");
			if (stateCheck != null && stateCheck.equals("該当Janコードの商品は取り扱っておりません。\\nJanコードをお確かめください。")) {
				String[] alart = new String[10];
				session.setAttribute("alart", alart);
				session.setAttribute("state", null);
			}
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
		new CommonMethod().dispatchToActionByInputOutputScreenClass("main/productMaster.jsp", request, response);
	}

	// 以下､メソッド=================================================================
	//janコード以外の入力値確認メソッド
	//戻り値　｢false:入力値に問題あり｣｢true:入力値に問題なし｣
	//　→｢true:入力値に問題なし｣で､リクエストパラメータを格納したbean｢ProductDrink｣のインスタンスをセッション属性｢productDrink｣へ代入
	private boolean valueCheckAndInstall(HttpServletRequest request, HttpServletResponse response) {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		boolean judge = false;
		int reqPara = 0;
		// リクエストパラメータチェック
		// 他の入力項目の入力チェック
		boolean priceQuantityCheck = new CommonMethod().quantityCheck(request, response, "price", 7);
		if (priceQuantityCheck == true) {
			reqPara = Integer.parseInt(request.getParameter("price"));
			if (reqPara > 9999) {
				int[] alartNum = new int[10];
				alartNum[7 - 1] = 7;
				// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
				// 正常終了で｢0｣異常終了で｢1｣を返す
				int result = new CommonMethod().setAlart(request, response, alartNum);
				if (result == 0) {
					priceQuantityCheck = false;
					session.setAttribute("state", "規定以上の整数値が入力されています｡\\n入力内容をお確かめ下さい｡");
				} else {
					session.setAttribute("state", "アラートセットが異常終了しました｡");
				}
			}
		}
		boolean unitBlankCheck = new CommonMethod().blankCheck(request, response, "unit", ".{1,5}", 6);
		boolean deptBlankCheck = new CommonMethod().blankCheck(request, response, "dept", ".{1,50}", 5);
		boolean contentsQuantityCheck = new CommonMethod().quantityCheck(request, response, "contents", 4);
		if (contentsQuantityCheck == true) {
			reqPara = Integer.parseInt(request.getParameter("contents"));
			if (reqPara > 99999) {
				int[] alartNum = new int[10];
				alartNum[4 - 1] = 4;
				// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
				// 正常終了で｢0｣異常終了で｢1｣を返す
				int result = new CommonMethod().setAlart(request, response, alartNum);
				if (result == 0) {
					contentsQuantityCheck = false;
					session.setAttribute("state", "規定以上の整数値が入力されています｡\\n入力内容をお確かめ下さい｡");
				} else {
					session.setAttribute("state", "アラートセットが異常終了しました｡");
				}
			}
		}
		boolean makerBlankCheck = new CommonMethod().blankCheck(request, response, "maker", ".{1,50}", 3);
		boolean nameBlankCheck = new CommonMethod().blankCheck(request, response, "name", ".{1,100}", 2);

		if (nameBlankCheck && makerBlankCheck && contentsQuantityCheck && deptBlankCheck
				&& unitBlankCheck && priceQuantityCheck == true) {
			// bean｢ProductDrink｣のインスタンスへリクエストパラメータを格納
			ProductDrink pd = new ProductDrink();
			pd.setJanCode(request.getParameter("jan"));
			pd.setName(request.getParameter("name"));
			pd.setMaker(request.getParameter("maker"));
			pd.setContents(Integer.parseInt(request.getParameter("contents")));
			pd.setDept(request.getParameter("dept"));
			pd.setUnit(request.getParameter("unit"));
			pd.setUnitPrice(Integer.parseInt(request.getParameter("price")));
			// リクエストパラメータを格納したbean｢ProductDrink｣のインスタンスをセッション属性｢productDrink｣へ代入
			session.setAttribute("pd", pd);
			// 戻り値を「true」に変更
			judge = true;
		}
		return judge;
	}

	// 戻り値は､DB処理が「-1：失敗」「2：成功(テーブル｢STORE_PRODUCT｣の成功で｢+1｣､テーブル｢GOODS_ISSUE｣の成功で｢+1｣)」
	private int productRecordProcess(HttpServletRequest request, HttpServletResponse response) {
		int line = -1;
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();
		// 処理用DAO準備
		ProductDrinkDAO pdDAO = new ProductDrinkDAO();
		StoreProductDAO spDAO = new StoreProductDAO();

		// 操作しているユーザ情報取得
		Users user = (Users) session.getAttribute("user");
		// リクエストパラメータを格納したbean｢ProductDrink｣のインスタンスを取得
		ProductDrink pd = (ProductDrink) session.getAttribute("pd");

		// テーブル｢ProductDrink｣処理
		// →テーブル｢ProductDrink｣にレコードが存在すれば､テーブル｢StoreProduct｣にも存在するはずだが､念のため､両テーブルの登録状況確認を行う)
		// →戻り値がnullならレコードの新規追加､nulでなければレコードの新規追加
		ProductDrink productDrink = pdDAO.searchProductDrinkByJanCode(pd);
		if (productDrink == null) {
			// レコードの新規追加
			line = pdDAO.insertToProductDrink(pd, user);
		} else if (productDrink != null) {
			session.setAttribute("productDrink", productDrink);
			// レコードの項目更新
			line = pdDAO.updateToProductDrink(pd, user);
		}

		// テーブル｢StoreProduct｣処理準備
		// リクエストパラメータを｢StoreProduct｣のbeanへ格納
		// →この処理は､レコードの固定部の新規作成/更新の為､入庫数/出庫数は｢0｣とする
		StoreProduct sp = new StoreProduct();
		sp.setJanCode(pd.getJanCode());
		sp.setTnyukosu(0);
		sp.setTsyukosu(0);
		session.setAttribute("sp", sp);
		// テーブル｢StoreProduct｣処理
		// →テーブル｢ProductDrink｣にレコードが存在すれば､テーブル｢StoreProduct｣にも存在するはずだが､念のため､両テーブルの登録状況確認を行う
		// →戻り値がnullならレコードの新規追加､nullでなければレコードの新規追加
		StoreProduct storeProduct = spDAO.searchStoreProductByJanCode(sp);
		if (storeProduct == null) {
			// レコードの新規追加
			line += spDAO.insertToStoreProduct(sp);
		} else if (storeProduct != null) {
			session.setAttribute("storeProduct", storeProduct);
			// レコードの項目更新
			line += spDAO.updateToStoreProduct(sp, storeProduct);
		}
		return line;
	}

	public ProductMaster() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
