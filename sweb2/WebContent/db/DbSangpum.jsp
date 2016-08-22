<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "java.sql.*"%>
    
<%
Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

String pa = request.getParameter("pa");		//페이지 번호 얻기
if(pa == null || pa.equals(""))		pa = "1";

try{
	Class.forName("com.mysql.jdbc.Driver");   
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123");
	
}catch(Exception e){
	System.out.println("db연결 실패: "+e);
	out.println("db연결 실패: "+e);
	return;
}

try{
	//페이지 당 제한된 자료 출력 준비작업-------------------------------
	pstmt = conn.prepareStatement("select count(*) from sangdata");
	rs = pstmt.executeQuery();
	rs.next();
	int recTotal = rs.getInt(1);
	int pageSize = 5;
	int totalPage = recTotal / pageSize;		//전체 페이지 수 얻기
	int re = recTotal % pageSize;
	if(re != 0)		totalPage += 1;
	//System.out.println(totalPage);
		
	pstmt = conn.prepareStatement("select * from sangdata order by code desc");
	rs = pstmt.executeQuery();
	
	//각 페이지 시작번호 받고 이동하기 준비작업....
	int startNum = (Integer.parseInt(pa)-1)*pageSize+1;		//1,6,11... 
	for(int p=1;p<startNum;p++){
		rs.next();		//레코드 포인터 위치: 0,5,10...
	}
	//----------------------------------------------------------------------------------
	
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>** 상품 자료 **</h2>
<a href="sangins.html">상품 추가</a><br>
<a href="sangdel.html">상품 삭제</a><br>
<table border="1">
	<tr><th>코드</th><th>품명</th><th>수량</th><th>단가</th></tr>
	<%
	int i=1;
	while(rs.next() && i<=pageSize){
	%>
	<tr>
		<td><%=rs.getString("code") %></td>
		<td><%=rs.getString("sang") %></td>
		<td><%=rs.getString("su") %></td>
		<td><%=rs.getString("dan") %></td>
	</tr>
	<%	
	i++;
	}
	%>
	<tr>
		<td colspan="4" style="text-align: center;">
		<%
		if(totalPage > 0){
			for(int pNo=1;pNo <= totalPage;pNo++){
		%>
			[<a href="DbSangpum.jsp?pa=<%=pNo %>"><%=pNo %></a>]
		<%				
			}
		}
		%>
		</td>
	</tr>
</table>
</body>
</html>
<%
}catch(Exception e2){
	System.out.println("처리 실패: "+e2);
	out.println("처리 실패: "+e2);
}finally{
	if(rs!= null)	rs.close();
	if(pstmt != null)		pstmt.close();
	if(conn != null)		conn.close();
}
%>