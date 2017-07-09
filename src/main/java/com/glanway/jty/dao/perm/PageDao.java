package com.glanway.jty.dao.perm;

import java.util.List;
import java.util.Map;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.perm.Page;

/**
 * 页面管理Dao
 * date: 2015年9月29日 下午5:59:39 <br/>
 *
 * @author wuqi
 * @version 
 * @since JDK 1.7
 * @change Log:
 *************************************************
 */
public interface PageDao extends BaseDao<Page, Long> {
	
	String PAGE_FILTERS_PROP = "_page_filters";
	String MODULE_FILTERS_PROP = "_module_filters";
	
    List<Page> getBasePage(Map<String, Object> paramMap);
    List<Page> getPagesByRoleId(Long roleId);

    void delRolePageByPageId(Long pageId);
}
