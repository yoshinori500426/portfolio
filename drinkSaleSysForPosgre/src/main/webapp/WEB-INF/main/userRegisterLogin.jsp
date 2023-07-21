<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--

パスワードは正規表現でチェックする事。
（英字、大文字、小文字、数字で8文字以上）


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
            <form action="InputOutputScreen" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>ユーザ登録(パスワード再入力)</h1>
                    </div>
                    <div class="col-sm-4 col-xs-12 text-right">
                        <a href="javascript:doExecute1('main/menu.jsp')">メニュー画面へ</a>
                    </div>
                </div>
            </form>
        </div>
    </header>

    <hr>

    <div>
        <div>
<%-- document.forms[2]; --%>
            <form action="UserRegister" method="post">
	            <input type="hidden" name="userID" value="${G_userRegister.userID}">
	            <input type="hidden" name="userName" value="${G_userRegister.userName}">
	            <input type="hidden" name="password" value="${G_userRegister.password}">
	            <input type="hidden" name="passwordForCheck" value="${G_userRegister.passwordForCheck}">
	            <input type="hidden" name="etc" value="${G_userRegister.etc}">
	            <input type="hidden" name="inputState" value="${G_userRegister.inputState}">
	            <input type="hidden" name="toAction" value="execute_${G_userRegister.toAction}">
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="userID">ログイン名</label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：${G_userRegisterLogin.userID}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="passwordForRegister">パスワード&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="password" id="passwordForRegister" name="passwordForRegister" style="width: 200px"
                        value="${G_userRegisterLogin.password}"> ${alart[0]}</p>
                    </div>
                </div>
                <p><button type="button" class="btn btn-primary btn-block"  name="userUpdateInsertDelete"
                        onclick="doExecute2('execute_${G_userRegister.toAction}')">
							    <c:choose>
									<c:when test="${G_userRegister.toAction=='updateInsert'}">登録/更新</c:when>
									<c:when test="${G_userRegister.toAction=='delete'}">削除</c:when>
									<c:otherwise>不明</c:otherwise>
								</c:choose>
                        </button></p>
            </form>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>