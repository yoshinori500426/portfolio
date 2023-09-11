package bean;

public class G_EntryExitInfo {
	private String enExId; // 入出庫番号
	private String productNo; // 品番
	private String productName; // 品名
	private String wStockQty; // 倉庫在庫数
	private String oowStockQty; // 倉庫外在庫数
	private String stockInOut; // 入庫･出庫(ラジオボタン)
	private String enExDate; // 入出庫日(=登録/変更日)
	private String enExNum; // 数量(入出庫数)
	private String reason; // 理由
	// 以下､隠し要素
	private String nyukoQty; // 入庫数
	private String syukoQty; // 出庫数
	private String befEnExNum; // 更新時の更新前の値
	private String registDate; // 入出庫番号登録日(更新/削除の際のテーブル｢PuroductStock｣のYYYY/MMに該当)

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

	public String getwStockQty() {
		return wStockQty;
	}

	public void setwStockQty(String wStockQty) {
		this.wStockQty = wStockQty;
	}

	public String getOowStockQty() {
		return oowStockQty;
	}

	public void setOowStockQty(String oowStockQty) {
		this.oowStockQty = oowStockQty;
	}

	public String getStockInOut() {
		return stockInOut;
	}

	public void setStockInOut(String stockInOut) {
		this.stockInOut = stockInOut;
	}

	public String getEnExDate() {
		return enExDate;
	}

	public void setEnExDate(String enExDate) {
		this.enExDate = enExDate;
	}

	public String getEnExNum() {
		return enExNum;
	}

	public void setEnExNum(String enExNum) {
		this.enExNum = enExNum;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getBefEnExNum() {
		return befEnExNum;
	}

	public void setBefEnExNum(String befEnExNum) {
		this.befEnExNum = befEnExNum;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
}