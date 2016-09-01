<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8");%>
<jsp:useBean id="bean" class="pack.business.DataDTO"></jsp:useBean>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="processDao" class="pack.business.ProcessDAO"></jsp:useBean>    

<%
processDao.updateData(bean);
response.sendRedirect("list.jsp");
%>