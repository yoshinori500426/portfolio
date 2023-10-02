package action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_AmountCalcOrder;
import bean.OrderTable;
import dao.AmountCalcDAO;
import dao.OrderTableDAO;
import tool.Action;

public class AmountCalcAction extends Action implements Runnable {
	// Runnnableインターフェイスのメソッド｢run()｣は､引数を持てない為､
	// AmountCalcActionDAOのメソッドに必要なインスタンス｢request｣をフィールド経由で渡そうとしたが、
	// インスタンス｢request｣は渡せたが、「request.getSession();」でセッションのインスタンスが取得できない不具合発生
	// その為、セッションのインスタンスをRunnnableインターフェイスのメソッド｢run()｣へ渡す事とする
	private HttpSession session;

	// コンストラクタ作成
	// メソッド｢run()｣にインスタンス｢request｣を渡す為の処理
	// FrontControllerでインスタンス化する際に必要なコンストラクタ
	public AmountCalcAction() {
	}

	// コンストラクタ作成
	// メソッド｢run()｣にインスタンス｢request｣を渡す為の処理
	// Thredのインスタンスを作成する際に使用するコンストラクタ
	public AmountCalcAction(HttpServletRequest request) {
		HttpSession session = request.getSession();
		this.session = session;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 処理準備
		HttpSession session = request.getSession();
		Thread thread = null;
		AmountCalcDAO acDAO = null;
		G_AmountCalcOrder G_AmountCalcOrder = null;

		// ログイン状態確認
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		// セッション切れ処理
		if (loginStatusCheck == 0) {
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			// メッセージ作成
			session.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
			return "/WEB-INF/main/login.jsp";
		}

		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		session.setAttribute("toAction", toAction);

		// 処理種により､処理を分岐
		switch (toAction) {
		case "startAmountCalc":
			// メソッド｢run()｣開始
			thread = new Thread(new AmountCalcAction(request));
			thread.start();
			// キャンセル等､スレッドのハンドリングを行う為、作成したスレッドクラスのインスタンス「thread」をセッション属性に保存
			session.setAttribute("therad", thread);
			// AmountCalcDAOで使用するセッション属性のnullクリア
			// →「amountCalcProgFlg」のみ処理中の「1」へ変更
			new AmountCalcDAO().outPutMSG(session, null, null, null, "1");
			new AmountCalcDAO().changeAttribute(session, null, null);
			break;
		case "processNow":
			// 処理状況更新を目的に、規定秒数事に「processNow」動作が行われる
			// →更新周期は、「header.jsp」ファイルのJavaScript「window.setTimeout()」で規定
			break;
		case "goToAmountCalcOrderPage":
			// スレッドの生存状況を確認
			// スレッドが生存していれば「true」、そうでなければ「false」
			thread = (Thread) session.getAttribute("therad");
			if (thread != null && thread.isAlive() == true) {
				// スレッドが生存していれば､スレッドの終了を待つ
				thread.join();
			}
			// AmountCalcDAOで使用したセッション属性のnullクリア
			session.setAttribute("therad", null);
			new AmountCalcDAO().outPutMSG(session, null, null, null, null);
			return "/WEB-INF/main/amountCalcOrder.jsp";
		case "productNoCheck":
		case "orderLotNumCheck":
		case "":
			// ページ｢amountCalcOrder.jsp｣の値をbean｢G_AmountCalcOrder.java｣に格納
			G_AmountCalcOrder = getG_AmountCalcOrderParam(request, response);
			return "/WEB-INF/main/amountCalcOrder.jsp";
		case "doOrder":
			// 入力値再確認
			// ページ｢amountCalcOrder.jsp｣の値をbean｢G_AmountCalcOrder.java｣に格納
			G_AmountCalcOrder = getG_AmountCalcOrderParam(request, response);
			// bean｢G_AmountCalcOrder.java｣の必要項目を､bean｢OrderTable.java｣へ格納
			OrderTable ot = new OrderTable();
			ot.setSupplierNo(G_AmountCalcOrder.getSupplierNo());
			ot.setProductNo(G_AmountCalcOrder.getProductNo());
			ot.setOrderQty(Integer.parseInt(G_AmountCalcOrder.getOrderQty()));
			ot.setDeliveryDate(G_AmountCalcOrder.getExpectedDeliveryDate());
			ot.setFinFlg("0");
			// DAO使用準備
			OrderTableDAO otDAO = new OrderTableDAO();
			// 処理結果取得準備
			int line = 0;
			// 発注処理
			// トランザクション処理準備
			Connection con = otDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				line = otDAO.insert(ot, request);
				// 成功/失敗判定
				if (line == 1) {
					con.commit();
					session.setAttribute("state", "処理が正常に終了しました｡");
				} else {
					con.rollback();
					session.setAttribute("state", "処理中に異常が発生しました｡/n処理は行われていません｡");
				}
				// トランザクション処理終了
				con.setAutoCommit(true);
			}
			// コネクションクローズ処理
			//　➔このコネクションClose処理が抜けると､複数回の動作でプールを使い果たし､コネクションが取得できずにフリーズする
			con.close();
			// 発注処理後の所要量取得
			// →排他処理/トランザクション処理不要の為､分けて記述
			// 成功/失敗判定
			if (line == 1) {
				acDAO = new AmountCalcDAO();
				acDAO.searchByOrdNo(session, ot);
			}
			// 発注ロット数をクリアする
			G_AmountCalcOrder.setOrderLotNum(null);
			session.setAttribute("G_AmountCalcOrder", G_AmountCalcOrder);
			return "/WEB-INF/main/amountCalcOrder.jsp";
		// 「case "doOrder":」実施後、Webブラウザの「更新」で「case "doOrder":」が走ってしまう。
		// その回避の為、「case "doOrder":」後のアラートダイヤログ「OK」押下で「case "aftDoOrder":」を走らせる事で、
		// Webブラウザの「更新」動作を「case "doOrder":」から「case "aftDoOrder":」へ切り替える
		case "aftDoOrder":
			return "/WEB-INF/main/amountCalcOrder.jsp";
		case "cancel":
			// キャンセルを行う為、セッション属性に保存指定している
			// クラス「AmountCalcDAO」で動作中のスレッドクラスのインスタンス「thread」を取得
			thread = (Thread) session.getAttribute("therad");
			// インスタンス「threadForCancel」へ、割り込み動作を行う
			thread.interrupt();
			// スレッドの生存状況を確認
			// スレッドが生存していれば「true」、そうでなければ「false」
			thread = (Thread) session.getAttribute("therad");
			if (thread != null && thread.isAlive() == true) {
				// スレッドが生存していれば､スレッドの終了を待つ
				thread.join();
			}
			// 参照用セッション属性をnullクリア
			new AmountCalcDAO().changeAttribute(session, null, null);
			break;
		default:
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			break;
		}
		return "/WEB-INF/main/amountCalc.jsp";
	}

	// 以下､メソッド=================================================================
	/**
	 * VIEW ｢AMOUNT_CALC_ALL｣参照値取得メソッド
	 *
	 * @param 引数無し(間接的に､フィールド経由でインスタンス｢session｣を渡している)
	 * @return 無し(取得リストは､セッション属性で受け渡し)
	 */
	@Override
	public void run() {
		// ｢AmountCalcDAO｣の使用準備
		AmountCalcDAO acDAO = new AmountCalcDAO();
		acDAO.searchAllByOrdNo(session);
	}

	/**
	 * ページ｢amountCalcOrder.jsp｣の値をbean｢G_AmountCalcOrder.java｣に格納するメソッド
	 *
	 * @param request,response
	 * @return bean｢G_AmountCalcOrder｣のインスタンス + セッション属性へのbean｢G_AmountCalcOrder｣格納
	 */
	private G_AmountCalcOrder getG_AmountCalcOrderParam(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		// リクエストパラメーター取得
		String productNo = request.getParameter("gProductNo");
		// 非表示時の｢orderLotNum｣の値保持を目的とした細工
		String orderLotNum;
		String toAction = request.getParameter("toAction");
		if (toAction.equals("orderLotNumCheck") || toAction.equals("")) {
			orderLotNum = request.getParameter("gOrderLotNum");
		} else {
			orderLotNum = request.getParameter("orderLotNumBackUp");
		}
		String productName = request.getParameter("gProductName");
		String supplierNo = request.getParameter("gSupplierNo");
		String supplierName = request.getParameter("gSupplierName");
		String basestock = request.getParameter("gBasestock");
		String latestCumulativeQty = request.getParameter("gLatestCumulativeQty");
		String requiredQty = request.getParameter("gRequiredQty");
		String leadtimeFromSupplier = request.getParameter("gLeadtimeFromSupplier");
		String expectedDeliveryDate = request.getParameter("gExpectedDeliveryDate");
		String lotPcs = request.getParameter("gLotPcs");
		String unitPrice = request.getParameter("gUnitPrice");
		String orderQty = request.getParameter("gOrderQty");
		String orderPrice = request.getParameter("gOrderPrice");

		// 画面入力値をString形式でbeanに格納し､セッション属性｢G_AmountCalcOrder｣へ格納
		G_AmountCalcOrder G_AmountCalcOrder = new G_AmountCalcOrder();
		G_AmountCalcOrder.setProductNo(productNo);
		G_AmountCalcOrder.setOrderLotNum(orderLotNum);
		G_AmountCalcOrder.setProductName(productName);
		G_AmountCalcOrder.setSupplierNo(supplierNo);
		G_AmountCalcOrder.setSupplierName(supplierName);
		G_AmountCalcOrder.setBasestock(basestock);
		G_AmountCalcOrder.setLatestCumulativeQty(latestCumulativeQty);
		G_AmountCalcOrder.setRequiredQty(requiredQty);
		G_AmountCalcOrder.setLeadtimeFromSupplier(leadtimeFromSupplier);
		G_AmountCalcOrder.setExpectedDeliveryDate(expectedDeliveryDate);
		G_AmountCalcOrder.setLotPcs(lotPcs);
		G_AmountCalcOrder.setUnitPrice(unitPrice);
		G_AmountCalcOrder.setOrderQty(orderQty);
		G_AmountCalcOrder.setOrderPrice(orderPrice);
		session.setAttribute("G_AmountCalcOrder", G_AmountCalcOrder);
		return G_AmountCalcOrder;
	}
}