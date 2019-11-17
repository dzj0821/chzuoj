package cn.edu.chzu.chzuoj.entity;

import java.sql.Timestamp;

/**
 * news表的实体类
 * @author dzj0821
 *
 */
public class New {
	private Integer id;
	private User user;
	private String title;
	private String content;
	private Timestamp time;
	private Integer importance;
	private Character defunct;

	@Override
	public String toString() {
		return "New [id=" + id + ", user=" + user + ", title=" + title + ", content=" + content + ", time=" + time
				+ ", importance=" + importance + ", defunct=" + defunct + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getImportance() {
		return importance;
	}

	public void setImportance(Integer importance) {
		this.importance = importance;
	}

	public Character getDefunct() {
		return defunct;
	}

	public void setDefunct(Character defunct) {
		this.defunct = defunct;
	}
}
