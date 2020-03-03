package cn.edu.chzu.chzuoj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import cn.edu.chzu.chzuoj.entity.Problem;
import cn.edu.chzu.chzuoj.util.CharacterBoolean;

/**
 * 
 * @author dzj0821
 *
 */
public interface ProblemDao {
	/**
	 * 根据搜索内容和是否是管理员获得题目数量
	 * @param search 搜索内容
	 * @param selectAll 是否查询全部（如果为否，则不查询竞赛内的题目与被禁用的题目）
	 * @return 符合条件的题目数量
	 */
	@SelectProvider(type = ProblemDaoProvider.class, method = "selectProblemCount")
	public int selectProblemCount(@Param("search") String search, boolean selectAll);
	
	/**
	 * 获取题目
	 * @param search 搜索条件
	 * @param selectAll 是否查询全部（如果为否，则不查询竞赛内的题目与被禁用的题目）
	 * @param offset 筛选条件后，从第多少题开始
	 * @param limit 选取多少个题目
	 * @return
	 */
	@SelectProvider(type = ProblemDaoProvider.class, method = "selectProblems")
	@Results(id = "problemMap", value = {
			@Result(column = "problem_id", property = "id", id = true)
	})
	public List<Problem> selectProblems(@Param("search") String search, boolean selectAll, int offset, int limit);
	
	/**
	 * 获取题目信息
	 * @param id 题目id
	 * @param selectAll 是否查询全部（如果为否，则不查询竞赛内的题目与被禁用的题目）
	 * @return
	 */
	@SelectProvider(type = ProblemDaoProvider.class, method = "selectProblemById")
	@ResultMap("problemMap")
	public Problem selectProblemById(@Param("id") int id, boolean selectAll);
	
	/**
	 * 根据竞赛id与序号获取题目信息
	 * @param contestId 竞赛id
	 * @param num 序号
	 * @return
	 */
	@Select("SELECT * FROM `problem` WHERE `defunct` = 'N' AND `problem_id` IN ("
			+ "SELECT `problem_id` FROM `contest_problem` WHERE `contest_id` = #{contestId} AND `num` = #{num})")
	@ResultMap("problemMap")
	public Problem selectContestProblemByContestIdAndNum(@Param("contestId") int contestId, @Param("num") int num);
	
	class ProblemDaoProvider {
		//普通用户查询题目时的统一限制
		private static final String FLITER_SQL = String.format("(defunct = '%c' AND problem_id NOT IN ("
				+ "SELECT problem_id FROM contest AS c INNER JOIN contest_problem AS cp "
				+ "ON c.contest_id = cp.contest_id AND c.private = 1 AND c.defunct = '%c'))", 
				CharacterBoolean.FALSE, CharacterBoolean.FALSE);
		private static final String SEARCH_SQL = "(title like CONCAT('%', #{search}, '%') "
				+ "OR source like CONCAT('%', #{search}, '%'))";
		
		public String selectProblemCount(String search, boolean selectAll) {
			return new SQL() {{
				SELECT("COUNT(*)");
				FROM("problem");
				if (!selectAll) {
					//去掉禁用的和在竞赛中的题目
					WHERE(FLITER_SQL);
				}
				if (search != null) {
					//搜索的情况，在所有题目的标题与来源中搜索
					WHERE(SEARCH_SQL);
				}
			}}.toString();
		}
		
		public String selectProblems(String search, boolean selectAll, int offset, int limit) {
			return new SQL() {{
				SELECT("problem_id", "title", "source", "submit", "accepted");
				FROM("problem");
				if (!selectAll) {
					WHERE(FLITER_SQL);
				}
				if (search != null) {
					WHERE(SEARCH_SQL);
				}
				ORDER_BY("problem_id");
				OFFSET(offset);
				LIMIT(limit);
			}}.toString();
		}
		
		public String selectProblemById(int id, boolean selectAll) {
			return new SQL() {{
				SELECT("*");
				FROM("problem");
				WHERE("problem_id = #{id}");
				if (!selectAll) {
					WHERE(FLITER_SQL);
				}
			}}.toString();
		}
	}
}
