<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
完成
動作確認済み

JANコードが入力されたら、データベーステーブルの商品マスターにあるかをチェック
無ければエラーを出す。
個数を入力すると、請求単価を表示する。
確定が押されると、個数が0以上がどうかをチェックして、在庫テーブル、出庫テーブルを更新して
販売画面を再度表示。　キャンセルは画面の入力内容をすべてクリアする。

Alart
　name="jan" -> ${alart[0]}
　name="pcs" -> ${alart[1]}

動作指示
　janCodeCheck
　calcPrice
　salesProcess
　cancel
--%>
<div class="box">
    <header>
        <div class="main">
<%-- document.forms[1]; --%>
            <form action="Main.action" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>販売</h1>
                    </div>
                    <div class="col-sm-4 col-xs-12 text-right">
                        <a href="javascript:doExecute1('main/login.jsp')">メニュー画面へ</a>
                    </div>
                </div>
            </form>
        </div>
    </header>
    <hr>

    <div>
        <div>
            <div class="row">
                <div class="col-sm-8 col-xs-12">
                    <h1>飲料水販売</h1>
                </div>
                <div class="col-sm-4 col-xs-12">
                </div>
            </div>
            <div>
                <p>いらっしゃいませ。<br>冷蔵庫より商品を取り出し
                    JANコードを入力またはバーコードリーダーで読込み
                    商品名が出ましたら個数を選択してください。<br>
                    請求金額を投入後、確定ボタンをクリックしてください。</p>
            </div>
        </div>
    </div>

    <hr>

    <div>
        <div>
<%-- document.forms[2]; --%>
            <form action="Index.action" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-xs-4">
                        <label for="jan">JANコード&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-xs-8">
                        <p>：<input type="text" name="jan" style="width: 200px" placeholder="13桁の整数(ハイフン不要)"
                        onChange="doExecute2('janCodeCheck')"  value =
																	    <c:choose>
																			<c:when test="${empty productDrink.janCode}">"${G_index.janCode}"</c:when>
																			<c:otherwise>"${productDrink.janCode}"</c:otherwise>
																		</c:choose>
                        												>
                        												${alart[0]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <p>商品名</p>
                    </div>
                    <div class="col-xs-8">
                        <p>：${productDrink.name}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <p>メーカー</p>
                    </div>
                    <div class="col-xs-8">
                        <p>：${productDrink.maker}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <p>内容量</p>
                    </div>
                    <div class="col-xs-8">
                        <p>：${productDrink.contents}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <p>単位</p>
                    </div>
                    <div class="col-xs-8">
                        <p>：${productDrink.unit}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <p>分類</p>
                    </div>
                    <div class="col-xs-8">
                        <p>：${productDrink.dept}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <p>単価(税込み)</p>
                    </div>
                    <div class="col-xs-8">
                        <p>：${productDrink.unitPrice}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <p>在庫数</p>
                    </div>
                    <div class="col-xs-8">
                        <p>：${storeProduct.stockPCS}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <label for="pcs">個数&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-xs-3">
                        <p>：<input type="text" name="pcs" style="width: 50px" placeholder="個数"
                        onChange="doExecute2('calcPrice')" value ="${G_index.pcs}">
                        											${alart[1]}</p>
                    </div>
                    <div class="col-xs-5">
                        <p class="text-right">${empty totalPrice?'':'請求金額：'+=totalPrice+='円'}</p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-xs-7">
                    </div>
                    <div class="col-xs-2 text-right">
                        <a href="javascript:doExecute2('salesProcess')">確定</a>
                    </div>
                    <div class="col-xs-3 text-right">
                        <a href="javascript:doExecute2('cancel')">キャンセル</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>