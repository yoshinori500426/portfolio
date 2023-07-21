<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%-- 
完成
動作確認済み

Alart
　無し

動作指示
　場合分けなし(遷移先ファイルのディレクトリ情報を渡すのみ)
--%>
<div class="box">
    <header>
        <div class="main">
<%-- document.forms[1]; --%>
            <form action="InputOutputScreen" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>メニュー</h1>
                    </div>
                    <div class="col-sm-4 col-xs-12 text-right">
                        <a href="javascript:logout()">販売画面へ</a>
                    </div>
                </div>
            </form>
        </div>
    </header>

    <hr>

    <div>
        <div>
<%-- document.forms[2]; --%>
            <form action="InputOutputScreen" method="post">
            <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="doExecute2('main/purchase.jsp')"> 仕入処理</button></p>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="doExecute2('main/productMaster.jsp')">商品マスタ</button></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="doExecute2('main/inventory.jsp')">棚卸処理</button></p>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="doExecute2('main/earnings.jsp')">売上処理</button></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="doExecute2('main/batch.jsp')">バッチ処理</button></p>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="doExecute2('main/setting.jsp')">設定</button></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-primary btn-block"
                                onclick="doExecute2('main/userRegister.jsp')">ユーザ登録</button></p>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                        <p><button type="button" class="btn btn-success btn-block"
                                onclick="logout()">販売画面へ戻る(ログアウト)</button></p>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>