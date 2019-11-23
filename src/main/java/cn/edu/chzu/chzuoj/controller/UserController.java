package cn.edu.chzu.chzuoj.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.annotation.NeedNotLogin;
import cn.edu.chzu.chzuoj.annotation.ContestEnable;
import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.entity.LoginLog;
import cn.edu.chzu.chzuoj.entity.User;
import cn.edu.chzu.chzuoj.service.LoginLogService;
import cn.edu.chzu.chzuoj.service.SolutionService;
import cn.edu.chzu.chzuoj.service.UserService;
import cn.edu.chzu.chzuoj.util.EncodeUtil;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private Config config;
	@Autowired
	private UserService userService;
	@Autowired
	private SolutionService solutionService;
	@Autowired
	private LoginLogService loginLogService;
	
	@ContestEnable
	@NeedNotLogin
	@GetMapping("/login")
	public ModelAndView login() {
		if (request.getSession().getAttribute(SessionAttrNameUtil.getUserId()) != null) {
			//如果已登录，提示需要先登出
			return message("please-logout-first", "/logout");
		}
		return new ModelAndView();
	}
	
	@ContestEnable
	@NeedNotLogin
	@GetMapping("/register")
	public ModelAndView register() {
		if (!config.getOpenRegister()) {
			return message("not-open-register");
		}
		return new ModelAndView();
	}
	
	@GetMapping("/modify")
	public ModelAndView modify() {
		String uid = (String) request.getSession().getAttribute(SessionAttrNameUtil.getUserId());
		if (uid == null) {
			//如果用户未登录
			return message("current-not-login");
		}
		User user = userService.getUser(uid);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("uid", uid);
		modelAndView.addObject("name", user.getName());
		modelAndView.addObject("clazz", user.getClazz());
		modelAndView.addObject("email", user.getEmail());
		return modelAndView;
	}
	
	@GetMapping("/info")
	public ModelAndView info(@RequestParam(required = true) String uid) {
		User user = userService.getUser(uid);
		if (user == null) {
			return message("no-such-user");
		}
		//获取解答题目数和总提交数
		Map<String, Object> infoMap = userService.getInfo(uid);
		List<LoginLog> loginLogs = null;
		if (request.getSession().getAttribute(SessionAttrNameUtil.getAdministrator()) != null) {
			//如果是管理员，需要显示最近10条用户登录记录
			loginLogs = loginLogService.getLoginLogs(uid);
		}
		//获取用户的提交结果情况与数量
		List<Map<String, Object>> resultCounts = userService.getSubmitResultCounts(uid);
		List<Map<String, Integer>> problems = solutionService.getProblemAcceptedCounts(uid);
		//获取用户提交情况的图表信息
		Map<String, String[][]> chartData = solutionService.getSolutionChartDatas(uid);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("uid", uid);
		modelAndView.addObject("clazz", user.getClazz());
		modelAndView.addObject("name", user.getName());
		modelAndView.addAllObjects(infoMap);
		modelAndView.addObject("loginLogs", loginLogs);
		modelAndView.addObject("resultCounts", resultCounts);
		modelAndView.addObject("all", EncodeUtil.encodeByJson(chartData.get("all")));
		modelAndView.addObject("ac", EncodeUtil.encodeByJson(chartData.get("ac")));
		modelAndView.addObject("problems", problems);
		return modelAndView;
	}
}
