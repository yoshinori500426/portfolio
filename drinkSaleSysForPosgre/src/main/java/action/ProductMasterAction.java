package action;

import java.sql.Connection;

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
import tool.Action;

public class ProductMasterAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// DB操作前のログイン状態確認
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		//セッション切れ処理
		if (loginStatusCheck == 0) {
			session.setAttribute("loginState", "セッション切れの為､購入画面に移動しました｡");
			return "/WEB-INF/main/index.jsp";
		}

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
		session.setAttribute("toAction", toAction);
		session.setAttribute("register", register);

		//入力値チェック用変数定義
		boolean judgeJanCodeCheckOnly = false;

		// 処理種により､処理を分岐
		switch (toAction) {
		case "janCodeCheck":
			new MainAction().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			break;
		case "janCodeCheckOnly":
			//入力したJanコードがテーブルに存在すれば更新(＝Update)、存在しなければ新規追加(＝insert)と判断する
			//Janコードが13桁の数値か否かを判定
			judgeJanCodeCheckOnly = new MainAction().inputValueCheck(request, response, "jan", "^\\d{13}$");
			if (judgeJanCodeCheckOnly == false) {
				session.setAttribute("state",
						"Janコードに異常値が入力されています｡\\n｢編集｣は､次の場合でないと有効になりません。\\n　-Janコード欄に13桁の数字が打ち込まれている\\n　-Janコード欄が空欄");
				session.setAttribute("register", "NG");
			}
			break;
		case "nameCheck":
			new MainAction().blankCheck(request, response, "name", ".+", 2);
			break;
		case "makerCheck":
			new MainAction().blankCheck(request, response, "maker", ".+", 3);
			break;
		case "contentsCheck":
			new MainAction().quantityCheck(request, response, "contents", 4);
			break;
		case "deptCheck":
			new MainAction().blankCheck(request, response, "dept", ".+", 5);
			break;
		case "unitCheck":
			new MainAction().blankCheck(request, response, "unit", ".+", 6);
			break;
		case "priceCheck":
			new MainAction().quantityCheck(request, response, "price", 7);
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
					//new MainAction().clearAttribute(request, response);
					session.setAttribute("state", "処理が正常に終了しました｡");
				} else {
					con.rollback();
					session.setAttribute("state", "処理中に異常が発生しました｡\\n処理は行われていません｡");
				}
				// トランザクション処理終了
				con.setAutoCommit(true);
			}
			break;
		case "productRecordProcessAft":
			new MainAction().janCodeCheck(request, response, "jan", "^\\d{13}$", 1);
			session.setAttribute("register", "NG");
			break;
		case "cancel":
			// セッション属性｢session｣「loginState」以外を全てリセット
			new MainAction().clearAttribute(request, response);
			break;
		default:
			// セッション属性｢session｣「loginState」以外を全てリセット
			new MainAction().clearAttribute(request, response);
		}
		return "/WEB-INF/main/productMaster.jsp";
	}

	// 以下､メソッド=================================================================
	private boolean valueCheckAndInstall(HttpServletRequest request, HttpServletResponse response) {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		boolean judge = false;
		boolean JanCodeCheck = true;
		// リクエストパラメータチェック
		//他の入力項目の入力チェック
		boolean priceQuantityCheck = new MainAction().quantityCheck(request, response, "price", 7);
		boolean unitBlankCheck = new MainAction().blankCheck(request, response, "unit", ".+", 6);
		boolean deptBlankCheck = new MainAction().blankCheck(request, response, "dept", ".+", 5);
		boolean contentsQuantityCheck = new MainAction().quantityCheck(request, response, "contents", 4);
		boolean makerBlankCheck = new MainAction().blankCheck(request, response, "maker", ".+", 3);
		boolean nameBlankCheck = new MainAction().blankCheck(request, response, "name", ".+", 2);
		//入力したJanコードがテーブルに存在すれば更新(＝Update)、存在しなければ新規追加(＝insert)と判断する
		//Janコードが13桁の数値か否かを判定
		JanCodeCheck = new MainAction().inputValueCheck(request, response, "jan", "^\\d{13}$");
		if (JanCodeCheck == false) {
			session.setAttribute("state",
					"Janコードに異常値が入力されています｡\\n｢編集｣は､次の場合でないと有効になりません。\\n　-Janコード欄に13桁の数字が打ち込まれている\\n　-Janコード欄が空欄");
			session.setAttribute("register", "NG");
		}

		if (JanCodeCheck && nameBlankCheck && makerBlankCheck && contentsQuantityCheck && deptBlankCheck
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
			//戻り値を「true」に変更
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

}
