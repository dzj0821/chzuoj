package cn.edu.chzu.chzuoj.exception;

/**
 * 用于service执行过程中失败所抛出的异常
 * 
 * @author dzj0821
 *
 */
public class ExecuteFailedException extends Exception {
	private static final long serialVersionUID = -1899279389581733287L;
	private String key = null;
	private String[] args = null;

	/**
	 * 
	 * @param key  信息在国际化中的key
	 * @param args 信息的参数
	 */
	public ExecuteFailedException(String key, String[] args) {
		this.key = key;
		this.args = args;
	}

	public ExecuteFailedException(String key) {
		this(key, null);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}
}
