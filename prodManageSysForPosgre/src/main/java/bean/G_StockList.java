package bean;

public class G_StockList {
	private String productNo;
	private String productName;
	private int stockQty; // テーブル｢PURODUCT_STOCK｣の在庫数(隠し要素)
	// 以下､テーブル用フィールド
	private String deliveryDate; // 年月日
	private String orderNo; // 受発注番号
	private String customerNo; // 受注先番号
	private String customerName; // 受注先名
	private int purchaseOrderQty; // 受注数
	private String supplierNo; // 発注先番号
	private String supplierName; // 発注先名
	private int orderQty; // 発注数
	private int stockQtyEstimate; // 受注数･発注数を元にした予測在庫数

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

	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getPurchaseOrderQty() {
		return purchaseOrderQty;
	}

	public void setPurchaseOrderQty(int purchaseOrderQty) {
		this.purchaseOrderQty = purchaseOrderQty;
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

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public int getStockQtyEstimate() {
		return stockQtyEstimate;
	}

	public void setStockQtyEstimate(int stockQtyEstimate) {
		this.stockQtyEstimate = stockQtyEstimate;
	}
}
