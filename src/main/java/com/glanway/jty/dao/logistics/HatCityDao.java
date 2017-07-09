package com.glanway.jty.dao.logistics;


import com.glanway.jty.entity.logistics.HatCity;

import java.util.List;
import java.util.Map;

/**
* @文件名: HatCityDao.java
* @功能描述: 城市信息DAO层
* @author SunF
* @date 2016年4月7日 下午3:29:50 
*  
*/
public interface HatCityDao {
    /** 
    * @功能描述: 根据省份编号获取城市信息列表
    * @param
    * @return       
    */
    public List<HatCity> listCityBySuperCode(String superCode);

	/** 
	* @功能描述: 根据城市名称和省份编码获取城市信息
	* @param cityName
	 * @param provinceId 
	* @return       
	*/
	public HatCity queryLikeNameAndSuperCode(Map<String, String> map);
	
}
