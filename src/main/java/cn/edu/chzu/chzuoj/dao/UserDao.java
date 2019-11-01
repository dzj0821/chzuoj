package cn.edu.chzu.chzuoj.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import cn.edu.chzu.chzuoj.pojo.User;

/**
 * 
 * @author dzj0821
 *
 */
public interface UserDao {
	/**
	 * 根据user表的id选取User
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM `users` WHERE `user_id` = #{id}")
	@Results(id = "userMap", value = {
			@Result(column = "user_id", property = "id", id = true),
			@Result(column = "accesstime", property = "accessTime"),
			@Result(column = "reg_time", property = "registerTime")
	})
	public User selectUserById(@Param("id") String id);
}
