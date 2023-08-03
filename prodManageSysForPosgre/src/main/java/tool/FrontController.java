package tool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.action")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			String path = request.getServletPath().substring(1);
			String name = path.replace(".a", "A").replace('/', '.');
			Action action = (Action) Class.forName(name).newInstance();
			String url = action.execute(request, response);
			request.setAttribute("nextJsp", url);
			
//			// 動作確認用の処理(portfolioとして成立させる為､テーブル情報を公開する事が目的)
//			// 以下項目をセッション属性として挙げる
//			// 画面毎に必要な情報は変化するが､今回は､必要な全ての情報をセッション属性に挙げ､JSPファイルのEL式で表示内容を切り替える仕様とする
//			// (セッション属性に挙げる段では場合分けしない)
//			// ①登録済みJanコード
//			List<ProductDrink> pdListPF = new ProductDrinkDAO().searchProductDrinkAll();
//			session.setAttribute("pdListPF", pdListPF);
//			// ②登録済みユーザ
//			List<Users> usListPF = new UsersDAO().searchUsersAll();
//			session.setAttribute("usListPF", usListPF);
			
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}

}
