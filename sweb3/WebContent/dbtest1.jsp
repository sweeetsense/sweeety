<%@page import="pack.SangpumDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="connbean" class="pack.ConnBean"></jsp:useBean>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>** 상품 자료(beans 사용) **</h2>
<table border="1">
	<tr><th>코드</th><th>상품</th><th>수량</th><th>단가</th></tr>
<%
ArrayList<SangpumDto> list = connbean.getData();
for(SangpumDto a:list){
%>	
	<tr>
		<td><%=a.getCode() %></td>
		<td><%=a.getSang() %></td>
		<td><%=a.getSu() %></td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		<td><%=a.getDan() %></td>
	</tr>
<%	
}
%>	
</table>

<hr>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table border="1">
	<tr><th>코드</th><th>상품</th><th>수량</th><th>단가</th></tr>
	<c:forEach var="s" items="<%=list %>">
	<tr>
		<td>${s.code}</td>
		<td>${s.sang}</td>
		<td>${s.su}</td>
		<td>${s.dan}</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>