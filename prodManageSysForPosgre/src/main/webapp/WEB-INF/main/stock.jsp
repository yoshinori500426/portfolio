<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫確認画面</title>
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
							<h2>在庫確認画面</h2>
						</div>
					</div>
			</div>
		</header>
		<hr>
		<form action="Stock.action" method="post">
			<input type="hidden" name="siji" value="">
			<div class="row">
				<div class="col-sm-6 col-xs-12">
					<p>
						品番<input type="text" maxlength="10" id="productNo" name="productNo"
							onchange="doExecute('search')" value="${product.productNo}"
							style="margin-left: 5px;">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-xs-12">
					<div class="stockProductName">
						品名<input type="text" maxlength= 100 id="name" name="name" readOnly
							value="${product.productName}"
							style="border-style: none; border-bottom: 1px solid;
							 margin: 0px 0px 12px 5px ;width: 340px; background-color: azure;">
					</div>
				</div>
			</div>
		</form>


		<table class="enextable">
			<thead class="enexthead">
				<tr>
					<th>年月日</th>
					<th>顧客先</th>
					<th>受発注番号</th>
					<th>受注数</th>
					<th>発注先</th>
					<th>発注数</th>
					<th>在庫数</th>
				</tr>
			</thead>
			<tbody>

				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>${ps.stockQty}</td>
				</tr>
				<c:forEach var="stock" items="${list}">
					<tr>
						<td>${stock.deliveryDate}</td>
						<td title="${stock.customerName}">${stock.customerNo}</td>
						<td>${stock.poNo}</td>
						<td>${stock.poQty == 0 ? "": stock.poQty}</td>
						<td title="${stock.supprierName}">${stock.supprierNo}</td>
						<td>${stock.orQty == 0 ? "": stock.orQty}</td>
						<td>${ps.stockQty = ps.stockQty - stock.poQty + stock.orQty}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<script>
		document.addEventListener('DOMContentLoaded', messageAlert());

		function messageAlert() {
			var recvMSG = "${message}";

			if (recvMSG != null && recvMSG != "") {
				alert(recvMSG);
			}
		}
	</script>
</body>
</html>
<%@ include file="../footer.jsp"%>