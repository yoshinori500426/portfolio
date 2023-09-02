package bean;

public class G_Order implements java.io.Serializable {
	private String orderNo;
	private String productNo; // 入力必須
	private String productName;
	private String supplierNo;
	private String supplierName;
	private String lot; // 購買ロット
	private String orderLot; // 発注ロット 入力必須
	private String orderQty;
	private String deliveryDate; // 入力必須
	private String arrivalQty; // 入荷数量
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getOrderLot() {
		return orderLot;
	}

	public void setOrderLot(String orderLot) {
		this.orderLot = orderLot;
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

	public String getArrivalQty() {
		return arrivalQty;
	}

	public void setArrivalQty(String arrivalQty) {
		this.arrivalQty = arrivalQty;
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
