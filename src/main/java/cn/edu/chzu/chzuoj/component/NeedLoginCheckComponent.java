package cn.edu.chzu.chzuoj.component;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.listener.EventType;

/**
 * 
 * @author dzj0821
 *
 */
@Component
public class NeedLoginCheckComponent implements BaseComponent {
	@Autowired
	private Config config;

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response, Map<String, String> params,
			ModelAndView modelAndView, EventType type) {
		if (config.getNeedLogin()) {
			String[] whiteList = {
					"/Login",
					"/LostPassword",
					"/LostPassword2",
					"/Register",
			};
			String uri = request.getRequestURI();
			//如果不在白名单内
			if (Arrays.stream(whiteList).allMatch((String string) -> !uri.equals(string))) {
				return false;
			}
		}
		return true;
	}

}
