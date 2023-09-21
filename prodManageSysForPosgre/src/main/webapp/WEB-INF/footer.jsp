<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</div>
<script type="text/javascript" src="../js/jquery-3.6.4.min.js"></script>
<script>
//========================================================================================================================================================================================================================
//共通動作
var form = document.forms[2];
if(document.getElementsByName('btnSelect').length==1){
	var btnSelect = form.elements['btnSelect'].value;
}
window.addEventListener('load', function(){
	/* 以下不要
	　　document.getElementById('body').setAttribute('style','visibility: visible;');
	*/
	var scrollY=window.sessionStorage.getItem(['scrollY']);
	if(scrollY!=null){
		scrollTo(0, scrollY);
	}
})
window.setTimeout(function () {
	if('${state}'!=''){
		if(!alert('${state}')){
			${state=""};
			doExecute2('aftDoOrder');
		}
	 }
	if('${message}'!=''){
		if(!alert('${message}')){
			${message=""};
			doExecute2('dummy');
	 	}
	}
}, 150);
// Actionクラストリガー用メソッド
//　→formタグの属性actionでActionクラスを指定し､
//　　リクエストパラメータに動作内容を指定する事で､複雑な動作を行わせる
function commonDoExecute(num,atype) {
	var positionY = window.pageYOffset;
	window.sessionStorage.setItem(['scrollY'],[positionY]);
	var form = document.forms[num];
	form.toAction.value = atype;
	document.getElementById('body').setAttribute('style','visibility: hidden;');
 	for(let i = 0; i < form.elements.length; i++){
		form.elements[i].removeAttribute('disabled');
	}
	form.submit();
}
function doExecute0(atype) {
	commonDoExecute(0,atype);
}
function doExecute1(atype) {
	commonDoExecute(1,atype);
}
function doExecute2(atype) {
	commonDoExecute(2,atype);
}
function doExecute3(atype) {
	commonDoExecute(3,atype);
}
function logout() {
	 var judge = window.confirm('ログアウトしますか? \nログアウトするとログイン画面に移動します。');
	 if(judge==true){
		 doExecute1();
	 }
}
//ボタン押下時､各タグの有効無効切り替えメソッド(=｢delete｣ボタンありなしどちらでも対応可能)
//属性｢data-changeDisabled｣を使用
//　0:｢disabled｣制御対象外､　1:｢insert｣ボタン､　2:｢update｣ボタン､　3:｢delete｣ボタン　4:テーブルのキー番号の入力欄､　5:テーブルのキー以外の入力欄
function doChangeDisabled(keyValue) {
	for(let i = 0; i < form.elements.length; i++){
		if(btnSelect=='insert'){
			//ボタン有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='1'){
				form.elements[i].setAttribute('disabled','disabled');
			}else if(form.elements[i].getAttribute('data-changeDisabled')=='2' || form.elements[i].getAttribute('data-changeDisabled')=='3'){
				form.elements[i].removeAttribute('disabled');
			}
			//入力欄有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='4'){
				form.elements[i].setAttribute('disabled','disabled');
			}else if(form.elements[i].getAttribute('data-changeDisabled')=='5'){
				form.elements[i].removeAttribute('disabled');
			}
		}else if(btnSelect=='update'){
			//ボタン有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='1' || form.elements[i].getAttribute('data-changeDisabled')=='3'){
				form.elements[i].removeAttribute('disabled');
			}else if(form.elements[i].getAttribute('data-changeDisabled')=='2'){
				form.elements[i].setAttribute('disabled','disabled');
			}
			//入力欄有効/無効切り替え
			if(keyValue!=''){
				if(form.elements[i].getAttribute('data-changeDisabled')=='4'){
					form.elements[i].setAttribute('disabled','disabled');
				}else if(form.elements[i].getAttribute('data-changeDisabled')=='5'){
					form.elements[i].removeAttribute('disabled');
				}
			}else if(keyValue==''){
				if(form.elements[i].getAttribute('data-changeDisabled')=='4'){
					form.elements[i].removeAttribute('disabled');
				}else if(form.elements[i].getAttribute('data-changeDisabled')=='5'){
					form.elements[i].setAttribute('disabled','disabled');
				}
			}
		}else if(btnSelect=='delete'){
			//ボタン有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='1' || form.elements[i].getAttribute('data-changeDisabled')=='2'){
				form.elements[i].removeAttribute('disabled');
			}else if(form.elements[i].getAttribute('data-changeDisabled')=='3'){
				form.elements[i].setAttribute('disabled','disabled');
			}
			//入力欄有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='4'){
				form.elements[i].removeAttribute('disabled');
			}else if(form.elements[i].getAttribute('data-changeDisabled')=='5'){
				form.elements[i].setAttribute('disabled','disabled');
			}
		}else if(btnSelect==''){
			//ボタン有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='1' || form.elements[i].getAttribute('data-changeDisabled')=='2' || form.elements[i].getAttribute('data-changeDisabled')=='3'){
				form.elements[i].removeAttribute('disabled');
			}
			//入力欄有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='4' || form.elements[i].getAttribute('data-changeDisabled')=='5'){
				form.elements[i].setAttribute('disabled','disabled');
			}
		}
	}
}
//ボタン押下時､Java動作用メソッド(=｢delete｣ボタンありなしどちらでも対応可能)
function btnChange(atype) {
	//隠しデータ｢btnSelect｣の値変更
	if(atype=='insert' && btnSelect!='insert'){
		form.elements['btnSelect'].value = 'insert';
	}else if(atype=='update' && btnSelect!='update'){
		form.elements['btnSelect'].value = 'update';
	}else if(atype=='delete' && btnSelect!='delete'){
		form.elements['btnSelect'].value = 'delete';
	}
	//属性｢data-changeDisabled｣
	//　0:｢disabled｣制御対象外､　1:｢insert｣ボタン､　2:｢update｣ボタン､　3:｢delete｣ボタン　4:テーブルのキー番号の入力欄､　5:テーブルのキー以外の入力欄
	for(let i = 0; i < form.elements.length; i++){
		//値クリア
		if(form.elements[i].getAttribute('data-changeDisabled')=='4' || form.elements[i].getAttribute('data-changeDisabled')=='5'){
			form.elements[i].value='';
		}
	}
	//表示処理
	doExecute2('btnSelect');
}

//========================================================================================================================================================================================================================
//'login.jsp'
if('${nextJsp}'=='/WEB-INF/main/login.jsp'){
	window.addEventListener('load', function(){
		docheck();
	})
	function docheck() {
		// userId
		var userId = form.elements['userId'];
		var judgeUserId = userId.value.match(/^[0-9]{6}$/)!=null?true:false;
		if(judgeUserId==true){
			userId.setAttribute('data-inputRequired','true');
		}else{
			userId.setAttribute('data-inputRequired','false');
		}
		// password
		var password = form.elements['password']
		var judgePassword = password.value.match(/^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[A-Za-z0-9]{8,20}$/)!=null?true:false;
		if(judgePassword==true){
			password.setAttribute('data-inputRequired','true');
		}else{
			password.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if((judgeUserId && judgePassword)==true){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
	function winClose() {
		open('about:blank', '_self').close();
	}
	function logout2() {
		var judge = window.confirm('在庫管理システムを終了しますか? \n終了するとウィンドウ/タブが閉じます。');
		if (judge == true) {
			winClose();
			session.invalidate();
		}
	}
}

//========================================================================================================================================================================================================================
//'purchaseOrder.jsp'
if('${nextJsp}'=='/WEB-INF/main/purchaseOrder.jsp'){
	window.addEventListener('load', function(){
		docheck();
		var keyValue = ('${G_PurchaseOrder.poNo}'!='' && '${G_PurchaseOrder.finFlg}'=='0')?'1':'';
		doChangeDisabled(keyValue);
		//orderDate日付選択を入直日まで､deliveryDate日付選択をorderDate日以降に規定
		var orderDate = form.elements['orderDate'];
		var deliveryDate = form.elements['deliveryDate'];
		var dayLimit = new Date();
		dayLimit.setDate(dayLimit.getDate());
		orderDate.setAttribute("max",dayLimit.toISOString().substr(0,10));
		deliveryDate.setAttribute('min', orderDate.value);
	})
	function docheck() {
		//customerNo
		var customerNo = form.elements['customerNo'];
		var customerName = form.elements['customerName'];
		var judgeCustomerNo = (customerNo.value.match(/^[A-Z]{1}[0-9]{4}$/)!=null && customerName.value.length>=1)?true:false;
		if(judgeCustomerNo==true){
			customerNo.setAttribute('data-inputRequired','true');
		}else{
			customerNo.setAttribute('data-inputRequired','false');
		}
		//productNo
		var productNo = form.elements['productNo'];
		var productName = form.elements['productName'];
		var judgeProductNo = (productNo.value.match(/^[0-9]{10}$/)!=null && productName.value.length>=1)?true:false;
		if(judgeProductNo==true){
			productNo.setAttribute('data-inputRequired','true');
		}else{
			productNo.setAttribute('data-inputRequired','false');
		}
		//orderDate 
		var orderDate = form.elements['orderDate'];
		var judgeOrderDate = (orderDate.value.length==10)?true:false;
		if(judgeOrderDate==true){
			orderDate.setAttribute('data-inputRequired','true');
		}else{
			orderDate.setAttribute('data-inputRequired','false');
		}
		//orderQty
		var orderQty = form.elements['orderQty'];
		var judgeOrderQty = (orderQty.value>=1 && orderQty.value<=99999999)?true:false;
		if(judgeOrderQty==true){
			orderQty.setAttribute('data-inputRequired','true');
		}else{
			orderQty.setAttribute('data-inputRequired','false');
		}
		//deliveryDate
		var deliveryDate = form.elements['deliveryDate'];
		deliveryDate.setAttribute('min', orderDate.value);
		var judgeDeliveryDate = (deliveryDate.value.length==10)?true:false;
		if(judgeDeliveryDate==true){
			deliveryDate.setAttribute('data-inputRequired','true');
		}else{
			deliveryDate.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if(((judgeCustomerNo && judgeProductNo && judgeOrderDate && judgeOrderQty && judgeDeliveryDate)==true)&&('${G_PurchaseOrder.finFlg}'=='0' || '${G_PurchaseOrder.finFlg}'=='')){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'order.jsp'
if('${nextJsp}'=='/WEB-INF/main/order.jsp'){
	window.addEventListener('load', function(){
		//｢更新｣｢削除｣時のorderLotの値算出
		var orderLot = form.elements['orderLot'];
		var lot = form.elements['lot'];
		var orderQty = form.elements['orderQty'];
		if(orderQty.value>=1 && lot.value>=1 && orderLot.value==''){
			orderLot.value = orderQty.value/lot.value;
		}
		docheck();
		var keyValue = ('${G_Order.orderNo}'!='' && '${G_Order.finFlg}'=='0')?'1':'';
		doChangeDisabled(keyValue);
		// 発注ロットの'placeholder'を規定
		var orderLot = form.elements['orderLot'];
		var maxNum = '1';
		if('${ProductMaster.lot}'==''){
			info = '品番入力後に入力可能';
		}else{
			info = '1-'+parseInt(99999999/Number('${ProductMaster.lot}'));
		}
		orderLot.setAttribute('placeholder', info);
		//deliveryDateの日付選択を受注日以降､入力日までとする
		var orderDate = form.elements['orderDate'];
		if(orderDate.value==''){
			var dayLimit = new Date();
			dayLimit.setDate(dayLimit.getDate());
			deliveryDate.setAttribute("min", dayLimit.toISOString().substr(0,10));
		}else if(orderDate.value!=''){
			deliveryDate.setAttribute('min', orderDate.value);
		}
	})
	function docheck() {
		//productNo
		var productNo = form.elements['productNo'];
		var productName = form.elements['productName'];
		var supplierNo = form.elements['supplierNo'];
		var supplierName = form.elements['supplierName'];
		var judgeProductNo = (productNo.value.match(/^[0-9]{10}$/)!=null && productName.value.length>=1 && supplierNo.value.match(/^[0-9]{6}$/)!=null && supplierName.value.length>=1)?true:false;
		if(judgeProductNo==true){
			productNo.setAttribute('data-inputRequired','true');
		}else{
			productNo.setAttribute('data-inputRequired','false');
		}
		//orderLot
		var orderLot = form.elements['orderLot'];
		var lot = form.elements['lot'];
		var orderQty = form.elements['orderQty'];
		if(productNo.value==''){
			orderLot.value='';
			orderQty.value = '';
		}
		var judgeOrderLot = (orderLot.value>=1 && orderLot.value<=parseInt(99999999/Number('${ProductMaster.lot}')))?true:false;
		if(judgeOrderLot==true){
			if(lot.value>=1 && lot.value!=''){
				orderQty.value = orderLot.value*lot.value;
			}
			orderLot.setAttribute('data-inputRequired','true');
		}else{
			orderQty.value = '';
			orderLot.setAttribute('data-inputRequired','false');
		}
		//deliveryDate
		var deliveryDate = form.elements['deliveryDate'];
		var judgeDeliveryDate = (deliveryDate.value.length==10)?true:false;
		if(judgeDeliveryDate==true){
			deliveryDate.setAttribute('data-inputRequired','true');
		}else{
			deliveryDate.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if(((judgeProductNo && judgeOrderLot && judgeDeliveryDate)==true)&&('${G_Order.finFlg}'=='0' || '${G_Order.finFlg}'=='')){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'shipping.jsp'
if('${nextJsp}'=='/WEB-INF/main/shipping.jsp'){
	window.addEventListener('load', function(){
		docheck();
		var keyValue = ('${G_Shipping.poNo}'!='' && '${G_Shipping.finFlg}'=='0')?'1':'';
		doChangeDisabled(keyValue);
		//shipDateの日付選択をorderDate以降とする
		var orderDate = form.elements['orderDate'];
		var shipDate = form.elements['shipDate'];
		shipDate.setAttribute('min', orderDate.value);
	})
	function docheck() {
		//shipDate
		var shipDate = form.elements['shipDate'];
		var judgeShipDate = (shipDate.value.length==10)?true:false;
		if(judgeShipDate==true){
			shipDate.setAttribute('data-inputRequired','true');
		}else{
			shipDate.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if((judgeShipDate==true)&&(('${G_Shipping.finFlg}'=='0' && btnSelect=='update') || ('${G_Shipping.finFlg}'=='1' &&  btnSelect=='delete'))){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'arrival.jsp'
if('${nextJsp}'=='/WEB-INF/main/arrival.jsp'){
	window.addEventListener('load', function(){
		//｢更新｣｢削除｣時のorderLotの値算出
		var orderLot = form.elements['orderLot'];
		var lot = form.elements['lot'];
		var orderQty = form.elements['orderQty'];
		if(orderQty.value>=1 && lot.value>=1 && orderLot.value==''){
			orderLot.value = orderQty.value/lot.value;
		}
		docheck();
		var keyValue = ('${G_Arrival.orderNo}'!='' && '${G_Arrival.finFlg}'=='0')?'1':'';
		doChangeDisabled(keyValue);
		//dueDateの日付選択をorderDate以降とする
		var orderDate = form.elements['orderDate'];
		var dueDate = form.elements['dueDate'];
		dueDate.setAttribute('min', orderDate.value);
	})
	function docheck() {
		//dueDate
		var dueDate = form.elements['dueDate'];
		var judgeDueDate = (dueDate.value.length==10)?true:false;
		if(judgeDueDate==true){
			dueDate.setAttribute('data-inputRequired','true');
		}else{
			dueDate.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if((judgeDueDate==true)&&(('${G_Arrival.finFlg}'=='0' && btnSelect=='update') || ('${G_Arrival.finFlg}'=='1' &&  btnSelect=='delete'))){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'entryExitInfo.jsp'
if('${nextJsp}'=='/WEB-INF/main/entryExitInfo.jsp'){
	window.addEventListener('load', function(){
		// 入出庫ラジオボタンのチェックを指定
		var stockInOut = form.elements['stockInOut'];
		for (var i = 0; i < stockInOut.length; i++) {
			if (stockInOut[i].value=='${G_EntryExitInfo.stockInOut}') {
				stockInOut[i].checked=true;
				break;
			}
			if(i==1 && '${G_EntryExitInfo.productNo}'!=''){
				stockInOut[0].checked=true;
				break;
			}
		}
		docheck();
		var keyValue = ('${G_EntryExitInfo.enExId}'!='' && '${G_EntryExitInfo.registDate}'!='')?'1':'';
		doChangeDisabled(keyValue);
		// 入出庫ラジオボタンの有効無効切替
		for (var i = 0; i < stockInOut.length; i++) {
			if(btnSelect=='insert'){
				stockInOut[i].removeAttribute('disabled');
			}else if(btnSelect=='update' || btnSelect=='delete' || btnSelect==''){
				stockInOut[i].setAttribute('disabled','disabled');
			}
		}
		// 更新時のproductNoを有効にしない
		if(btnSelect=='update' && keyValue=='1'){
			var productNo = form.elements['productNo'];
			productNo.setAttribute('disabled','disabled');
		}
	})
	function docheck() {
		//productNo
		var productNo = form.elements['productNo'];
		var productName = form.elements['productName'];
		var wStockQty = form.elements['wStockQty'];
		var oowStockQty = form.elements['oowStockQty'];
		var judgeProductNo = (productNo.value.match(/^[0-9]{10}$/)!=null && productName.value.length>=1 && (wStockQty.value!=null || wStockQty.value!='') && (oowStockQty.value!=null || oowStockQty.value!=''))?true:false;
		if(judgeProductNo==true){
			productNo.setAttribute('data-inputRequired','true');
		}else{
			productNo.setAttribute('data-inputRequired','false');
		}
		// stockInOut
		var stockInOut = form.elements['stockInOut'];
		var judgeStockInOut = false;
		for (var i = 0; i < stockInOut.length; i++) {
			if (stockInOut[i].checked==true) {
				judgeStockInOut=true;
				break;
			}
		}
		//enExDate
		var enExDate = form.elements['enExDate'];
		var judgeEnExDate = (enExDate.value.length==10)?true:false;
		if(judgeEnExDate==true){
			enExDate.setAttribute('data-inputRequired','true');
		}else{
			enExDate.setAttribute('data-inputRequired','false');
		}
		//enExNum
		var enExNum = form.elements['enExNum'];
		var judgeEnExNum = false;
		var onkeyupValue = '';
		var maxValue = '';
		var info = '';
		if('${G_EntryExitInfo.productNo}'=='' || '${ProductMaster.productName}'==''){
			for (var i = 0; i < stockInOut.length; i++) {
				stockInOut[i].checked=false;
			}
			enExDate.value='';
			enExNum.value='';
			maxValue = '99999999';
			info = '品番入力後に入力可能';
		}else{
			if(stockInOut.value=='in'){
				info = '1-'+oowStockQty.value;
				maxValue = oowStockQty.value;
				judgeEnExNum = (Number(enExNum.value)>=1 && Number(enExNum.value)<=Number(oowStockQty.value))?true:false;
			}else if(stockInOut.value=='out' || stockInOut.value==''){
				judgeEnExNum = (Number(enExNum.value)>=1 && Number(enExNum.value)<=Number(wStockQty.value))?true:false;
				maxValue = wStockQty.value;
				info = '1-'+wStockQty.value;
			}
		}
		enExNum.setAttribute('max', maxValue);
		enExNum.setAttribute('onkeydown', "befValue=this.value");
		onkeyupValue = "this.value = ('${G_EntryExitInfo.productNo}'=='' || this.value=='')?'':(this.value>=1 && this.value<="+maxValue+")?this.value:befValue;"
		enExNum.setAttribute('onkeyup', onkeyupValue);
		enExNum.setAttribute('placeholder', info);
		if(judgeEnExNum==true){
			enExNum.setAttribute('data-inputRequired','true');
		}else{
			enExNum.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if((judgeProductNo && judgeStockInOut && judgeEnExDate && judgeEnExNum)==true){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'stockList.jsp'
if('${nextJsp}'=='/WEB-INF/main/stockList.jsp'){
	window.addEventListener('load', function(){
		if('${ProductMasterList}'==''){
			doExecute2('searchProductMasterList');
		}
		docheck();
		var keyValue='';
		doChangeDisabled(keyValue);
	})
	function docheck() {
		//productNo
		var productNo = form.elements['productNo'];
		var productName = form.elements['productName'];
		var judgeProductNo = (productNo.value.match(/^[0-9]{10}$/)!=null && productName.value.length>=1)?true:false;
		if(judgeProductNo==true){
			productNo.setAttribute('data-inputRequired','true');
		}else{
			productNo.setAttribute('data-inputRequired','false');
		}
	}
}

//========================================================================================================================================================================================================================
//'purchaseOrderList'
if('${nextJsp}'=='/WEB-INF/main/purchaseOrderList.jsp'){
	window.addEventListener('load', function(){
		if('${ProductMasterList}'==''){
			doExecute2('searchProductMasterList');
		}
		if('${G_PurchaseOrderList.productNo}'!='' && '${ProductMaster.productName}'!=''){
			// チェックボックスのチェック指定
			var shipped = form.elements['shipped'];
			if('${G_PurchaseOrderList.shipped}'=='shipped'){
				shipped.setAttribute('checked', 'cheked');
			}else{
				shipped.removeAttribute('checked');
			}
			var notShipped = form.elements['notShipped'];
			if('${G_PurchaseOrderList.notShipped}'=='notShipped'){
				notShipped.setAttribute('checked', 'cheked');
			}else{
				notShipped.removeAttribute('checked');
			}
			// ラジオボタンのチェック指定
			var ascendingDescending = form.elements['ascendingDescending'];
			if('${G_PurchaseOrderList.ascendingDescending}'==''){
				ascendingDescending[0].checked=true;
			}else if('${G_PurchaseOrderList.ascendingDescending}'!=''){
				for (var i = 0; i < ascendingDescending.length; i++) {
					if (ascendingDescending[i].value=='${G_PurchaseOrderList.ascendingDescending}') {
						ascendingDescending[i].checked=true;
						break;
					}
				}
			}
			// ラジオボタンのチェック指定
			var sort = form.elements['sort'];
			for (var i = 0; i < sort.length; i++) {
				if (sort[i].value=='${G_PurchaseOrderList.sort}') {
					sort[i].checked=true;
					break;
				}
			}
			//startDate endDateの日付選択を規定
			var startDate = form.elements['startDate'];
			var endDate = form.elements['endDate'];
			if(startDate.value != ''){
				endDate.setAttribute('min', startDate.value);
			}
			if(endDate.value != ''){
				startDate.setAttribute('max', endDate.value);
			}
		}
		docheck();
		var productNo = form.elements['productNo'];
		var productName = form.elements['productName'];
		var keyValue = (productNo.value.match(/^[0-9]{10}$/)!=null && productName.value.length>=1)?'1':'';
		doChangeDisabled(keyValue);
	})
	function docheck() {	
		//productNo
		var productNo = form.elements['productNo'];
		var productName = form.elements['productName'];
		var judgeProductNo = (productNo.value.match(/^[0-9]{10}$/)!=null && productName.value.length>=1)?true:false;
		if(judgeProductNo==true){
			productNo.setAttribute('data-inputRequired','true');
		}else{
			productNo.setAttribute('data-inputRequired','false');
		}
	}
}

//========================================================================================================================================================================================================================
//'orderList.jsp'
if('${nextJsp}'=='/WEB-INF/main/orderList.jsp'){
	window.addEventListener('load', function(){
		if('${ProductMasterList}'==''){
			doExecute2('searchProductMasterList');
		}
		if('${G_OrderList.productNo}'!='' && '${ProductMaster.productName}'!=''){
			// チェックボックスのチェック指定
			var inStock = form.elements['inStock'];
			if('${G_OrderList.inStock}'=='inStock'){
				inStock.setAttribute('checked', 'cheked');
			}else{
				inStock.removeAttribute('checked');
			}
			var notInStock = form.elements['notInStock'];
			if('${G_OrderList.notInStock}'=='notInStock'){
				notInStock.setAttribute('checked', 'cheked');
			}else{
				notInStock.removeAttribute('checked');
			}
			// ラジオボタンのチェック指定
			var ascendingDescending = form.elements['ascendingDescending'];
			if('${G_OrderList.ascendingDescending}'==''){
				ascendingDescending[0].checked=true;
			}else if('${G_OrderList.ascendingDescending}'!=''){
				for (var i = 0; i < ascendingDescending.length; i++) {
					if (ascendingDescending[i].value=='${G_OrderList.ascendingDescending}') {
						ascendingDescending[i].checked=true;
						break;
					}
				}
			}
			// ラジオボタンのチェック指定
			var sort = form.elements['sort'];
			for (var i = 0; i < sort.length; i++) {
				if (sort[i].value=='${G_OrderList.sort}') {
					sort[i].checked=true;
					break;
				}
			}
			//startDate endDateの日付選択を規定
			var startDate = form.elements['startDate'];
			var endDate = form.elements['endDate'];
			if(startDate.value != ''){
				endDate.setAttribute('min', startDate.value);
			}
			if(endDate.value != ''){
				startDate.setAttribute('max', endDate.value);
			}
		}
		docheck();
		var productNo = form.elements['productNo'];
		var productName = form.elements['productName'];
		var keyValue = (productNo.value.match(/^[0-9]{10}$/)!=null && productName.value.length>=1)?'1':'';
		doChangeDisabled(keyValue);
	})
	function docheck() {
		//productNo
		var productNo = form.elements['productNo'];
		var productName = form.elements['productName'];
		var judgeProductNo = (productNo.value.match(/^[0-9]{10}$/)!=null && productName.value.length>=1)?true:false;
		if(judgeProductNo==true){
			productNo.setAttribute('data-inputRequired','true');
		}else{
			productNo.setAttribute('data-inputRequired','false');
		}
	}
}





//========================================================================================================================================================================================================================
//'productMaster.jsp'
if('${nextJsp}'=='/WEB-INF/main/productMaster.jsp'){
	window.addEventListener('load', function(){
		docheck();
		var keyValue = ('${G_ProductMaster.productNo}'!='' && '${G_ProductMaster.productName}'!='') || ('${G_ProductMaster.productNo}'!='' && '${toAction}'!='searchProductNo')?'1':'';
		doChangeDisabled(keyValue);
	})
	function docheck() {
		//productName 
		var productName = form.elements['productName'];
		var judgeProductName = (productName.value.length>=1 && productName.value.length<=100)?true:false;
		if(judgeProductName==true){
			productName.setAttribute('data-inputRequired','true');
		}else{
			productName.setAttribute('data-inputRequired','false');
		}
		//supplierNo 
		var supplierNo = form.elements['supplierNo'];
		var judgeSupplierNo = (supplierNo.value.match(/^[0-9]{6}$/)!=null && supplierName.value!="")?true:false;
		if(judgeSupplierNo==true){
			supplierNo.setAttribute('data-inputRequired','true');
		}else{
			supplierNo.setAttribute('data-inputRequired','false');
		}
		//unitPrice 
		var unitPrice = form.elements['unitPrice'];
		var judgeUnitPrice = (unitPrice.value>=0.01 && unitPrice.value<=999999.99)?true:false;
		if(judgeUnitPrice==true){
			unitPrice.setAttribute('data-inputRequired','true');
		}else{
			unitPrice.setAttribute('data-inputRequired','false');
		}
		//leadTime
		var leadTime = form.elements['leadTime'];
		var judgeLeadTime = (leadTime.value>=1 && leadTime.value<=999)?true:false;
		if(judgeLeadTime==true){
			leadTime.setAttribute('data-inputRequired','true');
		}else{
			leadTime.setAttribute('data-inputRequired','false');
		}
		//lot
		var lot = form.elements['lot'];
		var judgeLot = (lot.value>=1 && lot.value<=999999)?true:false;
		if(judgeLot==true){
			lot.setAttribute('data-inputRequired','true');
		}else{
			lot.setAttribute('data-inputRequired','false');
		}
		//location 
		var location = form.elements['location'];
		var judgeLocation = (location.value.length>=1 && location.value.length<=6)?true:false;
		if(judgeLocation==true){
			location.setAttribute('data-inputRequired','true');
		}else{
			location.setAttribute('data-inputRequired','false');
		}
		//baseStock
		var baseStock = form.elements['baseStock'];
		var judgeBaseStock = (baseStock.value>=1 && baseStock.value<=999999)?true:false;
		if(judgeBaseStock==true){
			baseStock.setAttribute('data-inputRequired','true');
		}else{
			baseStock.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if((judgeProductName && judgeSupplierNo && judgeUnitPrice && judgeLeadTime && judgeLot && judgeLocation && judgeBaseStock)==true){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'customerMaster.jsp'
if('${nextJsp}'=='/WEB-INF/main/customerMaster.jsp'){
	window.addEventListener('load', function(){
		docheck();
		var keyValue = ('${G_CustomerMaster.customerNo}'!='' && '${G_CustomerMaster.customerName}'!='')?'1':'';
		doChangeDisabled(keyValue);
	})
	function docheck() {
		//customerName 
		var customerName = form.elements['customerName'];
		var judgeCustomerName = (customerName.value.length>=1 && customerName.value.length<=100)?true:false;
		if(judgeCustomerName==true){
			customerName.setAttribute('data-inputRequired','true');
		}else{
			customerName.setAttribute('data-inputRequired','false');
		}
		//tel 
		var tel = form.elements['tel'];
		var judgeTel = tel.value.match(/^[0-9]{9,15}$/)!=null?true:false;
		if(judgeTel==true){
			tel.setAttribute('data-inputRequired','true');
		}else{
			tel.setAttribute('data-inputRequired','false');
		}
		//delivaryLeadtime
		var delivaryLeadtime = form.elements['delivaryLeadtime'];
		var judgeDelivaryLeadtime = (delivaryLeadtime.value>=1 && delivaryLeadtime.value<=999)?true:false;
		if(judgeDelivaryLeadtime==true){
			delivaryLeadtime.setAttribute('data-inputRequired','true');
		}else{
			delivaryLeadtime.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if((judgeCustomerName && judgeTel && judgeDelivaryLeadtime)==true){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'supplierMaster.jsp'
if('${nextJsp}'=='/WEB-INF/main/supplierMaster.jsp'){
	window.addEventListener('load', function(){
		docheck();
		var keyValue = ('${G_SupplierMaster.supplierNo}'!='' && '${G_SupplierMaster.supplierName}'!='')?'1':'';
		doChangeDisabled(keyValue);
	})
	function docheck() {
		//supplierName 
		var supplierName = form.elements['supplierName'];
		var judgeSupplierName = (supplierName.value.length>=1 && supplierName.value.length<=100)?true:false;
		if(judgeSupplierName==true){
			supplierName.setAttribute('data-inputRequired','true');
		}else{
			supplierName.setAttribute('data-inputRequired','false');
		}
		//tel
		var tel = form.elements['tel'];
		var judgeTel = tel.value.match(/^[0-9]{9,15}$/)!=null?true:false;
		if(judgeTel==true){
			tel.setAttribute('data-inputRequired','true');
		}else{
			tel.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if((judgeSupplierName && judgeTel)==true){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'userMaster.jsp'
if('${nextJsp}'=='/WEB-INF/main/userMaster.jsp'){
	window.addEventListener('load', function(){
		docheck();
		var keyValue = ('${G_UserMaster.userId}'!='' && '${G_UserMaster.name}'!='')?'1':'';
		doChangeDisabled(keyValue);
	})
	function docheck() {
		//userName
		var userName = form.elements['userName'];
		var judgeUserName = (userName.value.length>0 && userName.value.length<=50)?true:false;
		if(judgeUserName==true){
			userName.setAttribute('data-inputRequired','true');
		}else{
			userName.setAttribute('data-inputRequired','false');
		}
		//password
		var password = form.elements['password'];
		var judgePassword = password.value.match(/^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[A-Za-z0-9]{8,20}$/)!=null?true:false;
		if(judgePassword==true){
			password.setAttribute('data-inputRequired','true');
		}else{
			password.setAttribute('data-inputRequired','false');
		}
		//passwordForCheck
		var passwordForCheck = form.elements['passwordForCheck'];
		var judgePasswordForCheck = (passwordForCheck.value.match(/^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[A-Za-z0-9]{8,20}$/)!=null&&passwordForCheck.value==password.value)?true:false;
		if(judgePasswordForCheck==true){
			passwordForCheck.setAttribute('data-inputRequired','true');
		}else{
			passwordForCheck.setAttribute('data-inputRequired','false');
		}
		//dept
		var dept = form.elements['dept'];
		var judgeDept = (dept.value.length>0 && dept.value.length<=50)?true:false;
		if(judgeDept==true){
			dept.setAttribute('data-inputRequired','true');
		}else{
			dept.setAttribute('data-inputRequired','false');
		}
		//hireDate
		var hireDate = form.elements['hireDate'];
		var date = new Date(hireDate.value);
		var outPutDate = date.getFullYear() + '/' + ('0' + (date.getMonth() + 1)).slice(-2) + '/' + ('0' + date.getDate()).slice(-2)
		var judgeHireDate = (hireDate.value.match(/^[0-9]{4}\/[0-9]{2}\/[0-9]{2}$/)!=null && isNaN(date.getDate())!=true && hireDate.value==outPutDate)?true:false;
		if(judgeHireDate==true){
			hireDate.setAttribute('data-inputRequired','true');
		}else{
			hireDate.setAttribute('data-inputRequired','false');
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if((judgeUserName && judgePassword && judgePasswordForCheck && judgeDept && judgeHireDate)==true){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}
	
//========================================================================================================================================================================================================================
//'amountCalc.jsp'
if('${nextJsp}'=='/WEB-INF/main/amountCalc.jsp'){
	window.addEventListener('load', function(){
		 //「amountCalcProgFlg」は、
		 //   0:途中終了、1:処理中、2:異常なし終了、3:異常あり終了
		 if('${amountCalcProgFlg}' != '' && '${amountCalcProgFlg}' != '0' && '${amountCalcProgFlg}' != '2'){
			 if('${amountCalcProgFlg}' == '3'){
			 	form.elements['startAmountCalc'].setAttribute('disabled','disabled');
			 	form.elements['cancel'].setAttribute('disabled','disabled');
				window.setTimeout(function () {
					doExecute2('goToAmountCalcOrderPage');
				}, 3500);
			 }else if ('${amountCalcProgFlg}' == '1'){
				form.elements['startAmountCalc'].setAttribute('disabled','disabled');
				form.elements['cancel'].removeAttribute('disabled');
				window.setTimeout(function () {
					doExecute2('processNow');
				}, 800);
			 }
		 }else{
		 	form.elements['startAmountCalc'].removeAttribute('disabled');
		 	form.elements['cancel'].setAttribute('disabled','disabled');
		 }
	})
}
	
//========================================================================================================================================================================================================================
//'amountCalcOrder.jsp'
if('${nextJsp}'=='/WEB-INF/main/amountCalcOrder.jsp'){
	window.addEventListener('load', function(){
		var productNo = document.forms[2].elements['gProductNo'];
		var orderLotNum = document.forms[2].elements['gOrderLotNum'];
		for(var i=0;i<productNo.length;i++){
			if('${G_AmountCalcOrder.productNo}' != '' && productNo.options[i].value == '${G_AmountCalcOrder.productNo}'){
				productNo.options[i].selected = true;
				break;
			}else if('${G_AmountCalcOrder.productNo}' == ''){
				productNo.options[0].selected = true;
				break;
			}
		}
		docheck();
	})
	function docheck() {
		// gProductNo
		var gProductNo = form.elements['gProductNo'];
		var judgeGProductNo = (gProductNo.value!='')?true:false;
		if(judgeGProductNo==true){
			gProductNo.setAttribute('data-inputRequired','true');
		}else{
			gProductNo.setAttribute('data-inputRequired','false');
		}	
		// gOrderLotNum
		var gOrderLotNum = form.elements['gOrderLotNum'];
		var judgeGOrderLotNum = false;
		if(typeof gOrderLotNum!=undefined && gOrderLotNum!=null){
			var orderNum = document.getElementById('orderNum');
			var orderPrice = document.getElementById('orderPrice');
			judgeGOrderLotNum = (gOrderLotNum.value>=1 && gOrderLotNum.value%1==0)?true:false;
			if(judgeGOrderLotNum==true){
				gOrderLotNum.setAttribute('data-inputRequired','true');
				// 発注数[個]/発注額[円]の算出
				var lotPcs = '${amountCalcAllListMap[productNoKey][0].lotPcs}'!=''?'${amountCalcAllListMap[productNoKey][0].lotPcs}':0;
				var unitPrice = '${amountCalcAllListMap[productNoKey][0].unitPrice}'!=''?'${amountCalcAllListMap[productNoKey][0].unitPrice}':0;
				orderNum.innerHTML = '：' + (gOrderLotNum.value * lotPcs);
				orderPrice.innerHTML = '：' + (gOrderLotNum.value * lotPcs * unitPrice);
			}else{
				gOrderLotNum.setAttribute('data-inputRequired','false');
				// 発注数[個]/発注額[円]の表示
				orderNum.innerHTML = '：';
				orderPrice.innerHTML = '：';
			}
		}
		// doExecuteBTNのdisabled属性変更
		if(document.getElementsByName('doExecuteBTN').length==1){
			if((judgeGProductNo && judgeGOrderLotNum)==true){
				form.elements['doExecuteBTN'].removeAttribute('disabled');
			}else{
				form.elements['doExecuteBTN'].setAttribute('disabled','disabled');
			}
		}
	}
}
</script>
</body>
</html>