package shopdb;

//jdk/jre/lib/ext/cos.jar
//Tomcat8/lib/cos.jar

//상품 등록 하려고, 파일 업로드, cos.jar 사용
//현재 프로젝트 WEB-INF/lib/cos.jar

import java.sql.*;//Connection,PreparedStatement..
import java.util.*;//List,Vector ..
import javax.sql.*;//DataSource
import javax.naming.*;//lookup

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import javax.servlet.http.*;//서블릿
import java.io.*;//그림파일 삭제 하기 위해서
//비지니스 로직
public class ProductMgr {

	//싱글톤 객체를 사용하면 메모리 절약 효과
	private ProductMgr(){}//디폴트생성자
	private static ProductMgr dao=new ProductMgr();//객체 생성
	
	//jsp에서 호출하여 객체를 얻어 가는 메서드
	public static ProductMgr getDao(){
		return dao;
	}//
	
	//커넥션 풀 사용
	private Connection getCon() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
		
	}//getCon() end
	
	
	//----------------
	//1.상품목록 리스트
	//----------------
	public List getGoodList() throws Exception{
		String sql="";//변수
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		List<ProductDto> goodList=new ArrayList<ProductDto>();
		
		try{
			con=getCon();//커넥션얻기
			
			sql="select * from shop_info";
			stmt=con.createStatement();//Statement 생성
			rs=stmt.executeQuery(sql);//쿼리실행
			
			while(rs.next()){
				ProductDto dto=new ProductDto();
				
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setCode(rs.getString("code"));
				dto.setName(rs.getString("name"));
				
				dto.setPrice(rs.getInt("price"));
				dto.setStock(rs.getInt("stock"));
				dto.setDetail(rs.getString("detail"));
				dto.setComp(rs.getString("comp"));
				
				//NOW():년월일 시분초
				//rs.getTimestamp()읽어온다
				dto.setRegdate(rs.getTimestamp("regdate"));
				dto.setImage(rs.getString("image"));
				
				goodList.add(dto);//모델빈을 List객체에 넣는다
				
			}//while end
			
		}catch(Exception ex1){
			System.out.println("getGoodList() 예외 : "+ex1);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex2){
				
			}
		}//finally end
		
		return goodList;
	}//getGoodList() end--------------------------
	
	
	//------------------
	//상세보기
	//------------------
	public ProductDto getDetails(String code) throws Exception{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		ProductDto dto=new ProductDto();
		
		try{
			con=getCon();
			stmt=con.createStatement();
			sql="select * from shop_info where code='"+code+"'";
			rs=stmt.executeQuery(sql);
			
			while(rs.next()){
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setCode(rs.getString("code"));
				dto.setName(rs.getString("name"));
				
				dto.setPrice(rs.getInt("price"));
				dto.setStock(rs.getInt("stock"));
				
				dto.setDetail(rs.getString("detail"));
				dto.setComp(rs.getString("comp"));
				
				//NOW() : 년월일 시붅초, rs.getTimestamp() 읽어온다
				dto.setRegdate(rs.getTimestamp("regdate"));
				dto.setImage(rs.getString("image"));
			}//while end
		}catch(Exception ex1){
			System.out.println("getDetails() 예외 : "+ex1);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex2){
				
			}
		}//finally end
		
		return dto;
	}//getDetails() end----------------------------------------
	
	
	//--------------------------------
	//3.pro_no 정보: 상품 일련번호, update에서 사용
	//주문 목록 리스트에서 사용
	//---------------------------------
	public ProductDto getProduct(String code,int pro_no){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		ProductDto dto=null;
		String sql=null;
		
		try{
			con=getCon();
			sql="select * from shop_info where pro_no=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, pro_no);
			rs=pstmt.executeQuery();//쿼리실행
			
			while(rs.next()){
				dto=new ProductDto();
				
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setCode(rs.getString("code"));
				dto.setName(rs.getString("name"));
				
				dto.setPrice(rs.getInt("price"));
				dto.setStock(rs.getInt("stock"));
				dto.setDetail(rs.getString("detail"));
				
				dto.setComp(rs.getString("comp"));
				dto.setRegdate(rs.getTimestamp("regdate"));
				dto.setImage(rs.getString("image"));
				
				
			}//while end
			
			
		}catch(Exception ex1){
			System.out.println("getProduct() 예외 : "+ex1);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
				
			}catch(Exception ex2){
				
			}
		}//finally end
		
		return dto;
	}//getProduct() end----------------------------------
	
	
	//-----------------------
	//4.남은 물량 계산
	//-----------------------
	public void reduceProduct(OrderDto order){
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try{
			con=getCon();
			String sql="update shop_info set stock=(stock-?) where pro_no=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1,order.getQuantity());//수량
			pstmt.setInt(2, order.getPro_no());
			pstmt.executeUpdate();//쿼리 수행
			
		}catch(Exception ex1){
			System.out.println("reduceProduct() 예외 : "+ex1);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex2){
				
			}
		}//finally end
		
		
	}//reduceProduct() end------------------------
	
	
	//---------------------
	//관리자가 작업해야 하는 부분
	//---------------------
	
	//insert : 상품 등록
	//import javax.servlet.http.*;
	//import com.oreilly.servlet.*;
	//import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
	
	public boolean insertProduct(HttpServletRequest req){
		
		Connection con=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		
		//JSP경우 : 실제경로 얻기 : 그림등록하기 위해
		//<%= config.getServletContext().getRealPath("/")%>;//이것을 사용하세요
		//<%= application.getRealpath("/")%>
		
		//서블릿에서 실제 경로 얻기 : 그림등록 하기 위해
		//request.getRealPath("/");
		//request.getServletCotnext().getRealPath("/"); => 이것이 더 정확함
		
		//JSP 웹 경로 얻기 : 그림을 가져와 웹으로 출력할때
		//<%=request.getContextPath()%>
		//==>프로젝트일므이 컨텍스트 이름이다
		
		try{
			con=getCon();
			String real_path=req.getServletContext().getRealPath("/");//실제경로얻기
			String upload_Dir=real_path+"/imgs/";//상품등록 하려고
			
			//cos.jar 사용하여 파일 업로드한다
			//파일 업로드는 객체생성시 업로드 된다
			MultipartRequest multi= new MultipartRequest(req,upload_Dir,5*1024*1024,"utf-8",new DefaultFileRenamePolicy());
			
			String sql="";
			sql="insert into shop_info(pro_no,name,code,price,stock,detail,comp,regdate,image)"
								+ " values(0,?,?,?,?,?,?,NOW(),?)";
			
			pstmt=con.prepareStatement(sql);//생성시 인자 들어간다
			
			//?값채우기
			pstmt.setString(1, multi.getParameter("name"));
			pstmt.setString(2, multi.getParameter("code"));
			pstmt.setInt(3, Integer.parseInt(multi.getParameter("price")));
			pstmt.setInt(4, Integer.parseInt(multi.getParameter("stock")));
			
			pstmt.setString(5, multi.getParameter("detail"));
			pstmt.setString(6, multi.getParameter("comp"));
			
			if(multi.getFilesystemName("image")!=null){//그림 파일이있을때
				pstmt.setString(7, multi.getFilesystemName("image"));
			}else{
				pstmt.setString(7, "ready.gif");
			}//else
			
			int count=pstmt.executeUpdate();//쿼리 실행
			
			if(count==1){//insert가 되었으면
				result=true;
			}
			
		}catch(Exception ex1){
			System.out.println("insertProduct() 예외 : "+ex1);
		}finally{
			try{
			if(pstmt!=null){pstmt.close();}
			if(con!=null){con.close();}
			}catch(Exception ex2){
				
			}
		}//finally end
		
		return result;
	}//insertProduct() end---------------------------------
	
	
	//------------------------
	//상품 update (관리자가 하는 일)
	//------------------------
	public boolean updateProduct(HttpServletRequest req){
		Connection con=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		String sql="";
		
		try{
			con=getCon();
			String real_path=req.getServletContext().getRealPath("/");
			String upload_Dir=real_path+"/imgs/";
			int size=5*1024*1024;
			
			//그림 올리기(파일 업로드)
			MultipartRequest multi= new MultipartRequest(req,upload_Dir,5*1024*1024,"utf-8",new DefaultFileRenamePolicy());
			
			if(multi.getFilesystemName("image")==null){
				//그림파일이 없으면
				sql="update shop_info set name=?,stock=?,detail=?,price=?,code=?,comp=? where pro_no=?";
				
				pstmt=con.prepareStatement(sql);//생성시 인자 들어간다
				
				
				//?값 채우기
				pstmt.setString(1, multi.getParameter("name"));
				pstmt.setInt(2, Integer.parseInt(multi.getParameter("stock")));
				pstmt.setString(3, multi.getParameter("detail"));

				pstmt.setInt(4, Integer.parseInt(multi.getParameter("price")));
				pstmt.setString(5, multi.getParameter("code"));
				pstmt.setString(6, multi.getParameter("comp"));
				
				pstmt.setInt(7, Integer.parseInt(multi.getParameter("pro_no")));
				
				
			}else{
				//그림파일이 있으면
				//먼저 그림파일을 삭제한다
				int im=Integer.parseInt(multi.getParameter("pro_no"));
				String sql2="select image from shop_info where pro_no="+im;
				
				Statement stmt=con.createStatement();//Statement 생성
				ResultSet rs=stmt.executeQuery(sql2);//쿼리 실행
				
				if(rs.next()){//이미지 존재하면
					//String image=rs.getString("image");
					//File f=new File(upload_Dir+image);
					File f=new File(upload_Dir+rs.getString("image"));
					if(f.isFile()){//그림파일이 존재하면 삭제
						f.delete();//***그림 파일 삭제
					}//if
					
				}//if
				rs.close();
				stmt.close();
				
				//update구문 작성
				sql="update shop_info set name=?,stock=?,detail=?,price=?,code=?,comp=?,image=? where pro_no=?";
				pstmt=con.prepareStatement(sql);//생성시 인자 들어간다
				
				//?값채우기
				pstmt.setString(1, multi.getParameter("name"));
				pstmt.setInt(2, Integer.parseInt(multi.getParameter("stock")));
				pstmt.setString(3, multi.getParameter("detail"));
				
				pstmt.setInt(4, Integer.parseInt(multi.getParameter("price")));
				pstmt.setString(5, multi.getParameter("code"));
				pstmt.setString(6, multi.getParameter("comp"));
				
				pstmt.setString(7, multi.getParameter("image"));
				pstmt.setInt(8, Integer.parseInt(multi.getParameter("pro_no")));
				
			}//else end
			int count=pstmt.executeUpdate();//쿼리 실행
			
			if(count==1){//insert가 되었으면
				result=true;
			}
		}catch(Exception ex1){
			System.out.println("updateProduct() 예외 : "+ex1);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex2){
				
			}
		}//finally end
	
		
		return result;
	}//updateProduct() end--------------------------------
	
	
	//--------------------------------
	//행=row=record 삭제 (관리자가 하는일)
	//그림파일 삭제(업로드도니 그림 삭제)
	//--------------------------------
	public boolean deleteProduct(HttpServletRequest req,int pro_no){

	      Connection con=null;
	      PreparedStatement pstmt=null;
	      boolean result= false;

	      try{

	         con=getCon(); //커넥션 얻기

	         //그림파일 삭제

	         String sql2="select image from shop_info where pro_no="+pro_no;
	         String real_path=req.getServletContext().getRealPath("/");
	         String upload_Dir=real_path+"imgs/";

	         Statement stmt=con.createStatement();//Statement 생성
	         ResultSet rs=stmt.executeQuery(sql2);//실행시 인자 들어간다, Statement

	         if(rs.next()){
	            File f= new File(upload_Dir+rs.getString("image"));
	            if(f.isFile()){
	               f.delete();//그림파일 삭제
	            }//if
	         }//if

	         rs.close();
	         stmt.close();

	         //DB shop_info 테이블 레코드 삭제
	         String sql="delete from shop_info where pro_no=?";
	         pstmt=con.prepareStatement(sql);//생성시 인자 들어간다
	         pstmt.setInt(1, pro_no);
	         int count=pstmt.executeUpdate();//쿼리실행

	         if(count>0){
	            result=true;
	         }

	      }catch(Exception ex1){
	         System.out.println("deleteProduct() 예외 :" +ex1);
	      }finally{
	         try{
	            if(pstmt!=null){pstmt.close();}
	            if(con!=null){con.close();}
	         }catch(Exception ex2){}
	      }
	      return result;

	   }//deleteProduct() end

	
}//class
