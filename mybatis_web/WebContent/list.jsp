<%@page import="pack.business.DataDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="processDao" class="pack.business.ProcessDAO"/>    
<%ArrayList<DataDTO> list = (ArrayList)processDao.selectDataAll();%>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
상품 자료(mybatis)<p>
<table border="1">
<c:forEach var="s" items="<%=list %>">
	<tr>
		<td><a href="delete.jsp?code=${s.code}">${s.code }</a></td>
		<td><a href="update.jsp?code=${s.code}">${s.sang }</a></td>
		<td>${s.su }</td>
		<td>${s.dan }</td>
	</tr>
</c:forEach>
</table>
</body>
</html>