package com.glanway.jty.utils.logistics.bean;

/** 
* @文件名: SunfengRouteRequestBean.java
* @功能描述: 顺丰快递路由信息请求数据
* @author SunF
* @date 2016年4月19日 下午3:44:17 
*  
*/
public class ShunFengRouteRequestBean {
	
	/** 
	* @Fields trackingType :查询号类别
	*  1：根据顺丰运单号查询， order 节点中 tracking_number 将被当作顺丰运单号处理  
	* 	 2：根据客户订单号查询， order 节点中 tracking_number 将被当作客户订单号处理 
	*/ 
	private String trackingType;
	/** 
	* @Fields trackingNumber :查询号：  
	* 	如果 tracking_type=1，则此值 为顺丰运单号  
	* 	如果 tracking_type=2，则此值 为客户订单号 
	*/ 
	private String trackingNumber;
	/** 
	* @Fields method_type : 路由查询类别：  
	* 	1：标准路由查询  
	* 	2：定制路由查询 
	*/ 
	private String method_type;
	
	public String getTrackingType() {
		return trackingType;
	}
	public void setTrackingType(String trackingType) {
		this.trackingType = trackingType;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getMethod_type() {
		return method_type;
	}
	public void setMethod_type(String method_type) {
		this.method_type = method_type;
	}
	@Override
	public String toString() {
		return "ShunFengRouteRequestBean [trackingType=" + trackingType + ", trackingNumber=" + trackingNumber
				+ ", method_type=" + method_type + "]";
	}
	
}
