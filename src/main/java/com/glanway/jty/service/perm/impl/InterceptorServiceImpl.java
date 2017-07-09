package com.glanway.jty.service.perm.impl;

import com.glanway.jty.dao.perm.InterceptorDao;
import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.entity.perm.Page;
import com.glanway.jty.service.perm.InterceptorService;
import com.glanway.jty.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterceptorServiceImpl implements InterceptorService {
	
	@Autowired
	private InterceptorDao interceptorDao;
    @Autowired
    private CacheUtil cacheUtil;
	@Override
	public List<Module> getModuleByUserId(Long userId) {
		return interceptorDao.findModuleByUserId(userId);
	}

	@Override
	public List<Page> getPagesByModuleId(Long moduleId) {
		List<Page> pages = interceptorDao.findPageByModuleId(moduleId);
		return pages;
	}

    @Override
    public Long getCasheShopId() {
        return Long.parseLong("1");
    }
}
