package member;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.*;//DataSource
import javax.naming.*;//lookup

import org.apache.tomcat.jdbc.pool.DataSource;

//DAO 비지니스 로직
public class MemberDAO {
	
	//싱글톤 객체 사용, 객체를 하나만 사용하도록 한다. 메모리 절약이 된다
	private static MemberDAO instance=new MemberDAO();//객체 생성
	
	//JSP에서 객체를 얻을 때는  : MemberDAO.getInstance()
	public static MemberDAO getInstance(){
		return instance;
	}//getInstance()
	
	private MemberDAO(){}//디폴트 생성자, 외부에서 객체 생성 못한다,
						//<jsp:useBean>도 사용 못함
	
	//----------------
	//커넥션 풀 사용 메서드
	//----------------
	 private Connection getCon() throws Exception{
		 Context ct=new InitialContext();
		 DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		 return ds.getConnection();
	 }//getCon() end
	 
	 
	 //-----------------
	 //id 중복 체크
	 //-----------------
	 public int confirmId(String id)throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 int x=1;
		 
		 try{
			 con=getCon();//커넥션 얻기
			 pstmt=con.prepareStatement("select id from member where id=?");
			 pstmt.setString(1, id);
			 rs=pstmt.executeQuery();//쿼리수행
			 
			 if(rs.next()){//사용중인 id
				 x=1;
			 }else{//사용가능한 id
				 x=-1;
			 }
		 }catch(Exception ex1){
			 System.out.println("confirmId 예외 : "+ex1);
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
	 //회원 가입
	 //-------------------
	 public void insertMember(MemberDTO dto) throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 
		 try{
			 con=getCon();//커넥션 얻는다
			 pstmt=con.prepareStatement("insert into member values(?,?,?,?,?,?,?,?,?,?)");
			 
			 //?값채우기
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
			 
			 pstmt.executeUpdate();//쿼리수행*****
			 
		 }catch(Exception ex1){
			 System.out.println("insertMember 예외 : "+ex1);
		 }finally{
			 try{
				 if(pstmt!=null){pstmt.close();}
				 if(con!=null){con.close();}
			 }catch(Exception ex2){
				 
			 }
		 }//finally end
	 }//insertMember() end-----------------------------------
	 
	 //-------------------
	 //로그인(인증)
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
			 rs=pstmt.executeQuery();//쿼리 수행
			 
			 if(rs.next()){
				 dbPwd=rs.getString("passwd");
				 
				 if(passwd.equals(dbPwd)){
					 x=1;//인증 성공
				 }else{
					 x=0;//암호가 다름
				 }
						 
			 }else{
				 x=-1;//해당 아이디 없음
			 }//else
		 }catch(Exception ex1){
			 System.out.println("userCheck() 예외 : "+ex1);
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
	 //회원정보 수정:웹에 출력
	 //-----------------
	 public MemberDTO getMember(String id) throws Exception{
		 MemberDTO dto=null;//변수
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try{
			 con=getCon();//커넥션 얻기
			 pstmt=con.prepareStatement("select * from member where id='"+id+"'");
			 rs=pstmt.executeQuery();//쿼리실행
			 
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
			 System.out.println("getMember() 예외 : "+ex1);
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
	 //회원정보 수정(DB)
	 //----------------
	 public void updateMember(MemberDTO dto)throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 String sql="";
		 
		 try{
			 con=getCon();//커넥션 얻기
			 sql="update member set passwd=?,name=?,email=?,zipcode=?,addr=?,job=? where id=?";
			 pstmt=con.prepareStatement(sql);//PreparedStatement 생성
			 
			 //?값 채우기
			 pstmt.setString(1, dto.getPasswd());
			 pstmt.setString(2, dto.getName());
			 pstmt.setString(3, dto.getEmail());
			 pstmt.setString(4, dto.getZipcode());
			 pstmt.setString(5, dto.getAddr());
			 pstmt.setString(6, dto.getJob());
			 pstmt.setString(7, dto.getId());
			 
			 pstmt.executeUpdate();//쿼리 실행
		 }catch(Exception ex1){
			 System.out.println("updateMember() 예외 : "+ex1);
		 }finally{
			 try{
				 if(pstmt!=null){pstmt.close();}
				 if(con!=null){con.close();}
			 }catch(Exception ex2){
				 
			 }
		 }//finally end
		 
		 
	 }//updateMember() end-------------------------------------
	
	 
	 //-----------------
	 //회원탈퇴
	 //-----------------
	 public int deleteMember(String id,String passwd)throws Exception{
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 PreparedStatement pstmt2=null;
		 ResultSet rs=null;
		 
		 String dbPasswd="";
		 int x=-1;
		 
		 try{
			 con=getCon();//커넥션 얻기
			 pstmt=con.prepareStatement("select passwd from member where id=?");
			 pstmt.setString(1, id);//? 값 채우고
			 rs=pstmt.executeQuery();//쿼리실행
			 
			 if(rs.next()){
				 dbPasswd=rs.getString("passwd");
				 if(passwd.equals(dbPasswd)){//암호가 일치하면 삭제
					 pstmt2=con.prepareStatement("delete from member where id=?");
					 pstmt2.setString(1, id);
					 pstmt2.executeUpdate();//쿼리실행
					 x=1;//회원 탈퇴
				 }else{
					 x=-1;//비밀번호 틀림
				 }//else end
				 
			 }else{
				 x=0;//존재하지 않을때
			 }//else end
			 
			 
		 }catch(Exception ex1){
			 System.out.println("deleteMember 예외 : "+ex1);
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

	 
	 //-----------------------------------관리자------------------------------------
	 //------------
	 //회원 전체목록 리스트 출력
	 //-------------
	 public ArrayList<MemberDTO> getMemberAll(){
		 ArrayList<MemberDTO> list=new ArrayList<MemberDTO>();
		 
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 String sql="";
		 
		 try{
			 con=getCon();//커넥션 얻기
			 pstmt=con.prepareStatement("select * from member");
			 rs=pstmt.executeQuery();//쿼리실행
			 
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
			 System.out.println("getMemberAll() 예외 : "+ex1);
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
	 //관리자로그인(인증)
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
			 rs=pstmt.executeQuery();//쿼리 수행
			 
			 if(rs.next()){
				 dbPwd=rs.getString("admin_pwd");
				 
				 if(admin_pwd.equals(dbPwd)){
					 x=1;//인증 성공
				 }else{
					 x=0;//암호가 다름
				 }
			 }else{
				 x=-1;//해당 아이디 없음
			 }//else
		 }catch(Exception ex1){
			 System.out.println("userCheck() 예외 : "+ex1);
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
