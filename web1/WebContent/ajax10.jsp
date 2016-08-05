<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
String keyword = request.getParameter("keyword");
System.out.println(keyword);

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try{
   Class.forName("oracle.jdbc.driver.OracleDriver");

   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
   String sql = "select sawon_name from sawon where sawon_name like ?";
   pstmt = conn.prepareStatement(sql);
   pstmt.setString(1, keyword+"%");
   rs = pstmt.executeQuery();
   
   ArrayList<String> list = new ArrayList<String>();
   while(rs.next()){
		list.add(rs.getString(1));
   }
   out.print(list.size());
   out.print("|");
   for(int i=0;i<list.size();i++){
	   String data = list.get(i);
	   out.print(data);
	   if(i<list.size()-1){
		   out.print(",");
	   }
   }
   rs.close();
   pstmt.close();
   conn.close();
}catch(Exception e2){
   System.out.println("오류: " + e2);
}
%>