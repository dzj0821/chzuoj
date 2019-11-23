package cn.edu.chzu.chzuoj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.dao.SolutionDao;
import cn.edu.chzu.chzuoj.service.SolutionService;

/**
 * 
 * @author dzj0821
 *
 */
@Service("solutionService")
public class SolutionServiceImpl implements SolutionService {
	@Autowired
	private SolutionDao solutionDao;

	@Override
	public Map<String, String[][]> getSolutionChartDatas(String uid) {
		//总提交
		List<Map<String, Object>> allChartDatas = solutionDao.selectSubmitCountsAndTimestamps(uid, false);
		String[][] all = new String[allChartDatas.size()][2];
		for (int i = 0; i < allChartDatas.size(); i++) {
			all[i][0] = allChartDatas.get(i).get("timestamp").toString();
			all[i][1] = allChartDatas.get(i).get("count").toString();
		}
		//正确提交
		List<Map<String, Object>> acceptedChartDatas = solutionDao.selectSubmitCountsAndTimestamps(uid, true);
		String[][] ac = new String[acceptedChartDatas.size()][2];
		for (int i = 0; i < acceptedChartDatas.size(); i++) {
			ac[i][0] = acceptedChartDatas.get(i).get("timestamp").toString();
			ac[i][1] = acceptedChartDatas.get(i).get("count").toString();
		}
		Map<String, String[][]> map = new HashMap<String, String[][]>(2);
		map.put("all", all);
		map.put("ac", ac);
		return map;
	}
	
	@Override
	public List<Map<String, Integer>> getProblemAcceptedCounts(String uid) {
		return solutionDao.selectProblemAcceptedCountsByUserId(uid);
	}

}
