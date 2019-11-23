package cn.edu.chzu.chzuoj.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.chzu.chzuoj.annotation.NeedNotLogin;
import cn.edu.chzu.chzuoj.annotation.ContestEnable;
import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.controller.BaseController;
import cn.edu.chzu.chzuoj.exception.ExecuteFailedException;
import cn.edu.chzu.chzuoj.service.PermissionService;
import cn.edu.chzu.chzuoj.service.UserService;
import cn.edu.chzu.chzuoj.service.VcodeService;
import cn.edu.chzu.chzuoj.util.RequestUtil;
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
	 * 用户简介，用于获取导航栏用户登录状态
	 * 
	 * @return OK需要返回uid, shareCode, permission uid: 用户id shareCode: 此时是否能分享代码
	 *         permission: 是否具有进入管理页面的权限
	 */
	@ContestEnable
	@NeedNotLogin
	@GetMapping("/profile")
	public ResponseEntity<Map<String, Object>> profile() {
		Map<String, Object> result = new HashMap<String, Object>(3);
		HttpSession session = request.getSession();
		Object userId = session.getAttribute(SessionAttrNameUtil.getUserId());
		result.put("uid", userId);
		if (userId == null) {
			return json(HttpStatus.FORBIDDEN);
		}
		// shareCode条件：服务器开启分享代码且不在现场赛或考试状态
		result.put("shareCode",
				config.getShareCode() && config.getOnSiteContestId() == null && config.getExamContestId() == null);
		// permission条件：至少拥有以下4个管理权限中的一个
		boolean permission = false;
		String[] permissionNames = { SessionAttrNameUtil.getAdministrator(), SessionAttrNameUtil.getClassManager(),
				SessionAttrNameUtil.getContestCreator(), SessionAttrNameUtil.getProblemEditor() };
		for (String string : permissionNames) {
			if (session.getAttribute(string) != null) {
				permission = true;
				break;
			}
		}
		result.put("permission", permission);
		return json(HttpStatus.OK, result);
	}

	/**
	 * 登录接口
	 * 
	 * @param uid      用户名
	 * @param password 密码
	 * @param vcode    验证码
	 * @return OK时返回json对象，包含属性back back:
	 *         如果OJ是need-login状态返回false代表客户端跳转到主页，否则返回true代表客户端返回到上一页面
	 */
	@ContestEnable
	@NeedNotLogin
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam(required = true) String uid,
			@RequestParam(required = true) String password, String vcode) {
		HttpSession session = request.getSession();
		// 验证码验证
		String sessionVcode = (String) session.getAttribute(SessionAttrNameUtil.getVcode());
		if (!vcodeService.verify(sessionVcode, vcode)) {
			return json(HttpStatus.FORBIDDEN, "verify-code-wrong");
		}
		// 移除验证码，防止验证码重用攻击
		session.setAttribute(SessionAttrNameUtil.getVcode(), null);
		// 获取用户ip
		String ip = RequestUtil.getActuallyIp(request);
		// 验证用户登录
		try {
			userService.login(uid, password, ip);
		} catch (ExecuteFailedException e) {
			// 登录失败的情况
			return json(HttpStatus.FORBIDDEN, e.getKey(), e.getArgs());
		}
		// 登录成功
		session.setAttribute(SessionAttrNameUtil.getUserId(), uid);
		// 往session中添加权限
		List<String> permissions = permissionService.getPermissionStrings(uid);
		for (String string : permissions) {
			session.setAttribute(SessionAttrNameUtil.getSessionName(string), uid);
		}
		// 跳转，back参数为true代表浏览器往上一页面跳转，为false代表跳转到主页
		Map<String, Object> map = new HashMap<String, Object>(1);
		if (config.getNeedLogin()) {
			// 如果开启了需要登录访问OJ的选项，往上一页面跳转会导致浏览器读取缓存再此跳转回登录页面，所以要往主页跳转
			map.put("back", false);
		} else {
			map.put("back", true);
		}
		return json(HttpStatus.OK, map);
	}

	@ContestEnable
	@NeedNotLogin
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@RequestParam(required = true) String uid,
			@RequestParam(required = true) String name, @RequestParam(required = true) String password,
			@RequestParam(required = true) String clazz, @RequestParam(required = true) String email, String vcode) {
		if (!config.getOpenRegister()) {
			return json(HttpStatus.FORBIDDEN, "not-open-register");
		}
		HttpSession session = request.getSession();
		// 验证码验证
		String sessionVcode = (String) session.getAttribute(SessionAttrNameUtil.getVcode());
		// 清空验证码防止重用
		session.setAttribute(SessionAttrNameUtil.getVcode(), null);
		if (!vcodeService.verify(sessionVcode, vcode)) {
			return json(HttpStatus.FORBIDDEN, "verify-code-wrong");
		}
		// 获取用户ip
		String ip = RequestUtil.getActuallyIp(request);
		try {
			userService.register(uid, name, password, clazz, email, ip);
		} catch (ExecuteFailedException e) {
			return json(HttpStatus.FORBIDDEN, e.getKey(), e.getArgs());
		}
		if (!config.getRegisterNeedReview()) {
			// 如果不需要审核
			session.setAttribute(SessionAttrNameUtil.getUserId(), uid);
			// 往session中添加权限
			List<String> permissions = permissionService.getPermissionStrings(uid);
			for (String string : permissions) {
				session.setAttribute(SessionAttrNameUtil.getSessionName(string), uid);
			}
			return json(HttpStatus.OK, "register-success");
		}
		return json(HttpStatus.OK, "register-success-but-need-review");
	}

	@ContestEnable
	@PostMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout() {
		HttpSession session = request.getSession();
		if (session.getAttribute(SessionAttrNameUtil.getUserId()) == null) {
			return json(HttpStatus.FORBIDDEN, "current-not-login");
		}
		SessionUtil.removeAllAttributes(request.getSession());
		return json(HttpStatus.OK, "logout-success");
	}

	@PostMapping("/modify")
	public ResponseEntity<Map<String, Object>> modify(@RequestParam(required = true) String oldPassword,
			@RequestParam(required = true) String newPassword, @RequestParam(required = true) String name,
			@RequestParam(required = true) String clazz, @RequestParam(required = true) String email) {
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute(SessionAttrNameUtil.getUserId());
		if (uid == null) {
			return json(HttpStatus.FORBIDDEN, "current-not-login");
		}
		try {
			userService.modify(uid, oldPassword, newPassword, name, clazz, email);
		} catch (ExecuteFailedException e) {
			return json(HttpStatus.FORBIDDEN, e.getKey(), e.getArgs());
		}
		return json(HttpStatus.OK, "modify-success");
	}
}
