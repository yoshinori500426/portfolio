package bean;

public class G_AmountCalcOrder implements java.io.Serializable {
	private String productNo;
	private String orderLotNum;
	private String productName;
	private String supplierNo;
	private String supplierName;
	private String basestock;
	private String latestCumulativeQty;
	private String requiredQty;
	private String leadtimeFromSupplier;
	private String expectedDeliveryDate;
	private String lotPcs;
	private String unitPrice;
	private String orderQty;
	private String orderPrice;

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getOrderLotNum() {
		return orderLotNum;
	}

	public void setOrderLotNum(String orderLotNum) {
		this.orderLotNum = orderLotNum;
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

	public String getBasestock() {
		return basestock;
	}

	public void setBasestock(String basestock) {
		this.basestock = basestock;
	}

	public String getLatestCumulativeQty() {
		return latestCumulativeQty;
	}

	public void setLatestCumulativeQty(String latestCumulativeQty) {
		this.latestCumulativeQty = latestCumulativeQty;
	}

	public String getRequiredQty() {
		return requiredQty;
	}

	public void setRequiredQty(String requiredQty) {
		this.requiredQty = requiredQty;
	}

	public String getLeadtimeFromSupplier() {
		return leadtimeFromSupplier;
	}

	public void setLeadtimeFromSupplier(String leadtimeFromSupplier) {
		this.leadtimeFromSupplier = leadtimeFromSupplier;
	}

	public String getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(String expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public String getLotPcs() {
		return lotPcs;
	}

	public void setLotPcs(String lotPcs) {
		this.lotPcs = lotPcs;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
}
