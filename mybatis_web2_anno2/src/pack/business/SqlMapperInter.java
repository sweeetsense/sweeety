package pack.business;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SqlMapperInter {	//메소드에 맵핑
	@Select("select * from membertab")
	public List<DataDTO> selectDataAll();
	
	@Select("select * from membertab where id=#(id)")
	public DataDTO selectDataById(String id);
	
	@Insert("insert into membertab values(#{id},#{name},#{password},now())")
	public int insertData(DataDTO dto);
	
	@Update("update membertab set name=#{name} where id=#{id}")
	public int updateData(DataDTO dto);

	@Delete("delete from membertab where id=#{id}")
	public int deleteData(String id);
	

}
