package cn.edu.chzu.chzuoj.entity;

import cn.edu.chzu.chzuoj.util.LocaleUtil;

/**
 * 判题结果的枚举类
 * @author dzj0821
 *
 */
public enum JudgeResult {
	/**
	 * 等待
	 */
	PENDING(0, "pending"),
	/**
	 * 等待重判
	 */
	PENDING_REJUDGE(1, "pending-rejudge"),
	/**
	 * 编译中
	 */
	COMPILING(2, "compiling"),
	/**
	 * 运行并评判
	 */
	RUNNING_AND_JUDGING(3, "running-and-judging"),
	/**
	 * 正确
	 */
	ACCEPTED(4, "accepted"),
	/**
	 * 格式错误
	 */
	PRESENTATION_ERROR(5, "presentation-error"),
	/**
	 * 答案错误
	 */
	WRONG_ANSWER(6, "wrong-answer"),
	/**
	 * 时间超限
	 */
	TIME_LIMIT_EXCEEDED(7, "time-limit-exceeded"),
	/**
	 * 内存超限
	 */
	MEMORY_LIMIT_EXCEEDED(8, "memory-limit-exceeded"),
	/**
	 * 输出超限
	 */
	OUTPUT_LIMIT_EXCEEDED(9, "output-limit-exceeded"),
	/**
	 * 运行错误
	 */
	RUNTIME_ERROR(10, "runtime-error"),
	/**
	 * 编译错误
	 */
	COMPLIE_ERROR(11, "compile-error"),
	/**
	 * 编译成功
	 */
	COMPLIE_SUCCESS(12, "compile-success"),
	/**
	 * 测试运行
	 */
	TEST_RUNNING(13, "test-running");
	
	
	private int value;
	private String key;
	private String message;
	
	public static JudgeResult get(int value) {
		for (JudgeResult judgeResult : JudgeResult.values()) {
			if (judgeResult.getValue() == value) {
				return judgeResult;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param value 类型对应的数字编号
	 * @param key   类型对应的字符串在国际化中的key
	 */
	private JudgeResult(int value, String key) {
		this.value = value;
		this.key = key;
		this.message = LocaleUtil.getMessage(key);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
