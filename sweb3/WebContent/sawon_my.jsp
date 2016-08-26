<%@page import="sawon.SawonDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="log" class="sawon.SawonConnBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1" style="width: 100%">
<thead>
<tr style="text-align: center;">
		<td>고객번호</td>
		<td>고객명</td>
		<td>주민번호</td>
		<td>고객전화</td>
</tr>
</thead>
<%
 String num = request.getParameter("num");
 ArrayList<SawonDTO> list = log.getMyData(num);
 int count = 0;
 for(SawonDTO s :list){
	 // 
%>
  <tr>
  <td><%= s.getNo() %></td>
  <td><%= s.getName() %></td>
  <td><%= s.getJumin() %></td>
  <td><%= s.getTel() %></td>
  </tr>
<%
count ++;
}
%>
</table>
<br>
<b>총 인원수 : <%= count %>명</b>
</body>
</html>