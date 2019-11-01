package cn.edu.chzu.chzuoj.dao;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * @author dzj0821
 *
 */
public interface ContestDao {
	/**
	 * 获取比赛的开始时间
	 * @param contestId
	 * @return
	 */
	@Select("SELECT MIN(start_time) FROM contest WHERE start_time <= NOW() AND end_time >= NOW() AND contest_id >= #{contestId}")
	public Timestamp selectContestStartTime(@Param("contestId") int contestId);
}
