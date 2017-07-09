package com.glanway.jty.service.platform;


import com.glanway.jty.entity.platform.FlowBanner;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.utils.UserAgent;

import java.util.List;

public interface FlowBannerService extends BaseService<FlowBanner, Long> {

	String DELETED = "deleted";
	/** 
	* @功能描述: 批量删除轮播图信息
	* @param ids
	* @return       
	*/
	boolean batchDeleteFlowBanner(Long[] ids);
	
	
	
	/**ua
	 * 根据银行编号，查询其所有轮播图信息记录
	 * @param flowBanner 需要指定所属银行和所属页面
	 * @param ua 用户请求设备数据
	 * @return
	 */
	public List<FlowBanner> findAllByBankId(FlowBanner flowBanner, UserAgent ua);

	public List<FlowBanner> findAllByBankIdMobile(FlowBanner flowBanner);
//	/**
//	 * 查询是否与页面名称、平台、银行相同的记录
//	 * @param flowBanner
//	 * @return
//	 */
//	public Map<String, Boolean> checkIsPageNameAndPlatNameAndBankExists(FlowBanner flowBanner);
}
