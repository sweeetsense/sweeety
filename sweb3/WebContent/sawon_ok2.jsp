<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="bean" class="pack.SawonBean"/>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="pooling" class="pack.SawonPooling"/>

<%
request.setCharacterEncoding("utf-8");
System.out.println(request.getParameter("sawon_no")+request.getParameter("sawon_name"));
boolean b = pooling.loginData(bean);
if(b){
	session.setAttribute("sawon_no", request.getParameter("sawon_no"));
	session.setAttribute("sawon_name", request.getParameter("sawon_name"));
	session.setAttribute("err", "false");
}
else{
	session.removeAttribute("sawon_no");
	session.setAttribute("err", "true");
}

response.sendRedirect("sawon_main.jsp");
%>    
