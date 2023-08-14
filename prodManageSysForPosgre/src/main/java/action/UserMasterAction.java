package action;

import java.sql.Connection;
import java.text.DateFormat;

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
		// 処理準備
		HttpSession session = request.getSession();

		// ログイン状態確認
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		// セッション切れ処理
		if (loginStatusCheck == 0) {
			request.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
			// 画面「userMaster.jsp」で使用したセッション属性のnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("btnSelect", null);
			session.setAttribute("G_UserMaster", null);

			session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
			return "/WEB-INF/main/login.jsp";
		}

		// 使用インスタンスを参照先「null」で作成
		UserMasterDAO umDAO = null;
		UserMaster userForChange = null;
		int line = 0;
		// 正規表現判定用変数を宣言
		String regEx = "";
		String checkParam = "";
		String message = "";
		boolean judge = false;
		boolean judgeRegEx = true;

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);

		// 画面情報取得
		G_UserMaster G_UserMaster = new G_UserMaster();
		if (request.getParameter("userId") != null && request.getParameter("userId").length() <= 6) {
			if (request.getParameter("userId").equals("")) {
				G_UserMaster.setUserId(request.getParameter("userId"));
			} else {
				G_UserMaster.setUserId(String.format("%6s", request.getParameter("userId")).replace(" ", "0"));
			}
		} else {
			G_UserMaster.setUserId(request.getParameter("userId"));
		}
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
			// 不要セッション属性をnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("message", null);
			session.setAttribute("G_UserMaster", null);
			break;
		case "searchUserID":
			// 画面「userMaster.jsp」で使用したセッション属性のnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("message", null);
			// 正規表現判定
			// →空文字を判定対象外とする
			// String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
			regEx = "^\\d{6}$";
			judge = false;
			checkParam = G_UserMaster.getUserId();
			if (checkParam == null || checkParam.equals("")) {
				userIdErrorHandling(request, response, judge);
				break;
			} else if (checkParam != null && !checkParam.equals("")) {
				judge = new MainAction().inputValueCheck(checkParam, regEx);
				if (judge == false) {
					session.setAttribute("message", "入力値がユーザIDの基準(6桁以下の数字)を満たしていません。\\n入力内容を確認ください。");
					userIdErrorHandling(request, response, judge);
					break;
				}
			}
			umDAO = new UserMasterDAO();
			userForChange = umDAO.searchByID(G_UserMaster);
			if (userForChange == null) {
				session.setAttribute("message", "入力値に該当するユーザIDは存在しません。\\n入力内容を確認ください。");
				userIdErrorHandling(request, response, judge);
				break;
			}
			G_UserMaster.setName(userForChange.getName());
			G_UserMaster.setDept(userForChange.getDept());
			G_UserMaster.setEtc(userForChange.getEtc());
			G_UserMaster.setHireDate(userForChange.getHireDate());
			session.setAttribute("G_UserMaster", G_UserMaster);
			break;
		case "doBTNExecute":
			if (btnSelect == null || btnSelect.equals("")) {
				break;
			}
			message = (String) session.getAttribute("message");
			if (message != null) {
				session.setAttribute("nextJsp", "/WEB-INF/main/userMaster.jsp");
				return "/WEB-INF/main/userMaster.jsp";
			}
			// 入力値チェック
			// 対象は以下
			// ➀ユーザ名：空文字NG
			// ➁パスワード：8-20文字(英数構成、大文字1以上)
			// ➂パスワード(確認用)：パスワードとパスワード(確認用)の同一を確認
			// ➃分類：空文字NG
			// ➄入社日：YYYY/MM/DD
			// 個別にチェックを行い、エラーの場合は一括で出力する
			// エラーの際には、パスワード/パスワード(確認用)を空文字にする
			// 入力値判定用変数「judge」に「true」を格納
			// →1件でもチェックでNGとなると、「false」が格納される
			judgeRegEx = true;
			message = "";
			// 明示「確認」を表示させる為、配列alart作成
			// →添え字「1:ユーザ名」「2:パスワード」「3:パスワード(確認用)」「4:入社日」
			String[] alert = new String[5];
			// ➀ユーザ名：空文字NG
			checkParam = G_UserMaster.getName();
			if (checkParam == null || checkParam.equals("")) {
				message = massageCreate(message, "ユーザ名が入力されていません。");
				judgeRegEx = false;
				alert[0] = "<span class=\"label label-danger\">確認</span>";
			}
			// ➁パスワード：8文字以上、英数構成、英字大文字小文字１以上
			regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
			checkParam = G_UserMaster.getPassword();
			if (checkParam == null || checkParam.equals("")) {
				message = massageCreate(message, "パスワードが入力されていません。");
				judgeRegEx = false;
				alert[1] = "<span class=\"label label-danger\">確認</span>";
			} else if (checkParam != null && !checkParam.equals("")) {
				judge = new MainAction().inputValueCheck(checkParam, regEx);
				if (judge == false) {
					message = massageCreate(message, "パスワードが入力基準､｢8-20文字(英数構成、大文字1以上)｣を満たしていません。");
					judgeRegEx = false;
					alert[1] = "<span class=\"label label-danger\">確認</span>";
				}
			}
			// ➂パスワード(確認用)：パスワードとパスワード(確認用)の同一を確認
			if (!G_UserMaster.getPasswordForCheck().equals(checkParam)) {
				message = massageCreate(message, "パスワード(確認用)に、パスワードと違う文字が入力されています。");
				judgeRegEx = false;
				alert[2] = "<span class=\"label label-danger\">確認</span>";
			}
			// ➃分類：空文字NG
			checkParam = G_UserMaster.getDept();
			if (checkParam == null || checkParam.equals("")) {
				message = massageCreate(message, "分類が入力されていません。");
				judgeRegEx = false;
				alert[3] = "<span class=\"label label-danger\">確認</span>";
			}
			// ➄入社日：YYYY/MM/DD
			regEx = "^[0-9]{4}/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])$";
			checkParam = G_UserMaster.getHireDate();
			if (checkParam == null || checkParam.equals("")) {
				message = massageCreate(message, "入社日が入力されていません。");
				judgeRegEx = false;
				alert[4] = "<span class=\"label label-danger\">確認</span>";
			} else if (checkParam != null && !checkParam.equals("")) {
				// 正規表現確認
				judge = new MainAction().inputValueCheck(checkParam, regEx);
				if (judge == false) {
					message = massageCreate(message, "入社日が入力基準､｢YYYY/MM/DD｣を満たしていません。");
					judgeRegEx = false;
					alert[4] = "<span class=\"label label-danger\">確認</span>";
				} else if (judge == true) {
					// カレンダーベースの入力値妥当性確認(2023/02/29(存在しない日付)などをNG検知)
					DateFormat format = DateFormat.getDateInstance();
					format.setLenient(false);
					try {
						format.parse(checkParam);
					} catch (Exception e) {
						message = massageCreate(message, "入社日がカレンダーに存在しない年月日です。");
						judgeRegEx = false;
						alert[4] = "<span class=\"label label-danger\">確認</span>";
					}
				}
			}
			// 入力値チェックNGの場合の処理
			if (judgeRegEx == false) {
				session.setAttribute("alert", alert);
				session.setAttribute("message", message);
				doBTNExecuteErrorHandling(request, response);
				break;
			}
			// 入力値チェックOKの場合の処理
			session.setAttribute("alert", null);

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
					session.setAttribute("message", "処理が正常に終了しました｡");
					session.setAttribute("alert", null);
					session.setAttribute("btnSelect", null);
					session.setAttribute("G_UserMaster", null);
				} else {
					con.rollback();
					session.setAttribute("message", "処理中に異常が発生しました｡\\n処理は行われていません｡");
				}
				// トランザクション処理終了
				con.setAutoCommit(true);
			}
			break;
		case "dummy":
			// 要削除
			// umDAO = new UserMasterDAO();
			// userForChange = umDAO.searchByID(G_UserMaster);
			// session.setAttribute("G_UserMaster", userForChange);
			session.setAttribute("message", null);
			break;
		case "cancel":
		default:
			// 参照用セッション属性をnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("message", null);
			session.setAttribute("btnSelect", null);
			session.setAttribute("G_UserMaster", null);
			break;
		}
		session.setAttribute("nextJsp", "/WEB-INF/main/userMaster.jsp");
		return "/WEB-INF/main/userMaster.jsp";
	}

	// 以下､メソッド=================================================================
	/**
	 * セッション属性nullクリア、bean「G_UserMaster」処理のみのメソッド
	 * 
	 * @param judge
	 *
	 * @param HttpServletRequest request, HttpServletResponse response
	 * @return 無し(取得リストは､セッション属性で受け渡し)
	 */
	private void userIdErrorHandling(HttpServletRequest request, HttpServletResponse response, boolean judge) {
		// 処理準備
		HttpSession session = request.getSession();
		session.setAttribute("G_UserMaster", null);
		G_UserMaster G_UserMaster = new G_UserMaster();
		if (judge == false) {
			G_UserMaster.setUserId("");
		} else if (judge == true) {
			G_UserMaster.setUserId(String.format("%6s", request.getParameter("userId")).replace(" ", "0"));
		}
		session.setAttribute("G_UserMaster", G_UserMaster);
	}

	/**
	 * 入力値チェックの際のメッセージ作成用メソッド
	 *
	 * @param String message(加工前), String string
	 * @return String message(加工後)
	 */
	private String massageCreate(String message, String string) {
		if (message != "") {
			message += "\\n" + string;
		} else {
			message += string;
		}
		return message;
	}

	/**
	 * bean「G_UserMaster」のパスワード/パスワード(確認用)を空文字に変更するメソッド
	 *
	 * @param HttpServletRequest request, HttpServletResponse response
	 * @return 無し(取得リストは､セッション属性で受け渡し)
	 */
	private void doBTNExecuteErrorHandling(HttpServletRequest request, HttpServletResponse response) {
		// 処理準備
		HttpSession session = request.getSession();
		G_UserMaster G_UserMaster = (G_UserMaster) session.getAttribute("G_UserMaster");
		G_UserMaster.setPassword("");
		G_UserMaster.setPasswordForCheck("");
		session.setAttribute("G_UserMaster", G_UserMaster);
	}
}
