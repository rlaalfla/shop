<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="java.util.*" %>
    <%@ page import="shopdb.*" %>
    
    <%--ProductUpdate.jsp --%>
    
<%
request.setCharacterEncoding("utf-8");//post 요청 한글 처리
ProductMgr proMgr=ProductMgr.getDao();//dao객체 얻기
%>

<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

</head>

<body topmargin="20">
<%@ include file="Top.jsp" %>

<%
String im=request.getParameter("pro_no");
int pro_no=Integer.parseInt(im);
String imcode=request.getParameter("code");
ProductDto productDto=proMgr.getProduct(imcode, pro_no);//dao메서드 호출
%>

<table width="80%" align="center" bgcolor="#EEEEEE">
<tr>
<td align="center" bgcolor="#EEEEEE">
	<form method="post" action="ProductProc.jsp?flag=update" encType="multipart/form-data" >
	<table width="95%" align="center" bgcolor="#EEEEEE" border="1">

		<tr bgcolor="#b9b9dd">
		<td colspan="2" align="center"><h2>상품수정</h2></td>
		</tr>
	
		<tr>
		<td width="31%" align="center">상품이름</td>
		<td width="69%">
		<input type="text" name="name" id="name" value="<%=productDto.getName() %>" size="20">
		</td>
		</tr>
		
		<tr>
		<td align="center">상품code</td>
		<td>
		<input type="text" name="code" id="code" value="<%=productDto.getCode() %>" size="15">
		</td> 
		</tr>
		
		<tr>
		<td align="center">상품가격</td>
		<td>
		<input type="text" name="price" id="price" value="<%=productDto.getPrice() %>" size="20">원
		</td> 
		</tr>
		
		<tr>
		<td align="center">상품설명</td>
		<td>
		<textarea name="detail" id="detail" rows="10" cols="45"><%=productDto.getDetail() %></textarea>
		</td>
		</tr>
		
		<tr>
		<td align="center">입고수량</td>
		<td>
		<input type="text" name="stock" id="stock" value="<%=productDto.getStock() %>" size="10">
		</td> 
		</tr>
		
		<tr>
		<td align="center">상품이미지</td>
		<td>
		<img src="<%=request.getContextPath() %>/imgs/<%=productDto.getImage() %>" width="150" height="150">
		<%--
		<img src="../imgs/<%=productDto.getImage() %>" width="150" height="150">
		 --%>
		<br>
		<input type="file" name="image">
		</td>
		</tr>
		
		<tr>
		<td align="center">제조회사</td>
		<td>
		<input type="text" name="comp" id="comp" value="<%=productDto.getComp() %>" size="20">
		</td>
		</tr>
		
		<tr>
		<td colspan="2" align="center">
		<input type="submit" value="제품수정">&nbsp;&nbsp;
		<input type="reset" value="입력내용취소">
		<input type="hidden" name="pro_no" value="<%=productDto.getPro_no()%>">
		</td>
		</tr>
		
		
	</table>
	</form>

</td>
</tr>
</table>


<%@ include file="Bottom.jsp" %>
</body>
</html>