package field;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_userRegister;
import bean.G_userRegisterLogin;
import bean.Users;
import dao.DAO;
import dao.UsersDAO;
import tool.CommonMethod;

/**
 * Servlet implementation class UserRegister
 */
@WebServlet("/UserRegister")
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// ログイン後の処理前用メソッド(①不要セッション属性nullクリア②ログイン状態確認)
		new CommonMethod().actionBefProc(request, response);

		// 画面入力値をString形式でbeanに格納し､セッション属性｢G_userRegister｣へ格納
		// G_userRegisterのフィールド「inputState」は、
		// →1は新規作成(ID登録なし) ：userRegister.jspのボタン「登録/更新」がアクティブ
		// →2は更新(ID既に登録済み) ：userRegister.jspのボタン「登録/更新」がアクティブ
		// →3は更新もしくは削除 ：userRegister.jspのボタン「登録/更新」「削除」がアクティブ
		G_userRegister G_userRegister = new G_userRegister();
		G_userRegister.setUserID(request.getParameter("userID"));
		G_userRegister.setUserName(request.getParameter("userName"));
		G_userRegister.setPassword(request.getParameter("password"));
		G_userRegister.setPasswordForCheck(request.getParameter("passwordForCheck"));
		G_userRegister.setEtc(request.getParameter("etc"));
		G_userRegister.setInputState(request.getParameter("inputState"));
		G_userRegister.setToAction(request.getParameter("toAction"));
		session.setAttribute("G_userRegister", G_userRegister);
		// セッション属性｢process｣に｢getParameter("toAction")｣の値を格納する
		// →セッション属性｢toAction｣は､メソッド｢new
		// CommonMethod().dispatchToActionByInputOutputScreenClass()｣で､
		// ｢main/〇〇.jsp｣の値が格納される
		session.setAttribute("process", G_userRegister.getToAction());

		// UsersDAOクラス使用用bean準備
		Users us = new Users();
		us.setUserID(G_userRegister.getUserID());
		us.setUserName(G_userRegister.getUserName());
		us.setPassword(G_userRegister.getPassword());
		us.setEtc(G_userRegister.getEtc());
		session.setAttribute("us", us);
		UsersDAO usDAO = new UsersDAO();

		// 現在ログイン中のユーザ情報が格納されているbean「user」の取得
		Users user = (Users) session.getAttribute("user");

		// 入力値チェック用変数定義
		boolean judgeUserID = false;
		boolean judgeUserName = false;
		boolean judgePassword = false;

		// 処理種により､処理を分岐
		switch (G_userRegister.getToAction()) {
		case "inputCheck":
			G_userRegister.setInputState("");
			// passwordチェック
			judgePassword = new CommonMethod().passwordCheck(request, response, "password",
					"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$", 3);
			// userNameチェック
			judgeUserName = new CommonMethod().blankCheck(request, response, "userName", ".{1,30}", 2);
			// userIDチェック
			judgeUserID = new CommonMethod().blankCheck(request, response, "userID", "[A-Za-z0-9]{1,20}", 1);
			if (judgeUserID && judgeUserName && judgePassword == true) {
				// passwordForCheckチェック
				if (G_userRegister.getPassword().equals(G_userRegister.getPasswordForCheck())) {
					// テーブル「USERS」チェック
					// →すでに登録済みのuserID/passwordの場合、「削除」ボタンを有効にする
					// テーブル「USERS」チェック
					Users IdAndPassword = usDAO.searchUsersByUserIDAndPassword(us);
					Users ID = usDAO.searchUsersByUserID(us);
					// 判定
					// →1は新規作成(ID登録なし) ：userRegister.jspのボタン「登録/更新」がアクティブ
					// →2は更新(ID既に登録済み) ：userRegister.jspのボタン「登録/更新」がアクティブ
					// →3は更新もしくは削除 ：userRegister.jspのボタン「登録/更新」「削除」がアクティブ
					if (IdAndPassword != null) {
						G_userRegister.setInputState("3");
					} else if (ID != null) {
						G_userRegister.setInputState("2");
					} else {
						G_userRegister.setInputState("1");
					}
					session.setAttribute("G_userRegister", G_userRegister);
				} else if (G_userRegister.getPassword() != G_userRegister.getPasswordForCheck()) {
					int[] alartNum = new int[10];
					alartNum[0] = 4;
					new CommonMethod().setAlart(request, response, alartNum);
					session.setAttribute("state", "パスワードとパスワード(確認)の入力が違います｡\\n同じ値を入力して下さい｡");
				}
			}
			//アラートが頻繁にで､作業性が悪くなる為､session属性｢state｣をnullとする
			session.setAttribute("state", null);
			break;
		case "updateInsert":
		case "delete":
			if (G_userRegister.getToAction().equals("delete") && !G_userRegister.getInputState().equals("3")) {
				session.setAttribute("state", "入力されているログイン名/パスワードに一致するユーザがありません｡\\n入力内容をお確かめ下さい｡");
				// クラス｢InputOutputScreen｣を使用した画面遷移
				new CommonMethod().dispatchToActionByInputOutputScreenClass("main/userRegister.jsp", request, response);
			}
			// 現在ログイン中のユーザIDを取得し、bean「G_userRegisterLogin」に格納
			G_userRegisterLogin G_userRegisterLogin = new G_userRegisterLogin();
			G_userRegisterLogin.setUserID(user.getUserID());
			// セッション属性「G_userRegisterLogin」にbean「G_userRegisterLogin」を格納
			session.setAttribute("G_userRegisterLogin", G_userRegisterLogin);
			// クラス｢InputOutputScreen｣を使用した画面遷移
			new CommonMethod().dispatchToActionByInputOutputScreenClass("main/userRegisterLogin.jsp", request,
					response);
		case "execute_updateInsert":
		case "execute_delete":
			// ログインチェック
			// 現在ログイン中のユーザ名/パスワードと同一かを確認
			// →入力はパスワードのみ
			// リクエストパラメータ取得
			Users execute_us = new Users();
			execute_us.setPassword(request.getParameter("passwordForRegister"));
			// 比較チェック
			// 入力されたパスワードがログイン中のユーザIDに対応するパスワードと合致する場合
			// →処理実施
			if (usDAO.passwordCheckByUserID(session, execute_us)) {
				int line = -1;

				PrintWriter out = response.getWriter();
				try {
					// レコード更新/新規追加
					// トランザクション処理準備
					DAO dao = new DAO();
					Connection con = dao.getConnection();
					// 排他制御
					synchronized (this) {
						// トランザクション処理開始
						con.setAutoCommit(false);
						// DB処理
						// G_userRegisterのフィールド「inputState」は、
						// →1は新規作成(ID登録なし) ：userRegister.jspのボタン「登録/更新」がアクティブ
						// →2は更新(ID既に登録済み) ：userRegister.jspのボタン「登録/更新」がアクティブ
						// →3は更新もしくは削除 ：userRegister.jspのボタン「登録/更新」「削除」がアクティブ
						if (G_userRegister.getToAction().equals("execute_updateInsert")) {
							if (G_userRegister.getInputState().equals("1")) {
								line = usDAO.insertToUsers(us, user);
							} else if (G_userRegister.getInputState().equals("2")
									|| G_userRegister.getInputState().equals("3")) {
								line = usDAO.updateToUsers(us, user);
							}
						} else if (G_userRegister.getToAction().equals("execute_delete")
								&& G_userRegister.getInputState().equals("3")) {
							line = usDAO.deleteToUsers(us);
						}
						// 成功/失敗判定
						if (line == 1) {
							con.commit();
							new CommonMethod().clearAttribute(request, response);
							session.setAttribute("state", "処理が正常に終了しました｡");
						} else {
							con.rollback();
							session.setAttribute("state", "処理中に異常が発生しました｡\\n処理は行われていません｡");
						}
						// トランザクション処理終了
						con.setAutoCommit(true);
					}
				} catch (Exception e) {
					e.printStackTrace(out);
				}
			} else {
				session.setAttribute("state", "ログイン名またはパスワードが違います。");
				// アラートを出す項目番号を指定
				// →格納する値は1-10(使用時は､1差引いて使用)
				int[] alartNum = new int[10];
				alartNum[0] = 1;
				alartNum[1] = 2;
				// 配列｢int[] alartNum｣の値を基に､配列｢String[] alart｣に値をセット
				new CommonMethod().setAlart(request, response, alartNum);
			}
			break;
		case "executeAft":
			break;
		case "clear":
		default:
			// セッション属性｢session｣以外を全てリセット
			new CommonMethod().clearAttribute(request, response);
			break;
		}
		// クラス｢InputOutputScreen｣を使用した画面遷移
		new CommonMethod().dispatchToActionByInputOutputScreenClass("main/userRegister.jsp", request, response);
	}

	public UserRegister() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
