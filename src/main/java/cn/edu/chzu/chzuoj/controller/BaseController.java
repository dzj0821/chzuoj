package cn.edu.chzu.chzuoj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.component.BaseComponent;
import cn.edu.chzu.chzuoj.listener.EventDispatcher;
import cn.edu.chzu.chzuoj.listener.EventType;

/**
 * 
 * @author dzj0821
 *
 */
public abstract class BaseController {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	private EventDispatcher eventDispatcher = EventDispatcher.getInstance();
	
	protected ModelAndView control(BaseComponent[] components, Map<String, String> params, EventType type) {
		ModelAndView modelAndView = new ModelAndView();
		if (components != null) {
			for (BaseComponent component : components) {
				boolean result = component.process(request, response, params, modelAndView, type);
				if (!result) {
					if (type != null) {
						eventDispatcher.dispatchEvent(request, response, params, modelAndView, type, false);
					}
					return modelAndView;
				}
			}
		}
		if (type != null) {
			eventDispatcher.dispatchEvent(request, response, params, modelAndView, type, true);
		}
		return modelAndView;
	}
}
