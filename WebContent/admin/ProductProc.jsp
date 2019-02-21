<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>    
<%@ page import="shopdb.*" %>

<%--ProductProc.jsp--%>

<%
request.setCharacterEncoding("utf-8");//post요청 한글 처리
ProductMgr proMgr=ProductMgr.getDao();//dao객체 얻기
%>

<jsp:useBean id="ordMgr" class="shopdb.OrderMgr"/>

<%
String flag=request.getParameter("flag");//insert,update,delete
boolean result=false;

int iti=0;//변수

if(flag.equals("insert")){
	result=proMgr.insertProduct(request);//dao메서도 호출
	response.sendRedirect("ProductList.jsp");//상품목록 보기
	
}else if(flag.equals("update")){
	result=proMgr.updateProduct(request);//dao메서도 호출
	response.sendRedirect("ProductList.jsp");//상품목록 보기
	
	
}else if(flag.equals("del")){
	Vector vec=ordMgr.getOrderList();
	if(vec.size()==0){//주문 목록에 없으면,삭제
		
		int im=Integer.parseInt(request.getParameter("pro_no"));
		result=proMgr.deleteProduct(request, im);//상품삭제
		response.sendRedirect("ProductList.jsp");
		
	}else{//주문 목록이 있으면
		for(int i=0;i<vec.size();i++){
			OrderDto order=(OrderDto)vec.get(i);
			int pro_no=order.getPro_no();//상품일련번호
			
			if(pro_no==Integer.parseInt(request.getParameter("pro_no"))){
				iti++;
			}//if
		}//for end
		
		if(iti==0){//주문 상황이 아니면,삭제
			int im_pro_no=Integer.parseInt(request.getParameter("pro_no"));
		result=proMgr.deleteProduct(request, im_pro_no);//삭제
		response.sendRedirect("ProductList.jsp");
		}else{//주문 상황이면
			result=false;//주문상태라서 삭제 못한 경우
			%>
			<script>
			alert("주문 상태라서 삭제 못함");
			location.href="ProductList.jsp";
			</script>
			<%
		}//else end
	}//else end
}else{//위의 조건에 맞지 않는 경우, 상품 목록 보기
		response.sendRedirect("ProductList.jsp");
}//else end
%>