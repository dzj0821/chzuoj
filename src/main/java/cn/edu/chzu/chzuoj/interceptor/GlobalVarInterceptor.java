package cn.edu.chzu.chzuoj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.service.MessageService;
import cn.edu.chzu.chzuoj.util.EncodeUtil;

/**
 * 用于给ModelAndView添加常用的变量信息
 * @author dzj0821
 *
 */
@Component
public class GlobalVarInterceptor extends BaseInterceptor {
	@Autowired
	private Config config;
	@Autowired
	private MessageService messageService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		if (modelAndView != null) {
			modelAndView.addObject("config", config);
			//nav需要用到，用于高亮当前页面所在标签
			modelAndView.addObject("currentUrl", request.getRequestURI());
			//nav的滚动公告
			String marqueeMessage = EncodeUtil.encodeByJs(messageService.getMarqueeMessage());
			modelAndView.addObject("marqueeMessage", marqueeMessage);
			//页尾内容
			String footerMessage = EncodeUtil.encodeByJs(messageService.getFooterMessage());
			modelAndView.addObject("footerMessage", footerMessage);
		}
	}
}
