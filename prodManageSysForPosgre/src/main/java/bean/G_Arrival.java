package bean;

public class G_Arrival implements java.io.Serializable {
	private String orderNo; // 発注番号
	private String productNo; // 品番
	private String productName; // 品名
	private String supplierNo; // 仕入先コード
	private String supplierName; // 仕入先名
	private String orderDate; // 発注日
	private String lot; // 購買ロット
	private String orderLot; // 発注ロット 入力必須
	private String orderQty;
	private String leadTime; // 購買リードタイム
	private String deliveryDate; // 納期
	private String dueQty; // 納入数量
	private String dueDate; // 納入日
	private String biko; // 備考
	private String finFlg;

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

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
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

	public String getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDueQty() {
		return dueQty;
	}

	public void setDueQty(String dueQty) {
		this.dueQty = dueQty;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
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
}
