<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 로그인</title>
<link href="../css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/script.js"></script>
</head>
<body>
<%
String id = request.getParameter("id");
String pwd = request.getParameter("pwd");

if(id.equals("admin") && pwd.equals("111")){
	session.setAttribute("adminOk", id);
	out.println("로그인 성공");
}else{
	out.println("로그인 실패");
}
%>
<br><br>
[<a href="javascript:window.close()">창닫기</a>]
</body>
</html>