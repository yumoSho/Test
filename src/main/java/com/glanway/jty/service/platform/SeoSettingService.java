package com.glanway.jty.service.platform;


import com.glanway.jty.entity.platform.SeoSetting;
import com.glanway.jty.service.BaseService;

import java.util.List;
import java.util.Map;

public interface SeoSettingService extends BaseService<SeoSetting, Long> {

	String DELETED = "deleted";
	/** 
	* @功能描述: 批量删除SEO信息
	* @param ids
	* @return       
	*/
	boolean batchDeleteSeoSetting(Long[] ids);
	
	
	
	/**
	 * 根据银行编号，查询其所有SEO信息记录
	 * @param bankId
	 * @return
	 */
	public List<SeoSetting> findAllByBankId(Long bankId);

	/**
	 * 查询是否与页面名称、平台、银行相同的记录
	 * @param seoSetting
	 * @return
	 */
	public Map<String, Boolean> checkIsPageNameAndPlatNameAndBankExists(SeoSetting seoSetting);

	List<SeoSetting> findByList(Map param);
}
