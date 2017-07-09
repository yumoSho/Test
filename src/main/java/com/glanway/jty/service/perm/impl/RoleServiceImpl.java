package com.glanway.jty.service.perm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.glanway.jty.utils.AdminUserUtil;
import com.glanway.jty.dao.perm.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.glanway.jty.dao.perm.PageDao;
import com.glanway.jty.entity.perm.Role;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.perm.RoleService;

/**
 * 对T_role表的操作
 *
 * @author zhuhaodong 2015/9/30
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

    private RoleDao rolesDao;
    @Autowired
    private AdminUserUtil adminUserUtil;

    @Autowired
    public void setRoleDao(@Qualifier("roleDao") RoleDao rolesDao) {
        this.rolesDao = rolesDao;
        setCrudDao(rolesDao);
    }

    @Autowired
    private PageDao pageDao;

    @Override
    @Transactional
    public void saveRole(Role role, String pageIds) {
        Long userId = Long.valueOf(adminUserUtil.getCurrentUser());
        role.setDeleted(false);
        role.setCreatedBy(userId);
        rolesDao.save(role);
        if (null != pageIds && !pageIds.isEmpty()){
            this.saveRolePage(role.getId(), pageIds, userId);
        }
    }


    @Override
    public List<Role> getBaseRole(String name) {
        return rolesDao.getBaseRole(name);
    }

    @Override
    public Map<String, Boolean> checkIsRoleExists(String name) {
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        List<Role> roleList = getBaseRole(name);
        if (roleList.size() > 0) {
            result.put("isExists", true);
        } else {
            result.put("isExists", false);
        }
        return result;
    }

    @Override
    public Role getRoleById(Long id) {
        Role role = rolesDao.find(id);
        return role;
    }

    @Override
    public Map<String, Boolean> hasAnnotherRole(String[] roleIdAndName) {
        Map<String, Boolean> result = new HashMap<String, Boolean>();

        // 通过角色名称查找
        List<Role> roleList = getBaseRole(roleIdAndName[1]);

        Iterator<Role> it = roleList.iterator();
        Role role = it.hasNext() ? it.next() : null;

        // 如果角色不存在或是相同 对象则合法
        result.put("isExists", null != role && !role.getId().equals(Long.valueOf(roleIdAndName[0])));
        return result;
    }


    @Override
    public void updateRole(Role role, String pageIds) {
        Long userId = Long.valueOf(adminUserUtil.getCurrentUser());
        role.setLastModifiedBy(userId);
        update(role);
        if (null != pageIds && !pageIds.isEmpty()) {
            rolesDao.deleteByRoleId(role.getId());  //删除该角色原有的页面
            this.saveRolePage(role.getId(), pageIds, userId);   //保存该角色所拥有的页面
        }
    }

    @Override
    public List<Role> findAll() {
        return rolesDao.findAll();
    }

    /**
     * 删除角色的同时
     * 删除 角色-页面对应关系
     * 删除 用户 - 角色 对应关系
     *
     * @param ids
     */
    @Override
    public void remove(Long[] ids) {
        for (Long id : ids) {
            this.delete(id);
            rolesDao.deleteByRoleId(id);  //删除角色 页面对应关系
            rolesDao.deleteUserRoleByRoleId(id);   //删除角色 用户对应关系
        }
    }

    /**
     * 保存某个角色的所有页面
     *
     * @param roleId
     * @param pageIds
     */
    private void saveRolePage(Long roleId, String pageIds, Long userId) {
        String[] ids;
        if (!StringUtils.hasText(pageIds)) {
            ids = new String[0];
        }
        if (null != pageIds) {
            ids = pageIds.split(",");
            for (int i = 0; i < ids.length; i++) {
                Long pageId = Long.valueOf(ids[i]);
                Map<String, Object> roleToPage = new HashMap();
                roleToPage.put("id", null);
                roleToPage.put("roleId", roleId);
                roleToPage.put("pageId", pageId);
                roleToPage.put("deleted", 0);
                roleToPage.put("createdDate", new Date());
                roleToPage.put("createdBy", userId);
                rolesDao.saveRoleToPage(roleToPage);
            }
        }
    }

}
