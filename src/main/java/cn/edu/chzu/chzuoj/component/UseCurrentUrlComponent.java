package cn.edu.chzu.chzuoj.component;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.listener.EventType;

/**
 * 所有包含include/nav.html的页面需要引入此组件
 * @author dzj0821
 *
 */
@Component
public class UseCurrentUrlComponent implements BaseComponent {

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response, Map<String, String> params,
			ModelAndView modelAndView, EventType type) {
		modelAndView.addObject("currentUrl", request.getRequestURI());
		return true;
	}

}
