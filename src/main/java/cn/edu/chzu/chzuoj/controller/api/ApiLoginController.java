package cn.edu.chzu.chzuoj.controller.api;

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.controller.BaseController;
import cn.edu.chzu.chzuoj.dao.ContestDao;
import cn.edu.chzu.chzuoj.dao.LoginLogDao;
import cn.edu.chzu.chzuoj.dao.PrivilegeDao;
import cn.edu.chzu.chzuoj.dao.UserDao;
import cn.edu.chzu.chzuoj.pojo.User;
import cn.edu.chzu.chzuoj.util.CharacterBoolean;
import cn.edu.chzu.chzuoj.util.LocaleUtil;
import cn.edu.chzu.chzuoj.util.MathUtil;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
public class ApiLoginController extends BaseController {
	@Autowired
	private Config config;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ContestDao contestDao;
	@Autowired
	private LoginLogDao loginLogDao;
	@Autowired
	private PrivilegeDao privilegeDao;
	private final static int OLD_PASSWORD_LENGTH = 32;
	private final static String LOCAL_IP = "0:0:0:0:0:0:0:1";
	
	/**
	 * 
	 * @param params
	 * @return OK时返回back
	 * back: 如果OJ是need-login状态返回false代表客户端跳转到主页，否则返回true代表客户端返回到上一页面
	 */
	@PostMapping("/api/Login")
	public ResponseEntity<Object> login(@RequestParam Map<String, String> params) {
		HttpSession session = request.getSession();
		
		if (config.getVcode()) {
			//如果开启了验证码，开始验证
			String rightVcode = (String) session.getAttribute(SessionAttrNameUtil.getVcode());
			String postVcode = params.get("vcode");
			if (rightVcode == null || postVcode == null || !rightVcode.equals(postVcode.trim())) {
				return new ResponseEntity<Object>(LocaleUtil.getMessage("verify-code-wrong"), HttpStatus.FORBIDDEN);
			}
		}
		
		Enumeration<String> keys = session.getAttributeNames();
		//移除所有session属性，主要是移除验证码防止验证码重用攻击
		while (keys.hasMoreElements()) {
			session.removeAttribute(keys.nextElement());
		}
		
		String uid = params.get("uid");
		String password = params.get("password");
		User user = null;
		//为null时不去查询
		if (uid != null || password != null) {
			user = userDao.selectUserById(uid);
		}
		
		if (user == null || !checkPassword(password, user.getPassword())) {
			//用户不存在或密码错误的情况
			return new ResponseEntity<Object>(LocaleUtil.getMessage("username-or-password-wrong"), HttpStatus.FORBIDDEN);
		}
		
		if (user.getDefunct() == CharacterBoolean.TRUE) {
			//账号在审核状态的情况
			return new ResponseEntity<Object>(LocaleUtil.getMessage("user-reviewing"), HttpStatus.FORBIDDEN);
		}
		//不使用用户输入，保险
		uid = user.getId();
		//获取用户ip
		String ip = request.getRemoteAddr();
		String xForwardedFor = request.getHeader("X-Forwarded-For");
		if (xForwardedFor != null && !"".equals(xForwardedFor)) {
			//如果是通过代理转发的，获取真实ip
			String[] split = xForwardedFor.split(",", 2);
			if (split.length > 0) {
				//php中进行了html转义，不知道为什么
				ip = HtmlUtils.htmlEscape(split[0], "UTF-8");
			}
		}
		if (LOCAL_IP.equals(ip)) {
			//如果是本地ip，转换成php本地ip的格式，防止不统一
			ip = "::1";
		}
		
		Integer examId = config.getExamContestId();
		if (examId != null) {
			//如果在考试的情况
			Timestamp startTime = contestDao.selectContestStartTime(examId);
			String lastIp = loginLogDao.selectLastIpAfterTimeByUserId(uid, startTime);
			if (startTime != null && lastIp != null && !lastIp.equals(ip)) {
				//如果登录的ip和最后一次登录考试时的ip不同
				return new ResponseEntity<Object>(LocaleUtil.getMessage("examing-login-from-different-ip"), HttpStatus.FORBIDDEN);
			}
		}
		//登录成功
		loginLogDao.insertLoginOkLog(uid, ip);
		session.setAttribute(SessionAttrNameUtil.getUserId(), uid);
		//往session中添加权限
		List<String> permissions = privilegeDao.selectPermissionByUserId(uid);
		if (permissions != null) {
			for (String string : permissions) {
				session.setAttribute(SessionAttrNameUtil.getSessionName(string), uid);
			}
		}
		//跳转
		Map<String, Object> map = new HashMap<String, Object>(1);
		if (config.getNeedLogin()) {
			map.put("back", false);
		} else {
			map.put("back", true);
		}
		return new ResponseEntity<Object>(map, HttpStatus.OK);
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
