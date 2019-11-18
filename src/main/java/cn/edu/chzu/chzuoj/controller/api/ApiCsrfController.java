package cn.edu.chzu.chzuoj.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import cn.edu.chzu.chzuoj.annotation.NeedNotLogin;
import cn.edu.chzu.chzuoj.annotation.OnSiteContestEnable;
import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.controller.BaseController;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 用于获取csrf token防止csrf攻击
 * @author dzj0821
 *
 */
@Controller
public class ApiCsrfController extends BaseController {
	@Autowired
	private Config config;
	/**
	 * 单用户最大token数量
	 */
	private static final int MAX_CSRF_TOKEN_COUNT = 10;
	
	@OnSiteContestEnable
	@NeedNotLogin
	@GetMapping("/api/csrf")
	public ResponseEntity<Map<String, Object>> csrf() {
		if (!config.getCsrfCheck()) {
			return json(HttpStatus.SERVICE_UNAVAILABLE, "csrf-check-disabled");
		}
		//获取一个随机token
		String token = UUID.randomUUID().toString();
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<String> csrfKeys = (List<String>) session.getAttribute(SessionAttrNameUtil.getCsrfKeys());
		if (csrfKeys == null) {
			//如果还没获取过token，新建一个数组存token，使用Vector线程安全
			csrfKeys = new Vector<String>(MAX_CSRF_TOKEN_COUNT);
			session.setAttribute(SessionAttrNameUtil.getCsrfKeys(), csrfKeys);
		}
		int size = csrfKeys.size();
		//如果存进去之后会超出最大数量，提前把最早存入的移除
		while (size > MAX_CSRF_TOKEN_COUNT - 1) {
			csrfKeys.remove(0);
			size--;
		}
		csrfKeys.add(token);
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("token", token);
		return json(HttpStatus.OK, map);
	}
	
}
