<%@page import="java.util.ArrayList"%>
<%@page import="pack.GogekBean"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
String gogek_no = request.getParameter("gogek_no");
String gogek_name = request.getParameter("gogek_name");
//System.out.println(gogek_no+" "+gogek_name);
%>    
<jsp:useBean id="bean" class="pack.GogekBean"/>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="pooling" class="pack.GogekPooling"/>

<%
boolean b = pooling.loginData(bean);
if(b){
	session.setAttribute("gogek_no", gogek_no);
	session.setAttribute("gogek_name", gogek_name);
}
else{
	session.removeAttribute("gogek_no");

}
response.sendRedirect("gogek_main.jsp");
%>
