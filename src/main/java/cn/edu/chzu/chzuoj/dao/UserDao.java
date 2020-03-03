package cn.edu.chzu.chzuoj.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.edu.chzu.chzuoj.entity.User;

/**
 * 操作users表的类，实体类为User
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
			@Result(column = "accesstime", property = "lastLoginTime"),
			@Result(column = "volume", property = "problemListRememberPage"),
			@Result(column = "reg_time", property = "registerTime"),
			@Result(column = "nick", property = "name"),
			@Result(column = "school", property = "clazz")
	})
	public User selectUserById(@Param("id") String id);

	/**
	 * 新建用户
	 * 
	 * @param id      用户id
	 * @param name     姓名
	 * @param password 密码
	 * @param clazz    班级
	 * @param email    邮箱
	 * @param ip       登录ip
	 * @param defunct  是否禁用
	 */
	@Insert("INSERT INTO `users`(`user_id`,`email`,`ip`,`accesstime`,`password`,`reg_time`,`nick`,`school`,`defunct`) "
			+ "VALUES(#{id}, #{email}, #{ip}, NOW(), #{password}, NOW(), #{name}, #{clazz}, #{defunct})")
	public void insertUser(@Param("id") String id, @Param("name") String name, @Param("password") String password,
			@Param("clazz") String clazz, @Param("email") String email, @Param("ip") String ip,
			@Param("defunct") char defunct);

	/**
	 * 更新用户信息
	 * @param id 需要更新的用户id
	 * @param password 新密码
	 * @param name 新姓名
	 * @param clazz 新班级
	 * @param email 新邮箱
	 */
	@Update("UPDATE `users` SET `password` = #{password}, `nick` = #{name}, "
			+ "`school` = #{clazz}, `email` = #{email} WHERE `user_id` = #{id}")
	public void updateUserById(@Param("id") String id, @Param("password") String password, @Param("name") String name,
			@Param("clazz") String clazz, @Param("email") String email);
	
	/**
	 * 更新对应用户id的记住页码的页数
	 * @param id
	 * @param page 要记住的页码
	 */
	@Update("UPDATE `users` SET `volume` = #{page} WHERE `user_id` = #{id}")
	public void updateUserProblemListRememberPageById(@Param("id") String id, @Param("page") int page);
	
	/**
	 * 更新用户提交数
	 * @param id 用户id
	 * @param solved 解决题目数
	 * @param submit 提交数
	 */
	@Update("UPDATE `users` SET `solved` = #{solved}, `submit` = #{submit} WHERE `user_id` = #{id}")
	public void updateUserSolvedAndSubmitById(@Param("id") String id, @Param("solved") int solved, @Param("submit") int submit);
	
	/**
	 * 更新用户的最后访问时间
	 * @param id 需要更新的用户id
	 */
	@Update("UPDATE `users` SET `accesstime` = NOW() WHERE `user_id` = #{id}")
	public void updateAccessTime(@Param("id") String id);
	
	/**
	 * 获得用户中解答题目数超过solved的人数
	 * @param solved 需要获取排名的用户的解答题目数
	 * @return 用户排名
	 */
	@Select("SELECT COUNT(*) FROM `users` WHERE `solved` > #{solved}")
	public int selectRankBySolved(@Param("solved") int solved);
}
