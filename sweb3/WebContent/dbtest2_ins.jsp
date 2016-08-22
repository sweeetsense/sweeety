<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="bean" class="pack.SangpumBean"/>
<jsp:setProperty property="*" name="bean" />
<jsp:useBean id="connBeanPooling" class="pack.ConnBeanPooling"/>
<%
boolean b = connBeanPooling.insertData(bean);

if(b)
	response.sendRedirect("dbtest2.jsp");
else
	response.sendRedirect("dbtest2_fail.html");
%>
