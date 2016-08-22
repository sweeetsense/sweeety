<%@page import="pack.SangpumDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String code = request.getParameter("code");
//out.println(code);
%>
<jsp:useBean id="connBeanPooling" class="pack.ConnBeanPooling" />

 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
SangpumDto dto = connBeanPooling.updateList(code);
if(dto == null){
%>
	<script>
		alert("등록된 코드가 아닙니다. \n확인 바람");
		location.href="dbtest2.jsp";
	</script>
<%
return;
}
%>
** 상품 수정 ** <p>
<form action="dbtest2_upok.jsp" method="post">
코드:<%=dto.getCode() %><br/>
<input type="hidden" name="code" value="<%=dto.getCode() %>">
품명: <input type="text" name="sang" value="<%=dto.getSang() %>"><br>
수량: <input type="text" name="su" value="<%=dto.getSu() %>"><br>
단가: <input type="text" name="dan" value="<%=dto.getDan() %>"><br>
<input type="submit" value="자료 수정 확인">
<input type="button" value="목록보기로 이동" onclick="javascript:location.href='dbtest2.jsp'">
</form>
</body>
</html>