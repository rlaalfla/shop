<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import ="shopdb.*" %>    


<%
request.setCharacterEncoding("utf-8");//post요청 한글 처리
shopdb.OrderMgr dao=shopdb.OrderMgr.getInstance();//dao 객체 얻기
%>

<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

<title>Insert title here</title>
</head>
<body align="center" bgcolor="ivory">
<%@ include file="Top.jsp" %>

<form>

<table width="80%" align="center" bgcolor="#f9fafb">
		<tr style="background-color: #b9b9dd" align="center">
			<th>주문번호</th><th>상품번호</th><th>주문수량</th><th>주문날짜</th><th>상태(1.주문, 2.배송)</th><th>주문회원ID</th>
		</tr>
	
		

	<%
		Vector <OrderDto> vec = dao.getOrderList();
		for(OrderDto dto : vec){
	%>

			<tr align="center">
				<td><%=dto.getOrdno() %></td>
				<td><%=dto.getPro_no() %></td>
				<td><%=dto.getQuantity() %></td>
				<td><%=dto.getOrddate() %></td>
				<td><%=dto.getState() %></td>
				<td><%=dto.getUserid() %></td>

	
			</tr>

	<%
		}
	%>
	
	</table>	
	
	<table align="center">
	
	<tr>
	<td>
	<input type="button" value="메인으로" onclick="javaScript:location='Index.jsp'" class="btn">
	</td>
	</tr>
	</table>

</form>


<%@ include file="Bottom.jsp" %>
</body>
</html> 