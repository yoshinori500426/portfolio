<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%--

--%>
<header>
	<script>
		function kousin() {
			var form = document.forms[2];
			form.dousa.value = "update";
			document.getElementById("ProductNo").removeAttribute("disabled");
			document.getElementById("ProductName").setAttribute("disabled",
					true);
			document.getElementById("SupplierNo")
					.setAttribute("disabled", true);
			document.getElementById("SupplierName").setAttribute("disabled",
					true);
			document.getElementById("UnitPrice").setAttribute("disabled", true);
			document.getElementById("SellingPrice").setAttribute("disabled",
					true);
			document.getElementById("Leadtime").setAttribute("disabled", true);
			document.getElementById("Lot").setAttribute("disabled", true);
			document.getElementById("Location").setAttribute("disabled", true);
			document.getElementById("BaseStock").setAttribute("disabled", true);
			document.getElementById("Etc").setAttribute("disabled", true);
			document.getElementById("jyoutai").value = "既存データ更新";
		}
	</script>
	<script>
		function kousin2() {
			var form = document.forms[2];
			document.getElementById("ProductNo").removeAttribute("disabled");
			document.getElementById("ProductName").removeAttribute("disabled");
			document.getElementById("SupplierNo").removeAttribute("disabled");
			document.getElementById("SupplierName").setAttribute("disabled",true);
			document.getElementById("UnitPrice").removeAttribute("disabled");
			document.getElementById("SellingPrice").removeAttribute("disabled");
			document.getElementById("Leadtime").removeAttribute("disabled");
			document.getElementById("Lot").removeAttribute("disabled");
			document.getElementById("Location").removeAttribute("disabled");
			document.getElementById("BaseStock").removeAttribute("disabled");
			document.getElementById("Etc").removeAttribute("disabled");
			document.getElementById("jyoutai").value = "既存データ更新";
		}
	</script>
	<script>
		function sinki() {
			var form = document.forms[2];
			form.dousa.value = "insert";
			document.getElementById("ProductNo").setAttribute("disabled", true);
			document.getElementById("ProductName").setAttribute("disabled",
					true);
			document.getElementById("SupplierNo").removeAttribute("disabled");
			document.getElementById("SupplierName").setAttribute("disabled",
					true);
			document.getElementById("UnitPrice").setAttribute("disabled", true);
			document.getElementById("SellingPrice").setAttribute("disabled",
					true);
			document.getElementById("Leadtime").setAttribute("disabled", true);
			document.getElementById("Lot").setAttribute("disabled", true);
			document.getElementById("Location").setAttribute("disabled", true);
			document.getElementById("BaseStock").setAttribute("disabled", true);
			document.getElementById("Etc").setAttribute("disabled", true);
			document.getElementById("jyoutai").value = "新規登録";
		}
	</script>
	<script>
		function sinki2() {
			var form = document.forms[2];
			document.getElementById("ProductNo").setAttribute("disabled", true);
			document.getElementById("ProductName").removeAttribute("disabled");
			document.getElementById("SupplierNo").removeAttribute("disabled");
			document.getElementById("SupplierName").setAttribute("disabled",
					true);
			document.getElementById("UnitPrice").removeAttribute("disabled");
			document.getElementById("SellingPrice").removeAttribute("disabled");
			document.getElementById("Leadtime").removeAttribute("disabled");
			document.getElementById("Lot").removeAttribute("disabled");
			document.getElementById("Location").removeAttribute("disabled");
			document.getElementById("BaseStock").removeAttribute("disabled");
			document.getElementById("Etc").removeAttribute("disabled");
			document.getElementById("jyoutai").value = "新規登録";
		}
	</script>
	<script>
		function doExecute(args) {
			var form = document.forms[2];
			alert(form.dousa.value);
			if (args == null) {
				form.toAction.value = form.dousa.value;
			} else {
				form.toAction.value = args;
			}
			form.submit();
		}

		function jikkou() {
			var form = document.forms[2];
			var naiyou = form.dousa.value;
			var judge = window.confirm(naiyou == "insert" ? "新規登録を行いますか？"
					: "データ内容の更新をおこないますか？");

			if (judge == true) {
				doExecute();

			}
		}
	</script>
	<script>
		function code(hikisu) {
			var form = document.forms[2];
			form.toAction.value = hikisu;
			form.submit();
		}
	</script>

	<style>
label {
	text-align: center;
}
</style>
</header>


	<div class="box">
		<header>
			<div class="main">
				<%-- document.forms[1]; --%>
				<form action="Logout.action" method="post">
					<input type="hidden" name="toAction">
					<div class="row">
						<div class="col-sm-8 col-xs-12">
							<h2>品番マスタ・登録・更新</h2>
						</div>
						<div class="col-sm-8 col-xs-12 text-right">
							<label>状態：</label><input readonly="readonly" value=""
								id="jyoutai"
								style="border: none; border-bottom: 1px solid #999; width: 120px; background-color: azure;">
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
				<form action="ProductMaster.action" method="post">
					<input type="hidden" name="toAction" value=""> <input
						type="hidden" name="dousa" value="${dousa}" id="dousa">
					<div class="row">
						<div class="col-sm-5 col-xs-12">
							<div class="col-sm-5 col-xs-12">
								<p>
									<button type="button" onClick="sinki()"
										class="btn-warning btn-block">登録</button>
								</p>
							</div>
							<div class="col-sm-5 col-xs-12">
								<p>
									<button type="button" onClick="kousin()"
										class="btn-success btn-block">更新</button>
								</p>
							</div>
							<br>
							<hr>
							<label> 品番 　　　　　　<input type="text" name="Number"
								placeholder="(例)0000000100" id="ProductNo"
								onchange="doExecute('searchNo')" value="${Number}" disabled>
							</label> <label> 品名 　　　　　　<input type="text" name="name"
								placeholder="(例)トマト" id="ProductName" value="${Name}" disabled>
							</label> <label> 仕入先コード　　 <input type="text" name="Code"
								placeholder="(例)000100" id="SupplierNo"
								onchange="doExecute('siire')" value="${Code}" disabled>
							</label> <label> 仕入先名　　　　 <input type="text" name="CodeName"
								id="SupplierName" value="${CodeName}" disabled>
							</label> <label> 仕入単価 　　　　<input type="text" name="Price"
								placeholder="(例)100" id="UnitPrice" value="${Price}" disabled>
							</label> <label> 売価 　　　　　　<input type="text" name="SellingPrice"
								placeholder="(例)240" id="SellingPrice" value="${SellingPrice}"
								disabled>
							</label> <label> 購買リードタイム <input type="text" name="leadTime"
								placeholder="(例)2" id="Leadtime" value="${leadTime}" disabled>
							</label> <label> 購買ロット 　　　<input type="text" name="purchaseLot"
								placeholder="(例)3" id="Lot" value="${purchaseLot}" disabled>
							</label> <label> 在庫ロケーション <input type="text" name="stockLocation"
								placeholder="(例)B-01-3" id="Location" value="${stockLocation}"
								disabled>
							</label> <label> 基本在庫　　　　 <input type="text" name="backStock"
								placeholder="(例)50" id="BaseStock" value="${backStock}" disabled>
							</label> <label> 備考 　　　　　　<input type="text" name="remarks" id="Etc"
								placeholder="(例)新鮮" value="${Etc}" disabled>
							</label>
							<hr>

						</div>

						<div class="col-sm-5 col-xs-12"></div>
					</div>
					<div class="row">
						<div class="col-sm-5 col-xs-12">
							<div class="col-sm-5 col-xs-12">
								<p>
									<button type="button" onclick="doExecute()"
										class="btn-danger btn-block">登録・更新</button>
								</p>
							</div>
							<div class="col-sm-5 col-xs-12">
								<p>
									<button type="button"
										onclick="doExecute2('main/productMaster.jsp')"
										class="btn-primary btn-block">キャンセル</button>
								</p>
							</div>
							<div class="col-sm-5 col-xs-12">
								<p></p>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<script>
			document.addEventListener('DOMContentLosaded', messageAlert());
			function messageAlert() {


				var recvMSG = "${message}";
				if (recvMSG != null && recvMSG != "") {
					alert(recvMSG);
					if(('${nakami}'=='insert'||'${nakami}'=='update')&&(recvMSG=='登録に成功しました。'||recvMSG=='更新に成功しました。')){
							doExecute('AllOkEnd');
					}
				}else if (recvMSG != null && recvMSG != "") {
					alert(recvMSG);
				}
				var form = document.forms[2];
				if (document.getElementById("dousa").value == "update") {
					kousin2();
				}
				if (document.getElementById("dousa").value == "insert") {
					sinki2();
				}


			}
		</script>
		<%@ include file="../footer.jsp"%>