<?xml version = "1.0" encoding = "utf-8" ?>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
String buser = request.getParameter("buser");
System.out.println(buser);
Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try{
   Class.forName("oracle.jdbc.driver.OracleDriver");

   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
   String sql = "select sawon_no, sawon_name, sawon_jik from"+
		   " sawon inner join buser on sawon.buser_num = buser.buser_no where buser_name = ?";
   pstmt = conn.prepareStatement(sql);
   pstmt.setString(1, buser);
   rs = pstmt.executeQuery();
   
   String ss = "";
   while(rs.next()){
		ss += "("+ rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+")";
   }
	
   out.print("<ele>");
   out.print("<make>"+ss+"</make>");
   out.print("</ele>");
   
   rs.close();
   pstmt.close();
   conn.close();
}catch(Exception e2){
   System.out.println("오류: " + e2);
}
%>


