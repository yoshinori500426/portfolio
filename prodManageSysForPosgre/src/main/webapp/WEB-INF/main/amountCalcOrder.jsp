<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="productNoKey" value="${G_AmountCalcOrder.productNo}" />
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
						<h1 class="h2 col-xs-7">発注画面</h1>
						<h1 class="h3 col-xs-3 text-left">
							<c:choose>
								<c:when test="${amountCalcAllListMap[productNoKey][0].orderFinFlg == '0-1'}">
									<span class="label label-warning">状態：納期確認要</span>
								</c:when>
								<c:when test="${amountCalcAllListMap[productNoKey][0].orderFinFlg == '0-2'}">
									<span class="label label-danger">状態：手配要</span>
								</c:when>
								<c:otherwise>
									<span class="label label-primary"></span>
								</c:otherwise>
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
			<form action="AmountCalc.action" method="post">
				<input type="hidden" name="toAction">
				<%-- 以下、品番未入力で非表示にする --%>
				<c:choose>
					<c:when test="${empty G_AmountCalcOrder.productNo}">
						<p class="h4 text-nowrap">==========================================================================</p>
					</c:when>
					<c:when test="${!empty G_AmountCalcOrder.productNo}">
						<p class="h4 text-nowrap">=================================品番情報=================================</p>
					</c:when>
				</c:choose>
				<div class="row">
					<p class="col-xs-2">品番&nbsp;<span class="label label-danger">必須</span></p>
					<p class="col-xs-4">：<select style="width: 200px; height: 20.5px;" name="gProductNo" class="inputRequired" data-inputRequired="false" onChange="doExecute2('productNoCheck')">
												<option value=""></option>
												<c:forEach var="pmlf" items="${productMasterListFinal}" >
													<option value="${pmlf.productNo}">${pmlf.productNo}</option>
												</c:forEach>
											</select>
					</p>
					<p class="col-xs-2">
						<%-- 以下、品番未入力で非表示にする --%>
						<c:choose>
							<c:when test="${empty G_AmountCalcOrder.productNo}"></c:when>
							<c:when test="${!empty G_AmountCalcOrder.productNo}">顧客コード</c:when>
						</c:choose>
					</p>
					<p class="col-xs-4">
						<%-- 以下、品番未入力で非表示にする --%>
						<c:choose>
							<c:when test="${empty G_AmountCalcOrder.productNo}"></c:when>
							<c:when test="${!empty G_AmountCalcOrder.productNo}">
								：${amountCalcAllListMap[productNoKey][0].supplierNo}
							</c:when>
						</c:choose>
					</p>
				</div>
				<%-- 以下、品番未入力で非表示にする --%>
				<c:choose>
					<c:when test="${empty G_AmountCalcOrder.productNo}"></c:when>
					<c:when test="${!empty G_AmountCalcOrder.productNo}">
						<div class="row">
							<p class="col-xs-2">品名</p>
							<p class="col-xs-4">：${amountCalcAllListMap[productNoKey][0].productName}</p>
							<p class="col-xs-2">仕入先会社名</p>
							<p class="col-xs-4">：${amountCalcAllListMap[productNoKey][0].supplierName}</p>
						</div>
						<div class="row">
							<p class="col-xs-2">仕入単価[円]</p>
							<p class="col-xs-3">：${amountCalcAllListMap[productNoKey][0].unitPrice}</p>
							<p class="col-xs-3 text-right">購買リードタイム[日]</p>
							<p class="col-xs-4">：${amountCalcAllListMap[productNoKey][0].leadtimeFromSupplier}</p>
						</div>
						<div class="row">
							<p class="col-xs-2">購買ロット数[個]</p>
							<p class="col-xs-3">：${amountCalcAllListMap[productNoKey][0].lotPcs}</p>
							<p class="col-xs-3 text-right">見込納期@本日手配</p>
							<p class="col-xs-4">：${amountCalcAllListMap[productNoKey][0].expectedDeliveryDate}</p>
						</div>
						<br>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${!empty G_AmountCalcOrder.productNo && amountCalcAllListMap[productNoKey][0].orderFinFlg == '0-2'}">
						<p class="h4 text-nowrap">=================================発注情報=================================</p>
						<div class="row">
							<p class="col-xs-5"></p>
							<p class="col-xs-3 text-right">発注ロット数&nbsp;<span class="label label-danger">必須</span></p>
							<p class="col-xs-4">：<input type="number" style="width: 160px; height: 20.5px;" name="gOrderLotNum" class="inputRequired" data-inputRequired="false"
													 min="1" step="1" onkeydown="befValue=this.value;" 
													 onkeyup="this.value=(befValue.length==1 && this.value.length==0)?'':this.value.match(/^[1-9][0-9]*$/)?this.value:befValue;" 
													 onchange="docheck();" onblur="doExecute2('orderLotNumCheck');" value="${G_AmountCalcOrder.orderLotNum}"></p>
						</div>
						<div class="row">
							<p class="col-xs-6"></p>
							<p class="col-xs-2">発注数[個]</p>
							<p class="col-xs-4" id="orderNum"></p>
						</div>
						<div class="row">
							<p class="col-xs-6"></p>
							<p class="col-xs-2">発注額[円]</p>
							<p class="col-xs-4" id="orderPrice"></p>
						</div>
						<br>
						<div class="row">
							<p class="col-xs-6"></p>
							<p class="col-xs-5"><button type="button" class="btn btn-primary btn-block" name="doExecuteBTN" onclick="doExecute2('doOrder')" disabled>発注</button></p>
							<p class="col-xs-1"></p>
						</div>
						<br>
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty G_AmountCalcOrder.productNo}"></c:when>
					<c:when test="${!empty G_AmountCalcOrder.productNo}">
						<p class="h4 text-nowrap">=================================所要量情報================================</p>
						<div class="row">
							<p class="col-xs-6"></p>
							<p class="col-xs-2">基本在庫[個]</p>
							<p class="col-xs-4">：${amountCalcAllListMap[productNoKey][0].basestock}</p>
						</div>
						<div class="row">
							<p class="col-xs-6"></p>
							<p class="col-xs-2">見込在庫[個]</p>
							<p class="col-xs-4">：${amountCalcAllListMap[productNoKey][0].latestCumulativeQty}</p>
						</div>
						<div class="row">
								<p class="col-xs-6"></p>
								<p class="col-xs-2"><strong>所要量[個]</strong></p>
								<p class="col-xs-4"><strong>：${amountCalcAllListMap[productNoKey][0].latestCumulativeQty-amountCalcAllListMap[productNoKey][0].basestock}</strong></p>
						</div>
							<table class="table table-bordered table-hover">
								<thead class="thead-dark">
									<tr>
										<th scope="col" class="text-center">発注</th>
										<th colspan="3" scope="col" class="text-center">受注</th>
										<th rowspan="2" scope="col" class="text-center">増減予定日<br><small>(入荷予定日)<br>(出荷予定日)</small></th>
										<th colspan="4" scope="col" class="text-center">見込在庫</th>
									</tr>
									<tr>
										<th scope="col" class="text-center">番号</th>
										<th scope="col" class="text-center">番号</th>
										<th scope="col" class="text-center">納期</th>
										<th scope="col" class="text-center"><small>輸送リードタイム</small></th>
										<th scope="col" class="text-center">入荷数</th>
										<th scope="col" class="text-center">出荷数</th>
										<th scope="col" class="text-center">累　計</th>
									</tr>
								</thead>
								<c:forEach var="amountCalcAll" items="${amountCalcAllListMap[productNoKey]}" varStatus="s" >
									<tbody>
										<tr>
											<td class="text-left">${amountCalcAll.orderNo}</td>
											<td class="text-left">${amountCalcAll.poNo}</td>
											<td class="text-right">${amountCalcAll.deliveryDateToCustomer}</td>
											<td class="text-right">${amountCalcAll.leadtimeToCustomer=="0"?"":amountCalcAll.leadtimeToCustomer}</td>
											<td class="text-right">${amountCalcAll.stockChangeDate==null?"現在庫数":amountCalcAll.stockChangeDate}</td>
											<td class="text-right">${amountCalcAll.incleaseQty!="0"?amountCalcAll.incleaseQty:s.index=="0"?"0":""}</td>
											<td class="text-right">${amountCalcAll.decreaseQty=="0"?"":amountCalcAll.decreaseQty}</td>
											<td class="text-right">${amountCalcAll.cumulativeQty}</td>
										</tr>
									</tbody>
								</c:forEach>
							</table>
	<hr>
					</c:when>
				</c:choose>
				<%--
					発注ロット数に値を入力した状態で、品番を空文字とすると、
					タグ＜c:choose＞が動作する事で、発注ロット数が非表示となり、リクエストパラメータとして値が渡せなくなる
					→発注ロット数非表示の際の、値保持を目的に、以下隠し要素を使用
				--%>
				<input type="hidden" name="orderLotNumBackUp" value="${G_AmountCalcOrder.orderLotNum}">
				<%--
					発注用リクエストパラメーター取得を目的としたhidden要素
					→画面表示は、セッション属性「amountCalcAllListMap」「G_AmountCalcOrder」などを参照しているのみの為、
					　「〇〇Action.java」で参照元のセッション属性を参照する事で、必要なパラメータは取得できるが、
					　クライアントからの「リクエスト」という点で、クライアントが確認した画面の値を取得するようにする
					　今回､過剰ではあるが､画面表示のパラメータ全てを取得する方針とする
				--%>
				<input type="hidden" name="gProductName" value="${amountCalcAllListMap[productNoKey][0].productName}">
				<input type="hidden" name="gSupplierNo" value="${amountCalcAllListMap[productNoKey][0].supplierNo}">
				<input type="hidden" name="gSupplierName" value="${amountCalcAllListMap[productNoKey][0].supplierName}">
				<input type="hidden" name="gBasestock" value="${amountCalcAllListMap[productNoKey][0].basestock}">
				<input type="hidden" name="gLatestCumulativeQty" value="${amountCalcAllListMap[productNoKey][0].latestCumulativeQty}">
				<input type="hidden" name="gRequiredQty" value="${amountCalcAllListMap[productNoKey][0].latestCumulativeQty-amountCalcAllListMap[productNoKey][0].basestock}">
				<input type="hidden" name="gLeadtimeFromSupplier" value="${amountCalcAllListMap[productNoKey][0].leadtimeFromSupplier}">
				<input type="hidden" name="gExpectedDeliveryDate" value="${amountCalcAllListMap[productNoKey][0].expectedDeliveryDate}">
				<input type="hidden" name="gLotPcs" value="${amountCalcAllListMap[productNoKey][0].lotPcs}">
				<input type="hidden" name="gUnitPrice" value="${amountCalcAllListMap[productNoKey][0].unitPrice}">
				<input type="hidden" name="gOrderQty" value="${amountCalcAllListMap[productNoKey][0].lotPcs*G_AmountCalcOrder.orderLotNum}">
				<input type="hidden" name="gOrderPrice" value="${amountCalcAllListMap[productNoKey][0].lotPcs*G_AmountCalcOrder.orderLotNum*amountCalcAllListMap[productNoKey][0].unitPrice}">
			</form>
		</div>
<%@ include file="../footer.jsp" %>