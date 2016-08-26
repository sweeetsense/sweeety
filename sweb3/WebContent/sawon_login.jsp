<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강남물산 직원</title>
<script type="text/javascript">

</script>
</head>
<body>
<form action="sawon_ok.jsp">
<table border="1" style="width: 100%;">
<tr>
	<td colspan="4" style="text-align: center;"><b>강남물산 직원</b></td>
</tr>
<tr>
	<td colspan="4" style="text-align: center;">
	<%= session.getAttribute("no")+"번" %>
	<%= session.getAttribute("name")+"님, 안녕하세요!" %>
	<input type="hidden" name="no"> 
	<input type="hidden" name="name">
	<input type="submit" value="Logout">
	</td>
</tr>
<tr style="background-color: orange;">
	<td style="text-align: center;">
	<a href="sawon_all.jsp" target="frame">전체고객</a>
	</td>
	<td style="text-align: center;">
	<a href="sawon_my.jsp?num=<%= session.getAttribute("no") %>" target="frame">내관리고객</a>
	</td>
	<td style="text-align: center;">
	<a href="office_main.html">홈페이지</a>
	</td>
</tr>
</table>
<hr>

</form>
</body>
</html>