package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_CustomerMaster;
import tool.Action;

public class CustomerMasterAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
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
			session.setAttribute("G_CustomerMaster", null);

			session.setAttribute("nextJsp", "/WEB-INF/main/login.jsp");
			return "/WEB-INF/main/login.jsp";
		}

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

		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 不要セッション属性をnullクリア
			session.setAttribute("alert", null);
			session.setAttribute("message", null);
			session.setAttribute("G_CustomerMaster", null);
			break;
		case "searchCustomerNo":
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
			session.setAttribute("G_CustomerMaster", null);
			break;
		}
		session.setAttribute("nextJsp", "/WEB-INF/main/customerMaster.jsp");
		return "/WEB-INF/main/customerMaster.jsp";
//		// ログイン状態確認
//		// →セッション切れで､購入画面｢index.jsp｣へ遷移
//		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
//		//セッション切れ処理
//		if (loginStatusCheck == 0) {
//			request.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
//
//			return "/WEB-INF/main/login.jsp";
//		}
//
//		String nakami = request.getParameter("toAction");
//		String getdousa = request.getParameter("dousa");
//		//顧客情報を入れるビーン
//		CustomerMaster cm = new CustomerMaster();
//
//
//		//追記★
//		request.setAttribute("nakami", nakami);
//
//		//画面の入力内容をBean cm　に格納する
//		cm.setCustomerNo(request.getParameter("CustomerNo"));
//		cm.setCustomerName(request.getParameter("CustomerName"));
//		cm.setBranchName(request.getParameter("BranchName"));
//		cm.setZipNo(request.getParameter("ZipNo"));
//		cm.setAddress1(request.getParameter("Address1"));
//		cm.setAddress2(request.getParameter("Address2"));
//		cm.setAddress3(request.getParameter("Address3"));
//		cm.setTel(request.getParameter("Tel"));
//		cm.setFax(request.getParameter("Fax"));
//		cm.setManager(request.getParameter("Manager"));
//		if (request.getParameter("DelivaryLeadTime") != null && !request.getParameter("DelivaryLeadTime").isEmpty()) {
//			cm.setDelivaryLeadtime(Integer.parseInt(request.getParameter("DelivaryLeadTime")));
//		}
//		cm.setEtc(request.getParameter("Etc"));
//
//		//ログインユーザー情報を取得
//		HttpSession session = request.getSession();
//		session.getAttribute("user");
//		UserMaster um = (UserMaster) session.getAttribute("user");
//		cm.setRegistuser(um.getUserId());
//
//		switch (nakami) {
//		case "searchNo"://顧客ナンバーでテーブル検索
//			//CustomerMasterDAOのインスタンス化
//			CustomerMasterDAO dao = new CustomerMasterDAO();
//			//画面　顧客ナンバーに入力された値をDAOのメソッドに送り返されたデータを　getCustomerに格納
//			CustomerMaster getCustomer = dao.searchByCusNo(cm.getCustomerNo());
//			//取得結果がnullの場合はメッセージを画面へ送る
//			if (getCustomer == null) {
//				request.setAttribute("message", "入力された顧客番号は存在しません");
//			} else {//検索結果が見つかった場合データベースより結果を取得
//
//				request.setAttribute("ALL", getCustomer);
//			}
//			break;
//
//		case "DelivaryLeadTimeCheck":
//			if (cm.getDelivaryLeadtime() < 1) {
//				request.setAttribute("message", "最小輸送リードタイムは”1”です");
//
//			} else if ((cm.getCustomerName() != null && cm.getCustomerName().length() != 0)
//					&& (cm.getTel() != null && cm.getTel().length() != 0)
//					&& (cm.getDelivaryLeadtime() != 0)) {
//				request.setAttribute("upDateCheck", "ok");
//			}
//
//			request.setAttribute("ALL", cm);
//
//			break;
//
//		case "TelNoCheck":
//			if (cm.getTel().length() < 8) {
//				request.setAttribute("message", "8文字以上での入力をしてください");
//
//			} else if ((cm.getCustomerName() != null && cm.getCustomerName().length() != 0)
//					&& (cm.getTel() != null && cm.getTel().length() != 0)
//					&& (cm.getDelivaryLeadtime() != 0)) {
//				request.setAttribute("upDateCheck", "ok");
//			}
//			request.setAttribute("ALL", cm);
//
//			break;
//		case "customerNameCheck":
//			if (cm.getCustomerName()== "" || cm.getCustomerName() == null) {
//				request.setAttribute("message", "顧客名は必須入力してください");
//
//			} else if ((cm.getCustomerName() != null && cm.getCustomerName().length() != 0)
//					&& (cm.getTel() != null && cm.getTel().length() != 0)
//					&& (cm.getDelivaryLeadtime() != 0)) {
//				request.setAttribute("upDateCheck", "ok");
//			}
//			request.setAttribute("ALL", cm);
//
//			break;
//
//		case "insert"://新規登録
//			CustomerMasterDAO insertDao = new CustomerMasterDAO();
//			int result_insert = 0; //新規登録の実行結果　0or1
//			try {
//				result_insert = insertDao.insertCustomerMaster(cm);
//				if (result_insert > 0) {
//					request.setAttribute("message", "登録に成功しました");
////					request.setAttribute("End", "AllOkEnd");
//				} else {
//					request.setAttribute("message", "登録に失敗しました");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			break;
//		case "update":
//			CustomerMasterDAO updateDao = new CustomerMasterDAO();
//			int result_update = 0;
//			try {
//				result_update = updateDao.updateByCusNo(cm);
//				if (result_update == 1) {
//					request.setAttribute("message", "更新に成功しました");
////					request.setAttribute("End", "AllOkEnd");
//
//				} else {
//					request.setAttribute("message", "更新に失敗しました");
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			break;
//
//		case "getZip":
//			ZipDAO getzipDAO = new ZipDAO();
//			//			ZipData setZipDataBean = null;
//			List<ZipData> getZipDataBean = null;
//			String getZip = cm.getZipNo();
//			try {
//				if (getZip.replace("-", "").replace("ー", "").length() < 7) {
//					request.setAttribute("message", "郵便番号の入力は整数7桁で入力してください");
//
//				} else {
//					getZipDataBean = getzipDAO.searchAllByZipNo(getZip);
//
//					if (getZipDataBean.size() == 0) {
//						request.setAttribute("message", "郵便番号が見つかりません");
//						request.setAttribute("ALL", cm);
//
//					}
//					cm.setZipNo(getZipDataBean.get(0).getZipno());
//					cm.setAddress1(getZipDataBean.get(0).getPref());
//					cm.setAddress2(getZipDataBean.get(0).getCity() + getZipDataBean.get(0).getVillege());
//
//					request.setAttribute("ALL", cm);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			break;
//
//		case "AllOkEnd":
//			cm = null;
//			getdousa = null;
////			request.setAttribute("End", null);
//
//			break;
//
//		default:
//			cm = null;
//			getdousa = null;
//			break;
//		}

	}

}
