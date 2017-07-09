package com.glanway.jty.dao.perm;

import java.util.List;
import java.util.Map;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.perm.Role;

public interface RoleDao extends BaseDao<Role, Long> {
	
	void saveRoleToPage(Map<String, Object> roleToPage);
	List<Role> getBaseRole(String name);
	void deleteByRoleId(Long roleId);
	void deleteUserRoleByRoleId(Long roleId);
    List<Role>  findAll();
}
