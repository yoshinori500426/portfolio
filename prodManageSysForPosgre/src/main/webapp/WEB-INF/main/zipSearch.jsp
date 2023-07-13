<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
function doe(){
	document.forms[0].submit();
}
</script>
<style>
td,th{
border:1px solid black;}
</style>
<body>
<form action="action.ZipSearch.action" method="post">
郵便番号<input type="text" name="zipno" onchange="doe()" value="${zipno}">
<table>
		<thead>
			<tr>
				<th>郵便番号</th>
				<th>都道府県</th>
				<th>市</th>
				<th>村</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${list}">
				<tr>
					<td style="width:100px;">${item.zipno}</td>
					<td style="width:200px;">${item.pref}</td>
					<td style="width:500px;">${item.city}</td>
					<td style="width:500px;">${item.villege}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</form>
</body>
</html>