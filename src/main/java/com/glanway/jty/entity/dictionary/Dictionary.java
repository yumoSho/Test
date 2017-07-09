package com.glanway.jty.entity.dictionary;

import com.glanway.jty.entity.BaseEntity;

/** 
* @文件名: Dictionary.java
* @功能描述: 数据字典
* @author SunF
* @date 2016年3月31日 上午10:16:45 
*  
*/
public class Dictionary extends BaseEntity{
	private static final long serialVersionUID = -9067143203269192854L;
	
	/** 
	* @Fields superId : 所属父字典ID
	*/ 
	private Long superId;
	/** 
	* @Fields dicCode : 字典编号
	*/ 
	private String dicCode;
	/** 
	* @Fields dicName : 字典名称
	*/ 
	private String dicName;
	/** 
	* @Fields remark : 备注
	*/ 
	private String remark;
	/** 
	* @Fields sortNum : 排列序号
	*/ 
	private Integer sortNum;
	/** 
	* @Fields deleted : 删除标记 
	*/ 
	private Integer deleted;
	private String content;
	
	public Long getSuperId() {
		return superId;
	}
	public void setSuperId(Long superId) {
		this.superId = superId;
	}
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Dictionary [superId=" + superId + ", dicCode=" + dicCode + ", dicName=" + dicName + ", remark=" + remark
				+ ", sortNum=" + sortNum + ", deleted=" + deleted + ", id=" + id + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate="
				+ lastModifiedDate + "]";
	}
	
}
