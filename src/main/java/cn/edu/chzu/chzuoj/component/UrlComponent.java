package cn.edu.chzu.chzuoj.component;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.listener.EventType;

/**
 * url相关组件
 * @author dzj0821
 *
 */
@Component
public class UrlComponent implements BaseComponent {

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response, Map<String, String> params,
			ModelAndView modelAndView, EventType type) {
		//当前url
		modelAndView.addObject("currentUrl", request.getRequestURI());
		return true;
	}

}
