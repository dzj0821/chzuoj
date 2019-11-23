package cn.edu.chzu.chzuoj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import cn.edu.chzu.chzuoj.annotation.ContestEnable;
import cn.edu.chzu.chzuoj.entity.ResponseType;


/**
 * 当比赛模式开启时此拦截器将会启用，防止用于进入比赛以外的页面
 * @author dzj0821
 *
 */
@Component
public class ContentCehckInterceptor extends BaseInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 if (!super.preHandle(request, response, handler)) {
			 return false;
		 }
		 if (handler instanceof HandlerMethod) {
			 HandlerMethod method = (HandlerMethod) handler;
			 if (method.getMethodAnnotation(ContestEnable.class) == null) {
				 ResponseType type = getResponseType(method);
				 switch (type) {
				case HTML:
					alert(HttpStatus.FORBIDDEN, "contest-mode-not-allowed", response);
					break;
				case JSON:
					json(HttpStatus.FORBIDDEN, "contest-mode-not-allowed", response);
				default:
					response.setStatus(HttpStatus.FORBIDDEN.value());
					break;
				}
				 return false;
			 }
		 }
		 return true;
	}
}
