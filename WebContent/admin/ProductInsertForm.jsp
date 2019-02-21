<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%--ProductInsertForm.jsp--%>

<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

</head>

<body topmargin="20">
<%@ include file="Top.jsp" %>

<table width="80%" align="center" bgcolor="#EEEEEE">
	<tr>
	<td align="center" bgcolor="#EEEEEE">
		<form method="post" action="ProductProc.jsp?flag=insert" encType="multipart/form-data">
		<table width="95%" align="center" bgcolor="#EEEEEE" border="1">
			<tr>
			<td colspan="2" align="center">상품등록</td>
			</tr>
			
			<tr>
			<td align="center">상품이름</td>
			<td align="left">
			<input type="text" name="name" id="name" size="20">
			</td>
			</tr>
			
			<tr>
			<td align="center">상품코드</td>
			<td align="left">
			<input type="text" name="code" id="code" size="15">
			</td>
			</tr>
			
			<tr>
			<td  align="center">상품가격</td>
			<td align="left">
			<input type="text" name="price" id="price" size="20">원
			</td>
			</tr>
			
			
			<tr>
			<td  align="center">상품설명</td>
			<td align="left">
			<textarea rows="10" cols="45" name="detail" id="detail"></textarea>
			</td>
			</tr>
			
			<tr>
			<td  align="center">상품입고수량</td>
			<td align="left">
			<input type="text" name="stock" id="stock" size="10">개
			</td>
			</tr>
			
			<tr>
			<td align="center">상품이미지</td>
			<td><input type="file" name="image"></td>
			</tr>
			
			
			<tr>
			<td align="center">제조회사</td>
			<td align="left">
			<input type="text" name="comp" id="comp" size="20">
			</td>
			</tr>
			
			<tr>
			<td colspan="2" align="center">
			<input type="submit" value="상품등록">
			<input type="reset" value="다시쓰기">
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