package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_UserMaster;
import bean.UserMaster;
import dao.UserMasterDAO;
import tool.Action;

public class LoginAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// 使用インスタンス作成
		UserMaster user = null;
		UserMasterDAO umDAO = new UserMasterDAO();
		// 画面情報取得
		G_UserMaster G_UserMaster = new G_UserMaster();
		G_UserMaster.setUserId(request.getParameter("userId"));
		G_UserMaster.setPassword(request.getParameter("password"));
		session.setAttribute("G_UserMaster", G_UserMaster);
		// リクエストパラメータ取得
		String toAction = request.getParameter("toAction");
		session.setAttribute("toAction", toAction);
		// 処理種により､処理を分岐
		switch (toAction) {
		case "logIn":
			user = umDAO.searchByUM(G_UserMaster);
			if (user == null) {
				session.setAttribute("message", "ログインIDまたはパスワードが違います。");
			} else {
				session.setAttribute("message", null);
				session.setAttribute("user", user);
				session.setAttribute("loginState", "ようこそ、" + user.getName() + "さん");
				return "/WEB-INF/main/menu.jsp";
			}
			break;
		case "dummy":
			break;
		case "cancel":
		default:
			// 不要なセッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			break;
		}
		return "/WEB-INF/main/login.jsp";
	}
}
