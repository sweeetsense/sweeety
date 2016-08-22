<%@page import="java.awt.Robot"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
int code = Integer.parseInt(request.getParameter("code"));
//System.out.println(code);

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try{
	Class.forName("com.mysql.jdbc.Driver");   
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123");
	pstmt = conn.prepareStatement("select code from sangdata where code = ?");
	pstmt.setInt(1, code);
	rs = pstmt.executeQuery();
	
	if(rs.next()){
		pstmt = conn.prepareStatement("delete from sangdata where code = ?");
		pstmt.setInt(1, code);
		pstmt.executeUpdate();
		response.sendRedirect("DbSangpum.jsp");
	}else{
		out.println("없는 번호입니다.");
		return;
	}
}catch(Exception e){
	System.out.println("db오류");	
}

%>    