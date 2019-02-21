<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="shopdb.*" %>
    
    <%--CartProc.jsp --%>
    
    <jsp:useBean id="cartMgr" class="shopdb.CartMgr" scope="session">
    </jsp:useBean>

    <%--dto에 모두 setter작업 --%>
    <jsp:useBean id="orderDto" class="shopdb.OrderDto">
    	<jsp:setProperty name="orderDto" property="*"/>
    </jsp:useBean>
    
<%
String flag=request.getParameter("flag");//update,del
String mem_id=(String)session.getAttribute("mem_id");

if(mem_id==null){//로그인 안된 상태이면
	response.sendRedirect("Login.jsp");//로그인 화면으로 가기
}else{//로그인 상태
	if(flag==null){//장바구니에 넣기
		orderDto.setUserid(mem_id);//userid를 setter하고
		cartMgr.addCart(orderDto);//장바구니에 넣기
		%>
		<script>
		alert("장바구니에 담았습니다");
		location.href="CartList.jsp";//장바구니 목록 보기로 갑니다
		</script>
		<%
	}else if(flag.equals("update")){//장바구니 수정
		orderDto.setUserid(mem_id);//setter
		cartMgr.updateCart(orderDto);//장바구니 수정 메서드 호출
		%>
		<script>
		alert("수정 하였습니다");
		location.href="CartList.jsp";//장바구니 목록 보기로 갑니다
		</script>		
		<%
	}else if(flag.equals("del")){//장바구니에서 삭제
		cartMgr.deleteCart(orderDto);//장바구니에서 삭제하는 메서드 호출
		%>
		<script>
		alert("삭제 하였습니다");
		location.href="CartList.jsp";//장바구니 목록 보기로 갑니다
		</script>
		<%
	}//else if end
}//else  end
%>    
    
    