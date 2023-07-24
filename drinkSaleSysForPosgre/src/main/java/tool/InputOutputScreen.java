package tool;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ProductDrink;
import bean.Users;
import dao.CreateTableDAO;
import dao.ProductDrinkDAO;
import dao.UsersDAO;

/**
 * 画面遷移用クラス
 */
@WebServlet("/InputOutputScreen")
public class InputOutputScreen extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String toAction = "";
		String toActionFromAttri = (String) session.getAttribute("toAction");
		if (toActionFromAttri == null) {
			toActionFromAttri = "";
		}
		String toActionFromPara = request.getParameter("toAction");
		if (toActionFromPara == null) {
			toActionFromPara = "";
		}
		// toActionFromPara
		// →値が｢fromIndexToSales｣：｢index.html→sales.jsp｣の画面遷移
		// session属性の｢toAction｣
		// →値取得できた：｢〇〇.jsp→〇〇.java→〇〇.jsp｣の画面遷移
		// →session属性のクリア禁止
		// →値取得できず:｢〇〇.jsp→△△.jsp｣の画面遷移
		// →session属性のクリア必要
		if (toActionFromPara.equals("fromIndex")) {
			toAction = "main/sales.jsp";
			// 画面遷移でリセット必要なセッション属性のクリア(画面遷移前処理)
			// ログイン情報削除
			session.setAttribute("user", null);
			session.setAttribute("loginState", null);
			new CommonMethod().clearAttribute(request, response);

			// 動作確認用テーブル作成
			CreateTableDAO ctDAO = new CreateTableDAO();
			int line = 0;
			line += ctDAO.createTable();
			line += ctDAO.insertProductDrinkTable();
			line += ctDAO.insertStoreProductTable();
			line += ctDAO.insertGoodsReceiptTable();
			line += ctDAO.insertGoodsIssueTable();
			line += ctDAO.insertMonthDataTable();
			line += ctDAO.insertUsersTable();
			if (line != (6 + 9 + 9 + 333 + 333 + 19 + 3)) {
				session.setAttribute("state", "動作確認用テーブルが正常に作成されていません｡\\n再度､飲料水販売システムに入り直して下さい｡");
			}
		} else if ((toActionFromAttri.length() >= 5 && toActionFromPara.length() >= 5)
				&& (toActionFromAttri.substring(0, 5).equals("main/")
						&& toActionFromPara.substring(0, 5).equals("main/"))
				&& !toActionFromAttri.equals(toActionFromPara)) {
			toAction = toActionFromPara;
			// 画面遷移でリセット必要なセッション属性のクリア(画面遷移前処理)
			session.setAttribute("loginState", null);
			new CommonMethod().clearAttribute(request, response);
		} else if (toActionFromAttri.equals("") || toActionFromAttri.equals(toActionFromPara)) {
			toAction = toActionFromPara;
			// 画面遷移でリセット必要なセッション属性のクリア(画面遷移前処理)
			session.setAttribute("loginState", null);
			new CommonMethod().clearAttribute(request, response);
		} else {
			toAction = toActionFromAttri;
		}

		// ログイン状態確認(画面遷移前処理)
		// →セッション切れで､かつ購入画面｢seles.jsp｣以外へ遷移指定の場合､購入画面｢seles.jsp｣へ遷移
		int loginStatusCheck = new CommonMethod().loginStatusCheck(request, response);
		// セッション切れ処理
		if (loginStatusCheck == 0) {
			if ((toAction == null) || toAction.equals("main/login.jsp") || (toAction.equals("main/sales.jsp"))) {
			} else if (toAction != null) {
				session.setAttribute("loginState", "セッション切れの為､購入画面に移動しました｡");
				toAction = "main/sales.jsp";
			}
		}
		// 動作確認用の処理(portfolioとして成立させる為､テーブル情報を公開する事が目的)
		// 以下項目をセッション属性として挙げる
		// 画面毎に必要な情報は変化するが､今回は､必要な全ての情報をセッション属性に挙げ､JSPファイルのEL式で表示内容を切り替える仕様とする
		// (セッション属性に挙げる段では場合分けしない)
		// ①登録済みJanコード
		List<ProductDrink> pdListPF = new ProductDrinkDAO().searchProductDrinkAll();
		session.setAttribute("pdListPF", pdListPF);
		// ②登録済みユーザ
		List<Users> usListPF = new UsersDAO().searchUsersAll();
		session.setAttribute("usListPF", usListPF);

		// 画面遷移アクション
		new CommonMethod().dispatchToAction(toAction, request, response);
	}

	public InputOutputScreen() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
