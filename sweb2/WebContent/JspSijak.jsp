<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP Test</title>
</head>
<body>
안녕 난 jsp 문서!
<br>
<%		//scriptlet : 자유롭게 자바의 코드를 기술
String ir="홍길동";
out.println(ir+" 의 홈페이지!");	//out: 클라이언트에 자료를 내보내는 내장 객체

for(int i=1;i<7;i++){
	out.print("<h"+i+">");
	out.print("자바를 이용한 태그 처리");
	out.println("</h"+i+">");
}

out.println("자료 출력: ");
%>
<br>
<%="자료 출력(표현식): "%>
<br>
<% out.print("자료 출력:");%>
<br>
<%=new java.util.Date() %>
<br>
<%
int a=0, sum=0;
do{
	a++;
	sum += a;
}while(a<10);
%>
<%="10까지의 합은 "+sum %>
<br>
<%=ir+"님의 전화번호는 "+tel %>
<%! String tel = "111-1234"; //선언: 멤버필드(전역변수) %>
<br>
<%!
public int add(int m, int n){
	return m+n;
}
%>
<%=add(100,200) %>
</body>
</html>