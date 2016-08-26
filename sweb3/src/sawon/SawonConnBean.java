package sawon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class SawonConnBean {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DataSource ds;
	private int no;
	private String name;
	
	
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SawonConnBean() {
		try {
			Context context = new InitialContext();	//javax.naming
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria"); //키워드 java:comp/env/ 입력 후 context.xml에 입력해놓은 name을 써준다
		} catch (Exception e) {
			System.out.println("db 연결 error : " + e);
		}
	}
	
	public ArrayList<SawonDTO> getData(){
		ArrayList<SawonDTO> list = new ArrayList<>();
		
		try{
			conn = ds.getConnection();
			String sql = "select * from gogek";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				SawonDTO dto = new SawonDTO();
				dto.setNo(rs.getInt("gogek_no"));
				dto.setName(rs.getString("gogek_name"));
				dto.setTel(rs.getString("gogek_tel"));
				dto.setJumin(rs.getString("gogek_jumin"));
				list.add(dto);
			}
		}catch (Exception e){
			System.out.println("data err : "+e);
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return list;
	}
	
	public void login(String no, String name){
		String sql = "select * from sawon where sawon_no=? and sawon_name=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if(rs.next()){
			  setNo(rs.getInt("sawon_no"));
			  setName(rs.getString("sawon_name"));
			  System.out.println(getNo()+" "+getName());
			}
			
		} catch (Exception e) {
			System.out.println("e"+e);
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close(); // 연결이 끝나면 끊음
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	
	}
	
	public ArrayList<SawonDTO> getMyData(String no){
		ArrayList<SawonDTO> list = new ArrayList<>();
		
		try{
			conn = ds.getConnection();
			String sql = "select * from gogek where gogek_damsano=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				SawonDTO dto = new SawonDTO();
				dto.setNo(rs.getInt("gogek_no"));
				dto.setName(rs.getString("gogek_name"));
				dto.setTel(rs.getString("gogek_tel"));
				dto.setJumin(rs.getString("gogek_jumin"));
				list.add(dto);
			}
		}catch (Exception e2){
			System.out.println("data erar : "+e2);
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return list;
	}
	

}
