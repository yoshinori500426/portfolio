<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>Production Management System</title>
<link type="text/css" rel="stylesheet" href="../css/bootstrap/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="../css/bootstrap/css/bootstrap-theme.min.css">
<link type="text/css" rel="stylesheet" href="../css/style.css">
</head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<div class="box">
		<div class="row">
			<p class="h1 col-xs-8 text-left">在庫管理システム</p>
			<div class="col-xs-4 text-left">
				<c:choose>
					<c:when test="${nextJsp!='/WEB-INF/main/login.jsp'}">
						<p class="text-right">${loginState}</p>
					</c:when>
				</c:choose>
				<%-- document.forms[0]; --%>
				<form action="Main.action" method="post" class="text-right">
					<input type="hidden" name="toAction">
					<c:choose>
						<c:when test="${nextJsp=='/WEB-INF/main/login.jsp' || nextJsp=='/WEB-INF/main/menu.jsp'}">
						</c:when>
						<c:when test="${nextJsp=='/WEB-INF/main/productMaster.jsp' || nextJsp=='/WEB-INF/main/customerMaster.jsp' || nextJsp=='/WEB-INF/main/supplierMaster.jsp' || nextJsp=='/WEB-INF/main/userMaster.jsp'}">
							 <a href="javascript:doExecute0('main/menuMaster.jsp')" >マスタ登録画面に戻る</a>
						</c:when>
						<c:otherwise>
							 <a href="javascript:doExecute0('main/menu.jsp')" >メニュー画面に戻る</a>
						</c:otherwise>
					</c:choose>
				</form>
			</div>
		</div>
	</div>