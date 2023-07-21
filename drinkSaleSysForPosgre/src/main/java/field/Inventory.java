package field;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.StoreProductList;
import dao.StoreProductDAO;
import tool.CommonMethod;

/**
 * Servlet implementation class Inventory
 */
@WebServlet("/Inventory")
public class Inventory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();
		
		// ログイン後の処理前用メソッド(①不要セッション属性nullクリア②ログイン状態確認)
		new CommonMethod().actionBefProc(request, response);

		// テーブル｢StoreProduct｣のリスト取得
		StoreProductDAO spDAO = new StoreProductDAO();
		List<StoreProductList> storeProductLists = spDAO.storeProductAllRecords();

		// セッション属性｢storeProductList｣にリスト｢storeProductList｣を格納
		session.setAttribute("storeProductLists", storeProductLists);
		// クラス｢InputOutputScreen｣を使用した画面遷移
		new CommonMethod().dispatchToActionByInputOutputScreenClass("main/inventory.jsp", request, response);
	}

	public Inventory() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
