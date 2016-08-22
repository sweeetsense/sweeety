<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>server가 넘겨준 값</h2>
<%
//redirect
String data = request.getParameter("data");
out.println("자료 1: "+data);

//forward
String msg = (String)request.getAttribute("msg");
out.println("자료 2: "+msg);

ArrayList<String> mylist = (ArrayList)request.getAttribute("msg2");
out.println("<br>나의 친구들: ");
for(String f:mylist){
	out.println(f+" ");
}
%>
</body>
</html>