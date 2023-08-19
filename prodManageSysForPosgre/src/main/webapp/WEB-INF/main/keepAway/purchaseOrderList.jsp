<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	function doExecute(args) {
		var form = document.forms[2];

		form.toAction.value = args;

		if (document.getElementById("productNo").value == ""
				&& args != "cancel") {
			alert("品番を入力してください。");

		} else {

			form.submit();
		}
	}
</script>
<%--

--%>


<div class="box">
	<header>
		<div class="main">
			<%-- document.forms[1]; --%>
			<form action="Logout.action" method="post">
				<input type="hidden" name="toAction">
				<div class="row">
					<div class="col-sm-8 col-xs-12">
						<h2>受注一覧画面</h2>
					</div>
					<div class="col-sm-4 col-xs-12 text-right">
						<a href="javascript:logout()"></a>
					</div>
				</div>
			</form>
		</div>
	</header>

	<hr>

	<div>
		<div>
			<%-- document.forms[2]; --%>
			<form action="PurchaseOrderList.action" method="post">
				<input type="hidden" name="toAction" value="">
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<p>
							品番<input type="text" id="productNo" name="productNo"
								onchange="doExecute('search')" value="${pList.productNo}"
								style="margin-left: 5px;"> <input type="button"
								onclick="doExecute('cancel')" value="リセット">
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<p>
							品名<input type="text" id="name" name="name" readonly
								value="${pList.productName}"
								style="border-style: none; border-bottom: 1px solid; margin: 0px 0px 12px 5px; width: 340px; background-color: azure;">
						</p>
					</div>
				</div>
				<p>
					検索条件 : 日付<input type="text" maxlength=10 id="date" name="startDate"
						placeholder="例:2023/01/01" onchange="doExecute('startdate')"
						value="${startDate}"> ～ <input type="text" maxlength=10
						id="endDate" name="endDate" placeholder="例:2023/12/31"
						onchange="doExecute('startdate')" value="${endDate}">
				</p>
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<p>
							検索：並び替え： <input type="radio" id="sort" name="sort" value="ymd"
								onchange="doExecute('sortdate')">受注日 <input type="radio"
								id="sort" name="sort" value="nouki"
								onchange="doExecute('sortDeadline')">納期 <input
								type="radio" id="sort" name="sort" value="syutka"
								onchange="doExecute('sortShipping')">出荷日 <input
								type="radio" id="sort" name="sort" value="Mash"
								onchange="doExecute('sortQuanitiy')">数量 <input
								type="radio" id="sort" name="sort" value="Castamer"
								onchange="doExecute('sortClient')">顧客
						</p>
					</div>
				</div>
				<p>

					検索：絞り込み <input type="radio" name="sibori" id="sibori" value="mika"
						onchange="doExecute()">未納入 <input type="radio"
						name="sibori" id="sibori" value="nouryuzumi"
						onchange="doExecute()">納入済み
			</form>

			<table class="enextable">
				<thead class="enexthead">
					<tr>
						<th>受注日</th>
						<th>納期</th>
						<th>出荷日</th>
						<th>数量</th>
						<th>顧客名</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${pList.pOrder}">
						<tr>
							<td>${list.orderDate}</td>
							<td>${list.deliveryDate}</td>
							<td>${list.shipDate}</td>
							<td>${list.orderQty}</td>
							<td title="${list.customerNo}">${list.customerName}</td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script>
	document.addEventListener('DOMContentLoaded', messageAlert());

	function messageAlert() {
		var recvMSG = "${message}";

		if (recvMSG != null && recvMSG != "") {
			alert(recvMSG);
		}
		checkRadio();
		checkRadio2();
		checkRadio3();
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
	function checkRadio3() {
		var sel = "${sibori}";
		var form = document.getElementsByName("sibori")
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
<%@ include file="../footer.jsp"%>