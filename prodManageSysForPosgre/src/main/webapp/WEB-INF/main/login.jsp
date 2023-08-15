<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%--

--%>
		<div class="box">
			<header>
				<div class="main">
					<%-- document.forms[1]; --%>
					<form action="Main.action" method="post">
						<input type="hidden" name="toAction" value="">
						<div class="row">
							<h1 class="h2 col-xs-8">ログイン画面</h1>
							<div class="col-xs-4 text-right"><a href="javascript:logout2()">終了する</a></div>
						</div>
					</form>
				</div>
			</header>
	
			<hr>

			<div>
				<div>
					<%-- document.forms[2]; --%>
					<form action="Login.action" method="post">
						<input type="hidden" name="toAction" value="">
						<div class="row">
							<div class="col-xs-2"></div>
							<label class="form-label col-xs-4" for="userId">ユーザID&nbsp;<span class="label label-danger">必須</span></label>
							<p class="col-xs-5">：<input type="number" style="width: 250px" name="userId" id="userId" class="inputRequired" data-inputRequired="false" min="0" max="999999" step="1" onkeyup="javascript: this.value = this.value.slice(0, 6);" onchange="javascript: this.value = this.value==0?'':('000000'+this.value).slice(-6);" placeholder="6桁数字" value="${G_UserMaster.userId}"></p>
							<div class="col-xs-1"></div>
						</div>
						<div class="row">
							<div class="col-xs-2"></div>
							<label class="form-label col-xs-4" for="password">パスワード&nbsp;<span class="label label-danger">必須</span></label>
							<p class="col-xs-5">：<input type="password" style="width: 250px" name="password" id="password" class="inputRequired" data-inputRequired="false" placeholder="8-20文字(英数構成、大文字1以上)"></p>
							<div class="col-xs-1"></div>
						</div>
						<br>
						<div class="row">
							<div class="col-xs-1"></div>
							<div class="col-xs-5"><button type="button" class="btn btn-primary btn-block" onClick="doExecute2('logIn')">ログイン</button></div>
							<div class="col-xs-5"><button type="button" class="btn btn-warning btn-block" onClick="doExecute2('cancel')">キャンセル</button></div>
							<div class="col-xs-1"></div>
						</div>
					</form>
				</div>
			</div>
			<br>
		    <br>
		    <hr>
			<div class="row">
				<div class="col-xs-1"></div>
				<div class="col-xs-10">
				    <h1 class="h5">登録済みユーザ(動作確認用)</h1>
					<table class="table table-bordered table-hover">
						<thead class="thead-dark">
							<tr>
								<th scope="col">ログイン名</th>
								<th scope="col">パスワード</th>
							</tr>
						</thead>
						<c:forEach var="usPF" items="${usListPF}">
							<tbody>
								<tr>
									<td>${usPF.userID}</td>
									<td>${usPF.password}</td>
								</tr>
							</tbody>
						</c:forEach>
					</table>
				</div>
				<div class="col-xs-1"></div>
			</div>
		</div>
		

	<%@ include file="../footer.jsp"%>