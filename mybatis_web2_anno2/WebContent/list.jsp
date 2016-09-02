<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="pack.business.DataDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="processDAO" class="pack.business.ProcessDAO" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="materialize/js/jquery-3.1.0.js"></script>
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.css"> 
<script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#test').DataTable();
});
</script>
</head>
<body>
<h3>회원 자료</h3>
<a href="ins.jsp">회원 추가</a><br>
<a href="list.jsp?keyword=a">id검색</a><br>
<a href="list.jsp">전체보기</a><br>
	<table id="test">
		<thead>
			<tr>
			<th>아이디</th><th>이름</th><th>비밀번호</th><th>가입일</th>
			</tr>
		</thead>
		<tbody>
<%
//ArrayList<DataDTO> list = (ArrayList) processDAO.selectDataAll();
//검색이 있는 경우
request.setCharacterEncoding("utf-8");
String keyword = request.getParameter("keyword");
Map<String, String> map = new HashMap<String, String>();
map.put("search", keyword);
List<DataDTO> list = processDAO.selectDataAll(map);

 if(list == null){
	 out.println("<tr><td colspan'4'>자료 없음</td></tr>");
 }else{
	 for(DataDTO d:list){
%>
	<%-- <tr>
	<td><%= d.getId() %></td>
	<td><%= d.getName() %></td>
	<td><%= d.getpassword() %></td>
	<td><%= d.getRegdate() %></td>
	</tr> --%>
<%		 
	 }
 }
%>
		<c:forEach var="s" items="<%= list %>">
		<tr>
			<td><a href="up.jsp?id=${s.id }">${s.id}</a></td>
			<td><a href="del.jsp?id=${s.id }">${s.name}</a></td>
			<td>${s.password}</td>
			<td>${s.regdate}</td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>