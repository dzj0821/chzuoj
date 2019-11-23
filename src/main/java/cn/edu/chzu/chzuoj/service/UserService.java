package cn.edu.chzu.chzuoj.service;

import java.util.List;
import java.util.Map;

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
	 * 
	 * @param uid      用户名
	 * @param password 密码
	 * @param ip       用户登录的ip
	 * @return 登录成功的用户实例
	 * @throws ExecuteFailedException 当登录发生错误时抛出此异常，原因参见message
	 */
	public User login(String uid, String password, String ip) throws ExecuteFailedException;

	/**
	 * 注册
	 * 
	 * @param uid      用户名
	 * @param name     姓名
	 * @param password 密码
	 * @param clazz    班级
	 * @param email    邮箱
	 * @param ip       注册ip
	 * @throws ExecuteFailedException 当注册发生错误时抛出此异常，原因参见message
	 */
	public void register(String uid, String name, String password, String clazz, String email, String ip)
			throws ExecuteFailedException;

	/**
	 * 查询用户信息
	 * 
	 * @param uid 用户id
	 * @return 用户
	 */
	public User getUser(String uid);

	/**
	 * 修改用户信息
	 * @param uid 需要修改的用户id
	 * @param oldPassword 明文旧密码
	 * @param newPassword 明文新密码
	 * @param name 姓名
	 * @param clazz 班级
	 * @param email 邮箱
	 * @throws ExecuteFailedException 当旧密码错误或新信息不符合要求
	 */
	public void modify(String uid, String oldPassword, String newPassword, String name, String clazz, String email) throws ExecuteFailedException;
	
	/**
	 * 获取并用户的名次、解决题目数、总提交数信息
	 * @param uid
	 * @return map中solved为解决题目数，submit为总提交数，rank为名次
	 */
	public Map<String, Object> getInfo(String uid);
	
	/**
	 * 获取用户总提交结果与数量
	 * @param uid
	 * @return 每行一个map，map中type代表一种提交类型（JudgeResult的value），name代表提交结果，count代表这种类型提交的数量
	 */
	public List<Map<String, Object>> getSubmitResultCounts(String uid);
}
