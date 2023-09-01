package bean;

public class G_EntryExitInfo {
	private String enExId; // 入出庫番号
	private String productNo; // 品番
	private String productName; // 品名
	private String nyukoQty; // 入庫数
	private String syukoQty; // 出庫数
	private String reason; // 理由
	// 以降､画面に存在しない項目(不要の可能性あり)
	private String enExDate; // 入出庫日(=登録/変更日)
	private String registDate; // 登録日
	private String registUser; // 登録者
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

	public String getNyukoQty() {
		return nyukoQty;
	}

	public void setNyukoQty(String nyukoQty) {
		this.nyukoQty = nyukoQty;
	}

	public String getSyukoQty() {
		return syukoQty;
	}

	public void setSyukoQty(String syukoQty) {
		this.syukoQty = syukoQty;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEnExDate() {
		return enExDate;
	}

	public void setEnExDate(String enExDate) {
		this.enExDate = enExDate;
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