package cn.edu.chzu.chzuoj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.entity.Contest;
import cn.edu.chzu.chzuoj.entity.Problem;
import cn.edu.chzu.chzuoj.exception.ExecuteFailedException;
import cn.edu.chzu.chzuoj.service.ContestService;
import cn.edu.chzu.chzuoj.service.ProblemService;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
@RequestMapping("/problem")
public class ProblemController extends BaseController {
	@Autowired
	private Config config;
	@Autowired
	private ProblemService problemService;
	@Autowired
	private ContestService contestService;

	@GetMapping("/list")
	public ModelAndView list(Integer page, String search) {
		// 是否是管理员
		boolean administrator = request.getSession().getAttribute(SessionAttrNameUtil.getAdministrator()) != null;
		// 用户id，null表示未登录
		String uid = (String) request.getSession().getAttribute(SessionAttrNameUtil.getUserId());
		if ("".equals(search)) {
			search = null;
		}
		// 获取筛选后题目总页数
		int pageCount = problemService.getPageCount(search, administrator);
		// 题目列表界面记住页码功能
		page = problemService.remeberPage(page, uid, pageCount, search != null);
		// 获取题目
		List<Problem> problems = problemService.getProblems(search, administrator, page);
		// 获取题目来源及配色
		List<ArrayList<HashMap<String, String>>> problemSources = problemService.getProblemSources(problems);
		// 获取题目完成状态
		Boolean[] problemAccepts = problemService.getProblemStatusByUserId(uid, problems);
		// 计算可以选取的页数范围
		int maxChoosePageCount = config.getMaxChoosePageCount();
		int startPage = page > maxChoosePageCount ? page - maxChoosePageCount : 1;
		int endPage = page + maxChoosePageCount > pageCount ? pageCount : page + maxChoosePageCount;

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("search", search);
		modelAndView.addObject("problems", problems);
		modelAndView.addObject("page", page);
		modelAndView.addObject("startPage", startPage);
		modelAndView.addObject("endPage", endPage);
		modelAndView.addObject("problemAccepts", problemAccepts);
		modelAndView.addObject("pageCount", pageCount);
		modelAndView.addObject("problemSources", problemSources);
		return modelAndView;
	}

	@GetMapping("/info")
	public ModelAndView info(Integer id, Integer cid, Integer num) {
		//TODO 未完成
		if (id != null && cid == null && num == null) {
			return normalInfo(id);
		}
		if (id == null && cid != null && num != null) {
			return contestInfo(cid, num);
		}
		return message("no-such-problem");
	}

	private ModelAndView normalInfo(int id) {
		boolean special = isSpecial();
		Problem problem = problemService.getProblem(id, special);
		if (problem == null && special) {
			//如果查询了所有题目，依然没找到，说明没有这个题目
			return message("no-such-problem");
		}
		if (problem == null && !special) {
			//如果没有查询所有题目，可能是因为题目在未开启的竞赛中
			List<Map<String, Object>> contests = contestService.whichContestsHaveTheProblem(id);
			if (contests.isEmpty()) {
				//如果还是没有，说明没有这个题目
				return message("no-such-problem");
			}
			ModelAndView modelAndView = new ModelAndView("problem/problem-in-private-contest");
			modelAndView.addObject("contests", contests);
			return modelAndView;
		}
		String[] sources = problemService.getSourcesName(problem);
		//查询到题目了
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("problem", problem);
		modelAndView.addObject("sources", sources);
		//代表这是普通题目
		modelAndView.addObject("normal", true);
		modelAndView.addObject("special", special);
		return modelAndView;
	}

	private ModelAndView contestInfo(int cid, int num) {
		boolean special = isSpecial();
		String userId = (String) request.getSession().getAttribute(SessionAttrNameUtil.getUserId());
		if (userId == null) {
			return message("not-invited");
		}
		Contest contest = contestService.getContest(cid, special);
		if (contest == null) {
			return message("no-such-contest");
		}
		Problem problem = null;
		try {
			problem = problemService.getContestProblem(cid, num, special, userId);
		} catch (ExecuteFailedException e) {
			return message(e.getKey());
		}
		if (problem == null) {
			return message("no-such-problem");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("problem", problem);
		//代表这是竞赛中的题目
		modelAndView.addObject("contest", contest);
		modelAndView.addObject("num", num);
		modelAndView.addObject("number", num + 'A');
		modelAndView.addObject("special", special);
		return modelAndView;
	}
	
	private boolean isSpecial() {
		return request.getSession().getAttribute(SessionAttrNameUtil.getAdministrator()) != null
				|| request.getSession().getAttribute(SessionAttrNameUtil.getContestCreator()) != null
				|| request.getSession().getAttribute(SessionAttrNameUtil.getProblemEditor()) != null;
	}
}
