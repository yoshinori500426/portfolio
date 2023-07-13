package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// ログイン情報削除
		session.setAttribute("user", null);
		// ログイン状態表示クリア
		session.setAttribute("loginState", null);
		// 画面遷移先を販売画面に指定
		return "/WEB-INF/main/index.jsp";
	}
}
