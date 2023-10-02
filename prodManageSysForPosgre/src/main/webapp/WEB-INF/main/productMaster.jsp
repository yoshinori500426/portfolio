<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
					<p class="col-xs-7">：<input type="number" style="width: 300px;" list="productNoList" name="productNo" id="productNo" data-changeDisabled="4" 
											min="0" max="9999999999" onkeyup="javascript: this.value = this.value.slice(0, 10);" 
											onchange="javascript: this.value = this.value==0?'':('0000000000'+this.value).slice(-10);doExecute2('searchProductNo');" 
											placeholder="数字(最大10桁)" value="${G_ProductMaster.productNo}">
											<datalist id="productNoList">
												<c:forEach var="pml" items="${ProductMasterList}" >
													<option value="${pml.productNo}" label="品番:${pml.productNo}, 品名:${pml.productName}">
												</c:forEach>
											</datalist></p>
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
					<p class="col-xs-7">：<input type="number" style="width: 300px;" list="supplierNoList" name="supplierNo" id="supplierNo" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											min="0" max="999999" onkeyup="javascript: this.value = this.value.slice(0, 6);" 
											onchange="javascript: this.value = this.value==0?'':('000000'+this.value).slice(-6);doExecute2('searchSupplierNo');" 
											placeholder="数字(最大6桁)" value="${G_ProductMaster.supplierNo}">
											<datalist id="supplierNoList">
												<c:forEach var="sml" items="${SupplierMasterList}" >
													<option value="${sml.supplierNo}" label="仕入先コード:${sml.supplierNo}, 会社名:${sml.supplierName}, 支店名:${sml.branchName}">
												</c:forEach>
											</datalist></p>
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
	<hr>
		<div class="row">
			<div class="col-xs-1"></div>
			<div class="col-xs-10">
			    <h1 class="h5">登録済み品番レコード(動作確認用)</h1>
				<table class="table table-bordered table-hover">
					<thead class="thead-dark">
						<tr>
							<th scope="col">品番</th>
							<th scope="col">仕入先コード</th>
							<th scope="col">単価</th>
							<th scope="col">リードタイム</th>
							<th scope="col">ロット</th>
							<th scope="col">ロケーション</th>
							<th scope="col">基本在庫</th>
						</tr>
					</thead>
					<c:forEach var="pmPF" items="${PmListPF}">
						<tbody>
							<tr>
								<td>${pmPF.productNo}</td>
								<td>${pmPF.supplierNo}</td>
								<td class="text-right">${pmPF.unitPrice}</td>
								<td class="text-right">${pmPF.leadTime}</td>
								<td class="text-right">${pmPF.lot}</td>
								<td>${pmPF.location}</td>
								<td class="text-right">${pmPF.baseStock}</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
			<div class="col-xs-1"></div>
<%@ include file="../footer.jsp"%>