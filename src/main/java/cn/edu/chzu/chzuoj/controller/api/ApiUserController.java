package cn.edu.chzu.chzuoj.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.chzu.chzuoj.annotation.NeedNotLogin;
import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.controller.BaseController;
import cn.edu.chzu.chzuoj.service.PermissionService;
import cn.edu.chzu.chzuoj.service.UserService;
import cn.edu.chzu.chzuoj.service.VcodeService;
import cn.edu.chzu.chzuoj.util.LocaleUtil;
import cn.edu.chzu.chzuoj.util.RequestUtil;
import cn.edu.chzu.chzuoj.util.ResponseUtil;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;
import cn.edu.chzu.chzuoj.util.SessionUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
@RequestMapping("/api/user")
public class ApiUserController extends BaseController {
	@Autowired
	private Config config;
	@Autowired
	private VcodeService vcodeService;
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 
	 * @param uid 用户名
	 * @param password 密码
	 * @param vcode 验证码
	 * @return OK时返回json对象，包含属性back
	 * back: 如果OJ是need-login状态返回false代表客户端跳转到主页，否则返回true代表客户端返回到上一页面
	 */
	@NeedNotLogin
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(String uid, String password, String vcode) {
		HttpSession session = request.getSession();
		//验证码验证
		String sessionVcode = (String) session.getAttribute(SessionAttrNameUtil.getVcode());
		if (!vcodeService.verify(sessionVcode, vcode)) {
			return ResponseUtil.json(HttpStatus.FORBIDDEN, LocaleUtil.getMessage("verify-code-wrong"), null);
		}
		//移除所有session属性，主要是移除验证码，防止验证码重用攻击
		SessionUtil.removeAllAttributes(session);
		//验证用户登录
		try {
			userService.login(uid, password);
		} catch (IllegalArgumentException e) {
			//用户名或密码错误的情况
			return ResponseUtil.json(HttpStatus.FORBIDDEN, LocaleUtil.getMessage("username-or-password-wrong"), null);
		} catch (IllegalStateException e) {
			//账号在审核状态的情况
			return ResponseUtil.json(HttpStatus.FORBIDDEN, LocaleUtil.getMessage("user-reviewing"), null);
		}
		//获取用户ip
		String ip = RequestUtil.getActuallyIp(request);
		//考试状态检查
		if (!userService.examingLoginCheck(uid, ip)) {
			return ResponseUtil.json(HttpStatus.FORBIDDEN, LocaleUtil.getMessage("examing-login-from-different-ip"), null);
		}
		//登录成功，记录日志
		userService.log(uid, ip);
		session.setAttribute(SessionAttrNameUtil.getUserId(), uid);
		//往session中添加权限
		List<String> permissions = permissionService.getPermissionStrings(uid);
		for (String string : permissions) {
			session.setAttribute(SessionAttrNameUtil.getSessionName(string), uid);
		}
		//跳转，back参数为true代表浏览器往上一页面跳转，为false代表跳转到主页
		Map<String, Object> map = new HashMap<String, Object>(1);
		if (config.getNeedLogin()) {
			//如果开启了需要登录访问OJ的选项，往上一页面跳转会导致浏览器读取缓存再此跳转回登录页面，所以要往主页跳转
			map.put("back", false);
		} else {
			map.put("back", true);
		}
		return ResponseUtil.json(HttpStatus.OK, null, map);
	}
}
