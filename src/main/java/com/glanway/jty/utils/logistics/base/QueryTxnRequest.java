package com.glanway.jty.utils.logistics.base;

/** 
* @文件名: QueryTxnRequest.java
* @功能描述: 物流信息查询请求类 
* @author SunF
*
*/
public class QueryTxnRequest {
	
	/** 
	* @Fields channel : 请求渠道
	*/ 
	private String channel;
	
	/** 
	* @Fields trackingNumber : 运单号
	*/ 
	private String trackingNumber;
	
	/** 
	* @Fields companyCode : 公司编号（暂时不使用）
	*/ 
	private String companyCode;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	public String toString() {
		return "QueryTxnRequest [channel=" + channel + ", trackingNumber=" + trackingNumber + ", companyCode="
				+ companyCode + "]";
	}

}
