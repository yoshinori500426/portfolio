<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%--

--%>
	    <br>

        <div class="box">
            <header>
                <div class="main">
                    <%-- document.forms[1]; --%>
                        <form action="Logout.action" method="post">
                            <input type="hidden" name="toAction">
                            <div class="row">
                                <div class="col-sm-8 col-xs-12">
                                    <h1 class="h2">メニュー画面</h1>
                                </div>
                                <div class="col-sm-4 col-xs-12 text-right" >
                                    <a href="javascript:logout()" >ログアウト</a>
                                </div>
                            </div>
                        </form>
                </div>
            </header>

            <hr>

            <div>
                <div>
                    <%-- document.forms[2]; --%>
                        <form action="Main.action" method="post">
                            <input type="hidden" name="toAction">
                            <div class="row">
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/order.jsp')"> 受注処理</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/entryExitInfo.jsp')">入出庫処理</button></p>
                                </div>
                            </div>

                            <br>

                            <div class="row">
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/supplier.jsp')">納品処理</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/shipping.jsp')">出荷処理</button></p>
                                </div>
                            </div>

                            <br>

                            <div class="row">
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/stock.jsp')">在庫確認</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/purchaseOrderList.jsp')">受注一覧</button></p>
                                </div>
                            </div>

                            <br>

                            <div class="row">
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/orderList.jsp')">発注一覧</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/entryExitInfoList.jsp')">入出庫一覧</button></p>
                                </div>
                            </div>

                            <br>

                            <div class="row">
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/amountCalc.jsp')">所要量計算</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-success btn-block"
                                            onclick="doExecute2('main/menuMaster.jsp')">マスタ登録</button></p>
                                </div>
                            </div>
                        </form>
                </div>
            </div>

<%@ include file="../footer.jsp" %>