<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
window.onload = function(){
	document.getElementById("btnOk").onclick = func1;
}
function func1(){
	frm.submit();
}
</script>
</head>
<body>
<form name="frm" action="ExBang_ins.jsp">
	<table>
		<tr>
			<td>작성자: </td>
			<td><input type="text" name="name"></td>
		</tr>
		<tr>
			<td>제목: </td>
			<td><input type="text" name="subject"></td>
		</tr>
		<tr>
			<td>내용: </td>
			<td ><textarea rows="5" cols="30" name="content"></textarea></td>
		</tr>
		<tr>
			<td colspan='2' style="text-align: center;">
			<input type="button" value="등록" id="btnOk">
			</td>
		</tr>
	</table>
</form>
</body>
</html>