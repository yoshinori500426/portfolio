package tool;

import java.util.Comparator;

import bean.PurchaseOrder;

public class purchaseOrderListToolDate implements Comparator<PurchaseOrder> {

	@Override
	public int compare(PurchaseOrder en1, PurchaseOrder en2) {
		return en1.getDeliveryDate().compareTo(en2.getDeliveryDate());
	}
}
