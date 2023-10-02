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
						<h1 class="h2 col-xs-6">受注一覧画面</h1>
						<h1 class="h3 col-xs-4 text-left"></h1>
						<div class="col-xs-2"><a href="javascript:logout()"></a></div>
					</div>
				</form>
			</div>
		</header>
	<hr>
		<div>
			<%-- document.forms[2]; --%>
			<form action="PurchaseOrderList.action" method="post">
			    <input type="hidden" name="toAction" data-changeDisabled="0">
			    <%-- 切り替えボタンは存在しないが､｢update｣と同様の動作を行わせる為､値｢update｣を固定値とする --%>
				<input type="hidden" name="btnSelect" data-changeDisabled="0" value="update">
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productNo">品番&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" list="productNoList" name="productNo" id="productNo" class="inputRequired" data-changeDisabled="4" 
											min="0" max="9999999999" onkeyup="javascript: this.value = this.value.slice(0, 10);" 
											onchange="javascript: this.value = this.value==0?'':('0000000000'+this.value).slice(-10);doExecute2('searchProductNo');" 
											placeholder="数字(最大10桁)" value="${G_PurchaseOrderList.productNo}">
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
					<div class="col-xs-8"><button type="button"class="btn btn-primary btn-block" data-changeDisabled="0" onClick="doExecute2('cancel')">品番リセット</button></div>
					<div class="col-xs-2"></div>
				</div>
			<c:choose>
				<c:when test="${empty G_PurchaseOrderList.productNo || empty ProductMaster.productName}"></c:when>
				<c:otherwise>
	<hr>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-2 text-left">検索条件</label>
					<p class="col-xs-8">：受注日&nbsp;&nbsp;
											<input type="date" style="width: 150px;"  name="startDate" id="startDate" class="inputRequired" data-changeDisabled="5" 
											onchange="doExecute2('searchByConditions');" value="${G_PurchaseOrderList.startDate}">
											&nbsp;~&nbsp;
											<input type="date" style="width: 150px;"  name="endDate" id="endDate" class="inputRequired" data-changeDisabled="5" 
											onchange="doExecute2('searchByConditions');" value="${G_PurchaseOrderList.endDate}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div> 
					<div class="col-xs-2"></div>
					<p class="col-xs-8">：出荷状況&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="shipped" id="shipped" class="inputRequired" data-changeDisabled="5" 
											onchange="doExecute2('searchByConditions');" value="shipped">
											<label class="form-label" for="shipped">&nbsp;&nbsp;出荷済み</label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="notShipped" id="notShipped" class="inputRequired" data-changeDisabled="5" 
											onchange="doExecute2('searchByConditions');" value="notShipped">
											<label class="form-label" for="notShipped">&nbsp;&nbsp;未出荷</label></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-2 text-left">並び替え条件</label>
					<p class="col-xs-8">：昇順/降順&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="form-check-input" type="radio" name="ascendingDescending" id="ascending" 
													onchange="doExecute2('sortByConditions');" value="ascending">
											<label class="form-check-label" for="ascending">&nbsp;&nbsp;昇順</label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input class="form-check-input" type="radio" name="ascendingDescending" id="descending" 
													onchange="doExecute2('sortByConditions');" value="descending">
											<label class="form-check-label" for="descending">&nbsp;&nbsp;降順</label>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<div class="col-xs-2"></div>
					<div class="col-xs-8">：条件(複数選択不可)</div>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<div class="col-xs-2"></div>
					<div class="form-check col-xs-2" style="height: 34px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="form-check-input" type="radio" name="sort" id="orderDate" onchange="doExecute2('sortByConditions');" value="orderDate">
						<label class="form-check-label" for="orderDate">受注日</label>
					</div>
					<div class="form-check col-xs-2" style="height: 34px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="form-check-input" type="radio" name="sort" id="customerName" onchange="doExecute2('sortByConditions');" value="customerName">
						<label class="form-check-label" for="customerName">顧客名</label>
					</div>
					<div class="form-check col-xs-2" style="height: 34px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="form-check-input" type="radio" name="sort" id="orderQty" onchange="doExecute2('sortByConditions');" value="orderQty">
						<label class="form-check-label" for="orderQty">受注数量</label>
					</div>
					<div class="col-xs-2"></div>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<div class="col-xs-2"></div>
					<div class="form-check col-xs-2" style="height: 34px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="form-check-input" type="radio" name="sort" id="deliveryDate" onchange="doExecute2('sortByConditions');" value="deliveryDate">
						<label class="form-check-label" for="deliveryDate">納期</label>
					</div>
					<div class="form-check col-xs-2" style="height: 34px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="form-check-input" type="radio" name="sort" id="shipDate" onchange="doExecute2('sortByConditions');" value="shipDate">
						<label class="form-check-label" for="shipDate">出荷日</label>
					</div>
					<div class="col-xs-2"></div>
					<div class="col-xs-2"></div>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<div class="col-xs-8"><button type="button"class="btn btn-warning btn-block" data-changeDisabled="0" onClick="doExecute2('conditionsClear')">条件リセット</button></div>
					<div class="col-xs-2"></div>
				</div>
 				</c:otherwise>
			</c:choose>
			</form>
			<c:choose>
				<c:when test="${empty G_PurchaseOrderList.productNo || empty ProductMaster.productName}"></c:when>
				<c:otherwise>
	<hr>
					<div class="row">
						<div class="col-xs-1"></div>
						<div class="col-xs-10">
							<table class="table table-bordered table-hover">
								<thead class="thead-dark">
									<tr>
										<th scope="col" class="text-center">受注日</th>
										<th scope="col" class="text-center">顧客名</th>
										<th scope="col" class="text-center">受注数量</th>
										<th scope="col" class="text-center">納期</th>
										<th scope="col" class="text-center">出荷日</th>
									</tr>
								</thead>
								<c:forEach var="gpolsbc" items="${G_PurchaseOrderListSortByCondition}" varStatus="s">
									<tbody>
										<tr>
											<td class="text-left">${gpolsbc.orderDate}</td>
											<td title="${gpolsbc.customerNo}" class="text-left">${gpolsbc.customerName}</td>
											<td class="text-right">${gpolsbc.orderQty}</td>
											<td class="text-left">${gpolsbc.deliveryDate}</td>
											<td class="text-left">${gpolsbc.shipDate}</td>
										</tr>
									</tbody>
								</c:forEach>
							</table>
						</div>
						<div class="col-xs-1"></div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
<%@ include file="../footer.jsp"%>