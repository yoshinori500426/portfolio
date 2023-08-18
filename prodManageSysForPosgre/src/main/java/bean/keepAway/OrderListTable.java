package bean;

import java.util.List;

public class OrderListTable {
	String productNo;
	String productName;
	List<OrderTable> oOrder;


	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<OrderTable> getoOrder() {
		return oOrder;
	}
	public void setoOrder(List<OrderTable> oOrder) {
		this.oOrder = oOrder;
	}

}
