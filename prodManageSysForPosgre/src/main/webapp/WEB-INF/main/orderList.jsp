<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
						<h2>発注一覧画面</h2>
					</div>
					<div class="col-sm-4 col-xs-12 text-right">
						<a href="javascript:logout()"></a>
					</div>
				</div>
			</form>
		</div>
	</header>

	<hr>


<script>
function doExecute(args) {
	var form = document.forms[2];

		form.siji.value =args;

		if(document.getElementById("productNo").value=="" && args!="reset"){
			alert("品番を入力してください。");

		}else{

			form.submit();

		}


}
</script>




<div>
<div>
<%-- document.forms[3]; --%>
	<form action="OrderList.action" method="post">
	<input type="hidden" name="siji" value="">



<div class="row">
<div class="col-sm-6 col-xs-12">

	<p>品番<input type="text" id="productNo" name="productNo" onchange="doExecute('search')" value="${oList.productNo}"
	          style="margin-left: 5px;" >
	<input type="button" value="リセット" onclick="doExecute('reset')"></p>
</div>
</div>


<div class="row">
<div class="col-sm-6 col-xs-12">

	<p>品名<input type="text" id="name" name="name" readOnly value="${oList.productName}"
	      style="border-style: none; border-bottom: 1px solid;
				 margin: 0px 0px 12px 5px ;width: 340px; background-color: azure;"></p>
</div>
</div>

	<p>検索条件 : 日付<input type="text" maxlength=10 id="startDate" name="startDate" placeholder="例:2023/01/01" onchange="doExecute('searchDate')"  value="${startDate}">
	～<input type="text" maxlength=10 id="endDate" name="endDate" placeholder="例:2023/12/31" onchange="doExecute('searchDate')" value="${endDate}" ></p>

<div class="row">
<div class="col-sm-6 col-xs-12">
	<p>検索 : 並び替え：<input type="radio" id="sort" name="sort" value="ymd" onchange="doExecute('sortDate')">発注日
	<input type="radio" id="sort" name="sort" value="ymd2" onchange="doExecute('sortdueDate')">納期
	<input type="radio" id="sort" name="sort" value="count" onchange="doExecute('sortCount')">数量
	<input type="radio" id="sort" name="sort" value="issue" onchange="doExecute('issue')">仕入先CD</p>
</div>
</div>

<div class="row">
<div class="col-sm-6 col-xs-12">
	<p>検索 : 絞り込み : <input type="radio" id="sibori" name="sibori" value="mika" onchange="doExecute()">未納入
	<input type="radio" id="sibori" name="sibori" value="nounyuzumi" onchange="doExecute()">納入済み</p>
</div>
</div>
</form>
<br>

	<table class="enextable">
	<thead class="enexthead">
	<tr><th>発注日</th><th>納期</th><th>納入日</th><th>発注数量</th><th>仕入先名</th></tr>
	</thead>

	<tbody>
	<c:forEach var="enEx" items="${oList.oOrder}">
	 <tr>
	<td>${enEx.orderDate}</td>
	<td>${enEx.deliveryDate}</td>
	<td>${enEx.dueDate}</td>
	<td>${enEx.orderQty}</td>
	<td title="${enEx.supplierNo}">${enEx.supplierName}</td>
	</tr>
	</c:forEach>
	</tbody>

	</table>
</div>
</div>
</div>



<script>
document.addEventListener('DOMContentLoaded', messageAlert());

function messageAlert() {
	 var recvMSG = "${message}";

	 if (recvMSG != null && recvMSG !="") {
		 alert(recvMSG);
	}
	 checkRadio();
	 checkRadio2();
}

function checkRadio() {
	var sel ="${sort}";
	var form =document.getElementsByName("sort")
	for(i=0; i<form.length;i++){
		if(form[i].value ==sel){
			form[i].checked =true;
			}
	}
}



function checkRadio2() {
	var sel = "${sibori}";
	var form = document.getElementsByName("sibori")
	for (i = 0; i < form.length; i++) {
		if (form[i].value == sel) {
			form[i].checked = true;
		}
	}
}



</script>

<%@ include file="../footer.jsp"%>