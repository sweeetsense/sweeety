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
String gen = request.getParameter("gen");
if(gen==""){
	gen="%";
}
System.out.println(gen);
System.out.println("aa");
/* Connection conn = null;
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
    String sql = "select gogek_name, substr(gogek_jumin,8,1) gogek_gen, gogek_tel, sawon_name, buser_tel from sawon join gogek"+
   		 " on sawon_no = gogek_damsano left outer join buser on buser_num = buser_no"+ 
   		 " where substr(gogek_jumin,8,1) like ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, gen);
    rs = pstmt.executeQuery();
	while(rs.next()){
		result+="{";
		result+="\"gogek_name\": \""+rs.getString("gogek_name")+"\",";
		result+="\"gogek_gen\": \""+rs.getString("gogek_gen")+"\",";
		result+="\"gogek_tel\": \""+rs.getString("gogek_tel")+"\",";
		result+="\"sawon_name\": \""+rs.getString("sawon_name")+"\",";
		result+="\"buser_tel\": \""+rs.getString("buser_tel")+"\"";
		result+="},";
	}
	if(result.length()>0){
		result = result.substring(0,result.length()-1);
	}
	rs.close();
	pstmt.close();
	conn.close();
}catch(Exception e2){
	System.out.println("db 오류: "+e2);
} */
%>
] 
}