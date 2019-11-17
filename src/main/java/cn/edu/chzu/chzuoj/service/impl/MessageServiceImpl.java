package cn.edu.chzu.chzuoj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.dao.MessageDao;
import cn.edu.chzu.chzuoj.service.MessageService;

/**
 * 
 * @author dzj0821
 *
 */
@Service("messageSerivce")
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDao messageDao;

	@Override
	public String getMarqueeMessage() {
		return messageDao.selectMarqueeMessage();
	}

}
