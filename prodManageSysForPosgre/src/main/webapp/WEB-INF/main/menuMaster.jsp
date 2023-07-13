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
                                    <h1 class="h2">マスタ登録画面</h1>
                                </div>
                                <div class="col-sm-4 col-xs-12 text-right">
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
                                            onclick="doExecute2('main/productMaster.jsp')"> 品番マスタ</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/customer.jsp')">顧客先マスタ</button></p>
                                </div>
                            </div>
                            <br>
                            <br>
                            <div class="row">
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/supplierMaster.jsp')">仕入先マスタ</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/user_y.jsp')">ユーザマスタ</button></p>
                                </div>
                            </div>
                        </form>
                </div>
            </div>

<%@ include file="../footer.jsp" %>