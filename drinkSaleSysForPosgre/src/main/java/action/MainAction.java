package action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ProductDrink;
import bean.StoreProduct;
import bean.Users;
import dao.ProductDrinkDAO;
import dao.StoreProductDAO;
import tool.Action;

public class MainAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// 画面遷移先のパス取得
		String toAction = request.getParameter("toAction");

		// ログイン状態確認(画面遷移前処理)
		// →セッション切れで､かつ購入画面｢index.jsp｣以外へ遷移指定の場合､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		//セッション切れ処理
		if (loginStatusCheck == 0) {
			if ((toAction == null) || toAction.equals("main/login.jsp") || (toAction.equals("main/index.jsp"))) {
			} else if (toAction != null) {
				session.setAttribute("loginState", "セッション切れの為､購入画面に移動しました｡");
				toAction = "main/index.jsp";
			}
		}

		// 遷移画面振り分け
		if (toAction == null) {
			toAction = "main/index.jsp";
		}
		session.setAttribute("nextJsp", toAction);
		return "/WEB-INF/" + toAction;
	}

	// 以下､メソッド=================================================================
	// ログイン状態確認/セッション属性｢loginState｣変更用メソッド
	// 戻り値｢0:ログアウト状態｣｢1:ログイン状態｣
	public int loginStatusCheck(HttpServletRequest request, HttpServletResponse response) {
		int judge = 0;
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("loginState", null);
		} else if (user != null) {
			session.setAttribute("loginState", user.getUserName() + "さんがログイン中です。");
			judge++;
		}
		return judge;
	}

	// 画面遷移/クリア押下時の属性リセット用メソッド
	// →ログインを示す､セッション属性｢user｣以外を全てリセット
	// return 整数 「0：失敗」「1：成功」
	public int clearAttribute(HttpServletRequest request, HttpServletResponse response) {
		int judge = 0;
		HttpSession session = request.getSession();
		// 状態表示関係
		String[] alart = new String[10];
		session.setAttribute("alart", alart);
		session.setAttribute("state", null);

		// リクエストパラメータ格納用bean(画面に対し1つ)
		// →フィールドは全てString
		session.setAttribute("G_index", null);
		session.setAttribute("G_purchase", null);
		session.setAttribute("G_productMaster", null);
		session.setAttribute("G_earnings", null);
		session.setAttribute("G_userRegister", null);
		session.setAttribute("G_userRegisterLogin", null);

		// 処理選択関係(JavaScriptメソッド｢confirm()｣で処理選択)
		session.setAttribute("selectExecute", null);
		session.setAttribute("nextJsp", null);
		session.setAttribute("toAction", null);

		// DAO処理用bean
		// 属性名､フルネームがDBの戻り値､省略名がリクエストパラメータを格納したbean
		// →リクエストパラメータは､入力値の型違いで格納不可がある為､
		// 別にフィールドが全てStringの､正規表現で入力値チェックを行う前のリクエストパラメータを格納するbeanを用意している
		session.setAttribute("us", null);
		session.setAttribute("productDrink", null);
		session.setAttribute("pd", null);
		session.setAttribute("storeProductLists", null);
		session.setAttribute("storeProductList", null);
		session.setAttribute("storeProduct", null);
		session.setAttribute("sp", null);
		session.setAttribute("goodsReceipt", null);
		session.setAttribute("gr", null);
		session.setAttribute("goodsIssue", null);
		session.setAttribute("gi", null);
		session.setAttribute("earningsList", null);
		session.setAttribute("earningsEtc", null);

		// 販売画面(index.jsp)での料金格納用
		session.setAttribute("totalPrice", null);

		// 商品マスタ(productMaster.jsp)でのボタン｢編集｣のフラグ用
		session.setAttribute("register", null);

		judge = 1;
		return judge;
	}

	// アラートセット用メソッド
	// 正常終了で｢0｣異常終了で｢1｣を返す
	// →引数｢int[] alartNums｣のは､1-10の値
	// →intの初期値｢0｣の為､1から始めないと､初期値を指定値と誤判断する
	public int setAlart(HttpServletRequest request, HttpServletResponse response, int[] alartNums) {
		int ret = 0;
		HttpSession session = request.getSession();
		// alart配列確保
		String[] alart = new String[10];
		// String[] alartNumsの要素数10個以上の場合のアラーム回避(ありえないケースだが念の為)
		// →alart配列に格納できるalart数は､最大10件
		int alartNum = alartNums.length;
		if (alartNum > 10) {
			alartNum = 10;
			ret++;
		}
		// String[] alartへ値を格納する
		String alartSentence = "<span class=\"label label-danger\">確認</span>";
		for (int i = 0; i < 10; i++) {
			int count = 0;
			for (int j = 0; j < alartNum; j++) {
				if (i == (alartNums[j] - 1)) {
					alart[i] = alartSentence;
					count++;
				}
			}
			if (count == 0) {
				alart[i] = null;
			}
		}
		session.setAttribute("alart", alart);
		return ret;
	}

	// テキストボックスに入力されてる値のパターンマッチングを行うメソッド
	// 引数として､｢request｣｢response｣以外に､
	// ･正規表現(regEx)
	// ･リクエストパラメータ名(reqParaName)
	// ･アラート位置番号
	// →アラートを出す項目番号を指定
	// →格納する値は1-10(使用時は､1差引いて使用)
	public boolean inputValueCheck(HttpServletRequest request, HttpServletResponse response, String reqParaName,
			String regEx) {
		// リクエストパラメータ(pcs)取得
		String reqPara = request.getParameter(reqParaName);
		// パターンマッチ準備
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(reqPara);
		// パターンマッチ判定
		boolean judge = matcher.matches();

		return judge;
	}

	// 戻り値は､リクエストパラメータに記述のJanコードが存在する場合｢true｣､そうでない場合｢false｣
	public boolean janCodeCheck(HttpServletRequest request, HttpServletResponse response, String reqParaName,
			String regEx, int alartNo) {

		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// 入力値チェック(正規表現を使用)
		boolean judge = new MainAction().inputValueCheck(request, response, reqParaName, regEx);

		//入力値が空の場合は、判定しない
		String reqPara = request.getParameter(reqParaName);

		if (judge == false && !reqPara.equals("")) {
			int[] alartNum = new int[10];
			alartNum[alartNo - 1] = alartNo;
			// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
			// 正常終了で｢0｣異常終了で｢1｣を返す
			int result = new MainAction().setAlart(request, response, alartNum);
			if (result == 0) {
				session.setAttribute("state", "JANコードの入力基準(13桁の整数)を満たしていません｡\\n入力内容をお確かめ下さい｡");
			} else {
				session.setAttribute("state", "アラートセットが異常終了しました｡");
			}
		} else if (judge == true) {
			judge = false;
			// リクエストパラメータ(janCode)を取得
			String janCode = request.getParameter(reqParaName);
			// ProductDrinkのインスタンスへリクエストパラメータ格納
			ProductDrink pd = new ProductDrink();
			pd.setJanCode(janCode);
			// リクエストパラメータを格納したbeanをセッション属性｢pd｣に格納
			session.setAttribute("pd", pd);

			// DB照合
			ProductDrink productDrink = new ProductDrinkDAO().searchProductDrinkByJanCode(pd);
			if (productDrink != null) {
				// DB検索結果をセッション属性｢productDrink｣に格納
				session.setAttribute("productDrink", productDrink);
				//
				judge = true;
			} else {
				// アラートを出す項目番号を指定
				// →格納する値は1-10(使用時は､1差引いて使用)
				int[] alartNum = new int[10];
				alartNum[0] = 1;
				// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
				// 正常終了で｢0｣異常終了で｢1｣を返す
				int result = new MainAction().setAlart(request, response, alartNum);
				if (result == 0) {
					session.setAttribute("state", "該当Janコードの商品は取り扱っておりません。\\nJanコードをお確かめください。");
				} else {
					session.setAttribute("state", "Janコード確認が異常終了しました｡");
				}
			}
		}
		return judge;
	}

	// 戻り値は､個数欄の記述が妥当であれば｢true｣､そうでない場合｢false｣
	public boolean quantityCheck(HttpServletRequest request, HttpServletResponse response, String reqParaName,
			int alartNo) {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// 入力値チェック(正規表現を使用)
		boolean judge = new MainAction().inputValueCheck(request, response, reqParaName, "^\\d+$");

		//入力値が空の場合は、判定しない
		String reqPara = request.getParameter(reqParaName);

		if (judge == false && !reqPara.equals("")) {
			int[] alartNum = new int[10];
			alartNum[alartNo - 1] = alartNo;
			// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
			// 正常終了で｢0｣異常終了で｢1｣を返す
			int result = new MainAction().setAlart(request, response, alartNum);
			if (result == 0) {
				session.setAttribute("state", "個数に１以上の整数以外の値が入力されています｡\\n入力内容をお確かめ下さい｡");
			} else {
				session.setAttribute("state", "アラートセットが異常終了しました｡");
			}
		} else if (judge == true) {
			// リクエストパラメータ取得
			int reqParaNum = Integer.parseInt(request.getParameter(reqParaName));
			// 数字確認
			if (reqParaNum < 1) {
				// アラートを出す項目番号を指定
				// →格納する値は1-10(使用時は､1差引いて使用)
				int[] alartNum = new int[10];
				alartNum[alartNo - 1] = alartNo;
				// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
				// 正常終了で｢0｣異常終了で｢1｣を返す
				int result = new MainAction().setAlart(request, response, alartNum);
				if (result == 0) {
					session.setAttribute("state", "個数に１以上の整数以外の値が入力されています｡\\n入力内容をお確かめ下さい｡");
				} else {
					session.setAttribute("state", "アラートセットが異常終了しました｡");
				}
				judge = false;
			}
		}
		return judge;
	}

	// 戻り値は､個数欄の記述が妥当であれば｢true｣､そうでない場合｢false｣
	public boolean blankCheck(HttpServletRequest request, HttpServletResponse response, String reqParaName,
			String regEx, int alartNo) {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// 入力値チェック(正規表現を使用)
		boolean judge = new MainAction().inputValueCheck(request, response, reqParaName, regEx);
		if (judge == false) {
			int[] alartNum = new int[10];
			alartNum[alartNo - 1] = alartNo;
			// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
			// 正常終了で｢0｣異常終了で｢1｣を返す
			int result = new MainAction().setAlart(request, response, alartNum);
			if (result == 0) {
				session.setAttribute("state", "値が入力されていません。\\n入力内容をお確かめ下さい｡");
			} else {
				session.setAttribute("state", "アラートセットが異常終了しました｡");
			}
		}
		return judge;
	}

	// 戻り値は､個数欄の記述が妥当であれば｢true｣､そうでない場合｢false｣
	public boolean passwordCheck(HttpServletRequest request, HttpServletResponse response, String reqParaName,
			String regEx, int alartNo) {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// 入力値チェック(正規表現を使用)
		boolean judge = new MainAction().inputValueCheck(request, response, reqParaName, regEx);
		if (judge == false) {
			int[] alartNum = new int[10];
			alartNum[alartNo - 1] = alartNo;
			// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
			// 正常終了で｢0｣異常終了で｢1｣を返す
			int result = new MainAction().setAlart(request, response, alartNum);
			if (result == 0) {
				session.setAttribute("state", "パスワードが入力基準を満たしていません。\\n入力内容をお確かめ下さい｡");
			} else {
				session.setAttribute("state", "アラートセットが異常終了しました｡");
			}
		}
		return judge;
	}

	public void searchStock(HttpServletRequest request, HttpServletResponse response, String reqParaName) {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// リクエストパラメータ取得
		String reqPara = request.getParameter(reqParaName);

		// 商品在庫数を取得
		// リクエストパラメータをbeanにセットし､セッション属性｢sp｣に格納する
		StoreProduct sp = new StoreProduct();
		sp.setJanCode(reqPara);
		session.setAttribute("sp", sp);

		// DB検索
		StoreProductDAO dao = new StoreProductDAO();
		StoreProduct storeProduct = dao.searchStoreProductByJanCode(sp);
		if (storeProduct != null) {
			// DBの検索結果をセッション属性｢storeProduct｣へ格納
			session.setAttribute("storeProduct", storeProduct);
		}
	}
}
