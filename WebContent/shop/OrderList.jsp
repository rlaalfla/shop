<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import ="java.util.*" %>
    <%@ page import="shopdb.*" %>
    
<%--OrderList.jsp--%>

<%--dao 객체 생성--%>
<jsp:useBean id="orderMgr" class="shopdb.OrderMgr"/>

<%
shopdb.ProductMgr proMgr=ProductMgr.getDao();//dao객체 얻기
%>

<%@ include file="Top.jsp"%>

<%
if(session.getAttribute("mem_id") == null){
	//로그인 상태가 아니면
	 out.println("<script>alert('로그인해주세요'); location.href='../member/Login.jsp';</script>");
	//response.sendRedirect("../member/Login.jsp");
}else{
	//로그인 상태이면
	mem_id=(String)session.getAttribute("mem_id");
}//else
%>
<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

</head>

<body topmargin="20">
<table width="80%" align="center" >
	<tr>
	<td align=center colspan=5>
	<br>
	<h4><%=mem_id %>님의 구매목록</h4>
	</td>
	</tr>
	
	<tr>
	<td align="center">
		<table width="95%" align="center">
			<tr bgcolor="#D0D0D0">
			<td align="center">주문번호</td>
			<td align="center">제품</td>
			<td align="center">주문수량</td>
			<td align="center">주문날짜</td>
			<td align="center">주문상태</td>
			</tr>
			<%
			Vector vec=orderMgr.getOrder(mem_id);//orderMgr 메서도 호출
			if(vec.size()==0){//구매목록이 없으면
				%>
				<tr>
				<td align="center"colspan="5">주문하신 물품이 없습니다</td>
				</tr>
				<%
			}else{//구매목록이 있으면
				for(int i=0;i<vec.size();i++){
					OrderDto orddto=(OrderDto)vec.get(i);
					
					String code=request.getParameter("code");
					int pro_no=orddto.getPro_no();
					ProductDto proDto=proMgr.getProduct(code, pro_no);//proMgr메서도 호출하여 해당 정보들을 가져온다
					%>
					<tr>
						<!-- 주문번호 -->
						<td align="center"><%=orddto.getOrdno() %></td>
						
						<!-- 상품명 클릭하면, 상세보기 -->
						<td align="center">
						<a href="javascript:productDetail('<%=proDto.getCode()%>')">
						<%=proDto.getName() %>
						</a>
						</td>
						
						<!-- 수량 -->
						<td align="center"><%=orddto.getQuantity() %></td>
						
						<!-- 주문날짜 -->
						<td align="center"><%=orddto.getOrddate() %></td>
						
						<!-- 상태 -->
						<td align="center">
						<%
						switch(Integer.parseInt(orddto.getState())){
						case 1:out.println("접수중");break;
						case 2:out.println("접수");break;
						case 3:out.println("입금확인");break;
						case 4:out.println("배송준비");break;
						case 5:out.println("배송중");break;
						default:out.println("배송확인");
						}//switch end
						%>
						</td>
					</tr>
					<%		
				}//for 
			}//else end
			%>
		</table>
	</td>
	</tr>

</table>
<%@ include file="Bottom.jsp" %>

<form name="detail" method="post" action="ProductDetail.jsp">
	<input type="hidden" name="code">
</form>

</body>
</html>