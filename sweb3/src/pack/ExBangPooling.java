package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ExBangPooling {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DataSource ds;
	
	public ExBangPooling() {
		try {
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria");
		} catch (Exception e) {
			System.out.println("연결 실패: "+e);
		}
	}
	public ArrayList<ExBangBean> getData(){
		ArrayList<ExBangBean> list = new ArrayList<>();
		try {
			String sql = "select * from guest";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ExBangBean bean = new ExBangBean();
				bean.setCode(rs.getInt("code"));
				bean.setName(rs.getString("name"));
				bean.setSubject(rs.getString("subject"));
				bean.setContent(rs.getString("content"));
				list.add(bean);
			}
		} catch (Exception e) {
			System.out.println("getData err: "+e);
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
	public boolean insertData(ExBangBean bean){
		boolean b = false;
		String sql = "";
		try {
			sql = "select max(code) from guest";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int maxcode = 0;
			if(rs.next()){
				maxcode = rs.getInt(1);
			}
			maxcode++;
			
			sql = "insert into guest values(?,?,?,?)";
			pstmt.close();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, maxcode);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getContent());
			int re = pstmt.executeUpdate();
			if(re == 1)		b = true;
		} catch (Exception e) {
			System.out.println("insertData err: "+e);
		}finally{
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return b;
	}
	public deleteData(ExBangBean bean){
		boolean b = false;
		String sql = "";
		try {
			sql = "delete from guest where code = ?";
			
		} catch (Exception e) {
			System.out.println("delete err: "+e);
		}
	}
}
