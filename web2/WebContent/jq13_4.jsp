<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");

String irum=request.getParameter("irum");
String nai=request.getParameter("nai");

String re="[";
re+="{\"name\":"+"\""+irum+"\",\"age\":"+nai+"}";
re+="]";
out.print(re);
%>