package cn.edu.chzu.chzuoj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import cn.edu.chzu.chzuoj.annotation.NeedNotLogin;
import cn.edu.chzu.chzuoj.config.Config;

/**
 * 用于实现need-login（需要登录才能访问OJ内容）功能
 * @author dzj0821
 *
 */
@Component
public class NeedLoginCheckInterceptor implements BaseInterceptor {
	@Autowired
	private Config config;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 if (!BaseInterceptor.super.preHandle(request, response, handler)) {
			 return false;
		 }
		 //如果没有开启必须登录才能访问OJ的功能
		 if (!config.getNeedLogin()) {
			 return true;
		 }
		 if (handler instanceof HandlerMethod) {
			 HandlerMethod method = (HandlerMethod) handler;
			 if (method.getMethodAnnotation(NeedNotLogin.class) == null) {
				 //如果访问的方法未标注允许在未登录下访问
				 response.sendRedirect("/user/login");
				 System.out.println("redirect" + method.getBean());
				 return false;
			 }
		 }
		 return true;
	}
}
