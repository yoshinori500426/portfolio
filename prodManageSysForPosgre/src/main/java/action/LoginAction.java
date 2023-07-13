package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserMaster;
import dao.UserMasterDAO;
import tool.Action;

public class LoginAction extends Action {

	@SuppressWarnings("unused")
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		//	baenをインスタンス化
		UserMaster us = new UserMaster();
		//DAOをインスタンス化
		UserMasterDAO dao = new UserMasterDAO();
		//JSPのhiddenを使えるようにする。
		String toAction = request.getParameter("toAction");
		request.getParameter("logout");
		//
		us.setUserId(request.getParameter("userID"));
		us.setPassword(null);

		UserMaster usDao = dao.searchByUM(us.getUserId(), request.getParameter("password"));
		if (usDao == null) {
			request.setAttribute("message", "ログインIDまたはパスワードが違います。");
			session.setAttribute("us", us);
			return "/WEB-INF/main/login.jsp";
		} else {
			request.setAttribute("message", null);
			session.setAttribute("message", null);
			session.setAttribute("loginState", usDao.getName());
			session.setAttribute("user", usDao);
			session.setAttribute("loginIdA", usDao.getUserId());
		}

		return "/WEB-INF/main/menu.jsp";
	}

}
