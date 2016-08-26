<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="bean" class="pack.GogekBean"/>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="pooling" class="pack.GogekPooling"/>
<%
request.setCharacterEncoding("utf-8");
/* String f_irum = request.getParameter("name");
String f_tel = request.getParameter("tel");
System.out.println("gg"+gogek_no+f_irum+f_tel); */
String gogek_no = request.getParameter("gogek_no");
boolean b = pooling.insertData(bean);

if(b)	out.println("등록성공");
else 	response.sendRedirect("gajok_ins.jsp");
%>