/*
 * Copyright (c) 2005, 2014  glanway.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.glanway.jty.dao.perm;

import java.util.List;
import java.util.Map;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.perm.Role;
import com.glanway.jty.entity.perm.User;

/**
 * @author wentan
 *
 */
public interface UserDao extends BaseDao<User, Long> {

	String ROLE_FILTERS_PROP = "_role_filters";


	List<User> getBaseUser(Map<String, Object> paramMap);

	List<User> isAnotherUserExists(Map<String, Object> paramMap);

	void saveUserToRole(Map<String, Object> userToRole);

	Long[] getRoleIdsByUserId(Long userId);

	void deleteUserToRoleByUserId(Long userId);

    List<Role> findRoleByUser(Long userId);

	@Deprecated
	List<User> listFindMany(Map params);

	@Deprecated
	int  listCount(Map params);

	void updateLoginTime(Long userId,String ip);

	void deleteBatchByCustomerId(Long[] customerIds);

	Integer finUserType(Long id);

	List<User> findByCustomerId(Long customerId);
}
