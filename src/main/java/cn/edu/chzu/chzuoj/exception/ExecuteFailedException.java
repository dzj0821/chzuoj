package cn.edu.chzu.chzuoj.exception;

/**
 * 用于service执行过程中失败所抛出的异常
 * @author dzj0821
 *
 */
public class ExecuteFailedException extends Exception {
	private static final long serialVersionUID = -1899279389581733287L;
	
	/**
	 * 
	 * @param key 信息在国际化中的key
	 */
	public ExecuteFailedException(String key) {
		super(key);
	}

}
