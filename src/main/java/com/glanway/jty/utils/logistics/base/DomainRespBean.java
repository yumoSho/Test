package com.glanway.jty.utils.logistics.base;

import java.util.LinkedHashMap;

/** 
* @文件名: DomainRespBean.java
* @功能描述: 基础响应信息 
* @author SunF
* @date 2016年4月20日 下午2:22:54 
*  
*/
public class DomainRespBean {
	
	/** 
	* @Fields trackingNumber : 运单号码
	*/ 
	private String trackingNumber;
	
	/** 
	* @Fields companyCode : 快递公司编号
	*/ 
	private String companyCode;
	
	/** 
	* @Fields routeInfoMap : 路由信息表
	*/ 
	private LinkedHashMap<String, String> routeInfoMap;
	
	/** 
	* @Fields field1 : 预留字段1
	*/ 
	private String field1;
	/** 
	* @Fields field1 : 预留字段2
	*/
	private String field2;
	/** 
	* @Fields field1 : 预留字段3
	*/
	private String field3;
	/** 
	* @Fields field1 : 预留字段4
	*/
	private String field4;

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public LinkedHashMap<String, String> getRouteInfoMap() {
		return routeInfoMap;
	}

	public void setRouteInfoMap(LinkedHashMap<String, String> routeInfoMap) {
		this.routeInfoMap = routeInfoMap;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	public String toString() {
		return "DomainRespBean [trackingNumber=" + trackingNumber + ", companyCode=" + companyCode + ", routeInfoMap="
				+ routeInfoMap + ", field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + ", field4="
				+ field4 + "]";
	}
	
}
