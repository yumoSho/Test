package com.glanway.jty.dao.perm;

import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.entity.perm.Page;

import java.util.List;

public interface InterceptorDao {
	List<Module> findModuleByUserId(Long userId);
	List<Page> findPageByModuleId(long moduleId);
}
