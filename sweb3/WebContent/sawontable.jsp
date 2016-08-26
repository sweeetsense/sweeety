<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String sawon_no = (String)session.getAttribute("sawon_no");
String sawon_name = (String)session.getAttribute("sawon_name");
%>
<form action="sawon2.jsp">
로그인 성공! <%=sawon_name %>님 환영합니다.
<input type="submit" value="로그아웃">
</form>
</body>
</html>