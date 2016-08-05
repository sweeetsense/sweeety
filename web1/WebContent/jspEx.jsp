<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	window.onload = function() {
		document.getElementById("btnA").onclick = showdata;
		//document.getElementById("btnM").onclick = showdata1;
		//document.getElementById("btnW").onclick = showdata2;
	}
	function showdata() {
<%
			String str = "";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception e) {
				System.out.println("연결 오류: " + e);
				return;
			}
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
				pstmt = conn.prepareStatement(
						"select sawon_no, sawon_name, sawon_jik, to_char(sawon_ibsail,'yyyy') sawon_ibsail"
								+ " from sawon where sawon_gen like ?  order by sawon_no");

				pstmt.setString(1, "%");

				rs = pstmt.executeQuery();

				while (rs.next()){
					str += rs.getString("sawon_no") + " " + rs.getString("sawon_name") + " " + rs.getString("sawon_jik")+ " " + rs.getString("sawon_ibsail") + " ";
				}
				System.out.println(str);
			} catch(Exception e2){
				System.out.println("오류: "+e2);
			}
				
%>

	}

</script>
</head>
<body>
	<input type="radio" id="btnA" name="radio" checked />전체
	<input type="radio" id="btnM" name="radio" />남
	<input type="radio" id="btnW" name="radio" />여
	<hr>
	<div id="show"></div>
</body>
</html>