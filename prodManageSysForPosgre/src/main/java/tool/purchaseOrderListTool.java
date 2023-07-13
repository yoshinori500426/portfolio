package tool;

import java.util.Comparator;

import bean.PurchaseOrder;

public class purchaseOrderListTool implements Comparator<PurchaseOrder> {

	@Override
	public int compare(PurchaseOrder en1, PurchaseOrder en2) {
		return en1.getShipDate().compareTo(en2.getShipDate());
	}
}
