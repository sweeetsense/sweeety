<%@page import="pack.GogekBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="pooling" class="pack.GogekPooling"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
window.onload = function(){
	document.getElementById("update").onclick = funcUp;
	document.getElementById("delete").onclick = funcDel;
}
function funcUp(){
	var f_bun = document.getElementById("f_bun").value;
	location.href = "gajok_up.jsp?f_bun="+f_bun+"&gogek_no="+<%=request.getParameter("gogek_no")%>;
}
function funcDel(){
	var f_bun = document.getElementById("f_bun").value;
	location.href = "gajok_del.jsp?f_bun="+f_bun+"&gogek_no="+<%=request.getParameter("gogek_no")%>;
}
</script>
<body>
<table border="1" style="text-align:center;">
	<tr>
		<td>가족번호</td><td>가족명</td><td>전화</td>
	</tr>
<%
String gogek_no = request.getParameter("gogek_no");
ArrayList<GogekBean> list = pooling.tableData(gogek_no);
for(GogekBean a: list){
%>
	<tr>
		<td><%=a.getF_bun() %></td>
		<td><%=a.getF_irum() %></td>
		<td><%=a.getF_tel() %></td>
	</tr>
<%	
}
%>	
</table><br>
가족번호: <input type="text" id="f_bun" name="f_bun" size="3">
<input type="button" id="update" value="수정">
<input type="button" id="delete" value="삭제">
</body>
</html>