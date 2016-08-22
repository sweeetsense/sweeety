<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
request.setCharacterEncoding("utf-8");
%>    
<jsp:useBean id="bean" class="pack.ExBean"/>
<jsp:setProperty property="*" name="bean"/>
<%
System.out.println(bean.getFruit()+" "+bean.getPrice()+" "+bean.getDiscount());

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
과일 <jsp:getProperty property="fruit" name="bean"/>의 구매 가격은 정가 <jsp:getProperty property="price" name="bean"/> 원 에서
<jsp:getProperty property="discount" name="bean"/> % 할인된 

<jsp:useBean id="excompute" class="pack.ExCompute"/>
<jsp:setProperty property="exbean" name="excompute" value="<%=bean %>"/>
<jsp:getProperty property="rp" name="excompute"/> 원
</body>
</html>