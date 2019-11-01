package cn.edu.chzu.chzuoj.component;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.listener.EventType;
import cn.edu.chzu.chzuoj.pojo.Permission;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Component
public class UserComponent implements BaseComponent {

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response, Map<String, String> params,
			ModelAndView modelAndView, EventType type) {
		HttpSession session = request.getSession();
		Object userId = session.getAttribute(SessionAttrNameUtil.getUserId());
		if (userId == null) {
			return true;
		}
		//用户已登录的情况
		modelAndView.addObject("uid", userId);
		Permission permission = new Permission();
		permission.setAdministrator(session.getAttribute(SessionAttrNameUtil.getAdministrator()) != null);
		permission.setContestCreator(session.getAttribute(SessionAttrNameUtil.getContestCreator()) != null);
		permission.setProblemEditor(session.getAttribute(SessionAttrNameUtil.getProblemEditor()) != null);
		permission.setClassManager(session.getAttribute(SessionAttrNameUtil.getClassManager()) != null);
		modelAndView.addObject("permission", permission);
		return true;
	}

}
