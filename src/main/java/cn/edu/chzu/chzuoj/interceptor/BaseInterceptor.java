package cn.edu.chzu.chzuoj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 所有拦截器的基类，便于统一管理
 * @author dzj0821
 *
 */
public interface BaseInterceptor extends HandlerInterceptor {
	/**
	 * 进入controller前调用的方法
	 * @param request 请求实例
	 * @param response 响应实例
	 * @param handler Controller响应的方法实例
	 * @throws Exception
	 * @return 是否继续向下执行
	 */
	@Override
	public default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	/**
	 * 进入controller后，页面渲染前调用的方法
	 * @param request 请求实例
	 * @param response 响应实例
	 * @param handler Controller响应的方法实例
	 * @param modelAndView controller返回的ModelAndView
	 * @throws Exception
	 */
	@Override
	public default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * 页面渲染后调用的方法
	 * @param request 请求实例
	 * @param response 响应实例
	 * @param handler controller响应的方法实例
	 * @param ex controller抛出的异常
	 * @throws Exception
	 */
	@Override
	public default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
