<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
** 선택 결과 확인 **<p>
<%
	request.setCharacterEncoding("utf-8");
	String movie= request.getParameter("movie");
	String id = (String)session.getAttribute("idkey");
	
	if(id != null){
%>
<%=id %> 님이 좋아하는 영화는 <%=movie %>입니다.<br>
세션 아이디: <%=session.getId() %><br>
세션 유지 시간: <%=session.getMaxInactiveInterval() %>
<%		
	}else{
		out.println("세션이 설정되지 않았습니다.");
	}
%>
</body>
</html>