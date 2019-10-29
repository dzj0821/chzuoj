package cn.edu.chzu.chzuoj.component;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.dao.MessageDao;
import cn.edu.chzu.chzuoj.listener.EventType;

/**
 * 所有包含include/js.html的页面需要引入此组件
 * @author dzj0821
 *
 */
@Component
public class UseMarqueeMessageComponent implements BaseComponent {
	@Autowired
	private MessageDao messageDao;

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response, Map<String, String> params,
			ModelAndView modelAndView, EventType type) {
		String marqueeMessage = messageDao.selectMarqueeMessage();
		modelAndView.addObject("marqueeMessage", marqueeMessage);
		return true;
	}

}
