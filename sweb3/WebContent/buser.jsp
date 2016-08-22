<%@page import="pack.ExdbDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:useBean id="buser" class="pack.ExPooling"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String sawon_name = request.getParameter("sawon_name");
System.out.println(sawon_name);
ArrayList<ExdbDto> list = buser.getData();
for(ExdbDto a:list){
	if(sawon_name.equals(a.getSawon_name())){
%>	
부서 번호: <%=a.getNo() %><br>
부서명:	<%=a.getBuser() %><br>
전화: <%=a.getTel() %><br>
위치: <%=a.getLoc() %><br>
		
<%		
	}
}
%>
</body>
</html>