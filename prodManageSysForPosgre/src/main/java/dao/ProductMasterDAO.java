package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bean.ProductMaster;

public class ProductMasterDAO extends DAO {

	public int insertByProNo(ProductMaster productMaster) throws Exception {
		int result = 0;
		try {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat Da = new SimpleDateFormat("yyyy/MM/dd");
			Connection con = getConnection();
			PreparedStatement st;
			st = con.prepareStatement("INSERT INTO PRODUCT_MASTER VALUES(LPAD(PRODUCT_NO.NEXTVAL,10,'0'),?,?,?,?,?,?,?,?,?,?,?)");
			st.setString(1, productMaster.getProductName());
			st.setString(2, productMaster.getSupplierNo());
			st.setInt(3, productMaster.getUnitPrice());
			st.setInt(4, productMaster.getSellingPrice());
			st.setInt(5, productMaster.getLeadtime());
			st.setInt(6, productMaster.getLot());
			st.setString(7, productMaster.getLocation());
			st.setInt(8, productMaster.getBaseStock());
			st.setString(9, productMaster.getEtc());
			st.setString(10, Da.format(c.getTime()));
			st.setString(11, productMaster.getRegistUser());

			result = st.executeUpdate();

			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ProductMaster searchByProNo(ProductMaster pm) throws Exception {
		ProductMaster prm = null;

		Connection con = getConnection();

		PreparedStatement st;
		st = con.prepareStatement(
				"SELECT * FROM PRODUCT_MASTER WHERE PRODUCT_NO  = ?");
		st.setString(1, pm.getProductNo());
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			prm = new ProductMaster();
			prm.setProductNo(rs.getString("PRODUCT_NO"));
			prm.setProductName(rs.getString("PRODUCT_NAME"));
			prm.setSupplierNo(rs.getString("SUPPLIER_NO"));
			prm.setUnitPrice(rs.getInt("UNIT_PRICE"));
			prm.setSellingPrice(rs.getInt("SELLING_PRICE"));
			prm.setLeadtime(rs.getInt("LEADTIME"));
			prm.setLot(rs.getInt("LOT"));
			prm.setLocation(rs.getString("LOCATION"));
			prm.setBaseStock(Integer.parseInt(rs.getString("BASESTOCK")));
			prm.setEtc(rs.getString("ETC"));
			prm.setRegistDate(rs.getString("REGIST_DATE"));
			prm.setRegistUser(rs.getString("REGIST_USER"));
		}
		st.close();
		con.close();
		return prm;
	}

	public int updatesearchByProNo(ProductMaster recv_data) {
		int result = 0;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat Da = new SimpleDateFormat("yyyy/MM/dd");

		try {
			Connection con = getConnection();

			PreparedStatement st;
			st = con.prepareStatement("UPDATE PRODUCT_MASTER SET PRODUCT_NAME = ?,SUPPLIER_NO = ?,UNIT_PRICE = ?,SELLING_PRICE = ?,LEADTIME = ?,LOT = ?,LOCATION = ?,BASESTOCK = ?,ETC = ?,REGIST_DATE = ?,REGIST_USER = ? WHERE PRODUCT_NO = ?");

			st.setString(1, recv_data.getProductName());
			st.setString(2, recv_data.getSupplierNo());
			st.setInt(3, recv_data.getUnitPrice());
			st.setInt(4, recv_data.getSellingPrice());
			st.setInt(5, recv_data.getLeadtime());
			st.setInt(6, recv_data.getLot());
			st.setString(7, recv_data.getLocation());
			st.setInt(8, recv_data.getBaseStock());
			st.setString(9, recv_data.getEtc());
			st.setString(10, Da.format(c.getTime()));
			st.setString(11, recv_data.getRegistUser());
			st.setString(12, recv_data.getProductNo());

			result = st.executeUpdate();

			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return result;
	}

	public ProductMaster searchByProNoS(String productNo) throws Exception {
		ProductMaster prm = null;

		Connection con = getConnection();

		PreparedStatement st;
		st = con.prepareStatement(
				"SELECT * FROM PRODUCT_MASTER WHERE PRODUCT_NO=? ");
		st.setString(1, productNo);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			prm = new ProductMaster();
			prm.setProductNo(rs.getString("product_No"));
			prm.setProductName(rs.getString("product_Name"));
			prm.setSupplierNo(rs.getString("supplier_No"));
			prm.setUnitPrice(rs.getInt("unit_Price"));
			prm.setSellingPrice(rs.getInt("selling_Price"));
			prm.setLeadtime(rs.getInt("leadtime"));
			prm.setLot(rs.getInt("lot"));
			prm.setLocation(rs.getString("location"));
			prm.setEtc(rs.getString("etc"));
			prm.setRegistDate(rs.getString("regist_Date"));
			prm.setRegistUser(rs.getString("regist_User"));
		}
		st.close();
		con.close();
		return prm;
	}

	public ProductMaster searchBySireNo(String No) throws Exception {
		ProductMaster prm = null;

		Connection con = getConnection();

		PreparedStatement st;
		st = con.prepareStatement(
				"SELECT * FROM PRODUCT_MASTER WHERE PRODUCT_NO  = ?");
		st.setString(1, No);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			prm = new ProductMaster();
			prm.setProductNo(rs.getString("PRODUCT_NO"));
			prm.setProductName(rs.getString("PRODUCT_NAME"));
			prm.setSupplierNo(rs.getString("SUPPLIER_NO"));
			prm.setUnitPrice(rs.getInt("UNIT_PRICE"));
			prm.setSellingPrice(rs.getInt("SELLING_PRICE"));
			prm.setLeadtime(rs.getInt("LEADTIME"));
			prm.setLot(rs.getInt("LOT"));
			prm.setLocation(rs.getString("LOCATION"));
			prm.setBaseStock(rs.getInt("BASESTOCK"));
			prm.setEtc(rs.getString("ETC"));
		}
		st.close();
		con.close();
		return prm;
	}

}
