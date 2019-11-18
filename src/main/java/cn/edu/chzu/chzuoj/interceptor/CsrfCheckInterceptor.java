package cn.edu.chzu.chzuoj.interceptor;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 用于校验csrf token，防止csrf攻击
 * 
 * @author dzj0821
 *
 */
@Component
public class CsrfCheckInterceptor extends BaseInterceptor {

	private static final String POST_METHOD = "POST";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!super.preHandle(request, response, handler)) {
			return false;
		}
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		if (!POST_METHOD.equals(request.getMethod())) {
			return true;
		}
		String csrfToken = request.getParameter("csrf");
		@SuppressWarnings("unchecked")
		Vector<String> csrfKeys = (Vector<String>) request.getSession().getAttribute(SessionAttrNameUtil.getCsrfKeys());
		if (csrfToken == null || csrfKeys == null || !csrfKeys.contains(csrfToken)) {
			// csrf校验失败
			String key = "csrf-check-failed";
			switch (getResponseType((HandlerMethod) handler)) {
			case JSON:
				json(HttpStatus.FORBIDDEN, key, response);
				break;
			case HTML:
				alert(HttpStatus.FORBIDDEN, key, response);
				break;
			case UNKNOWN:
				response.setStatus(HttpStatus.FORBIDDEN.value());
				break;
			default:
				break;
			}
			return false;
		}
		return true;
	}
}
