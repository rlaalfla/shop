<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%--LogOut.jsp --%>
<%
session.invalidate();//세션무효화
%>

<script>
	alert("로그아웃 되었습니다");
	location.href="../shop/Index.jsp";  
</script>