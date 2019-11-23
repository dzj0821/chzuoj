package cn.edu.chzu.chzuoj.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

/**
 * solution表的类
 * name属性在数据库中的名称为nick
 * @author dzj0821
 *
 */
public interface SolutionDao {

	/**
	 * 选取所有用户（当uid不为null时，仅选取uid对应的用户）的提交时间与各时间的提交数（当accepted为true时，仅获取正确提交数）
	 * @param uid 如果为null则选取所有用户，不为null则选取指定用户
	 * @param accepted 如果为false则选取所有提交数据，否则仅选取正确提交数据
	 * @return map参数timestamp为提交时间，参数count为提交数
	 */
	@SelectProvider(type = UserDaoProvider.class, method = "selectSubmitCountsAndTimestamps")
	public List<Map<String, Object>> selectSubmitCountsAndTimestamps(String uid, boolean accepted);
	
	/**
	 * 获得每分钟的平均提交数
	 * @return
	 */
	@Select("SELECT AVG(speed) FROM (SELECT AVG(1) AS speed, judgetime FROM solution WHERE result > 3 AND judgetime > DATE_SUB(NOW(), INTERVAL 1 HOUR) GROUP BY (judgetime DIV 60 * 60) ORDER BY speed) AS `table`")
	public Integer selectSubmitSpeedPerMin();
	
	/**
	 * 更新某用户的提交的所有姓名
	 * @param uid 用户id
	 * @param name 新姓名
	 */
	@Update("UPDATE `solution` SET `nick` = #{name} WHERE `user_id` = #{uid}")
	public void updateNameByUserId(@Param("uid") String uid, @Param("uid") String name);
	
	/**
	 * 获取用户的总通过题目数
	 * @param uid 用户id
	 * @return
	 */
	@Select("SELECT COUNT(DISTINCT `problem_id`) AS `ac` FROM `solution` WHERE `user_id` = #{uid} AND `result` = 4")
	public int selectSolvedProblemsCountByUserId(@Param("uid") String uid);
	
	/**
	 * 获取用户的总提交数
	 * @param uid 用户id
	 * @return
	 */
	@Select("SELECT COUNT(`solution_id`) AS `submit` FROM `solution` WHERE `user_id` = #{uid} AND `problem_id` > 0")
	public int selectSubmitsCountByUserId(@Param("uid") String uid);
	
	/**
	 * 获取用户各类提交结果的数量
	 * @param uid
	 * @return 每行一个map，map中type对应JudgeResult的value，count代表对应类型的提交数
	 */
	@Select("SELECT `result` AS `type`, count(1) AS `count` FROM `solution`"
			+ "WHERE `user_id` = #{uid} AND `result` >= 4 GROUP BY `result` ORDER BY `result`")
	public List<Map<String, Integer>> selectSubmitResultCountsByUserId(@Param("uid") String uid);
	
	/**
	 * 获取用户解答正确的题目编号和正确次数
	 * @param uid
	 * @return 每行一个map，map中id为解答正确的题目编号，count为该题的正确提交次数
	 */
	@Select("SELECT `problem_id` AS `id`, COUNT(1) AS `count` FROM `solution`"
			+ "WHERE `user_id` = #{uid} AND result = 4 GROUP BY `problem_id` ORDER BY `problem_id` ASC")
	public List<Map<String, Integer>> selectProblemAcceptedCountsByUserId(@Param("uid") String uid);
	
	class UserDaoProvider {
		public String selectSubmitCountsAndTimestamps(String uid, boolean accepted) {
			return new SQL() {{
				SELECT("UNIX_TIMESTAMP(DATE(in_date)) * 1000 AS `timestamp`", "COUNT(1) AS `count`");
				if (uid == null) {
					//仅选择前8000条数据，防止数据过多查询变慢
					String solution = new SQL() {{
						SELECT("*");
						FROM("solution");
						ORDER_BY("`solution_id` DESC");
						LIMIT(8000);
					}}.toString();
					FROM("(" + solution + ") AS `solution`");
				} else {
					FROM("solution");
				}
				if (accepted) {
					WHERE("result = 4");
				} else {
					WHERE("result < 13");
				}
				if (uid != null) {
					AND();
					WHERE("user_id = #{uid}");
				}
				GROUP_BY("timestamp");
				ORDER_BY("timestamp DESC");
				LIMIT(200);
			}}.toString();
		}
	}
}
