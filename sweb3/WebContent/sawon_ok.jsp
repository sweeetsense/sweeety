<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String no = request.getParameter("no");
	String name = request.getParameter("name"); 
%>

	<jsp:useBean id="log" class="sawon.SawonConnBean"></jsp:useBean>
<%

  log.login(no,name);
  if(String.valueOf(log.getNo()).equals(no) && log.getName().equals(name)){
		session.setAttribute("no", no);
		session.setAttribute("name", name);
		
	}else{
		session.removeAttribute("name");
	}

	response.sendRedirect("sawon.jsp");
%>