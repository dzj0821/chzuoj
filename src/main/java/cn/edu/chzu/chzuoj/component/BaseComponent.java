package cn.edu.chzu.chzuoj.component;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.listener.EventType;

/**
 * 
 * @author dzj0821
 *
 */
public interface BaseComponent {
	/**
	 * 处理请求
	 * @param request - http请求，可以通过此对象获取session和cookie
	 * @param response - http响应，一般情况不直接操作此对象
	 * @param params - 传递参数
	 * @param modelAndView - 一般使用此对象进行响应
	 * @param type - 事件类型，如果不产生事件传递null
	 * @return 处理是否成功，如果失败则立刻中断处理并响应客户端
	 */
	public boolean process(HttpServletRequest request, HttpServletResponse response, Map<String, String> params, ModelAndView modelAndView, EventType type);
}
