<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
完成
動作確認済み

JANコードが入力されたら、データベーステーブルの商品マスターにあるかをチェック
無ければエラーを出す。
個数のチェックは1以上
仕入単価のチェックは１以上。
確定ボタンでデータベーステーブルの在庫テーブルと、入庫テーブルを更新する。
キャンセルで画面をクリアーする。


Alart
　name="jan" -> ${alart[0]}
　name="pcs" -> ${alart[1]}
　name="price" -> ${alart[2]}

動作指示
　janCodeCheck
　pcsCheck
　priceCheck
　storingProcess
　cancel
--%>
<div class="box">
    <header>
        <div class="main">
<%-- document.forms[1]; --%>
            <form action="InputOutputScreen" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>仕入処理</h1>
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
            <form action="Purchase" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <label for="jan">JANコード&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-8 col-xs-12">
                        <p>：<input type="text" id="jan" name="jan" style="width: 200px" placeholder="13桁の整数(ハイフン不要)"
                        onChange="doExecute2('janCodeCheck')" value =
																    <c:choose>
																		<c:when test="${empty productDrink.janCode}">"${G_purchase.janCode}"</c:when>
																		<c:otherwise>"${productDrink.janCode}"</c:otherwise>
																	</c:choose>
                       												>
                       												${alart[0]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <p>商品名</p>
                    </div>
                    <div class="col-sm-8 col-xs-12">
                        <p>：${productDrink.name}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <p>メーカー</p>
                    </div>
                    <div class="col-sm-8 col-xs-12">
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
                        <p>在庫数</p>
                    </div>
                    <div class="col-xs-8">
                        <p>：${storeProduct.stockPCS}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <label for="pcs">個数&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-8 col-xs-12">
                        <p>：<input type="text" id="pcs" name="pcs" style="width: 120px" placeholder="個数"
                        onChange="doExecute2('pcsCheck')" value =
															    <c:choose>
																	<c:when test="${empty gr.nyukoPCS}">"${G_purchase.pcs}"</c:when>
																	<c:otherwise>"${gr.nyukoPCS}"</c:otherwise>
																</c:choose>
                      											>
                       											${alart[1]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <label for="price">仕入単価&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-8 col-xs-12">
                        <p>：<input type="text" id="price" name="price" style="width: 120px" placeholder="単価(1-999999)"
                        onChange="doExecute2('priceCheck')" value =
																    <c:choose>
																		<c:when test="${empty gr.unitPrice}">"${G_purchase.price}"</c:when>
																		<c:otherwise>"${gr.unitPrice}"</c:otherwise>
																	</c:choose>
                       												>
                           											${alart[2]}</p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-7 col-xs-12">
                    </div>
                    <div class="col-md-2 col-xs-12 text-right">
                        <a href="javascript:doExecute2('storingProcess')">確定</a>
                    </div>
                    <div class="col-md-3 col-xs-12 text-right">
                        <a href="javascript:doExecute2('cancel')">キャンセル</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <br>
    <br>
    <hr>
    <h1 class="h5">登録済みJanコード(動作確認用)</h1>
	<table class="table table-bordered table-hover">
		<thead class="thead-dark">
			<tr>
				<th scope="col">JANコード</th>
				<th scope="col">商品名</th>
			</tr>
		</thead>
		<c:forEach var="pdPF" items="${pdListPF}">
			<tbody>
				<tr>
					<td>${pdPF.janCode}</td>
					<td>${pdPF.name}</td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
</div>

<%@ include file="../footer.jsp" %>