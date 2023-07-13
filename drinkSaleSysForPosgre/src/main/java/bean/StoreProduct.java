package bean;

public class StoreProduct implements java.io.Serializable {
	private String janCode;
	private int stockPCS;
	private int tnyukosu;
	private int tsyukosu;
	private String etc;

	public String getJanCode() {
		return janCode;
	}

	public void setJanCode(String janCode) {
		this.janCode = janCode;
	}

	public int getStockPCS() {
		return stockPCS;
	}

	public void setStockPCS(int stockPCS) {
		this.stockPCS = stockPCS;
	}

	public int getTnyukosu() {
		return tnyukosu;
	}

	public void setTnyukosu(int tnyukosu) {
		this.tnyukosu = tnyukosu;
	}

	public int getTsyukosu() {
		return tsyukosu;
	}

	public void setTsyukosu(int tsyukosu) {
		this.tsyukosu = tsyukosu;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}
}
