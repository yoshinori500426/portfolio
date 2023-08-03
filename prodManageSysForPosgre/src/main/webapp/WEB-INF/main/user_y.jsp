<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.jsp"%>
<%--

"(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8, }"
--%>
	<div class="box">
		<header>
			<div class="main">
				<%-- document.forms[1]; --%>
				<form action="Logout.action" method="post">
					<input type="hidden" name="toAction">
					<div class="row">
						<div class="col-xs-6">
							<h1  class="h2">ユーザーマスタ画面</h1>
						</div>
						<div class="col-xs-4">
							<h1 class="h3 text-left">
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
						</div>
						<div class="col-xs-2">
							<a href="javascript:logout()"></a>
						</div>
					</div>
				</form>
			</div>
		</header>

	<hr>

		<div>
			<%-- document.forms[2]; --%>
			<form action="UserY.action" method="post">
			    <input type="hidden" name="toAction">
				<input type="hidden" name="btnSelect" value="${btnSelect}" >
				<input type="hidden" name="reload">
				<div class="row">
					<div class="col-xs-1">
					</div>
					<div class="col-xs-5">
						<button type="button" class="btn btn-warning btn-block"  name="insert" onClick="btnChange('insert')">登録</button>
					</div>
					<div class="col-xs-5">
						<button type="button" class="btn btn-success btn-block" name="update" onClick="btnChange('update')">更新</button>
					</div>
					<div class="col-xs-1">
					</div>
				</div>

		<hr>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label class="form-label"> ユーザID</label>
					</div>
					<div class="col-xs-7">
						<p >：<input type="text"  style="width: 300px;" name="userId"  placeholder="6桁以下の数字"  onchange="doExecute2('searchUserID')" value=
													<c:choose>
															<c:when test="${G_UserMaster.userId=='' || empty G_UserMaster.userId}">
																	"${userForChange.userId}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.userId}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>ユーザ名&nbsp;${alert[0]}</label>
					</div>
					<div class="col-xs-7">
						<p>：<input type="text"  style="width: 300px;" name="userName" placeholder="入力必須" value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	"${userForChange.name}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.name}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>パスワード&nbsp;${alert[1]}</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<input type="password" style="width: 300px;" name="password" placeholder="8文字以上、英数構成、大文字1以上" value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	"${userForChange.password}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.password}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>パスワード(確認用)&nbsp;${alert[2]}</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<input type="password" style="width: 300px;" name="passwordForCheck" placeholder="8文字以上、英数構成、大文字1以上"  value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	""
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.passwordForCheck}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>分類&nbsp;${alert[3]}</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<input type="text" style="width: 300px;" name="dept" placeholder="入力必須" value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	"${userForChange.dept}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.dept}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>備考</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<textarea rows="2" style="width: 300px;" name="etc"><c:choose><c:when
							test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">${userForChange.etc}</c:when><c:otherwise>${G_UserMaster.etc}</c:otherwise></c:choose></textarea></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>入社日&nbsp;${alert[4]}</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<input type="text" style="width: 300px;" name="hireDate"  placeholder="YYYY/MM/DD"  value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	"${userForChange.hireDate}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.hireDate}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

		<hr>

				<div class="row">
					<div class="col-xs-1">
					</div>
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
					<div class="col-xs-5">
						<button type="button" class="btn btn-primary btn-block" onClick="doExecute2('cancel')">リセット</button>
					</div>
					<div class="col-xs-1">
					</div>
				</div>



				<br> <br>
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<p></p>
					</div>
					<div class="col-sm-6 col-xs-12">
						<p></p>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>




	<%@ include file="../footer.jsp"%>