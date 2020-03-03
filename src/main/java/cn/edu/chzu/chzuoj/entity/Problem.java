package cn.edu.chzu.chzuoj.entity;

import java.sql.Timestamp;

/**
 * 
 * @author dzj0821
 *
 */
public class Problem {
	/**
	 * 对应数据库problem_id
	 */
	private Integer id;
	/**
	 * 题目标题
	 */
	private String title;
	/**
	 * 题目描述
	 */
	private String description;
	/**
	 * 输入描述
	 */
	private String input;
	/**
	 * 输出描述
	 */
	private String output;
	/**
	 * 样例输入
	 */
	private String sampleInput;
	/**
	 * 样例输出
	 */
	private String sampleOutput;
	/**
	 * special judge简称，如果为'1'则不使用输入输出文件进行判题，具体判题见http://www.hustoj.com/?p=1281
	 */
	private Character spj;
	/**
	 * 题目提示
	 */
	private String hint;
	/**
	 * 题目来源
	 */
	private String source;
	/**
	 * 录入时间
	 */
	private Timestamp inDate;
	/**
	 * 时间限制
	 */
	private Integer timeLimit;
	/**
	 * 内存限制
	 */
	private Integer memoryLimit;
	/**
	 * 题目是否禁用
	 */
	private Character defunct;
	/**
	 * 通过数量
	 */
	private Integer accepted;
	/**
	 * 提交数量
	 */
	private Integer submit;
	/**
	 * 未知用途
	 */
	private Integer solved;
	/**
	 * 上传者用户id
	 */
	private String userId;

	@Override
	public String toString() {
		return "Problem [id=" + id + ", title=" + title + ", description=" + description + ", input=" + input
				+ ", output=" + output + ", sampleInput=" + sampleInput + ", sampleOutput=" + sampleOutput + ", spj="
				+ spj + ", hint=" + hint + ", source=" + source + ", inDate=" + inDate + ", timeLimit=" + timeLimit
				+ ", memoryLimit=" + memoryLimit + ", defunct=" + defunct + ", accepted=" + accepted + ", submit="
				+ submit + ", solved=" + solved + ", userId=" + userId + "]";
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getSampleInput() {
		return sampleInput;
	}

	public void setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
	}

	public String getSampleOutput() {
		return sampleOutput;
	}

	public void setSampleOutput(String sampleOutput) {
		this.sampleOutput = sampleOutput;
	}

	public Character getSpj() {
		return spj;
	}

	public void setSpj(Character spj) {
		this.spj = spj;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Timestamp getInDate() {
		return inDate;
	}

	public void setInDate(Timestamp inDate) {
		this.inDate = inDate;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(Integer memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public Character getDefunct() {
		return defunct;
	}

	public void setDefunct(Character defunct) {
		this.defunct = defunct;
	}

	public Integer getAccepted() {
		return accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
