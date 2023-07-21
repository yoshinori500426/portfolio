<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>Dring Sale System</title>
<link type="text/css" rel="stylesheet" href="css/bootstrap/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="css/bootstrap/css/bootstrap-theme.min.css">
<link type="text/css" rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery-3.6.4.min.js"></script>
<script>
window.addEventListener('load', function() {
	var scrollY=window.sessionStorage.getItem(['scrollY']);
	 if(scrollY!=null){
		scrollTo(0, scrollY);
	 }
    var btn = document.getElementById('logout');
	 if('${user.userID}'==''){
		 btn.style.visibility = 'hidden';
	 }else{
		 btn.style.visibility = 'none';
	 }
	 if('${state}'!=''){
		 alert('${state}');
	 }
	 if('${nextJsp}'=='/WEB-INF/main/sales.jsp' && '${process}'=='salesProcess'){
		 doExecute2('salesProcessAft');
	 }
	 if('${nextJsp}'=='/WEB-INF/main/purchase.jsp' && '${process}'=='storingProcess'){
		 doExecute2('storingProcessAft');
	 }
	 if('${nextJsp}'=='/WEB-INF/main/productMaster.jsp' && '${process}'=='productRecordProcess'){
		 doExecute2('productRecordProcessAft');
	 }
	 if(('${nextJsp}'=='/WEB-INF/main/userRegisterLogin.jsp'||'${nextJsp}'=='/WEB-INF/main/userRegister.jsp') && ('${process}'=='execute_updateInsert'||'${process}'=='execute_delete')){
		 doExecute2('executeAft');
	 }
	 if('${nextJsp}'=='/WEB-INF/main/productMaster.jsp'){
		 var form = document.forms[2];
		 if('${register}'==''){
		 	form.elements['register'].value = 'NG';
		 }else if('${register}'!=''){
		 	form.elements['register'].value = '${register}';
		 }
		 if('${register}'=='NG' || '${register}'==''){
			 for(i=3;i<form.elements.length;i++){
			 	form.elements[i].setAttribute('disabled','disabled');
			 }
		 }else if('${register}'=='OK'){
			 form.elements[2].setAttribute('disabled','disabled');
			 for(var i=3;i<form.elements.length;i++){
			 	form.elements[i].removeAttribute('disabled');
			 }
		 }
	 }
	 if('${nextJsp}'=='/WEB-INF/main/inventory.jsp'){
		if('${storeProductLists}'==''){
			var positionY = window.pageYOffset;
			window.sessionStorage.setItem(['scrollY'],[positionY]);
			var form = document.forms[2];
			form.submit();
		}
	 }
	 if('${nextJsp}'=='/WEB-INF/main/earnings.jsp'){
		 var year = document.forms[2].elements['year'];
		 var month = document.forms[2].elements['month'];
		 for(var i=0;i<year.length;i++){
			 if('${earningsEtc.year}' != '' && year.options[i].value == '${earningsEtc.year}'){
				 year.options[i].selected = true;
				 break;
			 }else if('${earningsEtc.year}' == ''){
				 year.options[0].selected = true;
				 break;
			 }
		}
		 for(var i=0;i<month.length;i++){
			 if('${earningsEtc.month}' != '' && month.options[i].value == '${earningsEtc.month}'){
				 month.options[i].selected = true;
				 break;
			 }else if('${earningsEtc.month}' == ''){
				 month.options[0].selected = true;
				 break;
			 }
		}
	}
	if('${nextJsp}'=='/WEB-INF/main/userRegister.jsp'){
		var form = document.forms[2];
		form.elements['userUpdateInsert'].setAttribute('disabled','disabled');
		form.elements['userDelete'].setAttribute('disabled','disabled');
		if('${G_userRegister.inputState}'=='1' || '${G_userRegister.inputState}'=='2'){
			form.elements['userUpdateInsert'].removeAttribute('disabled');
		}else if('${G_userRegister.inputState}'=='3'){
			form.elements['userDelete'].removeAttribute('disabled');
		}
	}
})
function logout() {
	 var judge = window.confirm('ログアウトしますか? \nログアウトすると販売画面に移動します。');
	 if(judge==true){
		 doExecute0();
	 }
}
<%--
｢'${nextJsp}'=='/WEB-INF/main/productMaster.jsp'｣で使用するメソッド
--%>
function activate() {
     var form = document.forms[2];
	 if(form.elements['register'].value == 'NG' || form.elements['register'].value == ''){
	 	var judge = window.confirm('編集しますか? \nJanコード以降が表示されている場合､内容が上書きされます｡\nJanコード以降が表示されていない場合､新規登録されます。\n｢編集｣押下で編集可/不可が切り替ります｡(編集不可では登録不可)');
	 	if(judge==true){
		 	form.elements['register'].value = 'OK';
		    form.toAction.value = 'janCodeCheckOnly';
			 for(i=3;i<form.elements.length;i++){
				 	form.elements[i].removeAttribute('disabled');
				 }
			var positionY = window.pageYOffset;
			window.sessionStorage.setItem(['scrollY'],[positionY]);
			form.submit();
	 	}
	 }else{
		 form.elements['register'].value = 'NG';
		 form.elements[2].removeAttribute('disabled');
		 for(i=3;i<form.elements.length;i++){
			 form.elements[i].setAttribute('disabled','disabled');
		 }
	 }
}
function userUpdateInsertExecute() {
	 var judge = window.confirm('ユーザを登録/更新しますか? \n「OK」を押下すると、ユーザ登録(パスワード再入力)画面に移動します。');
	 if(judge==true){
		 doExecute2('updateInsert');
	 }
}
function userDeleteExecute(){
	var judge = window.confirm('ユーザを削除しますか? \n「OK」を押下すると、ユーザ登録(パスワード再入力)画面に移動します。');
	if(judge==true){
		doExecute2('delete');
	}
}

 // Actionクラストリガー用メソッド
 //　→formタグの属性actionでActionクラスを指定し､
 //　　リクエストパラメータに動作内容を指定する事で､複雑な動作を行わせる
 function doExecute0(atype) {
	var positionY = window.pageYOffset;
	window.sessionStorage.setItem(['scrollY'],[positionY]);
	var form = document.forms[0];
    form.toAction.value = atype;
	form.submit();
 }
 function doExecute1(atype) {
	var positionY = window.pageYOffset;
	window.sessionStorage.setItem(['scrollY'],[positionY]);
	var form = document.forms[1];
    form.toAction.value = atype;
	form.submit();
 }
 function doExecute2(atype) {
	var positionY = window.pageYOffset;
	window.sessionStorage.setItem(['scrollY'],[positionY]);
	var form = document.forms[2];
	if('${nextJsp}'=='/WEB-INF/main/productMaster.jsp'){
		 form.elements[2].removeAttribute('disabled');
	 }
    form.toAction.value = atype;
	form.submit();
 }
 function doExecute3(atype) {
	var positionY = window.pageYOffset;
	window.sessionStorage.setItem(['scrollY'],[positionY]);
	var form = document.forms[3];
    form.toAction.value = atype;
	form.submit();
 }
</script>
</head>
<body>
    <div class="box">
        <div class="row">
            <div class="col-sm-8 col-xs-12 text-right">
                <p>${loginState}</p>
            </div>
            <div class="col-sm-4 col-xs-12 text-right">
<%-- document.forms[0]; --%>
                <form action="Logout" method="post">
                    <input type="hidden" name="toAction">
                    <a href="javascript:logout()" id="logout">ログアウト</a>
                </form>
            </div>
        </div>
    </div>