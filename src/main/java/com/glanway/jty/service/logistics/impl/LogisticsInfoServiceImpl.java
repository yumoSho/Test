package com.glanway.jty.service.logistics.impl;

import com.glanway.jty.service.logistics.LogisticsInfoService;
import com.glanway.jty.utils.logistics.LogisticsExternalService;
import com.glanway.jty.utils.logistics.RouteVO;
import com.glanway.jty.utils.logistics.base.QueryTxnResult;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
* @文件名: LogisticsInfoServiceImpl.java
* @功能描述: 物流信息查询接口实现类
* @author SunF
* @date 2016年4月19日 下午3:31:11 
*  
*/
@Service("logisticsInfoService")
public class LogisticsInfoServiceImpl implements LogisticsInfoService{

	@Override
	public QueryTxnResult getRouteInfo(String channel, String trackingNumber, String companyCode) {
		return LogisticsExternalService.getRouteInfo(channel, trackingNumber, companyCode);
	}

	@Override
	public QueryTxnResult getRouteInfo(String channel, String trackingNumber) {
		return LogisticsExternalService.getRouteInfo(channel, trackingNumber);
	}

	@Override
	public List<RouteVO> getRouteVoList(String channel, String trackingNumber) {
		return LogisticsExternalService.getRouteVoList(channel, trackingNumber);
	}

	@Override
	public List<RouteVO> getRouteVoList(String channel, String trackingNumber, String companyCode) {
		return LogisticsExternalService.getRouteVoList(channel, trackingNumber, companyCode);
	}

}
