
<%@ page import = "java.sql.*" %>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
{"sangpum" :
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
   pstmt = conn.prepareStatement("select * from sangdata");
   rs = pstmt.executeQuery();
   
   while(rs.next()){
	result += "{";
		result += "\"code\":" +"\""+ rs.getString("code")+"\",";
		result += "\"sang\":" +"\""+ rs.getString("sang")+"\",";
		result += "\"su\":" +"\""+ rs.getString("su")+"\",";
		result += "\"dan\":" +"\""+ rs.getString("dan")+"\"";
	result += "},";	
   }
	if(result.length()>0){
		result = result.substring(0,result.length()-1);
	}
   out.println(result);
   rs.close();
   pstmt.close();
   conn.close();
  
}catch(Exception e2){
   System.out.println("오류: " + e2);
}
%>
 ]
   }

