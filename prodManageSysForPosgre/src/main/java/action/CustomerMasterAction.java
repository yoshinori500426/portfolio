package action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CustomerMaster;
import bean.G_CustomerMaster;
import dao.CustomerMasterDAO;
import tool.Action;

public class CustomerMasterAction extends Action {
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
			return "/WEB-INF/main/login.jsp";
		}
		// 各メッセージリセット
		session.setAttribute("alert", null);
		session.setAttribute("message", null);
		session.setAttribute("state", null);
		// 使用DAOインスタンス取得
		CustomerMasterDAO cmDAO = new CustomerMasterDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		CustomerMaster customerForChange = null;
		List<CustomerMaster> CustomerMasterList = null;
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面情報取得
		G_CustomerMaster G_CustomerMaster = new G_CustomerMaster();
		G_CustomerMaster.setCustomerNo(request.getParameter("customerNo"));
		G_CustomerMaster.setCustomerName(request.getParameter("customerName"));
		G_CustomerMaster.setBranchName(request.getParameter("branchName"));
		G_CustomerMaster.setZipNo(request.getParameter("zipNo"));
		G_CustomerMaster.setAddress1(request.getParameter("address1"));
		G_CustomerMaster.setAddress2(request.getParameter("address2"));
		G_CustomerMaster.setAddress3(request.getParameter("address3"));
		G_CustomerMaster.setTel(request.getParameter("tel"));
		G_CustomerMaster.setFax(request.getParameter("fax"));
		G_CustomerMaster.setManager(request.getParameter("manager"));
		G_CustomerMaster.setDelivaryLeadtime(request.getParameter("delivaryLeadtime"));
		G_CustomerMaster.setEtc(request.getParameter("etc"));
		session.setAttribute("G_CustomerMaster", G_CustomerMaster);
		// 処理種により､処理を分岐
		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			session.setAttribute("btnSelect", btnSelect);
			break;
		case "searchCustomerNo":
			// テーブル検索
			customerForChange = cmDAO.searchByCusNo(G_CustomerMaster);
			if (customerForChange == null) {
				session.setAttribute("message", "入力値に該当する顧客コードは存在しません。\\n入力内容を確認ください。");
				break;
			} else if (customerForChange != null) {
				G_CustomerMaster.setCustomerNo(customerForChange.getCustomerNo());
				G_CustomerMaster.setCustomerName(customerForChange.getCustomerName());
				G_CustomerMaster.setBranchName(customerForChange.getBranchName());
				G_CustomerMaster.setZipNo(customerForChange.getZipNo());
				G_CustomerMaster.setAddress1(customerForChange.getAddress1());
				G_CustomerMaster.setAddress2(customerForChange.getAddress2());
				G_CustomerMaster.setAddress3(customerForChange.getAddress3());
				G_CustomerMaster.setTel(customerForChange.getTel());
				G_CustomerMaster.setFax(customerForChange.getFax());
				G_CustomerMaster.setManager(customerForChange.getManager());
				G_CustomerMaster.setDelivaryLeadtime(String.valueOf(customerForChange.getDelivaryLeadtime()));
				G_CustomerMaster.setEtc(customerForChange.getEtc());
				session.setAttribute("G_CustomerMaster", G_CustomerMaster);
			}
			break;
		case "doBTNExecute":
			int line = 0;
			// トランザクション処理準備
			Connection con = cmDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				if (btnSelect.equals("insert")) {
					line = cmDAO.insert(G_CustomerMaster, request);
				} else if (btnSelect.equals("update")) {
					line = cmDAO.updateByCusNo(G_CustomerMaster, request);
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
			// コネクションクローズ処理
			//　➔このコネクションClose処理が抜けると､複数回の動作でプールを使い果たし､コネクションが取得できずにフリーズする
			con.close();
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
		CustomerMasterList = cmDAO.searchAll();
		session.setAttribute("CustomerMasterList", CustomerMasterList);
		return "/WEB-INF/main/customerMaster.jsp";
	}
}
