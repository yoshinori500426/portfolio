package bean;

public class EntryExitInfo {
	private String enExId; // 入出庫番号
	private String enExDate; // 入出庫日(=登録/変更日)
	private String productNo; // 品番
	private int nyukoQty; // 入庫数
	private int syukoQty; // 出庫数
	private String reason; // 理由
	private String registDate; // 入出庫番号登録日(更新/削除の際のテーブル｢PuroductStock｣のYYYY/MMに該当)
	private String registUser; // 入出庫番号登録者
	// 以降､テーブルに存在しない項目
	private String productName; // 品名
	private String judge;
	private int count;
	private int stockQty;
	private int tNyuko;
	private int tSyuko;

	public String getEnExId() {
		return enExId;
	}

	public void setEnExId(String enExId) {
		this.enExId = enExId;
	}

	public String getEnExDate() {
		return enExDate;
	}

	public void setEnExDate(String enExDate) {
		this.enExDate = enExDate;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public int getNyukoQty() {
		return nyukoQty;
	}

	public void setNyukoQty(int nyukoQty) {
		this.nyukoQty = nyukoQty;
	}

	public int getSyukoQty() {
		return syukoQty;
	}

	public void setSyukoQty(int syukoQty) {
		this.syukoQty = syukoQty;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getRegistUser() {
		return registUser;
	}

	public void setRegistUser(String registUser) {
		this.registUser = registUser;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}

	public int gettNyuko() {
		return tNyuko;
	}

	public void settNyuko(int tNyuko) {
		this.tNyuko = tNyuko;
	}

	public int gettSyuko() {
		return tSyuko;
	}

	public void settSyuko(int tSyuko) {
		this.tSyuko = tSyuko;
	}
}