<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("utf-8");
String gogek_no = request.getParameter("gogek_no");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="gajok_insok.jsp" >
	<table style="text-align:center;">
	<tr><td>가족명: <input type="text" name="f_irum" size="17"></td></tr> 
	<tr><td>전화: <input type="text" name="f_tel"></td></tr>
	<tr><td><input type="submit" value="등록"></td></tr>
	<input type="hidden" name="gogek_no" value="<%=gogek_no%>">
	</table> 
</form>
</body>
</html>