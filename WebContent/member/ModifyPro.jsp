<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="member.*" %>
    <%--modifyPro.jsp--%>
    
<%
request.setCharacterEncoding("utf-8");//post요청 한글 처리
%>

<jsp:useBean id="dto" class="member.MemberDTO">
	<jsp:setProperty name="dto" property="*"/>	
</jsp:useBean>

<html>
<body>
<%
String id=(String)session.getAttribute("mem_id");
dto.setId(id);//setter작업

MemberDAO dao=MemberDAO.getInstance();//dao객체얻기
dao.updateMember(dto);//dao메서드 호출
%>

<table>
	<tr>
	<td height="40" align="center">
	<font size="+2"><b>회원 정보 수정이 되었습니다</b></font>
	</td>
	</tr>
	
	<tr>
	<td align="center">
		<form>
		<input type="button" value="메인으로" onClick="window.location='../shop/Index.jsp'">
		</form>
		3초 후에 메인으로 이동 합니다
		<meta http-equiv="Refresh" content="3;url=../shop/Index.jsp">
	</td>
	</tr>

</table>

</body>
</html>