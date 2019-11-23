package cn.edu.chzu.chzuoj.entity;

import java.sql.Timestamp;

/**
 * 
 * @author dzj0821
 *
 */
public class LoginLog {
	private String userId;
	/**
	 * 对应数据库字段password，存储登录时信息（注册时为no save，登录为login ok）
	 */
	private String message;
	private String ip;
	private Timestamp time;

	@Override
	public String toString() {
		return "LoginLog [userId=" + userId + ", message=" + message + ", ip=" + ip + ", time=" + time + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
}
