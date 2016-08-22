<%@page import="pack.ExdbDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:useBean id="pooling" class="pack.ExPooling"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
	<tr><th>사번</th><th>이름</th><th>직급</th></tr>
<%
ArrayList<ExdbDto> list = pooling.getData();
for(ExdbDto a:list){
%>	
		<jsp:useBean id="dto" class="pack.ExdbDto"/>
		<jsp:setProperty property="buser_name" name="dto" value="<%=a.getSawon_name() %>"/>
	<tr>
		<td><%=a.getSawon_no()%></td>
		<td><a href="buser.jsp?sawon_name=<%=a.getSawon_name()%>" target="buser" name="buser_name"><%=a.getSawon_name()%></a></td>
		<td><%=a.getSawon_jik()%></td>
	</tr>
<%		
} 
%>
</table>
<hr>
<iframe src="about:blank" name="buser"></iframe>
</body>
</html>