package cn.edu.chzu.chzuoj.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author dzj0821
 *
 */
public interface SolutionService {
	/**
	 * 获得全部用户（如果uid不为null则获取指定用户）的提交信息，以前端可以处理的图表数据返回
	 * @param uid 如果获取指定用户的提交数据
	 * @return map中参数为all代表全部提交的图表信息，参数为ac代表正确提交的图表信息
	 */
	public Map<String, String[][]> getSolutionChartDatas(String uid);
	
	/**
	 * 获取通过的题目编号与对应的正确提交数
	 * @param uid
	 * @return 每行一个map，map中id为解答正确的题目编号，count为该题的正确提交次数
	 */
	public List<Map<String, Integer>> getProblemAcceptedCounts(String uid);
}
