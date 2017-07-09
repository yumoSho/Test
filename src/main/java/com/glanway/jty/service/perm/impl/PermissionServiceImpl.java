package com.glanway.jty.service.perm.impl;

import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.perm.PermissionDao;
import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.entity.perm.Role;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.service.perm.PermissionService;
import com.glanway.jty.service.perm.UserService;
import com.glanway.jty.utils.AdminUserUtil;
import com.glanway.jty.utils.ArrayUtil;
import com.glanway.jty.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2014/12/25.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private AdminUserUtil adminUserUtil;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private UserService userService;

    @Override
    public List<Module> findPermissionByUserId(Long userId){
        List<Module> modules = new ArrayList<Module>();
        if(null==userId){
            return modules;
        }

        modules = permissionDao.findPermissionByUserId(userId);
        return modules;
    }

    @Override
    public List<Module> getPermissionFromCache(){

        String userId = adminUserUtil.getCurrentUser();

        List<Module> modules = (List<Module>) cacheUtil.getCacheValue(Constants.AUTHORIZE_PREFIX_PERMISSION+userId);
        return modules;
    }

    @Override
    public List<Integer> getCurrentAuditPerm() {
        List<Integer> auditPerm = new ArrayList<Integer>();
        String userId = adminUserUtil.getCurrentUser();
        User currentUser=null;
        if(null!=userId){
            currentUser    = userService.find(Long.parseLong(userId));
        }

        if(null==currentUser){
            return auditPerm;
        }
        List<Role> roles = currentUser.getRoles();
        if(!CollectionUtils.isEmpty(roles)){
        for(Role role: roles){
            String permissionsStr = role.getAuditPerm();
            String[] permissions = ArrayUtil.stringToArray(permissionsStr);
            for(String permission: permissions){
                int permissionInt = Integer.parseInt(permission);
                if(!auditPerm.contains(permissionInt)){
                    auditPerm.add(permissionInt);
                }
            }
        }
        }
        return auditPerm;
    }
}
