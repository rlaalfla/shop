<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import ="member.*" %>
    <%@ page import ="java.util.ArrayList" %>
    
<%
request.setCharacterEncoding("utf-8");//post요청 한글 처리
member.MemberDAO dao=member.MemberDAO.getInstance();//dao 객체 얻기
%>
<jsp:useBean id="membermanager" class="member.MemberDTO">
	<jsp:setProperty name="dto" property="*"/>
</jsp:useBean>

<html>
<head>

<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>


<script type="text/javascript">
function admin_modify_member(id){
	document.modifyForm.id.value=id;
	document.modifyForm.submit();
}
function admin_delete_member(id){
	document.deleteForm.id.value=id;
	document.deleteForm.submit();
}
</script>
</head>

<body align="center" bgcolor="ivory">
<%@ include file="Top.jsp" %>


<form>

<table width="80%" align="center" bgcolor="#f9fafb">
		<tr style="background-color: #b9b9dd" align="center">
			<th>아이디</th><th>회원명</th><th>이메일</th><th>우편번호</th><th>상세주소</th><th>직업</th>
		</tr>
		
		

	<%
		ArrayList<MemberDTO> list = dao.getMemberAll();
		for(MemberDTO dto : list){
	%>

			<tr align="center">
				<td><%=dto.getId() %></td>
				<td><%=dto.getName() %></td>
				<td><%=dto.getEmail() %></td>
				<td><%=dto.getZipcode() %></td>
				<td><%=dto.getAddr() %></td>
				<td><%=dto.getJob() %></td>
				
			</tr>

	<%
		}
	%>
	
	</table>	
	
	<table align="center">
	
	<tr>
	<td>
	<input type="button" value="메인으로" onclick="javaScript:location='Index.jsp'" class="btn">
	</td>
	</tr>
	</table>

</form>

	




<%@ include file="Bottom.jsp" %>
</body>
</html> 