package cn.edu.chzu.chzuoj.entity;

/**
 * 代表班级与竞赛之间关系的实体
 * @author dzj0821
 *
 */
public class ClazzContest {
	private Integer id;
	/**
	 * 班级id，字段名为class_id
	 */
	private Integer clazzId;
	private Integer contestId;

	@Override
	public String toString() {
		return "ClassContest [id=" + id + ", clazzId=" + clazzId + ", contestId=" + contestId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClazzId() {
		return clazzId;
	}

	public void setClazzId(Integer clazzId) {
		this.clazzId = clazzId;
	}

	public Integer getContestId() {
		return contestId;
	}

	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}

}
