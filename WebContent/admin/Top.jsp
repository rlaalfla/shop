<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%--Top.jsp --%>
<%
String admin_id=(String)session.getAttribute("admin_id");

if(admin_id==null){//로그인 상태가 아닐때
	response.sendRedirect("AdminLogin.jsp");
}

String context=request.getContextPath();//현재 컨텍스트 경로 얻기
%>

<table width="80%" align="center" bgcolor="#f9fafb">
	<tr bgcolor="#b9b9dd">
	<td align="center">
	<b><a href="<%=context %>/admin/Index.jsp">홈</a></b>
	</td>
	
	<td align="center">
	<b><a href="<%=context%>/admin/AdminLogout.jsp">로그아웃</a></b>
	</td>
	
	<td align="center">
	<b><a href="<%=context%>/admin/MemberMgr.jsp">회원관리</a></b>
	</td>
	
	<td align="center">
	<b><a href="<%=context%>/admin/ProductList.jsp">상품관리</a></b>
	</td>
	
	<td align="center">
	<b><a href="<%=context%>/admin/OrderMgr.jsp">주문관리</a></b>
	</td>
	
	</tr>

</table>