package com.glanway.jty.service.logistics;


import com.glanway.jty.entity.logistics.HatCity;

import java.util.List;

/** 
* @文件名: HatCityService.java
* @功能描述: 城市信息业务层接口
* @author SunF
* @date 2016年4月7日 下午3:18:47 
*  
*/
public interface HatCityService {
	String CITY_NAME = "cityName";
	String SUPER_CODE = "superCode";
    /** 
    * @功能描述: 据所属省份CODE获取城市信息
    * @param superCode 省份编号
    * @return       
    */
    public List<HatCity> listCityBySuperCode(String superCode);

	/** 
	* @功能描述: 查询城市信息
	* @param cityName 名称（请传两个以上汉字）
	 * @param superCode 省级代码 
	* @return       
	*/
	public HatCity queryLikeNameAndSuperCode(String cityName, String superCode);
	
}
