package cn.edu.chzu.chzuoj.service;

import java.util.List;

/**
 * 
 * @author dzj0821
 *
 */
public interface PermissionService {
	/**
	 * 获取用户名对应的权限名列表
	 * @param uid 需要查询的用户名
	 * @return 权限名称列表
	 */
	public List<String> getPermissionStrings(String uid);
}
