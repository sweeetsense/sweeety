<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String id = request.getParameter("id");
String pwd = request.getParameter("pwd");
System.out.println(id+" "+pwd);
if((id.equalsIgnoreCase("admin") && pwd.equals("123")) ||
		(id.equalsIgnoreCase("user") && pwd.equals("111"))){
	session.setAttribute("id", id);
	response.sendRedirect("LoginOk.jsp");
}else{
	response.sendRedirect("LoginFail.html");
}
%>    