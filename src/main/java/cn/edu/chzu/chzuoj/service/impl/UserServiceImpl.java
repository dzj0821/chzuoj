package cn.edu.chzu.chzuoj.service.impl;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.dao.ContestDao;
import cn.edu.chzu.chzuoj.dao.LoginLogDao;
import cn.edu.chzu.chzuoj.dao.UserDao;
import cn.edu.chzu.chzuoj.entity.User;
import cn.edu.chzu.chzuoj.exception.ExecuteFailedException;
import cn.edu.chzu.chzuoj.service.UserService;
import cn.edu.chzu.chzuoj.util.CharacterBoolean;
import cn.edu.chzu.chzuoj.util.ConvertUtil;
import cn.edu.chzu.chzuoj.util.EncryptUtil;

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
	/**
	 * 旧密码长度
	 */
	private final static int OLD_PASSWORD_LENGTH = 32;
	/**
	 * 用户名id最小长度
	 */
	private final static int USER_ID_MIN_LENGTH = 3;
	/**
	 * 用户名id最大长度
	 */
	private final static int USER_ID_MAX_LENGTH = 20;
	/**
	 * 姓名最大长度
	 */
	private final static int NAME_MAX_LENGTH = 100;
	/**
	 * 密码最小长度
	 */
	private final static int PASSWORD_MIN_LENGTH = 6;
	/**
	 * 班级最大长度
	 */
	private final static int CLAZZ_MAX_LENGTH = 100;
	/**
	 * 邮箱最大长度
	 */
	private final static int EMAIL_MAX_LENGTH = 100;

	@Override
	public User login(String uid, String password, String ip) throws ExecuteFailedException {
		User user = null;
		user = userDao.selectUserById(uid);
		// 登录检查
		if (user == null || !checkPassword(password, user.getPassword())) {
			throw new ExecuteFailedException("username-or-password-wrong");
		}
		// 审核状态检查
		if (user.getDefunct() == CharacterBoolean.TRUE) {
			throw new ExecuteFailedException("user-reviewing");
		}
		// 考试状态检查
		examingLoginCheck(uid, ip);
		// 登录成功，记录日志
		loginLogDao.insertLog(uid, "login ok", ip);
		return user;
	}
	
	@Override
	public void register(String uid, String name, String password, String clazz, String email, String ip)
			throws ExecuteFailedException {
		if ("".equals(name)) {
			// 如果姓名是空的，默认为用户id
			name = uid;
		}
		//验证
		registerVerify(uid, name, password, clazz, email);
		//判断用户是否存在
		if (userDao.selectUserById(uid) != null) {
			throw new ExecuteFailedException("user-already-exist");
		}
		//加密密码
		password = generatePassword(password, false);
		//如果注册需要审核，则先禁用账号
		char defunct = config.getRegisterNeedReview() ? CharacterBoolean.TRUE : CharacterBoolean.FALSE;
		userDao.insertUser(uid, name, password, clazz, email, ip, defunct);
		loginLogDao.insertLog(uid, "no save", ip);
	}

	/**
	 * 考试状态登录检查
	 * 
	 * @param uid 用户名
	 * @param ip  当前登录ip
	 * @return 是否允许登录
	 * @throws ExecuteFailedException 如果在考试状态中且登录ip与上次登录ip不同
	 */
	private void examingLoginCheck(String uid, String ip) throws ExecuteFailedException {
		Integer examId = config.getExamContestId();
		if (examId != null) {
			// 如果在考试的情况
			Timestamp startTime = contestDao.selectContestStartTime(examId);
			String lastIp = loginLogDao.selectLastIpAfterTimeByUserId(uid, startTime);
			if (startTime != null && lastIp != null && !lastIp.equals(ip)) {
				// 如果登录的ip和最后一次登录考试时的ip不同
				throw new ExecuteFailedException("examing-login-from-different-ip");
			}
		}
	}

	private boolean checkPassword(String postPassword, String savedPassword) {
		if (postPassword == null || savedPassword == null) {
			return false;
		}
		if (!isOldPassword(postPassword)) {
			// 传来的是新版本的密码，转换成老版本的
			postPassword = EncryptUtil.md5(postPassword);
		}
		if (isOldPassword(savedPassword)) {
			// 存储的是老版本密码的情况
			if (savedPassword.equals(postPassword)) {
				return true;
			}
			return false;
		}
		// 存储的是新版本密码的情况
		byte[] bytes = Base64.getDecoder().decode(savedPassword);
		// 盐值是最后4个字符
		String salt = new String(bytes, 20, 4);
		byte[] sha1 = EncryptUtil.sha1(postPassword + salt);
		for (int i = 0; i < sha1.length; i++) {
			if (bytes[i] != sha1[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是老版本的密码，老版本密码是32位的十六进制数
	 * 
	 * @return
	 */
	private boolean isOldPassword(String password) {
		int length = password.length();
		if (password == null || length != OLD_PASSWORD_LENGTH) {
			return false;
		}
		for (int i = 0; i < length; i++) {
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
	
	/**
	 * 验证注册信息
	 * @param uid 用户id
	 * @param name 姓名
	 * @param password 密码
	 * @param clazz 班级
	 * @param email 邮箱
	 * @throws ExecuteFailedException 注册信息不合法，具体信息参见message
	 */
	private void registerVerify(String uid, String name, String password, String clazz, String email)
			throws ExecuteFailedException {
		// 用户名
		if (uid.length() < USER_ID_MIN_LENGTH || uid.length() > USER_ID_MAX_LENGTH) {
			// 长度验证
			throw new ExecuteFailedException("user-id-length-check-failed");
		}
		if (!isValidUserId(uid)) {
			// 字符验证
			throw new ExecuteFailedException("user-id-content-check-failed");
		}
		// 姓名
		if (name.length() > NAME_MAX_LENGTH) {
			throw new ExecuteFailedException("name-length-check-failed");
		}
		// 密码
		if (password.length() < PASSWORD_MIN_LENGTH) {
			throw new ExecuteFailedException("password-length-check-failed");
		}
		// 班级
		if (clazz.length() > CLAZZ_MAX_LENGTH) {
			throw new ExecuteFailedException("clazz-length-check-failed");
		}
		// 邮箱
		if (email.length() > EMAIL_MAX_LENGTH) {
			throw new ExecuteFailedException("email-length-check-failed");
		}
	}

	/**
	 * 是否是合法用户名，如果uid中的字符都是字母或数字或下划线（第一个字符允许是*），则返回true，否则返回false
	 * 
	 * @param uid 用户名
	 * @return
	 */
	private boolean isValidUserId(String uid) {
		for (int i = 0; i < uid.length(); i++) {
			char c = uid.charAt(i);
			//是否是数字
			boolean digit = c >= '0' && c <= '9';
			//是否是字母
			boolean letter = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
			//是否是合法的其他字符
			boolean other = (c == '_') || (i == 0 && c == '*');
			if (!digit && !letter && !other) {
				//如果都不是
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 根据明文password生成加密后的password
	 * @param password 明文password
	 * @param md5 参数password是否已经经过md5加密
	 * @return 加密后的password
	 */
	private String generatePassword(String password, boolean md5) {
		if (!md5) {
			//先对密码md5
			password = EncryptUtil.md5(password);
		}
		//获取一个随机的4位字符（小写字母或数字）作为盐值
		String salt = ConvertUtil.bytesToHexString(EncryptUtil.sha1(UUID.randomUUID().toString()), false).substring(0, 4);
		//二次加密防止彩虹表攻击
		byte[] first = EncryptUtil.sha1(password + salt);
		byte[] second = salt.getBytes();
		//把加密后的密码与盐值合并
		byte[] bytes = new byte[first.length + second.length];
		for (int i = 0; i < bytes.length; i++) {
			if (i < first.length) {
				bytes[i] = first[i];
			} else {
				bytes[i] = second[i - first.length];
			}
		}
		//最后Base64编码
		password = Base64.getEncoder().encodeToString(bytes);
		return password;
	}
}
