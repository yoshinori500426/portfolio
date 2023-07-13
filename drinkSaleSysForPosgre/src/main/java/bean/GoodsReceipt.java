package bean;

public class GoodsReceipt implements java.io.Serializable {
	private String janCode;
	private String NyukoYMD;
	private int NyukoPCS;
	private int UnitPrice;
	private int SellingPrice;
	private String etc;

	public String getJanCode() {
		return janCode;
	}

	public void setJanCode(String janCode) {
		this.janCode = janCode;
	}

	public String getNyukoYMD() {
		return NyukoYMD;
	}

	public void setNyukoYMD(String nyukoYMD) {
		NyukoYMD = nyukoYMD;
	}

	public int getNyukoPCS() {
		return NyukoPCS;
	}

	public void setNyukoPCS(int nyukoPCS) {
		NyukoPCS = nyukoPCS;
	}

	public int getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		UnitPrice = unitPrice;
	}

	public int getSellingPrice() {
		return SellingPrice;
	}

	public void setSellingPrice(int sellingPrice) {
		SellingPrice = sellingPrice;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}
}
