package action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserMaster;
import dao.AmountCalcDAO;
import tool.Action;

public class MainAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// 画面「amountCalc.jsp」「amountCalcOrder.jsp」「user_y.jsp」に遷移時の処理
		crearAttributeForAmountCalcAndUser(session);

		String toAction = request.getParameter("toAction");
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);

		if (loginStatusCheck == 0 && toAction != null && !toAction.equals("main/login.jsp")) {
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
		int judge = 0;

		HttpSession session = request.getSession();
		UserMaster um = (UserMaster) session.getAttribute("user");
		if (um == null) {
			session.setAttribute("loginState", null);
		} else if (um != null) {
			session.setAttribute("loginState", um.getName());
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
	 * 画面「amountCalc.jsp」「amountCalcOrder.jsp」「user_y.jsp」に遷移時にセッション属性をクリアするメソッド
	 *
	 * @param HttpSession session(セッション属性操作用インスタンス)
	 * @return 戻り値無し
	 */
	private void crearAttributeForAmountCalcAndUser(HttpSession session) {
		String nextJsp = (String) session.getAttribute("nextJsp");
		// 画面「amountCalc.jsp」「amountCalcOrder.jsp」「user_y.jsp」に遷移時の処理
		if (nextJsp != null && (nextJsp.equals("/WEB-INF/main/amountCalc.jsp")
				|| nextJsp.equals("/WEB-INF/main/amountCalcOrder.jsp") || nextJsp.equals("/WEB-INF/main/user_y.jsp"))) {
			//画面「amountCalc.jsp」「amountCalcOrder.jsp」で使用したセッション属性のnullクリア
			session.setAttribute("therad", null);
			session.setAttribute("G_AmountCalcOrder", null);
			session.setAttribute("amountCalcOrderMSG1", null);
			session.setAttribute("state", null);
			new AmountCalcDAO().outPutMSG(session, null, null, null, null);
			new AmountCalcDAO().changeAttribute(session, null, null);
			//画面「user_y.jsp」で使用したセッション属性のnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("message", null);
			session.setAttribute("btnSelect", null);
			session.setAttribute("userForChange", null);
			session.setAttribute("G_UserMaster", null);
		}
	}
}
