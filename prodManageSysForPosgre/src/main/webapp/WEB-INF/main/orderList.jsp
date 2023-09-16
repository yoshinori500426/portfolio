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
						<h1 class="h2 col-xs-6">発注一覧画面</h1>
						<h1 class="h3 col-xs-4 text-left"></h1>
						<div class="col-xs-2"><a href="javascript:logout()"></a></div>
					</div>
				</form>
			</div>
		</header>
	<hr>
		<div>
			<%-- document.forms[2]; --%>
			<form action="OrderList.action" method="post">
			    <input type="hidden" name="toAction" data-changeDisabled="0">
			    <%-- 切り替えボタンは存在しないが､｢update｣と同様の動作を行わせる為､値｢update｣を固定値とする --%>
				<input type="hidden" name="btnSelect" data-changeDisabled="0" value="update" >
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productNo">品番&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" list="productNoList" name="productNo" id="productNo" class="inputRequired" data-changeDisabled="4" 
											min="0" max="9999999999" onkeyup="javascript: this.value = this.value.slice(0, 10);" 
											onchange="javascript: this.value = this.value==0?'':('0000000000'+this.value).slice(-10);doExecute2('searchProductNo');" 
											placeholder="数字(最大10桁)" value="${G_StockList.productNo}">
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
					<div class="col-xs-8"><button type="button"class="btn btn-primary btn-block" data-changeDisabled="0" onClick="doExecute2('cancel')">リセット</button></div>
					<div class="col-xs-2"></div>
				</div>
<%-- 			<c:choose>
				<c:when test="${empty G_StockList.productNo || empty ProductMaster.productName}"></c:when>
				<c:otherwise> --%>
	<hr>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left">検索条件</label>
					<p class="col-xs-7">：日付&nbsp;&nbsp;
											<input type="date" style="width: 150px;"  name="startDate" id="startDate" class="inputRequired" data-changeDisabled="5" 
											onchange="docheck();" value="${G_Shipping.shipDate}">
											&nbsp;~&nbsp;
											<input type="date" style="width: 150px;"  name="endDate" id="endDate" class="inputRequired" data-changeDisabled="5" 
											onchange="docheck();" value="${G_Shipping.shipDate}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<div class="col-xs-3"></div>
					<p class="col-xs-7">：入荷状況&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="alreadyInStock" id="alreadyInStock" class="inputRequired" data-changeDisabled="5" 
											value="alreadyInStock" checked="checked">&nbsp;&nbsp;入荷済み
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="notInStock" id="notInStock" class="inputRequired" data-changeDisabled="5" 
											value="notInStock" checked="checked">&nbsp;&nbsp;未入荷</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left">並び替え条件(昇順のみ)</label>
					<p class="col-xs-7">：条件(複数選択不可)</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<div class="col-xs-3"></div>
					<div class="col-xs-1"></div>
					<div class="form-check col-xs-2" style="height: 34px;">
						<input class="form-check-input" type="radio" name="sort" id="supplierName" value="supplierName">
						<label class="form-check-label" for="supplierName">仕入先名</label>
					</div>
					<div class="form-check col-xs-2" style="height: 34px;">
						<input class="form-check-input" type="radio" name="sort" id="orderDate" value="orderDate">
						<label class="form-check-label" for="orderDate">発注日</label>
					</div>
					<div class="form-check col-xs-2" style="height: 34px;">
						<input class="form-check-input" type="radio" name="sort" id="orderQty" value="orderQty">
						<label class="form-check-label" for="orderQty">発注数量</label>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<div class="col-xs-3"></div>
					<div class="col-xs-1"></div>
					<div class="form-check col-xs-2" style="height: 34px;">
						<input class="form-check-input" type="radio" name="sort" id="deliveryDate" value="deliveryDate">
						<label class="form-check-label" for="deliveryDate">納期</label>
					</div>
					<div class="form-check col-xs-4" style="height: 34px;">
						<input class="form-check-input" type="radio" name="sort" id="dueDate" value="dueDate">
						<label class="form-check-label" for="dueDate">入荷日</label>
					</div>
				</div>
<%-- 				</c:otherwise>
			</c:choose> --%>
			</form>
			<c:choose>
				<c:when test="${empty G_StockList.productNo || empty ProductMaster.productName}"></c:when>
				<c:otherwise>
	<hr>
					<div class="row">
						<div class="col-xs-1"></div>
						<div class="col-xs-10">
							<table class="table table-bordered table-hover">
								<thead class="thead-dark">
									<tr>
										<th scope="col" class="text-center">年月日</th>
										<th scope="col" class="text-center">受発注番号</th>
										<th scope="col" class="text-center">顧客先コード</th>
										<th scope="col" class="text-center">受注数</th>
										<th scope="col" class="text-center">発注先コード</th>
										<th scope="col" class="text-center">発注数</th>
										<th scope="col" class="text-center">在庫数</th>
									</tr>
								</thead>
									<tbody>
										<tr>
											<td colspan="6" scope="col" class="text-right">現在庫</td>
											<td class="text-right">${PuroductStock.stockQty}</td>
										</tr>
										<c:forEach var="stockInOut" items="${G_StockListAllByProductNo}" varStatus="s">
											<tr>
												<td class="text-left">${stockInOut.deliveryDate}</td>
												<td class="text-left">${stockInOut.orderNo}</td>
												<td title="${stockInOut.customerName}" class="text-left">${stockInOut.customerNo}</td>
												<td class="text-right">${stockInOut.purchaseOrderQty==0?'':stockInOut.purchaseOrderQty}</td>
												<td title="${stockInOut.supplierName}" class="text-left">${stockInOut.supplierNo}</td>
												<td class="text-right">${stockInOut.orderQty==0?'':stockInOut.orderQty}</td>
												<td class="text-right">${stockInOut.stockQtyEstimate}</td>
											</tr>
										</c:forEach>
									</tbody>
							</table>
						</div>
						<div class="col-xs-1"></div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
<%@ include file="../footer.jsp"%>