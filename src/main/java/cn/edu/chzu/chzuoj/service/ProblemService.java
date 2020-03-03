package cn.edu.chzu.chzuoj.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.chzu.chzuoj.entity.Problem;
import cn.edu.chzu.chzuoj.exception.ExecuteFailedException;

/**
 * 
 * @author dzj0821
 *
 */
public interface ProblemService {
	/**
	 * 根据条件获得题目总页数
	 * @param search
	 * @param administrator
	 * @return
	 */
	public int getPageCount(String search, boolean administrator);
	
	/**
	 * 获得记住的页码，如果打开了新页码则更新记住的页码
	 * @param page 用户传递来的页码
	 * @param uid 用户id
	 * @param pageCount 此次题目列表的总页数
	 * @param search 是否在查询
	 * @return 应该显示的页码
	 */
	public int remeberPage(Integer page, String uid, int pageCount, boolean search);
	
	/**
	 * 根据条件查询题目
	 * @param search 搜索文字，在标题与来源中搜索
	 * @param administrator 是否是管理员
	 * @param page 第几页
	 * @return 
	 */
	public List<Problem> getProblems(String search, boolean administrator, int page);
	
	/**
	 * 获取题目列表中用户是否提交正确过
	 * @param uid 用户id
	 * @param problems 题目列表
	 * @return true代表对应下标的题目提交正确，false代表提交但是失败了，null代表未提交过
	 */
	public Boolean[] getProblemStatusByUserId(String uid, List<Problem> problems);
	
	/**
	 * 计算每个题目来源的背景色
	 * @param problems 题目列表
	 * @return 数组元素与题目对应，每个元素的列表对应来源列表，每个来源有名字和颜色两个属性，以name和theme为键存储
	 */
	public List<ArrayList<HashMap<String, String>>> getProblemSources(List<Problem> problems);
	
	/**
	 * 根据题目id获取题目信息
	 * @param id
	 * @param special 是否有特殊权限，有特殊权限可以获取被禁用题目和在竞赛中的题目的信息
	 * @return
	 */
	public Problem getProblem(int id, boolean special);
	
	/**
	 * 获取题目的来源名称数组
	 * @param problem
	 * @return
	 */
	public String[] getSourcesName(Problem problem);
	
	/**
	 * 获取竞赛中的题目信息
	 * @param cid 竞赛id
	 * @param num 题目在竞赛中的序号
	 * @param special 是否有特殊权限，没有特殊权限只能获取与用户有关班级的竞赛题目
	 * @param userId 用户id
	 * @return
	 * @throws ExecuteFailedException 如果用户无特殊权限，且与竞赛允许的班级之间没有联系，则抛出异常
	 */
	public Problem getContestProblem(int cid, int num, boolean special, String userId) throws ExecuteFailedException;
}
