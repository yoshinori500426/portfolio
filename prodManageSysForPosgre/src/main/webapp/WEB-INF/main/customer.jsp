<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<style>
.spin_on::-webkit-inner-spin-button, .spin_on::-webkit-outer-spin-button
	{
	opacity: 1;
}

.spin_off::-webkit-inner-spin-button, .spin_off::-webkit-outer-spin-button
	{
	-webkit-appearance: none;
}
</style>
<%--

--%>
<header>
	<script>
		function kousin() {
			var form = document.forms[2];
			form.dousa.value = "update";
			document.getElementById("CustomerNo").removeAttribute("disabled");
			document.getElementById("CustomerName").setAttribute("disabled",
					true);
			document.getElementById("BranchName")
					.setAttribute("disabled", true);
			document.getElementById("ZipNo").setAttribute("disabled", true);
			document.getElementById("Address1").setAttribute("disabled", true);
			document.getElementById("Address2").setAttribute("disabled", true);
			document.getElementById("Address3").setAttribute("disabled", true);
			document.getElementById("Tel").setAttribute("disabled", true);
			document.getElementById("Fax").setAttribute("disabled", true);
			document.getElementById("Manager").setAttribute("disabled", true);
			document.getElementById("DelivaryLeadTime").setAttribute(
					"disabled", true);
			document.getElementById("Etc").setAttribute("disabled", true);
			document.getElementById("jyoutai").value = "既存データ更新";

		}
	</script>
	<script>
		function kousin2() {
			var form = document.forms[2];
			document.getElementById("CustomerNo").removeAttribute("disabled");
			document.getElementById("CustomerName").removeAttribute("disabled");
			document.getElementById("BranchName").removeAttribute("disabled");
			document.getElementById("ZipNo").removeAttribute("disabled");
			document.getElementById("Address1").removeAttribute("disabled");
			document.getElementById("Address2").removeAttribute("disabled");
			document.getElementById("Address3").removeAttribute("disabled");
			document.getElementById("Tel").removeAttribute("disabled");
			document.getElementById("Fax").removeAttribute("disabled");
			document.getElementById("Manager").removeAttribute("disabled");
			document.getElementById("DelivaryLeadTime").removeAttribute(
					"disabled");
			document.getElementById("Etc").removeAttribute("disabled");
			document.getElementById("jyoutai").value = "既存データ更新";
		}
	</script>
	<script>
		function sinki() {
			var form = document.forms[2];
			form.dousa.value = "insert";
			document.getElementById("CustomerNo")
					.setAttribute("disabled", true);
			document.getElementById("CustomerName").removeAttribute("disabled");
			document.getElementById("BranchName").removeAttribute("disabled");
			document.getElementById("ZipNo").removeAttribute("disabled");
			document.getElementById("Address1").removeAttribute("disabled");
			document.getElementById("Address2").removeAttribute("disabled");
			document.getElementById("Address3").removeAttribute("disabled");
			document.getElementById("Tel").removeAttribute("disabled");
			document.getElementById("Fax").removeAttribute("disabled");
			document.getElementById("Manager").removeAttribute("disabled");
			document.getElementById("DelivaryLeadTime").removeAttribute(
					"disabled");
			document.getElementById("Etc").removeAttribute("disabled");
			document.getElementById("jyoutai").value = "新規登録";
		}
	</script>



	<script>
		function jikkou() {
			var form = document.forms[2];
			var naiyou = form.dousa.value;
			var judge = window.confirm(naiyou == "insert" ? "新規登録を行いますか？"
					: "データ内容の更新をおこないますか？");

			if (judge == true) {
				doExecute();

			}
		}

		function docheck(hikisu) {

			var form = document.forms[2];
			var zip = document.getElementById("ZipNo").value.trim();
			var tel = document.getElementById("Tel").value.trim();
			var fax = document.getElementById("Fax").value.trim();

			switch (hikisu) {

			case "zip":
				if (zip.length < 7) {
					alert("７文字の入力をしてください");
					return;
				}
				var reg = new RegExp(/^[0-9]*$/);
				var res = reg.test(zip);
				if (res != true) {
					alert("整数値での入力を行ってください");
					return;
				}
				doExecute("getZip");
				break;

			case "tel":
				if (tel.length < 8) {
					alert("8文字以上での入力をしてください");
				}
				var reg = new RegExp(/^[0-9]*$/);
				var res = reg.test(tel);
				if (res != true) {
					alert("整数値での入力を行ってください");

				}
				doExecute("TelNoCheck");

				break;

			case "fax":
				if (tel.length < 8) {
					alert("8文字以上での入力をしてください");
				}
				var reg = new RegExp(/^[0-9]*$/);
				var res = reg.test(fax);
				if (res != true) {
					alert("整数値での入力を行ってください");

				}
				break;

			default:
				break;
			}

		}

		function doExecute(args) {
			var form = document.forms[2];

	<%-- alert(form.dousa.value); --%>
		if (args == null) {
				form.toAction.value = form.dousa.value;
			} else {
				form.toAction.value = args;
			}
			form.submit();
		}
	</script>
	<script>
		function code(hikisu) {
			var form = document.forms[2];
			form.toAction.value = hikisu;
			form.submit();
		}
	</script>

</header>


<br>

<div class="box">
	<header>
		<div class="main">
			<%-- document.forms[1]; --%>
			<form action="Logout.action" method="post">
				<input type="hidden" name="toAction">
				<div class="row">
					<div class="col-sm-8 col-xs-12">
						<h1 class="h2">顧客先マスタ</h1>
					</div>
					<div class="col-sm-8 col-xs-12 text-right">
						<label>状態：</label><input readonly="readonly" value="" id="jyoutai"
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
			<form action="Customer.action" method="post">
				<input type="hidden" name="toAction" value=""> <input
					type="hidden" name="dousa" value="${dousa}" id="dousa">
				<div class="row">
					<div class="col-sm-6 col-xs-12">
						<div class="col-sm-5 col-xs-12">
							<button type="button" onclick="sinki()"
								class="btn-warning btn-block">登録</button>
						</div>
						<div class="col-sm-5 col-xs-12">
							<button type="button" onclick="kousin()"
								class="btn-success btn-block">更新</button>
						</div>
						<div class="row">
							<div class="col-sm-6 col-xs-12">
								<p></p>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<label>顧客コード</label>
							</div>

							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="CustomerNo" id="CustomerNo"
									maxlength="5" onchange="doExecute('searchNo')"
									value="${ALL.customerNo}" disabled>
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
								<label><span class="label label-danger">必須</span> 会社名</label>
							</div>

							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="CustomerName" maxlength="100"
									placeholder="ex.) (株)ABC" id="CustomerName"
									onblur="doExecute('customerNameCheck')"
									value="${ALL.customerName}" disabled required="required">
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
								<label>支店名</label>
							</div>

							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="BranchName" maxlength="100"
									placeholder="ex.) 滋賀" id="BranchName" value="${ALL.branchName}"
									disabled>
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
								<label>郵便番号</label>
							</div>

							<div class="col-sm-5 col-xs-12 text-right">
								<input type="number" name="ZipNo" maxlength="7" class="spin_off"
									onchange="docheck('zip')" placeholder="'-'なしの7桁数字" id="ZipNo"
									value="${ALL.zipNo}" disabled>
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
								<label>住所1</label>
							</div>

							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="Address1" maxlength="100"
									placeholder="都道府県 ex.)滋賀県" id="Address1"
									value="${ALL.address1}" disabled>
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
								<label>住所2</label>
							</div>

							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="Address2" maxlength="100"
									placeholder="市町村番地" id="Address2" value="${ALL.address2}"
									disabled>
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
								<label>住所3</label>
							</div>

							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="Address3" maxlength="100"
									placeholder="建物名.部屋番号" id="Address3" value="${ALL.address3}"
									disabled>
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
								<label><span class="label label-danger">必須</span> 電話番号</label>
							</div>

							<div class="col-sm-5 col-xs-12 text-right">
								<input type="tel" name="Tel" maxlength="15" id="Tel"
									onblur="docheck('tel')" value="${ALL.tel}" disabled
									required="required" placeholder="9桁～15桁の整数で入力">
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
								<label>FAX番号</label>
							</div>

							<div class="col-sm-5 col-xs-12 text-right">
								<input type="tel" name="Fax" maxlength="15" id="Fax"
									onchange="docheck('fax')" value="${ALL.fax}" disabled
									placeholder="9桁～15桁の整数で入力">
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
								<label>担当者</label>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="Manager" maxlength="30"
									placeholder="敬称除く" id="Manager" value="${ALL.manager}" disabled>
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
								<label><span class="label label-danger">必須</span> 輸送L/T</label>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">


								<input type="number" name="DelivaryLeadTime" class="spin_on"
									onkeydown="return event.keyCode !== 69" id="DelivaryLeadTime"
									value="${ALL.delivaryLeadtime}" disabled min="1" step="1"
									onblur="doExecute('DelivaryLeadTimeCheck')"
									oninput="javascript:if(this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
									maxlength="3" required="required" placeholder="1以上の整数を入力">
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
								<label>備考</label>
							</div>
							<div class="col-sm-5 col-xs-12 text-right">
								<input type="text" name="Etc" id="Etc" maxlength="120"
									value="${ALL.etc}" disabled>
							</div>
							<div class="col-sm-1 col-xs-12">
								<p></p>
							</div>
						</div>



						<hr>
						<div class="col-sm-5 col-xs-12">
							<button type="button" onclick="jikkou()"
								class="btn-danger btn-block" disabled="disabled" id="go">登録・更新</button>
						</div>
						<div class="col-sm-5 col-xs-12">
							<button type="button" name="close"
								onclick="doExecute2('main/customer.jsp')"
								class="btn-primary btn-block">キャンセル</button>
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
			document.addEventListener('DOMContentLosaded', messageAlert());


			function messageAlert() {


		<%-- alert("最終チェック"); --%>
			var recvMSG = "${message}";
				var CN = document.getElementById("CustomerName").value.trim();
				var TEL = document.getElementById("Tel").value.trim();
				var DLT = document.getElementById("DelivaryLeadTime").value;
//追記★
				if (recvMSG != null && recvMSG != "") {
					alert(recvMSG);
					if(('${nakami}'=='insert'||'${nakami}'=='update')&&(recvMSG=='登録に成功しました'||recvMSG=='更新に成功しました')){
							doExecute('AllOkEnd');
					}
				}

				else if ((recvMSG == null || recvMSG == "")
						&& (CN != null || CN != "")
						&& ((TEL.length > 8) && (TEL != null || TEL != ""))
						&& ((DLT > 0) && (DLT != null || DLT != ""))) {
					document.getElementById("go").removeAttribute("disabled");

				}

				var form = document.forms[2];
				if (document.getElementById("dousa").value == "update") {
					kousin2();
				}
				if (document.getElementById("dousa").value == "insert") {
					sinki();
				}

			}

			function upDateCheck() {
				recvUpDateCheck = "${upDateCheck}";

				switch (recvUpDateCheck) {
				case "ok":
					document.getElementById("go").removeAttribute(
					"disabled");

					break;

				default:
					break;
				}

			}



		</script>
	<%@ include file="../footer.jsp"%>