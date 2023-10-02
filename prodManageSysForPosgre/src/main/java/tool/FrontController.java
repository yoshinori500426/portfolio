package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.MainAction;
import bean.ProductMaster;
import dao.ProductMasterDAO;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		try {
			String path = request.getServletPath().substring(1);
			String name = path.replace(".a", "A").replace('/', '.');
			Action action = (Action) Class.forName(name).newInstance();
			String url = action.execute(request, response);
			session.setAttribute("nextJsp", url);
			// 初期画面遷移のタイミングで以下画面のプルダウン用リスト取得要の為､このStepにコードを記述
			//   ｢/WEB-INF/main/stockList.jsp｣｢/WEB-INF/main/purchaseOrderList.jsp｣｢/WEB-INF/main/orderList.jsp｣｢/WEB-INF/main/entryExitInfoList.jsp｣
			@SuppressWarnings("unchecked")
			List<ProductMaster> ProductMasterList = (List<ProductMaster>) session.getAttribute("ProductMasterList");
			if (ProductMasterList == null && (url.equals("/WEB-INF/main/stockList.jsp")
											|| url.equals("/WEB-INF/main/purchaseOrderList.jsp")
											|| url.equals("/WEB-INF/main/orderList.jsp")
											|| url.equals("/WEB-INF/main/entryExitInfoList.jsp"))) {
				// プルダウン用リスト取得
				ProductMasterDAO pmDAO = new ProductMasterDAO();
				ProductMasterList = pmDAO.searchAll();
				session.setAttribute("ProductMasterList", ProductMasterList);
			}
			// 動作確認用の処理(portfolioとして成立させる事を目的とした､テーブル情報を取得メソッド)
			new MainAction().getListsForPortfolio(request);
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}

}
