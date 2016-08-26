<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>직원</title>
</head>
<body>

<table border="1" style="width: 80%; margin-left: auto; margin-right: auto;">
	<tr>
		<td colspan="4">
		<% if(session.getAttribute("name") == null){ %>
			
			<%@include file="sawon_unlogin.jsp" %> <!--  이동하는것 -->
			
		<%}else{ %>
			<%@include file="sawon_login.jsp" %> 
		<%} %>
		</td>
	</tr>
	
	<tr style="height: 50%">	
		<td colspan="4">
		<iframe name="frame" width="100%" style="border-style: none;"></iframe>
		</td>
	</tr>
</table>
</body>
</html>