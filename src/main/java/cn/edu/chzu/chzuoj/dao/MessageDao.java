package cn.edu.chzu.chzuoj.dao;

import org.apache.ibatis.annotations.Select;

/**
 * 
 * @author dzj0821
 *
 */
public interface MessageDao {
	/**
	 * 获取滚动条公告
	 * @return
	 */
	@Select("SELECT text FROM `message` WHERE type = 'marquee'")
	public String selectMarqueeMessage();
}
