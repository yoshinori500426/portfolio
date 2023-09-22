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
						<h1 class="h2 col-xs-6">入出庫一覧画面</h1>
						<h1 class="h3 col-xs-4 text-left"></h1>
						<div class="col-xs-2"><a href="javascript:logout()"></a></div>
					</div>
				</form>
			</div>
		</header>
	<hr>
		<div>
			<%-- document.forms[2]; --%>
			<form action="EntryExitInfoList.action" method="post">
			    <input type="hidden" name="toAction" data-changeDisabled="0">
			    <%-- 切り替えボタンは存在しないが､｢update｣と同様の動作を行わせる為､値｢update｣を固定値とする --%>
				<input type="hidden" name="btnSelect" data-changeDisabled="0" value="update" >
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productNo">品番&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" list="productNoList" name="productNo" id="productNo" class="inputRequired" data-changeDisabled="4" 
											min="0" max="9999999999" onkeyup="javascript: this.value = this.value.slice(0, 10);" 
											onchange="javascript: this.value = this.value==0?'':('0000000000'+this.value).slice(-10);doExecute2('searchProductNo');" 
											placeholder="数字(最大10桁)" value="${G_EntryExitInfoList.productNo}">
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
				<c:when test="${empty G_EntryExitInfoList.productNo || empty ProductMaster.productName}"></c:when>
				<c:otherwise>
	<hr>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-2 text-left">検索条件</label>
					<p class="col-xs-8">：受注日&nbsp;&nbsp;
											<input type="date" style="width: 150px;"  name="startDate" id="startDate" class="inputRequired" data-changeDisabled="5" 
											onchange="doExecute2('searchByConditions');" value="${G_EntryExitInfoList.startDate}">
											&nbsp;~&nbsp;
											<input type="date" style="width: 150px;"  name="endDate" id="endDate" class="inputRequired" data-changeDisabled="5" 
											onchange="doExecute2('searchByConditions');" value="${G_EntryExitInfoList.endDate}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div> 
					<div class="col-xs-2"></div>
					<p class="col-xs-8">：入出庫&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="stockIn" id="stockIn" class="inputRequired" data-changeDisabled="5" 
											onchange="doExecute2('searchByConditions');" value="stockIn">
											<label class="form-label" for="stockIn">&nbsp;&nbsp;入庫</label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" name="stockOut" id="stockOut" class="inputRequired" data-changeDisabled="5" 
											onchange="doExecute2('searchByConditions');" value="stockOut">
											<label class="form-label" for="stockOut">&nbsp;&nbsp;出庫</label></p>
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
						<input class="form-check-input" type="radio" name="sort" id="enExDate" onchange="doExecute2('sortByConditions');" value="enExDate">
						<label class="form-check-label" for="enExDate">入出庫日</label>
					</div>
					<div class="form-check col-xs-2" style="height: 34px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="form-check-input" type="radio" name="sort" id="stockInOut" onchange="doExecute2('sortByConditions');" value="stockInOut">
						<label class="form-check-label" for="stockInOut">入出庫</label>
					</div>
					<div class="form-check col-xs-2" style="height: 34px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="form-check-input" type="radio" name="sort" id="nyukoSyukoQty" onchange="doExecute2('sortByConditions');" value="nyukoSyukoQty">
						<label class="form-check-label" for="nyukoSyukoQty">数量</label>
					</div>
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
				<c:when test="${empty G_EntryExitInfoList.productNo || empty ProductMaster.productName}"></c:when>
				<c:otherwise>
	<hr>
					<div class="row">
						<div class="col-xs-1"></div>
						<div class="col-xs-10">
							<table class="table table-bordered table-hover">
								<thead class="thead-dark">
									<tr>
										<th scope="col" class="text-center">入出庫日</th>
										<th scope="col" class="text-center">入出庫</th>
										<th scope="col" class="text-center">数量</th>
										<th scope="col" class="text-center">備考</th>
									</tr>
								</thead>
								<c:forEach var="geeilsbc" items="${G_EntryExitInfoListSortByCondition}" varStatus="s">
									<tbody>
										<tr>
											<td class="text-left">${geeilsbc.enExDate}</td>
											<td class="text-left">${geeilsbc.stockInOut}</td>
											<td class="text-right">${geeilsbc.nyukoSyukoQty}</td>
											<td class="text-left">${geeilsbc.reason}</td>
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