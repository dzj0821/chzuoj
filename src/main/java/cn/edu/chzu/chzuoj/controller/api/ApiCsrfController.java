package cn.edu.chzu.chzuoj.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import cn.edu.chzu.chzuoj.controller.BaseController;
import cn.edu.chzu.chzuoj.util.ResponseUtil;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

@Controller
public class ApiCsrfController extends BaseController {
	public static final int MAX_CSRF_TOKEN_COUNT = 10;
	
	@GetMapping("/api/csrf")
	public ResponseEntity<Map<String, Object>> csrf() {
		String token = UUID.randomUUID().toString();
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<String> csrfKeys = (List<String>) session.getAttribute(SessionAttrNameUtil.getCsrfKeys());
		if (csrfKeys == null) {
			csrfKeys = new Vector<String>(MAX_CSRF_TOKEN_COUNT);
			session.setAttribute(SessionAttrNameUtil.getCsrfKeys(), csrfKeys);
		}
		int size = csrfKeys.size();
		while (size > MAX_CSRF_TOKEN_COUNT - 1) {
			csrfKeys.remove(0);
			size--;
		}
		csrfKeys.add(token);
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("token", token);
		return ResponseUtil.json(HttpStatus.OK, null, map);
	}
	
}
