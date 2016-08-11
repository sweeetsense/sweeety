<%@page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%
request.setCharacterEncoding("utf-8");
String code = request.getParameter("code");
String sang = request.getParameter("sang");
String su = request.getParameter("su");
String dan = request.getParameter("dan");
//System.out.println(code+" "+sang+" "+su+" "+dan);

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;
String result = "";

try{
	Class.forName("oracle.jdbc.driver.OracleDriver");
}catch(Exception e){
	System.out.println("연결 오류:" + e);
	return;
}

try{	
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
	String sql = "insert into sangdata values(?,?,?,?)";
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, code);
	pstmt.setString(2, sang);
	pstmt.setString(3, su);
	pstmt.setString(4, dan);
	
	int re = pstmt.executeUpdate();
	if(re == 1){
		//out.print("t");	
	}else{
		//out.print("f");
	}
}catch(Exception e2){
	out.print("f");
	System.out.println("에러:" + e2);
}finally{

	pstmt.close();
	conn.close();
}
%>
