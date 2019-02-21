<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="shopdb.*" %>
    <%@ page import="java.util.*" %>
    
    <%--CartList.jsp --%>
    <jsp:useBean id="cartMgr" class="shopdb.CartMgr" scope="session"/>
<%
OrderDto ordDto=null;
ProductDto proDto=null;
shopdb.ProductMgr proMgr=null;
Hashtable hCart=null;

try{
	proMgr=shopdb.ProductMgr.getDao();//dao 객체 얻기
	
	if(session.getAttribute("mem_id")==null){
		 out.println("<script>alert('로그인해주세요'); location.href='../member/Login.jsp';</script>");//로그인 alert창 띄우고, login창으로 이동 

		//response.sendRedirect("../member/Login.jsp");//로그인으로 가기
		
	}else{//로그인 상태
	%>
	<html>
	<head>
	<link href="style.css" rel="stylesheet" type="text/css">

	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

	<script type="text/javascript" src="script.js"></script>
	</head>
	
	<body  topmargin="100">
		<%@ include file="Top.jsp" %>
		
			<table width="80%" align="center" >
			<tr>
			<td align="center" >
				<table width="95%" align="center">
				
				<tr bgcolor="#D0D0D0">
				<td align="center"><font color="#000000">제품</font></td>
				<td align="center"><font color="#000000">수량</font></td>
				<td align="center"><font color="#000000">가격</font></td>
				<td align="center"><font color="#000000">수정/삭제</font></td>
				<td align="center"><font color="#000000">조회</font></td>
				</tr>
				<%
				int totalPrice=0;//총금액
				hCart=cartMgr.getCartList();//장바구니 내용 가져오기
				
				if(hCart.size()==0){//장바구니에 물건이 없을때
					%>
					<tr>
					<td colspan="5" align="center">
					선택하신 물품이 없습니다
					</td>
					</tr>
					<%
				}else{//장바구니에 물건이 있을때
					out.println("<tr><td align=center colspan=5>장바구니에 들어있는 상품</td></tr>");
				Enumeration hCartKey=hCart.keys();//모든 key값 얻기
				
				while(hCartKey.hasMoreElements()){//자료가 있는 동안 반복 수행
					ordDto=(OrderDto)hCart.get(hCartKey.nextElement());
					
					//ProductMgr.getProduct()
					proDto=proMgr.getProduct(request.getParameter("code"),ordDto.getPro_no());
					
					int price=proDto.getPrice();//가격
					int quantity=Integer.parseInt(ordDto.getQuantity());//수량
					int subTotal=price*quantity;//계산
					//totalPrice+=pice*quantity;//총금액 구함
					totalPrice += subTotal;//총금액구함
					
					java.util.Date date=new java.util.Date();
					java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd H:mm:ss");
					%>
					<form method="post" action="CartProc.jsp">
					<input type="hidden" name="pro_no" value="<%=proDto.getPro_no()%>">
					<input type="hidden" name="flag">
					<input type="hidden" name="state" value="2">
					
					<tr>
					<td align="center"><%=proDto.getName() %></td>
					<td align="center">
					<input type=text name=quantity value="<%=ordDto.getQuantity()%>">
					</td>
					<td align="center"><%=subTotal+"" %></td>
					<td align="center">
					<input type="button" value="cart수정" onClick="javaScript:cartUpdate(this.form")>/
					<input type="button" value="cart삭제" onClick="javaScript:cartDelete(this.form")>
					</td>
					
					<td align="center">
					<a href="javaScript:productDetail('<%=proDto.getCode()%>')">상세보기</a>
					</td>
					</tr>
					
					</form>
					<%
				}//while end
				%>
					<tr>
					<td colspan="4" align="right">총 금액 : <%=totalPrice %>원</td>
					<td align="center">
					<a href="OrderProc.jsp">주문하기</a>
					</td>
					</tr>
				<%	
				}//else end
				%>
				</table>
			
			</td>
			</tr>
			</table>
			
			<form name="detail" method="post" action="ProductDetail.jsp">
				<input type="hidden" name="code">
			
			</form> 
		<%@ include file="Bottom.jsp" %>
	
	</body>
	
	</html>
	<%
	}//else end
}catch(Exception ex1){
	out.print("CartList.jsp 예외 :"+ex1 );
}//catch
%>