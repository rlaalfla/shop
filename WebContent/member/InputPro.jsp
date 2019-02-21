<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ page import="member.*" %>
<%@ page import="java.sql.Timestamp" %>
   
<%-- inputPro.jsp--%>

<%
request.setCharacterEncoding("utf-8");//post요청 한글 처리
%>

<jsp:useBean id="dto" class="member.MemberDTO">
	<jsp:setProperty name="dto" property="*"/>
</jsp:useBean>

<%
//날짜 setter
dto.setRegdate(new Timestamp(System.currentTimeMillis()));

member.MemberDAO dao=member.MemberDAO.getInstance();//dao 객체 얻기
dao.insertMember(dto);//dao메서드 호출
response.sendRedirect("../shop/Index.jsp");
%>