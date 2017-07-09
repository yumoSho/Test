package com.glanway.jty.service.perm;

import java.util.List;
import java.util.Map;

import com.glanway.jty.entity.perm.Page;
import com.glanway.jty.service.BaseService;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Pageable;

/**
 *********************************************** 
 * 页面管理service层
 * date: 2015年9月30日 下午1:21:17 <br/>
 *
 * @author wuqi
 * @version 
 * @since JDK 1.7
 * @change Log:
 ************************************************* 
 */
public interface PageService extends BaseService<Page, Long> {
	
    List<Page> getBasePage(Map<String, Object> paramMap);

    com.glanway.gone.spring.bind.domain.Page<Page> findPage(
			Filters filters,
			Filters moduleFilters,
			Pageable pageable
	);

	boolean deleteAdminPage(Long id);
	
	void savePage(Page adminPage);
    
	Map<String, Boolean> checkIsPageExists(String name);
	
	List<Page> getPagesByRoleId(Long roleId);

	void remove(Long[] ids);
}
