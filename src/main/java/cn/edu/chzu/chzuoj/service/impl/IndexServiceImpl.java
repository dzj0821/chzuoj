package cn.edu.chzu.chzuoj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.dao.SolutionDao;
import cn.edu.chzu.chzuoj.entity.ChartData;
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
	public String[][] getAllChartData() {
		//获得图表数据
		List<ChartData> allChartDatas = solutionDao.selectAllChartData();
		String[][] all = new String[allChartDatas.size()][2];
		for (int i = 0; i < allChartDatas.size(); i++) {
			all[i][0] = allChartDatas.get(i).getTimestamp();
			all[i][1] = allChartDatas.get(i).getCount();
		}
		return all;
	}

	@Override
	public String[][] getAcceptedChartData() {
		// 获得图表数据
		List<ChartData> acceptedChartDatas = solutionDao.selectAcceptedChartData();
		String[][] ac = new String[acceptedChartDatas.size()][2];
		for (int i = 0; i < acceptedChartDatas.size(); i++) {
			ac[i][0] = acceptedChartDatas.get(i).getTimestamp();
			ac[i][1] = acceptedChartDatas.get(i).getCount();
		}
		return ac;
	}

	@Override
	public String getSubmitSpeed(String[][] allChartData, boolean administrator) {
		if (administrator) {
			Integer speedPerMin = solutionDao.selectSubmitSpeedPerMin();
			if (speedPerMin == null) {
				return "0";
			}
			return speedPerMin.toString();
		}
		if (allChartData.length > 0 && allChartData[0][1] != null) {
			return allChartData[0][1];
		}
		return "0";
	}

	@Override
	public String getSubmitSpeedUnit(boolean administrator) {
		if (administrator) {
			return "min";
		}
		return "day";
	}

}
