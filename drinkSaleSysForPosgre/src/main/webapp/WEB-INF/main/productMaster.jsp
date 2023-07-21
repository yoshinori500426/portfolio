<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
完成
動作確認済み

JANコードが入力されたら、データベーステーブルの商品テーブルを参照して、あれば、商品名、メーカ名を表示
無ければ、「新規登録ですか？」と表示を行い、OKなら、商品名、メーカを入力させる。
確定では、新規登録の場合、商品テーブル、在庫テーブルに追加。　
キャンセルは

inputタグの｢disabled="disabled"｣属性を使用し､
textボックスの編集可/不可を切替える

Alart
　name="jan" -> ${alart[0]}
　name="pcs" -> ${alart[1]}
　name="maker" -> ${alart[2]}
　name="contents" -> ${alart[3]}
　name="dept" -> ${alart[4]}
　name="unit" -> ${alart[5]}
　name="price" -> ${alart[6]}

動作指示
  janCodeCheck
  nameCheck
  makerCheck
  contentsCheck
  deptCheck
  unitCheck
  priceCheck
　productRecordProcess
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
                        <h1>商品マスタ</h1>
                    </div>
                    <div class="col-sm-4 col-xs-12 text-right">
                        <p><a href="javascript:doExecute1('main/menu.jsp')">メニュー画面へ</a></p>
                    </div>
                </div>
            </form>
        </div>
    </header>

    <hr>

    <div>
        <div>
<%-- document.forms[2]; --%>
            <form action="ProductMaster" method="post">
                <input type="hidden" name="toAction">
                <input type="hidden" name="register">
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="jan">JANコード&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" id="jan" name="jan" style="width: 200px" placeholder="13桁の整数(ハイフン不要)"
                        onChange="doExecute2('janCodeCheck')" value =
																    <c:choose>
																		<c:when test="${register=='NG' and !empty productDrink.janCode}">"${productDrink.janCode}"</c:when>
																		<c:when test="${register=='NG' and empty productDrink.janCode}">"${G_productMaster.janCode}"</c:when>
																		<c:when test="${register=='OK'}">"${G_productMaster.janCode}"</c:when>
																	</c:choose>
											                        >
											                        ${alart[0]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="name">商品名&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" id="name" name="name" style="width: 200px" placeholder="商品名(1-100文字)"
                        onChange="doExecute2('nameCheck')" value =
																	<c:choose>
																		<c:when test="${register=='NG'}">"${productDrink.name}"</c:when>
																		<c:when test="${register=='OK'}">"${G_productMaster.name}"</c:when>
																	</c:choose>
                       												>
                       												${alart[1]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="maker">メーカー&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" id="maker" name="maker" style="width: 200px" placeholder="メーカ名(1-50文字)"
                        onChange="doExecute2('makerCheck')" value =
																	<c:choose>
																		<c:when test="${register=='NG'}">"${productDrink.maker}"</c:when>
																		<c:when test="${register=='OK'}">"${G_productMaster.maker}"</c:when>
																	</c:choose>
                       												>
                       												${alart[2]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="contents">内容量&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" id="contents" name="contents" style="width: 150px" placeholder="内容量(1-99999)"
                        onChange="doExecute2('contentsCheck')" value =
																	<c:choose>
																		<c:when test="${register=='NG'}">"${productDrink.contents}"</c:when>
																		<c:when test="${register=='OK'}">"${G_productMaster.contents}"</c:when>
																	</c:choose>
                       												>
                       												${alart[3]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="dept">分類&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" id="dept" name="dept" style="width: 150px" placeholder="分類(1-50文字)"
                        onChange="doExecute2('deptCheck')" value =
																	<c:choose>
																		<c:when test="${register=='NG'}">"${productDrink.dept}"</c:when>
																		<c:when test="${register=='OK'}">"${G_productMaster.dept}"</c:when>
																	</c:choose>
                       												>
                       												${alart[4]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="unit">単位&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" id="unit" name="unit" style="width: 150px" placeholder="単位(mlなど､1-5文字)"
                        onChange="doExecute2('unitCheck')" value =
																	<c:choose>
																		<c:when test="${register=='NG'}">"${productDrink.unit}"</c:when>
																		<c:when test="${register=='OK'}">"${G_productMaster.unit}"</c:when>
																	</c:choose>
                       												>
                       												${alart[5]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="price">標準単価&nbsp;<span class="label label-danger">必須</span></label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" id="price" name="price" style="width: 150px" placeholder="単価(1-9999)"
                        onChange="doExecute2('priceCheck')" value =
                        											<c:choose>
																		<c:when test="${register=='NG'}">"${productDrink.unitPrice}"</c:when>
																		<c:when test="${register=='OK'}">"${G_productMaster.price}"</c:when>
																	</c:choose>
                       												>
                       												${alart[6]}</p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-5 col-xs-12">
                    </div>
                    <div class="col-md-2 col-xs-12 text-right">
                        <a href="javascript:doExecute2('productRecordProcess')">確定</a>
                    </div>
                    <div class="col-md-2 col-xs-12 text-right">
                        <a href="javascript:activate()">編集</a>
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