package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Exdb {
	private String buser_name;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public Exdb(){
		try {
			Class.forName("com.mysql.jdbc.Driver");   
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123");
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
			String sql = "select sawon_no, sawon_name, sawon_jik, sawon_gen, avg(sawon_pay) sawon_pay, count(sawon_no) sawon_count from sawon "
					+ "left outer join buser on buser_no = buser_num where buser_name=? group by sawon_no, sawon_name, sawon_jik, sawon_gen ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, buser_name);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ExdbDto dto = new ExdbDto();
				dto.setSawon_no(rs.getInt("sawon_no"));
				dto.setSawon_name(rs.getString("sawon_name"));
				dto.setSawon_jik(rs.getString("sawon_jik"));
				dto.setSawon_gen(rs.getString("sawon_gen"));
				dto.setSawon_count(rs.getInt("sawon_count"));
				dto.setSawon_pay(rs.getInt("sawon_pay"));
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("데이터 처리 실패: "+e);
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
