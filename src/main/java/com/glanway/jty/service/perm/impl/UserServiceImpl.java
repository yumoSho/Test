/*
 * Copyright (c) 2005, 2014  glanway.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.glanway.jty.service.perm.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.dao.perm.UserDao;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.perm.UserService;
import com.glanway.jty.utils.CipherUtil;
import com.glanway.jty.utils.PassWordSaltUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author wentan
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    private UserDao userDao;
    @Autowired
    private PassWordSaltUtil passWordSaltUtil;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        setCrudDao(userDao);
    }

    @Override
    public void saveAdminUser(User user, String roleIds) {
        user.setDeleted(false);
        user.setCreatedDate(passWordSaltUtil.getFormatDate(new Date()));
        user.setPassword(passWordSaltUtil.passWordSalt(user.getPassword(), user.getCreatedDate()));
        user.setUserType(User.TYPE_HG);   //添加用户只能是红果
        userDao.save(user);
        saveUserToRoles(user, roleIds);
    }

    /**
     * 保存用户权限信息
     *
     * @param user
     * @param roleIds
     */
    private void saveUserToRoles(User user, String roleIds) {
        if (!StringUtils.hasText(roleIds) || null == user || 1 > user.getId()) {
            return;
        }
        String[] ids = roleIds.split(",");
        Map<String, Object> userToRole = Maps.newHashMap();
        for (String roleId : ids) {
            userToRole.put("userId", user.getId());
            userToRole.put("roleId", Long.parseLong(roleId));
            userToRole.put("deleted", false);
            userDao.saveUserToRole(userToRole);
        }
    }

    @Override
    public Map<String, Boolean> checkIsUserExists(String username) {
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userName", username);
        paramMap.put("deleted", Boolean.FALSE);
        List<User> users = getBaseUser(paramMap);
        if (users.size() > 0) {
            result.put("isExists", true);
        } else {
            result.put("isExists", false);
        }
        return result;
    }

    public List<User> getBaseUser(Map<String, Object> paramMap) {
        paramMap.put("deleted",Boolean.FALSE);
        return userDao.getBaseUser(paramMap);
    }

    @Override
    public void updateUser(User user, String roleIds) {
        if (StringUtils.isEmpty(user)) {
            return;
        }
        User user1 = find(user.getId());
        if (!CipherUtil.validatePassword(user1.getPassword(), user.getPassword()) && StringUtils.hasLength(user.getPassword())) {
            user.setPassword(passWordSaltUtil.passWordSalt(user.getPassword(), user1.getCreatedDate()));
        }else{
            user.setPassword(null);
        }
        update(user);
        if (!StringUtils.hasText(roleIds)) {
            return;
        }
        Long userId = user.getId();
        userDao.deleteUserToRoleByUserId(userId);
        saveUserToRoles(user, roleIds);
    }

    @Override
    public Boolean isAnotherUserExists(String username, String userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userName", username);
        paramMap.put("deleted", Boolean.FALSE);
        List<User> users = getBaseUser(paramMap);
        Iterator<User> it = users.iterator();
        User user = it.hasNext() ? it.next() : null;
        return (null == user || user.getId().equals(Long.valueOf(userId)));
    }

    @Override
    public Boolean logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null != session) {
            session.invalidate();
            return true;
        }
        return false;
    }

    @Override
    public Long[] findUserToRoleIds(Long userId) {
        Long[] roleIds = userDao.getRoleIdsByUserId(userId);
        return roleIds;
    }


    @Override
    public void delUsers(Long[] ids) {
        for (Long id : ids) {
            Integer  userType = userDao.finUserType(id);
            if(id == getCurrentUserId()){
                throw new CustomException("您不能删除自己");
            }
            if(null != userType && userType > 1) {
                throw new CustomException("非平台用户,不能删除");
            }
            this.delete(id);
            userDao.deleteUserToRoleByUserId(id);
        }
    }

    /**
     * 鼎商原有的方法，现在废置，等确认完全无用后，将删除
     * @param roleFilter
     * @param filters
     * @param pageable
     * @return
     */
    @Override
    @Deprecated
    public Page<User> listFindPage(Filters roleFilter, Filters filters, Pageable pageable) {
        roleFilter = new IterateNamingTransformFilters(roleFilter);
        filters = new IterateNamingTransformFilters(filters);
        Map<String, Object> paramsMap = createParamsMap();
        if (null != roleFilter) {
            paramsMap.put(UserDao.ROLE_FILTERS_PROP, roleFilter);
        }
        if (null != filters) {
            paramsMap.put(BaseDao.FILTERS_PROP, filters);
        }
        if (null != pageable) {
            paramsMap.put(UserDao.OFFSET_PROP, pageable.getOffset());
            paramsMap.put(UserDao.MAX_RESULTS_PROP, pageable.getPageSize());

            Sort sort = pageable.getSort();
            if (null != sort) {
                paramsMap.put(UserDao.SORT_PROP, new IterateNamingTransformSort(sort));
            }
        }

        int total = userDao.listCount(paramsMap);
        List<User> data = total > 0 ? userDao.listFindMany(paramsMap) : Collections.<User>emptyList();

        return new SimplePage<User>(pageable, data, total);
    }

    @Override
    public void updateLoginTime(Long userId,String ip) {
        if (null == userId) return;
        userDao.updateLoginTime(userId,ip);
    }


}
