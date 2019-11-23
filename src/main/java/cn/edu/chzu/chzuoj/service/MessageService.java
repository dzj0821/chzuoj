package cn.edu.chzu.chzuoj.service;

/**
 * 
 * @author dzj0821
 *
 */
public interface MessageService {
	
	/**
	 * 获取滚动公告
	 * @return 滚动公告
	 */
	public String getMarqueeMessage();
	/**
	 * 获取页尾内容
	 * @return
	 */
	public String getFooterMessage();
}
