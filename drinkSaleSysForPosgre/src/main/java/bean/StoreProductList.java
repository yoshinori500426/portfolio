package bean;

public class StoreProductList implements java.io.Serializable {
	private String janCode;
	private int stockPCS;
	private String name;
	private String maker;
	private int price;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
