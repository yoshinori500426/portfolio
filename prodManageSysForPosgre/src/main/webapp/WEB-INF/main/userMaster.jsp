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
						<h1 class="h2 col-xs-6">ユーザーマスタ画面</h1>
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
			<form action="UserMaster.action" method="post">
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
					<label class="form-label col-xs-3 text-left" for="userId">ユーザID</label>
					<p class="col-xs-7">：<input type="number"  style="width: 300px;" list="userIdList" name="userId" id="userId" data-changeDisabled="4" 
											min="0" max="999999" step="1" onkeyup="javascript: this.value = this.value.slice(0, 6);" 
											onchange="javascript: this.value = this.value==0?'':('000000'+this.value).slice(-6);doExecute2('searchUserID');" 
											placeholder="数字(最大6桁)" value="${G_UserMaster.userId}">
											<datalist id="userIdList">
												<c:forEach var="uml" items="${UserMasterList}" >
													<option value="${uml.userId}" label="ユーザID:${uml.userId}, ユーザ名:${uml.name}">
												</c:forEach>
											</datalist></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="userName">ユーザ名&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text"  style="width: 300px;" name="userName" id="userName" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											maxlength="50" onchange="docheck();" placeholder="1-50文字" value="${G_UserMaster.name}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="password">パスワード&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="password" style="width: 300px;" name="password" id="password" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											onchange="docheck();" placeholder="8-20文字(英数構成、大文字1以上)" value="${G_UserMaster.password}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="passwordForCheck">パスワード(確認用)&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="password" style="width: 300px;" name="passwordForCheck" id="passwordForCheck" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											onchange="docheck();" placeholder="8-20文字(英数構成、大文字1以上)" value="${G_UserMaster.passwordForCheck}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="dept">分類&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="dept" id="dept" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											maxlength="50" onchange="docheck();" placeholder="1-50文字" value="${G_UserMaster.dept}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="hireDate">入社日&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="hireDate" id="hireDate" class="inputRequired" data-inputRequired="false" data-changeDisabled="5" 
											maxlength="10" onchange="docheck();" placeholder="YYYY/MM/DD" value="${G_UserMaster.hireDate}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="etc">備考</label>
					<p class="col-xs-7">：<textarea rows="2" style="width: 300px;" name="etc" id="etc" data-changeDisabled="5" 
											maxlength="50" placeholder="0-50文字">${G_UserMaster.etc}</textarea></p>
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
			    <h1 class="h5">登録済みユーザレコード(動作確認用)</h1>
				<table class="table table-bordered table-hover">
					<thead class="thead-dark">
						<tr>
							<th scope="col">ユーザID</th>
							<th scope="col">ユーザ名</th>
							<th scope="col">パスワード</th>
							<th scope="col">分類</th>
							<th scope="col">入社日</th>
						</tr>
					</thead>
					<c:forEach var="umPF" items="${UmListPF}">
						<tbody>
							<tr>
								<td>${umPF.userId}</td>
								<td>${umPF.name}</td>
								<td>${umPF.password}</td>
								<td>${umPF.dept}</td>
								<td>${umPF.hireDate}</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
			<div class="col-xs-1"></div>
<%@ include file="../footer.jsp"%>