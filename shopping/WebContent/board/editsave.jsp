<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="bean" class="shop.board.BoardBean"/>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="boardMgr" class="shop.board.BoardMgr"/>

<%
String spage = request.getParameter("page");
System.out.println("spage="+spage);
boolean b = boardMgr.checkPass(bean.getNum(), bean.getPass());
if(b){
	boardMgr.saveEdit(bean);
	response.sendRedirect("boardlist.jsp?page="+spage);
}else{
%>
	<script>
		alert("비밀번호 불일치!");
		history.back();		//이전 화면으로 돌아가기
	</script>
<%	
}
%>