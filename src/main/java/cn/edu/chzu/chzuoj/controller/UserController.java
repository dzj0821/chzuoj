package cn.edu.chzu.chzuoj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.annotation.NeedNotLogin;
import cn.edu.chzu.chzuoj.config.Config;
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
	
	@NeedNotLogin
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		if (request.getSession().getAttribute(SessionAttrNameUtil.getUserId()) != null) {
			//如果已登录，提示需要先登出
			modelAndView.addObject("message", "please-logout-first");
			modelAndView.addObject("url", "/logout");
			modelAndView.setViewName("message");
			return modelAndView;
		}
		modelAndView.addObject("config", config);
		return modelAndView;
	}
}
