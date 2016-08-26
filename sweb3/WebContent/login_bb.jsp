<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="aa" style="margin: 10 10 auto">
	<form action="loginok.jsp" method="post">
		<h2>교육센터</h2>
		<%=session.getAttribute("id")+" 님이 로그인 중" %>
		<input type="hidden" name="id"/>
		<input type="hidden" name="pwd"/>
		<input type="submit" value="로그아웃"><p/>
		<a href="loginjikwon.jsp" target="frame">자료보기</a>
	</form>
</div>