<%@page import="pack.business.DataDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="processDAO" class="pack.business.ProcessDAO"/>
<%
String code = request.getParameter("code");
DataDTO dto = processDAO.selectDataPart(code);
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
** 상품 수정 **<p/>

<form action="updateok.jsp" method="post">
<input type="hidden" name="code" value="<%=dto.getCode()%>">
코드: <%=dto.getCode() %><br>
상품: <input type="text" name="sang" value="<%=dto.getSang()%>"><br>
수량: <input type="text" name="su" value="<%=dto.getSu()%>"><br>
단가: <input type="text" name="dan" value="<%=dto.getDan()%>"><br>
<input type="submit" value="수정">
</form>
</body>
</html>