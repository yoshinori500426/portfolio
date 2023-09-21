package bean;

public class G_PurchaseOrderList {
	private String productNo;
	private String productName;
	// 検索条件格納用フィールド
	private String startDate; // 開始日(受注日絞り込み用)
	private String endDate; // 終了日(受注日絞り込み用)
	private String shipped; // リスト対象(出荷済みをリストに加えるか否か←値が｢inStock｣の場合､入荷済みを含める)
	private String notShipped; // リスト対象(未出荷をリストに加えるか否か←値が｢notInStock｣の場合､未入荷を含める)
	private String ascendingDescending; // 並び替えの昇順/降順の指定(値が｢ascending｣なら昇順､｢descending｣なら降順)
	private String sort; // 並び替え項目(値が｢supplierName｣なら仕入先名で､｢orderDate｣なら発注日で､｢orderQty｣なら発注数量で､｢deliveryDate｣なら納期で､｢dueDate｣なら入荷日で並び替えを行う)
	// 以下､テーブル用フィールド
	private String orderDate; // 受注日
	private String deliveryDate; // 納期
	private String shipDate; // 出荷日
	private int orderQty; // 受注数量
	private String customerNo; // 顧客コード
	private String customerName; // 顧客名

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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getShipped() {
		return shipped;
	}

	public void setShipped(String shipped) {
		this.shipped = shipped;
	}

	public String getNotShipped() {
		return notShipped;
	}

	public void setNotShipped(String notShipped) {
		this.notShipped = notShipped;
	}

	public String getAscendingDescending() {
		return ascendingDescending;
	}

	public void setAscendingDescending(String ascendingDescending) {
		this.ascendingDescending = ascendingDescending;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
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

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
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
}
