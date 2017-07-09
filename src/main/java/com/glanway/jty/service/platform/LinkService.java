package com.glanway.jty.service.platform;


import com.glanway.jty.entity.platform.Link;
import com.glanway.jty.service.BaseService;

import java.util.List;

public interface LinkService extends BaseService<Link, Long> {

	String DELETED = "deleted";
	/** 
	* @功能描述: 批量删除链接信息
	* @param ids
	* @return       
	*/
	boolean batchDeleteLink(Long[] ids);
	
	/**
	 * 根据查询链接信息
	 * @return
	 */
	List<Link> findAllByBankId();
}
