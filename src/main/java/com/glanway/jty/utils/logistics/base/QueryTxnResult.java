package com.glanway.jty.utils.logistics.base;

import java.util.Date;

/** 
* @文件名: QueryTxnResult.java
* @功能描述: 物流信息查询结果类 
* @author SunF
* @date 2016年4月19日 下午1:35:32 
*  
*/
public class QueryTxnResult {
	/** 
	* @Fields RETURN_SUCCESS : 处理结果返回码
	*/ 
	public static final String RETURN_SUCCESS = "0000";
	
	/** 
	* @Fields responseBean : 响应数据包装
	*/ 
	private DomainRespBean responseBean;
	
	/** 
	* @Fields queryResultStr : 响应结果原文
	*/ 
	private String queryResultStr;
	
	/** 
	* @Fields errMessage : 错误信息
	*/ 
	private String errMessage;
	
	/** 
	* @Fields resultCode : 响应结果  成功：0000
	*/ 
	private String resultCode;
	
	/** 
	* @Fields respTime : 响应时间
	*/ 
	private Date respTime;

	public String getQueryResultStr() {
		return queryResultStr;
	}

	public void setQueryResultStr(String queryResultStr) {
		this.queryResultStr = queryResultStr;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public Date getRespTime() {
		return respTime;
	}

	public void setRespTime(Date respTime) {
		this.respTime = respTime;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public DomainRespBean getResponseBean() {
		return responseBean;
	}

	public void setResponseBean(DomainRespBean responseBean) {
		this.responseBean = responseBean;
	}

	@Override
	public String toString() {
		return "QueryTxnResult [responseBean=" + responseBean + ", queryResultStr=" + queryResultStr + ", errMessage="
				+ errMessage + ", resultCode=" + resultCode + ", respTime=" + respTime + "]";
	}
	
}
