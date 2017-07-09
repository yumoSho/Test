package com.glanway.jty.service.perm;

import java.util.List;
import java.util.Map;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.service.BaseService;

public interface ModuleService extends BaseService<Module, Long> {

	List<Module> getBaseModule(Map<String, Object> paramMap);
	Map<String, Boolean> checkIsModuleExists(String name);
	Map<String, Boolean> hasAnnotherModule(String[] hasAnnotherModule);

	Message remove(Long[] ids);
}
