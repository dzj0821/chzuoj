package cn.edu.chzu.chzuoj.entity;

import java.sql.Timestamp;

/**
 * users表实体类
 * 
 * @author dzj0821
 *
 */
public class User {
	/**
	 * 对应数据库字段user_id
	 */
	private String id;
	private String email;
	private Integer submit;
	private Integer solved;
	private Character defunct;
	private String ip;
	/**
	 * 对应数据库字段accesstime
	 */
	private Timestamp lastLoginTime;
	private Integer volume;
	private Integer language;
	private String password;
	/**
	 * 对应数据库字段reg_time
	 */
	private Timestamp registerTime;
	/**
	 * 对应数据库字段nick
	 */
	private String name;
	/**
	 * 对应数据库字段school
	 */
	private String clazz;

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", submit=" + submit + ", solved=" + solved + ", defunct="
				+ defunct + ", ip=" + ip + ", lastLoginTime=" + lastLoginTime + ", volume=" + volume + ", language="
				+ language + ", password=" + password + ", registerTime=" + registerTime + ", name=" + name + ", clazz="
				+ clazz + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSubmit() {
		return submit;
	}

	public void setSubmit(Integer submit) {
		this.submit = submit;
	}

	public Integer getSolved() {
		return solved;
	}

	public void setSolved(Integer solved) {
		this.solved = solved;
	}

	public Character getDefunct() {
		return defunct;
	}

	public void setDefunct(Character defunct) {
		this.defunct = defunct;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
