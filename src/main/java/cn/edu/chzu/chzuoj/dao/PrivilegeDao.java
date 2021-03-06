package cn.edu.chzu.chzuoj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * @author dzj0821
 *
 */
public interface PrivilegeDao {
	
	/**
	 * 获取用户拥有的权限
	 * @param uid
	 * @return
	 */
	@Select("SELECT `rightstr` FROM `privilege` WHERE `user_id` = #{uid}")
	public List<String> selectPermissionByUserId(@Param("uid") String uid);
}
