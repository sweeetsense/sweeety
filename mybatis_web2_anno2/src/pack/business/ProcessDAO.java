package pack.business;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import pack.myBatis.SqlMapConfig;

public class ProcessDAO {
	private SqlSessionFactory factory = SqlMapConfig.getSqlSession();
	
//	public List selectDataAll(){
	public List selectDataAll(Map<String, String> map){
		SqlSession sqlSession = factory.openSession();
		List list = null;
		try {
			//list = sqlSession.selectList("selectDataAll");
			//list = sqlSession.selectList("selectDataAll",map);
			SqlMapperInter inter = (SqlMapperInter)sqlSession.getMapper(SqlMapperInter.class);
			list = inter.selectDataAll();
		} catch (Exception e) {
			System.out.println("selectAll err "+e);
		}finally{
			if(sqlSession != null) sqlSession.close();
		}
		return list;
		
	}
	
	public DataDTO selectDataById(String id){
		SqlSession sqlSession = factory.openSession();
		DataDTO dto = null;
		try {
			SqlMapperInter inter = (SqlMapperInter)sqlSession.getMapper(SqlMapperInter.class);
			dto = inter.selectDataById(id);
		} catch (Exception e) {
			System.out.println("selectById err "+e);
		}finally{
			if(sqlSession != null) sqlSession.close();
		}
		return dto;
	}
	
	public boolean insertData(DataDTO dto){
		boolean b = false;
		SqlSession sqlSession = factory.openSession();
		try {
			SqlMapperInter inter = (SqlMapperInter)sqlSession.getMapper(SqlMapperInter.class);
			
			if(inter.insertData(dto)> 0) b=true;
			sqlSession.commit();
		} catch (Exception e) {
			System.out.println("insertData err "+e);
			sqlSession.rollback();
		}finally{
			if(sqlSession != null) sqlSession.close();
		}
		return b;
	}
	
	public boolean updateData(DataDTO dto){
		boolean b = false;
		SqlSession sqlSession = factory.openSession();
		try {
			//비밀번호 비교
			SqlMapperInter inter = (SqlMapperInter)sqlSession.getMapper(SqlMapperInter.class);
			DataDTO dto2 = inter.selectDataById(dto.getId());
			if(dto2.isCheckpassword(dto.getpassword()) == false){
				return b;
			}
			if(inter.updateData(dto) > 0) b=true;
			sqlSession.commit();
		} catch (Exception e) {
			System.out.println("updateData err "+e);
			sqlSession.rollback();
		}finally{
			if(sqlSession != null) sqlSession.close();
		}
		return b;
	}
	
	public boolean deleteData(String id){
		boolean b = false;
		SqlSession sqlSession = factory.openSession();
		try {
			SqlMapperInter inter = (SqlMapperInter)sqlSession.getMapper(SqlMapperInter.class);
			if(inter.deleteData(id) > 0) b=true;
			sqlSession.commit();
		} catch (Exception e) {
			System.out.println("deleteData err "+e);
			sqlSession.rollback();
		}finally{
			if(sqlSession != null) sqlSession.close();
		}
		return b;
	}
}
