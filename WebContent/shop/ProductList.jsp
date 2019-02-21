<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="java.util.*" %>
    <%@ page import="shopdb.*" %>
    
    <%--ProductList.jsp --%>
    
<%
request.setCharacterEncoding("utf-8");//post요청 한글 처리
%>

<%
List shopList;//전역변수
%>

<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script type="text/javascript" src="script.js"></script>

</head>

<body topmargin="20">
<%@ include file="Top.jsp" %>

<%
shopdb.ProductMgr dao=shopdb.ProductMgr.getDao();//dao객체 얻기
shopList=dao.getGoodList();//dao메서드 호출

if(shopList.isEmpty()){//상품이 없으면
%>
등록된 상품이 없습니다
<%	
}else{//상품이 있으면
%>
	<table width=80% cellpadding=3 align=center>
	<tr>
	<%
	for(int i=0;i<shopList.size();i++){
		if(i%3==0){
			out.println("</tr><tr>");
		}//if
		
		//list에서 bean를 꺼내어 형변환하여 할당한다
		ProductDto dto=(ProductDto)shopList.get(i);
		%>
		<td>
		<table border="0">
			<tr align=center bgcolor=#D0D0D0 height=120%>
				<td colspan=2><%=dto.getName() %></td>
			</tr>
			
			<tr>
			<td colspan=2 align=center>
			현재경로:<%=request.getContextPath() %>
			<%
			if(dto.getStock()>0){//상품 수량이 있을때
				%>
				<a href="javascript:productDetail('<%=dto.getCode() %>')">
				<img src="<%=request.getContextPath()%>/imgs/<%=dto.getImage()%>" width="150" height="150">
				</a>
				<%
			}else{//상품 수량이 없을때
				%>
				<script>
				alert("<%=dto.getName()%>품절상품입니다");			
				</script>
				<%
			}//else end
			%>
			</td>
			</tr>
			
			<tr>
			<td width=50%>상품코드:</td>
			<td><%=dto.getCode() %></td>
			</tr>
			
			<tr>
			<td>가격:</td>
			<td><%=dto.getPrice() %></td>
			</tr>
			
			<tr>
			<td>출시날짜:</td>
			<td><%=dto.getRegdate() %></td>
			</tr>
			
			<tr>
			<td>제조회사(국내산)</td>
			<td><%=dto.getComp() %></td>
			</tr>
			
			<tr>
			<td>물량,stock</td>
			<td>
			<%
			if(dto.getStock()>0){
				%>
				<%=dto.getStock() %>
				<%
			}else{
				%>
				품절
				<%
			}
			%>
			</td>
			</tr>
		</table>
		</td>
		<%
	}//for end
	%>
	</tr>
	</table>
<%	
}//else end
%>

<form name="detail" method="post" action="ProductDetail.jsp">
<input type="hidden" name="code">

</form>


<%@ include file="Bottom.jsp" %>


</body>
</html>