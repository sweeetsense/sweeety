<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
[	
<%
request.setCharacterEncoding("utf-8");

String sawonnum=request.getParameter("sawonno");
System.out.println(sawonnum);

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try{
		Class.forName("oracle.jdbc.driver.OracleDriver");

	   	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
	   	String sql = "select gogek_name, gogek_tel, gogek_gen, (117-substr(gogek_jumin,1,2)) age from gogek where gogek_damsano = ?";
	   	
	   	pstmt = conn.prepareStatement(sql);
	   	pstmt.setString(1, sawonnum);
	   	rs = pstmt.executeQuery();
	   	
	   	String result="";
	   	while(rs.next()){
	   		result += "{";
	   		result +="\"gogek_name\":"+"\""+rs.getString("gogek_name")+"\",";
	   		result +="\"gogek_tel\":"+"\""+rs.getString("gogek_tel")+"\",";
	   		result +="\"gogek_gen\":"+"\""+rs.getString("gogek_gen").trim()+"\",";
	   		result +="\"age\":"+"\""+rs.getString("age")+"\"";
	   		result+="},";
	   	}
	   	if(result.length()>0){
	   		result = result.substring(0,result.length()-1);
	   	}
	   	
	   	out.println(result);
	   
	   	
}catch(Exception e){
	System.out.println("디비에러: "+e);
}
%>
]