package cn.edu.chzu.chzuoj.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.dao.SolutionDao;
import cn.edu.chzu.chzuoj.service.IndexService;

/**
 * 
 * @author dzj0821
 *
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {
	@Autowired
	private SolutionDao solutionDao;

	@Override
	public Map<String, String> getSubmitSpeedAndUnit(String[][] allChartData, boolean administrator) {
		Map<String, String> map = new HashMap<String, String>(2);
		if (administrator) {
			map.put("speedUnit", "min");
		} else {
			map.put("speedUnit", "day");
		}
		if (administrator) {
			Integer speedPerMin = solutionDao.selectSubmitSpeedPerMin();
			if (speedPerMin == null) {
				map.put("speed", "0");
			} else {
				map.put("speed", speedPerMin.toString());
			}
			return map;
		}
		if (allChartData.length > 0 && allChartData[0][1] != null) {
			map.put("speed", allChartData[0][1]);
		} else {
			map.put("speed", "0");
		}
		return map;
	}
}
