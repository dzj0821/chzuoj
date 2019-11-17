package cn.edu.chzu.chzuoj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.dao.NewDao;
import cn.edu.chzu.chzuoj.entity.New;
import cn.edu.chzu.chzuoj.service.NewService;

/**
 * 
 * @author dzj0821
 *
 */
@Service("newService")
public class NewServiceImpl implements NewService {
	@Autowired
	private NewDao newDao;

	@Override
	public List<New> getNews() {
		return newDao.selectNews();
	}

}
