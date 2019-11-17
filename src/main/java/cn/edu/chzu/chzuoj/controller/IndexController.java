package cn.edu.chzu.chzuoj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.entity.New;
import cn.edu.chzu.chzuoj.service.IndexService;
import cn.edu.chzu.chzuoj.service.MessageService;
import cn.edu.chzu.chzuoj.service.NewService;
import cn.edu.chzu.chzuoj.util.EncodeUtil;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 主页
 * @author dzj0821
 *
 */
@Controller
public class IndexController extends BaseController {
	@Autowired
	private Config config;
	@Autowired
	private MessageService messageService;
	@Autowired
	private NewService newService;
	@Autowired
	private IndexService indexService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("config", config);
		//nav需要用到，用于高亮当前页面所在标签
		modelAndView.addObject("currentUrl", request.getRequestURI());
		//nav的滚动公告
		String marqueeMessage = messageService.getMarqueeMessage();
		modelAndView.addObject("marqueeMessage", marqueeMessage);
		//主页显示的新闻
		List<New> news = newService.getNews();
		modelAndView.addObject("news", news);
		//主页的图标数据
		String[][] all = indexService.getAllChartData();
		modelAndView.addObject("all", EncodeUtil.encodeByJson(all));
		String[][] ac = indexService.getAcceptedChartData();
		modelAndView.addObject("ac", EncodeUtil.encodeByJson(ac));
		//获取OJ上平均提交速度与速度单位
		boolean administrator = request.getSession().getAttribute(SessionAttrNameUtil.getAdministrator()) != null;
		String speed = indexService.getSubmitSpeed(all, administrator);
		String speedUnit = indexService.getSubmitSpeedUnit(administrator);
		modelAndView.addObject("speed", speed);
		modelAndView.addObject("speedUnit", speedUnit);
		
		return modelAndView;
	}
}
