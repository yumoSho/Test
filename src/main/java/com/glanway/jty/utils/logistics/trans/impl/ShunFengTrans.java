package com.glanway.jty.utils.logistics.trans.impl;

import com.glanway.jty.utils.logistics.base.DomainRespBean;
import com.glanway.jty.utils.logistics.base.QueryTxnRequest;
import com.glanway.jty.utils.logistics.base.QueryTxnResult;
import com.glanway.jty.utils.logistics.bean.ShunFengRouteRequestBean;
import com.glanway.jty.utils.logistics.bean.ShunFengRouteResponseBean;
import com.glanway.jty.utils.logistics.common.LogisticsConfig;
import com.glanway.jty.utils.logistics.common.ShunFengConstants;
import com.glanway.jty.utils.logistics.trans.ILogisticsTrans;
import com.glanway.jty.utils.logistics.util.ShunFengUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;

/** 
* @文件名: ShunFengTrans.java
* @功能描述: 顺丰快递业务处理类
* @author SunF
* @date 2016年4月20日 下午1:58:36 
*  
*/
public class ShunFengTrans implements ILogisticsTrans{
	Logger logger = Logger.getLogger(ShunFengTrans.class);

	@Override
	public QueryTxnResult queryRouteInfo(QueryTxnRequest queryTxnRequest) {
		logger.info("ShunFengTrans.queryRouteInfo - 开始查询物流路由信息 - " + queryTxnRequest);
		if(null == queryTxnRequest){
			return returnFailResult("检测到用于请求物流地址的数据源为空！");
		}
		
		ShunFengUtil sfUtil = new ShunFengUtil();
		//转换数据，添加默认信息
		ShunFengRouteRequestBean requestBean = sfUtil.createRequestBean(queryTxnRequest);
		String trackingNumber = requestBean.getTrackingNumber();
		if(StringUtils.isEmpty(trackingNumber)){
			return returnFailResult("检测到待查询号码为空！");
		}
		//数据准备
		String requestUrl = LogisticsConfig.getConfigValue(ShunFengConstants.PARAM_KEY_SHUNFENG_REQUEST_URL);
		String checkword = LogisticsConfig.getConfigValue(ShunFengConstants.PARAM_KEY_SHUNFENG_CHECKWORD);
		if(StringUtils.isEmpty(checkword) || StringUtils.isEmpty(requestUrl)){
			return returnFailResult("获取授权信息或请求地址失败！");
		}
		
		//组装xml报文
		String requestXML = sfUtil.generateRequestParamMsg(requestBean);
		//生成校验码
		String verifyCode = sfUtil.createVerifyCode(checkword, requestXML);
		//发送网关通信，获取响应消息
		String responseMsg = sfUtil.sendHttpPost(requestUrl, requestXML, verifyCode);
		//解析为顺丰快递响应数据模型
		ShunFengRouteResponseBean shunfengRespBean = sfUtil.parseResponseMsg(responseMsg);
		if(null == shunfengRespBean){
			return returnFailResult("解析网关响应报文错误！");
		}
		//转换为基础数据
		DomainRespBean respBean = sfUtil.convertResponseBean(shunfengRespBean);
		respBean.setTrackingNumber(trackingNumber); 
		
		QueryTxnResult txnResult = new QueryTxnResult();
		txnResult.setResponseBean(respBean);
		txnResult.setQueryResultStr(responseMsg);
		txnResult.setResultCode(QueryTxnResult.RETURN_SUCCESS);
		txnResult.setRespTime(new Date());
		logger.info("ShunFengTrans.queryRouteInfo - 查询物流路由信息已完成");
		return txnResult;
	}
	
	/** 
	* @功能描述: 返回错误信息
	* @param errMessage
	* @return       
	*/
	public QueryTxnResult returnFailResult(String errMessage){
		logger.error("ShunFengTrans.returnFailResult : " + errMessage);
		QueryTxnResult txnResult = new QueryTxnResult();
		txnResult.setErrMessage(errMessage);
		txnResult.setRespTime(new Date());
		return txnResult;
	}

}
