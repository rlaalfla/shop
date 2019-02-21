<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%--AdminLogout.jsp --%>
<%
session.invalidate();
%>

<script>
alert("로그아웃 되었습니다");
location.href="Index.jsp";
</script>