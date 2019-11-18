package cn.edu.chzu.chzuoj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import cn.edu.chzu.chzuoj.annotation.NeedNotLogin;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 用于实现need-login（需要登录才能访问OJ内容）功能
 * @author dzj0821
 *
 */
@Component
public class NeedLoginCheckInterceptor extends BaseInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 if (!super.preHandle(request, response, handler)) {
			 return false;
		 }
		 //如果已经在登录状态
		 if (request.getSession().getAttribute(SessionAttrNameUtil.getUserId()) != null) {
			 return true;
		 }
		 if (handler instanceof HandlerMethod) {
			 HandlerMethod method = (HandlerMethod) handler;
			 if (method.getMethodAnnotation(NeedNotLogin.class) == null) {
				 //如果访问的方法未标注允许在未登录下访问
				 response.sendRedirect("/user/login");
				 return false;
			 }
		 }
		 return true;
	}
}
