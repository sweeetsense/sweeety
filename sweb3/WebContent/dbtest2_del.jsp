<%@page import="pack.SangpumDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String code = request.getParameter("code");
//System.out.println(code);
%>
<jsp:useBean id="bean" class="pack.SangpumBean"/>
<jsp:setProperty property="*" name="bean" />
<jsp:useBean id="connBeanPooling" class="pack.ConnBeanPooling" />

<%
boolean b = connBeanPooling.deleteData(bean);

if(b){
	response.sendRedirect("dbtest2.jsp");
//	System.out.println("true");	
}else{
	response.sendRedirect("dbtest2_fail.html");
//	System.out.println("false");	
}
%>
