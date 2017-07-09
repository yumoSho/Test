package com.glanway.jty.dao.perm;

import java.util.List;
import java.util.Map;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.perm.Module;

/**
 * @author wuqi
 *
 */
public interface ModuleDao extends BaseDao<Module, Long> {

	List<Module> getBaseModule(Map<String, Object> paramMap);
	List<Module> getModuleByName(String name);
	int countPageByModuleId(Long moduleId);
}
