package cn.edu.chzu.chzuoj.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import cn.edu.chzu.chzuoj.entity.LoginLog;

/**
 * 
 * @author dzj0821
 *
 */
public interface LoginLogDao {
	/**
	 * 获取time时间后用户最后一次的登录ip
	 * @param uid
	 * @param time
	 * @return
	 */
	@Select("SELECT ip FROM loginlog WHERE user_id = #{uid} AND time > #{time} ORDER BY time DESC LIMIT 1")
	public String selectLastIpAfterTimeByUserId(@Param("uid") String uid, @Param("time") Timestamp time);
	
	/**
	 * 用户登录成功后，插入日志记录
	 * @param uid 登录的用户
	 * @param message 登录信息
	 * @param ip 登录ip
	 * @return
	 */
	@Insert("INSERT INTO `loginlog` VALUES(#{uid}, #{message}, #{ip}, NOW())")
	public int insertLog(@Param("uid") String uid, @Param("message") String message, @Param("ip") String ip);
	
	/**
	 * 选取用户的最近10条登录记录
	 * @param uid
	 * @return
	 */
	@Select("SELECT * FROM `loginlog` WHERE `user_id` = #{uid} ORDER BY `time` DESC LIMIT 0, 10")
	@Results(id = "loginLogMap", value = {
			@Result(column = "password", property = "message")
	})
	public List<LoginLog> selectLoginLogByUserId(@Param("uid") String uid);
}
