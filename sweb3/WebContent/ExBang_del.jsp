<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String code = request.getParameter("code");
//System.out.println(code);
%>    
<jsp:useBean id="bean" class="pack.ExBangBean"/>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="pooling" class="pack.ExBangPooling"/>

<%
boolean b = pooling.deleteData(bean);
if(b)	response.sendRedirect("Exbang.jsp");
else	response.sendRedirect("dbtest2_fail.html");
%>