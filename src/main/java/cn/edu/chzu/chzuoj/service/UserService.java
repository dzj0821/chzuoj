package cn.edu.chzu.chzuoj.service;

import cn.edu.chzu.chzuoj.entity.User;
import cn.edu.chzu.chzuoj.exception.ExecuteFailedException;

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
	 * @param ip 用户登录的ip
	 * @return 登录成功的用户实例
	 * @throws ExecuteFailedException 当登录发生错误时抛出此异常，原因参见message
	 */
	public User login(String uid, String password, String ip) throws ExecuteFailedException;
	
	/**
	 * 注册
	 * @param uid 用户名
	 * @param name 姓名
	 * @param password 密码
	 * @param clazz 班级
	 * @param email 邮箱
	 * @param ip 注册ip
	 * @throws ExecuteFailedException 当注册发生错误时抛出此异常，原因参见message
	 */
	public void register(String uid, String name, String password, String clazz, String email, String ip) throws ExecuteFailedException;
}
