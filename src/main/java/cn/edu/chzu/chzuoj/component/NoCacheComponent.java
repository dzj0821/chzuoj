package cn.edu.chzu.chzuoj.component;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.listener.EventType;

/**
 * 禁止缓存
 * @author dzj0821
 *
 */
@Component
public class NoCacheComponent implements BaseComponent {

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response, Map<String, String> params,
			ModelAndView modelAndView, EventType type) {
		response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Pragma", "no-cache");
		return true;
	}
	
}
