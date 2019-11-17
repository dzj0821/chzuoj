package cn.edu.chzu.chzuoj.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 
 * @author dzj0821
 *
 */
public class ResponseUtil {
	/**
	 * 统一api json接口
	 * @param status 返回码状态
	 * @param message 返回消息
	 * @param data 数据
	 * @return 用于springmvc的ResponseEntity
	 */
	public static ResponseEntity<Map<String, Object>> json(HttpStatus status, String message, Map<String, Object> data) {
		HashMap<String, Object> map = new HashMap<String, Object>(3);
		map.put("status", status.value());
		if (message == null) {
			message = status.toString();
		}
		map.put("message", message);
		map.put("data", data);
		return new ResponseEntity<Map<String, Object>>(map, status);
	}
}
