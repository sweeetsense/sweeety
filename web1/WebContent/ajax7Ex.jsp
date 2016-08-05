<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
{"sawon" : 
[
<%
request.setCharacterEncoding("utf-8");

String sabun = request.getParameter("sawon_no");
String irum = request.getParameter("sawon_name");

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;
String result = "";
try{
   Class.forName("oracle.jdbc.driver.OracleDriver");
   
}catch(Exception e){
   System.out.println("연결 오류: " + e);
   return;
}

try{
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
	pstmt = conn.prepareStatement("select * from sawon left outer join gogek on sawon_no=gogek_damsano");
	rs = pstmt.executeQuery();
	while(rs.next()){
		result+="{";
		result+="\"sawon_no\": \""+rs.getString("sawon_no")+"\",";
		result+="\"sawon_name\": \""+rs.getString("sawon_name")+"\",";
		result+="\"gogek_name\": \""+rs.getString("gogek_name")+"\",";
		result+="\"gogek_tel\": \""+rs.getString("gogek_tel")+"\",";
		result+="\"gogek_jumin\": \""+rs.getString("gogek_jumin")+"\",";
		result+="\"gogek_gen\": \""+rs.getString("gogek_gen")+"\"";
		result+="},";
	}
	if(result.length()>0){
		result = result.substring(0,result.length()-1);
	}
	out.println(result);
	rs.close();
	pstmt.close();
	conn.close();
}catch(Exception e2){
	System.out.println("db 오류: "+e2);
}
%>
] 
}
    