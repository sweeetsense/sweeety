<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
[	
<%
request.setCharacterEncoding("utf-8");

String bname=request.getParameter("busername");
//System.out.println(bname);
if(bname == null){
	bname = "%";
}

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try{
		Class.forName("oracle.jdbc.driver.OracleDriver");

	   	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
	   	String sql = "select sawon_no, sawon_name, sawon_jik, count(gogek_no) count from sawon" 
	   			+" left outer join buser on buser_num = buser_no" 
	   			+" left outer join gogek on sawon_no = gogek_damsano"
	   			+" where buser_name like ?"
				+" group by sawon_no, sawon_name, sawon_jik order by sawon_no";
	   	
	   	pstmt = conn.prepareStatement(sql);
	   	pstmt.setString(1, bname);
	   	rs = pstmt.executeQuery();
	   	
	   	String result="";
	   	while(rs.next()){
	   		result += "{";
	   		result +="\"sawon_no\":"+"\""+rs.getString("sawon_no")+"\",";
	   		result +="\"sawon_name\":"+"\""+rs.getString("sawon_name")+"\",";
	   		result +="\"sawon_jik\":"+"\""+rs.getString("sawon_jik")+"\",";
	   		result +="\"count\":"+"\""+rs.getString("count")+"\"";
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