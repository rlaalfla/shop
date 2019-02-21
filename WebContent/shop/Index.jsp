<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

</head>
<body topmargin="100">
<%@ include file="Top.jsp" %>



<%
mem_id=(String)session.getAttribute("mem_id");

if(mem_id!=null){//로그인 상태
%>
<table width="80%" align="center" bgcolor="#EEEEEE" height="500">
	<tr>
	<td align="center"><%=mem_id %>님 방문해 주셔서 감사합니다</td>
	</tr>

	<tr>
	<td align="center"><a href="ProductList.jsp">상품목록보기</a></td>
	</tr>
	</table>
<%	
}else{//로그인 상태가 아닐때
%>

	<table width="80%" align="center" bgcolor="#EEEEEE" height="500">
	<tr>
	<td align="center">로그인 하신 후 이용해 주세요
	<a href="../member/Login.jsp">로그인</a>
	</td>
	</tr>
	</table>

<%
}//else end
%>


<%@ include file="Bottom.jsp" %>


</body>
</html>