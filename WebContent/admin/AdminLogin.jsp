<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%--AdminLogin.jsp --%>
    
<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

<script type="text/javascript">
function admincheck(){
	if($('#admn_id').val()==''){
		alert("admin_id를 입력 하시오");
		$('#admin_id').val().focus();
		return false;
	}
	if($('#admin_pwd').val()==''){
		alert("admin_pwd를 입력 하시오");
		$('#admin_pwd').val().focus();
		return false;
	}
	document.ff.submit();      
}
</script>
</head>

<body topmargin="20">
<form name="ff" method="post" action="AdminLoginProc.jsp">
<table width="50%" border="1" align="center">
	<tr>
	<td width="47%" align="center">Admin ID</td>
	<td width="53%">
	<input type="text" name="admin_id" id="admin_id">
	</td>
	</tr>
	
	<tr>
	<td align="center">Admin PWD</td>
	<td>
	<input type="password" name="admin_pwd" id="admin_pwd">
	</td>
	</tr>
	
	<tr>
	<td colspan="2" align="center">
	<input type="button" value="Admin로그인" onclick="admincheck()">
	<input type="reset" value="취소">
	</td>
	</tr>

</table>



</form>



</body>
</html>