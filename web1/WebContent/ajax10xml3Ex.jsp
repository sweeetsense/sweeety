<?xml version = "1.0" encoding = "utf-8" ?>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
	pageEncoding="UTF-8"%>
<gogeks>
<%
request.setCharacterEncoding("utf-8");
String sawon_name = request.getParameter("sawon_name");
System.out.println("sawon_name="+sawon_name); 

Connection conn = null;
PreparedStatement pstmt,pstmt2 = null;
ResultSet rs,rs2 = null;

try{
	Class.forName("oracle.jdbc.driver.OracleDriver");

	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
	String sql = "select gogek_no, gogek_name, gogek_jumin, gogek_tel from gogek join sawon"+ 
			" on gogek_damsano = sawon_no where sawon_name = ? order by sawon_name";
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1,sawon_name);
	rs = pstmt.executeQuery();
	
	while(rs.next()){
%>		
		<gogek>
			<gogek_no><%=rs.getString("gogek_no") %></gogek_no>
			<gogek_name><%=rs.getString("gogek_name") %></gogek_name>
			<gogek_jumin><%=rs.getString("gogek_jumin") %></gogek_jumin>
			<gogek_tel><%=rs.getString("gogek_tel") %></gogek_tel>
		</gogek>
<%		
	}
	
	
}catch(Exception e){
	System.out.println("오류: "+e);
}
%>
</gogeks>
