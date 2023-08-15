<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.jsp"%>
<%--

"(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8, }"
${judgeInput}
${btnSelect}
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
			    <input type="hidden" name="toAction">
				<input type="hidden" name="btnSelect" value="${btnSelect}" >
				<input type="hidden" name="reload">
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5"><button type="button" class="btn btn-warning btn-block"  name="insert" onClick="userBtnChange('insert')">登録</button></div>
					<div class="col-xs-5"><button type="button" class="btn btn-success btn-block" name="update" onClick="userBtnChange('update')">更新</button></div>
					<div class="col-xs-1"></div>
				</div>
		<hr>

				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="userId">ユーザID</label>
					<p class="col-xs-7">：<input type="number"  style="width: 300px;" name="userId" id="userId" min="0" max="999999" step="1" onkeyup="javascript: this.value = this.value.slice(0, 6);" onchange="javascript: this.value = this.value==0?'':('000000'+this.value).slice(-6);" placeholder="6桁数字" onchange="doExecute2('searchUserID')" value="${G_UserMaster.userId}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="userName">ユーザ名&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text"  style="width: 300px;" name="userName" id="userName" class="inputRequired" data-inputRequired="false" maxlength="50" placeholder="1-50文字" value="${G_UserMaster.name}">&nbsp;${alert[0]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="password">パスワード&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="password" style="width: 300px;" name="password" id="password" class="inputRequired" data-inputRequired="false" placeholder="8-20文字(英数構成、大文字1以上)" value="${G_UserMaster.password}">&nbsp;${alert[1]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="passwordForCheck">パスワード(確認用)&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="password" style="width: 300px;" name="passwordForCheck" id="passwordForCheck" class="inputRequired" data-inputRequired="false" placeholder="8-20文字(英数構成、大文字1以上)" value="${G_UserMaster.passwordForCheck}">&nbsp;${alert[2]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="dept">分類&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="dept" id="dept" class="inputRequired" data-inputRequired="false" maxlength="50" placeholder="1-50文字" value="${G_UserMaster.dept}">&nbsp;${alert[3]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="hireDate">入社日&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="hireDate" id="hireDate" class="inputRequired" data-inputRequired="false" maxlength="10" placeholder="YYYY/MM/DD" value="${G_UserMaster.hireDate}">&nbsp;${alert[4]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="etc">備考</label>
					<p class="col-xs-7">：<textarea rows="2" style="width: 300px;" name="etc" id="etc" maxlength="50" placeholder="0-50文字">${G_UserMaster.etc}</textarea></p>
				</div>
		<hr>
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5">
						<c:choose>
								<c:when test="${empty btnSelect}">
										<button type="button" class="btn btn-primary btn-block" disabled>未選択</button>
								</c:when>
								<c:when test="${btnSelect=='insert'}">
										<button type="button" class="btn btn-warning btn-block" onClick="doExecute2('doBTNExecute')">登録</button>
								</c:when>
								<c:when test="${btnSelect=='update'}">
										<button type="button" class="btn btn-success btn-block" onClick="doExecute2('doBTNExecute')">更新</button>
								</c:when>
						</c:choose>
					</div>
					<div class="col-xs-5"><button type="button" class="btn btn-primary btn-block" onClick="doExecute2('cancel')">リセット</button></div>
					<div class="col-xs-1"></div>
				</div>
			</form>
		</div>
	<%@ include file="../footer.jsp"%>