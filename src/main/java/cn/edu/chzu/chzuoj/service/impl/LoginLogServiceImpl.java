package cn.edu.chzu.chzuoj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.dao.LoginLogDao;
import cn.edu.chzu.chzuoj.entity.LoginLog;
import cn.edu.chzu.chzuoj.service.LoginLogService;

/**
 * 
 * @author dzj0821
 *
 */
@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {
	@Autowired
	private LoginLogDao loginLogDao;

	@Override
	public List<LoginLog> getLoginLogs(String uid) {
		return loginLogDao.selectLoginLogByUserId(uid);
	}
	
}
