package tool;

import java.util.Comparator;

import bean.PurchaseOrder;

public class purchaseOrderListToolCheck implements Comparator<PurchaseOrder> {

	@Override
	public int compare(PurchaseOrder en1, PurchaseOrder en2) {
		return en1.getOrderDate().compareTo(en2.getOrderDate());

	}

}
