package cn.edu.chzu.chzuoj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.dao.ContestDao;
import cn.edu.chzu.chzuoj.entity.Contest;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
@RequestMapping("/contest")
public class ContestController extends BaseController {
	@Autowired
	private Config config;
	@Autowired
	private ContestDao contestDao;

	@GetMapping("/list")
	public ModelAndView list(Integer page, String search, String my) {
		if (page == null || page <= 0) {
			page = 1;
		}
		int contestCountPerPage = config.getContestCountPerPage();
		int offset = contestCountPerPage * (page - 1);
		boolean administrator = request.getSession().getAttribute(SessionAttrNameUtil.getAdministrator()) != null;
		String userId = (String) request.getSession().getAttribute(SessionAttrNameUtil.getUserId());
		int contestCount = contestDao.selectContestsCount(search, userId, administrator, my != null);
		int pageCount = contestCount % contestCountPerPage == 0 ? contestCount / contestCountPerPage
				: (contestCount / contestCountPerPage + 1);
		List<Contest> contests = contestDao.selectContests(search, userId, administrator, my != null, offset, contestCountPerPage);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("contests", contests);
		modelAndView.addObject("pageCount", pageCount);
		return modelAndView;
	}
}
