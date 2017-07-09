package com.glanway.jty.dao.logistics;

import com.glanway.jty.entity.logistics.HatProvince;

import java.util.List;

/** 
* @文件名: HatProvinceDao.java
* @功能描述: 省份信息DAO层
* @author SunF
* @date 2016年4月7日 下午2:54:46 
*  
*/
public interface HatProvinceDao{
    /** 
    * @功能描述: 获取所有省份列表（不含城市信息）
    * @return       
    */
    public List<HatProvince> listAllProvince();

    /** 
	* @功能描述: 获取所有省份列表 （含有城市信息）
	* @return       
	*/
	public List<HatProvince> listAllProvinceAndCity();

	/** 
    * @功能描述: 根据省份编号获取省份信息，以及城市列表
    * @param provinceCode 省份编号
    * @return
    */
    public HatProvince listProvinceInfoByCode(String provinceCode);
    
    public List<HatProvince> listMunicipalities();

	public HatProvince queryLikeName(String provinceName);
}
