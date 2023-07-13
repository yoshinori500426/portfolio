<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../header.jsp"%>
<%--
<script>
		document.addEventListener('DOMContentLosaded', messageAlert());
			function messageAlert() {
				hyouji();
				var form = document.forms[2];
				if (document.getElementById("dousa").value == "update") {
					kousin2();
				}
				if (document.getElementById("dousa").value == "insert") {
					sinki2();
				}
			}
</script>

"(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8, }"
--%>
	<script>
	window.addEventListener('load', function() {
		 if('${state}'!=''){
			if(!alert('${state}')){
				${state=""}
				doExecute22('aftDoOrder');
			}
		 }
		 if('${message}'!=''){
			if(!alert('${message}')){
				${message=""}
				doExecute22('dummy');
		 	}
		}
		if('${nextJsp}'=='/WEB-INF/main/user_y.jsp'){
				var form = document.forms[2];
				var btnSelect = form.elements['btnSelect'].value;
				if(btnSelect=='insert'){
					//ボタン有効/無効切り替え
					form.elements['insert'].setAttribute('disabled','disabled');
					form.elements['update'].removeAttribute('disabled');
					//入力欄有効/無効切り替え
					form.elements['userId'].setAttribute('disabled','disabled');
					form.elements['userName'].removeAttribute('disabled');
					form.elements['password'].removeAttribute('disabled');
					form.elements['passwordForCheck'].removeAttribute('disabled');
					form.elements['dept'].removeAttribute('disabled');
					form.elements['etc'].removeAttribute('disabled');
					form.elements['hireDate'].removeAttribute('disabled');
				}else if(btnSelect=='update' && '${userForChange.userId}'!=''){
					//ボタン有効/無効切り替え
					form.elements['insert'].removeAttribute('disabled');
					form.elements['update'].setAttribute('disabled','disabled');
					//入力欄有効/無効切り替え
					form.elements['userId'].setAttribute('disabled','disabled');
					form.elements['userName'].removeAttribute('disabled');
					form.elements['password'].removeAttribute('disabled');
					form.elements['passwordForCheck'].removeAttribute('disabled');
					form.elements['dept'].removeAttribute('disabled');
					form.elements['etc'].removeAttribute('disabled');
					form.elements['hireDate'].removeAttribute('disabled');
				}else if(btnSelect=='update' && '${userForChange.userId}'==''){
					//ボタン有効/無効切り替え
					form.elements['insert'].removeAttribute('disabled');
					form.elements['update'].setAttribute('disabled','disabled');
					//入力欄有効/無効切り替え
					form.elements['userId'].removeAttribute('disabled');
					form.elements['userName'].setAttribute('disabled','disabled');
					form.elements['password'].setAttribute('disabled','disabled');
					form.elements['passwordForCheck'].setAttribute('disabled','disabled');
					form.elements['dept'].setAttribute('disabled','disabled');
					form.elements['etc'].setAttribute('disabled','disabled');
					form.elements['hireDate'].setAttribute('disabled','disabled');
				}else if(btnSelect==''){
					//ボタン有効/無効切り替え
					form.elements['insert'].removeAttribute('disabled');
					form.elements['update'].removeAttribute('disabled');
					//入力欄有効/無効切り替え
					form.elements['userId'].setAttribute('disabled','disabled');
					form.elements['userName'].setAttribute('disabled','disabled');
					form.elements['password'].setAttribute('disabled','disabled');
					form.elements['passwordForCheck'].setAttribute('disabled','disabled');
					form.elements['dept'].setAttribute('disabled','disabled');
					form.elements['etc'].setAttribute('disabled','disabled');
					form.elements['hireDate'].setAttribute('disabled','disabled');
				}
		}

		if('${nextJsp}'=='/WEB-INF/main/amountCalc.jsp'){
			var form = document.forms[2];
			 //「amountCalcProgFlg」は、
			 //   0:途中終了、1:処理中、2:異常なし終了、3:異常あり終了
			 if('${amountCalcProgFlg}' != '' && '${amountCalcProgFlg}' != '0' && '${amountCalcProgFlg}' != '2'){
				 if('${amountCalcProgFlg}' == '3'){
						doExecute22('goToAmountCalcOrderPage');
				 }else if ('${amountCalcProgFlg}' == '1'){
						form.elements[1].setAttribute('disabled','disabled');
						form.elements[2].removeAttribute('disabled');
						 window.setTimeout(function () {
								doExecute22('processNow');
						 }, 800);
				 }
			 }else{
			 	form.elements[1].removeAttribute('disabled');
			 	form.elements[2].setAttribute('disabled','disabled');
			 }
			 //「amountCalcProgFlg」は、
			 //   0:途中終了、1:処理中、2:異常なし終了、3:異常あり終了
			 if('${amountCalcProgFlg}' != '' && '${amountCalcProgFlg}' != '0' && '${amountCalcProgFlg}' != '2'){
				 if('${amountCalcProgFlg}' == '3'){
						doExecute22('goToAmountCalcOrderPage');
				 }else if ('${amountCalcProgFlg}' == '1'){
						form.elements[1].setAttribute('disabled','disabled');
						form.elements[2].removeAttribute('disabled');
						 window.setTimeout(function () {
								doExecute22('processNow');
						 }, 800);
				 }
			 }else{
			 	form.elements[1].removeAttribute('disabled');
			 	form.elements[2].setAttribute('disabled','disabled');
			 }
		 }
		 if('${nextJsp}'=='/WEB-INF/main/amountCalcOrder.jsp'){
			 var form = document.forms[2];
			 var productNo = document.forms[2].elements['gProductNo'];
			 var orderLotNum = document.forms[2].elements['gOrderLotNum'];
			 for(var i=0;i<productNo.length;i++){
				 if('${G_AmountCalcOrder.productNo}' != '' && productNo.options[i].value == '${G_AmountCalcOrder.productNo}'){
					productNo.options[i].selected = true;
						//フィールド｢orderFinFlg｣は､
						// 「1：アクション(発注/納期調整)不要」
						// 「0-2：発注が必要な品番」
						// 「0-1：納期調整が必要な品番」
						if('${G_AmountCalcOrder.productNo}' != '' && '${G_AmountCalcOrder.orderLotNum}' != '' && '${amountCalcAllListMap[productNoKey][0].orderFinFlg}' == '0-2'){
							form.elements['orderBTN'].removeAttribute('disabled');
						}else{
							form.elements['orderBTN'].setAttribute('disabled','disabled');
						}
						if('${G_AmountCalcOrder.orderLotNum}' != ''){
							orderLotNum.value = parseInt('${G_AmountCalcOrder.orderLotNum}');
						}
					break;
				 }else if('${G_AmountCalcOrder.productNo}' == ''){
					productNo.options[0].selected = true;
					break;
				 }
			}
		}
		if('${nextJsp}'=='/WEB-INF/main/amountCalc.jsp' || '${nextJsp}'=='/WEB-INF/main/amountCalcOrder.jsp' ){
			var scrollY=window.sessionStorage.getItem(['scrollY']);
			if(scrollY!=null){
				scrollTo(0, scrollY);
			}
		}
	})
function doExecute22(atype) {
	var form = document.forms[2];
	if('${nextJsp}'=='/WEB-INF/main/amountCalc.jsp' || '${nextJsp}'=='/WEB-INF/main/amountCalcOrder.jsp' ){
		var positionY = window.pageYOffset;
		window.sessionStorage.setItem(['scrollY'],[positionY]);
	}
	if('${nextJsp}'=='/WEB-INF/main/user_y.jsp'){
		form.elements['userId'].removeAttribute('disabled');
		form.elements['userName'].removeAttribute('disabled');
		form.elements['password'].removeAttribute('disabled');
		form.elements['passwordForCheck'].removeAttribute('disabled');
		form.elements['dept'].removeAttribute('disabled');
		form.elements['etc'].removeAttribute('disabled');
		form.elements['hireDate'].removeAttribute('disabled');
	}
	doExecute2(atype);
}
function btnChange(atype) {
	var form = document.forms[2];
	if(atype=='insert'){
			if(form.elements['btnSelect'].value != 'insert'){
					form.elements['btnSelect'].value = 'insert';
					//値クリア
					form.elements['userId'].value='';
					form.elements['userName'].value='';
					form.elements['password'].value='';
					form.elements['passwordForCheck'].value='';
					form.elements['dept'].value='';
					form.elements['etc'].value='';
					form.elements['hireDate'].value='';
					//表示処理
					doExecute22('btnSelect');
			}
	}else if(atype=='update'){
			if(form.elements['btnSelect'].value != 'update'){
					form.elements['btnSelect'].value = 'update';
					//値クリア
					form.elements['userId'].value='';
					form.elements['userName'].value='';
					form.elements['password'].value='';
					form.elements['passwordForCheck'].value='';
					form.elements['dept'].value='';
					form.elements['etc'].value='';
					form.elements['hireDate'].value='';
					//表示処理
					doExecute22('btnSelect');
			}
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
						<div class="col-xs-6">
							<h1  class="h2">ユーザーマスタ画面</h1>
						</div>
						<div class="col-xs-4">
							<h1 class="h3 text-left">
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
						</div>
						<div class="col-xs-2">
							<a href="javascript:logout()"></a>
						</div>
					</div>
				</form>
			</div>
		</header>

	<hr>

		<div>
			<%-- document.forms[2]; --%>
			<form action="UserY.action" method="post">
			    <input type="hidden" name="toAction">
				<input type="hidden" name="btnSelect" value="${btnSelect}" >
				<input type="hidden" name="reload">
				<div class="row">
					<div class="col-xs-1">
					</div>
					<div class="col-xs-5">
						<button type="button" class="btn btn-warning btn-block"  name="insert" onClick="btnChange('insert')">登録</button>
					</div>
					<div class="col-xs-5">
						<button type="button" class="btn btn-success btn-block" name="update" onClick="btnChange('update')">更新</button>
					</div>
					<div class="col-xs-1">
					</div>
				</div>

		<hr>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label class="form-label"> ユーザID</label>
					</div>
					<div class="col-xs-7">
						<p >：<input type="text"  style="width: 300px;" name="userId"  placeholder="6桁以下の数字"  onchange="doExecute22('searchUserID')" value=
													<c:choose>
															<c:when test="${G_UserMaster.userId=='' || empty G_UserMaster.userId}">
																	"${userForChange.userId}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.userId}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>ユーザ名&nbsp;${alert[0]}</label>
					</div>
					<div class="col-xs-7">
						<p>：<input type="text"  style="width: 300px;" name="userName" placeholder="入力必須" value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	"${userForChange.name}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.name}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>パスワード&nbsp;${alert[1]}</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<input type="password" style="width: 300px;" name="password" placeholder="8文字以上、英数構成、大文字1以上" value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	"${userForChange.password}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.password}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>パスワード(確認用)&nbsp;${alert[2]}</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<input type="password" style="width: 300px;" name="passwordForCheck" placeholder="8文字以上、英数構成、大文字1以上"  value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	""
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.passwordForCheck}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>分類&nbsp;${alert[3]}</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<input type="text" style="width: 300px;" name="dept" placeholder="入力必須" value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	"${userForChange.dept}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.dept}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>備考</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<textarea rows="2" style="width: 300px;" name="etc"><c:choose><c:when
							test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">${userForChange.etc}</c:when><c:otherwise>${G_UserMaster.etc}</c:otherwise></c:choose></textarea></p>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-2">
					</div>
					<div class="col-xs-3">
							<label>入社日&nbsp;${alert[4]}</label>
					</div>
					<div class="col-xs-7">
						<p>
							：<input type="text" style="width: 300px;" name="hireDate"  placeholder="YYYY/MM/DD"  value=
													<c:choose>
															<c:when test="${G_UserMaster.name=='' && G_UserMaster.password=='' && G_UserMaster.dept=='' && G_UserMaster.etc=='' && G_UserMaster.hireDate==''}">
																	"${userForChange.hireDate}"
															</c:when>
															<c:otherwise>
																	"${G_UserMaster.hireDate}"
															</c:otherwise>
													</c:choose>
													></p>
					</div>
				</div>

		<hr>

				<div class="row">
					<div class="col-xs-1">
					</div>
					<div class="col-xs-5">
						<c:choose>
								<c:when test="${empty btnSelect}">
										<button type="button" class="btn btn-primary btn-block" disabled>未選択</button>
								</c:when>
								<c:when test="${btnSelect=='insert'}">
										<button type="button" class="btn btn-warning btn-block" onClick="doExecute22('doBTNExecute')">登録</button>
								</c:when>
								<c:when test="${btnSelect=='update'}">
										<button type="button" class="btn btn-success btn-block" onClick="doExecute22('doBTNExecute')">更新</button>
								</c:when>
						</c:choose>
					</div>
					<div class="col-xs-5">
						<button type="button" class="btn btn-primary btn-block" onClick="doExecute22('cancel')">リセット</button>
					</div>
					<div class="col-xs-1">
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
</div>




	<%@ include file="../footer.jsp"%>