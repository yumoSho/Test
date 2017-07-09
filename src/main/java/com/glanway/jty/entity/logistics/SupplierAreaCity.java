package com.glanway.jty.entity.logistics;

import com.glanway.jty.entity.BaseEntity;

/** 
* @文件名: SupplierAreaCity.java
* @功能描述: 供应商物流 与城市 关系
* @author SunF
* @date 2016年4月5日 下午1:19:42 
*  
*/
public class SupplierAreaCity extends BaseEntity{
	private static final long serialVersionUID = 2888671194771587298L;

	/** 
	* @Fields supplierAreaId : 
	*/ 
	private Long supplierAreaId;
	
	/** 
	* @Fields hatCity :
	*/ 
	private HatCity hatCity;
	
	/** 
	* @Fields deleted : 删除标记
	*/ 
	private Integer deleted;

	public Long getSupplierAreaId() {
		return supplierAreaId;
	}

	public void setSupplierAreaId(Long supplierAreaId) {
		this.supplierAreaId = supplierAreaId;
	}

	public HatCity getHatCity() {
		return hatCity;
	}

	public void setHatCity(HatCity hatCity) {
		this.hatCity = hatCity;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "SupplierAreaCity [supplierAreaId=" + supplierAreaId + ", hatCity=" + hatCity + ", deleted=" + deleted
				+ ", id=" + id + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy="
				+ lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + "]";
	}

}
