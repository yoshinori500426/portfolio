<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../header.jsp" %>
<%--
--%>
	<div class="box">
		<header>
			<div class="main">
				<%-- document.forms[1]; --%>
				<form action="Logout.action" method="post">
					<input type="hidden" name="toAction">
					<div class="row">
						<h1 class="h2 col-xs-6">受注画面</h1>
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
								<c:when test="${btnSelect=='delete'}">
									<span class="label btn-danger">状態：削除</span>
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
			<form action="PurchaseOrder.action" method="post">
			    <input type="hidden" name="toAction" data-changeDisabled="0">
				<input type="hidden" name="btnSelect" data-changeDisabled="0" value="${btnSelect}" >
				<input type="hidden" name="finFlg" data-changeDisabled="0" value="${G_PurchaseOrder.finFlg}" >
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-4"><button type="button" class="btn btn-warning btn-block" name="insert" data-changeDisabled="1" onClick="btnChange('insert')">登録</button></div>
					<div class="col-xs-4"><button type="button" class="btn btn-success btn-block" name="update" data-changeDisabled="2" onClick="btnChange('update')">更新</button></div>
					<div class="col-xs-2"><button type="button" class="btn btn-danger btn-block" name="delete" data-changeDisabled="3" onClick="btnChange('delete')">削除</button></div>
					<div class="col-xs-1"></div>
				</div>
	<hr>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="poNo">受注番号</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" list="poNoList" name="poNo" id="poNo" data-changeDisabled="4" 
											maxlength="8" onkeydown="javascript:this.value=(this.value.substr(0,3)=='PO-'&&this.value.substr(3).match(/^[0-9]*$/))?this.value.substr(3).slice(0, 5):(this.value.substr(0,3)!='PO-'&&this.value.match(/^[0-9]*$/))?this.value.slice(0, 5):'';befValue=this.value;"
											onchange="javascript: this.value = (this.value==0 || this.value=='')?'':(this.value.match(/^[0-9]*$/))?('PO-'+('00000'+this.value).slice(-5)):('PO-'+('00000'+befValue).slice(-5));doExecute2('searchPoNo');"
											placeholder="'PO-'に続く数字(最大5桁)" value="${G_PurchaseOrder.poNo}">
											<datalist id="poNoList">
												<c:forEach var="pol" items="${PurchaseOrderList}" >
													<option value="${fn:replace(pol.poNo,'PO-','')}" label="受注番号:${pol.poNo}, 数量:${pol.orderQty}, 納期:${fn:replace(pol.deliveryDate,'-','/')}">
												</c:forEach>
											</datalist></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="customerNo">顧客コード&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" list="customerNoList" name="customerNo" id="customerNo" class="inputRequired" data-changeDisabled="5" 
											maxlength="5" onkeydown="befValue=this.value;" 
											onkeyup="this.value=(befValue==''&&this.value.match(/^[A-Za-z]+$/))?this.value.toUpperCase():(befValue!=''&&(this.value.match(/^[A-Za-z][0-9]*$/)||this.value==''))?this.value:befValue;" 
											onchange="this.value=(this.value.substr(0,1).match(/^[A-Za-z]+$/)&&this.value.substr(1)==0)?this.value.substr(0,1):(this.value.substr(0,1).match(/^[A-Za-z]+$/)&&this.value.substr(1)!='')?this.value.substr(0,1)+('0000'+this.value.substr(1)).slice(-4):this.value;doExecute2('searchCustomerNo');" 
											placeholder="5文字(例:A0100)" value="${G_PurchaseOrder.customerNo}">
											<datalist id="customerNoList">
												<c:forEach var="cml" items="${CustomerMasterList}" >
													<option value="${cml.customerNo}" label="顧客コード:${cml.customerNo}, 会社名:${cml.customerName}, 支店名:${cml.branchName}">
												</c:forEach>
											</datalist></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="customerName">顧客名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="customerName" id="customerName" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${CustomerMaster.customerName}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productNo">品番&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" list="productNoList" name="productNo" id="productNo" class="inputRequired" data-changeDisabled="5" 
											min="0" max="9999999999" onkeyup="javascript: this.value = this.value.slice(0, 10);" 
											onchange="javascript: this.value = this.value==0?'':('0000000000'+this.value).slice(-10);doExecute2('searchProductNo');" 
											placeholder="10桁数字" value="${G_PurchaseOrder.productNo}">
											<datalist id="productNoList">
												<c:forEach var="pml" items="${ProductMasterList}" >
													<option value="${pml.productNo}" label="品番:${pml.productNo}, 品名:${pml.productName}">
												</c:forEach>
											</datalist></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productName">品名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="productName" id="productName" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${ProductMaster.productName}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="orderDate">受注日&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="date" style="width: 300px;" name="orderDate" id="orderDate" class="inputRequired" data-changeDisabled="5"
											 onchange="docheck();" value="${G_PurchaseOrder.orderDate}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="orderQty">受注数量&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="orderQty" id="orderQty" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											min="1" max="99999999" onkeyup="javascript: this.value = this.value.slice(0, 8);" onchange="docheck();" 
											placeholder="1-99999999" value="${G_PurchaseOrder.orderQty}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="deliveryDate">納期&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="date" style="width: 300px;" name="deliveryDate" id="deliveryDate" class="inputRequired" data-changeDisabled="5"
											onchange="docheck();" value="${G_PurchaseOrder.deliveryDate}"></p>
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
								<c:when test="${btnSelect=='delete'}">
										<button type="button" class="btn btn-danger btn-block" data-changeDisabled="0" name="doExecuteBTN" onClick="doExecute2('doBTNExecute')" disabled>削除</button>
								</c:when>
						</c:choose>
					</div>
					<div class="col-xs-5"><button type="button" class="btn btn-primary btn-block" data-changeDisabled="0" onClick="doExecute2('cancel')">リセット</button></div>
					<div class="col-xs-1"></div>
				</div>
			</form>
		</div>
<%@ include file="../footer.jsp"%>