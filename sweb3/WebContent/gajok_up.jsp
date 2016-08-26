<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String f_bun = request.getParameter("f_bun");
String gogek_no = request.getParameter("gogek_no");
//System.out.println("a: "+gogek_no+f_bun);
%>
<jsp:useBean id="bean" class="pack.GogekBean"/>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="pooling" class="pack.GogekPooling"></jsp:useBean>
<%
pooling.updatevalue(bean);
//System.out.println(bean.getF_irum()+bean.getF_tel());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="gajok_upok.jsp" >
	가족명: <input type="text" name="f_irum" value="<%=bean.getF_irum()%>"><br>
	번호: <input type="text" name="f_tel" value="<%=bean.getF_tel()%>"><br><br>
	<input type="hidden" name="gogek_no" value=<%=gogek_no %>>
	<input type="hidden" name="f_bun" value="<%=f_bun %>">
	<input type="submit" value="수정">
</form>
</body>
</html>    
