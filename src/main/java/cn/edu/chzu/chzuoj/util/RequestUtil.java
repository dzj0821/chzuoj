package cn.edu.chzu.chzuoj.util;

import javax.servlet.http.HttpServletRequest;

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
			ip = split[0];
		}
		if (LOCAL_IP.equals(ip)) {
			//如果是本地ip，转换成php本地ip的格式，防止不统一
			ip = "::1";
		}
		return ip;
	}
}
