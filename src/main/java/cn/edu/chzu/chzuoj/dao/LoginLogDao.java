package cn.edu.chzu.chzuoj.dao;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * @author dzj0821
 *
 */
public interface LoginLogDao {
	/**
	 * 获取time时间后用户最后一次的登录ip
	 * @param userId
	 * @param time
	 * @return
	 */
	@Select("SELECT ip FROM loginlog WHERE user_id = #{userId} AND time > #{time} ORDER BY time DESC LIMIT 1")
	public String selectLastIpAfterTimeByUserId(@Param("userId") String userId, @Param("time") Timestamp time);
	
	/**
	 * 用户登录成功后，插入日志记录
	 * @param userId 登录的用户
	 * @param message 登录信息
	 * @param ip 登录ip
	 * @return
	 */
	@Insert("INSERT INTO `loginlog` VALUES(#{userId}, #{message}, #{ip}, NOW())")
	public int insertLog(@Param("userId") String userId, @Param("message") String message, @Param("ip") String ip);
}
