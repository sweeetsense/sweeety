package sawon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
			Context context = new InitialContext();	//javax.naming
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria"); //키워드 java:comp/env/ 입력 후 context.xml에 입력해놓은 name을 써준다
		} catch (Exception e) {
			System.out.println("db 연결 err : " + e);
		}
	}
	
}
