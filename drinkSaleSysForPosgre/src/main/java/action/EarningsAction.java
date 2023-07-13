package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Earnings;
import bean.EarningsEtc;
import dao.GoodsIssueDAO;
import tool.Action;

public class EarningsAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// チェック結果アウトプット準備
		HttpSession session = request.getSession();

		// ログイン状態確認(ページ更新の際に有効なチェック)
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		//セッション切れ処理
		if (loginStatusCheck == 0) {
			session.setAttribute("loginState", "セッション切れの為､購入画面に移動しました｡");
			return "/WEB-INF/main/index.jsp";
		}

		//リクエストパラメータをEarningsEtcクラスのbeanに格納し、
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
		session.setAttribute("toAction", toAction);

		//処理準備
		GoodsIssueDAO giDAO = null;
		List<Earnings> earningsList = null;
		int[] alartNum = new int[10];
		String comment = "";

		// 処理種により､処理を分岐
		switch (toAction) {
		case "earningsCheckYear":
		case "earningsCheckMonth":
			//入力状態確認
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
				session.setAttribute("state", comment + "を選択してください。");
				new MainAction().setAlart(request, response, alartNum);
				session.setAttribute("earningsList",null);
				return "/WEB-INF/main/earnings.jsp";
			} else {
				//売上画面へのテーブル表示
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
			new MainAction().clearAttribute(request, response);
		}
		return "/WEB-INF/main/earnings.jsp";
	}
}
