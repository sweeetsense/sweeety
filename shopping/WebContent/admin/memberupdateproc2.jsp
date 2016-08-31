<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>    
<jsp:useBean id="bean" class="shop.member.MemberBean"></jsp:useBean>
<jsp:setProperty property="*" name="bean"/>
<jsp:useBean id="memberMgr" class="shop.member.MemberMgr"></jsp:useBean>    
<%
String id = request.getParameter("id");
boolean b = memberMgr.memberUpdate(bean, id);

if(b){
%>
	<script>
	alert("수정 성공(관리자)");
	location.href = "membermanager.jsp";
	</script>
	
<%}else{%>
	<script>
	alert("수정 실패\n관리자에게 문의 바람!");
	history.back();
	</script>
<%	
}
%>