package tool;

import java.util.Comparator;

import bean.OrderTable;

public class SortOrTaDate implements Comparator<OrderTable> {

	@Override
	public int compare(OrderTable en1, OrderTable en2) {
		return en1.getOrderDate().compareTo(en2.getOrderDate());

	}

}
