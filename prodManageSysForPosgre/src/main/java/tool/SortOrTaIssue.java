package tool;

import java.util.Comparator;

import bean.OrderTable;

public class SortOrTaIssue implements Comparator<OrderTable> {

	@Override
	public int compare(OrderTable en1, OrderTable en2) {
		return en1.getSupplierNo().compareTo(en2.getSupplierNo());

	}
}
