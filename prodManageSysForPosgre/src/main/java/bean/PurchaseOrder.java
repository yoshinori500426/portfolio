package bean;

public class PurchaseOrder {

	private String poNo;
	private String customerNo;
	private String productNo;
	private int orderQty;
	private String deliveryDate;
	private String shipDate;
	private String finFlg;
	private String orderDate;
	private String registUser;

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	public String getFinFlg() {
		return finFlg;
	}

	public void setFinFlg(String finFlg) {
		this.finFlg = finFlg;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getRegistUser() {
		return registUser;
	}

	public void setRegistUser(String registUser) {
		this.registUser = registUser;
	}
}
