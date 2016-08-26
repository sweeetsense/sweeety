<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

		<% if(session.getAttribute("gogek_no") == null){ %>
			<%@include file="gogek.jsp" %>		
			<b style="color:red">일치하는 정보가 없습니다.</b>
		<%}else{ %>
			<%@include file="gogektable.jsp" %>
		<%} %>

		<iframe name="frame" width="100%" height="400" style="border-style: none;"></iframe>

</body>
</html>