package cn.edu.chzu.chzuoj.service;

import java.util.List;
import java.util.Map;

import cn.edu.chzu.chzuoj.entity.Contest;

/**
 * 
 * @author dzj0821
 *
 */
public interface ContestService {

	/**
	 * 获取题目在哪些竞赛中
	 * @param problemId 题目id
	 * @return 一个数组，每个元素为一个map，每个map中包含id（包含此题目的竞赛id），title（包含此题目的竞赛标题），num（题目在此竞赛中的序号，从0开始）
	 */
	public List<Map<String, Object>> whichContestsHaveTheProblem(int problemId);
	
	/**
	 * 根据竞赛id获取竞赛信息
	 * @param cid 竞赛id
	 * @param special 是否拥有特殊权限，有特殊权限可以获得未开始和被禁用的竞赛信息
	 * @return
	 */
	public Contest getContest(int cid, boolean special);
}
