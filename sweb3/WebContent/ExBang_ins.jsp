<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="bean" class="pack.ExBangBean"/>
<jsp:setProperty property="*" name="bean"/>    
<jsp:useBean id="pooling" class="pack.ExBangPooling"/>
<%
boolean b = pooling.insertData(bean);

if(b)	response.sendRedirect("Exbang.jsp");
else	response.sendRedirect("dbtest2_fail.html");
%>