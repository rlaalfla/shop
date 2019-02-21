<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%--AdminLoginProc.jsp --%>
<%
String admin_id=request.getParameter("admin_id");
String admin_pwd=request.getParameter("admin_pwd");

//DB작업
session.setAttribute("admin_id", admin_id);
%>

<script>
location.href="Index.jsp";// admin/12345
</script>