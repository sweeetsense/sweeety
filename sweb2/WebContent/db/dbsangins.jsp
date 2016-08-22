<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>
<%
	request.setCharacterEncoding("utf-8");
	String sang = request.getParameter("sang");
	String su = request.getParameter("su");
	String dan = request.getParameter("dan");
	
//자료 검사 2차 - 1차는 js에서 수행
Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;


if(sang.equals("") || sang == null || su.equals("") || su == null || dan.equals("") || dan == null){
	response.sendRedirect("sangins.html");
	return;
}
try{
//수량과 단가는 숫자만 허용
	int suryang = Integer.parseInt(su);
	su = Integer.toString(suryang);
	int danga = Integer.parseInt(dan);
	dan = Integer.toString(danga);
}catch(Exception e){
	response.sendRedirect("sangins.html");
	return;
}

try{
	Class.forName("com.mysql.jdbc.Driver");   
	
}catch(Exception e){
	System.out.println("driver loading 실패: "+e);
	out.println("driver loading 실패: "+e);
	return;
}

try{
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123");
	//신상 코드를 구함
	pstmt = conn.prepareStatement("select max(code) from sangdata");
	rs = pstmt.executeQuery();
	rs.next();
	int maxCode = rs.getInt(1);
	//System.out.println("가장 큰 코드: "+maxCode);
	
	pstmt = conn.prepareStatement("insert into sangdata values(?,?,?,?)");
	pstmt.setInt(1, maxCode+1);
	pstmt.setString(2, sang);
	pstmt.setString(3, su);
	pstmt.setString(4, dan);
	pstmt.executeUpdate();
	
	//request.getRequestDispatcher("DbSangpum.jsp").forward(request, response);
	response.sendRedirect("DbSangpum.jsp");
}catch(Exception e2){
	System.out.println("처리 실패: "+e2);
	out.println("처리 실패: "+e2);
}finally{
	if(rs!= null)	rs.close();
	if(pstmt != null)		pstmt.close();
	if(conn != null)		conn.close();
}
%>