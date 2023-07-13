<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<script>
	function logout2() {
		var judge = window.confirm('セッションを破棄します \画面を移動します。');
		if (judge == true) {
			winClose();
			session.invalidate();
		}
	}
</script>
<script>
	function winClose() {
		open('about:blank', '_self').close();
	}
</script>
<script>
	function doExecute(args) {
		var form = document.forms[2];

		form.toAction.value = args;

		form.submit();
	}
</script>
<body>
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
					<div class="row">
						<div class="col-sm-5 col-xs-12">
							<label for="userID">ログインID<span
								class="label label-danger">必須</span></label>
						</div>
						<div class="col-sm-7 col-xs-12">
							<p>
								：<input type="text" name="userID" style="width: 200px"
									value="${us.userId}">
							</p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-5 col-xs-12">
							<label for="password">パスワード<span
								class="label label-danger">必須</span></label>
						</div>
						<div class="col-sm-7 col-xs-12">
							<p>
								：<input type="password" name="password" style="width: 200px">
							</p>
						</div>
					</div>
					<p>
						<input type="submit" class="btn btn-primary btn-block"
							value="ログイン">
					</p>
				</form>
			</div>
		</div>
	</div>
	<script>
		document.addEventListener('DOMContentLosaded', messageAlert());
		function messageAlert() {
			var recvMSG = "${message}";
			if (recvMSG != null && recvMSG != "") {
				alert(recvMSG);
			}
		}
	</script>

	<%@ include file="../footer.jsp"%>