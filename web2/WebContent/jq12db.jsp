<%@ page import = "java.sql.*" %>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>

[
<%
	request.setCharacterEncoding("utf-8");
 	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String result = "";
	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
		pstmt = conn.prepareStatement("select * from sawon order by sawon_no");
		rs = pstmt.executeQuery();
		
		while(rs.next()){
			result+="{";
			result+="\"sawon_no\": \""+rs.getString("sawon_no")+"\",";
			result+="\"sawon_name\": \""+rs.getString("sawon_name")+"\",";
			result+="\"buser_num\": \""+rs.getString("buser_num")+"\"";
			result+="},";
		}
		if(result.length()>0){
			result = result.substring(0, result.length()-1);
		}
		out.println(result);
		
		rs.close();
		pstmt.close();
		conn.close();
	}catch(Exception e){
		System.out.println("db오류: "+e);
	}
%>
]
