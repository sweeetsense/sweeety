<?xml version="1.0" encoding="utf-8"?>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>

<sangpums>
<%
request.setCharacterEncoding("utf-8");
//String irum="%";
String irum = request.getParameter("name");
if(irum == null){
	irum="%";
}
System.out.println(irum);

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try{
	Class.forName("oracle.jdbc.driver.OracleDriver");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
	pstmt = conn.prepareStatement("select * from sangdata where sang like ? order by code");
	pstmt.setString(1, irum+"%");
	rs = pstmt.executeQuery();
	while(rs.next()){
%>
	<sangpum>
		<code><%=rs.getString("code") %></code>
		<sang><%=rs.getString("sang") %></sang>
		<su><%=rs.getString("su") %></su>
		<dan><%=rs.getString("dan") %></dan>
	</sangpum>
<%		
	}
}catch(Exception e){
	System.out.println("db오류: "+e);
}
%>    
</sangpums>    