<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="dto" class="pack.business.DataDTO"/>
<jsp:setProperty property="*" name="dto"/>
<jsp:useBean id="processDAO" class="pack.business.ProcessDAO"></jsp:useBean>
<%
boolean b = processDAO.insertData(dto);
response.sendRedirect("list.jsp");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>