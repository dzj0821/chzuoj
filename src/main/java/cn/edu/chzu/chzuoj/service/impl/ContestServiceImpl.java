package cn.edu.chzu.chzuoj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.dao.ContestDao;
import cn.edu.chzu.chzuoj.entity.Contest;
import cn.edu.chzu.chzuoj.service.ContestService;

/**
 * 
 * @author dzj0821
 *
 */
@Service("contestService")
public class ContestServiceImpl implements ContestService {
	@Autowired
	private ContestDao contestDao;

	@Override
	public List<Map<String, Object>> whichContestsHaveTheProblem(int problemId) {
		return contestDao.selectContestsByProblemId(problemId);
	}
	
	@Override
	public Contest getContest(int cid, boolean special) {
		return contestDao.selectContestById(cid, special);
	}
	

}
