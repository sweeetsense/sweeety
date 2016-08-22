<%@page import="pack.ExdbDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
%>

<jsp:useBean id="db" class="pack.Exdb"></jsp:useBean>
<jsp:setProperty property="buser_name" name="db"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:getProperty property="buser_name" name="db"/><br><br>
<table border="1">
<tr><th>사원번호</th><th>사원이름</th><th>사원직급</th><th>사원 성별</th></tr>
<%
ArrayList<ExdbDto> list = db.getData();
int i=0, sum=0;
double avg=0;

for(ExdbDto a:list){
	if(a.getSawon_gen().equals("남")){
%>
	<tr><td><%=a.getSawon_no()%></td>
	<td style="color: blue"><%=a.getSawon_name()%></td>
	<td><%=a.getSawon_jik()%></td>
	<td><%=a.getSawon_gen() %></td></tr>
<%		
	}else{
%>	
	<tr><td><%=a.getSawon_no()%></td>
	<td style="color: yellow"><%=a.getSawon_name()%></td>
	<td><%=a.getSawon_jik()%></td>
	<td><%=a.getSawon_gen() %></td></tr>
<%	
	}
	i++;
	sum += a.getSawon_pay();
}
avg = sum/i;
%>	
<td colspan="4">총 <%=i %> 명,	
평균 연봉: <%=avg %> 원</td>
</table>
</body>
</html>