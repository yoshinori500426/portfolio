package bean;

public class OrderTable implements java.io.Serializable {
	private String orderNo;
	private String supplierNo;
	private String productNo;
	private int orderQty;
	private String deliveryDate;
	private String biko;
	private String dueDate;
	private int dueQty;
	private String finFlg;
	private String registUser;
	private String orderDate;
	private String orderUser;
	private String supplierName;
	private String productName;
	private String startDateBetween;
	private String endDateAnd;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getBiko() {
		return biko;
	}

	public void setBiko(String biko) {
		this.biko = biko;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public int getDueQty() {
		return dueQty;
	}

	public void setDueQty(int dueQty) {
		this.dueQty = dueQty;
	}

	public String getFinFlg() {
		return finFlg;
	}

	public void setFinFlg(String finFlg) {
		this.finFlg = finFlg;
	}

	public String getRegistUser() {
		return registUser;
	}

	public void setRegistUser(String registUser) {
		this.registUser = registUser;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStartDateBetween() {
		return startDateBetween;
	}

	public void setStartDateBetween(String startDateBetween) {
		this.startDateBetween = startDateBetween;
	}

	public String getEndDateAnd() {
		return endDateAnd;
	}

	public void setEndDateAnd(String endDateAnd) {
		this.endDateAnd = endDateAnd;
	}

}
