package cn.edu.chzu.chzuoj.service.impl;

import java.sql.Timestamp;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.dao.ContestDao;
import cn.edu.chzu.chzuoj.dao.LoginLogDao;
import cn.edu.chzu.chzuoj.dao.UserDao;
import cn.edu.chzu.chzuoj.entity.User;
import cn.edu.chzu.chzuoj.service.UserService;
import cn.edu.chzu.chzuoj.util.CharacterBoolean;
import cn.edu.chzu.chzuoj.util.MathUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private Config config;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ContestDao contestDao;
	@Autowired
	private LoginLogDao loginLogDao;
	private final static int OLD_PASSWORD_LENGTH = 32;

	@Override
	public User login(String uid, String password) throws IllegalArgumentException, IllegalStateException {
		User user = null;
		if (uid != null && password != null) {
			user = userDao.selectUserById(uid);
		}
		if (user == null || !checkPassword(password, user.getPassword())) {
			throw new IllegalArgumentException();
		}
		if (user.getDefunct() == CharacterBoolean.TRUE) {
			throw new IllegalStateException();
		}
		return user;
	}
	
	@Override
	public boolean examingLoginCheck(String uid, String ip) {
		Integer examId = config.getExamContestId();
		if (examId != null) {
			//如果在考试的情况
			Timestamp startTime = contestDao.selectContestStartTime(examId);
			String lastIp = loginLogDao.selectLastIpAfterTimeByUserId(uid, startTime);
			if (startTime != null && lastIp != null && !lastIp.equals(ip)) {
				//如果登录的ip和最后一次登录考试时的ip不同
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void log(String uid, String ip) {
		loginLogDao.insertLoginOkLog(uid, ip);
	}
	
	private boolean checkPassword(String postPassword, String savedPassword) {
		if (postPassword == null || savedPassword == null) {
			return false;
		}
		if (!isOldPassword(postPassword)) {
			//传来的是新版本的密码，转换成老版本的
			postPassword = MathUtil.md5(postPassword);
		}
		if (isOldPassword(savedPassword)) {
			//存储的是老版本密码的情况
			if (savedPassword.equals(postPassword)) {
				return true;
			}
			return false;
		}
		//存储的是新版本密码的情况
		byte[] bytes = Base64.decodeBase64(savedPassword);
		//盐值是最后4个字符
		String salt = new String(bytes, 20, 4);
		byte[] sha1 = MathUtil.sha1(postPassword + salt);
		for (int i = 0; i < sha1.length; i++) {
			if (bytes[i] != sha1[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断是否是老版本的密码，老版本密码是32位的十六进制数
	 * @return
	 */
	private boolean isOldPassword(String password) {
		int length = password.length();
		if (password == null || length != OLD_PASSWORD_LENGTH) {
			return false;
		}
		for(int i = 0; i < length; i++) {
			char ch = password.charAt(i);
			if ('0' <= ch && ch <= '9') {
				continue;
			}
			if ('a' <= ch && ch <= 'f') {
				continue;
			}
			if ('A' <= ch && ch <= 'F') {
				continue;
			}
			return false;
		}
		return true;
	}
}
