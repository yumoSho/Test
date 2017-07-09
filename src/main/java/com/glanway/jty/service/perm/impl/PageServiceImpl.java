package com.glanway.jty.service.perm.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glanway.jty.utils.AdminUserUtil;
import com.glanway.jty.dao.perm.PageDao;
import com.glanway.jty.entity.perm.Page;
import com.glanway.jty.service.perm.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.gone.dao.CrudDao;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import org.springframework.transaction.annotation.Transactional;

/**
 *********************************************** 
 * 页面管理service实现类
 * date: 2015年9月30日 下午1:23:31 <br/>
 *
 * @author wuqi
 * @version 
 * @since JDK 1.7
 * @change Log:
 ************************************************* 
 */
@Service
@Transactional
public class PageServiceImpl extends BaseServiceImpl<Page, Long> implements PageService {

	public PageDao pageDao;

	@Autowired
	private AdminUserUtil adminUserUtil;
	@Autowired
	public void setpageDao(PageDao pageDao){
		this.pageDao = pageDao;
		setCrudDao(pageDao);
	}

	/**
	 * 获取页面
	 */
	@Override
	public List<Page> getBasePage(Map<String, Object> paramMap) {
		return pageDao.getBasePage(paramMap);
	}
	
	/**
	 * 分页查询并且统计
	 */
	@Override
    public com.glanway.gone.spring.bind.domain.Page<Page> findPage(
            Filters filters,
            Filters moduleFilters,
            Pageable pageable
    ) {
        filters = new Filters(filters);
        moduleFilters = new Filters(moduleFilters);

        Map<String, Object> paramsMap = createParamsMap();
        if (null != filters) {
            paramsMap.put(PageDao.PAGE_FILTERS_PROP, filters);
        }
        if (null != moduleFilters) {
            paramsMap.put(PageDao.MODULE_FILTERS_PROP, moduleFilters);
        }
        if (null != pageable) {
            paramsMap.put(CrudDao.OFFSET_PROP, pageable.getOffset());
            paramsMap.put(CrudDao.MAX_RESULTS_PROP, pageable.getPageSize());

            Sort sort = pageable.getSort();
            if (null != sort) {
                paramsMap.put(CrudDao.SORT_PROP, sort);
            }
        }

        int total = count(paramsMap);
        List<Page> data = total > 0 ? findMany(paramsMap) : Collections.<Page>emptyList();

        return new SimplePage<Page>(pageable, data, total);
    }

	/**
	 * 删除信息
	 * 
	 */
	@Override
	public boolean  deleteAdminPage(Long id) {
		delete(id);
		return true;
	}

	/**
	 * 新增页面
	 */
	@Override
	public void savePage(Page adminPage) {
		adminPage.setCreatedDate(new Date(System.currentTimeMillis()));
		adminPage.setCreatedBy(Long.valueOf(adminUserUtil.getCurrentUser()));
		save(adminPage);
	}

	/**
	 * 检查重名
	 */
	@Override
	public Map<String, Boolean> checkIsPageExists(String name) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deleted", false);
		paramMap.put("name", name);
		List<Page> pages = getBasePage(paramMap);
		if (pages.size()>0){
			result.put("isExists", true);
		} else {
			result.put("isExists", false);
		}
		return result;
	}

	@Override
	public List<Page> getPagesByRoleId(Long roleId) {
		return pageDao.getPagesByRoleId(roleId);
		
	}

	/**
	 * 删除页面的同时，删除页面-角色对应关系
	 * @param ids
     */
	@Override
	public void remove(Long[] ids) {
		for (Long id : ids) {
			this.delete(id);
			pageDao.delRolePageByPageId(id);
		}
	}
}
