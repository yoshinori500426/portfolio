<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<title>Insert title here</title>

<%@ include file="../header.jsp" %>

<%--

--%>



	    <div class="box">

                <div class="main">
                    <%-- document.forms[1]; --%>
                        <form action="Logout.action" method="post">
                            <input type="hidden" name="toAction">
                            <div class="row">
                                <div class="col-sm-8 col-xs-12">
                                    <h2>入出庫画面</h2>
                                </div>
                                <div class="col-sm-4 col-xs-12 text-right">
                                    <a href="javascript:logout()" ></a>
                                </div>
                            </div>
                        </form>
                </div>


            <hr>




<script>
function doExecute7(args) {
	var form = document.forms[2];
	if(args==null){
		form.siji.value = form.dousa.value;
	}else{
	form.siji.value = args;
	}
	form.submit();

}

</script>

<script>
document.addEventListener('DOMContentLoaded',messageAlert());
function messageAlert() {
	var recvMSG = "${message}";
	if (recvMSG !=null && recvMSG !=""){
		alert(recvMSG);
	}
}
</script>



<script>
function scrset(){
	var recvProductName = "${productmaster.productName}"
	var recvDousa = "${dousa}"
	if(recvDousa=="nyukoregist" && recvProductName != null  ){
		document.getElementById("name").removeAttribute("disabled")
	}
	if(recvDousa=="syukoregist" && recvProductName != null  ){
		document.getElementById("name").removeAttribute("disabled")
	}

}
</script>

<script>

function nyuko(){
	var form = document.forms[2];
	form.dousa.value = "nyukoregist";
	document.getElementById("name").removeAttribute("disabled")
	document.getElementById("name").setAttribute("ReadOnly", true)

}

function syuko(){
	var form = document.forms[2];
	form.dousa.value = "syukoregist";
	document.getElementById("name").removeAttribute("disabled")
	document.getElementById("name").setAttribute("ReadOnly", true)
}

</script>

<script>
function qtyzero(){
	if(document.nyusyu.qty.value == 0){
		alert("0より大きな数字を入力してください。");
		return true;
	}else{
		return false;
	}
}
</script>

<script>
function change() {
	var element;

	if(document.getElementById("review")){
		element = document.getElementById("button");
		element.disabled = false;
	}else{
		element = document.getElementById("button");
		element.disabled = true;
	}

}
</script>

</head>


<div>
<div>
<%-- document.forms[3]; --%>
<form action="EntryExitInfo.action" method="post"  name="nyusyu" id="nyusyu">
<input type="hidden" name="siji">
<input type="hidden" name="dousa" value="${dousa}">
<input type="hidden" name="ko">

<div class="row">
<div class="col-sm-6 col-xs-12">
品番:<input type="text"  id="code" name="code"   onchange="doExecute7('idSearch')"  value="${productmaster.productNo}"  onblur="num()" required><br><br>
</div>
</div>

<div class="row">
<div class="col-sm-6 col-xs-12">
品名:<input type="text" id="name" name="name" value="${productmaster.productName}" disabled ><br><br>
</div>
</div>

<div class="row">
<div class="col-sm-6 col-xs-12">
<label><input type="radio" id="ko" name="ko" value="nyuko"  onclick="nyuko()" style="transform:scale(1.5);margin-left: 3em" required>入庫</label>
<label><input type="radio" id="ko" name="ko" value="syuko"  onclick="syuko()" style="transform:scale(1.5);margin-left: 3em" required>出庫</label><br><br>
</div>
</div>

<div class="row">
<div class="col-sm-6 col-xs-12">
数量:<input type="text" id="qty" name="qty" onblur="qtyzero()" required><br><br>
</div>
</div>

<div class="row">
<div class="col-sm-6 col-xs-12">
理由:<textarea name="review" id="review" rows="1" cols="30" onchange="change()" placeholder="必須項目! 入力内容確認後 確定ｸﾘｯｸ" required  ></textarea><br><br>
</div>
</div>

<div class="row">
<div class="col-sm-6 col-xs-12">
<button type="button" id="button" name="button"  onclick="doExecute7()" class=" btn-danger" disabled >確定</button>
<button type="reset" onclick="doExecute7('init')"  class=" btn-primary">キャンセル</button>
</div>
</div>

</form>
</div>
</div>
</div>
<%@ include file="../footer.jsp"%>