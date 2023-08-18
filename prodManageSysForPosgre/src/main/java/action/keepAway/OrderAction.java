package action;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CustomerMaster;
import bean.Order;
import bean.ProductMaster;
import bean.UserMaster;
import dao.CustomerMasterDAO;
import dao.ProductMasterDAO;
import dao.PurchaseOrderDAO;
import tool.Action;

public class OrderAction extends Action {

	//取得した顧客名を一旦入れるビーン
	CustomerMaster getCustomerName = new CustomerMaster();
	//取得した商品名を一旦入れるビーン
	ProductMaster getProductName = new ProductMaster();

	public String execute(HttpServletRequest request, HttpServletResponse response) {

		// ログイン状態確認
		// →セッション切れで､購入画面｢index.jsp｣へ遷移
		int loginStatusCheck = new MainAction().loginStatusCheck(request, response);
		//セッション切れ処理
		if (loginStatusCheck == 0) {
			request.setAttribute("message", "セッション切れの為､ログイン画面に移動しました｡");

			return "/WEB-INF/main/login.jsp";
		}

		//order.jsp より画面の指示を取得
		String getSiji = request.getParameter("siji");
		//画面の入力内容を取得し　order.jsp専用ビーンに格納
		Order ob = new Order();
		getScreenInfomation(request, ob);

		//koga追記　ログイン中のユーザー情報の取得
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserMaster um = (UserMaster) session.getAttribute("user");

		ob.setRegist_user(um.getUserId());

		//siji の内容のチェック
		if (getSiji == null || getSiji.isEmpty()) {
			getSiji = "";
		}

		//指示内容で処理の分岐
		switch (getSiji) {
		case "customerNoCheck":
			//顧客名を得るため　CustomerMasterDAO.searchByCusNoメソッドを使用
			CustomerMasterDAO cmd = new CustomerMasterDAO();
			//入力された値を　CustomerMasterDAOに送り返ったデータを新しいビーンに格納
			getCustomerName = cmd.searchByCusNo(ob.getCustomer_No());
			//取得した結果がnullの場合はメッセージをセット
			if (getCustomerName == null) {
				request.setAttribute("message", "入力されたIDは存在しません");
				getCustomerName = new CustomerMaster();
				getCustomerName.setCustomerName("");
				ob.setCustomer_Name(getCustomerName.getCustomerName());

			} else if ((ob.getCustomer_No() != null && ob.getCustomer_No().length() != 0)
					&& (ob.getProduct_No() != null && ob.getProduct_No().length() != 0)
					&&
					(ob.getDelivery_Date() != null && ob.getDelivery_Date().length() != 0)
					&&
					(ob.getOrder_Qty() != 0)

			) {
				ob.setCustomer_Name(getCustomerName.getCustomerName());
				request.setAttribute("check", "4");

			} else {
				//結果がnull出なければ受け取ったビーンに詰めたられたデータを取り出し上書きして画面に送る
				ob.setCustomer_Name(getCustomerName.getCustomerName());
				request.setAttribute("check", "1");

			}

			break;

		case "productNoCheck"://商品IDの存在の有無をチェックし存在した場合適合する商品名を返す
			//商品名を得るため　ProductMasterDAO.searchByProNoSメソッドを使用
			ProductMasterDAO pmd = new ProductMasterDAO();
			//入力された値をProductMasterDAOに送り返ったデータを新しいビーンに格納
			try {
				if (ob.getProduct_No() == null || ob.getProduct_No().equals("")) {
					ob.setProduct_No("");
					ob.setProduct_Name("");
					request.setAttribute("check", "1");
				} else {
					ob.setProduct_No(String.format("%10s", ob.getProduct_No().replace(" ", "")).replace(" ", "0"));
					getProductName = pmd
							.searchByProNoS(ob.getProduct_No());

					//取得した結果がnullの場合はメッセージをセット
					if (getProductName == null) {
						request.setAttribute("check", "1");
						request.setAttribute("message", "入力されたIDは存在しません");

						getProductName = new ProductMaster();
						getProductName.setProductName("");
						ob.setProduct_Name(getProductName.getProductName());

					} else if ((ob.getCustomer_No() != null && ob.getCustomer_No().length() != 0)
							&&
							(ob.getProduct_No() != null && ob.getProduct_No().length() != 0)
							&&
							(ob.getDelivery_Date() != null && ob.getDelivery_Date().length() != 0)
							&&
							(ob.getOrder_Qty() != 0)

					) {
						ob.setProduct_Name(getProductName.getProductName());
						request.setAttribute("check", "4");

					} else {
						//結果がnullで無ければ受け取ったビーンに詰められたデータを取り出し画面に送る
						ob.setProduct_Name(getProductName.getProductName());
						request.setAttribute("check", "1");
					}
				}

			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			break;

		case "deliveryDateCheck": //納期が当日日付より前に設定されていないかチェック
			//登録日用にCalendarクラスのオブジェクトを生成する
			Calendar cl = Calendar.getInstance();
			//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			int hikaku = ob.getDelivery_Date().compareTo(sdf.format(cl.getTime()));
			if (hikaku <= -1) {
				request.setAttribute("message", "過去日は入力できません。");
				ob.setDelivery_Date(sdf.format(cl.getTime()));
				request.setAttribute("check", "2");

			} else if ((ob.getCustomer_No() != null && ob.getCustomer_No().length() != 0)
					&&
					(ob.getProduct_No() != null && ob.getProduct_No().length() != 0)
					&&
					(ob.getDelivery_Date() != null && ob.getDelivery_Date().length() != 0)
					&&
					(ob.getOrder_Qty() != 0)

			) {
				request.setAttribute("check", "4");

			} else {
				request.setAttribute("check", "3");
			}

			break;
		case "orderQtyCheck":
			if (ob.getOrder_Qty() < 1) {
				request.setAttribute("message", "最小注文数は1です");

			} else if ((ob.getCustomer_No() != null && ob.getCustomer_No().length() != 0)
					&&
					(ob.getProduct_No() != null && ob.getProduct_No().length() != 0)
					&&
					(ob.getDelivery_Date() != null && ob.getDelivery_Date().length() != 0)
					&&
					(ob.getOrder_Qty() != 0)) {
				request.setAttribute("check", "4");

			} else {
				request.setAttribute("check", "3");

			}

			break;

		case "insert":
			String insertError = null;

			if (insertError == null) {
				PurchaseOrderDAO dao = new PurchaseOrderDAO();
				try {
					int error = dao.insertPurchaseOrder2(ob);
					if (error == 1) {
						request.setAttribute("message", "登録が成功しました");

						request.setAttribute("check", "5");

						return "/WEB-INF/main/order.jsp";

					} else {
						request.setAttribute("message", "登録に失敗しました");

					}

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			break;
		case "AllOkEnd":
			ob = null;
			request.setAttribute("check", null);

			break;

		default:
			ob = null;
			break;
		}
		setGamen(request, ob);
		return "/WEB-INF/main/order.jsp";

	}

	private void setGamen(HttpServletRequest request, Order ob) {
		request.setAttribute("order", ob);

	}

	private void getScreenInfomation(HttpServletRequest request, Order ob) {
		ob.setCustomer_No(request.getParameter("customer_No"));
		ob.setCustomer_Name(request.getParameter("customer_Name"));
		ob.setProduct_No(request.getParameter("product_No"));
		ob.setProduct_Name(request.getParameter("product_Name"));
		ob.setDelivery_Date(request.getParameter("delivery_Date"));
		if (request.getParameter("order_Qty") != null && !request.getParameter("order_Qty").isEmpty()) {
			ob.setOrder_Qty(Integer.parseInt(request.getParameter("order_Qty")));
		}
	}

	//	private void get(HttpServletRequest request, Order ob) {
	//		//登録日用にCalendarクラスのオブジェクトを生成する
	//		Calendar cl = Calendar.getInstance();
	//		//登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
	//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	//
	//		//koga追記　ログイン中のユーザー情報の取得
	//		HttpSession session = request.getSession();
	//		UserMaster um = (UserMaster) session.getAttribute("user");
	//
	//		ob.setRegist_user(um.getUserId());
	//
	//		//カレンダー
	//
	//	}

}
