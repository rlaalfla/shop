<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="shopdb.*" %>
    <%@ page import="java.util.*" %>
    
    <%--OrderProc.jsp --%>
    
<%--빈사용=객체생성--%>
<jsp:useBean id="cartMgr" class="shopdb.CartMgr" scope="session"/>
<jsp:useBean id="orderMgr" class="shopdb.OrderMgr"/>

<%
shopdb.ProductMgr proMgr=ProductMgr.getDao();//dao 객체 얻기

Hashtable hCart=cartMgr.getCartList();//cart내용 가져오기
Enumeration hCartKey=hCart.keys();//해쉬테이블의 키값들 얻기

if(hCart.size() != 0){//장바구니에 내용이 있으면
	while(hCartKey.hasMoreElements()){
		OrderDto order=(OrderDto)hCart.get(hCartKey.nextElement());
		
		orderMgr.insertOrder(order);//주문 테이블에 저장
		proMgr.reduceProduct(order);//남은 물량 계산
		cartMgr.deleteCart(order);//장바구니 비운다
	}//while end
	%>
	<script>
	alert("주문처리 하였습니다");
	location.href="OrderList.jsp";
	</script>
	<%
}else{//장바구니 내용이 없으면
	%>
	<script>
	alert("장바구니가 비어있습니다");
	location.href="ProductList.jsp";//상품목록 리스트
	</script>
	<%
}//else end
%>