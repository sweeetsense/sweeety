<%@ page import="pack.gugudan" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
** 방법 1: 현재 내 기술로 처리 하기<br>
<%
int dan = Integer.parseInt(request.getParameter("dan"));
out.println(dan + "출력<p>");

//gugudan gugudan = new gugudan();
gugudan gg = gugudan.getInstance(); 
int re[] = gg.compute(dan);
for(int a=0; a<9; a++){
	out.println(dan + "*" + (a+1) + "=" +re[a] + "&nbsp;&nbsp;");
}
%>
<hr>
<br>
** 방법2: 빈즈로 처리하기 **<br><br><br>
<jsp:useBean id="gugu" class="pack.gugudan"></jsp:useBean>
<%
System.out.println(gugu.toString());
int re2[] = gugu.compute(dan);
for(int a=0; a<9; a++){
	out.println(dan + "*" + (a+1) + "=" +re[a] + "&nbsp;&nbsp;");
}
%>
</body>
</html>