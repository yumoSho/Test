package com.glanway.jty.utils.logistics.common;

import org.apache.commons.lang3.StringUtils;

/** 
* @文件名: KuaiDi100Config.java
* @功能描述: 快递100配置文件
* @author SunF
* @date 2016年4月19日 上午10:18:57 
*  
*/
public class KuaiDi100Config {
	/** 
	* @Fields request_param_array : 快递100 - 请求信息参数列表
	* @说明： 请参考快递100请求参数列表，否则不准修改
	*//* 
	private static final String[] request_param_array =  new String[]{
			"id",
			"com",
			"nu",
			"valicode",
			"show",
			"muti",
			"order"
	};
	
	*//** 
	* @功能描述: 请求业务参数列表
	* @param txnCode 自定义业务代码
	* @return       
	*//*
	public static String[] getTxnArguments(String txnCode) {
		if(StringUtils.isEmpty(txnCode)){
			return null;
		}
		else if(KuaiDi100Constants.KUAIDI100_REQUEST.equals(txnCode)){
			//快递 - 查询请求
			return request_param_array;
		}
		else{
			return null;
		}
	}
	*/
	/** 
	* @功能描述: 查询配置文件信息
	* @param configKey
	* @return       
	*/
	public static String getConfigValue(String configKey){
		if(StringUtils.isEmpty(configKey)){
			return null;
		}
		return LogisticsConfig.getConfigValue(configKey);
	}
	
}
