<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="java.util.*" %>
    <%@ page import="shopdb.*"%>
    
    <%--ProductDetail.jsp --%>
<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>
</head>

<body topmargin="20">
<%@ include file="Top.jsp" %>

<%
ProductMgr proMgr=ProductMgr.getDao();//dao객체얻기
String code=request.getParameter("code");
int pro_no=Integer.parseInt(request.getParameter("pro_no"));
ProductDto productDto=proMgr.getProduct(code, pro_no);//dao메서드
%>

<table width="80%" align="center" bgcolor="#EEEEEE">
<tr>
<td align="center" bgcolor="#EEEEEE">
	<table width="80%" align="center" bgcolor="#EEEEEE">
	
		<tr bgcolor="#b9b9dd">
		<td colspan="3" align="center"><%=productDto.getName() %></td>
		</tr>
		
		<tr>
		<td width="20%">
		<img src="<%=request.getContextPath() %>/imgs/<%= productDto.getImage()%>" height="150" width="150">
		<%--
		<img src="../imgs/<%= productDto.getImage()%>" height="150" width="150">
		 --%>
		</td>
		
		<td width="30%" valign="top">
		<table border="1" width="100%" height="100%">
			<tr>
			<td><b>상품이름:</b><%=productDto.getName() %></td>
			</tr>
			
			<tr>
			<td><b>가격:</b><%=productDto.getPrice() %></td>
			</tr>
			
			<tr>
			<td><b>입고날짜:</b><%=productDto.getRegdate() %></td>
			</tr>
			
			<tr>
			<td><b>남은물량:</b>
			<%
			if(productDto.getStock()>0){
				out.println(productDto.getStock());
			}else{
				out.println("품절");
			}
			%>
			</td>
			</tr>
			
		</table>
		</td>
		
		<td width="50%" valign="top"><b>상세설명</b>
		<br><%=productDto.getDetail() %>
		</td>
		</tr>
		
		<tr>
		<td colspan="3" align="center">
		<a href="javascript:productUpdate('<%=productDto.getPro_no()%>')">수정하기</a>
		&nbsp;
		<a href="javascript:productDelete('<%=productDto.getPro_no()%>')">삭제하기</a>
		</td>
		</tr>
	
	</table>

</td>
</tr>

</table>

<%@ include file="Bottom.jsp" %>

<form name="update" method="post" action="ProductUpdate.jsp">
<input type="hidden" name="pro_no">
</form>

<form name="del" method="post" action="ProductProc.jsp?flag=del">
<input type="hidden" name="pro_no">
</form>



</body>

</html>