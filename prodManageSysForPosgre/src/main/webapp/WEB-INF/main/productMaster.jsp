<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.jsp"%>
<%--
--%>
	<div class="box">
		<header>
			<div class="main">
				<%-- document.forms[1]; --%>
				<form action="Logout.action" method="post">
					<input type="hidden" name="toAction">
					<div class="row">
						<h1 class="h2 col-xs-6">品番マスタ画面</h1>
						<h1 class="h3 col-xs-4 text-left">
							<c:choose>
								<c:when test="${empty btnSelect}">
									<span class="label label-primary">状態：未選択</span>
								</c:when>
								<c:when test="${btnSelect=='insert'}">
									<span class="label label-warning">状態：登録</span>
								</c:when>
								<c:when test="${btnSelect=='update'}">
									<span class="label label-success">状態：更新</span>
								</c:when>
							</c:choose>
						</h1>
						<div class="col-xs-2"><a href="javascript:logout()"></a></div>
					</div>
				</form>
			</div>
		</header>
	<hr>
		<div>
			<%-- document.forms[2]; --%>
			<form action="ProductMaster.action" method="post">
			    <input type="hidden" name="toAction" data-changeDisabled="0">
				<input type="hidden" name="btnSelect" data-changeDisabled="0" value="${btnSelect}" >
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5"><button type="button" class="btn btn-warning btn-block" name="insert" data-changeDisabled="1" onClick="btnChange('insert')">登録</button></div>
					<div class="col-xs-5"><button type="button" class="btn btn-success btn-block" name="update" data-changeDisabled="2" onClick="btnChange('update')">更新</button></div>
					<div class="col-xs-1"></div>
				</div>
	<hr>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productNo">品番</label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="productNo" id="productNo" data-changeDisabled="4" 
											min="0" max="9999999999" onkeyup="javascript: this.value = this.value.slice(0, 10);" 
											onchange="javascript: this.value = this.value==0?'':('0000000000'+this.value).slice(-10);doExecute2('searchProductNo');" 
											placeholder="10桁数字" value="${G_ProductMaster.productNo}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productName">品名&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text"  style="width: 300px;"name="productName" id="productName" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											maxlength="100" onchange="docheck();" placeholder="1−100文字" value="${G_ProductMaster.productName}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="supplierNo">仕入先コード&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="supplierNo" id="supplierNo" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											min="0" max="999999" onkeyup="javascript: this.value = this.value.slice(0, 6);" 
											onchange="javascript: this.value = this.value==0?'':('000000'+this.value).slice(-6);doExecute2('searchSupplierNo');" 
											placeholder="6桁数字" value="${G_ProductMaster.supplierNo}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="supplierName">仕入先名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="supplierName" id="supplierName" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${SupplierMaster.supplierName}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="unitPrice">仕入単価&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="unitPrice" id="unitPrice" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											onkeydown="befValue=value;" onkeyup="value=((value<=999999.99)&&(value*100%1===0))?value:befValue;" 
											onchange="value=(value=='')?'':(value==0)?0.01:value;docheck();" 
											placeholder="0.01-999999.99" value="${G_ProductMaster.unitPrice}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="sellingPrice">売価</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="sellingPrice" id="sellingPrice" data-changeDisabled="5" 
											onkeydown="befValue=value;" onkeyup="value=((value<=999999.99)&&(value*100%1===0))?value:befValue;" 
											onchange="value=(value=='')?'':(value==0)?0.01:value;" 
											placeholder="0.01-999999.99(入力任意)" value="${G_ProductMaster.sellingPrice}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="leadTime">購買リードタイム&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="leadTime" id="leadTime" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											min="0" max="999" onkeyup="javascript: this.value = this.value.slice(0, 3);" onchange="docheck();" 
											placeholder="1-999" value="${G_ProductMaster.leadTime}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="lot">購買ロット&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="lot" id="lot" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											min="0" max="999999" onkeyup="javascript: this.value = this.value.slice(0, 6);" onchange="docheck();" 
											placeholder="1-999999" value="${G_ProductMaster.lot}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="location">在庫ロケーション&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="location" id="location" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											maxlength="6" onchange="docheck();" placeholder="1-6文字(例:B-01-3)" value="${G_ProductMaster.location}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="baseStock">基本在庫&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="baseStock" id="baseStock" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											min="0" max="999999" onkeyup="javascript: this.value = this.value.slice(0, 6);" onchange="docheck();" 
											placeholder="1-999999" value="${G_ProductMaster.baseStock}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="etc">備考</label>
					<p class="col-xs-7">：<textarea rows="2" style="width: 300px;" name="etc" id="etc" data-changeDisabled="5" 
											placeholder="0−120文字">${G_ProductMaster.etc}</textarea></p>
				</div>
	<hr>
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5">
						<c:choose>
								<c:when test="${empty btnSelect}">
										<button type="button" class="btn btn-primary btn-block" data-changeDisabled="0" disabled>未選択</button>
								</c:when>
								<c:when test="${btnSelect=='insert'}">
										<button type="button" class="btn btn-warning btn-block" data-changeDisabled="0" name="doExecuteBTN" onClick="doExecute2('doBTNExecute')" disabled>登録</button>
								</c:when>
								<c:when test="${btnSelect=='update'}">
										<button type="button" class="btn btn-success btn-block" data-changeDisabled="0" name="doExecuteBTN" onClick="doExecute2('doBTNExecute')" disabled>更新</button>
								</c:when>
						</c:choose>
					</div>
					<div class="col-xs-5"><button type="button" class="btn btn-primary btn-block" data-changeDisabled="0" onClick="doExecute2('cancel')">リセット</button></div>
					<div class="col-xs-1"></div>
				</div>
			</form>
		</div>
<%@ include file="../footer.jsp"%>