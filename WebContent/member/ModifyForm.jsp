<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import ="member.*"%>

<%--modifyForm.jsp--%>

<%
request.setCharacterEncoding("euc-kr");//post요청 한글 처리
String id=(String)session.getAttribute("mem_id");//****************
MemberDAO dao=MemberDAO.getInstance();//dao객체 얻기
MemberDTO dto=dao.getMember(id);//dao메서드 호출
%>

<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<script type="text/javascript">
function openDaumPostcode(){
	//alert("A")
	new daum.Postcode({
		oncomplete:function(data){
			document.getElementById('zipcode').value=data.postcode1+"-"+data.postcode2;
			document.getElementById('addr').value=data.address;
 		}
		
	}).open();
}//openDaumPostcode()---
</script>

<script type="text/javascript">
function checkIt(){
	/*
	if(document.userForm.passwd.value==''){
		alert("암호를 입력 하시오");
		document.userForm.passwd.focus();
		return false;
	}
	if(document.userForm.passwd2.value==''){
		alert("암호확인을 입력 하시오");
		document.userForm.passwd2.focus();
		return false;
	}
	if(document.userForm.passwd.value != document.userForm.passwd2.value){
		alert("암호가 일치하지 않습니다");
		document.userForm.passwd.focus();
		return false;
	}
	*/
	
	if($('#passwd').val()==''){
		alert("암호를 입력하시오");
		$('#passwd').focus();
		return false;
	}
	if($('#passwd2').val()==''){
		alert("암호확인을 입력 하시오");
		$('#passwd2').focus();
		return false;
	}
	if($('#passwd').val() != $('#passwd2').val()){
		alert("암호가 일치하지 않습니다");
		$('#passwd').focus();
		return false;
	}
	return true;
}//checkIt() end
</script>
</head>

<body bgcolor="ivory">
<%@ include file="Top.jsp" %>

<form name="userForm" action="ModifyPro.jsp" onSubmit="return checkIt()">

<table border="1" width="800" cellpadding="3" align="center" bgcolor="ivory" >
	<tr>
		<td colspan="2" height="30" align="center">
		<b><font size="5">회원정보 수정</font></b>
		</td>
	</tr>
	

	
	<tr>
		<td width="200"><b>패스워드입력</b></td>
		<td width="400">&nbsp;</td>
	</tr>
	
	<tr>
		<td>사용자 ID</td>
		<td><%=dto.getId() %></td>
	</tr>

	<tr>
		<td>암호</td>
		<td><input type="password" name="passwd" id="passwd" size="20">필수입력</td>
	</tr>
	
	<tr>
		<td>암호확인</td>
		<td><input type="password" name="passwd2" id="passwd2" size="20">필수입력</td>
	</tr>
	
	<tr>
		<td width="200">개인정보 입력</td>
		<td width="400">&nbsp;</td>
	</tr>
	
	<tr>
		<td>사용자이름</td>
		<td><input type="text" name="name" id="name" size="15" value="<%=dto.getName()%>"></td>
	</tr>
	
	<tr>
		<td>주민번호</td>
		<td><%=dto.getJumin1() %>-<%=dto.getJumin2() %></td>
		</tr>
		
		<tr>
		<td>Email</td>
		<td>
		<% String im=dto.getEmail();
			if(im==null){
				im="";
			}
		%>
		<input type="text" name="email" id="email" size="40" value="<%=im %>">
		</td>
	</tr>
	
	<tr>
		<td>우편번호</td>
		<td>
		<%
		im=dto.getZipcode();
		if(im==null){
			im="";
		}
		%>
		<input type="text" name="zipcode" id="zipcode" size="7" maxlength="7" value="<%=im %>">
		<input type="button" value="우편번호찾기" onclick="openDaumPostcode()">
		</td>
	</tr>
	
	<tr>
		<td>주소</td>
		<td>
		<%
		im=dto.getAddr();
		if(im==null){
			im="";
		}
		%>
		<input type="text" name="addr" id="addr" size="50" value="<%=im %>">
		</td>
	</tr>
	
	<tr>
		<td>직업</td>
		<td>
		<%
		im=dto.getJob();
		if(im==null){
			im="";
		}
		%>
		
		<select name="job">
		<%
		if(im==""){
			out.println("<option value='0'>선택하시오</option>");
		}else{
			out.println("<option value="+im+">"+im+"</option>");
		}
		%>
		<option value="회사원">회사원</option>
		<option value="공무원">공무원</option>
		<option value="전문연구직">전문연구직</option>
		<option value="교수학생">교수학생</option>
		<option value="기타">기타</option>
		</select>
	
		</td>
	</tr>
	
	<tr>
		<td colspan="2" align="center">
		<input type="submit" value="수정">
		<input type="button" value="취소" onClick="javaScript:location='../shop/Index.jsp'">
		</td>
	</tr>
	
	
</table>


</form>

<%@ include file="Bottom.jsp" %>
</body>
</html>