package bean;

public class Earnings implements java.io.Serializable {
	private String janCode;
	private String syukoYMD;
	private int syukoPCS;
	private int unitPrice;
	private int totalPrice;

	public String getJanCode() {
		return janCode;
	}

	public void setJanCode(String janCode) {
		this.janCode = janCode;
	}

	public String getSyukoYMD() {
		return syukoYMD;
	}

	public void setSyukoYMD(String syukoYMD) {
		this.syukoYMD = syukoYMD;
	}

	public int getSyukoPCS() {
		return syukoPCS;
	}

	public void setSyukoPCS(int syukoPCS) {
		this.syukoPCS = syukoPCS;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

}
