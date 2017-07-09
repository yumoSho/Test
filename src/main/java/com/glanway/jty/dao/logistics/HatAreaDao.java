package com.glanway.jty.dao.logistics;


import com.glanway.jty.entity.logistics.HatArea;

import java.util.List;
import java.util.Map;

/** 
* @文件名: HatAreaDao.java
* @功能描述: 区县信息DAO层
* @author SunF
* @date 2016年4月7日 下午3:43:00 
*  
*/
public interface HatAreaDao {
    /** 
    * @功能描述: 根据所属城市编号获取县区信息列表
    * @param superCode 城市编号
    * @return       
    */
    public List<HatArea> listAreaBySuperCode(String superCode);

	/** 
	 * @功能描述: 根据名称和所属城市编号，获取县区信息 
	 * @param areaName 区县名称
	 * @param cityId 城市编码
	 * @return       
	 */
	public HatArea queryLikeNameAndSuperCode(Map<String, String> map);
}
