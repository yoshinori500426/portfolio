<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../header.jsp" %>
<%--
--%>
    <div class="box">
        <header>
            <div class="main">
               <%-- document.forms[1]; --%>
               <form action="Logout.action" method="post">
                   <input type="hidden" name="toAction">
                   <div class="row">
                       <h1 class="h2 col-xs-8">マスタ登録画面</h1>
                       <div class="col-xs-4 text-right"><a href="javascript:logout()" >ログアウト</a></div>
                   </div>
               </form>
            </div>
        </header>
	<hr>
        <div>
            <%-- document.forms[2]; --%>
            <form action="Main.action" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/productMaster.jsp')"> 品番マスタ</button></div>
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/customerMaster.jsp')">顧客先マスタ</button></div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/supplierMaster.jsp')">仕入先マスタ</button></div>
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/userMaster.jsp')">ユーザマスタ</button></div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6"></div>
                    <div class="col-xs-6"><button type="button" class="btn btn-success btn-block" onclick="doExecute2('main/menu.jsp')">メニュー</button></div>
                </div>
            </form>
        </div>
<%@ include file="../footer.jsp" %>