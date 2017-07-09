package com.glanway.jty.service.perm;

import java.util.List;
import java.util.Map;

import com.glanway.jty.entity.perm.Role;
import com.glanway.jty.service.BaseService;

public interface  RoleService extends BaseService<Role, Long>{
	
	void saveRole(Role role, String pageIds);
	List<Role> getBaseRole(String name);
	Map<String, Boolean> checkIsRoleExists(String name);
	Map<String, Boolean> hasAnnotherRole(String[] roleIdAndName);
	Role getRoleById(Long id);
	void updateRole(Role role, String pageIds);

    @Override
    List<Role> findAll();

	void remove(Long[] ids);
}
