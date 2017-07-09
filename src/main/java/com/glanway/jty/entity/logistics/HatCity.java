package com.glanway.jty.entity.logistics;

import java.util.List;

/** 
* @文件名: HatCity.java
* @功能描述: 城市信息 
* @author SunF
* @date 2016年4月1日 下午6:33:41 
*  
*/
public class HatCity {

    private Long id;
    /** 
    * @Fields cityCode : 城市编号
    */ 
    private String cityCode;
    /** 
    * @Fields cityName : 城市名称
    */ 
    private String cityName;
    /** 
    * @Fields superCode : 所属省份编号
    */ 
    private String superCode;

    /** 
    * @Fields areaList : 包含区县列表
    */ 
    private List<HatArea> areaList;
    
    public List<HatArea> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<HatArea> areaList) {
        this.areaList = areaList;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

	public String getCityCode() {
		return cityCode;
	}

	public String getSuperCode() {
		return superCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setSuperCode(String superCode) {
		this.superCode = superCode;
	}

	@Override
	public String toString() {
		return "HatCity [id=" + id + ", cityCode=" + cityCode + ", cityName=" + cityName + ", superCode=" + superCode
				+ ", areaList=" + areaList + "]";
	}

}
