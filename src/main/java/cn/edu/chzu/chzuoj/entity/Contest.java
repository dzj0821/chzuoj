package cn.edu.chzu.chzuoj.entity;

import java.sql.Timestamp;

/**
 * 
 * @author dzj0821
 *
 */
public class Contest {
	/**
	 * 竞赛id，字段名为contest_id
	 */
	private Integer id;
	/**
	 * 竞赛标题
	 */
	private String title;
	/**
	 * 竞赛开始时间
	 */
	private Timestamp startTime;
	/**
	 * 竞赛结束时间
	 */
	private Timestamp endTime;
	/**
	 * 竞赛是否禁用
	 */
	private Character defunct;
	/**
	 * 竞赛描述
	 */
	private String description;
	/**
	 * 竞赛是否私有，0代表公有，1代表私有，字段名为private
	 */
	private Integer privateFlag;
	/**
	 * 竞赛支持的语言，通过掩码表示
	 */
	private Integer langmask;
	/**
	 * 竞赛的密码
	 */
	private String password;
	/**
	 * 竞赛创建者的id
	 */
	private String userId;

	@Override
	public String toString() {
		return "Contest [id=" + id + ", title=" + title + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", defunct=" + defunct + ", description=" + description + ", privateFlag=" + privateFlag
				+ ", langmask=" + langmask + ", password=" + password + ", userId=" + userId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Character getDefunct() {
		return defunct;
	}

	public void setDefunct(Character defunct) {
		this.defunct = defunct;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrivateFlag() {
		return privateFlag;
	}

	public void setPrivateFlag(Integer privateFlag) {
		this.privateFlag = privateFlag;
	}

	public Integer getLangmask() {
		return langmask;
	}

	public void setLangmask(Integer langmask) {
		this.langmask = langmask;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
