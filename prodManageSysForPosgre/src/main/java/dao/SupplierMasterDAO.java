package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.G_Arrival;
import bean.G_SupplierMaster;
import bean.SupplierMaster;
import bean.UserMaster;

public class SupplierMasterDAO extends DAO {
	/**
	 * SupplierMasterテーブル参照メソッド
	 *  →SUPPLIER_NOでサーチ
	 * 
	 * @param 仕入先コード
	 * @return SupplierMasterビーン 「null：失敗」「インスタンス有：成功」
	 */
	public SupplierMaster searchBySupNo(String supplierNo) {
		SupplierMaster SupplierMaster = null;
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM SUPPLIER_MASTER WHERE SUPPLIER_NO = ?");
			st.setString(1, supplierNo);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				SupplierMaster = new SupplierMaster();
				SupplierMaster.setSupplierNo(rs.getString("SUPPLIER_NO"));
				SupplierMaster.setSupplierName(rs.getString("SUPPLIER_NAME"));
				SupplierMaster.setBranchName(rs.getString("BRANCH_NAME"));
				SupplierMaster.setZipNo(rs.getString("ZIP_NO"));
				SupplierMaster.setAddress1(rs.getString("ADDRESS1"));
				SupplierMaster.setAddress2(rs.getString("ADDRESS2"));
				SupplierMaster.setAddress3(rs.getString("ADDRESS3"));
				SupplierMaster.setTel(rs.getString("TEL"));
				SupplierMaster.setFax(rs.getString("FAX"));
				SupplierMaster.setManager(rs.getString("MANAGER"));
				SupplierMaster.setEtc(rs.getString("ETC"));
				SupplierMaster.setRegistDate(rs.getString("REGIST_DATE"));
				SupplierMaster.setRegistUser(rs.getString("REGIST_USER"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return SupplierMaster;
	}

	/**
	 * SupplierMasterテーブル参照メソッド
	 *  →SUPPLIER_NOでサーチ
	 * 
	 * @param G_SupplierMasterビーン
	 * @return SupplierMasterビーン 「null：失敗」「インスタンス有：成功」
	 */
	public SupplierMaster searchBySupNo(G_SupplierMaster G_SupplierMaster) {
		return searchBySupNo(G_SupplierMaster.getSupplierNo());
	}

	/**
	 * SupplierMasterテーブル参照メソッド
	 *  →SUPPLIER_NOでサーチ
	 * 
	 * @param G_Supplierビーン
	 * @return SupplierMasterビーン 「null：失敗」「インスタンス有：成功」
	 */
	public SupplierMaster searchBySupNo(G_Arrival G_Arrival) {
		return searchBySupNo(G_Arrival.getSupplierNo());
	}

	/**
	 * SupplierMasterテーブル取得メソッド(検索条件なし)
	 *
	 * @param 引数無し
	 * @return List<SupplierMaster> 「null：失敗」「null以外：成功」
	 */
	public List<SupplierMaster> searchAll() {
		// 戻り値用の変数宣言
		List<SupplierMaster> SupplierMasterList = new ArrayList<>();
		SupplierMaster SupplierMaster = null;
		try {
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM SUPPLIER_MASTER ORDER BY SUPPLIER_NO ASC");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				SupplierMaster = new SupplierMaster();
				SupplierMaster.setSupplierNo(rs.getString("SUPPLIER_NO"));
				SupplierMaster.setSupplierName(rs.getString("SUPPLIER_NAME"));
				SupplierMaster.setBranchName(rs.getString("BRANCH_NAME"));
				SupplierMaster.setZipNo(rs.getString("ZIP_NO"));
				SupplierMaster.setAddress1(rs.getString("ADDRESS1"));
				SupplierMaster.setAddress2(rs.getString("ADDRESS2"));
				SupplierMaster.setAddress3(rs.getString("ADDRESS3"));
				SupplierMaster.setTel(rs.getString("TEL"));
				SupplierMaster.setFax(rs.getString("FAX"));
				SupplierMaster.setManager(rs.getString("MANAGER"));
				SupplierMaster.setEtc(rs.getString("ETC"));
				SupplierMaster.setRegistDate(rs.getString("REGIST_DATE"));
				SupplierMaster.setRegistUser(rs.getString("REGIST_USER"));
				SupplierMasterList.add(SupplierMaster);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return SupplierMasterList;
	}

	/**
	 * SupplierMasterテーブル登録メソッド
	 * 
	 * @param SupplierMasterビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int insert(G_SupplierMaster G_SupplierMaster, HttpServletRequest request) {
		int line = 0;
		// SUPPLIER_NO取得用カウンタ
		int count = 0;
		String SUPPLIER_NO = "";
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster um = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			SupplierMaster SupplierMaster = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			// 仕入先コード作成
			do {
				// 仕入先コードのシーケンス値取得
				st = con.prepareStatement("SELECT NEXTVAL('SUPPLIER_NO')");
				rs = st.executeQuery();
				while (rs.next()) {
					SUPPLIER_NO = String.valueOf(rs.getInt("NEXTVAL"));
				}
				// 仕入先コードを左ゼロ埋めで6桁にする処理
				do {
					if (SUPPLIER_NO.length() < 6) {
						SUPPLIER_NO = "0" + SUPPLIER_NO;
					} else {
						break;
					}
				} while (true);
				// 作成した注文番号の未使用確認
				SupplierMaster = searchBySupNo(SUPPLIER_NO);
				if (SupplierMaster == null) {
					// 使用可能
					break;
				} else if (count >= 100000) {
					session.setAttribute("state", "使用可能な仕入先コードがありません。\\n処理を終了します｡");
					return line;
				}
				// SUPPLIER_NO取得用カウンタ、カウントアップ
				count++;
			} while (true);
			// 新規登録処理 13項目
			st = con.prepareStatement("INSERT INTO SUPPLIER_MASTER VALUES(? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, SUPPLIER_NO);
			st.setString(2, G_SupplierMaster.getSupplierName());
			st.setString(3, G_SupplierMaster.getBranchName());
			st.setString(4, G_SupplierMaster.getZipNo());
			st.setString(5, G_SupplierMaster.getAddress1());
			st.setString(6, G_SupplierMaster.getAddress2());
			st.setString(7, G_SupplierMaster.getAddress3());
			st.setString(8, G_SupplierMaster.getTel());
			st.setString(9, G_SupplierMaster.getFax());
			st.setString(10, G_SupplierMaster.getManager());
			st.setString(11, G_SupplierMaster.getEtc());
			st.setString(12, sdf.format(cl.getTime()));
			st.setString(13, um.getUserId());
			line = st.executeUpdate();
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * SupplierMasterテーブル更新メソッド
	 *  →SUPPLIER_NOで更新
	 * 
	 * @param SupplierMaster
	 * @param SupplierMasterビーン
	 * @return 整数 「0：失敗」「1：成功」
	 */
	public int updateBySupNo(G_SupplierMaster G_SupplierMaster, HttpServletRequest request) {
		int line = 0;
		// 登録日用にCalendarクラスのオブジェクトを生成する
		Calendar cl = Calendar.getInstance();
		// 登録日用SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		// ｢REGIST_USER｣が格納されたインスタンス取得
		HttpSession session = request.getSession();
		UserMaster um = (UserMaster) session.getAttribute("user");
		try {
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement("UPDATE SUPPLIER_MASTER SET SUPPLIER_NAME=?, BRANCH_NAME=?, ZIP_NO=?, ADDRESS1=?, ADDRESS2=?, ADDRESS3=?, "
														+ "TEL=?, FAX=?, MANAGER = ?, ETC=?, REGIST_DATE=?, REGIST_USER=? WHERE SUPPLIER_NO=? ");
			st.setString(1, G_SupplierMaster.getSupplierName());
			st.setString(2, G_SupplierMaster.getBranchName());
			st.setString(3, G_SupplierMaster.getZipNo());
			st.setString(4, G_SupplierMaster.getAddress1());
			st.setString(5, G_SupplierMaster.getAddress2());
			st.setString(6, G_SupplierMaster.getAddress3());
			st.setString(7, G_SupplierMaster.getTel());
			st.setString(8, G_SupplierMaster.getFax());
			st.setString(9, G_SupplierMaster.getManager());
			st.setString(10, G_SupplierMaster.getEtc());
			st.setString(11, sdf.format(cl.getTime()));
			st.setString(12, um.getUserId());
			st.setString(13, G_SupplierMaster.getSupplierNo());
			line = st.executeUpdate();
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}
}
