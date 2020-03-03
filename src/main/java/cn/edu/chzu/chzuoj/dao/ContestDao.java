package cn.edu.chzu.chzuoj.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import cn.edu.chzu.chzuoj.entity.Contest;
import cn.edu.chzu.chzuoj.util.CharacterBoolean;

/**
 * 
 * @author dzj0821
 *
 */
public interface ContestDao {
	/**
	 * 获取比赛的开始时间
	 * 
	 * @param contestId
	 * @return
	 */
	@Select("SELECT MIN(start_time) FROM contest WHERE start_time <= NOW() AND end_time >= NOW() AND contest_id >= #{contestId}")
	public Timestamp selectContestStartTime(@Param("contestId") int contestId);

	/**
	 * 获取所有包含problemId题目的竞赛id、竞赛标题以及题目所处的位置
	 * 
	 * @param problemId
	 * @return 竞赛id的键为id，竞赛标题为title，位置为num
	 */
	@Select("SELECT contest.`contest_id` AS id, contest.`title` AS title, contest_problem.num AS num "
			+ "FROM `contest_problem`, `contest` "
			+ "WHERE contest.contest_id = contest_problem.contest_id AND `problem_id` = #{problemId} and defunct = 'N' "
			+ "ORDER BY `num`")
	public List<Map<String, Object>> selectContestsByProblemId(@Param("problemId") int problemId);

	/**
	 * 根据竞赛id获取竞赛信息
	 * 
	 * @param id        竞赛id
	 * @param selectAll 如果此项为true，则不会获取被禁用和未开始的竞赛
	 * @return
	 */
	@SelectProvider(type = ContestDaoProvider.class, method = "selectContestById")
	@Results(id = "contestMap", value = {
			@Result(column = "contest_id", property = "id", id = true),
			@Result(column = "private", property = "privateFlag")
	})
	public Contest selectContestById(@Param("id") int id, boolean selectAll);
	
	/**
	 * 获取符合条件的竞赛数量
	 * @param search 在标题中搜索的关键字
	 * @param userId 用户id
	 * @param selectAll 是否从全部竞赛中选择，如果为false则忽略被禁用的竞赛
	 * @param onlySelectRelated 是否只从与用户有关的竞赛中选择
	 * @return
	 */
	@SelectProvider(type = ContestDaoProvider.class, method = "selectContestsCount")
	public int selectContestsCount(@Param("search") String search, @Param("userId") String userId, boolean selectAll,
			boolean onlySelectRelated);

	/**
	 * 获取符合条件的竞赛
	 * @param search 在标题中搜索的关键字
	 * @param userId 用户id
	 * @param selectAll 是否从全部竞赛中选择，如果为false则忽略被禁用的竞赛
	 * @param onlySelectRelated 是否只从与用户有关的竞赛中选择
	 * @param offset 选择竞赛的偏移量
	 * @param limit 选择的竞赛数量
	 * @return
	 */
	@SelectProvider(type = ContestDaoProvider.class, method = "selectContests")
	@ResultMap("contestMap")
	public List<Contest> selectContests(@Param("search") String search, @Param("userId") String userId,
			boolean selectAll, boolean onlySelectRelated, int offset, int limit);

	class ContestDaoProvider {
		private static final String FLITER_SQL = String.format("`defunct` = '%c' AND `start_time` <= NOW()", CharacterBoolean.FALSE);
		private static final String FLITER_SQL_EXCLUDE_TIME = String.format("defunct = '%c'", CharacterBoolean.FALSE);
		private static final String SEARCH_SQL = "title LIKE CONCAT('%', #{search}), '%'";
		private static final String RELATED_SQL = "(contest_id IN ("
				+ "SELECT contest_id FROM class_contest WHERE class_id IN ("
				+ "SELECT class_id FROM class_relationship WHERE user_id = #{userId} AND relationship = 'joined') "
				+ "OR class_id IN ("
				+ "SELECT class_id FROM classes WHERE manager_id = #{userId})) "
				+ "OR user_id= #{userId})";

		public String selectContestById(int id, boolean selectAll) {
			return new SQL() {{
					SELECT("*");
					FROM("contest");
					WHERE("contest_id = #{id}");
					if (!selectAll) {
						WHERE(FLITER_SQL);
					}
			}}.toString();
		}

		public String selectContestsCount(String search, String userId, boolean selectAll, boolean onlySelectRelated) {
			return new SQL() {{
				SELECT("COUNT(*)");
				FROM("contest");
				if (search != null) {
					WHERE(SEARCH_SQL);
				}
				if (selectAll) {
					WHERE(FLITER_SQL_EXCLUDE_TIME);
				}
				if (userId != null && onlySelectRelated) {
					WHERE(RELATED_SQL);
				}
			}}.toString();
		}

		public String selectContests(String search, String userId, boolean selectAll, boolean onlySelectRelated,
				int offset, int limit) {
			return new SQL() {{
				SELECT("*");
				FROM("contest");
				if (search != null) {
					WHERE(SEARCH_SQL);
				}
				if (selectAll) {
					WHERE(FLITER_SQL_EXCLUDE_TIME);
				}
				if (userId != null && onlySelectRelated) {
					WHERE(RELATED_SQL);
				}
			}}.toString();
		}
	}
}
