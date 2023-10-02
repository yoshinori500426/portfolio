package action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CustomerMaster;
import bean.EntryExitInfo;
import bean.OrderTable;
import bean.ProductMaster;
import bean.PurchaseOrder;
import bean.SupplierMaster;
import bean.UserMaster;
import dao.AmountCalcDAO;
import dao.CreateTableDAO;
import dao.CustomerMasterDAO;
import dao.EntryExitInfoDAO;
import dao.OrderTableDAO;
import dao.ProductMasterDAO;
import dao.PurchaseOrderDAO;
import dao.SupplierMasterDAO;
import dao.UserMasterDAO;
import tool.Action;

public class MainAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// 画面遷移時の接触属性クリア処理
		crearAttributeForScreenChange(session);

		String toAction = request.getParameter("toAction");
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);

		if (toAction.equals("fromIndex")) {
			toAction = "/WEB-INF/main/login.jsp";
			// ログイン情報削除
			session.setAttribute("user", null);
			session.setAttribute("loginState", null);
			// 動作確認用テーブル作成
			CreateTableDAO ctDAO = new CreateTableDAO();
			int line = 0;
			line += ctDAO.createTable();
			line += ctDAO.createSequence();
			line += ctDAO.inserUserMasterTable();
			line += ctDAO.insertProductMasterTable();
			line += ctDAO.insertCustomerMasterTable();
			line += ctDAO.insertSupplierMasterTable();
			line += ctDAO.insertPurchaseOrderTable();
			line += ctDAO.insertPuroductStockTable();
			line += ctDAO.insertEntryExitInfoTable();
			line += ctDAO.insertOrderTable();
			if (line != (10 + 7 + 10 + 8 + 10 + 8 + 32 + 16 + 15 + 32)) {
				session.setAttribute("state", "動作確認用テーブルが正常に作成されていません｡\\n再度､生産管理システムに入り直して下さい｡");
			}
		} else if (loginStatusCheck == 0 && toAction != null && !toAction.equals("main/login.jsp")) {
			session.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
			toAction = "/WEB-INF/main/login.jsp";
		} else if (loginStatusCheck == 0 && (toAction == null || toAction.equals("main/login.jsp"))) {
			toAction = "/WEB-INF/main/login.jsp";
		} else {
			toAction = "/WEB-INF/" + toAction;
		}
		return toAction;
	}

	// 以下､メソッド=================================================================
	/**
	 * ログイン状態確認/セッション属性｢loginState｣変更用メソッド
	 *
	 * @param request,response
	 * @return 戻り値｢0:ログアウト状態｣｢1:ログイン状態｣
	 */
	public int loginStatusCheck(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserMaster user = (UserMaster) session.getAttribute("user");
		int judge = 0;
		if (user == null) {
			session.setAttribute("loginState", null);
		} else if (user != null) {
			session.setAttribute("loginState", "ようこそ、" + user.getName() + "さん");
			judge++;
		}
		return judge;
	}

	/**
	 * テキストボックスに入力されてる値のパターンマッチングを行うメソッド
	 *
	 * @param 正規表現(regEx),判定文字列(checkParam)
	 * @return 戻り値｢true:マッチする｣｢false:マッチしない｣
	 */
	public boolean inputValueCheck(String checkParam, String regEx) {
		// パターンマッチ準備
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(checkParam);
		// パターンマッチ判定
		boolean judge = matcher.matches();
		return judge;
	}

	/**
	 * セッション属性をクリアするメソッド
	 * 
	 * @param HttpSession session(セッション属性操作用インスタンス)
	 * @return 戻り値無し
	 */
	public void crearAttributeForScreenChange(HttpSession session) {
		// メッセージ･アラート･状態表示関係削除
		session.setAttribute("alert", null);
		session.setAttribute("message", null);
		session.setAttribute("state", null);
		session.setAttribute("toAction", null);
		// 画面入力値削除
		session.setAttribute("G_AmountCalcOrder", null);
		session.setAttribute("G_Arrival", null);
		session.setAttribute("G_CustomerMaster", null);
		session.setAttribute("G_EntryExitInfo", null);
		session.setAttribute("G_EntryExitInfoList", null);
		session.setAttribute("G_EntryExitInfoListSearchByConditions", null);
		session.setAttribute("G_EntryExitInfoListSortByCondition", null);
		session.setAttribute("G_Order", null);
		session.setAttribute("G_OrderList", null);
		session.setAttribute("G_OrderListSearchByConditions", null);
		session.setAttribute("G_OrderListSortByCondition", null);
		session.setAttribute("G_ProductMaster", null);
		session.setAttribute("G_PurchaseOrder", null);
		session.setAttribute("G_PurchaseOrderList", null);
		session.setAttribute("G_PurchaseOrderListSearchByConditions", null);
		session.setAttribute("G_PurchaseOrderListSortByCondition", null);
		session.setAttribute("G_Shipping", null);
		session.setAttribute("G_StockList", null);
		session.setAttribute("G_StockListAllByProductNo", null);
		session.setAttribute("G_SupplierMaster", null);
		session.setAttribute("G_UserMaster", null);
		// 検索値削除
		session.setAttribute("PurchaseOrder", null);
		session.setAttribute("PurchaseOrderList", null);
		session.setAttribute("PurchaseOrderListWithProNameAndCusName", null);
		session.setAttribute("PurchaseOrderListFinFlg0", null);
		session.setAttribute("OrderTable", null);
		session.setAttribute("OrderTableList", null);
		session.setAttribute("OrderTableListWithProNameAndSupName", null);
		session.setAttribute("OrderTableListFinFlg0", null);
		session.setAttribute("EntryExitInfo", null);
		session.setAttribute("EntryExitInfoList", null);
		session.setAttribute("PuroductStock", null);
		// 検索値削除(~Master)
		session.setAttribute("ProductMaster", null);
		session.setAttribute("ProductMasterList", null);
		session.setAttribute("CustomerMaster", null);
		session.setAttribute("CustomerMasterList", null);
		session.setAttribute("SupplierMaster", null);
		session.setAttribute("SupplierMasterList", null);
		session.setAttribute("UserMaster", null);
		session.setAttribute("UserMasterList", null);
		// 画面「amountCalc.jsp」「amountCalcOrder.jsp」で使用したセッション属性のnullクリア
		session.setAttribute("therad", null);
		Connection con = (Connection) session.getAttribute("con");
		PreparedStatement st = (PreparedStatement) session.getAttribute("st");
		try {
			if(con!=null) {
				con.close();
				session.setAttribute("con", null);
			}
			if(st!=null) {
				st.close();
				session.setAttribute("st", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		new AmountCalcDAO().outPutMSG(session, null, null, null, null);
		new AmountCalcDAO().changeAttribute(session, null, null);
		// ボタン選択状態保持で使用したセッション属性のnullクリア
		session.setAttribute("btnSelect", null);
	}

	/**
	 * 日付記述方式変更用メソッド
	 *
	 * @param HTMLから渡される｢YYYY-MM-DDTHH:mm:ss.sssZ｣形式の日時
	 * @return DBに入力する｢YYYY/MM/DD｣形式の日付
	 */
	public String dateChangeForDB(String inputDate) {
		return (inputDate != null && !inputDate.isEmpty()) ? inputDate.substring(0, 10).replace("-", "/") : "";
	}

	/**
	 * 日付記述方式変更用メソッド
	 *
	 * @param DBから渡される｢YYYY/MM/DD｣形式の日時
	 * @return HTMLに入力する｢YYYY-MM-DD｣形式の日付
	 */
	public String dateChangeForHTML(String inputDate) {
		return (inputDate != null && !inputDate.isEmpty()) ? inputDate.replace("/", "-") : "";
	}

	/**
	 * 動作確認用メソッド(portfolioとして成立させる為､テーブル情報を公開する事が目的のメソッド)
	 *
	 * @param DBから渡される｢YYYY/MM/DD｣形式の日時
	 * @return HTMLに入力する｢YYYY-MM-DD｣形式の日付
	 */
	public void getListsForPortfolio(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 動作確認用の処理(portfolioとして成立させる為､テーブル情報を公開する事が目的)
		// 以下条件とする
		// ･画面毎に必要な情報は変化するが､今回は､必要な全ての情報をセッション属性に挙げ､JSPファイルのEL式で表示内容を切り替える仕様とする
		// ➔セッション属性に挙げる段では場合分けしない
		// ･このセッション属性は､nullクリアしない
		// ➔常に指定テーブルのインスタンスがUPされている状態とする
		// ･在庫テーブル(PURODUCT_STOCK)は､直接編集する画面が存在しない為､セッション属性としてUPしない
		// ･在庫確認画面用ビュー(STOCK_LIST_ALL)は参照のみの為､セッション属性としてUPしない
		// ･所要量計算用ビュー(AMOUNT_CALC_ALL)は参照のみの為､セッション属性としてUPしない
		// 以下項目をセッション属性として挙げる
		// ①ユーザーマスタ（USER_MASTER)テーブル
		List<UserMaster> UmListPF = new UserMasterDAO().searchAllForPortfolio();
		session.setAttribute("UmListPF", UmListPF);
		// ②品番マスタ（PRODUCT_MASTER)テーブル
		List<ProductMaster> PmListPF = new ProductMasterDAO().searchAll();
		session.setAttribute("PmListPF", PmListPF);
		// ③顧客先マスタ(CUSTOMER_MASTER)テーブル
		List<CustomerMaster> CmListPF = new CustomerMasterDAO().searchAll();
		session.setAttribute("CmListPF", CmListPF);
		// ④仕入先マスタ(SUPPLIER_MASTER)テーブル
		List<SupplierMaster> SmListPF = new SupplierMasterDAO().searchAll();
		session.setAttribute("SmListPF", SmListPF);
		// ⑤受注テーブル(PURCHASE_ORDER)テーブル
		List<PurchaseOrder> PoListPF = new PurchaseOrderDAO().searchAllForPortfolio();
		session.setAttribute("PoListPF", PoListPF);
		// ⑥入出庫テーブル(ENTRY_EXIT_INFO)テーブル
		List<EntryExitInfo> EeiListPF = new EntryExitInfoDAO().searchAllForPortfolio();
		session.setAttribute("EeiListPF", EeiListPF);
		// ⑦発注テーブル(ORDER_TABLE)テーブル
		List<OrderTable> OtListPF = new OrderTableDAO().searchAllForPortfolio();
		session.setAttribute("OtListPF", OtListPF);
	}
}
