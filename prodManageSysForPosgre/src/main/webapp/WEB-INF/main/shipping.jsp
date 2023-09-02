<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../header.jsp" %>
<%--
	private String poNo; // 受注番号
	private String customerNo; // 顧客コード
	private String customerName; // 会社名
	private String productNo; // 品番
	private String productName; // 品名
	private String orderDate; // 受注日(=登録日)
	private String orderQty; // 受注数量
	private String deliveryDate; // 納期
	private String shipDate; // 出荷日
	private String finFlg; // 完了フラグ
--%>
	<div class="box">
		<header>
			<div class="main">
				<%-- document.forms[1]; --%>
				<form action="Logout.action" method="post">
					<input type="hidden" name="toAction">
					<div class="row">
						<h1 class="h2 col-xs-6">出荷画面</h1>
						<h1 class="h3 col-xs-4 text-left">
							<c:choose>
								<c:when test="${empty btnSelect}">
									<span class="label label-primary">状態：未選択</span>
								</c:when>
								<c:when test="${btnSelect=='update'}">
									<span class="label label-success">状態：出荷登録</span>
								</c:when>
								<c:when test="${btnSelect=='delete'}">
									<span class="label btn-danger">状態：出荷取消</span>
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
			<form action="Shipping.action" method="post">
			    <input type="hidden" name="toAction" data-changeDisabled="0">
				<input type="hidden" name="btnSelect" data-changeDisabled="0" value="${btnSelect}" >
				<input type="hidden" name="finFlg" data-changeDisabled="0" value="${G_Shipping.finFlg}" >
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5"><button type="button" class="btn btn-success btn-block" name="update" data-changeDisabled="2" onClick="btnChange('update')">出荷登録</button></div>
					<div class="col-xs-5"><button type="button" class="btn btn-danger btn-block" name="delete" data-changeDisabled="3" onClick="btnChange('delete')">出荷取消</button></div>
					<div class="col-xs-1"></div>
				</div>
	<hr>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="poNo">受注番号</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" list="poNoList" name="poNo" id="poNo" data-changeDisabled="4" 
											maxlength="8" onkeydown="javascript:this.value=(this.value.substr(0,3)=='PO-'&&this.value.substr(3).match(/^[0-9]*$/))?this.value.substr(3).slice(0, 5):(this.value.substr(0,3)!='PO-'&&this.value.match(/^[0-9]*$/))?this.value.slice(0, 5):'';befValue=this.value;"
											onchange="javascript: this.value = (this.value==0 || this.value=='')?'':(this.value.match(/^[0-9]*$/))?('PO-'+('00000'+this.value).slice(-5)):('PO-'+('00000'+befValue).slice(-5));doExecute2('searchPoNo');"
											placeholder="'PO-'に続く数字(最大5桁)" value="${G_Shipping.poNo}">
											<datalist id="poNoList">
												<c:forEach var="pol" items="${PurchaseOrderList}" >
													<option value="${fn:replace(pol.poNo,'PO-','')}" label="受注番号:${pol.poNo}, 数量:${pol.orderQty}, 納期:${fn:replace(pol.deliveryDate,'-','/')}">
												</c:forEach>
											</datalist></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="customerNo">顧客コード</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="customerNo" id="customerNo" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${G_Shipping.customerNo}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="customerName">顧客名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="customerName" id="customerName" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${CustomerMaster.customerName}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productNo">品番</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="productNo" id="productNo" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${G_Shipping.productNo}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productName">品名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="productName" id="productName" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${ProductMaster.productName}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="orderDate">受注日</label>
					<p class="col-xs-7">：<input type="date" style="width: 300px;" name="orderDate" id="orderDate" data-changeDisabled="0" 
											value="${G_Shipping.orderDate}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="orderQty">受注数量</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="orderQty" id="orderQty" data-changeDisabled="0" 
											placeholder="表示のみ(入力不可)" value="${G_Shipping.orderQty}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="deliveryDate">納期</label>
					<p class="col-xs-7">：<input type="date" style="width: 300px;" name="deliveryDate" id="deliveryDate" data-changeDisabled="0" 
											value="${G_Shipping.deliveryDate}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="shipQty">出荷数量</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="shipQty" id="shipQty" data-changeDisabled="0" 
											placeholder="分納非対応(表示のみ(入力不可))" value="${G_Shipping.orderQty}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="shipDate">出荷日&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="date" style="width: 300px;" name="shipDate" id="shipDate" class="inputRequired" data-changeDisabled="5"
											onchange="docheck();" value="${G_Shipping.shipDate}"></p>
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
										<button type="button" class="btn btn-success btn-block" data-changeDisabled="0" name="doExecuteBTN" onClick="doExecute2('doBTNExecute')" disabled>出荷登録</button>
								</c:when>
								<c:when test="${btnSelect=='delete'}">
										<button type="button" class="btn btn-danger btn-block" data-changeDisabled="0" name="doExecuteBTN" onClick="doExecute2('doBTNExecute')" disabled>出荷取消</button>
								</c:when>
						</c:choose>
					</div>
					<div class="col-xs-5"><button type="button" class="btn btn-primary btn-block" data-changeDisabled="0" onClick="doExecute2('cancel')">リセット</button></div>
					<div class="col-xs-1"></div>
				</div>
			</form>
		</div>
<%@ include file="../footer.jsp"%>