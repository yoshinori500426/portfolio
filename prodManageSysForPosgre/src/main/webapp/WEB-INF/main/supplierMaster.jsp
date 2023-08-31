<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
						<h1 class="h2 col-xs-6">仕入先マスタ画面</h1>
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
			<form action="SupplierMaster.action" method="post">
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
					<label class="form-label col-xs-3 text-left" for="supplierNo">仕入先コード</label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" list="supplierNoList" name="supplierNo" id="supplierNo" data-changeDisabled="4" 
											min="0" max="999999" onkeyup="javascript: this.value = this.value.slice(0, 6);" 
											onchange="javascript: this.value = this.value==0?'':('000000'+this.value).slice(-6);doExecute2('searchSupplierNo');" 
											placeholder="6桁数字" value="${G_SupplierMaster.supplierNo}">
											<datalist id="supplierNoList">
												<c:forEach var="sml" items="${SupplierMasterList}" >
													<option value="${sml.supplierNo}" label="仕入先コード:${sml.supplierNo}, 会社名:${sml.supplierName}, 支店名:${sml.branchName}">
												</c:forEach>
											</datalist></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="supplierName">会社名&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="supplierName" id="supplierName" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											maxlength="100" onchange="docheck();" placeholder="1−100文字" value="${G_SupplierMaster.supplierName}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="branchName">支店名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="branchName" id="branchName" data-changeDisabled="5" 
											maxlength="100" placeholder="0−100文字" value="${G_SupplierMaster.branchName}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="zipNo">郵便番号</label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="zipNo" id="zipNo" data-changeDisabled="5" 
											max="9999999" onkeyup="javascript: this.value = this.value.slice(0, 7);" placeholder="7桁数字('-'なし)" value="${G_SupplierMaster.zipNo}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="address1">住所１</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="address1" id="address1" data-changeDisabled="5" 
											maxlength="100" placeholder="都道府県(0-100文字)" value="${G_SupplierMaster.address1}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="address2">住所2</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="address2" id="address2" data-changeDisabled="5" 
											maxlength="100" placeholder="市町村番地(0-100文字)" value="${G_SupplierMaster.address2}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="address3">住所3</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="address3" id="address3" data-changeDisabled="5" 
											maxlength="100" placeholder="建物名/部屋番号(0-100文字)" value="${G_SupplierMaster.address3}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="tel">電話番号&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="tel" id="tel" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											max="999999999999999" onkeyup="javascript: this.value = this.value.slice(0, 15);" onchange="docheck();" 
											placeholder="9-15桁数字('-'なし)" value="${G_SupplierMaster.tel}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="fax">FAX番号</label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="fax" id="fax" data-changeDisabled="5" 
											max="999999999999999" onkeyup="javascript: this.value = this.value.slice(0, 15);" 
											placeholder="9-15桁数字('-'なし)" value="${G_SupplierMaster.fax}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="manager">担当者</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="manager" id="manager" data-changeDisabled="5" 
											maxlength="30" placeholder="0-30文字(敬称略)" value="${G_SupplierMaster.manager}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="etc">備考</label>
					<p class="col-xs-7">：<textarea rows="2" style="width: 300px;" name="etc" id="etc" data-changeDisabled="5" 
											maxlength="120" placeholder="0−120文字">${G_SupplierMaster.etc}</textarea></p>
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
<%@ include file="../footer.jsp"%>