package cn.edu.chzu.chzuoj.util;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

/**
 * http session相关的工具类
 * @author dzj0821
 *
 */
public class SessionUtil {
	/**
	 * 移除session中的所有属性
	 * @param session 需要移除属性的session
	 */
	public static void removeAllAttributes(HttpSession session) {
		Enumeration<String> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			session.removeAttribute(keys.nextElement());
		}
	}
}
