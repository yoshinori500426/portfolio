<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
完成
動作確認済み

全商品の在庫数を表示させる。
入力項目なし

テーブルの出力には､DBの複数テーブルの結合が必要
また､出力用のbeanが必要

Alart
　無し

動作指示
　無し
--%>
<div class="box">
    <header>
        <div class="main">
<%-- document.forms[1]; --%>
            <form action="Main.action" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>棚卸確認</h1>
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
            <form action="Inventory.action" method="post">
                <input type="hidden" name="toAction">
                <table class="table table-bordered table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">JANコード</th>
                            <th scope="col">商品名</th>
                            <th scope="col">メーカ</th>
                            <th scope="col">在庫数</th>
                            <th scope="col">仕入単価</th>
                        </tr>
                    </thead>
	                <c:forEach var="storeProduct" items="${storeProductLists}">
	                    <tbody>
	                        <tr>
								<td>${storeProduct.janCode}</td>
								<td>${storeProduct.name}</td>
								<td>${storeProduct.maker}</td>
								<td>${storeProduct.stockPCS}</td>
								<td>${storeProduct.price}</td>
	                        </tr>
	                    </tbody>
	                </c:forEach>
                </table>
            </form>
        </div>
    </div>

    <hr>
    <div class="row">
        <div class="col-md-9 col-xs-12">
        </div>
        <div class="col-md-3 col-xs-12 text-right">
            <a href="javascript:doExecute2('cancel')">印刷</a>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>