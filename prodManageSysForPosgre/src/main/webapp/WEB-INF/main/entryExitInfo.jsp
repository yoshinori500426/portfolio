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
						<h1 class="h2 col-xs-6">入出庫画面</h1>
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
			<form action="EntryExitInfo.action" method="post">
			    <input type="hidden" name="toAction" data-changeDisabled="0">
				<input type="hidden" name="btnSelect" data-changeDisabled="0" value="${btnSelect}" >
				<input type="hidden" name="nyukoQty" data-changeDisabled="0" value="${G_EntryExitInfo.nyukoQty}" >
				<input type="hidden" name="syukoQty" data-changeDisabled="0" value="${G_EntryExitInfo.syukoQty}" >
				<input type="hidden" name="befEnExNum" data-changeDisabled="0" value="${G_EntryExitInfo.befEnExNum}" >
				<input type="hidden" name="registDate" data-changeDisabled="0" value="${G_EntryExitInfo.registDate}" >
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
					<label class="form-label col-xs-3 text-left" for="enExId">入出庫番号</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" list="enExIdList" name="enExId" id="enExId" data-changeDisabled="4" 
											min="0" max="99999999" onkeyup="javascript: this.value = this.value.slice(0, 8);" 
											onchange="javascript: this.value = this.value==0?'':('00000000'+this.value).slice(-8);doExecute2('searchenExId');" 
											placeholder="数字(最大8桁)" value="${G_EntryExitInfo.enExId}">
											<datalist id="enExIdList">
												<c:forEach var="eeil" items="${EntryExitInfoList}" >
													<option value="${eeil.enExId}" label="入出庫番号:${eeil.enExId}, 入出庫:${(eeil.nyukoQty!=''&&eeil.nyukoQty!='0')?'入庫':(eeil.syukoQty!=''&&eeil.syukoQty!='0')?'出庫':'不明'}, 数量:${(eeil.nyukoQty!=''&&eeil.nyukoQty!='0')?eeil.nyukoQty:(eeil.syukoQty!=''&&eeil.syukoQty!='0')?eeil.syukoQty:'不明'}">
												</c:forEach>
											</datalist></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productNo">品番&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" list="productNoList" name="productNo" id="productNo" class="inputRequired" data-changeDisabled="5" 
											min="0" max="9999999999" onkeyup="javascript: this.value = this.value.slice(0, 10);" 
											onchange="javascript: this.value = this.value==0?'':('0000000000'+this.value).slice(-10);doExecute2('searchProductNo');" 
											placeholder="数字(最大10桁)" value="${G_EntryExitInfo.productNo}">
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
					<label class="form-label col-xs-3 text-left" for="wStockQty">倉庫在庫数&nbsp;<small>(出庫可能数)</small></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="wStockQty" id="wStockQty" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${PuroductStock.wStockQty}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="oowStockQty">倉庫外在庫数&nbsp;<small>(入庫可能数)</small></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="oowStockQty" id="oowStockQty" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${(PuroductStock.stockQty-PuroductStock.wStockQty)=='0'?'':PuroductStock.stockQty-PuroductStock.wStockQty}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-4 text-left">入庫･出庫&nbsp;<span class="label label-danger">必須</span></label>
					<div class="form-check col-xs-2" style="height: 34px;">
						<input class="form-check-input" type="radio" name="stockInOut" id="stockIn" onchange="docheck();" value="in">
						<label class="form-check-label" for="stockIn">入庫</label>
					</div>
					<div class="form-check col-xs-4" style="height: 34px;">
						<input class="form-check-input" type="radio" name="stockInOut" id="stockOut" onchange="docheck();" value="out">
						<label class="form-check-label" for="stockOut">出庫</label>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="enExDate">入出庫日&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="date" style="width: 300px;" name="enExDate" id="enExDate" class="inputRequired" data-changeDisabled="5"
											 onchange="docheck();" value="${G_EntryExitInfo.enExDate}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="enExNum">数量&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="enExNum" id="enExNum" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											min="1" onchange="docheck();" value="${G_EntryExitInfo.enExNum}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="reason">理由</label>
					<p class="col-xs-7">：<textarea rows="2" style="width: 300px;" name="reason" id="reason" data-changeDisabled="5" 
											placeholder="0−120文字">${G_EntryExitInfo.reason}</textarea></p>
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
	<hr>
		<div class="row">
			<div class="col-xs-1"></div>
			<div class="col-xs-10">
			    <h1 class="h5">登録済み入出庫レコード(動作確認用)</h1>
				<table class="table table-bordered table-hover">
					<thead class="thead-dark">
						<tr>
							<th scope="col">入出庫番号</th>
							<th scope="col">品番</th>
							<th scope="col">入出庫</th>
							<th scope="col">入出庫日</th>
							<th scope="col">数量</th>
						</tr>
					</thead>
					<c:forEach var="eeiPF" items="${EeiListPF}">
						<tbody>
							<tr>
								<td>${eeiPF.enExId}</td>
								<td>${eeiPF.productNo}</td>
								<td>${eeiPF.nyukoQty!=''?'入庫':eeiPF.syukoQty!=''?'出庫':''}</td>
								<td>${eeiPF.enExDate}</td>
								<td class="text-right">${eeiPF.nyukoQty!=''?eeiPF.nyukoQty:eeiPF.syukoQty!=''?eeiPF.syukoQty:''}</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
			<div class="col-xs-1"></div>
<%@ include file="../footer.jsp"%>