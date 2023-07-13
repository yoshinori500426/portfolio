<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.jsp"%>
<%--

--%>
<title>納入画面</title>


<br>

<div class="box">

	<div class="main">
		<%-- document.forms[1]; --%>
		<form action="Logout.action" method="post">
			<input type="hidden" name="toAction">
			<div class="row">
				<div class="col-sm-8 col-xs-12">
					<h2>納入画面</h2>
				</div>
				<div class="col-sm-4 col-xs-12 text-right">
					<a href="javascript:logout()"></a>
				</div>
			</div>
		</form>
	</div>

	<hr>


	<script>
		function doSiji(hikisu) {
			var form = document.forms[2];
			form.siji.value = hikisu;
			form.submit();
		}
	</script>

	<div>
		<div>
			<%-- document.forms[3]; --%>
			<form action="Supplier.action" method="post">
				<input type="hidden" name="siji" value="${siji}">

				<div class="row">
					<div class="col-sm-6 col-xs-12">
						注文番号: <input type="text" name="orderNo" id="orderNo" placeholder="入力してください。"
							value="${orderNo}" onchange="doSiji('orderNoSearch')"><span class="label label-danger">必須</span><br>
						<br>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-6 col-xs-12">
						注 文 日 : <input type="text" name="orderDate" value="${orderDate}"
							readOnly><br> <br>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-6 col-xs-12">
						品　　番: <input type="text" name="productNo" id="productNo"
							value="${productNo}" readOnly><br> <br>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-6 col-xs-12">
						品　　名: <input type="text" name="productName" value="${productName}"
							readOnly><br> <br>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-6 col-xs-12">
						注文数量: <input type="text" name="orderQty" value="${orderQty}"
							readOnly><br> <br>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						納入数量: <input type="number" min="0" name="dueQty" placeholder="入力してください。" value="${dueQty}"><span class="label label-danger">必須</span><br>
						<br>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<button type="button" class="btn btn-danger btn-block" id="disa" onClick="doSiji('kakutei')"
							disabled>確　 定</button>
						<button type="button" class="btn btn-primary btn-block"  onClick="doSiji('clear')">ク リ ア</button>
					</div>
				</div>
			</form>
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
	change();

	}
function change() {
	var henkou = "${orderNo}";
	if(henkou != ""){
		document.getElementById("disa").removeAttribute("disabled");
	}
}


</script>
<%@ include file="../footer.jsp"%>