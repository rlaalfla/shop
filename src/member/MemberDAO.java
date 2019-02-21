package member;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.*;//DataSource
import javax.naming.*;//lookup

import org.apache.tomcat.jdbc.pool.DataSource;

//DAO �����Ͻ� ����
public class MemberDAO {
	
	//�̱��� ��ü ���, ��ü�� �ϳ��� ����ϵ��� �Ѵ�. �޸� ������ �ȴ�
	private static MemberDAO instance=new MemberDAO();//��ü ����
	
	//JSP���� ��ü�� ���� ����  : MemberDAO.getInstance()
	public static MemberDAO getInstance(){
		return instance;
	}//getInstance()
	
	private MemberDAO(){}//����Ʈ ������, �ܺο��� ��ü ���� ���Ѵ�,
						//<jsp:useBean>�� ��� ����
	
	//----------------
	//Ŀ�ؼ� Ǯ ��� �޼���
	//----------------
	 private Connection getCon() throws Exception{
		 Context ct=new InitialContext();
		 DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		 return ds.getConnection();
	 }//getCon() end
	 
	 
	 //-----------------
	 //id �ߺ� üũ
	 //-----------------
	 public int confirmId(String id)throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 int x=1;
		 
		 try{
			 con=getCon();//Ŀ�ؼ� ���
			 pstmt=con.prepareStatement("select id from member where id=?");
			 pstmt.setString(1, id);
			 rs=pstmt.executeQuery();//��������
			 
			 if(rs.next()){//������� id
				 x=1;
			 }else{//��밡���� id
				 x=-1;
			 }
		 }catch(Exception ex1){
			 System.out.println("confirmId ���� : "+ex1);
		 }finally{
			 try{
				 if(rs!=null){rs.close();}
				 if(pstmt!=null){pstmt.close();}
				 if(con!=null){con.close();}
			 }catch(Exception ex2){
				 
			 }
		 }//finally end
		 return x;
	 }//confirmId() end---------------------------------
	
	 
	 //-------------------
	 //ȸ�� ����
	 //-------------------
	 public void insertMember(MemberDTO dto) throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 
		 try{
			 con=getCon();//Ŀ�ؼ� ��´�
			 pstmt=con.prepareStatement("insert into member values(?,?,?,?,?,?,?,?,?,?)");
			 
			 //?��ä���
			 pstmt.setString(1, dto.getId());
			 pstmt.setString(2, dto.getPasswd());
			 pstmt.setString(3, dto.getName());
			 pstmt.setString(4, dto.getJumin1());
			 pstmt.setString(5, dto.getJumin2());

			 pstmt.setString(6, dto.getEmail());
			 pstmt.setString(7, dto.getZipcode());
			 pstmt.setString(8, dto.getAddr());
			 pstmt.setString(9, dto.getJob());
			 pstmt.setTimestamp(10, dto.getRegdate());
			 
			 pstmt.executeUpdate();//��������*****
			 
		 }catch(Exception ex1){
			 System.out.println("insertMember ���� : "+ex1);
		 }finally{
			 try{
				 if(pstmt!=null){pstmt.close();}
				 if(con!=null){con.close();}
			 }catch(Exception ex2){
				 
			 }
		 }//finally end
	 }//insertMember() end-----------------------------------
	 
	 //-------------------
	 //�α���(����)
	 //-------------------
	 public int userCheck(String id,String passwd) throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 String dbPwd="";
		 int x=-1;
		 
		 try{
			 con=getCon();
			 pstmt=con.prepareStatement("select * from member where id=?");
			 pstmt.setString(1, id);
			 rs=pstmt.executeQuery();//���� ����
			 
			 if(rs.next()){
				 dbPwd=rs.getString("passwd");
				 
				 if(passwd.equals(dbPwd)){
					 x=1;//���� ����
				 }else{
					 x=0;//��ȣ�� �ٸ�
				 }
						 
			 }else{
				 x=-1;//�ش� ���̵� ����
			 }//else
		 }catch(Exception ex1){
			 System.out.println("userCheck() ���� : "+ex1);
		 }finally{
			 try{
				 if(rs!=null){rs.close();}
				 if(pstmt!=null){pstmt.close();}
				 if(con!=null){con.close();}
			 }catch(Exception ex2){
				 
			 }
		 }//finally end
		 return x;
	 }//userCheck() end-----------------------------
	 
	 
	 //----------------
	 //ȸ������ ����:���� ���
	 //-----------------
	 public MemberDTO getMember(String id) throws Exception{
		 MemberDTO dto=null;//����
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{
			 con=getCon();//Ŀ�ؼ� ���
			 pstmt=con.prepareStatement("select * from member where id='"+id+"'");
			 rs=pstmt.executeQuery();//��������
			 
			 if(rs.next()){
				 dto=new MemberDTO();
				 
				 dto.setId(rs.getString("id"));
				 dto.setPasswd(rs.getString("passwd"));
				 dto.setName(rs.getString("name"));
				 
				 dto.setJumin1(rs.getString("jumin1"));
				 dto.setJumin2(rs.getString("jumin2"));
				 dto.setEmail(rs.getString("email"));
				 
				 dto.setZipcode(rs.getString("zipcode"));
				 dto.setAddr(rs.getString("addr"));
				 dto.setJob(rs.getString("job"));
				 dto.setRegdate(rs.getTimestamp("regdate"));
				 
			 }//if
		 }catch(Exception ex1){
			 System.out.println("getMember() ���� : "+ex1);
		 }finally{
			 try{
				 if(rs!=null){rs.close();}
				 if(pstmt!=null){pstmt.close();}
				 if(con!=null){con.close();}
				 
			 }catch(Exception ex2){
				 
			 }
		 }//finally end
		 
		 return dto;
	 }//getMember() end------------------------------------
	 
	 
	 //----------------
	 //ȸ������ ����(DB)
	 //----------------
	 public void updateMember(MemberDTO dto)throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 String sql="";
		 
		 try{
			 con=getCon();//Ŀ�ؼ� ���
			 sql="update member set passwd=?,name=?,email=?,zipcode=?,addr=?,job=? where id=?";
			 pstmt=con.prepareStatement(sql);//PreparedStatement ����
			 
			 //?�� ä���
			 pstmt.setString(1, dto.getPasswd());
			 pstmt.setString(2, dto.getName());
			 pstmt.setString(3, dto.getEmail());
			 pstmt.setString(4, dto.getZipcode());
			 pstmt.setString(5, dto.getAddr());
			 pstmt.setString(6, dto.getJob());
			 pstmt.setString(7, dto.getId());
			 
			 pstmt.executeUpdate();//���� ����
		 }catch(Exception ex1){
			 System.out.println("updateMember() ���� : "+ex1);
		 }finally{
			 try{
				 if(pstmt!=null){pstmt.close();}
				 if(con!=null){con.close();}
			 }catch(Exception ex2){
				 
			 }
		 }//finally end
		 
		 
	 }//updateMember() end-------------------------------------
	
	 
	 //-----------------
	 //ȸ��Ż��
	 //-----------------
	 public int deleteMember(String id,String passwd)throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 PreparedStatement pstmt2=null;
		 ResultSet rs=null;
		 
		 String dbPasswd="";
		 int x=-1;
		 
		 try{
			 con=getCon();//Ŀ�ؼ� ���
			 pstmt=con.prepareStatement("select passwd from member where id=?");
			 pstmt.setString(1, id);//? �� ä���
			 rs=pstmt.executeQuery();//��������
			 
			 if(rs.next()){
				 dbPasswd=rs.getString("passwd");
				 if(passwd.equals(dbPasswd)){//��ȣ�� ��ġ�ϸ� ����
					 pstmt2=con.prepareStatement("delete from member where id=?");
					 pstmt2.setString(1, id);
					 pstmt2.executeUpdate();//��������
					 x=1;//ȸ�� Ż��
				 }else{
					 x=-1;//��й�ȣ Ʋ��
				 }//else end
				 
			 }else{
				 x=0;//�������� ������
			 }//else end
			 
			 
		 }catch(Exception ex1){
			 System.out.println("deleteMember ���� : "+ex1);
		 }finally{
			 try{
				 if(rs!=null){rs.close();}
				 if(pstmt!=null){pstmt.close();}
				 if(pstmt2!=null){pstmt2.close();}
				 if(con!=null){con.close();}
			 }catch(Exception ex2){
				 
			 }
			 
		 }//finally end
		 
		 
		 return x;
	 }//deleteMember() end-------------------------------------------

	 
	 //-----------------------------------������------------------------------------
	 //------------
	 //ȸ�� ��ü��� ����Ʈ ���
	 //-------------
	 public ArrayList<MemberDTO> getMemberAll(){
		 ArrayList<MemberDTO> list=new ArrayList<MemberDTO>();
		 
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 String sql="";
		 
		 try{
			 con=getCon();//Ŀ�ؼ� ���
			 pstmt=con.prepareStatement("select * from member");
			 rs=pstmt.executeQuery();//��������
			 
			 while(rs.next()){
				MemberDTO dto=new MemberDTO();
				 
				 dto.setId(rs.getString("id"));
				 dto.setPasswd(rs.getString("passwd"));
				 dto.setName(rs.getString("name"));
				 
				 dto.setJumin1(rs.getString("jumin1"));
				 dto.setJumin2(rs.getString("jumin2"));
				 dto.setEmail(rs.getString("email"));
				 
				 dto.setZipcode(rs.getString("zipcode"));
				 dto.setAddr(rs.getString("addr"));
				 dto.setJob(rs.getString("job"));
				 dto.setRegdate(rs.getTimestamp("regdate"));
				 
				 
				 list.add(dto);
			 }
			 
		 }catch(Exception ex1){
			 System.out.println("getMemberAll() ���� : "+ex1);
		 }finally{
			 try{
				 if(rs!=null){rs.close();}
				 if(pstmt!=null){pstmt.close();}
				 if(con!=null){con.close();}
			 }catch(Exception ex2){
				 
			 }
		 }//finally end
		 return list;
	 }//getMemberAll() end -----------------------------

	 
	 //-------------------
	 //�����ڷα���(����)
	 //-------------------
	 public int adminCheck(String admin_id,String admin_pwd) throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 String dbPwd="";
		 int x=-1;
		 
		 try{
			 con=getCon();
			 pstmt=con.prepareStatement("select * from admin where admin_id=?");
			 pstmt.setString(1, admin_id);
			 rs=pstmt.executeQuery();//���� ����
			 
			 if(rs.next()){
				 dbPwd=rs.getString("admin_pwd");
				 
				 if(admin_pwd.equals(dbPwd)){
					 x=1;//���� ����
				 }else{
					 x=0;//��ȣ�� �ٸ�
				 }
			 }else{
				 x=-1;//�ش� ���̵� ����
			 }//else
		 }catch(Exception ex1){
			 System.out.println("userCheck() ���� : "+ex1);
		 }finally{
			 try{
				 if(rs!=null){rs.close();}
				 if(pstmt!=null){pstmt.close();}
				 if(con!=null){con.close();}
			 }catch(Exception ex2){
			 }
		 }//finally end
		 return x;
	 }//userCheck() end-----------------------------
	 
}//class
