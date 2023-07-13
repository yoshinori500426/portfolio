<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%--

--%>

<br>

<div class="box">
	<header>
		<div class="main">
			<%-- document.forms[1]; --%>
			<form action="Logout.action" method="post">
				<input type="hidden" name="toAction">
				<div class="row">
					<div class="col-sm-8 col-xs-12">
						<h1 class="h2">出荷画面</h1>
					</div>
					<div class="col-sm-4 col-xs-12 text-right">
						<a href="javascript:logout()"></a>
					</div>
				</div>
			</form>
		</div>
	</header>
</div>
<hr>



<%-- ここから --%>


<%-- document.forms[2]; --%>
<script>
	function dotoAction(hikisu) {
		var form = document.forms[2];
		form.toAction.value = hikisu;
		form.submit();
	}
</script>



	<form action="Shipping.action" method="post">
		<input type="hidden" name="toAction">
		<table style="margin:0 auto;">
			<tr>
						<td title="年月日">年月日</td>
						<td title="セル1-3">　<input type="date" name="today" id="today"
							readOnly></td>

			</tr>


				<tr>
					<td title="受注番号">受注番号
					<span class="label label-danger">必須</span></td>
					<td title="セル2-3">　<input type="text" name="Po_No"
						placeholder="例）PO_00000" value="${poNo}"
						onchange="dotoAction('poNoSeach')"></td>
				</tr>


				<tr>
					<td title="出荷日">出荷日　
					<span class="label label-danger">必須</span></td>
					<td title="セル3-3">　<input type="date" name="ship_date"></td>
				</tr>

			<tr>
			<td title="受注日">受注日</td>
			<td title="セル3-3">　${orderDate}</td>
		</tr>

			<tr>
			<td title="顧客コード">顧客コード</td>
			<td title="セル3-3">　${customerNo}</td>
		</tr>

			<tr>
			<td title="顧客名">顧客名</td>
			<td title="セル3-3">　${customerName}</td>
		</tr>

			<tr>
			<td title="品番">品番</td>
			<td title="セル3-3">　${productNo}</td>
		</tr>

			<tr>
			<td title="品名">品名</td>
			<td title="セル3-3">　${productName}</td>
		</tr>
			<tr>
			<td title="数量">数量</td>
			<td title="セル3-3">　${orderQty}</td>
		</tr>

				<tr>
			<td title="確定"><button type="button" class="btn btn-primary btn-block" id="KT"
							onclick="dotoAction('kakutei')" disabled>　確　定　</button></td>


				<td title="キャンセル"><button type="button" class="btn btn-primary btn-block"
							onclick="dotoAction('cancel')">キャンセル</button></td>

		</table>

</form>
<%-- ここまで --%>
<script><%--年月日--%>
	document.addEventListener("load", today());
	function today() {
		var today = new Date();
		today.setDate(today.getDate());
		var yyyy = today.getFullYear();
		var mm = ("0" + (today.getMonth() + 1)).slice(-2);
		var dd = ("0" + today.getDate()).slice(-2);
		document.getElementById("today").value = yyyy + '-' + mm + '-' + dd;
	}
</script>

<script>
	document.addEventListener('DOMContentLoaded', messageAlert());
	function messageAlert() {
		var recvMSG = "${message}";
		if (recvMSG != null && recvMSG != "") {
			alert(recvMSG);
		}
	}
</script>

<script>
	document.addEventListener('DOMContentLosaded', both());
	function both() {
		var form = document.forms[2];
		document.getElementById("KT").removeAttribute("disabled");
	}
</script>



<%@ include file="../footer.jsp"%>
<%-- ↑これはのこす --%>







