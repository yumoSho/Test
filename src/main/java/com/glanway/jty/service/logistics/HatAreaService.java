package com.glanway.jty.service.logistics;



import com.glanway.jty.entity.logistics.HatArea;

import java.util.List;

/** 
* @文件名: HatAreaService.java
* @功能描述: 区县信息业务层接口
* @author SunF
* @date 2016年4月7日 下午3:34:48 
*  
*/
public interface HatAreaService {
    /** 
    * @功能描述: 根据所属城市代码，获取区县信息列表
    * @param superCode
    * @return       
    */
    public List<HatArea> listAreaBySuperCode(String superCode);

	/** 
	* @功能描述: 根据城市名称和省份编码获取城市信息
	* @param areaName
	 * @param superCode 城市编号
	* @return       
	*/
	public HatArea queryLikeNameAndSuperCode(String areaName, String superCode);
}
