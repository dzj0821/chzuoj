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
	public static Map<String, Object> json(HttpStatus status, String key, Map<String, Object> data) {
		HashMap<String, Object> map = new HashMap<String, Object>(3);
		map.put("status", status.value());
		if (key == null) {
			map.put("message", status.toString());
		} else {
			map.put("message", LocaleUtil.getMessage(key));
		}
		map.put("data", data);
		return map;
	}
}
