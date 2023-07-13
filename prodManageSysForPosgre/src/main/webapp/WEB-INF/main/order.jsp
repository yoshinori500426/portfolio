<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%--

--%>
<style>
input[type=number]::-webkit-inner-spin-button,
input[type=number]::-webkit-outer-spin-button {
opacity: 1;

}
</style>

<script>
	function doSiji(hikisu) {
		var form = document.forms[2];
		form.siji.value = hikisu;
		if (form.siji.value == "orderQtyCheck") {
			var result = doNum();
		}
		if (result !== 0) {
		}
		document.getElementById("product_No").removeAttribute("disabled");
		document.getElementById("num").removeAttribute("disabled");
		form.submit();
	}
	function doNum() {
		var num = document.getElementById("num").value;
		if (num == "" || num < 1) {
			alert("1以上の整数値のみ入力してください!!! ");
			return 0;
		}
	}
</script>



<br>

<div class="box">
	<header>
		<div class="main">
			<%-- document.forms[1]; --%>
			<form action="Logout.action" method="post">
				<input type="hidden" name="toAction">
				<div class="row">
					<div class="col-sm-8 col-xs-12">
						<h1 class="h2">受注画面</h1>
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
			<form action="Order.action" method="post">
				<input type="hidden" name="toAction"> <input type="hidden"
					name="siji" value="">

				<div class="row">
					<div class="col-sm-6 col-xs-12">


						<div class="row">
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<label>顧客コード</label>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="customer_No"
									onblur="doSiji('customerNoCheck')" required="required"
									value="${order.customer_No }" maxlength="5"
									placeholder="ex.) A0100">
							</div>
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
						</div>



						<div class="row">
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<label>顧客名</label>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" readonly="readonly" name="customer_Name"
									value="${order.customer_Name}" id="c_name">
							</div>
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
						</div>



						<div class="row">
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<label>品番</label>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="product_No"
									onblur="doSiji('productNoCheck')" id="product_No"
									required="required" value="${order.product_No}" maxlength="10"
									disabled="disabled" placeholder="ex.) 0000000100">
							</div>
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
						</div>



						<div class="row">
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<label>品名</label>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="product_Name" readonly="readonly"
									value="${order.product_Name}" id="p_name">
							</div>
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
						</div>



						<div class="row">
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<label>納期</label>
							</div>
							<div class="col-sm-5 col-xs-12 text-left">
								<input id="dat" type="date" maxlength="10"
									value="${order.delivery_Date}" name="delivery_Date"
									onblur="doSiji('deliveryDateCheck')">
							</div>
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
						</div>



						<div class="row">
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<label>数量</label>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<input type="number" name="order_Qty" step="1"
									required="required" min="1" id="num" placeholder="'1'以上の数値を入力"
									onblur="doSiji('orderQtyCheck')" value="${order.order_Qty }"
									oninput="javascript:if(this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
									maxlength="8"
									>
							</div>
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
						</div>


						<hr>
						<div class="col-sm-5 col-xs-12">

							<input type="button" value="新規登録" onclick="doSiji('insert')"
								disabled="disabled" id="go" class="btn-danger btn-block">
						</div>

						<div class="col-sm-5 col-xs-12">
							<input type="button" onclick="doExecute0('main/order.jsp')"
								class="btn-primary btn-block" value="キャンセル">
						</div>


					</div>
					<div class="col-sm-6 col-xs-12">
						<p></p>
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
	<script>


		function dateset() {
			var today = new Date();
			var todayValue = today.getFullYear() + "-"
					+ addZero(today.getMonth() + 1) + "-"
					+ addZero(today.getDate())
			var cale = document.getElementById("dat");
			cale.setAttribute("min", todayValue);
			<%--cale.value = todayValue;--%>

		}
		function addZero(hikisu) {
			if (hikisu < 10) {
				return "0" + hikisu;
			}
			return hikisu;
		}
	</script>


	<script>
		document.addEventListener('DOMContentLoaded', messageAlert(),caseCheck());
		function messageAlert() {
			var recvMSG = "${message}";


			if (recvMSG != null && recvMSG != "") {
				alert(recvMSG);

			} else {}}


			function caseCheck(){

				var recvCheck = "${check}";
				var c_name = document.getElementById("c_name").value;
				var p_name = "${order.product_Name}";
				var num = "${order.order_Qty }";


				switch (recvCheck) {
				case "1":
					document.getElementById("product_No").removeAttribute(
							"disabled");
					<%--alert("case1");--%>
					break;

				case "3":
					<%--alert("case3");--%>
					if (num > 0) {
						document.getElementById("product_No").removeAttribute(
								"disabled");


					} else {
						document.getElementById("product_No").removeAttribute(
								"disabled");
						document.getElementById("num").removeAttribute(
								"disabled");
					}

					break;
				case "4":
					<%--alert("case4");--%>
					document.getElementById("product_No").removeAttribute(
							"disabled");
					document.getElementById("num").removeAttribute("disabled");
					document.getElementById("go").removeAttribute("disabled");

					break;
					//実行後再読み込み時ビーンと動作をクリアする

				case "5":
					doSiji("AllOkEnd");

				default:
					break;
				}}

			dateset();
	</script>






	<%@ include file="../footer.jsp"%>