/*
 * Copyright (c) 2005, 2014  glanway.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.glanway.jty.service.perm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.glanway.jty.entity.perm.User;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseService;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;

/**
 * @author wentan
 * 
 */
public interface UserService extends BaseService<User, Long> {

	void saveAdminUser(User adminUser, String roleIds);

	void updateUser(User user, String roleIds);

	Map<String, Boolean> checkIsUserExists(String username);

	Boolean isAnotherUserExists(String username, String userId);
	
	List<User> getBaseUser(Map<String, Object> map);

	Boolean logout(HttpServletRequest request);

	Long[] findUserToRoleIds(Long userId);

    void delUsers(Long[] ids) throws CustomException;

	@Deprecated
	Page<User> listFindPage(Filters roleFilter, Filters filters, Pageable pageable);

	void updateLoginTime(Long userId,String ip);


}
