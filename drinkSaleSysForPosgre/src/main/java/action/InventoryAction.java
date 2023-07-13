package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.StoreProductList;
import dao.StoreProductDAO;
import tool.Action;

public class InventoryAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// ログイン状態確認(ページ更新の際に有効なチェック)
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		//セッション切れ処理
		if (loginStatusCheck == 0) {
			session.setAttribute("loginState", "セッション切れの為､購入画面に移動しました｡");
			return "/WEB-INF/main/index.jsp";
		}

		// テーブル｢StoreProduct｣のリスト取得
		StoreProductDAO spDAO = new StoreProductDAO();
		List<StoreProductList> storeProductLists = spDAO.storeProductAllRecords();

		// セッション属性｢storeProductList｣にリスト｢storeProductList｣を格納
		session.setAttribute("storeProductLists", storeProductLists);
		return "/WEB-INF/main/inventory.jsp";
	}

}
