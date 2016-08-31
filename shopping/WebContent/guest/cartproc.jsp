<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="cartMgr" class="shop.order.CartMgr" scope="session"/>
<jsp:useBean id="order" class="shop.order.OrderBean"/>
<jsp:setProperty property="*" name="order"/>

<%
String flag = request.getParameter("flag");		//구매 목록 보기, 수정, 삭제 판단용
String id = (String)session.getAttribute("idKey");
//out.println(order.getProduct_no()+"주문수: "+order.getQuantity());

if(id == null){
%>	
	<script>
	alert("로그인을 해주세요!");
	</script>
<%	
	response.sendRedirect("login.jsp");
}else{
	if(flag == null){
		order.setId(id);
		cartMgr.addCart(order);		//cart에 주문상품 담기
%>
	<script>
	alert("장바구니에 담았습니다.");
	location.href="cartlist.jsp";
	</script>
<%		
	}else if(flag.equals("update")){
		order.setId(id);
		cartMgr.updateCart(order);
%>
	<script>
	alert("장바구니의 내용을 수정했습니다.");
	location.href="cartlist.jsp";
	</script>
<%	
	}else if(flag.equals("del")){
		cartMgr.deleteCart(order);
%>
	<script>
	alert("해당 상품의 주문을 삭제했습니다.");
	location.href="cartlist.jsp";
	</script>
<%			
	}
}
%>