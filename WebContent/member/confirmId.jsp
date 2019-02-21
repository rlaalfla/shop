<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="member.*"
    %>
    
    
<%
String id=request.getParameter("id");//ajax에서 보내준것
member.MemberDAO dao =member.MemberDAO.getInstance();//dao객체 얻기

int check=dao.confirmId(id);//1사용중, -1 사용가능
%>

{

"check":<%=check %>
}