package tool;

import java.util.Comparator;

import bean.PurchaseOrder;

public class purchaseOrderListToolClient implements Comparator<PurchaseOrder> {

	@Override
	public int compare(PurchaseOrder en1, PurchaseOrder en2) {
		return en1.getCustomerName().compareTo(en2.getCustomerName());
	}
}
