<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%--
完成
動作確認済み

Alart
　name="userID" -> ${alart[0]}
　name="password" -> ${alart[1]}

動作指示
　場合分けなし(ログインボタンのみ)
--%>
<div class="box">
    <header>
        <div class="main">
<%-- document.forms[1]; --%>
            <form action="Main.action" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>ログイン</h1>
                    </div>
                    <div class="col-sm-4 col-xs-12 text-right">
                        <a href="javascript:doExecute1('main/index.jsp')">販売画面へ</a>
                    </div>
                </div>
            </form>
        </div>
    </header>

    <hr>

    <div>
        <div>
<%-- document.forms[2]; --%>
            <form action="Login.action" method="post">
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="userID">ログイン名&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" name="userID" style="width: 200px" value="${us.userID}"> ${alart[0]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="password">パスワード&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="password" name="password" style="width: 200px"> ${alart[1]}</p>
                    </div>
                </div>
                <p><input type="submit" class="btn btn-primary btn-block" value="ログイン"></p>
            </form>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>