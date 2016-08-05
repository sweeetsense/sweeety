<?xml version = "1.0" encoding = "utf-8" ?>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
	pageEncoding="UTF-8"%>
<sawons>
<%
request.setCharacterEncoding("utf-8");
String year = request.getParameter("year");
//System.out.println("year="+year); 

Connection conn = null;
PreparedStatement pstmt,pstmt2 = null;
ResultSet rs,rs2 = null;

try{
	Class.forName("oracle.jdbc.driver.OracleDriver");

	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
	String sql = "select sawon_name from sawon"+ 
			" where to_char(sawon_ibsail,'yyyy') = ? order by sawon_name";
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, year);
	rs = pstmt.executeQuery();
	
	while(rs.next()){
%>		
		<sawon>
			<sawon_name><%=rs.getString("sawon_name") %></sawon_name>
		</sawon>
<%		
	}
	
	
}catch(Exception e){
	System.out.println("오류: "+e);
}
%>
</sawons>
