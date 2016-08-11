<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
String irum="한국인";
String nai="23";

String re="[";
re+="{\"name\":"+"\""+irum+"\",\"age\":"+nai+"}";
re+="]";
out.print(re);
%>