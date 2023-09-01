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
//	// ログイン状態確認
//	// →セッション切れで､購入画面｢index.jsp｣へ遷移
//	HttpSession session = request.getSession();
//	int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
//	//セッション切れ処理
//	if (loginStatusCheck == 0) {
//		request.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
//		//画面「amountCalc.jsp」「amountCalcOrder.jsp」で使用したセッション属性のnullクリア
//		session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
//		return "/WEB-INF/main/login.jsp";
//	}
//
//	String gettoAction = request.getParameter("toAction");
//	String dousa = request.getParameter("dousa");
//	//jsp画面の入力内容を取得する。
//
//	SupplierMaster SM = new SupplierMaster();
//	//SupplierMasterDAOを使える様にインスタンス化する
//	SupplierMasterDAO smDao = new SupplierMasterDAO();
//
//	SM.setSupplierNo(request.getParameter("SupplierNo"));
//	SM.setSupplierName(request.getParameter("SupplierName"));
//	SM.setBranchName(request.getParameter("BranchName"));
//	SM.setZipNo(request.getParameter("ZipNo"));
//	SM.setAddress1(request.getParameter("Address1"));
//	SM.setAddress2(request.getParameter("Address2"));
//	SM.setAddress3(request.getParameter("Address3"));
//	SM.setTel(request.getParameter("Tel"));
//	SM.setFax(request.getParameter("Fax"));
//	SM.setManager(request.getParameter("Manager"));
//	SM.setEtc(request.getParameter("Etc"));
//	SM.setRegistDate(request.getParameter("RegistDate"));
//	SM.setRegistUser(request.getParameter("RegistUser"));
//
//	switch (gettoAction) {
//	case "searchNo":
//		//SupplierMasterDAO のメッソドを呼び出す。
//		//呼び出した結果を取得する。getSm
//		SupplierMaster getSm = smDao.searchBySupNo(SM);
//		if (getSm == null) {
//			request.setAttribute("message", "入力された番号は登録されていません。");
//			request.setAttribute("sup", SM);
//		} else {
//			request.setAttribute("sup", getSm);
//		}
//
//		break;
//	case "update":
//		SupplierMasterDAO updateDao = new SupplierMasterDAO();
//		int update = 0;
//		try {
//			update = updateDao.updateBySupNo(SM, request);
//			if (update == 1) {
//				request.setAttribute("message", "更新に成功しました。");
//			} else {
//				request.setAttribute("message", "更新に失敗しました。");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		break;
//
//	case "insert":
//		SupplierMasterDAO insertDao = new SupplierMasterDAO();
//		if (SM.getSupplierName() == "" || SM.getTel() == "") {
//			request.setAttribute("message", "必須項目が入力されていません。");
//			break;
//		}
//
//		int insert = 0;
//		try {
//			insert = insertDao.insert(SM, request);
//			if (insert == 1) {
//				request.setAttribute("message", "登録に成功しました。");
//			} else {
//				request.setAttribute("message", "登録に失敗しました。");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		break;
//	case "zipno":
//		ZipDAO zd = new ZipDAO();
//		List<ZipData> lzd = zd.searchAllByZipNo(SM.getZipNo());
//		if (lzd.size() == 0) {
//			request.setAttribute("message", "郵便番号は存在しません。");
//		} else {
//			SM.setAddress1(lzd.get(0).getPref());
//			SM.setAddress2(lzd.get(0).getCity());
//			SM.setAddress3(lzd.get(0).getVillege());
//		}
//		request.setAttribute("sup", SM);
//		break;
//	default:
//		dousa = null;
//		break;
//	}

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
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/supplierMaster.jsp");
		return "/WEB-INF/main/supplierMaster.jsp";
	}
}
