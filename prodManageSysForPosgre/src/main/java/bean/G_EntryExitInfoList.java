package bean;

public class G_EntryExitInfoList {
	private String productNo; // 品番
	private String productName; // 品名
	// 検索条件格納用フィールド
	private String startDate; // 開始日(受注日絞り込み用)
	private String endDate; // 終了日(受注日絞り込み用)
	private String stockIn; // リスト対象(入庫をリストに加えるか否か←値が｢stockIn｣の場合､入庫レコードを含める)
	private String stockOut; // リスト対象(出庫をリストに加えるか否か←値が｢stockOut｣の場合､出庫レコードを含める)
	private String ascendingDescending; // 並び替えの昇順/降順の指定(値が｢ascending｣なら昇順､｢descending｣なら降順)
	private String sort; // 並び替え項目(値が｢supplierName｣なら仕入先名で､｢orderDate｣なら発注日で､｢orderQty｣なら発注数量で､｢deliveryDate｣なら納期で､｢dueDate｣なら入荷日で並び替えを行う)
	// 以下､テーブル用フィールド
	private String enExDate; // 入出庫日(=登録/変更日)
	private String stockInOut; // 入出庫種(=レコードの｢入庫｣｢出庫｣の文字列を格納)
	private int nyukoSyukoQty; // 入出庫数
	private String reason; // 理由

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

	public String getStockIn() {
		return stockIn;
	}

	public void setStockIn(String stockIn) {
		this.stockIn = stockIn;
	}

	public String getStockOut() {
		return stockOut;
	}

	public void setStockOut(String stockOut) {
		this.stockOut = stockOut;
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

	public String getEnExDate() {
		return enExDate;
	}

	public void setEnExDate(String enExDate) {
		this.enExDate = enExDate;
	}

	public String getStockInOut() {
		return stockInOut;
	}

	public void setStockInOut(String stockInOut) {
		this.stockInOut = stockInOut;
	}

	public int getNyukoSyukoQty() {
		return nyukoSyukoQty;
	}

	public void setNyukoSyukoQty(int nyukoSyukoQty) {
		this.nyukoSyukoQty = nyukoSyukoQty;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
