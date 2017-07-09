package com.glanway.jty.service.logistics;

import com.glanway.jty.utils.logistics.RouteVO;
import com.glanway.jty.utils.logistics.base.QueryTxnResult;

import java.util.List;

/** 
* @文件名: LogisticsInfoService.java
* @功能描述: 物流信息查询
* @author SunF
* @date 2016年4月19日 上午9:29:03 
*  
*/
public interface LogisticsInfoService {
	
	/** 
	 * @功能描述:  根据运单号查询物流路由信息 
	 * @param channel 查询渠道号 在 DomainConstants.java中定义
	 * @param trackingNumber 运单号
	 * @return       
	 */
	@Deprecated
	QueryTxnResult getRouteInfo(String channel, String trackingNumber);
	
	/** 
	* @功能描述:  根据运单号查询物流路由信息 
	* @param channel 查询渠道号 在 DomainConstants.java中定义
	* @param trackingNumber 运单号
	* @param companyCode 待查询的公司号
	* @return       
	*/
	QueryTxnResult getRouteInfo(String channel, String trackingNumber, String companyCode);
	
	/** 
	* @功能描述: 根据运单号查询物流路由信息 
	* @param channel 查询渠道号 在 DomainConstants.java中定义
	* @param trackingNumber  运单号
	* @return       
	*/
	@Deprecated
	List<RouteVO> getRouteVoList(String channel, String trackingNumber);
	
	/** 
	* @功能描述: 根据运单号查询物流路由信息 
	* @param channel 查询渠道号 在 DomainConstants.java中定义
	* @param trackingNumber  运单号
	* @param companyCode 待查询的公司号
	* @return       
	*/
	List<RouteVO> getRouteVoList(String channel, String trackingNumber, String companyCode);
	

}
