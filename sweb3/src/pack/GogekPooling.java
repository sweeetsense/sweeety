package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GogekPooling {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DataSource ds;
	
	public GogekPooling() {
		try {
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria");
		} catch (Exception e) {
			System.out.println("db연결 실패"+e);
		}
	}
	
	public boolean loginData(GogekBean bean){
		boolean b = false;
		try {
			String sql = "select * from gogek";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(bean.getGogek_no().equals(rs.getString("gogek_no"))){
					if(bean.getGogek_name().equals(rs.getString("gogek_name"))){
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
	
	public boolean insertData(GogekBean bean){
		boolean b = false;
		String sql = "";
		try {
			sql = "select max(f_bun) max from gajok";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int maxbun = 0;
			if(rs.next()){
				maxbun = rs.getInt("max");
			}
			maxbun++;
			
			sql = "insert into gajok values (?,?,?,?)";
			pstmt.close();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, maxbun);
			pstmt.setString(2, bean.getF_irum());
			pstmt.setString(3, bean.getF_tel());
			pstmt.setString(4, bean.getGogek_no());
			int re = pstmt.executeUpdate();
			if(re == 1)		b = true;
		} catch (Exception e) {
			System.out.println("insert data err: "+e);
		} finally{
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
	
	public GogekBean updatevalue(GogekBean bean){
		String sql="";
		try {
			sql = "select * from gajok where f_bun=?";
			System.out.println(bean.getGogek_no());
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getF_bun());
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString("f_gogek_no").equals(bean.getGogek_no())){
					bean.setF_irum(rs.getString("f_irum"));
					bean.setF_tel(rs.getString("f_tel"));
				}else{
					bean.setF_irum("없음");
					bean.setF_tel("없음");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return bean;
	}
	
	public boolean updateData(GogekBean bean){
		boolean b = false;
		String sql = "";
		String gogek_no = bean.getGogek_no();
		System.out.println(bean.getGogek_no()+bean.getF_bun());
		try {
			sql = "update gajok set f_irum=?, f_tel=? where f_bun=? and f_gogek_no=?";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getF_irum());
			pstmt.setString(2, bean.getF_tel());
			pstmt.setString(3, bean.getF_bun());
			pstmt.setString(4, gogek_no);
			int re = pstmt.executeUpdate();
			if(re == 1){
				b = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
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
	
	public boolean deleteData(GogekBean bean){
		boolean b = false;
		String sql = "";
		String gogek_no = bean.getGogek_no();
		String f_bun = bean.getF_bun();
		//System.out.println(gogek_no+f_bun);
		try {
			sql = "delete from gajok where f_bun=? and f_gogek_no=?";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getF_bun());
			pstmt.setString(2, bean.getGogek_no());
			int re = pstmt.executeUpdate();
			if(re == 1){
				b = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
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
}
