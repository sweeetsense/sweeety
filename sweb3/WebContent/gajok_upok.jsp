<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="bean" class="pack.GogekBean"/>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="pooling" class="pack.GogekPooling"/>
<%
request.setCharacterEncoding("utf-8");
String gogek_no = request.getParameter("gogek_no");
String f_bun = request.getParameter("f_bun");
//System.out.println("b: "+gogek_no);
boolean b = pooling.updateData(bean);
if(b == true)	out.println("수정 완료");
else		out.println("번호가 올바르지 않습니다.");
%>