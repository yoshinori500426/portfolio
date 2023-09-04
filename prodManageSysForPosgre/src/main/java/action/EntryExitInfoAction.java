package action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.EntryExitInfo;
import bean.G_EntryExitInfo;
import bean.ProductMaster;
import bean.PuroductStock;
import dao.EntryExitInfoDAO;
import dao.ProductMasterDAO;
import dao.PuroductStockDAO;
import tool.Action;

public class EntryExitInfoAction extends Action {
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
		// 各メッセージリセット
		session.setAttribute("alert", null);
		session.setAttribute("message", null);
		session.setAttribute("state", null);
		// 使用DAOインスタンス取得
		EntryExitInfoDAO eeiDAO = new EntryExitInfoDAO();
		ProductMasterDAO pmDAO = new ProductMasterDAO();
		PuroductStockDAO psDAO = new PuroductStockDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		EntryExitInfo entryExitInfoForChange = null;
		List<EntryExitInfo> EntryExitInfoList = null;
		ProductMaster ProductMaster = null;
		List<ProductMaster> ProductMasterList = null;
		PuroductStock PuroductStock = null;
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面の入力内容取得
		G_EntryExitInfo G_EntryExitInfo = new G_EntryExitInfo();
		G_EntryExitInfo.setEnExId(request.getParameter("enExId"));
		G_EntryExitInfo.setProductNo(request.getParameter("productNo"));
		G_EntryExitInfo.setProductName(request.getParameter("productName"));
		G_EntryExitInfo.setwStockQty(request.getParameter("wStockQty"));
		G_EntryExitInfo.setOowStockQty(request.getParameter("oowStockQty"));
		G_EntryExitInfo.setStockInOut(request.getParameter("stockInOut"));
		G_EntryExitInfo.setEnExDate(request.getParameter("enExDate"));
		G_EntryExitInfo.setEnExNum(request.getParameter("enExNum"));
		G_EntryExitInfo.setReason(request.getParameter("reason"));
		if (request.getParameter("stockInOut").equals("in")) {
			G_EntryExitInfo.setNyukoQty(request.getParameter("enExNum"));
			G_EntryExitInfo.setSyukoQty("");
		} else if (request.getParameter("stockInOut").equals("out")) {
			G_EntryExitInfo.setNyukoQty("");
			G_EntryExitInfo.setSyukoQty(request.getParameter("enExNum"));
		}
		G_EntryExitInfo.setBefEnExNum(request.getParameter("befEnExNum"));
		G_EntryExitInfo.setRegistDate(request.getParameter("registDate"));
		session.setAttribute("G_EntryExitInfo", G_EntryExitInfo);
		// 処理種により､処理を分岐
		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			session.setAttribute("btnSelect", btnSelect);
			break;
		case "searchenExId":
			// EnExIdのクリア動作
			if (G_EntryExitInfo.getEnExId().isEmpty()) {
				// 各種セッション属性のnullクリア
				new MainAction().crearAttributeForScreenChange(session);
				session.setAttribute("btnSelect", btnSelect);
				break;
			}
			// テーブル検索
			entryExitInfoForChange = eeiDAO.searchByEnId(G_EntryExitInfo);
			if (entryExitInfoForChange == null) {
				// 各種セッション属性のnullクリア
				new MainAction().crearAttributeForScreenChange(session);
				// EnExId以外の項目を空欄にする
				G_EntryExitInfo = new G_EntryExitInfo();
				G_EntryExitInfo.setEnExId(request.getParameter("enExId"));
				session.setAttribute("G_EntryExitInfo", G_EntryExitInfo);
				session.setAttribute("btnSelect", btnSelect);
				session.setAttribute("message", "入力値に該当する入出庫番号は存在しません。\\n入力内容を確認ください。");
				break;
			} else if (entryExitInfoForChange != null) {
				G_EntryExitInfo.setEnExId(entryExitInfoForChange.getEnExId());
				G_EntryExitInfo.setProductNo(entryExitInfoForChange.getProductNo());
				G_EntryExitInfo.setProductName(request.getParameter("productName"));
				G_EntryExitInfo.setwStockQty(request.getParameter("wStockQty"));
				G_EntryExitInfo.setOowStockQty(request.getParameter("oowStockQty"));
				G_EntryExitInfo.setEnExDate(entryExitInfoForChange.getEnExDate());
				// 入庫数/出庫数の0以上の値が入っている数量を｢enExNum｣とし､｢stockInOut｣の値を規定する
				if (entryExitInfoForChange.getNyukoQty() > 0 && entryExitInfoForChange.getSyukoQty() == 0) {
					G_EntryExitInfo.setStockInOut("in");
					G_EntryExitInfo.setEnExNum(String.valueOf(entryExitInfoForChange.getNyukoQty()));
				} else if (entryExitInfoForChange.getNyukoQty() == 0 && entryExitInfoForChange.getSyukoQty() > 0) {
					G_EntryExitInfo.setStockInOut("out");
					G_EntryExitInfo.setEnExNum(String.valueOf(entryExitInfoForChange.getSyukoQty()));
				}
				G_EntryExitInfo.setReason(entryExitInfoForChange.getReason());
				G_EntryExitInfo.setNyukoQty(String.valueOf(entryExitInfoForChange.getNyukoQty()));
				G_EntryExitInfo.setSyukoQty(String.valueOf(entryExitInfoForChange.getSyukoQty()));
				G_EntryExitInfo.setBefEnExNum(G_EntryExitInfo.getEnExNum());
				G_EntryExitInfo.setRegistDate(entryExitInfoForChange.getRegistDate());
				session.setAttribute("G_EntryExitInfo", G_EntryExitInfo);
			}
			// テーブル検索
			ProductMaster = pmDAO.searchByProNo(G_EntryExitInfo);
			PuroductStock = psDAO.searchByDate(G_EntryExitInfo);
			session.setAttribute("ProductMaster", ProductMaster);
			session.setAttribute("PuroductStock", PuroductStock);
			break;
		case "searchProductNo":
			// ProductNoのクリア動作
			if (G_EntryExitInfo.getProductNo().isEmpty()) {
				G_EntryExitInfo.setEnExNum("");
				session.setAttribute("G_EntryExitInfo", G_EntryExitInfo);
				break;
			}
			// テーブル検索
			ProductMaster = pmDAO.searchByProNo(G_EntryExitInfo);
			if (!G_EntryExitInfo.getProductNo().isEmpty()) {
				if (ProductMaster == null) {
					session.setAttribute("message", "入力値に該当する品番は存在しません。\\n入力内容を確認ください。");
				}
			}
			PuroductStock = psDAO.searchByDate(G_EntryExitInfo);
			session.setAttribute("ProductMaster", ProductMaster);
			session.setAttribute("PuroductStock", PuroductStock);
			break;
		case "doBTNExecute":
			int line = 0;
			// トランザクション処理準備
			Connection con = eeiDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				// テーブル｢ENTRY_EXIT_INFO｣｢PURODUCT_STOCK｣に対する処理方針
				// ･小売業/卸売業などの在庫管理を想定
				// →仕掛り在庫/半製品在庫/部品在庫など､細かな在庫分類は行わず､｢全在庫｣｢倉庫在庫｣のみ分類し管理する
				// ･出荷/入荷と入庫/出庫を区別し､出荷/入荷した商品は入庫/出庫するまで倉庫外在庫として扱う
				// →入荷/出荷のタイミングでは､テーブル｢PURODUCT_STOCK｣のみ変更する(←倉庫外在庫の増減として扱う=出荷/入荷では｢ENTRY_EXIT_INFO｣を変更しない)
				// ･テーブル｢PURODUCT_STOCK｣の｢現在在庫数｣は､倉庫在庫ではなく､倉庫外在庫/倉庫在庫の合計とする
				// ･テーブル｢PURODUCT_STOCK｣に新たに｢当月入荷数｣を追加し､｢現在在庫数｣は入荷/出荷のタイミングでのみ増減させ､入庫/出庫のタイミングでは増減させない
				// →入庫/出庫のタイミングで｢現在在庫数｣を増減させると､入荷/出荷と入庫/出庫でダブルカウントする事になり､正しい在庫数とならない為
				// ･テーブル｢PURODUCT_STOCK｣に新たに｢現在倉庫在庫数｣を追加し､
				// 入庫/出庫のタイミングでは､｢現在倉庫在庫数｣のみを増減させる(←その際､｢現在在庫数｣は増減させない=倉庫への出し入れは､在庫数全体の増減にはならない為)
				// ･出荷/入荷処理の取消処理時のテーブル｢PURODUCT_STOCK｣処理を以下とする
				// 出荷取消処理：テーブル｢PURODUCT_STOCK｣の出荷月の｢当月出荷数｣を取消数分マイナスし､出荷月以降の｢現在在庫数｣を取消数分プラスする
				// 入荷取消処理：テーブル｢PURODUCT_STOCK｣の入荷月の｢当月入荷数｣を取消数分マイナスし､入荷月以降の｢現在在庫数｣を取消数分マイナスする
				// ･入庫/出庫の更新/取消処理時のテーブル｢PURODUCT_STOCK｣処理を以下とする
				// 入庫の更新/取消処理：テーブル｢PURODUCT_STOCK｣の入出庫テーブルの日付月の｢当月入庫数｣を更新/取消数分増減させ､入出庫テーブルの日付月以降の｢現在倉庫在庫数｣を｢当月入庫数｣の増減と同じ符号の増減を行う
				// 出庫の更新/取消処理：テーブル｢PURODUCT_STOCK｣の入出庫テーブルの日付月の｢当月出庫数｣を更新/取消数分増減させ､入出庫テーブルの日付月以降の｢現在倉庫在庫数｣を｢当月出庫数｣の増減と逆の符号の増減を行う
				// ･DBで登録する日付は､テーブル毎に取得するのではなく､共通の日付を使用する
				// →このシステムでは､登録日等の｢PCの日時を取得する処理｣はDAOにて取得としているが､在庫テーブル処理は､Actionで取得した日時を処理を行う複数のテーブル全てに使用する動作とする
				// →稀なケースだが､テーブルを渡る処理が月またぎとなった場合､整合性が取れなくなる為､他のテーブルと年月を揃える事が目的
//				if (btnSelect.equals("update")) {
//					// テーブル｢PURCHASE_ORDER｣更新処理
//					line += eeiDAO.updateForShipByPoNo(G_EntryExitInfo, "1", request);
//				} else if (btnSelect.equals("delete")) {
//					// テーブル｢PURCHASE_ORDER｣更新処理
//					line += eeiDAO.updateForShipByPoNo(G_EntryExitInfo, "0", request);
//				}
//				// テーブル｢PURODUCT_STOCK(=在庫テーブル)｣処理(登録/更新)
//				line += psDAO.productStockProcess(G_EntryExitInfo);
//				// 成功/失敗判定
//				if (line == 2) {
//					con.commit();
//					// 各種セッション属性のnullクリア
//					new MainAction().crearAttributeForScreenChange(session);
//					session.setAttribute("message", "処理が正常に終了しました｡");
//				} else {
//					con.rollback();
//					session.setAttribute("message", "処理中に異常が発生しました｡\\n処理は行われていません｡");
//				}
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
		// プルダウン用リスト取得
		EntryExitInfoList = eeiDAO.searchAll();
		session.setAttribute("EntryExitInfoList", EntryExitInfoList);
		ProductMasterList = pmDAO.searchAll();
		session.setAttribute("ProductMasterList", ProductMasterList);
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/entryExitInfo.jsp");
		return "/WEB-INF/main/entryExitInfo.jsp";
	}
}