package cn.edu.chzu.chzuoj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cn.edu.chzu.chzuoj.pojo.ChartData;

/**
 * 
 * @author dzj0821
 *
 */
public interface SolutionDao {

	/**
	 * 筛选出近期的提交记录，用于主页图标显示
	 * @return
	 */
	@Select("SELECT UNIX_TIMESTAMP(DATE(in_date)) * 1000 AS `timestamp`, COUNT(1) AS `count` FROM (SELECT * FROM SOLUTION ORDER BY solution_id DESC LIMIT 8000) AS solution WHERE result < 13 GROUP BY `timestamp` ORDER BY `timestamp` DESC LIMIT 200")
	public List<ChartData> selectAllChartData();
	
	/**
	 * 筛选出近期的正确提交记录，用于主页图标显示
	 * @return
	 */
	@Select("SELECT UNIX_TIMESTAMP(DATE(in_date)) * 1000 AS `timestamp`, COUNT(1) AS `count` FROM (SELECT * FROM SOLUTION ORDER BY solution_id DESC LIMIT 8000) AS solution WHERE result = 4 GROUP BY `timestamp` ORDER BY `timestamp` DESC LIMIT 200")
	public List<ChartData> selectAcceptedChartData();
	
	/**
	 * 获得每分钟的平均提交数
	 * @return
	 */
	@Select("SELECT AVG(speed) FROM (SELECT AVG(1) AS speed, judgetime FROM solution WHERE result > 3 AND judgetime > DATE_SUB(NOW(), INTERVAL 1 HOUR) GROUP BY (judgetime DIV 60 * 60) ORDER BY speed) AS `table`")
	public Integer selectSubmitSpeedPerMin();
}
