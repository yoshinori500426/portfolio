package bean;

public class G_OrderList {
	private String productNo;
	private String productName;
	// 検索条件格納用フィールド
	private String startDate; // 開始日(発注日絞り込み用)
	private String endDate; // 終了日(発注日絞り込み用)
	private String inStock; // リスト対象(入荷済みをリストに加えるか否か←値が｢inStock｣の場合､入荷済みを含める)
	private String notInStock; // リスト対象(未入荷をリストに加えるか否か←値が｢notInStock｣の場合､未入荷を含める)
	private String ascendingDescending; // 並び替えの昇順/降順の指定(値が｢ascending｣なら昇順､｢descending｣なら降順)
	private String sort; // 並び替え項目(値が｢supplierName｣なら仕入先名で､｢orderDate｣なら発注日で､｢orderQty｣なら発注数量で､｢deliveryDate｣なら納期で､｢dueDate｣なら入荷日で並び替えを行う)
	// 以下､テーブル用フィールド
	private String orderDate; // 発注日
	private String deliveryDate; // 納期
	private String dueDate; // 入荷日
	private int orderQty; // 発注数量
	private String supplierNo; // 仕入先コード
	private String supplierName; // 仕入先名

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

	public String getInStock() {
		return inStock;
	}

	public void setInStock(String inStock) {
		this.inStock = inStock;
	}

	public String getNotInStock() {
		return notInStock;
	}

	public void setNotInStock(String notInStock) {
		this.notInStock = notInStock;
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

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
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
}
