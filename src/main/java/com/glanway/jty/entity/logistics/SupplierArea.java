package com.glanway.jty.entity.logistics;

import com.glanway.jty.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/** 
* @文件名: SupplierArea.java
* @功能描述: 供应商物流区域管理 
* @author SunF
* @date 2016年4月5日 下午1:16:54 
*  
*/
public class SupplierArea extends BaseEntity{
	private static final long serialVersionUID = -1223302910191715027L;
	
	/** 
	* @Fields areaName : 区域名称
	*/ 
	private String areaName;
	/**  
	* @Fields supplierId : 供应商ID
	*/ 
	private Long supplierId;
	/** 
	* @Fields supplierName : 供应商名称
	*/ 
	private String supplierName;
	
	/** 
	* @Fields hatCity : 包含城市列表
	*/ 
	private List<SupplierAreaCity> hatCityList;
	/** 
	* @Fields price : 价格
	*/ 
	private BigDecimal price;
	
	/** 
	* @Fields field1 : 预留字段1
	*/ 
	private String field1;
	/** 
	* @Fields field2 : 预留字段2
	*/ 
	private String field2;
	/** 
	* @Fields field3 : 预留字段3
	*/ 
	private String field3;
	/** 
	* @Fields field4 : 预留字段4
	*/ 
	private String field4;
	
	/** 
	* @Fields deleted : 删除标记
	*/ 
	private Integer deleted;


	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<SupplierAreaCity> getHatCityList() {
		return hatCityList;
	}

	public void setHatCityList(List<SupplierAreaCity> hatCityList) {
		this.hatCityList = hatCityList;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}
