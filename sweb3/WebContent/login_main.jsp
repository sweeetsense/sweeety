<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1" style:"width: 80%; margin-left:auto; margin-right: auto;">
	<tr>
		<td>
		<% if(session.getAttribute("id") == null){ %>
			<%@include file="login_aa.jsp" %>		
		<%}else{ %>
			<%@include file="login_bb.jsp" %>
		<%} %>
		</td>
	</tr>
	<tr>
		<td>
		<iframe name="frame" width="100%" style="border-style: none;"></iframe>
		</td>
	</tr>
</table>
</body>
</html>