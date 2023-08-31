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
//	// ログイン状態確認
//	// →セッション切れで､購入画面｢index.jsp｣へ遷移
//	int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
//	//セッション切れ処理
//	if (loginStatusCheck == 0) {
//		request.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");
//
//		return "/WEB-INF/main/login.jsp";
//	}
//
//	String nakami = request.getParameter("toAction");
//	String getdousa = request.getParameter("dousa");
//	//顧客情報を入れるビーン
//	CustomerMaster cm = new CustomerMaster();
//
//
//	//追記★
//	request.setAttribute("nakami", nakami);
//
//	//画面の入力内容をBean cm　に格納する
//	cm.setCustomerNo(request.getParameter("CustomerNo"));
//	cm.setCustomerName(request.getParameter("CustomerName"));
//	cm.setBranchName(request.getParameter("BranchName"));
//	cm.setZipNo(request.getParameter("ZipNo"));
//	cm.setAddress1(request.getParameter("Address1"));
//	cm.setAddress2(request.getParameter("Address2"));
//	cm.setAddress3(request.getParameter("Address3"));
//	cm.setTel(request.getParameter("Tel"));
//	cm.setFax(request.getParameter("Fax"));
//	cm.setManager(request.getParameter("Manager"));
//	if (request.getParameter("DelivaryLeadTime") != null && !request.getParameter("DelivaryLeadTime").isEmpty()) {
//		cm.setDelivaryLeadtime(Integer.parseInt(request.getParameter("DelivaryLeadTime")));
//	}
//	cm.setEtc(request.getParameter("Etc"));
//
//	//ログインユーザー情報を取得
//	HttpSession session = request.getSession();
//	session.getAttribute("user");
//	UserMaster um = (UserMaster) session.getAttribute("user");
//	cm.setRegistuser(um.getUserId());
//
//	switch (nakami) {
//	case "searchNo"://顧客ナンバーでテーブル検索
//		//CustomerMasterDAOのインスタンス化
//		CustomerMasterDAO dao = new CustomerMasterDAO();
//		//画面　顧客ナンバーに入力された値をDAOのメソッドに送り返されたデータを　getCustomerに格納
//		CustomerMaster getCustomer = dao.searchByCusNo(cm.getCustomerNo());
//		//取得結果がnullの場合はメッセージを画面へ送る
//		if (getCustomer == null) {
//			request.setAttribute("message", "入力された顧客番号は存在しません");
//		} else {//検索結果が見つかった場合データベースより結果を取得
//
//			request.setAttribute("ALL", getCustomer);
//		}
//		break;
//
//	case "DelivaryLeadTimeCheck":
//		if (cm.getDelivaryLeadtime() < 1) {
//			request.setAttribute("message", "最小輸送リードタイムは”1”です");
//
//		} else if ((cm.getCustomerName() != null && cm.getCustomerName().length() != 0)
//				&& (cm.getTel() != null && cm.getTel().length() != 0)
//				&& (cm.getDelivaryLeadtime() != 0)) {
//			request.setAttribute("upDateCheck", "ok");
//		}
//
//		request.setAttribute("ALL", cm);
//
//		break;
//
//	case "TelNoCheck":
//		if (cm.getTel().length() < 8) {
//			request.setAttribute("message", "8文字以上での入力をしてください");
//
//		} else if ((cm.getCustomerName() != null && cm.getCustomerName().length() != 0)
//				&& (cm.getTel() != null && cm.getTel().length() != 0)
//				&& (cm.getDelivaryLeadtime() != 0)) {
//			request.setAttribute("upDateCheck", "ok");
//		}
//		request.setAttribute("ALL", cm);
//
//		break;
//	case "customerNameCheck":
//		if (cm.getCustomerName()== "" || cm.getCustomerName() == null) {
//			request.setAttribute("message", "顧客名は必須入力してください");
//
//		} else if ((cm.getCustomerName() != null && cm.getCustomerName().length() != 0)
//				&& (cm.getTel() != null && cm.getTel().length() != 0)
//				&& (cm.getDelivaryLeadtime() != 0)) {
//			request.setAttribute("upDateCheck", "ok");
//		}
//		request.setAttribute("ALL", cm);
//
//		break;
//
//	case "insert"://新規登録
//		CustomerMasterDAO insertDao = new CustomerMasterDAO();
//		int result_insert = 0; //新規登録の実行結果　0or1
//		try {
//			result_insert = insertDao.insertCustomerMaster(cm);
//			if (result_insert > 0) {
//				request.setAttribute("message", "登録に成功しました");
////				request.setAttribute("End", "AllOkEnd");
//			} else {
//				request.setAttribute("message", "登録に失敗しました");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		break;
//	case "update":
//		CustomerMasterDAO updateDao = new CustomerMasterDAO();
//		int result_update = 0;
//		try {
//			result_update = updateDao.updateByCusNo(cm);
//			if (result_update == 1) {
//				request.setAttribute("message", "更新に成功しました");
////				request.setAttribute("End", "AllOkEnd");
//
//			} else {
//				request.setAttribute("message", "更新に失敗しました");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		break;
//
//	case "getZip":
//		ZipDAO getzipDAO = new ZipDAO();
//		//			ZipData setZipDataBean = null;
//		List<ZipData> getZipDataBean = null;
//		String getZip = cm.getZipNo();
//		try {
//			if (getZip.replace("-", "").replace("ー", "").length() < 7) {
//				request.setAttribute("message", "郵便番号の入力は整数7桁で入力してください");
//
//			} else {
//				getZipDataBean = getzipDAO.searchAllByZipNo(getZip);
//
//				if (getZipDataBean.size() == 0) {
//					request.setAttribute("message", "郵便番号が見つかりません");
//					request.setAttribute("ALL", cm);
//
//				}
//				cm.setZipNo(getZipDataBean.get(0).getZipno());
//				cm.setAddress1(getZipDataBean.get(0).getPref());
//				cm.setAddress2(getZipDataBean.get(0).getCity() + getZipDataBean.get(0).getVillege());
//
//				request.setAttribute("ALL", cm);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		break;
//
//	case "AllOkEnd":
//		cm = null;
//		getdousa = null;
////		request.setAttribute("End", null);
//
//		break;
//
//	default:
//		cm = null;
//		getdousa = null;
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
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/customerMaster.jsp");
		return "/WEB-INF/main/customerMaster.jsp";
	}
}
