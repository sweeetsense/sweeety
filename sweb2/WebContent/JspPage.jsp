<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.Calendar"
    session="true"
    buffer="8kb"
    autoFlush="true"
    isThreadSafe="true"
    info="JSP문서정보 담아둠. 검색로봇을 통한 자료 검색시 사용할 수도 있다."
    %>
 <!-- errorPage="error.jsp"   -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
* 페이지 지시어는 JSP 문서의 앞에서 무언가를 지시하는 역할*<br>
날짜 및 시간 출력:
<%
Calendar dt = Calendar.getInstance();
int y= dt.get(Calendar.YEAR);
int m= dt.get(Calendar.MONTH)+1;
int d= dt.get(Calendar.DATE);
out.println("오늘은 "+y+"년"+m+"월"+d+"일");
%>
<br>
<%= this.getServletInfo() %>
<hr>
<%
int a= 10 / 2; // 따로 불러오는게 아니다
out.println("나누기 결과 :"+a);
%>
</body>
</html>