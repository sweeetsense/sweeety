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
String gogek_no = (String)session.getAttribute("gogek_no");
String gogek_name = (String)session.getAttribute("gogek_name");
%>
<form action="gogek.jsp">
로그인 성공! <%=gogek_name %>님 환영합니다.
<input type="submit" value="로그아웃">
</form>
<a href="gogektableall.jsp?gogek_no=<%=session.getAttribute("gogek_no") %>" target="frame" >가족목록</a>&nbsp;&nbsp;
<a href="gajok_ins.jsp?gogek_no=<%=session.getAttribute("gogek_no") %>" target="frame" >가족등록</a>&nbsp;&nbsp;
<a href="office_main.html">홈페이지</a>
</body>
</html>