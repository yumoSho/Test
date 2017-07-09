package com.glanway.jty.utils.logistics.common;

/** 
* @文件名: ShunFengConstants.java
* @功能描述: 顺丰快递常量类 
* @author SunF
* @date 2016年4月20日 上午9:26:17 
*  
*/
public class ShunFengConstants extends DomainConstants{
	
	/** 
	* @Fields HTTP_PARMA_XML : http-post参数 - xml报文
	*/ 
	public static final String HTTP_POST_PARMA_XML = "xml";
	/** 
	* @Fields HTTP_PARMA_VERIFYCODE : http-post参数 - verifyCode验证码
	*/ 
	public static final String HTTP_POST_PARMA_VERIFYCODE = "verifyCode";
	
	/** 
	* @Fields XML_HEAD : xml报文头部
	*/ 
	public static final String XML_HEAD = "<?xml version='1.0' encoding='UTF-8'?>";
	
	/******************************接入编码****************************************/
	/** 
	* @Fields INTERFACE_NO_BSPDEVELOP : 接入编码 - 路由信息查询
	*/ 
	public static final String INTERFACE_NO_BSPDEVELOP = "BSPdevelop";
	
	/******************************配置文件****************************************/
	/** 
	* @Fields PARAM_KEY_SHUNFENG_REQUEST_URL : 路由查询请求地址
	*/ 
	public static final String PARAM_KEY_SHUNFENG_REQUEST_URL = "SHUNFENG_REQUEST_URL";
	/** 
	* @Fields PARAM_KEY_SHUNFENG_CDKLEY : 客户秘钥
	*/ 
	public static final String PARAM_KEY_SHUNFENG_CHECKWORD = "SHUNFENG_CHECKWORD";
	
	/******************************报文组成参数****************************************/
	/** 
	* @Fields DEFAULT_REQUEST_TRACKING_TYPE : 报文组成参数 - 根据运单号查询
	*/ 
	public static final String DEFAULT_REQUEST_TRACKING_TYPE = "1";
	
	/** 
	* @Fields DEFAULT_REQUEST_METHOD_TYPE : 报文组成参数 - 标准路由查询
	*/ 
	public static final String DEFAULT_REQUEST_METHOD_TYPE = "1";
	
	
	
	
}
