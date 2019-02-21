<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%--Top.jsp --%>
<%
String mem_id=(String)session.getAttribute("mem_id");
String log="";
String mem="";

if(mem_id==null){
	log="<a href=../member/Login.jsp>로그인</a>";
	mem="<a href=../member/InputForm.jsp>회원가입</a>";
	
}else{
	log="<a href=../member/LogOut.jsp>로그아웃</a>";
	mem="<a href=../member/ModifyForm.jsp>회원수정</a>";
}
%>

<table width="80%" align="center" bgcolor="#f9fafb">
<tr bgcolor="#b9b9dd">
	<td align="center"><b><%=log %></b></td>
	<td align="center"><b><%=mem %></b></td>
	<td align="center"><b><a href="ProductList.jsp">상품목록</a></b></td>
	<td align="center"><b><a href="CartList.jsp">장바구니</a></b></td>
	<td align="center"><b><a href="OrderList.jsp">구매목록</a></b></td>
</tr>


</table>