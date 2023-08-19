<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>入出庫一覧</title>
<%@ include file="../header.jsp"%>
<script>
	function doExecute(args) {
		var form = document.forms[1];

		form.siji.value = args;

		form.submit();
	}
</script>
<body>
	<div class="box">
		<header>
			<div class="main">
				<div class="row">
					<div class="col-sm-8 col-xs-12">
						<h2>入出庫一覧画面</h2>
					</div>
				</div>
			</div>
		</header>
		<hr>
		<div>
			<form action="EntryExitInfoLIst.action" method="post">
				<input type="hidden" name="siji" value="">
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<p>
							品番<input type="text" maxlength=10 id="productNo" name="productNo"
								onchange="doExecute('search')" value="${product.productNo}"
								style="margin-left: 5px;">
							<input type="button" value="リセット" onclick="doExecute('reset')">
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<p>
							品名<input type="text" id="name" name="name" readOnly
								value="${product.productName}
								"style="border-style: none; border-bottom: 1px solid;
							 		margin: 0px 0px 12px 5px ;width: 340px; background-color: azure;">
						</p>
					</div>
				</div>
				<p>
					検索条件 : 日付<input type="text" maxlength=10 id="startDate"
						name="startDate" placeholder="例:2023/01/01"
						onchange="doExecute('searchDate')" value="${startDate}"> ～<input
						type="text" maxlength=10 id="endDate" name="endDate"
						placeholder="例:2023/12/31" onchange="doExecute('searchDate')"
						value="${endDate}">
				</p>
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<input type="radio" name="kinds" value="issue"
							onchange="doExecute('issue')">入庫のみ
						<input type="radio"	name="kinds" value="entry" onchange="doExecute('entry')">出庫のみ

						<input type="radio" name="kinds" value="all" onchange="doExecute('all')">全て表示
						<br>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<p>
							並び替え：<input type="radio" id="sort" name="sort" value="ymd"
								onchange="doExecute('sortDate')">年月日 <input type="radio"
								name="sort" value="count" onchange="doExecute('sortCount')">数量
						</p>
					</div>
				</div>
				<table class="enextable">
					<thead class="enexthead">
						<tr>
							<th>入出庫番号</th>
							<th>年月日</th>
							<th>入出庫</th>
							<th>数量</th>
							<th>備考</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="enEx" items="${table}">
							<tr>
								<td>${enEx.enExId}</td>
								<td>${enEx.enExDate}</td>
								<td>${enEx.judge}</td>
								<td>${enEx.count}</td>
								<td>${enEx.reason}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<script>
			document.addEventListener('DOMContentLoaded', messageAlert());

			function messageAlert() {
				var recvMSG = "${message}";
				var clear = "${clear}";
				if (recvMSG != null && recvMSG != "") {
					alert(recvMSG);
				}
				if (clear == "" ){
				checkRadio();
				checkRadio2();
				}
			}

			function checkRadio() {
				var sel = "${sort}";
				var form = document.getElementsByName("sort")
				for (i = 0; i < form.length; i++) {
					if (form[i].value == sel) {
						form[i].checked = true;
					}
				}
			}

			function checkRadio2() {
				var sel = "${kinds}";
				var form = document.getElementsByName("kinds")
				for (i = 0; i < form.length; i++) {
					if (form[i].value == sel) {
						form[i].checked = true;
					}
				}
			}
		</script>
</html>
<%@ include file="../footer.jsp"%>