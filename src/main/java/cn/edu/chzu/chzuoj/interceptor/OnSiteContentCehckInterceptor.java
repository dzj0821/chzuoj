package cn.edu.chzu.chzuoj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import cn.edu.chzu.chzuoj.annotation.OnSiteContestEnable;


/**
 * 用于实现on-site-content（现场赛模式）功能，当进入除了现场赛以外的页面时跳转到现场赛页面
 * @author dzj0821
 *
 */
@Component
public class OnSiteContentCehckInterceptor extends BaseInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 if (!super.preHandle(request, response, handler)) {
			 return false;
		 }
		 if (handler instanceof HandlerMethod) {
			 HandlerMethod method = (HandlerMethod) handler;
			 if (method.getMethodAnnotation(OnSiteContestEnable.class) == null) {
				 alert(HttpStatus.FORBIDDEN, "on-site-contest-not-allowed", response);
				 return false;
			 }
		 }
		 return true;
	}
}
