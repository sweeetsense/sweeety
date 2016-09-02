package pack.myBatis;

import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapConfig {
	// ibatis의 SqlMapClient와 같은 역할
	public static SqlSessionFactory sqlSession;

	static {
		String resource = "pack/myBatis/Configuration.xml";

		try {
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSession = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
			
			//Annotation 사용 시
			Class[] mapper = {
					pack.business.SqlMapperInter.class
			};
			
			for(Class m:mapper){
				//Mapper 등록 작업
				sqlSession.getConfiguration().addMapper(m);
			}
		} catch (Exception e) {
			System.out.println("SqlMapConfig 오류 : " + e);
		}
	}

	public static SqlSessionFactory getSqlSession() {
		return sqlSession;
	}
}