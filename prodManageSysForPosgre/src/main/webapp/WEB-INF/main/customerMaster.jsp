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
						<h1 class="h2 col-xs-6">顧客先マスタ画面</h1>
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
			<form action="CustomerMaster.action" method="post">
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
					<label class="form-label col-xs-3 text-left" for="customerNo">顧客コード</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" list="customerNoList" name="customerNo" id="customerNo" data-changeDisabled="4" 
											maxlength="5" onkeydown="befValue=this.value;" 
											onkeyup="this.value=(befValue==''&&this.value.match(/^[A-Za-z]+$/))?this.value.toUpperCase():(befValue!=''&&(this.value.match(/^[A-Za-z][0-9]*$/)||this.value==''))?this.value:befValue;" 
											onchange="this.value=(this.value.substr(0,1).match(/^[A-Za-z]+$/)&&this.value.substr(1)==0)?this.value.substr(0,1):(this.value.substr(0,1).match(/^[A-Za-z]+$/)&&this.value.substr(1)!='')?this.value.substr(0,1)+('0000'+this.value.substr(1)).slice(-4):this.value;doExecute2('searchCustomerNo');" 
											placeholder="アルファベット1文字+数字(最大4桁)(例:A100)" value="${G_CustomerMaster.customerNo}">
											<datalist id="customerNoList">
												<c:forEach var="cml" items="${CustomerMasterList}" >
													<option value="${cml.customerNo}" label="顧客コード:${cml.customerNo}, 会社名:${cml.customerName}, 支店名:${cml.branchName}">
												</c:forEach>
											</datalist></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="customerName">会社名&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="customerName" id="customerName" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											maxlength="100" onchange="docheck();" placeholder="1−100文字" value="${G_CustomerMaster.customerName}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="branchName">支店名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="branchName" id="branchName" data-changeDisabled="5" 
											maxlength="100" placeholder="0−100文字" value="${G_CustomerMaster.branchName}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="zipNo">郵便番号</label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="zipNo" id="zipNo" data-changeDisabled="5" 
											max="9999999" onkeyup="javascript: this.value = this.value.slice(0, 7);" placeholder="7桁数字('-'なし)" value="${G_CustomerMaster.zipNo}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="address1">住所1</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="address1" id="address1" data-changeDisabled="5" 
											maxlength="100" placeholder="都道府県(0-100文字)" value="${G_CustomerMaster.address1}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="address2">住所2</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="address2" id="address2" data-changeDisabled="5" 
											maxlength="100" placeholder="市町村番地(0-100文字)" value="${G_CustomerMaster.address2}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="address3">住所3</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="address3" id="address3" data-changeDisabled="5" 
											maxlength="100" placeholder="建物名/部屋番号(0-100文字)" value="${G_CustomerMaster.address3}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="tel">電話番号&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="tel" id="tel" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											max="999999999999999" onkeyup="javascript: this.value = this.value.slice(0, 15);" onchange="docheck();" 
											placeholder="9-15桁数字('-'なし)" value="${G_CustomerMaster.tel}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="fax">FAX番号</label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="fax" id="fax" data-changeDisabled="5" 
											max="999999999999999" onkeyup="javascript: this.value = this.value.slice(0, 15);"
											placeholder="9-15桁数字('-'なし)" value="${G_CustomerMaster.fax}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="manager">担当者</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="manager" id="manager" data-changeDisabled="5" 
											maxlength="30" placeholder="0-30文字(敬称略)" value="${G_CustomerMaster.manager}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="delivaryLeadtime">輸送リードタイム&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="delivaryLeadtime" id="delivaryLeadtime" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											min="1" max="999" onkeyup="javascript: this.value = this.value.slice(0, 3);" onchange="docheck();" 
											placeholder="1-999" value="${G_CustomerMaster.delivaryLeadtime}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="etc">備考</label>
					<p class="col-xs-7">：<textarea rows="2" style="width: 300px;" name="etc" id="etc" data-changeDisabled="5" 
											maxlength="120" placeholder="0−120文字">${G_CustomerMaster.etc}</textarea></p>
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