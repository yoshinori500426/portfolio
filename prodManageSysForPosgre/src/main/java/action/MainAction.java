package action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserMaster;
import dao.AmountCalcDAO;
import dao.CreateTableDAO;
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
			if (line != (9 + 7 + 10 + 8 + 10 + 8 + 32 + 16 + 15 + 32)) {
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
		session.setAttribute("nextJsp", toAction);
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
		session.setAttribute("G_CustomerMaster", null);
		session.setAttribute("G_ProductMaster", null);
		session.setAttribute("G_PurchaseOrder", null);
		session.setAttribute("G_Shipping", null);
		session.setAttribute("G_SupplierMaster", null);
		session.setAttribute("G_UserMaster", null);
		// 検索値削除
		session.setAttribute("PurchaseOrder", null);
		session.setAttribute("OrderTable", null);
		session.setAttribute("ProductMaster", null);
		session.setAttribute("CustomerMaster", null);
		session.setAttribute("SupplierMaster", null);
		session.setAttribute("UserMaster", null);
		// 画面「amountCalc.jsp」「amountCalcOrder.jsp」で使用したセッション属性のnullクリア
		session.setAttribute("therad", null);
		new AmountCalcDAO().outPutMSG(session, null, null, null, null);
		new AmountCalcDAO().changeAttribute(session, null, null);
		// 画面「*Master.jsp」で使用したセッション属性のnullクリア
		session.setAttribute("btnSelect", null);
	}

	/**
	 * 日付記述方式変更用メソッド
	 *
	 * @param HTMLから渡される｢YYYY-MM-DDTHH:mm:ss.sssZ｣形式の日時
	 * @return DBに入力する｢YYYY/MM/DD｣形式の日付
	 */
	public String dateChangeForDB(String inputDate) {
		return (inputDate!=null && !inputDate.isEmpty())?inputDate.substring(0, 10).replace("-", "/"):"";
	}

	/**
	 * 日付記述方式変更用メソッド
	 *
	 * @param HTMLから渡される｢YYYY/MM/DD｣形式の日時
	 * @return HTMLに入力する｢YYYY-MM-DD｣形式の日付
	 */
	public String dateChangeForHTML(String inputDate) {
		return (inputDate!=null && !inputDate.isEmpty())?inputDate.replace("/", "-"):"";
	}
}
