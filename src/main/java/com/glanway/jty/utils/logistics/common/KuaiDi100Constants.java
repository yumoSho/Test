package com.glanway.jty.utils.logistics.common;

/** 
* @文件名: KuaiDi100Constants.java
* @功能描述: 快递100常量类
* @author SunF
* @date 2016年4月19日 上午9:57:29 
*  
*/
public class KuaiDi100Constants extends DomainConstants{
	
	/*******************************请求信息*********************************/
	/** 
	* @Fields REQUEST_ORDER_DESC : 请求信息 - 排序 - 按时间由新到旧排列
	*/ 
	public static final String REQUEST_ORDER_DESC = "desc";
	/** 
	* @Fields REQUEST_ORDER_ASC :  请求信息 - 排序 - 按时间由旧到新排列
	*/ 
	public static final String REQUEST_ORDER_ASC = "asc";
	
	
	/** 
	* @Fields REQUEST_SHOW_TYPE_JSON : 请求信息 - 返回类型 - JSON(默认值)
	*/ 
	public static final String REQUEST_SHOW_TYPE_JSON = "0";
	/** 
	* @Fields REQUEST_SHOW_TYPE_XML :  请求信息 - 返回类型 - XML
	*/ 
	public static final String REQUEST_SHOW_TYPE_XML = "1";
	/** 
	* @Fields REQUEST_SHOW_TYPE_HTML :	请求信息 - 返回类型 - HTML
	*/ 
	public static final String REQUEST_SHOW_TYPE_HTML = "2";
	/** 
	* @Fields REQUEST_SHOW_TYPE_TEXT :	请求信息 - 返回类型 - TEXT
	*/ 
	public static final String REQUEST_SHOW_TYPE_TEXT = "3";
	
	
	/** 
	* @Fields REQUEST_MUTI_ONE :	请求信息 - 返回信息数量 - 一条
	*/ 
	public static final String REQUEST_MUTI_ONE = "0";
	/** 
	* @Fields REQUEST_MUTI_MANY :	请求信息 - 返回信息数量 - 多条
	*/ 
	public static final String REQUEST_MUTI_MANY = "1";
	
	
	/*******************************响应信息********************************************/
	/** 
	* @Fields RESPONSE_STATE_0 :	响应信息 - 快递单状态	- 在途，即货物处于运输过程中；
	*/ 
	public static final String RESPONSE_STATE_0 = "0";
	/** 
	* @Fields RESPONSE_STATE_1 :	响应信息 - 快递单状态	- 揽件，货物已由快递公司揽收
	* 并且产生了第一条跟踪信息；
	*/ 
	public static final String RESPONSE_STATE_1 = "1";
	/** 
	* @Fields RESPONSE_STATE_2 :	响应信息 - 快递单状态	- 疑难，货物寄送过程出了问题；
	*/ 
	public static final String RESPONSE_STATE_2 = "2";
	/** 
	* @Fields RESPONSE_STATE_3 :	响应信息 - 快递单状态	- 签收，收件人已签收；
	*/ 
	public static final String RESPONSE_STATE_3 = "3";
	/** 
	* @Fields RESPONSE_STATE_4 :	响应信息 - 快递单状态	- 退签，即货物由于用户拒签、
	* 超区等原因退回，而且发件人已经签收；
	*/ 
	public static final String RESPONSE_STATE_4 = "4";
	/** 
	* @Fields RESPONSE_STATE_5 :	响应信息 - 快递单状态	- 派件，即快递正在进行同城派件；
	*/ 
	public static final String RESPONSE_STATE_5 = "5";
	/** 
	* @Fields RESPONSE_STATE_6 :	响应信息 - 快递单状态	- 退回，货物正处于退回发件人的途中；
	*/ 
	public static final String RESPONSE_STATE_6 = "6";
	
	
	/** 
	* @Fields RESPONSE_STATUS_NO_RESULT :	响应信息	- 查询结果 - 物流单暂无结果
	*/ 
	public static final String RESPONSE_STATUS_NO_RESULT = "0";
	/** 
	* @Fields RESPONSE_STATUS_SUCCESS :		响应信息	- 查询结果 - 查询成功
	*/ 
	public static final String RESPONSE_STATUS_SUCCESS = "1";
	/** 
	* @Fields RESPONSE_STATUS_ERROR :		响应信息	- 查询结果 - 接口出现异常
	*/ 
	public static final String RESPONSE_STATUS_ERROR = "2";
	
	
	/*******************************业务类********************************************/
	/** 
	* @Fields KUAIDI100_REQUEST : 自定义业务代码 - 快递100查询请求
	*/ 
	public static final String KUAIDI100_REQUEST = "kuaidi100Req";
	
	/*******************************配置文件类********************************************/
	/** 
	* @Fields PARAM_KEY_KUAIDI100_REQUEST_URL : 配置文件KEY - 快递100 - 请求地址
	*/ 
	public static final String PARAM_KEY_KUAIDI100_REQUEST_URL = "KUAIDI100_REQUEST_URL";
	/** 
	* @Fields PARAM_KEY_KUAIDI100_REQUEST_ID :  配置文件KEY - 快递100 - 身份授权key
	*/ 
	public static final String PARAM_KEY_KUAIDI100_REQUEST_ID = "KUAIDI100_REQUEST_ID";
	
	/**@Fields PARAM_KEY_KUAIDI100_CUSTOMER_ID : 配置文件KEY - 快递100 - 客户ID */ 
	public static final String PARAM_KEY_KUAIDI100_CUSTOMER_ID = "KUAIDI100_CUSTOMER_ID";
	
	/*******************************快递100对外支持的快递公司代码********************************************/
	/** 全部代码信息下载地址http://www.kuaidi100.com/openapi/api_post.shtml **/
	/** 
	* @Fields COMPANY_CODE_SHUNFENG : 顺丰快递
	*/ 
	public static final String COMPANY_CODE_SHUNFENG = "shunfeng";

	/**
	 * 申通快递
	 */
	public static final String COMPANY_CODE_SHENTONG = "shentong";

	
	
	/**@Fields RESP_CODE_SUCCESS : 成功响应信息 */ 
	public static final String RESP_CODE_SUCCESS = "200";

	
}
