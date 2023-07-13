<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>

<header>
	<script>
		function sinki() {
			var form = document.forms[2];
			form.dousa.value = "insert";
			document.getElementById("SupplierNo")
					.setAttribute("disabled", true);
			document.getElementById("SupplierName").removeAttribute("disabled");
			document.getElementById("BranchName").removeAttribute("disabled");
			document.getElementById("ZipNo").removeAttribute("disabled");
			document.getElementById("Address1").removeAttribute("disabled");
			document.getElementById("Address2").removeAttribute("disabled");
			document.getElementById("Address3").removeAttribute("disabled");
			document.getElementById("Tel").removeAttribute("disabled");
			document.getElementById("Fax").removeAttribute("disabled");
			document.getElementById("Manager").removeAttribute("disabled");
			document.getElementById("Etc").removeAttribute("disabled");
			document.getElementById("jyoutai").value="新規登録";

		}
	</script>
	<script>
		function sinki2() {
			var form = document.forms[2];
			form.dousa.value = "insert";
			document.getElementById("SupplierNo")
					.setAttribute("disabled", true);
			document.getElementById("SupplierName").removeAttribute("disabled");
			document.getElementById("BranchName").removeAttribute("disabled");
			document.getElementById("ZipNo").removeAttribute("disabled");
			document.getElementById("Address1").removeAttribute("disabled");
			document.getElementById("Address2").removeAttribute("disabled");
			document.getElementById("Address3").removeAttribute("disabled");
			document.getElementById("Tel").removeAttribute("disabled");
			document.getElementById("Fax").removeAttribute("disabled");
			document.getElementById("Manager").removeAttribute("disabled");
			document.getElementById("Etc").removeAttribute("disabled");
			document.getElementById("jyoutai").value="新規登録";
		}
	</script>

	<script>
		function kousin() {
			var form = document.forms[2];
			form.dousa.value = "update";
			document.getElementById("SupplierNo").removeAttribute("disabled");
			document.getElementById("SupplierName").setAttribute("disabled",
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
			document.getElementById("Etc").setAttribute("disabled", true);
			document.getElementById("jyoutai").value="既存データ更新";
		}
	</script>
	<script>
		function kousin2() {
			var form = document.forms[2];
			document.getElementById("SupplierNo").removeAttribute("disabled");
			document.getElementById("SupplierName").removeAttribute("disabled");
			document.getElementById("BranchName").removeAttribute("disabled");
			document.getElementById("ZipNo").removeAttribute("disabled");
			document.getElementById("Address1").removeAttribute("disabled");
			document.getElementById("Address2").removeAttribute("disabled");
			document.getElementById("Address3").removeAttribute("disabled");
			document.getElementById("Tel").removeAttribute("disabled");
			document.getElementById("Fax").removeAttribute("disabled");
			document.getElementById("Manager").removeAttribute("disabled");
			document.getElementById("Etc").removeAttribute("disabled");
			document.getElementById("jyoutai").value="既存データ更新";
		}
	</script>

	<script>
	//ここでテーブルを呼び出す
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

		<div class="main">
			<%-- document.forms[1]; --%>
			<form action="Logout.action" method="post">
				<input type="hidden" name="toAction">
				<div class="row">
					<div class="col-sm-8 col-xs-12">
						<h1 class="h2">仕入先マスタ</h1>
					</div>

					<div class="col-sm-10 col-xs-12 text-right">
						<label>状態：</label><input readonly="readonly" value="" id="jyoutai"
							style="border: none; border-bottom: 1px solid #999; width: 120px; background-color: azure;">
					</div>
					<div class="col-sm-4 col-xs-12 text-right">
						<a href="javascript:logout()"></a>
					</div>

				</div>
			</form>
		</div>


		<hr>

		<div>
			<div>
				<%-- document.forms[2]; --%>
				<form action="SupplierMaster.action" method="post">
					<input type="hidden" name="toAction" value=""> <input
						type="hidden" name="dousa" value="${dousa}" id="dousa">
					<div class="row">
						<div class="col-sm-3 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-3 col-xs-12">
							<button type="button" class="btn btn-warning btn-block"
								onClick="sinki()">新規</button>
						</div>
						<div class="col-sm-3 col-xs-12">
							<button type="button" class="btn btn-success btn-block"
								onClick="kousin()">更新</button>
						</div>
						<div class="col-sm-3 col-xs-12">
							<p></p>
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<label> 仕入先コード</label>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="SupplierNo"
								placeholder="　　　　例）000000" onchange="doExecute('searchNo')"
								id="SupplierNo" value="${sup.supplierNo}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<p>
								会社名<span class="label label-danger">必須</span>
							</p>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="SupplierName" id="SupplierName"
								placeholder="　　　　例） （株）〇〇〇" value="${sup.supplierName}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<label> 支店名</label>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="BranchName" id="BranchName"
								placeholder="　　　　例）　 △△支店" value="${sup.branchName}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<label> 郵便番号</label>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="ZipNo" id="ZipNo"  onchange="doExecute('zipno')"
								placeholder="　　　　　　例）5202301" value="${sup.zipNo}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<label> 住所１</label>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="Address1" id="Address1"
								placeholder="　　　　　　　　都道府県" value="${sup.address1}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<label> 住所２</label>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="Address2" id="Address2"
								placeholder="　　　　　　　　　市町村" value="${sup.address2}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<label> 住所３</label>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="Address3" id="Address3"
								placeholder="　番地・建物名・部屋番号" value="${sup.address3}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<p>
								電話番号<span class="label label-danger">必須</span>
							</p>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="Tel" id="Tel"
								placeholder="　　　　例）08012345678" value="${sup.tel}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<label> ＦＡＸ番号</label>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="Fax" id="Fax"
								placeholder="　　　　例）0771234567" value="${sup.fax}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<label> 担当者</label>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="Manager" id="Manager"
								placeholder="　　　　例）布里　須田美" value="${sup.manager}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12 text-right">
							<label> 備考</label>
						</div>
						<div class="col-sm-4 col-xs-12">
							<input type="text" name="Etc" id="Etc" value="${sup.etc}" disabled>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
					</div>


					<hr>
					<div class="row">
						<div class="col-sm-3 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-3 col-xs-12">
							<button type="button" class="btn btn-danger btn-block"
								onclick="doExecute()">実行</button>
						</div>
						<div class="col-sm-3 col-xs-12">
							<button type="button" class="btn btn-primary btn-block"
								onclick="doExecute2('main/supplierMaster.jsp')">キャンセル</button>
						</div>
						<div class="col-sm-3 col-xs-12">
							<p></p>
						</div>
					</div>
					<br> <br>
					<div class="row">
						<div class="col-sm-2 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-4 col-xs-12">
							<p></p>
						</div>
						<div class="col-sm-2 col-xs-12">
							<p></p>
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
				}
				var form = document.forms[2];
				if (document.getElementById("dousa").value == "insert") {
					sinki2();
				}
				if (document.getElementById("dousa").value == "update") {
					kousin2();
				}
			}
		</script>


		<%@ include file="../footer.jsp"%>