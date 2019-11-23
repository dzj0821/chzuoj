package cn.edu.chzu.chzuoj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.util.ResponseUtil;

/**
 * 
 * @author dzj0821
 *
 */
public abstract class BaseController implements WebBindingInitializer {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	
	@InitBinder
	@Override
	public void initBinder(WebDataBinder binder) {
		//为所有的输入使用trim过滤
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
	/**
	 * 在页面中显示消息的便捷方法
	 * @param key 消息在国际化中的key
	 * @param url 消息跳转的url
	 * @return
	 */
	protected ModelAndView message(String key, String url) {
		ModelAndView modelAndView = new ModelAndView("message");
		modelAndView.addObject("message", key);
		if (url != null) {
			modelAndView.addObject("url", url);
		}
		return modelAndView;
	}
	
	protected ModelAndView message(String key) {
		return message(key, null);
	}
	
	/**
	 * 统一api json接口
	 * @param status 返回码状态
	 * @param key 返回消息在国际化中的key
	 * @param args 返回消息的参数
	 * @param data 数据
	 * @return 用于springmvc的ResponseEntity
	 */
	protected ResponseEntity<Map<String, Object>> json(HttpStatus status, String key, String[] args, Map<String, Object> data) {
		return new ResponseEntity<Map<String, Object>>(ResponseUtil.json(status, key, args, data), status);
	}
	
	protected ResponseEntity<Map<String, Object>> json(HttpStatus status) {
		return json(status, null, null, null);
	}
	
	protected ResponseEntity<Map<String, Object>> json(HttpStatus status, String key) {
		return json(status, key, null, null);
	}
	
	protected ResponseEntity<Map<String, Object>> json(HttpStatus status, String key, String[] args) {
		return json(status, key, args, null);
	}
	
	protected ResponseEntity<Map<String, Object>> json(HttpStatus status, Map<String, Object> data) {
		return json(status, null, null, data);
	}
}
