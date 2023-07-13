package bean;

import java.util.List;

public class PurchaseListOrder {
	String productNo;
	String productName;
	List<PurchaseOrder> pOrder;


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
	public List<PurchaseOrder> getpOrder() {
		return pOrder;
	}
	public void setpOrder(List<PurchaseOrder> pOrder) {
		this.pOrder = pOrder;
	}



}
