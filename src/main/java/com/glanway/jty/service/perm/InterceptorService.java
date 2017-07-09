package com.glanway.jty.service.perm;

import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.entity.perm.Page;

import java.util.List;

public interface InterceptorService {
	List<Module> getModuleByUserId(Long userId);
	List<Page> getPagesByModuleId(Long moduleId);

    Long  getCasheShopId();
}
