package bean;

public class G_Order implements java.io.Serializable {
	private String orderNo;
	private String productNo; // 入力必須
	private String supplierNo; // productNoを元に検索した結果が格納される(←insert時に使用する為､項目を設けている)
	private String orderQty; // 入力必須
	private String deliveryDate; // 入力必須
	private String biko;
	private String finFlg;
	private String orderDate; // deliveryDateをorderDate以降にする目的でこの項目を設ける

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
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
}
