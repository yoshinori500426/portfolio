package tool;

import java.util.Comparator;

import bean.PurchaseOrder;

public class purchaseOrderListToolMash implements Comparator<PurchaseOrder> {

	@Override
	public int compare(PurchaseOrder en1, PurchaseOrder en2) {
		return (en1.getOrderQty() < en2.getOrderQty() ? -1 : 1);
	}
}
