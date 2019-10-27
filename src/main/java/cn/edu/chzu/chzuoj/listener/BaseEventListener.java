package cn.edu.chzu.chzuoj.listener;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author dzj0821
 *
 */
public interface BaseEventListener {
	/**
	 * 事件触发时的回调，在此方法内不得改变任何参数值
	 * @param request - http请求
	 * @param response - http响应
	 * @param params - 参数列表
	 * @param modelAndView - SpringMVC响应对象
	 * @param type - 事件类型
	 * @param success - 事件是否成功
	 */
	public void onEvent(HttpServletRequest request, HttpServletResponse response, Map<String, String> params, ModelAndView modelAndView, EventType type, boolean success);
}
