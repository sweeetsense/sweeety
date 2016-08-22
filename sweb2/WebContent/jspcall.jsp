<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
서블릿에 의해 호출된 파일임<br>
<%
request.setCharacterEncoding("utf-8");
//redirect 방식
String data = request.getParameter("data");
out.println("자료는 "+data+"<br>");

//forwording으로 넘어온 값
String data2 = (String)request.getAttribute("mydata");
out.println("자료2는 "+data2);
%>
</body>
</html>