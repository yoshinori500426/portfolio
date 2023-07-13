<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="productNoKey" value="${G_AmountCalcOrder.productNo}" />
<%@ include file="../header.jsp" %>
<script>
	window.addEventListener('load', function(){
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
						if('${amountCalcAllListMap[productNoKey][0].orderFinFlg}' == '0-1'){
							orderLotNum.value = '';
							orderLotNum.setAttribute('disabled','disabled');
						}else if('${amountCalcAllListMap[productNoKey][0].orderFinFlg}' == '0-2'){
							if('${G_AmountCalcOrder.orderLotNum}' != ''){
								orderLotNum.value = parseInt('${G_AmountCalcOrder.orderLotNum}');
							}
							orderLotNum.removeAttribute('disabled');
						}
						if('${G_AmountCalcOrder.productNo}' != '' && '${G_AmountCalcOrder.orderLotNum}' != '' && '${amountCalcAllListMap[productNoKey][0].orderFinFlg}' == '0-2'){
							form.elements['orderBTN'].removeAttribute('disabled');
						}else{
							form.elements['orderBTN'].setAttribute('disabled','disabled');
						}
					break;
				 }else if('${G_AmountCalcOrder.productNo}' == ''){
					productNo.options[0].selected = true;
					break;
				 }
			}
		}
		if('${nextJsp}'=='/WEB-INF/main/amountCalc.jsp' || '${nextJsp}'=='/WEB-INF/main/amountCalcOrder.jsp'  || '${nextJsp}'=='/WEB-INF/main/user_y.jsp'){
			var scrollY=window.sessionStorage.getItem(['scrollY']);
			if(scrollY!=null){
				scrollTo(0, scrollY);
			}
		}
	})
	function doExecute22(atype) {
		if('${nextJsp}'=='/WEB-INF/main/amountCalc.jsp' || '${nextJsp}'=='/WEB-INF/main/amountCalcOrder.jsp' || '${nextJsp}'=='/WEB-INF/main/user_y.jsp' ){
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
                                <div class="col-xs-8">
                                    <h1 class="h2">発注画面</h1>
                                </div>
                                <div class="col-xs-4 text-right" >
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
							<%-- 以下、品番未入力で非表示にする --%>
							<c:choose>
									<c:when test="${empty G_AmountCalcOrder.productNo}">
											<p class="h4 text-nowrap">==========================================================================</p>
									</c:when>
									<c:when test="${!empty G_AmountCalcOrder.productNo}">
											<p class="h4 text-nowrap">=================================品番情報=================================</p>
									</c:when>
							</c:choose>
							<div class="row">
									<div class="col-xs-2">
										<p>品番<span class="label label-danger">必須</span></p>
									</div>
									<div class="col-xs-4">
										<p>：<select name="gProductNo" style="width: 200px" onChange="doExecute22('productNoCheck')">
														<option value=""></option>
														<c:forEach var="pmlf" items="${productMasterListFinal}" >
															<option value="${pmlf.productNo}">${pmlf.productNo}</option>
														</c:forEach>
													</select>
										</p>
									</div>
									<div class="col-xs-2">
											<%-- 以下、品番未入力で非表示にする --%>
											<c:choose>
													<c:when test="${empty G_AmountCalcOrder.productNo}"></c:when>
													<c:when test="${!empty G_AmountCalcOrder.productNo}"><p>顧客コード</p></c:when>
											</c:choose>
									</div>
									<div class="col-xs-4">
											<%-- 以下、品番未入力で非表示にする --%>
											<c:choose>
													<c:when test="${empty G_AmountCalcOrder.productNo}"></c:when>
													<c:when test="${!empty G_AmountCalcOrder.productNo}">
															<p>：${amountCalcAllListMap[productNoKey][0].supplierNo}</p>
													</c:when>
											</c:choose>
									</div>
							</div>
							<%-- 以下、品番未入力で非表示にする --%>
							<c:choose>
								<c:when test="${empty G_AmountCalcOrder.productNo}"></c:when>
								<c:when test="${!empty G_AmountCalcOrder.productNo}">
										<div class="row">
												<div class="col-xs-2">
													<p>品名</p>
												</div>
												<div class="col-xs-4">
													<p>：${amountCalcAllListMap[productNoKey][0].productName}</p>
												</div>
												<div class="col-xs-2">
													<p>仕入先会社名</p>
												</div>
												<div class="col-xs-4">
													<p>：${amountCalcAllListMap[productNoKey][0].supplierName}</p>
												</div>
										</div>
										<div class="row">
												<div class="col-xs-2">
													<p>仕入単価[円]</p>
												</div>
												<div class="col-xs-3">
													<p>：${amountCalcAllListMap[productNoKey][0].unitPrice}</p>
												</div>
												<div class="col-xs-3">
													<p class="text-right">購買リードタイム[日]</p>
												</div>
												<div class="col-xs-4">
													<p>：${amountCalcAllListMap[productNoKey][0].leadtimeFromSupplier}</p>
												</div>
										</div>
										<div class="row">
												<div class="col-xs-2">
													<p>購買ロット数[個]</p>
												</div>
												<div class="col-xs-3">
													<p>：${amountCalcAllListMap[productNoKey][0].lotPcs}</p>
												</div>
												<div class="col-xs-3">
													<p class="text-right">見込納期@本日手配</p>
												</div>
												<div class="col-xs-4">
													<p>：${amountCalcAllListMap[productNoKey][0].expectedDeliveryDate}</p>
												</div>
										</div>
			            <br>
										<p class="h4 text-nowrap">=================================発注情報=================================</p>
										<div class="row">
												<div class="col-xs-5">
													<p class="text-right">${amountCalcOrderMSG1 }</p>
												</div>
												<div class="col-xs-3">
													<p class="text-right">発注ロット数<span class="label label-danger">必須</span></p>
												</div>
												<div class="col-xs-4">
													：<input type="number" name="gOrderLotNum" min="1" step="1"  style="width: 200px; height: 20px;" onChange="doExecute22('orderLotNumCheck')" >
												</div>
										</div>
										<div class="row">
												<div class="col-xs-6">
													<p class="text-right">${amountCalcOrderMSG2 }&emsp;&emsp;&emsp;&emsp;&emsp;</p>
												</div>
												<div class="col-xs-2">
													<p>発注数[個]</p>
												</div>
												<div class="col-xs-4">
														<p>：${amountCalcAllListMap[productNoKey][0].lotPcs*G_AmountCalcOrder.orderLotNum==0 || amountCalcAllListMap[productNoKey][0].orderFinFlg != '0-2'? ""
														:amountCalcAllListMap[productNoKey][0].lotPcs*G_AmountCalcOrder.orderLotNum}</p>
												</div>
										</div>
										<div class="row">
												<div class="col-xs-6">
												</div>
												<div class="col-xs-2">
													<p>発注額[円]</p>
												</div>
												<div class="col-xs-4">
													<p>：${amountCalcAllListMap[productNoKey][0].lotPcs*G_AmountCalcOrder.orderLotNum*amountCalcAllListMap[productNoKey][0].unitPrice==0 || amountCalcAllListMap[productNoKey][0].orderFinFlg != '0-2'? ""
													:amountCalcAllListMap[productNoKey][0].lotPcs*G_AmountCalcOrder.orderLotNum*amountCalcAllListMap[productNoKey][0].unitPrice}</p>
												</div>
										</div>
			            <br>
			                            <div class="row">
			                                <div class="col-xs-1">
			                                </div>
			                                <div class="col-xs-10">
			                                    <p><button type="button" class="btn btn-primary btn-block" name="orderBTN"
			                                            onclick="doExecute22('doOrder')">発注</button></p>
			                                </div>
			                                <div class="col-xs-1">
			                                </div>
			                            </div>
			            <br>
										<p class="h4 text-nowrap">=================================所要量情報================================</p>
										<div class="row">
												<div class="col-xs-6">
												</div>
												<div class="col-xs-2">
													<p>基本在庫[個]</p>
												</div>
												<div class="col-xs-4">
													<p>：${amountCalcAllListMap[productNoKey][0].basestock}</p>
												</div>
										</div>
										<div class="row">
												<div class="col-xs-6">
												</div>
												<div class="border border-danger">
														<div class="col-xs-2">
															<p>見込在庫[個]</p>
														</div>
														<div class="col-xs-4">
															<p>：${amountCalcAllListMap[productNoKey][0].latestCumulativeQty}</p>
														</div>
												</div>
										</div>
										<div class="row">
												<div class="col-xs-6">
												</div>
												<div class="col-xs-2">
													<p><strong>所要量[個]</strong></p>
												</div>
												<div class="col-xs-4">
													<p><strong>：${amountCalcAllListMap[productNoKey][0].latestCumulativeQty-amountCalcAllListMap[productNoKey][0].basestock}</strong>
													&emsp;
													<span class="label label-danger">${amountCalcAllListMap[productNoKey][0].orderFinFlg == '1'?"":amountCalcAllListMap[productNoKey][0].orderFinFlg == '0-1'?"納期確認要":amountCalcAllListMap[productNoKey][0].orderFinFlg == '0-2'?"手配要":""}</span></p>
												</div>
										</div>
						                <table class="table table-bordered table-hover">
						                    <thead class="thead-dark">
						                        <tr>
						                            <th scope="col" class="text-center">発注</th>
						                            <th colspan="3" scope="col" class="text-center">受注</th>
						                            <th rowspan="2" scope="col" class="text-center">増減予定日<br><small>(入荷予定日)<br>(出荷予定日)</small></th>
						                            <th colspan="4" scope="col" class="text-center">見込在庫</th>
						                        </tr>
						                        <tr>
						                            <th scope="col" class="text-center">番号</th>
						                        	<th scope="col" class="text-center">番号</th>
						                            <th scope="col" class="text-center">納期</th>
						                            <th scope="col" class="text-center"><small>輸送リードタイム</small></th>
						                            <th scope="col" class="text-center">入荷数</th>
						                            <th scope="col" class="text-center">出荷数</th>
						                            <th scope="col" class="text-center">累　計</th>
						                        </tr>
						                    </thead>
							                <c:forEach var="amountCalcAll" items="${amountCalcAllListMap[productNoKey]}" varStatus="s" >
							                    <tbody>
							                        <tr>
														<td class="text-left">${amountCalcAll.orderNo}</td>
														<td class="text-left">${amountCalcAll.poNo}</td>
														<td class="text-right">${amountCalcAll.deliveryDateToCustomer}</td>
														<td class="text-right">${amountCalcAll.leadtimeToCustomer=="0"?"":amountCalcAll.leadtimeToCustomer}</td>
														<td class="text-right">${amountCalcAll.stockChangeDate==null?"現在庫数":amountCalcAll.stockChangeDate}</td>
														<td class="text-right">${amountCalcAll.incleaseQty!="0"?amountCalcAll.incleaseQty:s.index=="0"?"0":""}</td>
														<td class="text-right">${amountCalcAll.decreaseQty=="0"?"":amountCalcAll.decreaseQty}</td>
														<td class="text-right">${amountCalcAll.cumulativeQty}</td>
							                        </tr>
							                    </tbody>
							                </c:forEach>
						                </table>
			            <hr>
								</c:when>
							</c:choose>
						  <%--
                            	発注ロット数に値を入力した状態で、品番を空文字とすると、
                            	タグ＜c:choose＞が動作する事で、発注ロット数が非表示となり、リクエストパラメータとして値が渡せなくなる
                            	→発注ロット数非表示の際の、値保持を目的に、以下隠し要素を使用
                            --%>
                            <input type="hidden" name="orderLotNumBackUp" value="${G_AmountCalcOrder.orderLotNum}">
                            <%--
                            	発注用リクエストパラメーター取得を目的としたhidden要素
                            	→画面表示は、セッション属性「amountCalcAllListMap」「G_AmountCalcOrder」などを参照しているのみの為、
                            	　「〇〇Action.java」で参照元のセッション属性を参照する事で、必要なパラメータは取得できるが、
                            	　クライアントからの「リクエスト」という点で、クライアントが確認した画面の値を取得するようにする
                            	　今回､過剰ではあるが､画面表示のパラメータ全てを取得する方針とする
                            --%>
                            <input type="hidden" name="gProductName" value="${amountCalcAllListMap[productNoKey][0].productName}">
                            <input type="hidden" name="gSupplierNo" value="${amountCalcAllListMap[productNoKey][0].supplierNo}">
                            <input type="hidden" name="gSupplierName" value="${amountCalcAllListMap[productNoKey][0].supplierName}">
                            <input type="hidden" name="gBasestock" value="${amountCalcAllListMap[productNoKey][0].basestock}">
                            <input type="hidden" name="gLatestCumulativeQty" value="${amountCalcAllListMap[productNoKey][0].latestCumulativeQty}">
                            <input type="hidden" name="gRequiredQty" value="${amountCalcAllListMap[productNoKey][0].latestCumulativeQty-amountCalcAllListMap[productNoKey][0].basestock}">
                            <input type="hidden" name="gLeadtimeFromSupplier" value="${amountCalcAllListMap[productNoKey][0].leadtimeFromSupplier}">
                            <input type="hidden" name="gExpectedDeliveryDate" value="${amountCalcAllListMap[productNoKey][0].expectedDeliveryDate}">
                            <input type="hidden" name="gLotPcs" value="${amountCalcAllListMap[productNoKey][0].lotPcs}">
                            <input type="hidden" name="gUnitPrice" value="${amountCalcAllListMap[productNoKey][0].unitPrice}">
                            <input type="hidden" name="gOrderQty" value="${amountCalcAllListMap[productNoKey][0].lotPcs*G_AmountCalcOrder.orderLotNum}">
                            <input type="hidden" name="gOrderPrice" value="${amountCalcAllListMap[productNoKey][0].lotPcs*G_AmountCalcOrder.orderLotNum*amountCalcAllListMap[productNoKey][0].unitPrice}">
                        </form>
                </div>
			</div>
<%@ include file="../footer.jsp" %>