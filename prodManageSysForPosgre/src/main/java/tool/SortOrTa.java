package tool;

import java.util.Comparator;

import bean.OrderTable;


public class SortOrTa implements Comparator<OrderTable> {

	@Override
	public int compare(OrderTable en1,OrderTable en2) {
		return (en1.getOrderQty() < en2.getOrderQty() ? -1 : 1);
	}
}
