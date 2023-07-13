<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<script>
	window.addEventListener('load', function() {
		 if('${state}'!=''){
			if(!alert('${state}')){
				${state=""};
				doExecute22('aftDoOrder');
			}
		 }
		 if('${message}'!=''){
			if(!alert('${message}')){
				${message=""};
				doExecute22('dummy');
		 	}
		}
		if('${nextJsp}'=='/WEB-INF/main/user_y.jsp'){
				var form = document.forms[2];
				var btnSelect = form.elements['btnSelect'].value;
				if(btnSelect=='insert'){
					form.elements['userId'].setAttribute('disabled','disabled');
					form.elements['userName'].removeAttribute('disabled');
					form.elements['password'].removeAttribute('disabled');
					form.elements['passwordForCheck'].removeAttribute('disabled');
					form.elements['dept'].removeAttribute('disabled');
					form.elements['etc'].removeAttribute('disabled');
					form.elements['hireDate'].removeAttribute('disabled');
				}else if(btnSelect=='update' && '${userForChange.userId}'!=''){
					form.elements['userId'].setAttribute('disabled','disabled');
					form.elements['userName'].removeAttribute('disabled');
					form.elements['password'].removeAttribute('disabled');
					form.elements['passwordForCheck'].removeAttribute('disabled');
					form.elements['dept'].removeAttribute('disabled');
					form.elements['etc'].removeAttribute('disabled');
					form.elements['hireDate'].removeAttribute('disabled');
				}else if(btnSelect=='update' && '${userForChange.userId}'==''){
					form.elements['userId'].removeAttribute('disabled');
					form.elements['userName'].setAttribute('disabled','disabled');
					form.elements['password'].setAttribute('disabled','disabled');
					form.elements['passwordForCheck'].setAttribute('disabled','disabled');
					form.elements['dept'].setAttribute('disabled','disabled');
					form.elements['etc'].setAttribute('disabled','disabled');
					form.elements['hireDate'].setAttribute('disabled','disabled');
				}else if(btnSelect==''){
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
		if('${nextJsp}'=='/WEB-INF/main/amountCalc.jsp' || '${nextJsp}'=='/WEB-INF/main/amountCalcOrder.jsp' ){
			var positionY = window.pageYOffset;
			window.sessionStorage.setItem(['scrollY'],[positionY]);
		}
		if('${nextJsp}'=='/WEB-INF/main/user_y.jsp'){
			var form = document.forms[2];
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
</script>

<%--
--%>
	    <br>

        <div class="box">
            <header>
                <div class="main">
                    <%-- document.forms[1]; --%>
                        <form action="Logout.action" method="post">
                            <input type="hidden" name="toAction">
                            <div class="row">
                                <div class="col-sm-8 col-xs-12">
                                    <h1 class="h2">所要量計算画面</h1>
                                </div>
                                <div class="col-sm-4 col-xs-12 text-right" >
                                    <a href="javascript:logout()" ></a>
                                </div>
                            </div>
                        </form>
                </div>
            </header>

            <hr>

            <div>
                <div>
                    <%-- document.forms[2]; --%>
                        <form action="AmountCalc.action" method="post">
                            <input type="hidden" name="toAction">
                            <div class="row">
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                             onclick="doExecute22('startAmountCalc')">所要量計算開始</button></p>
                                </div>
                                <div class="col-sm-6 col-xs-12">
                                    <p><button type="button" class="btn btn-primary btn-block"
                                            onclick="doExecute22('cancel')">キャンセル</button></p>
                                </div>
                            </div>
                        </form>
                </div>
            </div>

            <hr>

			<div class="border border-info" >
			        <div class="row">
							<div class="col-sm-1 col-xs-12">
							</div>
							<div class="col-sm-10 col-xs-12">
									<p>処理状況：${amountCalcMSG}</p>
									<div class="progress" style="height: 35px;">
											<div class="progress-bar  ${amountCalcProgColor}" role="progressbar" style="width: ${amountCalcProgPercent}%;">${amountCalcProgPercent}</div>
									</div>
							</div>
		       				<div class="col-sm-1 col-xs-12">
		              		</div>
					</div>
        	</div>

<%@ include file="../footer.jsp" %>