<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
%>    
<jsp:useBean id="bean" class="pack.ExamBean"/>
<jsp:setProperty property="*" name="bean"/>		<!-- 모든 값을 다 세팅한다. -->
<%
//System.out.println(bean.getName()+" "+bean.getKor()+" "+bean.getEng());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
폼빈에 등록된 사용자의 자료 출력<br>
이름은 <jsp:getProperty property="name" name="bean"/><br>
국어 점수는 <jsp:getProperty property="kor" name="bean"/><br>
영어 점수는 <jsp:getProperty property="eng" name="bean"/><br>

<jsp:useBean id="examProcess" class="pack.ExamProcess"/>
<jsp:setProperty property="examBean" name="examProcess" value="<%=bean %>"/>
총점은: <jsp:getProperty property="tot" name="examProcess"/>
평균은: <jsp:getProperty property="avg" name="examProcess"/>
</body>
</html>