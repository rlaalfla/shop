package shopdb;
import java.sql.*;//Connection,Statement,ResultSet
import java.util.Vector;


import javax.sql.*;//DataSource
import javax.naming.*;//lookup


import member.MemberDAO;


//DAO:�����Ͻ�����
public class OrderMgr {


	//�̱��� ��ü ���, ��ü�� �ϳ��� ����ϵ��� �Ѵ�. �޸� ������ �ȴ�
		private static OrderMgr instance=new OrderMgr();//��ü ����
		
		//JSP���� ��ü�� ���� ����  : OrderMgr.getInstance()
		public static OrderMgr getInstance(){
			return instance;
		}//getInstance()

	public OrderMgr(){}//����Ʈ ������


	//Ŀ�ؼ� ���
	public Connection getCon() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}//getCon()

	
	//--------------
	//1.�ֹ�
	//---------------
	public void insertOrder(OrderDto dto)throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try{
			con=getCon();//Ŀ�ؼ� ���
			String sql="insert into shop_order(ordno,userid,pro_no,quantity,orddate,state)"
									+" values(0,?,?,?,NOW(),?)";
			
			pstmt=con.prepareStatement(sql);//������ ���� ����
			
			pstmt.setString(1, dto.getUserid());
			pstmt.setInt(2, dto.getPro_no());
			pstmt.setString(3, dto.getQuantity());
			pstmt.setString(4, "1");//����
			
			//1 ������
			//2 ����
			//3 �Ա�Ȯ��
			//4 ����غ�
			//5 �����
			//	��ۿϷ�
			
			pstmt.executeUpdate();//��������
			
			}catch(Exception ex1){
			System.out.println("insertOrder() ���� : "+ex1);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex2){
				
			}
		}//finally end
		
		
	}//insertOrder() end-----------------------------------
	

	//--------------------------------------
	//2.userid(�Ѹ��� Ŭ���̾�Ʈ)�� �ش��ϴ� �ֹ� ��� ���
	//---------------------------------------
	public Vector<OrderDto> getOrder(String userid){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="";
		Vector<OrderDto> vec=new Vector<OrderDto>();
		
		try{
			con=getCon();//Ŀ�ؼ� ���
			sql="select * from shop_order where userid=? order by ordno desc";
			pstmt=con.prepareStatement(sql);//������ ���� ����
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();//��������
			
			OrderDto dto=null;
			while(rs.next()){
			
				dto=new OrderDto();
				
				dto.setUserid(rs.getString("userid"));
				dto.setQuantity(rs.getString("quantity"));
				dto.setOrddate(rs.getTimestamp("orddate"));
				dto.setOrdno(rs.getInt("ordno"));
				dto.setState(rs.getString("state"));
				dto.setPro_no(rs.getInt("pro_no"));
				
				vec.add(dto);//���Ϳ� dto�� �ִ´�
			}//while end
			
		}catch(Exception ex1){
			System.out.println("getOrder() ���� : "+ex1);
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
	//3.��� �ֹ� ����
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
			rs=stmt.executeQuery(sql);//����� ���� ����
			
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
			System.out.println("getOrderList() ���� : "+ex1);
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