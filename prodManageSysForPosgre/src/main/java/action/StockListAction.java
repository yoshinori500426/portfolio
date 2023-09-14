package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_StockList;
import bean.ProductMaster;
import bean.PuroductStock;
import dao.ProductMasterDAO;
import dao.PuroductStockDAO;
import dao.StockListDAO;
import tool.Action;

public class StockListAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// ログイン状態確認
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		// セッション切れ処理
		if (loginStatusCheck == 0) {
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			// メッセージ作成
			session.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
			// 画面遷移先登録
			session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
			return "/WEB-INF/main/login.jsp";
		}
		// 各メッセージリセット
		session.setAttribute("alert", null);
		session.setAttribute("message", null);
		session.setAttribute("state", null);
		// 使用DAOインスタンス取得
		ProductMasterDAO pmDAO = new ProductMasterDAO();
		PuroductStockDAO psDAO = new PuroductStockDAO();
		StockListDAO slDAO = new StockListDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		ProductMaster ProductMaster = null;
		List<ProductMaster> ProductMasterList = null;
		PuroductStock PuroductStock = null;
		List<G_StockList> G_StockListAllByProductNo = null;
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面の入力内容取得
		G_StockList G_StockList = new G_StockList();
		G_StockList.setProductNo(request.getParameter("productNo"));
		G_StockList.setProductName(request.getParameter("productName"));
		if (!request.getParameter("stockQty").isEmpty()) {
			G_StockList.setStockQty(Integer.parseInt(request.getParameter("stockQty")));
		}
		session.setAttribute("G_StockList", G_StockList);
		switch (toAction) {
		case "searchProductMasterList":
			// ｢ProductMasterList｣取得のみの為､caseでは処理を行わない
			break;
		case "searchProductNo":
			// ProductNoのクリア動作
			if (G_StockList.getProductNo().isEmpty()) {
				session.setAttribute("G_StockList", null);
				session.setAttribute("ProductMaster", null);
				session.setAttribute("PuroductStock", null);
				session.setAttribute("G_StockListAllByProductNo", null);
				break;
			} else if (!G_StockList.getProductNo().isEmpty()) {
				// テーブル検索
				ProductMaster = pmDAO.searchByProNo(G_StockList);
				if (ProductMaster == null) {
					session.setAttribute("message", "入力値に該当する品番は存在しません。\\n入力内容を確認ください。");
					G_StockList = new G_StockList();
					G_StockList.setProductNo(request.getParameter("productNo"));
				} else if (ProductMaster != null) {
					PuroductStock = psDAO.searchByDate(G_StockList);
					G_StockList.setProductName(ProductMaster.getProductName());
					G_StockList.setStockQty(PuroductStock.getStockQty());
					G_StockListAllByProductNo = slDAO.searchAllByProductNo(G_StockList);
				}
				session.setAttribute("G_StockList", G_StockList);
				session.setAttribute("ProductMaster", ProductMaster);
				session.setAttribute("PuroductStock", PuroductStock);
				session.setAttribute("G_StockListAllByProductNo", G_StockListAllByProductNo);
			}
			break;
		case "dummy":
			session.setAttribute("message", null);
			break;
		case "cancel":
		default:
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			break;
		}
		// プルダウン用リスト取得
		ProductMasterList = pmDAO.searchAll();
		session.setAttribute("ProductMasterList", ProductMasterList);
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/stockList.jsp");
		return "/WEB-INF/main/stockList.jsp";
	}

}
