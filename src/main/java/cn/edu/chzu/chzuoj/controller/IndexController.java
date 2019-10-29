package cn.edu.chzu.chzuoj.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.chzu.chzuoj.component.BaseComponent;
import cn.edu.chzu.chzuoj.component.OnSiteContentCheckComponent;
import cn.edu.chzu.chzuoj.component.UseConfigComponent;
import cn.edu.chzu.chzuoj.component.UseCurrentUrlComponent;
import cn.edu.chzu.chzuoj.component.UseMarqueeMessageComponent;
import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.dao.MessageDao;
import cn.edu.chzu.chzuoj.dao.NewDao;
import cn.edu.chzu.chzuoj.dao.SolutionDao;
import cn.edu.chzu.chzuoj.listener.EventType;
import cn.edu.chzu.chzuoj.pojo.ChartData;
import cn.edu.chzu.chzuoj.pojo.New;

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
	private UseConfigComponent useConfigComponent;
	@Autowired
	private OnSiteContentCheckComponent onSiteContentCehckComponent;
	@Autowired
	private UseCurrentUrlComponent useCurrentUrlComponent;
	@Autowired
	private UseMarqueeMessageComponent useMarqueeMessageComponent;
	@Autowired
	private NewDao newDao;
	@Autowired
	private SolutionDao solutionDao;
	
	@RequestMapping("/")
	public ModelAndView index(@RequestParam Map<String, String> map) {
		return control(new BaseComponent[] { 
				useConfigComponent,
				onSiteContentCehckComponent,
				useCurrentUrlComponent,
				useMarqueeMessageComponent,
				(HttpServletRequest request, HttpServletResponse response, Map<String, String> params, ModelAndView modelAndView, EventType type)->{
					List<New> news = newDao.selectUsedNews();
					modelAndView.addObject("news", news);
					//获得图表数据
					List<ChartData> allChartDatas = solutionDao.selectAllChartData();
					String all[][] = new String[allChartDatas.size()][2];
					for (int i = 0; i < allChartDatas.size(); i++) {
						all[i][0] = allChartDatas.get(i).getTimestamp();
						all[i][1] = allChartDatas.get(i).getCount();
					}
					//转换成JSON数据
					ObjectMapper objectMapper = new ObjectMapper();
					String json = null;
					try {
						json = objectMapper.writeValueAsString(all);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					modelAndView.addObject("all", json);
					List<ChartData> acceptedChartDatas = solutionDao.selectAcceptedChartData();
					String ac[][] = new String[acceptedChartDatas.size()][2];
					for (int i = 0; i < acceptedChartDatas.size(); i++) {
						ac[i][0] = acceptedChartDatas.get(i).getTimestamp();
						ac[i][1] = acceptedChartDatas.get(i).getCount();
					}
					try {
						json = objectMapper.writeValueAsString(ac);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					modelAndView.addObject("ac", json);
					//OJ上平均提交速度
					String speed = null;
					if (all.length > 0 && all[0][1] != null) {
						speed = all[0][1];
					} else {
						speed = "0";
					}
					String speedUnit = "day";
					//如果是管理员，查询更精细的数据
					if (request.getSession().getAttribute(config.getName() + "_administrator") != null) {
						Integer speedPerMin = solutionDao.selectSubmitSpeedPerMin();
						if (speedPerMin == null) {
							speed = "0";
						} else {
							speed = speedPerMin.toString();
						}
						speedUnit = "min";
					}
					modelAndView.addObject("speed", speed);
					modelAndView.addObject("speedUnit", speedUnit);
					//获取滚动条公告
					modelAndView.setViewName("index");
					return true;
				}
		}, map, EventType.TEST);
	}
}
