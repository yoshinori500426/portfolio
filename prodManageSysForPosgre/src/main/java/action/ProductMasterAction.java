package action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.G_ProductMaster;
import bean.ProductMaster;
import bean.SupplierMaster;
import dao.ProductMasterDAO;
import dao.SupplierMasterDAO;
import tool.Action;

public class ProductMasterAction extends Action {
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
		ProductMasterDAO pmDAO = new ProductMasterDAO();
		SupplierMasterDAO smDAO = new SupplierMasterDAO();
		// 使用インスタンスの格納変数を参照先「null」で宣言
		SupplierMaster supplier = null;
		ProductMaster productForChange = null;
		List<ProductMaster> ProductMasterList = null;
		SupplierMaster SupplierMaster = null;
		List<SupplierMaster> SupplierMasterList = null;
		// このインスタンスで行う処理を取得(リクエストパラメータ取得)
		String toAction = request.getParameter("toAction");
		String btnSelect = request.getParameter("btnSelect");
		session.setAttribute("toAction", toAction);
		session.setAttribute("btnSelect", btnSelect);
		// 画面情報取得
		G_ProductMaster G_ProductMaster = new G_ProductMaster();
		G_ProductMaster.setProductNo(request.getParameter("productNo"));
		G_ProductMaster.setProductName(request.getParameter("productName"));
		G_ProductMaster.setSupplierNo(request.getParameter("supplierNo"));
		G_ProductMaster.setUnitPrice(request.getParameter("unitPrice"));
		G_ProductMaster.setSellingPrice(request.getParameter("sellingPrice"));
		G_ProductMaster.setLeadTime(request.getParameter("leadTime"));
		G_ProductMaster.setLot(request.getParameter("lot"));
		G_ProductMaster.setLocation(request.getParameter("location"));
		G_ProductMaster.setBaseStock(request.getParameter("baseStock"));
		G_ProductMaster.setEtc(request.getParameter("etc"));
		session.setAttribute("G_ProductMaster", G_ProductMaster);
		// 仕入先名は､画面に直接入力するのではなく､仕入先コードの検索結果を表示させるため､｢G_ProductMaster｣には含めない
		supplier = new SupplierMaster();
		supplier.setSupplierName(request.getParameter("supplierName"));
		session.setAttribute("SupplierMaster", supplier);
		// 処理種により､処理を分岐
		switch (toAction) {
		case "btnSelect":
			// 「session.setAttribute("btnSelect", btnSelect);」を行う為の動作
			// 各種セッション属性のnullクリア
			new MainAction().crearAttributeForScreenChange(session);
			session.setAttribute("btnSelect", btnSelect);
			break;
		case "searchProductNo":
			// テーブル検索
			productForChange = pmDAO.searchByProNo(G_ProductMaster);
			if (productForChange == null) {
				session.setAttribute("message", "入力値に該当する品番は存在しません。\\n入力内容を確認ください。");
				break;
			} else if (productForChange != null) {
				G_ProductMaster.setProductNo(productForChange.getProductNo());
				G_ProductMaster.setProductName(productForChange.getProductName());
				G_ProductMaster.setSupplierNo(productForChange.getSupplierNo());
				G_ProductMaster.setUnitPrice(String.valueOf(productForChange.getUnitPrice()));
				G_ProductMaster.setSellingPrice(String.valueOf(productForChange.getSellingPrice()));
				G_ProductMaster.setLeadTime(String.valueOf(productForChange.getLeadTime()));
				G_ProductMaster.setLot(String.valueOf(productForChange.getLot()));
				G_ProductMaster.setLocation(productForChange.getLocation());
				G_ProductMaster.setBaseStock(String.valueOf(productForChange.getBaseStock()));
				G_ProductMaster.setEtc(productForChange.getEtc());
				session.setAttribute("G_ProductMaster", G_ProductMaster);
			}
		case "searchSupplierNo":
			// SupplierNoに値が入っていない場合､処理しない
			if (G_ProductMaster.getSupplierNo().equals("") || G_ProductMaster.getSupplierNo() == null) {
				session.setAttribute("SupplierMaster", null);
				break;
			}
			// テーブル検索
			SupplierMaster = smDAO.searchBySupNo(G_ProductMaster.getSupplierNo());
			if (SupplierMaster == null) {
				session.setAttribute("message", "入力値に該当する仕入先コードは存在しません。\\n入力内容を確認ください。");
				session.setAttribute("SupplierMaster", null);
				break;
			} else if (SupplierMaster != null) {
				session.setAttribute("SupplierMaster", SupplierMaster);
			}
			break;
		case "doBTNExecute":
			int line = 0;
			// トランザクション処理準備
			Connection con = pmDAO.getConnection();
			// 排他制御
			synchronized (this) {
				// トランザクション処理開始
				con.setAutoCommit(false);
				// DB処理
				if (btnSelect.equals("insert")) {
					line = pmDAO.insert(G_ProductMaster, request);
				} else if (btnSelect.equals("update")) {
					line = pmDAO.updateByPM(G_ProductMaster, request);
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
		ProductMasterList = pmDAO.searchAll();
		session.setAttribute("ProductMasterList", ProductMasterList);
		SupplierMasterList = smDAO.searchAll();
		session.setAttribute("SupplierMasterList", SupplierMasterList);
		// 遷移画面情報保存
		session.setAttribute("nextJsp", "/WEB-INF/main/productMaster.jsp");
		return "/WEB-INF/main/productMaster.jsp";
	}
}
