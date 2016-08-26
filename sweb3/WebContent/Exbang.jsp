<%@page import="pack.ExBangPooling"%>
<%@page import="pack.ExBangBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="pooling" class="pack.ExBangPooling"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function funcUp(name){
	//alert(name);
	/* if(name != "" && name != null){
		location.href="Exbang_up.jsp?name="+name;
	} */
}
function funcDel(code){
	location.href="ExBang_del.jsp?code="+code;
}
</script>
</head>
<body>
방명록<hr>
<form action="Exupload.jsp" name="frm">
<button>글쓰기</button>
</form><br>
<table border="1">
	<tr><th>코드</th><th>작성자</th><th>제목</th><th>내용</th></tr>
<%
ArrayList<ExBangBean> list = pooling.getData();
for(ExBangBean a:list){
%>	
	<tr>
		<td><a href="javascript:funcDel(<%=a.getCode() %>)"><%=a.getCode() %></a></td>
		<td><a href="ExBang_del.jsp?code=" name="<%=a.getName() %>"><%=a.getName() %></a></td>
		<td><%=a.getSubject() %></td>
		<td><%=a.getContent() %></td>
	</tr>
<%	
}
%>	
</table>
</body>
</html>