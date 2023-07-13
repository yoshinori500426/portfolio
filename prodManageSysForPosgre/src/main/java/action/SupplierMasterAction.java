package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SupplierMaster;
import bean.ZipData;
import dao.SupplierMasterDAO;
import dao.ZipDAO;
import tool.Action;

public class SupplierMasterAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ログイン状態確認
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		HttpSession session = request.getSession();
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		//セッション切れ処理
		if (loginStatusCheck == 0) {
			request.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
			//画面「amountCalc.jsp」「amountCalcOrder.jsp」で使用したセッション属性のnullクリア
			session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
			return "/WEB-INF/main/login.jsp";
		}

		String gettoAction = request.getParameter("toAction");
		String dousa = request.getParameter("dousa");
		//jsp画面の入力内容を取得する。

		SupplierMaster SM = new SupplierMaster();
		//SupplierMasterDAOを使える様にインスタンス化する
		SupplierMasterDAO smDao = new SupplierMasterDAO();

		SM.setSupplierNo(request.getParameter("SupplierNo"));
		SM.setSupplierName(request.getParameter("SupplierName"));
		SM.setBranchName(request.getParameter("BranchName"));
		SM.setZipNo(request.getParameter("ZipNo"));
		SM.setAddress1(request.getParameter("Address1"));
		SM.setAddress2(request.getParameter("Address2"));
		SM.setAddress3(request.getParameter("Address3"));
		SM.setTel(request.getParameter("Tel"));
		SM.setFax(request.getParameter("Fax"));
		SM.setManager(request.getParameter("Manager"));
		SM.setEtc(request.getParameter("Etc"));
		SM.setRegistDate(request.getParameter("RegistDate"));
		SM.setRegistUser(request.getParameter("RegistUser"));

		switch (gettoAction) {
		case "searchNo":
			//SupplierMasterDAO のメッソドを呼び出す。
			//呼び出した結果を取得する。getSm
			SupplierMaster getSm = smDao.searchBySupNo(SM);
			if (getSm == null) {
				request.setAttribute("message", "入力された番号は登録されていません。");
				request.setAttribute("sup", SM);
			} else {
				request.setAttribute("sup", getSm);
			}

			break;
		case "update":
			SupplierMasterDAO updateDao = new SupplierMasterDAO();
			int update = 0;
			try {
				update = updateDao.updateBySupNo(SM, request);
				if (update == 1) {
					request.setAttribute("message", "更新に成功しました。");
				} else {
					request.setAttribute("message", "更新に失敗しました。");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		case "insert":
			SupplierMasterDAO insertDao = new SupplierMasterDAO();
			if (SM.getSupplierName() == "" || SM.getTel() == "") {
				request.setAttribute("message", "必須項目が入力されていません。");
				break;
			}

			int insert = 0;
			try {
				insert = insertDao.insert(SM, request);
				if (insert == 1) {
					request.setAttribute("message", "登録に成功しました。");
				} else {
					request.setAttribute("message", "登録に失敗しました。");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "zipno":
			ZipDAO zd = new ZipDAO();
			List<ZipData> lzd = zd.searchAllByZipNo(SM.getZipNo());
			if (lzd.size() == 0) {
				request.setAttribute("message", "郵便番号は存在しません。");
			} else {
				SM.setAddress1(lzd.get(0).getPref());
				SM.setAddress2(lzd.get(0).getCity());
				SM.setAddress3(lzd.get(0).getVillege());
			}
			request.setAttribute("sup", SM);
			break;
		default:
			dousa = null;
			break;
		}
		request.setAttribute("dousa", dousa);
		return "/WEB-INF/main/supplierMaster.jsp";
	}

}
