<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
* xss test *<p/>
<%
request.setCharacterEncoding("utf-8");
String name= request.getParameter("name");

// < 문자를 &lt로 교체하라
if(name != null){
	name=name.replaceAll("<","&lt;");
	name=name.replaceAll(">","&gt;");
}else{
	return;
}
%>
<p>
이름은 <%=name %>
</body>
</html>