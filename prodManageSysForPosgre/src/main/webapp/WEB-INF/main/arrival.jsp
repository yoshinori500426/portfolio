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
						<h1 class="h2 col-xs-6">入荷画面</h1>
						<h1 class="h3 col-xs-4 text-left">
							<c:choose>
								<c:when test="${empty btnSelect}">
									<span class="label label-primary">状態：未選択</span>
								</c:when>
								<c:when test="${btnSelect=='update'}">
									<span class="label label-success">状態：入荷登録</span>
								</c:when>
								<c:when test="${btnSelect=='delete'}">
									<span class="label btn-danger">状態：入荷取消</span>
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
			<form action="Arrival.action" method="post">
			    <input type="hidden" name="toAction" data-changeDisabled="0">
				<input type="hidden" name="btnSelect" data-changeDisabled="0" value="${btnSelect}" >
				<input type="hidden" name="finFlg" data-changeDisabled="0" value="${G_Arrival.finFlg}" >
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5"><button type="button" class="btn btn-success btn-block" name="update" data-changeDisabled="2" onClick="btnChange('update')">入荷登録</button></div>
					<div class="col-xs-5"><button type="button" class="btn btn-danger btn-block" name="delete" data-changeDisabled="3" onClick="btnChange('delete')">入荷取消</button></div>
					<div class="col-xs-1"></div>
				</div>
	<hr>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="orderNo">発注番号</label>
					<c:choose>
						<c:when test="${empty btnSelect || btnSelect=='update'}">
							<p class="col-xs-7">：<input type="text" style="width: 300px;" list="orderNoList" name="orderNo" id="orderNo" data-changeDisabled="4" 
													maxlength="13" onkeydown="javascript:this.value=(this.value.substr(0,3)=='OD-'&&this.value.substr(3).match(/^[0-9\-]*$/))?this.value.substr(3).slice(0, 10):(this.value.substr(0,3)!='PO-'&&this.value.match(/^[0-9\-]*$/))?this.value.slice(0, 10):'';befValue=this.value;"
													onchange="javascript: this.value = (this.value.substr(5)==0 || this.value.substr(5)=='' || this.value.substr(2,2)>12 || this.value.substr(2,2)<1)?'':(this.value.match(/^[0-9]{4}[-][0-9]*$/))?('OD-'+this.value.substr(0,5)+('00000'+this.value.substr(5)).slice(-5)):'';doExecute2('searchOrderNo');"
													placeholder="'OD-'に続く､数字(4桁)+'-'+数字(最大5桁)" value="${G_Arrival.orderNo}">
													<datalist id="orderNoList">
														<c:forEach var="otl" items="${OrderTableList}" >
															<option value="${fn:replace(otl.orderNo,'OD-','')}" label="発注番号:${otl.orderNo}, 数量:${otl.orderQty}, 納期:${fn:replace(otl.deliveryDate,'-','/')}">
														</c:forEach>
													</datalist></p>
						</c:when>
						<c:when test="${btnSelect=='delete'}">
							<p class="col-xs-7">：<input type="text" style="width: 300px;" list="orderNoList" name="orderNo" id="orderNo" data-changeDisabled="4" 
													maxlength="13" onkeydown="javascript:this.value=(this.value.substr(0,3)=='OD-'&&this.value.substr(3).match(/^[0-9\-]*$/))?this.value.substr(3).slice(0, 10):(this.value.substr(0,3)!='PO-'&&this.value.match(/^[0-9\-]*$/))?this.value.slice(0, 10):'';befValue=this.value;"
													onchange="javascript: this.value = (this.value.substr(5)==0 || this.value.substr(5)=='' || this.value.substr(2,2)>12 || this.value.substr(2,2)<1)?'':(this.value.match(/^[0-9]{4}[-][0-9]*$/))?('OD-'+this.value.substr(0,5)+('00000'+this.value.substr(5)).slice(-5)):'';doExecute2('searchOrderNo');"
													placeholder="'OD-'に続く､数字(4桁)+'-'+数字(最大5桁)" value="${G_Arrival.orderNo}">
													<datalist id="orderNoList">
														<c:forEach var="otl" items="${OrderTableListFinFlg0}" >
															<option value="${fn:replace(otl.orderNo,'OD-','')}" label="発注番号:${otl.orderNo}, 数量:${otl.orderQty}, 入荷日:${fn:replace(otl.dueDate,'-','/')}">
														</c:forEach>
													</datalist></p>
						</c:when>
					</c:choose>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productNo">品番</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="productNo" id="productNo" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${G_Arrival.productNo}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productName">品名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="productName" id="productName" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${ProductMaster.productName}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="supplierNo">仕入先コード</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="supplierNo" id="supplierNo" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${SupplierMaster.supplierNo}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="supplierName">仕入先名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="supplierName" id="supplierName" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${SupplierMaster.supplierName}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="orderDate">発注日</label>
					<p class="col-xs-7">：<input type="date" style="width: 300px;" name="orderDate" id="orderDate" data-changeDisabled="0"
											placeholder="表示のみ(入力不可)" value="${G_Arrival.orderDate}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="lot">購買ロット</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="lot" id="lot" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${ProductMaster.lot}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="orderLot">発注ロット</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="orderLot" id="orderLot" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${G_Arrival.orderLot}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="orderQty">発注数量</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="orderQty" id="orderQty" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${G_Arrival.orderQty}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="leadTime">購買リードタイム</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="leadTime" id="leadTime" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${ProductMaster.leadTime}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="deliveryDate">納期</label>
					<p class="col-xs-7">：<input type="date" style="width: 300px;" name="deliveryDate" id="deliveryDate" data-changeDisabled="0"
											placeholder="表示のみ(入力不可)" value="${G_Arrival.deliveryDate}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="dueQty">入荷数量</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="dueQty" id="dueQty" data-changeDisabled="0"
											placeholder="表示のみ(入力不可)" value="${G_Arrival.dueQty}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="dueDate">入荷日&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="date" style="width: 300px;" name="dueDate" id="dueDate" class="inputRequired" data-changeDisabled="5"
											onchange="docheck();" value="${G_Arrival.dueDate}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="biko">備考</label>
					<p class="col-xs-7">：<textarea rows="2" style="width: 300px;" name="biko" id="biko" data-changeDisabled="5" 
											placeholder="0−120文字">${G_Arrival.biko}</textarea></p>
				</div>
	<hr>
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5">
						<c:choose>
								<c:when test="${empty btnSelect}">
										<button type="button" class="btn btn-primary btn-block" data-changeDisabled="0" disabled>未選択</button>
								</c:when>
								<c:when test="${btnSelect=='update'}">
										<button type="button" class="btn btn-success btn-block" data-changeDisabled="0" name="doExecuteBTN" onClick="doExecute2('doBTNExecute')" disabled>入荷登録</button>
								</c:when>
								<c:when test="${btnSelect=='delete'}">
										<button type="button" class="btn btn-danger btn-block" data-changeDisabled="0" name="doExecuteBTN" onClick="doExecute2('doBTNExecute')" disabled>入荷取消</button>
								</c:when>
						</c:choose>
					</div>
					<div class="col-xs-5"><button type="button" class="btn btn-primary btn-block" data-changeDisabled="0" onClick="doExecute2('cancel')">リセット</button></div>
					<div class="col-xs-1"></div>
				</div>
			</form>
		</div>
	<hr>
		<div class="row">
			<div class="col-xs-1"></div>
			<div class="col-xs-10">
			    <h1 class="h5">登録済み発注レコード(動作確認用)</h1>
				<table class="table table-bordered table-hover">
					<thead class="thead-dark">
						<tr>
							<th scope="col">発注番号</th>
							<th scope="col">仕入先コード</th>
							<th scope="col">品番</th>
							<th scope="col">発注数量</th>
							<th scope="col">納期</th>
							<th scope="col">状態</th>
						</tr>
					</thead>
					<c:forEach var="otPF" items="${OtListPF}">
						<tbody>
							<tr>
								<td>${otPF.orderNo}</td>
								<td>${otPF.supplierNo}</td>
								<td>${otPF.productNo}</td>
								<td class="text-right">${otPF.orderQty}</td>
								<td>${otPF.deliveryDate}</td>
								<td>${otPF.finFlg=='1'?'入荷済':'未入荷'}</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
			<div class="col-xs-1"></div>
<%@ include file="../footer.jsp"%>