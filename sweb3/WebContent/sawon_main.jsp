<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%=session.getAttribute("sawon_no")%>
<%String err = (String)session.getAttribute("err");%>
<%=err %>
<%if(session.getAttribute("sawon_no") == null){%>
	<%@include file="sawon2.jsp"%>
<%}else if((session.getAttribute("sawon_no") == null) && (err.equals("true"))){%>
	<%@include file="sawon2.jsp"%>
	<b style="color:red">일치하는 정보가 없습니다.</b>
<%}else{ %>
	<%@include file="sawontable.jsp"%>
<%} %>
<iframe name="frame" width="100%" height="400" style="border-style: none;"></iframe>

</body>
</html>