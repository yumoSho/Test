package com.glanway.jty.utils.logistics;

/** 
* @文件名: RouteVO.java
* @功能描述: 路由信息
* @author SunF
* @date 2016年4月21日 下午4:21:31 
*  
*/
public class RouteVO {

	/** 
	* @Fields time : 时间
	*/ 
	private String time;
	
	/** 
	* @Fields context : 内容
	*/ 
	private String context;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return "RouteVO [time=" + time + ", context=" + context + "]";
	}
	
}
