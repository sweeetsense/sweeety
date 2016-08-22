<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file ="Top.jsp"%>	<!-- 소스를 가져온다 -->
<h1>나의 페이지</h1>
<pre>
문서의 본문
..
.
.
.
..
</pre>

<div style="color:blue;"><!-- jsp action 태그를 사용함 -->
<jsp:include page="InAction1.jsp"/>	<!-- 처리된 결과를 가져온다 -->
</div>
<div style="color:cyan;">
<jsp:include page="InAction2.jsp">
	<jsp:param value="korea" name="msg"/>
</jsp:include>
</div>
<%@ include file="Bottom.jsp"%>
</body>
</html>