package cn.edu.chzu.chzuoj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.dao.ClazzContestDao;
import cn.edu.chzu.chzuoj.dao.ProblemDao;
import cn.edu.chzu.chzuoj.dao.SolutionDao;
import cn.edu.chzu.chzuoj.dao.UserDao;
import cn.edu.chzu.chzuoj.entity.ColorTheme;
import cn.edu.chzu.chzuoj.entity.Problem;
import cn.edu.chzu.chzuoj.exception.ExecuteFailedException;
import cn.edu.chzu.chzuoj.service.ProblemService;
import cn.edu.chzu.chzuoj.util.EncryptUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Service("problemService")
public class ProblemServiceImpl implements ProblemService {
	@Autowired
	private Config config;
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SolutionDao solutionDao;
	@Autowired
	private ClazzContestDao clazzContestDao;

	@Override
	public int getPageCount(String search, boolean administrator) {
		// 符合条件的题目总数
		int problemCount = problemDao.selectProblemCount(search, administrator);
		// 每页显示的题目数
		int problemCountPerPage = config.getProblemCountPerPage();
		// 总页数
		int pageCount = problemCount % problemCountPerPage == 0 ? (problemCount / problemCountPerPage)
				: (problemCount / problemCountPerPage + 1);
		return pageCount;
	}

	@Override
	public int remeberPage(Integer page, String uid, int pageCount, boolean search) {
		if (page != null && page < 1) {
			page = 1;
		}
		if (page != null && uid != null) {
			userDao.updateUserProblemListRememberPageById(uid, page);
		}
		// 非查询状态才记住页码
		if (page == null && uid != null && !search) {
			Integer remeberPage = userDao.selectUserById(uid).getProblemListRememberPage();
			if (remeberPage != null) {
				page = remeberPage;
			}
		}
		if (page == null || page > pageCount) {
			page = 1;
		}
		return page;
	}

	@Override
	public List<Problem> getProblems(String search, boolean administrator, int page) {
		int problemCountPerPage = config.getProblemCountPerPage();
		return problemDao.selectProblems(search, administrator, (page - 1) * problemCountPerPage, problemCountPerPage);
	}

	@Override
	public Boolean[] getProblemStatusByUserId(String uid, List<Problem> problems) {
		// 查询提交情况
		Set<Integer> submitIdsSet = new HashSet<Integer>();
		Set<Integer> acceptedIdsSet = new HashSet<Integer>();
		if (uid != null) {
			List<Integer> submitIds = solutionDao.selectSubmitProblemIdsByUserId(uid);
			submitIdsSet.addAll(submitIds);
			List<Integer> acceptedIds = solutionDao.selectSolvedProblemIdsByUserId(uid);
			acceptedIdsSet.addAll(acceptedIds);
		}
		// 计算列表中的题目是否已被提交，是否提交正确
		Boolean[] problemAccepts = new Boolean[problems.size()];
		for (int i = 0; i < problems.size(); i++) {
			Problem problem = problems.get(i);
			if (submitIdsSet.contains(problem.getId())) {
				if (acceptedIdsSet.contains(problem.getId())) {
					problemAccepts[i] = true;
				} else {
					problemAccepts[i] = false;
				}
			}
		}
		return problemAccepts;
	}

	@Override
	public List<ArrayList<HashMap<String, String>>> getProblemSources(List<Problem> problems) {
		List<ArrayList<HashMap<String, String>>> problemSources = new ArrayList<ArrayList<HashMap<String, String>>>(
				problems.size());
		for (Problem problem : problems) {
			String source = problem.getSource();
			ArrayList<HashMap<String, String>> sourceList = new ArrayList<HashMap<String, String>>();
			if (source == null || "".equals(source)) {
				problemSources.add(sourceList);
				continue;
			}
			String[] sourceNames = source.split("\\s+");
			for (String sourceName : sourceNames) {
				int hash = Integer.parseInt(EncryptUtil.md5(sourceName).substring(0, 7), 16);
				HashMap<String, String> map = new HashMap<String, String>(2);
				map.put("name", sourceName);
				map.put("theme", ColorTheme.get(hash % ColorTheme.values().length).getValue());
				sourceList.add(map);
			}
			problemSources.add(sourceList);
		}
		return problemSources;
	}

	@Override
	public Problem getProblem(int id, boolean special) {
		return problemDao.selectProblemById(id, special);
	}

	@Override
	public String[] getSourcesName(Problem problem) {
		return problem.getSource().split("\\s+");
	}

	@Override
	public Problem getContestProblem(int cid, int num, boolean special, String userId) throws ExecuteFailedException {
		// 只有非管理员才要判断用户是否与比赛所属班级有联系
		if (!special) {
			boolean related = clazzContestDao.isRelatedContest(cid, userId);
			if (!related) {
				throw new ExecuteFailedException("not-invited");
			}
		}
		return problemDao.selectContestProblemByContestIdAndNum(cid, num);
	}
}
