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
							<div class="col-sm-8 col-xs-12">
								<h2>ログイン画面</h2>
							</div>
							<div class="col-sm-4 col-xs-12 text-right">
								<a href="javascript:logout2()">終了する</a>
							</div>
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
							<div class="col-sm-2 col-xs-12">
							</div>
							<div class="col-sm-4 col-xs-12">
								<label for="userID">ログインID&nbsp;<span class="label label-danger">必須</span></label>
							</div>
							<div class="col-sm-5 col-xs-12">
								<p>
									：<input type="text" name="userID" style="width: 250px" value="${G_UserMaster.userId}">
								</p>
							</div>
							<div class="col-sm-1 col-xs-12">
							</div>
						</div>
						<div class="row">
							<div class="col-sm-2 col-xs-12">
							</div>
							<div class="col-sm-4 col-xs-12">
								<label for="password">パスワード&nbsp;<span	class="label label-danger">必須</span></label>
							</div>
							<div class="col-sm-5 col-xs-12">
								<p>
									：<input type="password" name="password" style="width: 250px">
								</p>
							</div>
							<div class="col-sm-1 col-xs-12">
							</div>
						</div>
						
						<br>
						
						<div class="row">
							<div class="col-sm-1 col-xs-12">
							</div>
							<div class="col-sm-5 col-xs-12">
								<button type="button" class="btn btn-primary btn-block" onClick="doExecute2('logIn')">ログイン</button>
							</div>
							<div class="col-sm-5 col-xs-12">
								<button type="button" class="btn btn-warning btn-block" onClick="doExecute2('cancel')">キャンセル</button>
							</div>
							<div class="col-sm-1 col-xs-12">
							</div>
						</div>
					</form>
				</div>
			</div>
			<br>
		    <br>
		    <hr>
			<div class="row">
				<div class="col-sm-1 col-xs-12">
				</div>
				<div class="col-sm-10 col-xs-12">
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
				<div class="col-sm-1 col-xs-12">
				</div>
			</div>
		</div>
		

	<%@ include file="../footer.jsp"%>