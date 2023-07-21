package field;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Earnings;
import bean.EarningsEtc;
import dao.GoodsIssueDAO;
import tool.CommonMethod;

/**
 * Servlet implementation class Earnings
 */
@WebServlet("/Earnings")
public class EarningsAct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// ログイン後の処理前用メソッド(①不要セッション属性nullクリア②ログイン状態確認)
		new CommonMethod().actionBefProc(request, response);

		// リクエストパラメータをEarningsEtcクラスのbeanに格納し、
		// セッション属性「earningsEtc」に格納する
		EarningsEtc earningsEtc = (EarningsEtc) session.getAttribute("earningsEtc");
		if (earningsEtc == null) {
			earningsEtc = new EarningsEtc();
		}
		earningsEtc.setYear(request.getParameter("year"));
		earningsEtc.setMonth(request.getParameter("month"));
		session.setAttribute("earningsEtc", earningsEtc);

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		// セッション属性｢process｣に｢getParameter("toAction")｣の値を格納する
		// →セッション属性｢toAction｣は､メソッド｢new
		// CommonMethod().dispatchToActionByInputOutputScreenClass()｣で､
		// ｢main/〇〇.jsp｣の値が格納される
		session.setAttribute("process", toAction);

		// 処理準備
		GoodsIssueDAO giDAO = null;
		List<Earnings> earningsList = null;
		int[] alartNum = new int[10];
		String comment = "";

		// 処理種により､処理を分岐
		switch (toAction) {
		case "earningsCheckYear":
		case "earningsCheckMonth":
			// 入力状態確認
			if (earningsEtc.getYear().equals("1999") || earningsEtc.getMonth().equals("0")) {
				if (earningsEtc.getYear().equals("1999") && earningsEtc.getMonth().equals("0")) {
					comment = "年/月";
					alartNum[0] = 1;
					alartNum[1] = 2;
				} else if (earningsEtc.getYear().equals("1999")) {
					comment = "年";
					alartNum[0] = 1;
				} else if (earningsEtc.getMonth().equals("0")) {
					comment = "月";
					alartNum[1] = 2;
				}
				//session.setAttribute("state", comment + "を選択してください。");
				new CommonMethod().setAlart(request, response, alartNum);
				session.setAttribute("earningsList", null);
				// クラス｢InputOutputScreen｣を使用した画面遷移
				new CommonMethod().dispatchToActionByInputOutputScreenClass("main/earnings.jsp", request, response);
			} else {
				// 売上画面へのテーブル表示
				// →入力状態に問題無い場合の処理
				// テーブル｢StoreProduct｣のリスト取得
				giDAO = new GoodsIssueDAO();
				earningsList = giDAO.searchGoodsIssueBySyukoYM(request, response, earningsEtc);
				// セッション属性｢earningsList｣にリスト｢earningsList｣を格納
				session.setAttribute("earningsList", earningsList);
			}
			break;
		default:
			// セッション属性｢session｣以外を全てリセット
			new CommonMethod().clearAttribute(request, response);
		}
		// クラス｢InputOutputScreen｣を使用した画面遷移
		new CommonMethod().dispatchToActionByInputOutputScreenClass("main/earnings.jsp", request, response);

	}

	public EarningsAct() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
