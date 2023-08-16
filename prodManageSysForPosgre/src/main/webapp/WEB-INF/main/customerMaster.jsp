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
		function jikkou() {
			var form = document.forms[2];
			var naiyou = form.dousa.value;
			var judge = window.confirm(naiyou == "insert" ? "新規登録を行いますか？"
					: "データ内容の更新をおこないますか？");

			if (judge == true) {
				doExecute();

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
</header>
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
						<h1 class="h2 col-xs-6">顧客先マスタ画面</h1>
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
			<form action="CustomerMaster.action" method="post">
			    <input type="hidden" name="toAction">
				<input type="hidden" name="btnSelect" value="${btnSelect}" >
				<input type="hidden" name="reload">
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5"><button type="button" class="btn btn-warning btn-block" name="insert" onClick="btnChange('insert')">登録</button></div>
					<div class="col-xs-5"><button type="button" class="btn btn-success btn-block" name="update" onClick="btnChange('update')">更新</button></div>
					<div class="col-xs-1"></div>
				</div>

		<hr>

				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="customerNo">顧客コード</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="customerNo" id="customerNo" maxlength="5" onkeydown="befValue=this.value;" onkeyup="this.value=(befValue==''&&this.value.match(/^[A-Za-z]+$/))?this.value.toUpperCase():(befValue!=''&&(this.value.match(/^[A-Za-z][0-9]*$/)||this.value==''))?this.value:befValue;" onchange="this.value=(this.value.substr(0,1).match(/^[A-Za-z]+$/)&&this.value.substr(1)==0)?this.value.substr(0,1):(this.value.substr(0,1).match(/^[A-Za-z]+$/)&&this.value.substr(1)!='')?this.value.substr(0,1)+('0000'+this.value.substr(1)).slice(-4):this.value;doExecute2('searchCustomerNo');" placeholder="5文字(例:A0100)" value="${G_CustomerMaster.customerNo}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="customerName">会社名&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="customerName" id="customerName" class="inputRequired" data-inputRequired="false" maxlength="100" placeholder="1−100文字" value="${G_CustomerMaster.customerName}">&nbsp;${alert[0]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="branchName">支店名</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="branchName" id="branchName" maxlength="100" placeholder="0−100文字" value="${G_CustomerMaster.branchName}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="zipNo">郵便番号</label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="zipNo" id="zipNo" max="9999999" onkeyup="javascript: this.value = this.value.slice(0, 7);" placeholder="7桁数字('-'なし)" onchange="docheck('zip')" value="${G_CustomerMaster.zipNo}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="address1">住所1</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="address1" id="address1" maxlength="100" placeholder="都道府県(0-100文字)" value="${G_CustomerMaster.address1}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="address2">住所2</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="address2" id="address2" maxlength="100" placeholder="市町村番地(0-100文字)" value="${G_CustomerMaster.address2}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="address3">住所3</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="address3" id="address3" maxlength="100" placeholder="建物名/部屋番号(0-100文字)" value="${G_CustomerMaster.address3}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="tel">電話番号&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="tel" id="tel" class="inputRequired" data-inputRequired="false" max="999999999999999" onkeyup="javascript: this.value = this.value.slice(0, 15);" placeholder="9-15桁数字('-'なし)" onblur="docheck('tel')" value="${G_CustomerMaster.tel}">&nbsp;${alert[1]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="fax">FAX番号</label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="fax" id="fax" max="999999999999999" onkeyup="javascript: this.value = this.value.slice(0, 15);" placeholder="9-15桁数字('-'なし)" onchange="docheck('fax')" value="${G_CustomerMaster.fax}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="manager">担当者</label>
					<p class="col-xs-7">：<input type="text" style="width: 300px;" name="manager" id="manager" maxlength="30" placeholder="0-30文字(敬称略)" value="${G_CustomerMaster.manager}"></p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="delivaryLeadtime">輸送リードタイム&nbsp;<span class="label label-danger">必須</span></label>
					<p class="col-xs-7">：<input type="number" style="width: 300px;" name="delivaryLeadtime" id="delivaryLeadtime" class="inputRequired" data-inputRequired="false" min="1" max="999" onkeyup="javascript: this.value = this.value.slice(0, 3);" placeholder="1-999" onkeydown="return event.keyCode !== 69" onblur="doExecute('DelivaryLeadTimeCheck')" onkeyup="javascript:if(this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);" value="${G_CustomerMaster.delivaryLeadtime}">&nbsp;${alert[2]}</p>
				</div>
				<div class="row">
					<div class="col-xs-2"></div>
					<label class="form-label col-xs-3 text-left" for="etc">備考</label>
					<p class="col-xs-7">：<textarea rows="2" style="width: 300px;" name="etc" id="etc" maxlength="120" placeholder="0−120文字">${G_CustomerMaster.etc}</textarea></p>
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