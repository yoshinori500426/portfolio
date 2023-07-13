<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%--

--%>
        <div class="box">
            <div class="row">
                <div class="col-sm-8 col-xs-12 text-left">
                    <p>ようこそ${loginState}さん。</p>
                </div>
                <div class="col-sm-4 col-xs-12 text-right">
                    <%-- document.forms[0]; --%>
                        <form action="Main.action" method="post">
                            <input type="hidden" name="toAction">
                            <a href="javascript:doExecute0('main/menu.jsp')">メニューに戻る</a>
                        </form>
                </div>
            </div>
        </div>

	    <br>

        <div class="box">
            <header>
                <div class="main">
                    <%-- document.forms[1]; --%>
                        <form action="Logout.action" method="post">
                            <input type="hidden" name="toAction">
                            <div class="row">
                                <div class="col-sm-8 col-xs-12">
                                    <h1>マスタ登録画面</h1>
                                </div>
                                <div class="col-sm-4 col-xs-12 text-right">
                                    <a href="javascript:logout()" ></a>
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
                                            onclick="doExecute2('main/product.jsp')"> 品番マスタ</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/coustomer.jsp')">顧客先マスタ</button></p>
                                </div>
                            </div>
                            <br>
                            <br>
                            <div class="row">
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/supplier.jsp')">仕入先マスタ</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute2('main/user.jsp')">ユーザマスタ</button></p>
                                </div>
                            </div>
                        </form>
                </div>
            </div>

<%@ include file="../footer.jsp" %>