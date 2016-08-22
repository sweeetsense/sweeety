<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
홀수 출력<br>
<%
for(int a=1;a<=10;a++){
	if(a % 2 == 1){
		out.print(a+" ");
	}
}
%>
<br>
<%="찍기위한 몸부림"%>
