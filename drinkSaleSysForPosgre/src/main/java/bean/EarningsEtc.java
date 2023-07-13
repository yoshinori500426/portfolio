package bean;

public class EarningsEtc implements java.io.Serializable {
	private int sumSyukoPcs;
	private int sumTotalPirce;
	private String year;
	private String month;

	public int getSumSyukoPcs() {
		return sumSyukoPcs;
	}

	public void setSumSyukoPcs(int sumSyukoPcs) {
		this.sumSyukoPcs = sumSyukoPcs;
	}

	public int getSumTotalPirce() {
		return sumTotalPirce;
	}

	public void setSumTotalPirce(int sumTotalPirce) {
		this.sumTotalPirce = sumTotalPirce;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
