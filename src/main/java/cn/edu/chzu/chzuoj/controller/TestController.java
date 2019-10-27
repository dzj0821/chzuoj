package cn.edu.chzu.chzuoj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.component.BaseComponent;
import cn.edu.chzu.chzuoj.listener.EventDispatcher;
import cn.edu.chzu.chzuoj.listener.EventType;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
public class TestController extends BaseController {
	static {
		EventDispatcher.getInstance().addEventListener(EventType.TEST, (HttpServletRequest request, HttpServletResponse response, Map<String, String> params, ModelAndView modelAndView, EventType type, boolean success)->{
			System.out.println("listen, " + success);
		});
	}
	
	@RequestMapping("/test")
	public ModelAndView test(@RequestParam Map<String, String> params) {
		return control(new BaseComponent[] {
				(HttpServletRequest request, HttpServletResponse response, Map<String, String> map, ModelAndView modelAndView, EventType type)->{
					return true;
				}
		}, params, EventType.TEST);
	}
}
