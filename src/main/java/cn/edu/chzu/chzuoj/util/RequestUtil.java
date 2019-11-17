package cn.edu.chzu.chzuoj.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.HtmlUtils;

/**
 * 
 * @author dzj0821
 *
 */
public class RequestUtil {
	private final static String LOCAL_IP = "0:0:0:0:0:0:0:1";
	
	public static String getActuallyIp(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		String xForwardedFor = request.getHeader("X-Forwarded-For");
		if (xForwardedFor != null && !"".equals(xForwardedFor)) {
			//如果是通过代理转发的，获取真实ip
			String[] split = xForwardedFor.split(",", 2);
			if (split.length > 0) {
				//php中进行了html转义，不知道为什么
				ip = HtmlUtils.htmlEscape(split[0], "UTF-8");
			}
		}
		if (LOCAL_IP.equals(ip)) {
			//如果是本地ip，转换成php本地ip的格式，防止不统一
			ip = "::1";
		}
		return ip;
	}
}
