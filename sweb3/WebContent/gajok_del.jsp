<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String f_bun = request.getParameter("f_bun");
String gogek_no = request.getParameter("gogek_no");

%>
<jsp:useBean id="bean" class="pack.GogekBean"/>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="pooling" class="pack.GogekPooling"/>

<%
	boolean b = pooling.deleteData(bean);
	if(b)	out.println("삭제 완료");
	else	out.println("번호를 확인해주세요");
%>