package com.glanway.jty.service.platform;

import com.glanway.jty.entity.platform.Advertisement;
import com.glanway.jty.service.BaseService;

import java.util.List;

public interface AdvertisementService extends BaseService<Advertisement, Long>{

	String DELETED = "deleted";
	/** 
	* @功能描述: 批量删除广告信息
	* @param ids
	* @return       
	*/
	boolean batchDeleteAdvertisement(Long[] ids);
	
	/**
	 * 查询指定银行下的所有广告记录
	 * @param
	 * @return
	 */
	List<Advertisement> findAllByBankId(Advertisement advertisement);
}
