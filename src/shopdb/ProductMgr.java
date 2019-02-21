package shopdb;

//jdk/jre/lib/ext/cos.jar
//Tomcat8/lib/cos.jar

//��ǰ ��� �Ϸ���, ���� ���ε�, cos.jar ���
//���� ������Ʈ WEB-INF/lib/cos.jar

import java.sql.*;//Connection,PreparedStatement..
import java.util.*;//List,Vector ..
import javax.sql.*;//DataSource
import javax.naming.*;//lookup

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import javax.servlet.http.*;//����
import java.io.*;//�׸����� ���� �ϱ� ���ؼ�
//�����Ͻ� ����
public class ProductMgr {

	//�̱��� ��ü�� ����ϸ� �޸� ���� ȿ��
	private ProductMgr(){}//����Ʈ������
	private static ProductMgr dao=new ProductMgr();//��ü ����
	
	//jsp���� ȣ���Ͽ� ��ü�� ��� ���� �޼���
	public static ProductMgr getDao(){
		return dao;
	}//
	
	//Ŀ�ؼ� Ǯ ���
	private Connection getCon() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
		
	}//getCon() end
	
	
	//----------------
	//1.��ǰ��� ����Ʈ
	//----------------
	public List getGoodList() throws Exception{
		String sql="";//����
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		List<ProductDto> goodList=new ArrayList<ProductDto>();
		
		try{
			con=getCon();//Ŀ�ؼǾ��
			
			sql="select * from shop_info";
			stmt=con.createStatement();//Statement ����
			rs=stmt.executeQuery(sql);//��������
			
			while(rs.next()){
				ProductDto dto=new ProductDto();
				
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setCode(rs.getString("code"));
				dto.setName(rs.getString("name"));
				
				dto.setPrice(rs.getInt("price"));
				dto.setStock(rs.getInt("stock"));
				dto.setDetail(rs.getString("detail"));
				dto.setComp(rs.getString("comp"));
				
				//NOW():����� �ú���
				//rs.getTimestamp()�о�´�
				dto.setRegdate(rs.getTimestamp("regdate"));
				dto.setImage(rs.getString("image"));
				
				goodList.add(dto);//�𵨺��� List��ü�� �ִ´�
				
			}//while end
			
		}catch(Exception ex1){
			System.out.println("getGoodList() ���� : "+ex1);
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
	//�󼼺���
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
				
				//NOW() : ����� �Ô���, rs.getTimestamp() �о�´�
				dto.setRegdate(rs.getTimestamp("regdate"));
				dto.setImage(rs.getString("image"));
			}//while end
		}catch(Exception ex1){
			System.out.println("getDetails() ���� : "+ex1);
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
	//3.pro_no ����: ��ǰ �Ϸù�ȣ, update���� ���
	//�ֹ� ��� ����Ʈ���� ���
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
			rs=pstmt.executeQuery();//��������
			
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
			System.out.println("getProduct() ���� : "+ex1);
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
	//4.���� ���� ���
	//-----------------------
	public void reduceProduct(OrderDto order){
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try{
			con=getCon();
			String sql="update shop_info set stock=(stock-?) where pro_no=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1,order.getQuantity());//����
			pstmt.setInt(2, order.getPro_no());
			pstmt.executeUpdate();//���� ����
			
		}catch(Exception ex1){
			System.out.println("reduceProduct() ���� : "+ex1);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex2){
				
			}
		}//finally end
		
		
	}//reduceProduct() end------------------------
	
	
	//---------------------
	//�����ڰ� �۾��ؾ� �ϴ� �κ�
	//---------------------
	
	//insert : ��ǰ ���
	//import javax.servlet.http.*;
	//import com.oreilly.servlet.*;
	//import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
	
	public boolean insertProduct(HttpServletRequest req){
		
		Connection con=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		
		//JSP��� : ������� ��� : �׸�����ϱ� ����
		//<%= config.getServletContext().getRealPath("/")%>;//�̰��� ����ϼ���
		//<%= application.getRealpath("/")%>
		
		//�������� ���� ��� ��� : �׸���� �ϱ� ����
		//request.getRealPath("/");
		//request.getServletCotnext().getRealPath("/"); => �̰��� �� ��Ȯ��
		
		//JSP �� ��� ��� : �׸��� ������ ������ ����Ҷ�
		//<%=request.getContextPath()%>
		//==>������Ʈ�Ϲ��� ���ؽ�Ʈ �̸��̴�
		
		try{
			con=getCon();
			String real_path=req.getServletContext().getRealPath("/");//������ξ��
			String upload_Dir=real_path+"/imgs/";//��ǰ��� �Ϸ���
			
			//cos.jar ����Ͽ� ���� ���ε��Ѵ�
			//���� ���ε�� ��ü������ ���ε� �ȴ�
			MultipartRequest multi= new MultipartRequest(req,upload_Dir,5*1024*1024,"utf-8",new DefaultFileRenamePolicy());
			
			String sql="";
			sql="insert into shop_info(pro_no,name,code,price,stock,detail,comp,regdate,image)"
								+ " values(0,?,?,?,?,?,?,NOW(),?)";
			
			pstmt=con.prepareStatement(sql);//������ ���� ����
			
			//?��ä���
			pstmt.setString(1, multi.getParameter("name"));
			pstmt.setString(2, multi.getParameter("code"));
			pstmt.setInt(3, Integer.parseInt(multi.getParameter("price")));
			pstmt.setInt(4, Integer.parseInt(multi.getParameter("stock")));
			
			pstmt.setString(5, multi.getParameter("detail"));
			pstmt.setString(6, multi.getParameter("comp"));
			
			if(multi.getFilesystemName("image")!=null){//�׸� ������������
				pstmt.setString(7, multi.getFilesystemName("image"));
			}else{
				pstmt.setString(7, "ready.gif");
			}//else
			
			int count=pstmt.executeUpdate();//���� ����
			
			if(count==1){//insert�� �Ǿ�����
				result=true;
			}
			
		}catch(Exception ex1){
			System.out.println("insertProduct() ���� : "+ex1);
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
	//��ǰ update (�����ڰ� �ϴ� ��)
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
			
			//�׸� �ø���(���� ���ε�)
			MultipartRequest multi= new MultipartRequest(req,upload_Dir,5*1024*1024,"utf-8",new DefaultFileRenamePolicy());
			
			if(multi.getFilesystemName("image")==null){
				//�׸������� ������
				sql="update shop_info set name=?,stock=?,detail=?,price=?,code=?,comp=? where pro_no=?";
				
				pstmt=con.prepareStatement(sql);//������ ���� ����
				
				
				//?�� ä���
				pstmt.setString(1, multi.getParameter("name"));
				pstmt.setInt(2, Integer.parseInt(multi.getParameter("stock")));
				pstmt.setString(3, multi.getParameter("detail"));

				pstmt.setInt(4, Integer.parseInt(multi.getParameter("price")));
				pstmt.setString(5, multi.getParameter("code"));
				pstmt.setString(6, multi.getParameter("comp"));
				
				pstmt.setInt(7, Integer.parseInt(multi.getParameter("pro_no")));
				
				
			}else{
				//�׸������� ������
				//���� �׸������� �����Ѵ�
				int im=Integer.parseInt(multi.getParameter("pro_no"));
				String sql2="select image from shop_info where pro_no="+im;
				
				Statement stmt=con.createStatement();//Statement ����
				ResultSet rs=stmt.executeQuery(sql2);//���� ����
				
				if(rs.next()){//�̹��� �����ϸ�
					//String image=rs.getString("image");
					//File f=new File(upload_Dir+image);
					File f=new File(upload_Dir+rs.getString("image"));
					if(f.isFile()){//�׸������� �����ϸ� ����
						f.delete();//***�׸� ���� ����
					}//if
					
				}//if
				rs.close();
				stmt.close();
				
				//update���� �ۼ�
				sql="update shop_info set name=?,stock=?,detail=?,price=?,code=?,comp=?,image=? where pro_no=?";
				pstmt=con.prepareStatement(sql);//������ ���� ����
				
				//?��ä���
				pstmt.setString(1, multi.getParameter("name"));
				pstmt.setInt(2, Integer.parseInt(multi.getParameter("stock")));
				pstmt.setString(3, multi.getParameter("detail"));
				
				pstmt.setInt(4, Integer.parseInt(multi.getParameter("price")));
				pstmt.setString(5, multi.getParameter("code"));
				pstmt.setString(6, multi.getParameter("comp"));
				
				pstmt.setString(7, multi.getParameter("image"));
				pstmt.setInt(8, Integer.parseInt(multi.getParameter("pro_no")));
				
			}//else end
			int count=pstmt.executeUpdate();//���� ����
			
			if(count==1){//insert�� �Ǿ�����
				result=true;
			}
		}catch(Exception ex1){
			System.out.println("updateProduct() ���� : "+ex1);
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
	//��=row=record ���� (�����ڰ� �ϴ���)
	//�׸����� ����(���ε嵵�� �׸� ����)
	//--------------------------------
	public boolean deleteProduct(HttpServletRequest req,int pro_no){

	      Connection con=null;
	      PreparedStatement pstmt=null;
	      boolean result= false;

	      try{

	         con=getCon(); //Ŀ�ؼ� ���

	         //�׸����� ����

	         String sql2="select image from shop_info where pro_no="+pro_no;
	         String real_path=req.getServletContext().getRealPath("/");
	         String upload_Dir=real_path+"imgs/";

	         Statement stmt=con.createStatement();//Statement ����
	         ResultSet rs=stmt.executeQuery(sql2);//����� ���� ����, Statement

	         if(rs.next()){
	            File f= new File(upload_Dir+rs.getString("image"));
	            if(f.isFile()){
	               f.delete();//�׸����� ����
	            }//if
	         }//if

	         rs.close();
	         stmt.close();

	         //DB shop_info ���̺� ���ڵ� ����
	         String sql="delete from shop_info where pro_no=?";
	         pstmt=con.prepareStatement(sql);//������ ���� ����
	         pstmt.setInt(1, pro_no);
	         int count=pstmt.executeUpdate();//��������

	         if(count>0){
	            result=true;
	         }

	      }catch(Exception ex1){
	         System.out.println("deleteProduct() ���� :" +ex1);
	      }finally{
	         try{
	            if(pstmt!=null){pstmt.close();}
	            if(con!=null){con.close();}
	         }catch(Exception ex2){}
	      }
	      return result;

	   }//deleteProduct() end

	
}//class
