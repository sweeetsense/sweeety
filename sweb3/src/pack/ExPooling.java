package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ExPooling {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs, rs2;
	private DataSource ds;
	String buser_name;
	
	public ExPooling() {
		try {
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria");
		} catch (Exception e) {
			System.out.println("db연결 실패: "+e);
		}
	}
	
	public String getBuser_name() {
		return buser_name;
	}
	
	public void setBuser_name(String buser_name) {
		this.buser_name = buser_name;
	}
	
	public ArrayList<ExdbDto> getData(){
		ArrayList<ExdbDto> list = new ArrayList<>();
		try {
			String sql = "select * from sawon left outer join buser on buser_no = buser_num";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ExdbDto dto = new ExdbDto();
				dto.setSawon_no(rs.getInt("sawon_no"));
				dto.setSawon_name(rs.getString("sawon_name"));
				dto.setSawon_jik(rs.getString("sawon_jik"));
				dto.setBuser_name(rs.getString("buser_name"));
				dto.setNo(rs.getInt("buser_no"));
				dto.setBuser(rs.getString("buser_name"));
				dto.setTel(rs.getString("buser_tel"));
				dto.setLoc(rs.getString("buser_loc"));
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("getData err: "+e);;
		}finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return list;
	}
	/*public ArrayList<ExdbDto> getBuser(){
		
		ArrayList<ExdbDto> list = new ArrayList<>();
		int no=0;
		String buser="", tel="", loc="";
		try {
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria");
			String sql = "select * from buser where buser_name=?";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "총무부");
			rs = pstmt.executeQuery();
			rs.next();
			ExdbDto dto = new ExdbDto();
			dto.setNo(rs.getInt("buser_no"));
			dto.setBuser(rs.getString("buser_name"));
			dto.setTel(rs.getString("buser_tel"));
			dto.setLoc(rs.getString("buser_loc"));
			list.add(dto);
			System.out.println(dto.getNo()+" "+dto.getBuser()+" "+dto.getTel()+" "+dto.getLoc());
		} catch (Exception e) {
			System.out.println("getBuser err: "+e);;
		}finally{
			try {
				if(rs2!= null)		rs2.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return list;
	}*/
}
