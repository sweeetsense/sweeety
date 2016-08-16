package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import project.Etc_Commons;

public class UrlTest_Read {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prpt = new Properties();
	private ArrayList<UrlTest_DTO> list = new ArrayList<>();

	public UrlTest_Read() {
		setDto();
		while (true) {
			Thread t = new Thread(new T_read());
			try {
				t.start();
				t.sleep(30000); // m 단위 계산 = 60*1000*m
			} catch (Exception e) {
				System.out.println("Thread Error : " + e);
			}
		}
	}

	private void DB_conn() { // DB연동
		try {
			prpt.load(new FileInputStream(Etc_Commons.DB_PROPERTIES));
			Class.forName(prpt.getProperty("driver"));
			conn = DriverManager.getConnection(prpt.getProperty("url"), prpt.getProperty("user"),
					prpt.getProperty("passwd"));
		} catch (Exception e) {
			System.out.println("DB_conn Err : " + e);
		}
	}

	private void setDto() {
		DB_conn();
		try {
			String sql = "select s_code, s_id, s_url from b_vstock";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UrlTest_DTO dto = new UrlTest_DTO();
				dto.setCode(rs.getString("s_code"));
				dto.setId(rs.getString("s_id"));
				dto.setUrl(rs.getString("s_url"));
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("setDto select Err : " + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				System.out.println("setDto close Err : " + e2);
			}
		}
	}

	class T_read implements Runnable {
		@Override
		public void run() {
			for (UrlTest_DTO dto : list) {
				String address = "https://www.google.com/finance?q=" + dto.getUrl();
				try {
					URL url = new URL(address);
					InputStream in = url.openStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(in));

					String line = "";
					Float price = null;
					while ((line = br.readLine()) != null) {
						// 주가 얻기
						if (line.contains(dto.getId())) {
							line = line.replace(dto.getId(), "");
							line = line.replace("</span>", "");							
							price = Float.valueOf(line.trim().replace(",", ""));  //,제거 후 실수형으로 데이터 저장
							break;
						}
					}
					// DB연동 및 b_data 테이블에 자료 insert
					DB_conn();
					try {
						// 현재시간 계산
						Date date = new Date();
						SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sTime = simple.format(date);
						// sql 실행
						String sql = "insert into b_vdata values (?,?,?)";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, dto.getCode());
						pstmt.setFloat(2, price);
						pstmt.setString(3, sTime);
						System.out.println(dto.getCode() + ", " + price + ", " + sTime);
						pstmt.executeUpdate();
					} catch (SQLException e1) {
						System.out.println("run insert Err : " + e1);
					} finally {
						try {
							if (rs != null)
								rs.close();
							if (pstmt != null)
								pstmt.close();
							if (conn != null)
								conn.close();
						} catch (Exception e2) {
							System.out.println("setDto close Err : " + e2);
						}
					}
					br.close();
					in.close();
				} catch (Exception e) {
					System.out.println("URL read Error : " + e);
				}
			}
		}
	}

	public static void main(String[] args) {
		new UrlTest_Read();
	}
}