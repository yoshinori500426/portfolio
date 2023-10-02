package action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_SupplierMaster;
import bean.SupplierMaster;
import dao.SupplierMasterDAO;
import tool.Action;

public class SupplierMasterAction extends Action {

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
		SupplierMasterDAO smDAO = new SupplierMasterDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		SupplierMaster supplierForChange = null;
		List<SupplierMaster> SupplierMasterList = null;
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面情報取得
		G_SupplierMaster G_SupplierMaster = new G_SupplierMaster();
		G_SupplierMaster.setSupplierNo(request.getParameter("supplierNo"));
		G_SupplierMaster.setSupplierName(request.getParameter("supplierName"));
		G_SupplierMaster.setBranchName(request.getParameter("branchName"));
		G_SupplierMaster.setZipNo(request.getParameter("zipNo"));
		G_SupplierMaster.setAddress1(request.getParameter("address1"));
		G_SupplierMaster.setAddress2(request.getParameter("address2"));
		G_SupplierMaster.setAddress3(request.getParameter("address3"));
		G_SupplierMaster.setTel(request.getParameter("tel"));
		G_SupplierMaster.setFax(request.getParameter("fax"));
		G_SupplierMaster.setManager(request.getParameter("manager"));
		G_SupplierMaster.setEtc(request.getParameter("etc"));
		session.setAttribute("G_SupplierMaster", G_SupplierMaster);
		// 処理種により､処理を分岐
		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			session.setAttribute("btnSelect", btnSelect);
			break;
		case "searchSupplierNo":
			// テーブル検索
			supplierForChange = smDAO.searchBySupNo(G_SupplierMaster);
			if (supplierForChange == null) {
				session.setAttribute("message", "入力値に該当する仕入先コードは存在しません。\\n入力内容を確認ください。");
				break;
			} else if (supplierForChange != null) {
				G_SupplierMaster.setSupplierNo(supplierForChange.getSupplierNo());
				G_SupplierMaster.setSupplierName(supplierForChange.getSupplierName());
				G_SupplierMaster.setBranchName(supplierForChange.getBranchName());
				G_SupplierMaster.setZipNo(supplierForChange.getZipNo());
				G_SupplierMaster.setAddress1(supplierForChange.getAddress1());
				G_SupplierMaster.setAddress2(supplierForChange.getAddress2());
				G_SupplierMaster.setAddress3(supplierForChange.getAddress3());
				G_SupplierMaster.setTel(supplierForChange.getTel());
				G_SupplierMaster.setFax(supplierForChange.getFax());
				G_SupplierMaster.setManager(supplierForChange.getManager());
				G_SupplierMaster.setEtc(supplierForChange.getEtc());
				session.setAttribute("G_SupplierMaster", G_SupplierMaster);
			}
			break;
		case "doBTNExecute":
			int line = 0;
			// トランザクション処理準備
			Connection con = smDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				if (btnSelect.equals("insert")) {
					line = smDAO.insert(G_SupplierMaster, request);
				} else if (btnSelect.equals("update")) {
					line = smDAO.updateBySupNo(G_SupplierMaster, request);
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
		SupplierMasterList = smDAO.searchAll();
		session.setAttribute("SupplierMasterList", SupplierMasterList);
		return "/WEB-INF/main/supplierMaster.jsp";
	}
}
