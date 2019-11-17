package cn.edu.chzu.chzuoj.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import cn.edu.chzu.chzuoj.annotation.NeedNotLogin;
import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.controller.BaseController;
import cn.edu.chzu.chzuoj.util.ResponseUtil;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
public class ApiProfileController extends BaseController {
	@Autowired
	private Config config;
	
	/**
	 * 
	 * @return OK需要返回uid, shareCode, permission
	 * uid: 用户id
	 * shareCode: 此时是否能分享代码
	 * permission: 是否具有进入管理页面的权限
	 */
	@NeedNotLogin
	@GetMapping("/api/profile")
	public ResponseEntity<Map<String, Object>> profile() {
		Map<String, Object> result = new HashMap<String, Object>(3);
		HttpSession session = request.getSession();
		Object userId = session.getAttribute(SessionAttrNameUtil.getUserId());
		result.put("uid", userId);
		if (userId == null) {
			return ResponseUtil.json(HttpStatus.FORBIDDEN, null, null);
		}
		//shareCode条件：服务器开启分享代码且不在现场赛或考试状态
		result.put("shareCode", config.getShareCode() && config.getOnSiteContestId() == null && config.getExamContestId() == null);
		//permission条件：至少拥有以下4个管理权限中的一个
		boolean permission = false;
		String[] permissionNames = {
				SessionAttrNameUtil.getAdministrator(),
				SessionAttrNameUtil.getClassManager(),
				SessionAttrNameUtil.getContestCreator(),
				SessionAttrNameUtil.getProblemEditor()
		};
		for (String string : permissionNames) {
			if (session.getAttribute(string) != null) {
				permission = true;
				break;
			}
		}
		result.put("permission", permission);
		return ResponseUtil.json(HttpStatus.OK, null, result);
	}
}
