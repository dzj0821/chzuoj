package cn.edu.chzu.chzuoj.service;

import java.util.List;

import cn.edu.chzu.chzuoj.entity.LoginLog;

/**
 * 
 * @author dzj0821
 *
 */
public interface LoginLogService {
	/**
	 * 获取用户的最近10条登录日志
	 * @param uid
	 * @return
	 */
	public List<LoginLog> getLoginLogs(String uid);
}
