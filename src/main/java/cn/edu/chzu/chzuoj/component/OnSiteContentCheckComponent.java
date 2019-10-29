package cn.edu.chzu.chzuoj.component;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.listener.EventType;

/**
 * 如果开启了现场赛则跳转到现场赛页面
 * @author dzj0821
 *
 */
@Component
public class OnSiteContentCheckComponent implements BaseComponent {
	
	@Autowired
	private Config config;

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response, Map<String, String> params,
			ModelAndView modelAndView, EventType type) {
		if (config.getOnSiteContestId() != null) {
			modelAndView.addObject("url", "/contest?cid=" + config.getOnSiteContestId());
			modelAndView.setViewName("redirect");
			return false;
		}
		return true;
	}

}
