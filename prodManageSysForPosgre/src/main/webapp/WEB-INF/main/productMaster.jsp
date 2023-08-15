<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		
<%--

"(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8, }"
--%>
	<div class="box">
		<header>
			<div class="main">
				<%-- document.forms[1]; --%>
				<form action="Logout.action" method="post">
					<input type="hidden" name="toAction">
					<div class="row">
						<h1 class="h2 col-xs-6">品番マスタ画面</h1>
						<h1 class="h3 col-xs-4 text-left">
							<c:choose>
									<c:when test="${empty btnSelect}">
											<span class="label label-primary">状態：未選択</span>
									</c:when>
									<c:when test="${btnSelect=='insert'}">
											<span class="label label-warning">状態：登録</span>
									</c:when>
									<c:when test="${btnSelect=='update'}">
											<span class="label label-success">状態：更新</span>
									</c:when>
							</c:choose>
						</h1>
						<div class="col-xs-2"><a href="javascript:logout()"></a></div>
					</div>
				</form>
			</div>
		</header>

	<hr>

		<div>
			<%-- document.forms[2]; --%>
			<form action="ProductMaster.action" method="post">
			    <input type="hidden" name="toAction">
				<input type="hidden" name="btnSelect" value="${btnSelect}" >
				<input type="hidden" name="reload">
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5"><button type="button" class="btn btn-warning btn-block" name="insert" onClick="productBtnChange('insert')">登録</button></div>
					<div class="col-xs-5"><button type="button" class="btn btn-success btn-block" name="update" onClick="productBtnChange('update')">更新</button></div>
					<div class="col-xs-1"></div>
				</div>
		<hr>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productNo">品番</label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="productNo" id="productNo" min="0" max="9999999999" onkeyup="javascript: this.value = this.value.slice(0, 10);" onchange="javascript: this.value = this.value==0?'':('0000000000'+this.value).slice(-10);doExecute2('searchProductNo');" placeholder="10桁数字" value="${G_ProductMaster.productNo}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="productName">品名&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text"  style="width: 300px;"name="productName" id="productName" class="inputRequired" data-inputRequired="false" maxlength="100" placeholder="1−100文字" value="${G_ProductMaster.productName}">&nbsp;${alert[0]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="supplierNo">仕入先コード&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="supplierNo" id="supplierNo" class="inputRequired" data-inputRequired="false" min="0" max="999999" onkeyup="javascript: this.value = this.value.slice(0, 6);" onchange="javascript: this.value = this.value==0?'':('000000'+this.value).slice(-6);doExecute2('searchSupplierNo');" placeholder="6桁数字" value="${G_ProductMaster.supplierNo}">&nbsp;${alert[1]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="supplierName">仕入先名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="supplierName" id="supplierName" placeholder="表示のみ(入力不可)" value="${SupplierMaster.supplierName}" disabled></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="unitPrice">仕入単価&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="unitPrice" id="unitPrice" class="inputRequired" data-inputRequired="false" onkeydown="befValue=value;" onkeyup="value=((value<=999999.99)&&(value*100%1===0))?value:befValue;" onchange="value=(value=='')?'':(value==0)?0.01:value" placeholder="0.01-999999.99" value="${G_ProductMaster.unitPrice}">&nbsp;${alert[2]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="sellingPrice">売価</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="sellingPrice" id="sellingPrice" onkeydown="befValue=value;" onkeyup="value=((value<=999999.99)&&(value*100%1===0))?value:befValue;" onchange="value=(value=='')?'':(value==0)?0.01:value" placeholder="0.01-999999.99(入力任意)" value="${G_ProductMaster.sellingPrice}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="leadTime">購買リードタイム&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="leadTime" id="leadTime" class="inputRequired" data-inputRequired="false" min="0" max="999" onkeyup="javascript: this.value = this.value.slice(0, 3);" placeholder="1-999" value="${G_ProductMaster.leadTime}">&nbsp;${alert[3]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="lot">購買ロット&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="lot" id="lot" class="inputRequired" data-inputRequired="false" min="0" max="999999" onkeyup="javascript: this.value = this.value.slice(0, 6);" placeholder="1-999999" value="${G_ProductMaster.lot}">&nbsp;${alert[4]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="location">在庫ロケーション&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="location" id="location" class="inputRequired" data-inputRequired="false" maxlength="6" placeholder="1-6文字(例:B-01-3)" value="${G_ProductMaster.location}">&nbsp;${alert[5]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="baseStock">基本在庫&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="baseStock" id="baseStock" class="inputRequired" data-inputRequired="false" min="0" max="999999" onkeyup="javascript: this.value = this.value.slice(0, 6);" placeholder="1-999999" value="${G_ProductMaster.baseStock}">&nbsp;${alert[6]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="etc">備考</label>
					<p class="col-xs-7">：<textarea rows="2" style="width: 300px;" name="etc" id="etc" placeholder="0−120文字">${G_ProductMaster.etc}</textarea></p>
				</div>
		<hr>
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5">
						<c:choose>
								<c:when test="${empty btnSelect}">
										<button type="button" class="btn btn-primary btn-block" disabled>未選択</button>
								</c:when>
								<c:when test="${btnSelect=='insert'}">
										<button type="button" class="btn btn-warning btn-block" onClick="doExecute2('doBTNExecute')">登録</button>
								</c:when>
								<c:when test="${btnSelect=='update'}">
										<button type="button" class="btn btn-success btn-block" onClick="doExecute2('doBTNExecute')">更新</button>
								</c:when>
						</c:choose>
					</div>
					<div class="col-xs-5"><button type="button" class="btn btn-primary btn-block" onClick="doExecute2('cancel')">リセット</button></div>
					<div class="col-xs-1"></div>
				</div>
			</form>
		</div>
		<%@ include file="../footer.jsp"%>