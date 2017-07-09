package com.glanway.jty.utils.logistics;

import com.glanway.jty.utils.logistics.base.DomainRespBean;
import com.glanway.jty.utils.logistics.base.QueryTxnRequest;
import com.glanway.jty.utils.logistics.base.QueryTxnResult;
import com.glanway.jty.utils.logistics.trans.ILogisticsTrans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 
* @文件名: LogisticsExternalService.java
* @功能描述: 物流接口 - 对外部服务类
* @author SunF
* @date 2016年4月21日 下午2:30:01 
*  
*/
public class LogisticsExternalService {
	private LogisticsExternalService(){}

	/** 
	* @功能描述: 路由信息查询
	* @param queryTxnRequest
	* @return       
	*/
	private static QueryTxnResult getRouteInfo(QueryTxnRequest queryTxnRequest) {
		String channel = queryTxnRequest.getChannel();
		ILogisticsTrans trans = LogisticsTransFactory.getInstance(channel);
		QueryTxnResult result = trans.queryRouteInfo(queryTxnRequest);
		return result;
	}

	/** 
	* @功能描述: 查询路由信息
	* @param channel 渠道号
	* @param trackingNumber 运单号
	* @param companyCode 快递公司号
	* @return       
	*/
	public static QueryTxnResult getRouteInfo(String channel, String trackingNumber, String companyCode) {
		QueryTxnRequest queryTxnRequest = new QueryTxnRequest();
		queryTxnRequest.setChannel(channel);
		queryTxnRequest.setCompanyCode(companyCode);
		queryTxnRequest.setTrackingNumber(trackingNumber);
		QueryTxnResult result = getRouteInfo(queryTxnRequest);
		return result;
	}

	/** 
	* @功能描述: 查询路由信息
	* @param channel 渠道号
	* @param trackingNumber 运单号
	* @return       
	*/
	public static QueryTxnResult getRouteInfo(String channel, String trackingNumber) {
		QueryTxnRequest queryTxnRequest = new QueryTxnRequest();
		queryTxnRequest.setChannel(channel);
		queryTxnRequest.setTrackingNumber(trackingNumber);
		QueryTxnResult result = getRouteInfo(queryTxnRequest);
		return result;
	}

	/** 
	* @功能描述: 查询路由信息列表（推荐）
	* @param channel 渠道号
	* @param trackingNumber 运单号
	* @param companyCode 快递公司号
	* @return       
	*/
	public static List<RouteVO> getRouteVoList(String channel, String trackingNumber, String companyCode) {
		QueryTxnResult result = getRouteInfo(channel, trackingNumber, companyCode);
		return parseRouteInfoMap(result);
	}

	/** 
	* @功能描述: 查询路由信息列表（推荐）
	* @param channel 渠道号
	* @param trackingNumber 运单号
	* @return       
	*/
	public static List<RouteVO> getRouteVoList(String channel, String trackingNumber) {
		QueryTxnResult result = getRouteInfo(channel, trackingNumber);
		return parseRouteInfoMap(result);
	}

	/** 
	* @功能描述: 根据通用响应结果返回路由信息列表
	* @param queryTxnResult 
	* @return       
	*/
	public static List<RouteVO> parseRouteInfoMap(QueryTxnResult queryTxnResult){
		if(null == queryTxnResult){
			return null;
		}
		
		DomainRespBean bean = queryTxnResult.getResponseBean();
		LinkedHashMap<String, String> routeInfoMap = bean.getRouteInfoMap();
		List<RouteVO> routeList = new ArrayList<RouteVO>();
		for (Map.Entry<String, String> entry : routeInfoMap.entrySet()) {
			String time = entry.getKey();
			String context = entry.getValue();
			RouteVO rvo = new RouteVO();
			rvo.setTime(time);
			rvo.setContext(context);
			routeList.add(rvo);
		}
		
		return routeList;
	}
	
}
