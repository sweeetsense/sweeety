package pack.business;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import pack.mybatis.SqlMapConfig;

public class ProcessDAO {
	private static ProcessDAO dao = new ProcessDAO();
	public static ProcessDAO getInstance(){
		return dao;
	}
	private SqlSessionFactory factory = SqlMapConfig.getSqlSession();
	
	public List selectDataAll() throws SQLException{
		SqlSession sqlSession = factory.openSession();
		List list = sqlSession.selectList("selectDataAll"); //DataMapper 의 ID를 읽기
		sqlSession.close();
		return list;
	}
	
	public DataDTO selectDataPart(String arg) throws SQLException{
		SqlSession sqlSession = factory.openSession();
		DataDTO dto = sqlSession.selectOne("selectDataById", arg);
		sqlSession.close();
		return dto;
	}
	
	public void insertData(DataDTO dto) throws SQLException{
		SqlSession sqlSession = factory.openSession();  //수동 (Commit 필요)
		//SqlSession sqlSession = factory.openSession(true);  //auto commit
		int re = sqlSession.insert("insertData", dto);
		//System.out.println("re : " + re);
		if(re > 0){
			sqlSession.commit();  //수동 커밋
		}
		sqlSession.close();
	}
	
	public void updateData(DataDTO dto) throws SQLException{
		SqlSession sqlSession = factory.openSession();  //수동 (Commit 필요)
		sqlSession.insert("updateData", dto);
		sqlSession.commit();  //수동 커밋
		sqlSession.close();
	}
	
	public boolean deleteData(int arg){
		SqlSession sqlSession = factory.openSession();
		boolean b = false;
		try {
			int cou = sqlSession.delete("deleteData", arg);
			if(cou > 0) b = true;
			sqlSession.commit();
		} catch (Exception e) {
			System.out.println("deleteData err : " + e);
			sqlSession.rollback();
		} finally {
			if(sqlSession != null) sqlSession.close();			
		}
		return b;
	}
}
