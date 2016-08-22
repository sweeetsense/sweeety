<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
String data = request.getParameter("data");
String msg = "Mr."+data;

//1. redirect 
//response.sendRedirect("JspCall3.jsp?data="+msg);

//2. forward
request.setAttribute("msg", msg);
ArrayList<String> list =  new ArrayList<String>();
list.add("tom");
list.add("james");
list.add("page");
request.setAttribute("msg2", list);

/* RequestDispatcher dispatcher = request.getRequestDispatcher("JspCall3.jsp");
dispatcher.forward(request, response); */
%>    
<jsp:forward page="JspCall3.jsp"></jsp:forward>