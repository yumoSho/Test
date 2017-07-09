package com.glanway.jty.service.perm;


import com.glanway.jty.entity.perm.Module;

import java.util.List;

/**
 * Created by ASUS on 2014/12/25.
 */
public interface PermissionService {
    List<Module> findPermissionByUserId(Long userId);

    List<Module> getPermissionFromCache();

    List<Integer> getCurrentAuditPerm();
}
