package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// 各メッセージリセット
		session.setAttribute("alert", null);
		session.setAttribute("message", null);
		session.setAttribute("state", null);
		// ログイン情報削除
		session.setAttribute("user", null);
		// ログイン状態表示クリア
		session.setAttribute("loginState", null);
		// 画面遷移時の接触属性クリア処理
		new MainAction().crearAttributeForScreenChange(session);
		return "/WEB-INF/main/login.jsp";
	}
}
