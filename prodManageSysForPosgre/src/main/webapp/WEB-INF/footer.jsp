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
	var scrollY=window.sessionStorage.getItem(['scrollY']);
	if(scrollY!=null){
		scrollTo(0, scrollY);
	}
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
})
// Actionクラストリガー用メソッド
//　→formタグの属性actionでActionクラスを指定し､
//　　リクエストパラメータに動作内容を指定する事で､複雑な動作を行わせる
function commonDoExecute(num,atype) {
	var positionY = window.pageYOffset;
	window.sessionStorage.setItem(['scrollY'],[positionY]);
	var form = document.forms[num];
	form.toAction.value = atype;
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
//｢*Master.jsp｣用メソッド
//属性｢data-changeDisabled｣
//　0:｢disabled｣制御対象外､　1:｢insert｣ボタン､　2:｢update｣ボタン､　3:テーブルのキー番号の入力欄､　4:テーブルのキー以外の入力欄
function doChangeDisabled(keyValue) {
	for(let i = 0; i < form.elements.length; i++){
		if(btnSelect=='insert'){
			//ボタン有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='1'){
				form.elements[i].setAttribute('disabled','disabled');
			}else if(form.elements[i].getAttribute('data-changeDisabled')=='2'){
				form.elements[i].removeAttribute('disabled');
			}
			//入力欄有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='3'){
				form.elements[i].setAttribute('disabled','disabled');
			}else if(form.elements[i].getAttribute('data-changeDisabled')=='4'){
				form.elements[i].removeAttribute('disabled');
			}
		}else if(btnSelect=='update'){
			//ボタン有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='1'){
				form.elements[i].removeAttribute('disabled');
			}else if(form.elements[i].getAttribute('data-changeDisabled')=='2'){
				form.elements[i].setAttribute('disabled','disabled');
			}
			//入力欄有効/無効切り替え
			if(keyValue!=''){
				if(form.elements[i].getAttribute('data-changeDisabled')=='3'){
					form.elements[i].setAttribute('disabled','disabled');
				}else if(form.elements[i].getAttribute('data-changeDisabled')=='4'){
					form.elements[i].removeAttribute('disabled');
				}
			}else if(keyValue==''){
				if(form.elements[i].getAttribute('data-changeDisabled')=='3'){
					form.elements[i].removeAttribute('disabled');
				}else if(form.elements[i].getAttribute('data-changeDisabled')=='4'){
					form.elements[i].setAttribute('disabled','disabled');
				}
			}
		}else if(btnSelect==''){
			//ボタン有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='1' || form.elements[i].getAttribute('data-changeDisabled')=='2'){
				form.elements[i].removeAttribute('disabled');
			}
			//入力欄有効/無効切り替え
			if(form.elements[i].getAttribute('data-changeDisabled')=='3' || form.elements[i].getAttribute('data-changeDisabled')=='4'){
				form.elements[i].setAttribute('disabled','disabled');
			}
		}
	}
}
//｢*Master.jsp｣用メソッド
function btnChange(atype) {
	//隠しデータ｢btnSelect｣の値変更
	if(atype=='insert' && btnSelect!='insert'){
		form.elements['btnSelect'].value = 'insert';
	}else if(atype=='update' && btnSelect!='update'){
		form.elements['btnSelect'].value = 'update';
	}
	//属性｢data-changeDisabled｣
	//　0:｢disabled｣制御対象外､　1:｢insert｣ボタン､　2:｢update｣ボタン､　3:テーブルのキー番号の入力欄､　4:テーブルのキー以外の入力欄
	for(let i = 0; i < form.elements.length; i++){
		//値クリア
		if(form.elements[i].getAttribute('data-changeDisabled')=='3' || form.elements[i].getAttribute('data-changeDisabled')=='4'){
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
//'productMaster.jsp'
if('${nextJsp}'=='/WEB-INF/main/productMaster.jsp'){
	window.addEventListener('load', function(){
		docheck();
		doChangeDisabled('${G_ProductMaster.productNo}');
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
		var judgeSupplierNo = supplierNo.value.match(/^[0-9]{6}$/)!=null?true:false;
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
		doChangeDisabled('${G_CustomerMaster.customerNo}');
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
		doChangeDisabled('${G_SupplierMaster.supplierNo}');
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
		doChangeDisabled('${G_UserMaster.userId}');
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
				doExecute2('goToAmountCalcOrderPage');
			 }else if ('${amountCalcProgFlg}' == '1'){
				form.elements[1].setAttribute('disabled','disabled');
				form.elements[2].removeAttribute('disabled');
				 window.setTimeout(function () {
						doExecute2('processNow');
				 }, 800);
			 }
		 }else{
		 	form.elements[1].removeAttribute('disabled');
		 	form.elements[2].setAttribute('disabled','disabled');
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
				//フィールド｢orderFinFlg｣は､
				// 「1：アクション(発注/納期調整)不要」
				// 「0-2：発注が必要な品番」
				// 「0-1：納期調整が必要な品番」
				if('${amountCalcAllListMap[productNoKey][0].orderFinFlg}' == '0-1'){
					orderLotNum.value = '';
					orderLotNum.setAttribute('disabled','disabled');
				}else if('${amountCalcAllListMap[productNoKey][0].orderFinFlg}' == '0-2'){
					if('${G_AmountCalcOrder.orderLotNum}' != ''){
						orderLotNum.value = parseInt('${G_AmountCalcOrder.orderLotNum}');
					}
					orderLotNum.removeAttribute('disabled');
				}
				if('${G_AmountCalcOrder.productNo}' != '' && '${G_AmountCalcOrder.orderLotNum}' != '' && '${amountCalcAllListMap[productNoKey][0].orderFinFlg}' == '0-2'){
					form.elements['orderBTN'].removeAttribute('disabled');
				}else{
					form.elements['orderBTN'].setAttribute('disabled','disabled');
				}
				break;
			 }else if('${G_AmountCalcOrder.productNo}' == ''){
				productNo.options[0].selected = true;
				break;
			 }
		}
	})
}
</script>
</body>
</html>