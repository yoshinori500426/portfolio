<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>Production Management System</title>
<link type="text/css" rel="stylesheet" href="../css/bootstrap/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="../css/bootstrap/css/bootstrap-theme.min.css">
<link type="text/css" rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src="../js/jquery-3.6.4.min.js"></script>
<script>
function logout() {
	 var judge = window.confirm('ログアウトしますか? \nログアウトするとログイン画面に移動します。');
	 if(judge==true){
		 doExecute1();
	 }
}
// Actionクラストリガー用メソッド
//　→formタグの属性actionでActionクラスを指定し､
//　　リクエストパラメータに動作内容を指定する事で､複雑な動作を行わせる
function doExecute0(atype) {
	var form = document.forms[0];
   form.toAction.value = atype;
	form.submit();
}
function doExecute1(atype) {
	var form = document.forms[1];
   form.toAction.value = atype;
	form.submit();
}
function doExecute2(atype) {
	var form = document.forms[2];
   form.toAction.value = atype;
	form.submit();
}
function doExecute3(atype) {
	var form = document.forms[3];
   form.toAction.value = atype;
	form.submit();
}
</script>
</head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<div class="box">
		<div class="row">
			<div class="col-sm-8 col-xs-12 text-left">
				<p class="h1">在庫管理システム</p>
			</div>
			<div class="col-sm-4 col-xs-12 text-left">
				<c:choose>
					<c:when test="${nextJsp!='/WEB-INF/main/login.jsp'}">
							<p class="text-right">ようこそ、${loginState}さん</p>
							<%-- document.forms[0]; --%>
					</c:when>
				</c:choose>
				<form action="Main.action" method="post" class="text-right">
						<c:choose>
							<c:when test="${nextJsp!='/WEB-INF/main/login.jsp' && nextJsp!='/WEB-INF/main/menu.jsp'}">
										<input type="hidden" name="toAction" > <a
											href="javascript:doExecute0('main/menu.jsp')" >メニューに戻る</a>
							</c:when>
						</c:choose>
				</form>
			</div>
		</div>
	</div>