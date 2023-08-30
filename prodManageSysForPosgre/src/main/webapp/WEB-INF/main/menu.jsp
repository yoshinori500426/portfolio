<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                       <h1 class="h2 col-xs-8">メニュー画面</h1>
                       <div class="col-xs-4 text-right" ><a href="javascript:logout()" >ログアウト</a></div>
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
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/purchaseOrder.jsp')"> 受注処理</button></div>
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/order.jsp')"> 発注処理</button></div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/shipping.jsp')">出荷処理</button></div>
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/supplier.jsp')">納品処理</button></div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/purchaseOrderList.jsp')">受注一覧</button></div>
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/orderList.jsp')">発注一覧</button></div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/entryExitInfo.jsp')">入出庫処理<small>(出荷/納品以外の入出庫)</small></button></div>
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/stock.jsp')">在庫確認</button></div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/entryExitInfoList.jsp')">入出庫一覧</button></div>
                    <div class="col-xs-6"><button type="button" class="btn btn-primary btn-block" onclick="doExecute2('main/amountCalc.jsp')">所要量計算</button></div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-6"></div>
                    <div class="col-xs-6"><button type="button" class="btn btn-success btn-block" onclick="doExecute2('main/menuMaster.jsp')">マスタ登録</button></div>
                </div>
            </form>
        </div>
<%@ include file="../footer.jsp" %>