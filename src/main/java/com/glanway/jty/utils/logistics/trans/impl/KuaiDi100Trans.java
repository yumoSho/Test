package com.glanway.jty.utils.logistics.trans.impl;

import com.glanway.jty.utils.logistics.base.DomainRespBean;
import com.glanway.jty.utils.logistics.base.QueryTxnRequest;
import com.glanway.jty.utils.logistics.base.QueryTxnResult;
import com.glanway.jty.utils.logistics.bean.KuaiDi100RequestBean;
import com.glanway.jty.utils.logistics.bean.KuaiDi100Result;
import com.glanway.jty.utils.logistics.common.KuaiDi100Config;
import com.glanway.jty.utils.logistics.common.KuaiDi100Constants;
import com.glanway.jty.utils.logistics.trans.ILogisticsTrans;
import com.glanway.jty.utils.logistics.util.KuaiDi100Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;

/** 
* @文件名: KuaiDi100Trans.java
* @功能描述: 快递100业务处理类
* @author SunF
* @date 2016年4月19日 上午11:05:15 
*  
*/
public class KuaiDi100Trans implements ILogisticsTrans{
	Logger logger = Logger.getLogger(KuaiDi100Trans.class);

	
	/** 
	* @功能描述: 物流信息查询
	* @param queryTxnRequest
	* @return       
	*/
	public QueryTxnResult queryRouteInfo(QueryTxnRequest queryTxnRequest){
		logger.info("KuaiDi100Trans.queryRouteInfo : 开始查询物流信息 - " + queryTxnRequest);
		if(null == queryTxnRequest){
			return returnFailResult("检测到用于请求物流地址的数据源为空！");
		}
		
		KuaiDi100Util kdUtil = new KuaiDi100Util();
		//转换数据，填充默认信息
		KuaiDi100RequestBean requestBean =  kdUtil.createRequestBean(queryTxnRequest);
		String trackingNumber = requestBean.getNu();
		String companyCode = requestBean.getCom();
		if(StringUtils.isEmpty(companyCode)){
			return returnFailResult("检测到快递公司代码为空！");
		}
		if(StringUtils.isEmpty(trackingNumber)){
			return returnFailResult("检测到要查询的快递单号为空！");
		}
		
		//数据准备
		String id = KuaiDi100Config.getConfigValue(KuaiDi100Constants.PARAM_KEY_KUAIDI100_REQUEST_ID);
		String customerId = KuaiDi100Config.getConfigValue(KuaiDi100Constants.PARAM_KEY_KUAIDI100_CUSTOMER_ID);
		String url = KuaiDi100Config.getConfigValue(KuaiDi100Constants.PARAM_KEY_KUAIDI100_REQUEST_URL);
		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(url)){
			return returnFailResult("获取授权信息或请求地址失败！");
		}
		requestBean.setId(id);
		
	/*	//请求参数列表
		String[] requestParam = KuaiDi100Config.getTxnArguments(KuaiDi100Constants.KUAIDI100_REQUEST);
		if(null == requestParam ){
			return returnFailResult("请求参数列表为空！");
		}*/
		
		//生成HTTP请求信息字符串
		HashMap<String, String> params = kdUtil.generateRequestParamMsg(requestBean,customerId);
		
		//通讯请求
		String queryResultStr = kdUtil.queryLogisticsInfo(url, params);
		if(StringUtils.isEmpty(queryResultStr)){
			return returnFailResult("物流查询响应信息为空！");
		}
		//解析响应内容
		KuaiDi100Result resultBean = kdUtil.parseResponseMsg(queryResultStr);
		if(null == resultBean){
			return returnFailResult("解析响应报文出错！");
		}
		
		DomainRespBean respBean = kdUtil.convertResponseBean(resultBean);
		respBean.setCompanyCode(companyCode);
		respBean.setTrackingNumber(trackingNumber);
		
		QueryTxnResult txnResult = new QueryTxnResult();
		txnResult.setResponseBean(respBean);
		txnResult.setQueryResultStr(queryResultStr);
		txnResult.setResultCode(QueryTxnResult.RETURN_SUCCESS);
		txnResult.setRespTime(new Date());
		logger.info("KuaiDi100Trans.queryRouteInfo : 查询物流信息已完成 ");
		return txnResult;
	}
	
	/** 
	* @功能描述: 返回错误信息
	* @param errMessage
	* @return       
	*/
	public QueryTxnResult returnFailResult(String errMessage){
		logger.error("KuaiDi100Trans.returnFailResult : "+errMessage);
		QueryTxnResult txnResult = new QueryTxnResult();
		txnResult.setErrMessage(errMessage);
		txnResult.setRespTime(new Date());
		return txnResult;
	}
	
}
