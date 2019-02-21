<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.*"%>
<%@ page import="shopdb.*"%>

<%--ProductList.jsp--%>
<%
ProductMgr proMgr=ProductMgr.getDao();//dao객체 얻기
%>

<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

</head>
<body>

<%@ include file="Top.jsp" %>

<table width="80%" align="center">
<tr>
<td align="center">
	<table width="95%" align="center" border="1">
		<tr bgcolor="#D0D0D0">
		<td align="center">상품명</td>
		<td align="center">가격</td>
		<td align="center">입고날짜</td>
		<td align="center">남은수량</td>
		<td align="center">&nbsp;</td>
		</tr>
		
		<%
		List <ProductDto> list=proMgr.getGoodList();
		if(list.size()==0){//상품 없으면
			%>
			<tr>
			<td align="center" colspan="5">상품이 없습니다</td>
			</tr>
			<%
		}else{
			for(int i=0;i<list.size();i++){
				ProductDto proDto=(ProductDto)list.get(i);
				%>
				<tr>
				<td align="center"><%=proDto.getName() %></td>
				<td align="center"><%=proDto.getPrice() %></td>
				<td align="center"><%=proDto.getRegdate() %></td>
				
				<td align="center">
				<%
				if(proDto.getStock()>0){//물량이 있으면
					out.println(proDto.getStock());
				}else{//물량이 없으면
					out.println("품절");
				}//else
				%>
				</td>
				
				<td align="center">
				<a href="javascript:productDetail('<%=proDto.getCode() %>','<%=proDto.getPro_no() %>')">
				상세보기</a>
				</td>
				</tr>
				
				<%
			}//for end
			
		}//else end
		%>
		
		<tr>
		<td colspan="5" align="center">
		<a href="ProductInsertForm.jsp">상품등록</a>
		</td>
		</tr>
	</table>
</td>
</tr>



</table>


<%@ include file="Bottom.jsp" %>

<form name="detail" method="post" action="ProductDetail.jsp">
<input type="hidden" name="code">
<input type="hidden" name="pro_no">

</form>
</body>
</html>
