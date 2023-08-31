<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
						<h1 class="h2 col-xs-8">所要量計算画面</h1>
						<div class="col-xs-4 text-right" ><a href="javascript:logout()" ></a></div>
					</div>
				</form>
			</div>
		</header>
	<hr>
		<div>
			<%-- document.forms[2]; --%>
			<form action="AmountCalc.action" method="post">
				<input type="hidden" name="toAction">
				<div class="row">
					<div class="col-xs-1"></div>
					<div class="col-xs-5"><button type="button" class="btn btn-primary btn-block" name="startAmountCalc" onclick="doExecute2('startAmountCalc')">所要量計算開始</button></div>
					<div class="col-xs-5"><button type="button" class="btn btn-warning btn-block" name="cancel" onclick="doExecute2('cancel')">キャンセル</button></div>
					<div class="col-xs-1"></div>
				</div>
			</form>
		</div>
	<hr>
		<div class="border border-info" >
			<div class="row">
				<div class="col-xs-1"></div>
				<div class="col-xs-10">
					<p>処理状況：${amountCalcMSG}</p>
					<div class="progress" style="height: 35px;">
						<c:choose>
							<c:when test="${!empty amountCalcProgPercent}">
								<div class="progress-bar ${amountCalcProgColor}" role="progressbar" style="width: ${amountCalcProgPercent}%;">${amountCalcProgPercent}%</div>
							</c:when>
							<c:otherwise>
								<div class="progress-bar ${amountCalcProgColor}" role="progressbar" style="width: ${amountCalcProgPercent};"></div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="col-xs-1"></div>
			</div>
       	</div>
<%@ include file="../footer.jsp" %>