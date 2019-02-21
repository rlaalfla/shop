<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.*" %>
    
    <%--Login.jsp --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

<script type="text/javascript">

function focusIt(){
	document.inForm.id.focus();
}//focusIt() end---------------

function checkIt(){
	InputForm=eval("document.inForm");
	
	//eval()함수
	//1.인자로 받은 문자열이 숫자라면 실제로 숫자로 바뀐다
	
	//2.인자로 받은 문자열의 내용이 자바스크립트가 인식할 수 있는
	//객체 형태라면, 문자열을 받아서 자바스크립트 객체로 리턴한다
	
	//3.eval()함수는 파싱도 한다
	
	if(!InputForm.id.value){
		alert("아이디를 입력하시오");
		InputForm.id.focus();
		return false;
	}
	
	if(!InputForm.passwd.value){
		alert("암호를 입력하시오");
		InputForm.passwd.focus();
		return false;
	}
}//checkIt() end---------------

</script>

</head>
<body topmargin="100">
<%@ include file="Top.jsp" %>


<table width="80%" align="center">
<tr>
<td height="190">
	<form name="inForm" method="post" action="LoginPro.jsp" onSubmit="return checkIt()">
		<table width="50%" border="1" align="center">
		
		<tr>
			<td colspan="2" align="center">로그인</td>
		</tr>
		
		<tr>
			<td width="47%" align="center">ID</td>
			<td width="53%" align="center">
			<input type="text" name="id" id="mem_id">
			</td>
		</tr>
		
		<tr>
			<td align="center">PWD</td>
			<td align="center">
			<input type="password" name="passwd" id="mem_passwd">
			</td>
		</tr>
		
		<tr>
			<td colspan="2" align="center">
			<input type="submit" value="login">
			&nbsp;&nbsp;
			<input type="button" value="회원가입" onClick="javaScript:location='InputForm.jsp'">
			</td>
		</tr>
		
		
		</table>
	</form>
	
</td>
</tr>

</table>
<%@ include file="Bottom.jsp" %>

</body>
</html>