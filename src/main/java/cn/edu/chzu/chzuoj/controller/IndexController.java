package cn.edu.chzu.chzuoj.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.entity.New;
import cn.edu.chzu.chzuoj.service.IndexService;
import cn.edu.chzu.chzuoj.service.NewService;
import cn.edu.chzu.chzuoj.service.SolutionService;
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
	private NewService newService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private SolutionService solutionService;
	
	@GetMapping("/")
	public ModelAndView index() {
		//主页显示的新闻
		List<New> news = newService.getNews();
		//主页的图标数据
		Map<String, String[][]> chartData = solutionService.getSolutionChartDatas(null);
		//获取OJ上平均提交速度与速度单位
		boolean administrator = request.getSession().getAttribute(SessionAttrNameUtil.getAdministrator()) != null;
		Map<String, String> speedData = indexService.getSubmitSpeedAndUnit(chartData.get("all"), administrator);
		
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("news", news);
		modelAndView.addObject("all", EncodeUtil.encodeByJson(chartData.get("all")));
		modelAndView.addObject("ac", EncodeUtil.encodeByJson(chartData.get("ac")));
		modelAndView.addAllObjects(speedData);
		return modelAndView;
	}
}
