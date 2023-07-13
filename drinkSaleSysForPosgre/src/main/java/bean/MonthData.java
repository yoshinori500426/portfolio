package bean;

public class MonthData implements java.io.Serializable {
	private String ym;
	private int purchaseTotal;
	private int salesTotal;
	private String etc;

	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
	}

	public int getPurchaseTotal() {
		return purchaseTotal;
	}

	public void setPurchaseTotal(int purchaseTotal) {
		this.purchaseTotal = purchaseTotal;
	}

	public int getSalesTotal() {
		return salesTotal;
	}

	public void setSalesTotal(int salesTotal) {
		this.salesTotal = salesTotal;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

}
