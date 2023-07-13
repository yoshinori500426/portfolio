package bean;

public class Order {

	//order.jsp専用のBean
private String po_No; //受注ナンバー
private	String customer_No; //顧客ナンバー
private String customer_Name; //顧客名
private	String product_No; //商品ナンバー
private	String product_Name; //商品名
private	String delivery_Date; //納期
private	String ship_Date; //出荷日
private	int order_Qty; //注文数量
private	String order_Date; //注文日
private	String regist_user; //登録者
public String getPo_No() {
	return po_No;
}
public void setPo_No(String po_No) {
	this.po_No = po_No;
}
public String getCustomer_No() {
	return customer_No;
}
public void setCustomer_No(String customer_No) {
	this.customer_No = customer_No;
}
public String getCustomer_Name() {
	return customer_Name;
}
public void setCustomer_Name(String customer_Name) {
	this.customer_Name = customer_Name;
}
public String getProduct_No() {
	return product_No;
}
public void setProduct_No(String product_No) {
	this.product_No = product_No;
}
public String getProduct_Name() {
	return product_Name;
}
public void setProduct_Name(String product_Name) {
	this.product_Name = product_Name;
}
public String getDelivery_Date() {
	return delivery_Date;
}
public void setDelivery_Date(String delivery_Date) {
	this.delivery_Date = delivery_Date;
}
public String getShip_Date() {
	return ship_Date;
}
public void setShip_Date(String ship_Date) {
	this.ship_Date = ship_Date;
}
public int getOrder_Qty() {
	return order_Qty;
}
public void setOrder_Qty(int order_Qty) {
	this.order_Qty = order_Qty;
}
public String getOrder_Date() {
	return order_Date;
}
public void setOrder_Date(String order_Date) {
	this.order_Date = order_Date;
}
public String getRegist_user() {
	return regist_user;
}
public void setRegist_user(String regist_user) {
	this.regist_user = regist_user;
}








}










