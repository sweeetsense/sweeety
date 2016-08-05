<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
{"sawon" : 
[
<%
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
	pstmt = conn.prepareStatement("select * from sawon left outer join buser on buser_num = buser_no where sawon_jik like ? order by sawon_no");
	pstmt.setString(1, "%");
	rs = pstmt.executeQuery();
	while(rs.next()){
		result+="{";
		result+="\"sawon_no\": \""+rs.getString("sawon_no")+"\",";
		result+="\"sawon_name\": \""+rs.getString("sawon_name")+"\",";
		result+="\"sawon_jik\": \""+rs.getString("sawon_jik")+"\",";
		result+="\"buser_name\": \""+rs.getString("buser_name")+"\",";
		result+="\"buser_tel\": \""+rs.getString("buser_tel")+"\",";
		result+="\"sawon_gen\": \""+rs.getString("sawon_gen")+"\"";
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
