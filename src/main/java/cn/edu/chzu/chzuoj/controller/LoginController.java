package cn.edu.chzu.chzuoj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.component.BaseComponent;
import cn.edu.chzu.chzuoj.component.UseConfigComponent;
import cn.edu.chzu.chzuoj.listener.EventType;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
public class LoginController extends BaseController {
	@Autowired
	private UseConfigComponent useConfigComponent;
	
	@GetMapping("/Login")
	public ModelAndView loginGet(@RequestParam Map<String, String> map) {
		return control(new BaseComponent[] {
				useConfigComponent,
				(HttpServletRequest request, HttpServletResponse response, Map<String, String> params, ModelAndView modelAndView, EventType type) -> {
					if (request.getSession().getAttribute(SessionAttrNameUtil.getUserId()) != null) {
						//如果已登录，提示需要先登出
						modelAndView.addObject("message", "please-logout-first");
						modelAndView.addObject("url", "/Logout");
						modelAndView.setViewName("message");
						return false;
					}
					return true;
				}
		}, map, null);
	}
}
