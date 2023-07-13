package bean;

public class GoodsIssue implements java.io.Serializable {
	private String janCode;
	private String syukoYMD;
	private int syukoPCS;
	private String etc;

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

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}
}
