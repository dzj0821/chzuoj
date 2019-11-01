package cn.edu.chzu.chzuoj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.chzu.chzuoj.config.Config;

/**
 * 获取session中各种属性名的工具类
 * @author dzj0821
 *
 */
@Component
public class SessionAttrNameUtil {
	
	private static Config config;
	
	public static final String SEPARATOR = "_";
	
	public static String getAdministrator() {
		return config.getName() + SEPARATOR + "administrator";
	}
	
	public static String getContestCreator() {
		return config.getName() + SEPARATOR + "contest_creator";
	}
	
	public static String getProblemEditor() {
		return config.getName() + SEPARATOR + "problem_editor";
	}
	
	public static String getClassManager() {
		return config.getName() + SEPARATOR + "class_manager";
	}
	
	public static String getUserId() {
		return config.getName() + SEPARATOR + "user_id";
	}
	
	public static String getVcode() {
		return config.getName() + SEPARATOR + "vcode";
	}
	
	public static String getSessionName(String string) {
		return config.getName() + SEPARATOR + string;
	}
	
	@Autowired
	public void setConfig(Config config) {
		SessionAttrNameUtil.config = config;
	}
	
	private SessionAttrNameUtil() {}
}
