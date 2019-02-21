<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ page import="member.*" %>
    
    <%--loginPro.jsp --%>
    
    <%
    request.setCharacterEncoding("utf-8");
    String id=request.getParameter("id");
    String passwd=request.getParameter("passwd");
    
    member.MemberDAO dao=member.MemberDAO.getInstance();//dao객체 얻기
    int check=dao.userCheck(id,passwd);//dao메서드 호출
    
    if(check==1){//로그인 정상
    	session.setAttribute("mem_id", id);
    	response.sendRedirect("../shop/Index.jsp");
    }else if(check==0){//비밀번호가 다를때
    %>	
    	<script>
    	alert("비밀번호가 다릅니다");
    	history.back();
    	</script>
    <%
    }else if(check==-1){//아이디가 존재하지 않을때
    %>
    	<script>
    	alert("아이디가  맞지 않습니다");
    	history.back();
    	</script>
    <%	
    }//else end------------
    
    %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>