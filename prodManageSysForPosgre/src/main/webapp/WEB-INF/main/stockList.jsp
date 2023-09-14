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
						<h1 class="h2 col-xs-6">在庫確認画面</h1>
						<h1 class="h3 col-xs-4 text-left"></h1>
						<div class="col-xs-2"><a href="javascript:logout()"></a></div>
					</div>
				</form>
			</div>
		</header>
	<hr>
		<div>
			<%-- document.forms[2]; --%>
			<form action="StockList.action" method="post">
			    <input type="hidden" name="toAction" data-changeDisabled="0">
			    <%-- 切り替えボタンは存在しないが､｢update｣と同様の動作を行わせる為､値｢update｣を固定値とする --%>
				<input type="hidden" name="btnSelect" data-changeDisabled="0" value="update" >
				<input type="hidden" name="stockQty" data-changeDisabled="0" value="${G_StockList.stockQty}">
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
			</form>
			<c:choose>
				<c:when test="${empty G_StockList.productNo || empty ProductMaster.productName}"></c:when>
				<c:otherwise>
	<hr>
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
				</c:otherwise>
			</c:choose>
		</div>
<%@ include file="../footer.jsp"%>