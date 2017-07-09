package com.glanway.jty.entity.logistics;

/** 
* @文件名: HatArea.java
* @功能描述: 区县信息 
* @author SunF
* @date 2016年4月1日 下午6:33:21 
*  
*/
public class HatArea {
    private Long id;
    /** 
    * @Fields areaCode : 区县编号
    */ 
    private String areaCode; 
    /** 
    * @Fields areaName : 区县名称
    */ 
    private String areaName;
    /** 
    * @Fields superCode : 所属城市编号
    */ 
    private String superCode;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSuperCode() {
		return superCode;
	}

	public void setSuperCode(String superCode) {
		this.superCode = superCode;
	}

	@Override
	public String toString() {
		return "HatArea [id=" + id + ", areaCode=" + areaCode + ", areaName=" + areaName + ", superCode=" + superCode
				+ "]";
	}

}
