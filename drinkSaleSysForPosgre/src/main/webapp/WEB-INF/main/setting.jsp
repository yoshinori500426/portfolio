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
            <form action="InputOutputScreen" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>設定</h1>
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
            <form action="InputOutputScreen" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="salePrice">販売価格</label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" name="salePrice" style="width: 200px" placeholder="" value ="">
                        ${alart[0]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="purchasePrice">仕入単価</label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：
							<input class="form-check-input" type="radio" name="purchasePrice" value="1">運賃を含む
							<input class="form-check-input" type="radio" name="purchasePrice" value="2">含まない
                        ${alart[1]}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-xs-12">
                        <label for="printer">プリンター</label>
                    </div>
                    <div class="col-sm-7 col-xs-12">
                        <p>：<input type="text" name="printer" style="width: 200px" placeholder="" value ="">
                        ${alart[2]}</p>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <br>
    <hr>
    <h1 class="h5">この画面は､表示のみです｡(動作はしません｡)</h1>
</div>

<%@ include file="../footer.jsp" %>