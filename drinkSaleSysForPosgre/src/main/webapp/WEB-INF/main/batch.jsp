<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%-- 
表示のみ
--%>
<div class="box">
    <header>
        <div class="main">
<%-- document.forms[1]; --%>
            <form action="Main.action" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>バッチ処理</h1>
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
            <form action="Main.action" method="post">
            <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="#"> 年次処理</button></p>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="#">年間更新</button></p>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="#">在庫棚卸修正</button></p>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>