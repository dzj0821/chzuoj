package cn.edu.chzu.chzuoj.service;

import java.util.List;

import cn.edu.chzu.chzuoj.entity.New;

/**
 * 
 * @author dzj0821
 *
 */
public interface NewService {
	/**
	 * 获取所有未删除的新闻
	 * @return
	 */
	public List<New> getNews();
}
