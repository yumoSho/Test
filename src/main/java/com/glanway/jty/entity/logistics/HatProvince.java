package com.glanway.jty.entity.logistics;

import java.io.Serializable;
import java.util.List;

/** 
* @文件名: HatProvince.java
* @功能描述: 省份信息
* @author SunF
* @date 2016年4月1日 下午6:35:19 
*  
*/
public class HatProvince implements Serializable{

    private Long id;
    /** 
    * @Fields provinceCode : 省份编号
    */ 
    private String provinceCode;	
    /** 
    * @Fields provinceName : 省份名称
    */ 
    private String provinceName;

    private String remark; //缩略省

    /** 
    * @Fields hatCities : 包含城市列表
    */ 
    private List<HatCity> hatCities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<HatCity> getHatCities() {
        return hatCities;
    }

    public void setHatCities(List<HatCity> hatCities) {
        this.hatCities = hatCities;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }else {
            if (this.getClass() == obj.getClass()) {
                HatProvince hatProvince = (HatProvince) obj;
                if (this.getProvinceCode().equals(hatProvince.getProvinceCode())) {
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
    }

	@Override
	public String toString() {
		return "HatProvince [id=" + id + ", provinceCode=" + provinceCode + ", provinceName=" + provinceName
				+ ", hatCities=" + hatCities + "]";
	}

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
