package cn.edu.chzu.chzuoj.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author dzj0821
 *
 */
public class ResponseUtil {
	/**
	 * 将数据转化为map用于转为json格式
	 * @param status 响应状态
	 * @param key 信息在国际化中的key
	 * @param args 信息的参数
	 * @param data 响应的数据
	 * @return
	 */
	public static Map<String, Object> json(HttpStatus status, String key, String[] args, Map<String, Object> data) {
		HashMap<String, Object> map = new HashMap<String, Object>(3);
		map.put("status", status.value());
		if (key == null) {
			map.put("message", status.toString());
		} else {
			map.put("message", LocaleUtil.getMessage(key, args));
		}
		map.put("data", data);
		return map;
	}
}
