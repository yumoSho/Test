package com.glanway.jty.utils.logistics.bean;

import com.glanway.jty.utils.logistics.util.JacksonHelper;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;

/**      
 * <p>名称: 快递100响应结果json包装类</p>
 * <p>说明: 用于快递100查询响应结果的转换</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 * 
 * @author：Sun.Fan
 * @date：2016年5月11日上午10:14:46   
 * @version: 1.0 
 */
public class KuaiDi100Result {

	private String message = "";
	@JsonIgnore
	private String nu = "";
	@JsonIgnore
	private String ischeck = "0";
	@JsonIgnore
	private String com = "";
	private String status = "0";
	@JsonIgnore
	private ArrayList<KuaiDi100ResultItem> data = new ArrayList<KuaiDi100ResultItem>();
	@JsonIgnore
	private String state = "0";
	@JsonIgnore
	private String condition = "";

	@SuppressWarnings("unchecked")
	public KuaiDi100Result clone() {
		KuaiDi100Result r = new KuaiDi100Result();
		r.setCom(this.getCom());
		r.setIscheck(this.getIscheck());
		r.setMessage(this.getMessage());
		r.setNu(this.getNu());
		r.setState(this.getState());
		r.setStatus(this.getStatus());
		r.setCondition(this.getCondition());
		r.setData((ArrayList<KuaiDi100ResultItem>) this.getData().clone());

		return r;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public ArrayList<KuaiDi100ResultItem> getData() {
		return data;
	}

	public void setData(ArrayList<KuaiDi100ResultItem> data) {
		this.data = data;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return JacksonHelper.toJSON(this);
	}
}
