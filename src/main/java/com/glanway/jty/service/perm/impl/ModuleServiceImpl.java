package com.glanway.jty.service.perm.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.perm.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glanway.jty.dao.perm.ModuleDao;
import com.google.common.collect.Maps;
import org.springframework.transaction.annotation.Transactional;

@Service("adminModuleService")
@Transactional
public class ModuleServiceImpl extends BaseServiceImpl<Module, Long> implements ModuleService {

	@Autowired
	public ModuleDao adminModuleDao;
	
	@Autowired
	public void setAdminModuleDao(ModuleDao adminModuleDao){
		this.adminModuleDao = adminModuleDao;
		setCrudDao(adminModuleDao);
	}

	@Override
	public List<Module> getBaseModule(Map<String, Object> paramMap) {
		return adminModuleDao.getBaseModule(paramMap);
	}

	public List<Module> getModuleByName(String name){
		return adminModuleDao.getModuleByName(name);
	}
	
	@Override
	public Map<String, Boolean> checkIsModuleExists(String name) {
		Map<String, Boolean> model = Maps.newHashMap();
		List<Module> moduleList = getModuleByName(name);
		if (moduleList.size() > 0) {
			model.put("isExists", true);
		} else {
			model.put("isExists", false);
		}
		return model;
	}

	@Override
	public Map<String, Boolean> hasAnnotherModule(String[] hasAnnotherModule) {
		Map<String, Boolean> result = Maps.newHashMap();
		List<Module> moduleList = getModuleByName(hasAnnotherModule[1]);
		Iterator<Module> it = moduleList.iterator();
		Module module = it.hasNext() ? it.next() : null;
		result.put("isExists", null != module && !module.getId().equals(Long.valueOf(hasAnnotherModule[0])));
		return result;
	}

	@Override
	public Message remove(Long[] ids)  {
		for(Long id : ids){
			int pageCount = adminModuleDao.countPageByModuleId(id);
			if(pageCount > 0){
				Module module = adminModuleDao.find(id);
				throw new CustomException("删除失败,【" + module.getName() + "】已有子页面！");
			}
			this.delete(id);
		}
		return Message.success();
	}
}
