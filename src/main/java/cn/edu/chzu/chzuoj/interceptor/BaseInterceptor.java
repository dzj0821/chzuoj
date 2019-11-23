package cn.edu.chzu.chzuoj.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.entity.ResponseType;
import cn.edu.chzu.chzuoj.util.EncodeUtil;
import cn.edu.chzu.chzuoj.util.LocaleUtil;
import cn.edu.chzu.chzuoj.util.ResponseUtil;

/**
 * 所有拦截器的基类，便于统一管理
 * 
 * @author dzj0821
 *
 */
public abstract class BaseInterceptor implements HandlerInterceptor {
	@Autowired
	private Config config;

	protected ResponseType getResponseType(HandlerMethod method) {
		Class<?> returnType = method.getMethod().getReturnType();
		if (returnType.equals(ResponseEntity.class)) {
			return ResponseType.JSON;
		}
		if (returnType.equals(ModelAndView.class)) {
			return ResponseType.HTML;
		}
		return ResponseType.UNKNOWN;
	}

	protected void json(HttpStatus status, String key, String[] args, Map<String, Object> data, HttpServletResponse response) throws IOException {
		response.setStatus(status.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> json = ResponseUtil.json(status, key, args, data);
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().append(mapper.writeValueAsString(json));
		
	}

	protected void json(HttpStatus status, HttpServletResponse response) throws IOException {
		json(status, null, null, null, response);
	}

	protected void json(HttpStatus status, String key, HttpServletResponse response) throws IOException {
		json(status, key, null, null, response);
	}
	
	protected void json(HttpStatus status, String key, String[] args, HttpServletResponse response) throws IOException {
		json(status, key, args, null, response);
	}

	protected void json(HttpStatus status, Map<String, Object> data, HttpServletResponse response) throws IOException {
		json(status, null, null, data, response);
	}

	protected void alert(HttpStatus status, String key, HttpServletResponse response) throws IOException {
		alert(status, key, null, response);
	}
	
	protected void alert(HttpStatus status, String key, String[] args, HttpServletResponse response) throws IOException {
		String message = LocaleUtil.getMessage(key, args);
		message = EncodeUtil.encodeByJs(message);
		//主页链接
		String home = EncodeUtil.encodeByJs(config.getHome());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(status.value());
		response.getWriter().append("<html><head><script>alert(\"" + message + "\");window.location.href=\"" 
		+ home + "\";</script></head><body></body></html>");
	}

	/**
	 * 进入controller前调用的方法
	 * 
	 * @param request  请求实例
	 * @param response 响应实例
	 * @param handler  Controller响应的方法实例
	 * @throws Exception
	 * @return 是否继续向下执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	/**
	 * 进入controller后，页面渲染前调用的方法
	 * 
	 * @param request      请求实例
	 * @param response     响应实例
	 * @param handler      Controller响应的方法实例
	 * @param modelAndView controller返回的ModelAndView
	 * @throws Exception
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 页面渲染后调用的方法
	 * 
	 * @param request  请求实例
	 * @param response 响应实例
	 * @param handler  controller响应的方法实例
	 * @param ex       controller抛出的异常
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
