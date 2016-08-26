package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SawonPooling {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DataSource ds;
	
	public SawonPooling() {
		try {
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria");
		} catch (Exception e) {
			System.out.println("db연결 실패"+e);
		}
	}
	
	public boolean loginData(SawonBean bean){
		boolean b = false;
		try {
			String sql = "select * from sawon";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(bean.getSawon_no().equals(rs.getString("sawon_no"))){
					if(bean.getSawon_name().equals(rs.getString("sawon_name"))){
						b = true;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("login data err: "+e);
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return b;
	}
	
	public ArrayList<GogekBean> tableData(String gogek_no){
		ArrayList<GogekBean> list = new ArrayList<>();
		try {
			String sql = "select * from gajok where f_gogek_no=?";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,gogek_no.trim());
			rs = pstmt.executeQuery();
			while(rs.next()){
				GogekBean bean = new GogekBean();
				bean.setF_bun(rs.getString("f_bun"));
				bean.setF_irum(rs.getString("f_irum"));
				bean.setF_tel(rs.getString("f_tel"));
				list.add(bean);
			}
		} catch (Exception e) {
			System.out.println("table data err: "+e);
		} finally{
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
	
	
}
