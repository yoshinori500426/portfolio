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
		// 画面遷移時の接触属性クリア処理
		new MainAction().crearAttributeForScreenChange(session);
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
		return "/WEB-INF/main/login.jsp";
	}
}
