package cn.edu.chzu.chzuoj.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import cn.edu.chzu.chzuoj.entity.User;

/**
 * 
 * @author dzj0821
 *
 */
public interface UserDao {
	/**
	 * 根据user表的id选取User
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM `users` WHERE `user_id` = #{id}")
	@Results(id = "userMap", value = {
			@Result(column = "user_id", property = "id", id = true),
			@Result(column = "accesstime", property = "accessTime"),
			@Result(column = "reg_time", property = "registerTime"),
			@Result(column = "nick", property = "name"),
			@Result(column = "school", property = "clazz") })
	public User selectUserById(@Param("id") String id);

	/**
	 * 新建用户
	 * @param uid 用户名
	 * @param name 姓名
	 * @param password 密码
	 * @param clazz 班级
	 * @param email 邮箱
	 * @param ip 登录ip
	 * @param defunct 是否禁用
	 */
	@Insert("INSERT INTO `users`(`user_id`,`email`,`ip`,`accesstime`,`password`,`reg_time`,`nick`,`school`,`defunct`) "
			+ "VALUES(#{uid}, #{email}, #{ip}, NOW(), #{password}, NOW(), #{name}, #{clazz}, #{defunct})")
	public void insertUser(@Param("uid") String uid, @Param("name") String name, @Param("password") String password, 
			@Param("clazz") String clazz, @Param("email") String email, @Param("ip") String ip, @Param("defunct") char defunct);
}
