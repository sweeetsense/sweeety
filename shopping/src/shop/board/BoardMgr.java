package shop.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardMgr {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DataSource ds;
	
	//paging
	private int tot;		//전체 레코드 수
	private int plist = 8;	//페이지당 출력 행 수
	private int pageSu;		//전체 페이지 수
	
	public BoardMgr() {
		try {
			Context context = new InitialContext();		//
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria");
		} catch (Exception e) {
			System.out.println("db 연결 실패"+e);
		}
	}
	
	public int currentGetNum(){			//최대 번호 구하기
		String sql = "select max(num) from board";
		int cnt = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) cnt = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("currentGetNum err: "+e);;
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return cnt;
	}
	public void saveData(BoardBean bean){
		String sql = "insert into board values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bean.getNum());
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getPass());
			pstmt.setString(4, bean.getMail());
			pstmt.setString(5, bean.getTitle());
			pstmt.setString(6, bean.getCont());
			pstmt.setString(7, bean.getBip());
			pstmt.setString(8, bean.getBdate());
			pstmt.setInt(9, 0);		//readcnt
			pstmt.setInt(10, bean.getGnum());
			pstmt.setInt(11, 0);		//onum
			pstmt.setInt(12, 0);	//nested
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("currentGetNum err: "+e);;
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	public void totalList(){		//전체 레코드 건수
		String sql = "select count(*) from board";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			tot = rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public int getPageSu(){		//총 페이지 수 구하기
		pageSu = tot/plist;
		if(tot%plist > 0){		//자투리 페이지 처리
			pageSu++;
		}
		return pageSu;
	}
	
	public ArrayList<BoardDto> getDataAll(int page, String stype, String sword){
		//System.out.println(sword);
		ArrayList<BoardDto> list = new ArrayList<>();
		String sql = "select * from board ";
		try {
			conn = ds.getConnection();
			if(sword == null){
				sql += " order by gnum desc, onum asc";
				pstmt = conn.prepareStatement(sql);
			}else{
				sql += " where "+stype+" like ?";
				sql += " order by gnum desc, onum asc";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+sword+"%");
			}
			rs = pstmt.executeQuery();
			
			for(int i=0;i<(page-1)*plist;i++){
				rs.next();		//레코드 포인터 위치: 0,7,15...
			}

			int k=0;
			while(rs.next() && k<plist){
				BoardDto dto = new BoardDto();
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setBdate(rs.getString("bdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				dto.setNested(rs.getInt("nested"));
				list.add(dto);
				k++;
			}
		} catch (Exception e) {
			// TODO: handle exception
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
	
	public void updateReadcnt(String num){		//글 내용 보기 하면 조회수 증가
		String sql = "update board set readcnt=readcnt+1 where num=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("updateReadcnt err"+e);
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public BoardDto getData(String num){
		String sql = "select * from board where num=?";
		BoardDto dto = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				dto = new BoardDto();
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setMail(rs.getString("mail"));
				dto.setTitle(rs.getString("title"));
				dto.setCont(rs.getString("cont"));
				dto.setBip(rs.getString("bip"));
				dto.setBdate(rs.getString("bdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
			}
		} catch (Exception e) {
			System.out.println("getData err"+e);
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return dto;
	}
	
	public BoardDto getReplyData(String num){		//댓글용
		String sql = "select * from board where num=?";
		BoardDto dto = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				dto = new BoardDto();
				dto.setTitle(rs.getString("title"));
				dto.setGnum(rs.getInt("gnum"));
				dto.setOnum(rs.getInt("onum"));
				dto.setNested(rs.getInt("nested"));
			}
		} catch (Exception e) {
			System.out.println("getReplyData err"+e);
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return dto;
	}
	
	public void updateOnum(int gnum, int onum){		//댓글용 -  onum 갱신
		//같은 그룹에 레코드는 모두 작업에 참여 - 같은 그룹의 onum 값 변경
		//댓글의 onum은 이미 db에 있는 onum보다 크거나 같은 값을 변경
		String sql = "update board set onum=onum+1 where onum >= ? and gnum=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, onum);
			pstmt.setInt(2, gnum);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("updateOnum err"+e);
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public void saveReply(BoardBean bean){
		String sql = "insert into board values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bean.getNum());
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getPass());
			pstmt.setString(4, bean.getMail());
			pstmt.setString(5, bean.getTitle());
			pstmt.setString(6, bean.getCont());
			pstmt.setString(7, bean.getBip());
			pstmt.setString(8, bean.getBdate());
			pstmt.setInt(9, 0);		//readcnt
			pstmt.setInt(10, bean.getGnum());
			pstmt.setInt(11, bean.getOnum());		//onum
			pstmt.setInt(12, bean.getNested());	//nested
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("saveReply err: "+e);;
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public boolean checkPass(int num, String new_pass){
		boolean b = false;
		String sql = "select pass from board where num=?";
		System.out.println(num);
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(new_pass.equals(rs.getString("pass"))){
					b = true;
				}
			}
			
		} catch (Exception e) {
			System.out.println("checkPass err: "+e);;
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
	
	public void saveEdit(BoardBean bean){
		String sql = "update board set name=?,mail=?,title=?,cont=? where num=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getMail());
			pstmt.setString(3, bean.getTitle());
			pstmt.setString(4, bean.getCont());
			pstmt.setInt(5, bean.getNum());
			pstmt.executeUpdate();
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			System.out.println("saveEdit err: "+e);;
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public void delData(String num){
		String sql = "delete from board where num=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("delData err: "+e);;
		} finally{
			try {
				if(rs!= null)		rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null)	conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}
