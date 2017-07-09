package com.glanway.jty.utils.logistics.bean;

/**      
 * <p>名称: 快递100响应信息-路由信息</p>
 * <p>说明: 不要修改结构</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 * 
 * @author：Sun.Fan
 * @date：2016年5月10日上午11:11:18   
 * @version: 1.0 
 */
public class KuaiDi100ResultItem {
	/**@Fields time : 时间 */ 
	String time; 
	/**@Fields context : 内容 */ 
	String context;
	/**@Fields ftime : 格式化后的时间，暂时不需要 */ 
	String ftime;

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

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

}
