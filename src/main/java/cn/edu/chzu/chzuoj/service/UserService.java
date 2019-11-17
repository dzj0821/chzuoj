package cn.edu.chzu.chzuoj.service;

import cn.edu.chzu.chzuoj.entity.User;

/**
 * 
 * @author dzj0821
 *
 */
public interface UserService {
	/**
	 * 登录
	 * @param uid 用户名
	 * @param password 密码
	 * @return 登录成功的用户实例
	 * @throws IllegalArgumentException 当用户名或密码错误时抛出此异常
	 * @throws IllegalStateException 当用户账户处于审核状态时抛出此异常
	 */
	public User login(String uid, String password) throws IllegalArgumentException, IllegalStateException;
	
	/**
	 * 考试状态登录检查，如果在考试状态中且登录ip与上次登录ip不同则返回false，否则返回true
	 * @param uid 用户名
	 * @param ip 当前登录ip
	 * @return 是否允许登录
	 */
	public boolean examingLoginCheck(String uid, String ip);
	
	/**
	 * 记录登录成功的日志
	 * @param uid 登录成功的用户名
	 * @param ip 登录ip
	 */
	public void log(String uid, String ip);
}
