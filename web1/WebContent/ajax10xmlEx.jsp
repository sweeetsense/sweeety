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
String keyword = request.getParameter("keyword");

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try{
	Class.forName("oracle.jdbc.driver.OracleDriver");

	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
	String sql = "select distinct(to_char(sawon_ibsail,'yyyy')) sawon_ibsail from sawon"+ 
			" where to_char(sawon_ibsail,'yyyy') like ? order by sawon_ibsail";
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, keyword+"%");
	rs = pstmt.executeQuery();
	
	while(rs.next()){
%>		
		<sawon>
			<sawon_ibsail><%=rs.getString("sawon_ibsail") %></sawon_ibsail>
		</sawon>
<%		
	}
	
	
}catch(Exception e){
	System.out.println("오류: "+e);
}

%>
</sawons>