<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%--
--%>
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