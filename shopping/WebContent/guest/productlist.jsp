<%@page import="shop.product.ProductMgr"%>
<%@page import="shop.product.ProductBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="productMgr" class="shop.product.ProductMgr"></jsp:useBean>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품목록</title>
<link href="../css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/script.js"></script>
</head>
<body>
** 고객님만을 위한 쇼핑천국! **<p>
<%@ include file ="guest_top.jsp" %>
<table style="width: 80%;">
	<tr style="background-color: cyan;">
		<th>상품</th><th>가격</th><th>재고량</th><th>상세보기</th>
	</tr>
	<%
	ArrayList<ProductBean> list = productMgr.getProductAll();
	for(ProductBean p:list){
	%>
	<tr style="text-ailgn: center;">
		<td>
		<img title="<%=p.getName() %>" src="../data/<%=p.getImage() %>" width="100">
		</td>
		<td><%=p.getPrice() %></td>
		<td><%=p.getStock() %></td>
		<td><a href="javascript:productDetail('<%=p.getNo() %>')">보기</a></td>
	</tr>
	<%
	}
	%>
</table>
<%@ include file = "guest_bottom.jsp" %>

<form action="productdetail_g.jsp" name="detailFrm" method="post">
	<input type="hidden" name="no">
</form>
</body>
</html>