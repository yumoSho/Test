package com.glanway.jty.utils.logistics.bean;

/** 
* @文件名: KuaiDi100RequestBean.java
* @功能描述: 快递100服务请求数据模型
* @API地址：http://www.kuaidi100.com/openapi/api_post.shtml
* @author SunF
* @date 2016年4月19日 上午9:49:48 
*  
*/
public class KuaiDi100RequestBean {

	/** 
	* @Fields id : 身份授权key
	* 不填 - 自动从配置文件中取
	*/ 
	private String id;
	/** 
	* @Fields com : 快递公司代码
	* 必填
	*/ 
	private String com;
	/** 
	* @Fields nu : 快递单号
	* 必填 - 请根据常量类中的记录自行转换
	*/ 
	private String nu;
	/** 
	* @Fields valicode : （已弃用字段,请忽略）
	*/  
	@Deprecated
	private String valicode;
	/** 
	* @Fields show : 返回类型： 0：返回json字符串（默认）       1：返回xml对象， 
	*						   2：返回html对象           3：返回text文本
	* 选填 - 默认json
	*/ 
	private String show;
	/** 
	* @Fields muti : 返回信息数量：1:返回多行完整的信息， 0:只返回一行信息。 
	* 选填 - 默认一条信息
	*/ 
	private String muti;
	/** 
	* @Fields order : 排序： desc：按时间由新到旧排列， asc：按时间由旧到新排列。 
	* 选填 - 默认desc
	*/ 
	private String order;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getNu() {
		return nu;
	}
	public void setNu(String nu) {
		this.nu = nu;
	}
	public String getValicode() {
		return valicode;
	}
	public void setValicode(String valicode) {
		this.valicode = valicode;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public String getMuti() {
		return muti;
	}
	public void setMuti(String muti) {
		this.muti = muti;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "KuaiDi100RequestBean [id=" + id + ", com=" + com + ", nu=" + nu + ", valicode=" + valicode + ", show="
				+ show + ", muti=" + muti + ", order=" + order + "]";
	}
	
}
