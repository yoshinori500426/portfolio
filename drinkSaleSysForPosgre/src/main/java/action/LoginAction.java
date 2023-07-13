package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Users;
import dao.UsersDAO;
import tool.Action;

public class LoginAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// インスタンス｢us｣にリクエストパラメータuserID/passwordを格納し､セッション属性｢us｣に格納
		Users us = new Users();
		us.setUserID(request.getParameter("userID"));
		us.setPassword(request.getParameter("password"));
		session.setAttribute("us", us);

		// userID/password入力確認
		// →未入力有れば､JSPファイルに処理を返す
		if (us.getUserID().equals("") || us.getPassword().equals("")) {
			session.setAttribute("loginState", "ログイン名/パスワードが入力されていません。");
			// アラートを出す項目番号を指定
			// →格納する値は1-10(使用時は､1差引いて使用)
			int[] alartNum = new int[10];
			if (us.getUserID().equals("") && us.getPassword().equals("")) {
				alartNum[0] = 1;
				alartNum[1] = 2;
			} else if (us.getUserID().equals("")) {
				alartNum[0] = 1;
			} else if (us.getPassword().equals("")) {
				alartNum[0] = 2;
			}
			// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
			new MainAction().setAlart(request, response, alartNum);
			return "/WEB-INF/main/login.jsp";
		}

		// userID/password照合
		// →照合結果を､JSPファイルに返す
		UsersDAO dao = new UsersDAO();
		Users users = dao.searchUsersByUserIDAndPassword(us);
		if (users == null) {
			session.setAttribute("loginState", "ログイン名またはパスワードが違います。");
			// アラートを出す項目番号を指定
			// →格納する値は1-10(使用時は､1差引いて使用)
			int[] alartNum = new int[10];
			alartNum[0] = 1;
			alartNum[1] = 2;
			// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
			new MainAction().setAlart(request, response, alartNum);
			return "/WEB-INF/main/login.jsp";
		} else {
			session.setAttribute("loginState", users.getUserName() + "さんがログイン中です。");
			session.setAttribute("user", users);
		}
		return "/WEB-INF/main/menu.jsp";
	}

}
