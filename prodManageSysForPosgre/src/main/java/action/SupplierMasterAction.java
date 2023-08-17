package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_SupplierMaster;
import tool.Action;

public class SupplierMasterAction extends Action {

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
			session.setAttribute("G_SupplierMaster", null);

			session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
			return "/WEB-INF/main/login.jsp";
		}

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
		
		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 不要セッション属性をnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("message", null);
			session.setAttribute("G_SupplierMaster", null);
			break;
		case "searchSupplierNo":
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
			session.setAttribute("G_SupplierMaster", null);
			break;
		}
		session.setAttribute("nextJsp", "/WEB-INF/main/supplierMaster.jsp");
		return "/WEB-INF/main/supplierMaster.jsp";
//		// ログイン状態確認
//		// →セッション切れで､購入画面｢index.jsp｣へ遷移
//		HttpSession session = request.getSession();
//		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
//		//セッション切れ処理
//		if (loginStatusCheck == 0) {
//			request.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
//			//画面「amountCalc.jsp」「amountCalcOrder.jsp」で使用したセッション属性のnullクリア
//			session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
//			return "/WEB-INF/main/login.jsp";
//		}
//
//		String gettoAction = request.getParameter("toAction");
//		String dousa = request.getParameter("dousa");
//		//jsp画面の入力内容を取得する。
//
//		SupplierMaster SM = new SupplierMaster();
//		//SupplierMasterDAOを使える様にインスタンス化する
//		SupplierMasterDAO smDao = new SupplierMasterDAO();
//
//		SM.setSupplierNo(request.getParameter("SupplierNo"));
//		SM.setSupplierName(request.getParameter("SupplierName"));
//		SM.setBranchName(request.getParameter("BranchName"));
//		SM.setZipNo(request.getParameter("ZipNo"));
//		SM.setAddress1(request.getParameter("Address1"));
//		SM.setAddress2(request.getParameter("Address2"));
//		SM.setAddress3(request.getParameter("Address3"));
//		SM.setTel(request.getParameter("Tel"));
//		SM.setFax(request.getParameter("Fax"));
//		SM.setManager(request.getParameter("Manager"));
//		SM.setEtc(request.getParameter("Etc"));
//		SM.setRegistDate(request.getParameter("RegistDate"));
//		SM.setRegistUser(request.getParameter("RegistUser"));
//
//		switch (gettoAction) {
//		case "searchNo":
//			//SupplierMasterDAO のメッソドを呼び出す。
//			//呼び出した結果を取得する。getSm
//			SupplierMaster getSm = smDao.searchBySupNo(SM);
//			if (getSm == null) {
//				request.setAttribute("message", "入力された番号は登録されていません。");
//				request.setAttribute("sup", SM);
//			} else {
//				request.setAttribute("sup", getSm);
//			}
//
//			break;
//		case "update":
//			SupplierMasterDAO updateDao = new SupplierMasterDAO();
//			int update = 0;
//			try {
//				update = updateDao.updateBySupNo(SM, request);
//				if (update == 1) {
//					request.setAttribute("message", "更新に成功しました。");
//				} else {
//					request.setAttribute("message", "更新に失敗しました。");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			break;
//
//		case "insert":
//			SupplierMasterDAO insertDao = new SupplierMasterDAO();
//			if (SM.getSupplierName() == "" || SM.getTel() == "") {
//				request.setAttribute("message", "必須項目が入力されていません。");
//				break;
//			}
//
//			int insert = 0;
//			try {
//				insert = insertDao.insert(SM, request);
//				if (insert == 1) {
//					request.setAttribute("message", "登録に成功しました。");
//				} else {
//					request.setAttribute("message", "登録に失敗しました。");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			break;
//		case "zipno":
//			ZipDAO zd = new ZipDAO();
//			List<ZipData> lzd = zd.searchAllByZipNo(SM.getZipNo());
//			if (lzd.size() == 0) {
//				request.setAttribute("message", "郵便番号は存在しません。");
//			} else {
//				SM.setAddress1(lzd.get(0).getPref());
//				SM.setAddress2(lzd.get(0).getCity());
//				SM.setAddress3(lzd.get(0).getVillege());
//			}
//			request.setAttribute("sup", SM);
//			break;
//		default:
//			dousa = null;
//			break;
//		}
	}

}
