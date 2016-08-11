<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String funcName = request.getParameter("callback");

if(funcName != null){
%>
	<%=funcName %>(
		{"datas":
			[
				{"no":"10", "name":"홍길동"},
				{"no":"20", "name":"고길동"},
				{"no":"30", "name":"신길동"}
			]
		}			
	);
<%	
}
%>