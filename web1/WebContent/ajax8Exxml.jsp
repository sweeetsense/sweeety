<?xml version = "1.0" encoding = "utf-8" ?>
<%@page import = "java.util.*" %>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<gogeks>    
<%
request.setCharacterEncoding("utf-8");
String gen = request.getParameter("gen");
if(gen==""){
	gen="%";
}
System.out.println(gen);
Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try{
	 Class.forName("oracle.jdbc.driver.OracleDriver");
     conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
     String sql = "select gogek_name, substr(gogek_jumin,8,1) gogek_gen, gogek_tel, sawon_name, buser_tel from sawon join gogek"+
    		 " on sawon_no = gogek_damsano left outer join buser on buser_num = buser_no"+ 
    		 " where substr(gogek_jumin,8,1) like ?";
     pstmt = conn.prepareStatement(sql);
     pstmt.setString(1, gen);
     rs = pstmt.executeQuery();
     
     String ss= "";
     while(rs.next()){
//    	 ss += "("+rs.getString("gogek_name")+" "+rs.getString("gogek_tel")+" "+rs.getString("sawon_name")+" "+rs.getString("buser_tel")+")";
%>
	<gogek>
		<gogek_name><%=rs.getString("gogek_name") %></gogek_name>
		<gogek_tel><%=rs.getString("gogek_tel") %></gogek_tel>
		<sawon_name><%=rs.getString("sawon_name") %></sawon_name>
		<buser_tel><%=rs.getString("buser_tel") %></buser_tel>
	</gogek>	
<%  	 
     }
/*       out.print("<ele>");
     out.print("<make>"+ss+"</make>");
     out.print("</ele>");  */

     rs.close();
     pstmt.close();
     conn.close();
}catch(Exception e){
	System.out.println("오류: " + e);
}
%>
</gogeks>