<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
完成
動作確認済み

年月を入力、入力月の出庫データをすべて取得して表示させる。

Alart
　name="year" -> ${alart[0]}
　name="month" -> ${alart[1]}

動作指示
　priceCheck
--%>
<div class="box">
    <header>
        <div class="main">
<%-- document.forms[1]; --%>
            <form action="InputOutputScreen" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
                    <div class="col-sm-8 col-xs-12">
                        <h1>売上確認</h1>
                    </div>
                    <div class="col-sm-4 col-xs-12 text-right">
                        <a href="javascript:doExecute1('main/menu.jsp')">メニュー画面へ</a>
                    </div>
                </div>
            </form>
        </div>
    </header>

    <hr>

    <div>
        <div onload="doExecute2()">
<%-- document.forms[2]; --%>
            <form action="Earnings" method="post">
                <input type="hidden" name="toAction">
                <div class="row">
	                <div class="col-sm-5 col-xs-12">
	                    <label for="year">年を選択&nbsp;<span class="label label-danger">必須</span></label>
	                </div>
	                <div class="col-sm-7 col-xs-12">
		                <p>：<select id="year" name="year" style="width: 100px" onChange="doExecute2('earningsCheckYear')">
										<c:forEach var="i" begin="1999" end="2023" varStatus="status">
											<c:choose>
												<c:when test="${i==1999}">
													<option value="${i}"></option>
												</c:when>
												<c:otherwise>
													<option value="${status.end-i+2000}">${status.end-i+2000}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									${alart[0]}</p>
	                </div>
                </div>
                <div class="row">
	                <div class="col-sm-5 col-xs-12">
	                    <label for="month">月を選択&nbsp;<span class="label label-danger">必須</span></label>
	                </div>
	                <div class="col-sm-7 col-xs-12">
		                <p>：<select id="month" name="month" style="width: 100px" onChange="doExecute2('earningsCheckMonth')">
										<c:forEach var="i" begin="0" end="12" >
											<c:choose>
												<c:when test="${i==0}">
													<option value="${i}"></option>
												</c:when>
												<c:otherwise>
													<option value="${i}">${i}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									${alart[1]}</p>
	                </div>
                </div>
            </form>
			<c:choose>
				<c:when test="${empty earningsEtc.year and empty earningsEtc.month}"></c:when>
				<c:when test="${earningsEtc.year!='1999' and earningsEtc.month!='0'}">
	                <table class="table table-bordered table-hover">
	                    <thead class="thead-dark">
	                        <tr>
	                            <th scope="col">JANコード</th>
	                            <th scope="col">出庫日</th>
	                            <th scope="col">出庫数</th>
	                            <th scope="col">単価</th>
	                            <th scope="col">合計金額</th>
	                        </tr>
	                    </thead>
		                <c:forEach var="earnings" items="${earningsList}">
		                    <tbody>
		                        <tr>
									<td>${earnings.janCode}</td>
									<td>${earnings.syukoYMD}</td>
									<td>${earnings.syukoPCS}</td>
									<td>${earnings.unitPrice}</td>
									<td>${earnings.totalPrice}</td>
		                        </tr>
		                    </tbody>
		                </c:forEach>
	                </table>
				    <hr>
				    <div class="row">
				        <div class="col-md-2 col-xs-12">
				        	<p>月合計</p>
				        </div>
				        <div class="row col-md-10 col-xs-12">
					        <div class="row col-md-6 col-xs-12">
						        <div class="col-md-6 col-xs-12">
						            <p>個数</p>
						        </div>
						        <div class="col-md-6 col-xs-12">
						            <p>:
				   						<c:choose>
											<c:when test="${earningsEtc.year=='1999' or earningsEtc.month=='0'}"></c:when>
											<c:otherwise>${earningsEtc.sumSyukoPcs}</c:otherwise>
										</c:choose>
									</p>
						        </div>
					        </div>
					        <div class="row col-md-6 col-xs-12">
						        <div class="col-md-6 col-xs-12">
						            <p>売上金額</p>
						        </div>
						        <div class="col-md-6 col-xs-12">
						            <p>:
										<c:choose>
											<c:when test="${earningsEtc.year=='1999' or earningsEtc.month=='0'}"></c:when>
											<c:otherwise>${earningsEtc.sumTotalPirce}</c:otherwise>
										</c:choose>
									</p>
						        </div>
					        </div>
				        </div>
				    </div>
				</c:when>
			</c:choose>
        </div>
    </div>

</div>

<%@ include file="../footer.jsp" %>