package com.glanway.jty.service.logistics;


import com.glanway.jty.entity.logistics.HatProvince;

import java.util.List;

/** 
* @文件名: HatProvinceService.java
* @功能描述: 省份信息业务层接口
* @author SunF
* @date 2016年4月7日 下午2:52:47 
*  
*/
public interface HatProvinceService{

    /** 
    * @功能描述: 获取所有省份列表（不含城市信息）
    * @return       
    */
    List<HatProvince> listAllProvince();

    /** 
	* @功能描述: 获取所有省份列表 （含有城市信息）
	* @return       
	*/
	List<HatProvince> listALLProvinceAndCity();

	/** 
    * @功能描述: 根据省份编号获取省份信息，以及城市列表
    * @param provinceCode 省份编号
    * @return       
    */
    HatProvince listProvinceInfoByCode(String provinceCode);

    /** 
    * @功能描述: 获取含有直辖市信息的省份列表
    * @return       
    */
    List<HatProvince> listMunicipalities();

	/** 
	* @功能描述: 根据省份名获取省份信息
	* @param provinceName 省份名称
	* @return       
	*/
	HatProvince queryLikeName(String provinceName);
	
	
}
