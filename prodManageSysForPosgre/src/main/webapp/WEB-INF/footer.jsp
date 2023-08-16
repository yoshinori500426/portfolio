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
if(document.getElementsByName('doExecuteBTN').length==1){
	var doExecuteBTN = form.elements['doExecuteBTN'];
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

//========================================================================================================================================================================================================================
//'login.jsp'
if('${nextJsp}'=='/WEB-INF/main/login.jsp'){
	window.addEventListener('load', function(){
		docheck();
	})
	function docheck() {
		var userId = form.elements['userId'];
		var password = form.elements['password']
		var judgeUserId = userId.value.match(/^[0-9]{6}$/)!=null?true:false;
		var judgePassword = password.value.match(/^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[A-Za-z0-9]{8,20}$/)!=null?true:false;
		if(judgeUserId==true){
			userId.setAttribute('data-inputRequired','true');
		}else{
			userId.setAttribute('data-inputRequired','false');
		}
		if(judgePassword==true){
			password.setAttribute('data-inputRequired','true');
		}else{
			password.setAttribute('data-inputRequired','false');
		}
		if((judgeUserId&&judgePassword)==true){
			doExecuteBTN.removeAttribute('disabled');
		}else{
			doExecuteBTN.setAttribute('disabled','disabled');
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
	function btnChange(atype) {
		if(atype=='insert'){
			if(btnSelect != 'insert'){
				form.elements['btnSelect'].value = 'insert';
				//値クリア
				form.elements['userId'].value='';
				form.elements['userName'].value='';
				form.elements['password'].value='';
				form.elements['passwordForCheck'].value='';
				form.elements['dept'].value='';
				form.elements['etc'].value='';
				form.elements['hireDate'].value='';
				//表示処理
				doExecute2('btnSelect');
			}
		}else if(atype=='update'){
			if(btnSelect != 'update'){
				form.elements['btnSelect'].value = 'update';
				//値クリア
				form.elements['userId'].value='';
				form.elements['userName'].value='';
				form.elements['password'].value='';
				form.elements['passwordForCheck'].value='';
				form.elements['dept'].value='';
				form.elements['etc'].value='';
				form.elements['hireDate'].value='';
				//表示処理
				doExecute2('btnSelect');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'customerMaster.jsp'
if('${nextJsp}'=='/WEB-INF/main/customerMaster.jsp'){
	function btnChange(atype) {
		if(atype=='insert'){
			if(btnSelect != 'insert'){
				form.elements['btnSelect'].value = 'insert';
				//値クリア
				form.elements['userId'].value='';
				form.elements['userName'].value='';
				form.elements['password'].value='';
				form.elements['passwordForCheck'].value='';
				form.elements['dept'].value='';
				form.elements['etc'].value='';
				form.elements['hireDate'].value='';
				//表示処理
				doExecute2('btnSelect');
			}
		}else if(atype=='update'){
			if(btnSelect != 'update'){
				form.elements['btnSelect'].value = 'update';
				//値クリア
				form.elements['userId'].value='';
				form.elements['userName'].value='';
				form.elements['password'].value='';
				form.elements['passwordForCheck'].value='';
				form.elements['dept'].value='';
				form.elements['etc'].value='';
				form.elements['hireDate'].value='';
				//表示処理
				doExecute2('btnSelect');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'supplierMaster.jsp'
if('${nextJsp}'=='/WEB-INF/main/supplierMaster.jsp'){
	function btnChange(atype) {
		if(atype=='insert'){
			if(btnSelect != 'insert'){
				form.elements['btnSelect'].value = 'insert';
				//値クリア
				form.elements['userId'].value='';
				form.elements['userName'].value='';
				form.elements['password'].value='';
				form.elements['passwordForCheck'].value='';
				form.elements['dept'].value='';
				form.elements['etc'].value='';
				form.elements['hireDate'].value='';
				//表示処理
				doExecute2('btnSelect');
			}
		}else if(atype=='update'){
			if(btnSelect != 'update'){
				form.elements['btnSelect'].value = 'update';
				//値クリア
				form.elements['userId'].value='';
				form.elements['userName'].value='';
				form.elements['password'].value='';
				form.elements['passwordForCheck'].value='';
				form.elements['dept'].value='';
				form.elements['etc'].value='';
				form.elements['hireDate'].value='';
				//表示処理
				doExecute2('btnSelect');
			}
		}
	}
}

//========================================================================================================================================================================================================================
//'userMaster.jsp'
if('${nextJsp}'=='/WEB-INF/main/userMaster.jsp'){
	window.addEventListener('load', function(){
		if(btnSelect=='insert'){
			//ボタン有効/無効切り替え
			form.elements['insert'].setAttribute('disabled','disabled');
			form.elements['update'].removeAttribute('disabled');
			//入力欄有効/無効切り替え
			form.elements['userId'].setAttribute('disabled','disabled');
			form.elements['userName'].removeAttribute('disabled');
			form.elements['password'].removeAttribute('disabled');
			form.elements['passwordForCheck'].removeAttribute('disabled');
			form.elements['dept'].removeAttribute('disabled');
			form.elements['etc'].removeAttribute('disabled');
			form.elements['hireDate'].removeAttribute('disabled');
		}else if(btnSelect=='update' && '${G_UserMaster.userId}'!=''){
			//ボタン有効/無効切り替え
			form.elements['insert'].removeAttribute('disabled');
			form.elements['update'].setAttribute('disabled','disabled');
			//入力欄有効/無効切り替え
			form.elements['userId'].setAttribute('disabled','disabled');
			form.elements['userName'].removeAttribute('disabled');
			form.elements['password'].removeAttribute('disabled');
			form.elements['passwordForCheck'].removeAttribute('disabled');
			form.elements['dept'].removeAttribute('disabled');
			form.elements['etc'].removeAttribute('disabled');
			form.elements['hireDate'].removeAttribute('disabled');
		}else if(btnSelect=='update' && '${G_UserMaster.userId}'==''){
			//ボタン有効/無効切り替え
			form.elements['insert'].removeAttribute('disabled');
			form.elements['update'].setAttribute('disabled','disabled');
			//入力欄有効/無効切り替え
			form.elements['userId'].removeAttribute('disabled');
			form.elements['userName'].setAttribute('disabled','disabled');
			form.elements['password'].setAttribute('disabled','disabled');
			form.elements['passwordForCheck'].setAttribute('disabled','disabled');
			form.elements['dept'].setAttribute('disabled','disabled');
			form.elements['etc'].setAttribute('disabled','disabled');
			form.elements['hireDate'].setAttribute('disabled','disabled');
		}else if(btnSelect==''){
			//ボタン有効/無効切り替え
			form.elements['insert'].removeAttribute('disabled');
			form.elements['update'].removeAttribute('disabled');
			//入力欄有効/無効切り替え
			form.elements['userId'].setAttribute('disabled','disabled');
			form.elements['userName'].setAttribute('disabled','disabled');
			form.elements['password'].setAttribute('disabled','disabled');
			form.elements['passwordForCheck'].setAttribute('disabled','disabled');
			form.elements['dept'].setAttribute('disabled','disabled');
			form.elements['etc'].setAttribute('disabled','disabled');
			form.elements['hireDate'].setAttribute('disabled','disabled');
		}
	})
	function btnChange(atype) {
		if(atype=='insert'){
			if(btnSelect != 'insert'){
				form.elements['btnSelect'].value = 'insert';
				//値クリア
				form.elements['userId'].value='';
				form.elements['userName'].value='';
				form.elements['password'].value='';
				form.elements['passwordForCheck'].value='';
				form.elements['dept'].value='';
				form.elements['etc'].value='';
				form.elements['hireDate'].value='';
				//表示処理
				doExecute2('btnSelect');
			}
		}else if(atype=='update'){
			if(btnSelect != 'update'){
				form.elements['btnSelect'].value = 'update';
				//値クリア
				form.elements['userId'].value='';
				form.elements['userName'].value='';
				form.elements['password'].value='';
				form.elements['passwordForCheck'].value='';
				form.elements['dept'].value='';
				form.elements['etc'].value='';
				form.elements['hireDate'].value='';
				//表示処理
				doExecute2('btnSelect');
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