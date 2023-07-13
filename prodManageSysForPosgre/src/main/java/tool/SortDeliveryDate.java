package tool;

import java.util.Comparator;

import bean.Stock;

public class SortDeliveryDate implements Comparator<Stock> {
	public int compare(Stock st1, Stock st2) {
		int result = (st1.getDeliveryDate().compareTo(st2.getDeliveryDate()));
		return result;
	}
}
