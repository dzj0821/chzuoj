package cn.edu.chzu.chzuoj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.chzu.chzuoj.dao.PrivilegeDao;
import cn.edu.chzu.chzuoj.service.PermissionService;

/**
 * 
 * @author dzj0821
 *
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PrivilegeDao privilegeDao;

	@Override
	public List<String> getPermissionStrings(String uid) {
		return privilegeDao.selectPermissionByUserId(uid);
	}

}
