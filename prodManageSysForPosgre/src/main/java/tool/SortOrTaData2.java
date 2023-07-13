package tool;

import java.util.Comparator;

import bean.OrderTable;

public class SortOrTaData2 implements Comparator<OrderTable> {

	@Override
	public int compare(OrderTable en1, OrderTable en2) {
		return en1.getDeliveryDate().compareTo(en2.getDeliveryDate());

	}

}
