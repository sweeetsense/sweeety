<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8");%>
<jsp:useBean id="processDao" class="pack.business.ProcessDAO"></jsp:useBean>    

<%
int code = Integer.parseInt(request.getParameter("code"));
boolean b = processDao.deleteData(code);

if(b){
	response.sendRedirect("list.jsp");
}else{
	%>
	<script>
	alert("삭제 실패!");
	location.href="list.jsp";
	</script>
	<%
}
%>