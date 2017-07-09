package com.glanway.jty.entity.perm;

import com.glanway.jty.entity.BaseEntity;

/**
 * 
 *********************************************** 
 * 页面管理的实体类
 * date: 2015年9月29日 下午1:48:49 <br/>
 *
 * @author wuqi
 * @version 
 * @since JDK 1.7
 * @change Log:
 *************************************************
 */
public class Page extends BaseEntity {
	
	/**
	 * 页面名称
	 */
	private String name;

	/**
	 * 页面链接
	 */
	private String url;
	
	/**
	 * 所属模块Id
	 */
	private Long moduleId;
	
	/**
	 * 所属模块
	 */
	private Module module;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 删除标识
	 */
	private Boolean deleted = false;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
