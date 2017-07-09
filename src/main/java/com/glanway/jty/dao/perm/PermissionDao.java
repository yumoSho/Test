package com.glanway.jty.dao.perm;


import com.glanway.jty.entity.perm.Module;

import java.util.List;

/**
 * Created by ASUS on 2014/12/25.
 */
public interface PermissionDao {

    List<Module> findPermissionByUserId(Long userId);
}
