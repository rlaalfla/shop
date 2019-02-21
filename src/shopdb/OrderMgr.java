package shopdb;
import java.sql.*;//Connection,Statement,ResultSet
import java.util.Vector;


import javax.sql.*;//DataSource
import javax.naming.*;//lookup


import member.MemberDAO;


//DAO:비지니스로직
public class OrderMgr {


	//싱글톤 객체 사용, 객체를 하나만 사용하도록 한다. 메모리 절약이 된다
		private static OrderMgr instance=new OrderMgr();//객체 생성
		
		//JSP에서 객체를 얻을 때는  : OrderMgr.getInstance()
		public static OrderMgr getInstance(){
			return instance;
		}//getInstance()

	public OrderMgr(){}//디폴트 생성자


	//커넥션 얻기
	public Connection getCon() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}//getCon()

	
	//--------------
	//1.주문
	//---------------
	public void insertOrder(OrderDto dto)throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try{
			con=getCon();//커넥션 얻기
			String sql="insert into shop_order(ordno,userid,pro_no,quantity,orddate,state)"
									+" values(0,?,?,?,NOW(),?)";
			
			pstmt=con.prepareStatement(sql);//생성시 인자 들어간다
			
			pstmt.setString(1, dto.getUserid());
			pstmt.setInt(2, dto.getPro_no());
			pstmt.setString(3, dto.getQuantity());
			pstmt.setString(4, "1");//상태
			
			//1 접수중
			//2 접수
			//3 입금확인
			//4 배송준비
			//5 배송중
			//	배송완료
			
			pstmt.executeUpdate();//쿼리실행
			
			}catch(Exception ex1){
			System.out.println("insertOrder() 예외 : "+ex1);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex2){
				
			}
		}//finally end
		
		
	}//insertOrder() end-----------------------------------
	

	//--------------------------------------
	//2.userid(한명의 클라이언트)에 해당하는 주문 목록 얻기
	//---------------------------------------
	public Vector<OrderDto> getOrder(String userid){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="";
		Vector<OrderDto> vec=new Vector<OrderDto>();
		
		try{
			con=getCon();//커넥션 얻기
			sql="select * from shop_order where userid=? order by ordno desc";
			pstmt=con.prepareStatement(sql);//생성시 인자 들어간다
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();//쿼리실행
			
			OrderDto dto=null;
			while(rs.next()){
			
				dto=new OrderDto();
				
				dto.setUserid(rs.getString("userid"));
				dto.setQuantity(rs.getString("quantity"));
				dto.setOrddate(rs.getTimestamp("orddate"));
				dto.setOrdno(rs.getInt("ordno"));
				dto.setState(rs.getString("state"));
				dto.setPro_no(rs.getInt("pro_no"));
				
				vec.add(dto);//벡터에 dto를 넣는다
			}//while end
			
		}catch(Exception ex1){
			System.out.println("getOrder() 예외 : "+ex1);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex2){

			}
		}//finally end
		
		return vec;
	}//getOrder() end---------------------------------

	
	//----------------
	//3.모든 주문 정보
	//-----------------
	public Vector getOrderList(){
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		Vector <OrderDto> vec=new Vector<OrderDto>();
		OrderDto dto=null;
		String sql="";

		try{
			con=getCon();
			sql="select * from shop_order order by ordno desc";
			stmt=con.createStatement();
			rs=stmt.executeQuery(sql);//실행시 인자 들어간다
			
			while(rs.next()){
				dto=new OrderDto();

				dto.setUserid(rs.getString("userid"));
				dto.setQuantity(rs.getString("quantity"));
				dto.setOrddate(rs.getTimestamp("orddate"));
				dto.setOrdno(rs.getInt("ordno"));
				dto.setState(rs.getString("state"));
				dto.setPro_no(rs.getInt("pro_no"));

				vec.add(dto);

			}//while end

		}catch(Exception ex1){
			System.out.println("getOrderList() 예외 : "+ex1);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex2){

			}
		}//fianlly end
		return vec;
	}//getOrderList() end----------------------------

}//class