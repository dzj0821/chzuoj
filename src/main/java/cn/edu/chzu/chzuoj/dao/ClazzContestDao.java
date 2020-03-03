package cn.edu.chzu.chzuoj.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * @author dzj0821
 *
 */
public interface ClazzContestDao {
	/**
	 * 判断contestId所代表的竞赛与userId所代表的用户是否有联系，联系包括：用户加入了竞赛所属的班级或用户是竞赛所属班级的管理者
	 * @param contestId
	 * @param userId
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM class_contest "
			+ "WHERE class_id IN (SELECT class_id FROM class_relationship WHERE user_id = #{userId} AND relationship = 'joined') "
			+ "OR class_id IN (SELECT class_id FROM classes WHERE manager_id = #{userId}) "
			+ "AND contest_id = #{contestId}")
	public boolean isRelatedContest(@Param("contestId") int contestId, @Param("userId") String userId);
}
