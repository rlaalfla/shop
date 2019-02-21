<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="java.util.*" %>
    <%@ page import="shopdb.*" %>
    
    <%--ProductDetail.jsp --%>
    
    <%
    request.setCharacterEncoding("utf-8");//post요청 한글 처리
    java.util.Date date=new java.util.Date();
    java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd H:mm:ss");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

</head>
<body topmargin="20">
	<%@ include file="Top.jsp" %>
<%
ProductMgr dao=ProductMgr.getDao();//dao객체 얻기
String code=request.getParameter("code");//상품 code값
ProductDto dto=dao.getDetails(code);//DAO메소드 호출
%>

<center><h2>상세보기</h2></center>

<%--장바구니에 담기로 이동 --%>

<form method="post" action="CartProc.jsp">
<table align="center" border="1" width="80%" cellpadding="3" bgcolor="ivory">

<tr align=center bgcolor=#FFE4E1 height=120%>
	<td colspan=2>
	<font size=2><%=dto.getName() %></font>
	</td>
</tr>

<tr>
	<td colspan=2 align=center>
	<img src="<%=request.getContextPath() %>/imgs/<%=dto.getImage() %>">
	</td>
</tr>

<%--
//이미지 파일을 가져올 때 사용
컨텍스트 경로(웹경로):<%=request.getContextPath() %><br>

//이미지파일 파일 업로드할때 사용
실제경로:<%=config.getServletContext().getRealPath("/")%>
 --%>

<tr>
	<td width=45%>상품코드</td>
	<td>
	<input type="text" name="code" id="code" value=<%=dto.getCode() %> >
	</td>
</tr>

<tr>
	<td>가격</td>
	<td>
	<input type="text" name="price" id="price" value=<%=dto.getPrice() %>>
	</td>
</tr>

<tr>
	<td>출시날짜</td>
	<td>
	<%=sdf.format(dto.getRegdate()) %>
	</td>
</tr>

<%--
OrderMgr.java에서 NOW()로 입력 햇음
아래와 같이 넘기면 무조건 String이다
<input type="hidden" name="ord_date" id="ord_date" value="<%=sdf.format(date)%>">
 --%>
 
 <tr>
	<td>제조회사(국내산)</td>
	<td>
	<input type="text" name="comp" id="comp" value=<%=dto.getComp() %>>
	</td>
</tr>
 
<tr>
	<td>수량:</td>
	<td>
	<select name="quantity" id="quantity">
		<option value="1" selected="selected">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
	</select>
	</td>
	
	<%--
	<td>
	<input type="number" min="1" max="10" step="1" name="quantity" id="quantity" required="required">
	</td>
	--%>
</tr>

<tr>
	<td colspan="2" align="center">
	<input type="submit" value="장바구니담기">
	<input type="reset" value="취소">
	
	<input type=hidden name=pro_no value=<%=dto.getPro_no() %>> 
	<input type=hidden name=userid value=<%=session.getAttribute("mem_id") %>> 
	<input type=hidden name=state value="1"> 
	</td>
</tr>

</table>



</form>





</body>
</html>




