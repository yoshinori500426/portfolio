<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
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
            <form action="Main.action" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>ユーザ登録</h1>
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
            <form action="UserRegister.action" method="post">
	            <input type="hidden" name="toAction" value="${G_userRegister.toAction}">
	            <input type="hidden" name="inputState" value="${G_userRegister.inputState}">
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <label for="userID">ログインID&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-8 col-xs-12">
                        <p>：<input type="text" name="userID" style="width: 250px"  placeholder="任意の1文字以上"
                        value="${G_userRegister.userID}"  onChange="doExecute2('inputCheck')" > ${alart[0]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <label for="userName">ログイン名&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-8 col-xs-12">
                        <p>：<input type="text" name="userName" style="width: 250px"  placeholder="任意の1文字以上"
                        value="${G_userRegister.userName}"  onChange="doExecute2('inputCheck')" > ${alart[1]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <label for="password">パスワード&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-8 col-xs-12">
                        <p>：<input type="password" name="password" style="width: 250px"  placeholder="8文字以上、英数構成、大文字1以上"
                        value="${G_userRegister.password}" onChange="doExecute2('inputCheck')" > ${alart[2]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <label for="passwordForCheck">パスワード(確認)&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-8 col-xs-12">
                        <p>：<input type="password" name="passwordForCheck" style="width: 250px"  placeholder="パスワード(確認)"
                        value="${G_userRegister.passwordForCheck}" onChange="doExecute2('inputCheck')"  > ${alart[3]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <label for="etc">備考</label>
                    </div>
                    <div class="col-sm-8 col-xs-12">
                        <p>：<textarea rows="2" style="width: 250px" name="etc">${G_userRegisterLogin.etc}</textarea></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"  name="userUpdateInsert"
                                onclick="userUpdateInsertExecute()">登録/更新</button></p>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-danger btn-block"  name="userDelete"
                                onclick="userDeleteExecute()">削除</button></p>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>