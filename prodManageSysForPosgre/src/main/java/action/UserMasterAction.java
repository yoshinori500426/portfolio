package action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_UserMaster;
import bean.UserMaster;
import dao.UserMasterDAO;
import tool.Action;

public class UserMasterAction extends Action {
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

		// 使用インスタンスの格納変数を参照先「null」で宣言
		UserMasterDAO umDAO = null;
		UserMaster userForChange = null;

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);

		// 画面情報取得
		G_UserMaster G_UserMaster = new G_UserMaster();
		G_UserMaster.setUserId(request.getParameter("userId"));
		G_UserMaster.setName(request.getParameter("userName"));
		G_UserMaster.setPassword(request.getParameter("password"));
		G_UserMaster.setPasswordForCheck(request.getParameter("passwordForCheck"));
		G_UserMaster.setDept(request.getParameter("dept"));
		G_UserMaster.setEtc(request.getParameter("etc"));
		G_UserMaster.setHireDate(request.getParameter("hireDate"));
		session.setAttribute("G_UserMaster", G_UserMaster);

		// 処理種により､処理を分岐
		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			session.setAttribute("btnSelect", btnSelect);
			break;
		case "searchUserID":
			// 不要セッション属性のnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("message", null);
			// テーブル検索
			umDAO = new UserMasterDAO();
			userForChange = umDAO.searchByID(G_UserMaster);
			if (userForChange == null) {
				session.setAttribute("message", "入力値に該当するユーザIDは存在しません。\\n入力内容を確認ください。");
				break;
			} else if (userForChange != null) {
				G_UserMaster.setName(userForChange.getName());
				G_UserMaster.setDept(userForChange.getDept());
				G_UserMaster.setEtc(userForChange.getEtc());
				G_UserMaster.setHireDate(userForChange.getHireDate());
				session.setAttribute("G_UserMaster", G_UserMaster);
			}
			break;
		case "doBTNExecute":
			int line = 0;
			// トランザクション処理準備
			umDAO = new UserMasterDAO();
			Connection con = umDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				if (btnSelect.equals("insert")) {
					line = umDAO.insertByUM(G_UserMaster, request);
				} else if (btnSelect.equals("update")) {
					line = umDAO.updateByUM(G_UserMaster, request);
				}
				// 成功/失敗判定
				if (line == 1) {
					con.commit();
					// 各種セッション属性のnullクリア
					new MainAction().crearAttributeForScreenChange(session);
					session.setAttribute("message", "処理が正常に終了しました｡");
				} else {
					con.rollback();
					session.setAttribute("message", "処理中に異常が発生しました｡\\n処理は行われていません｡");
				}
				// トランザクション処理終了
				con.setAutoCommit(true);
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
		session.setAttribute("nextJsp", "/WEB-INF/main/userMaster.jsp");
		return "/WEB-INF/main/userMaster.jsp";
	}
}
