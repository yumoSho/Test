package com.glanway.jty.dao.logistics;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.logistics.SupplierAreaCity;

import java.util.List;

/** 
* @文件名: SupplierAreaCityDao.java
* @功能描述: 物流信息与城市 
* @author SunF
* @date 2016年4月13日 上午9:32:01 
*  
*/
public interface SupplierAreaCityDao extends BaseDao<SupplierAreaCity, Long>{

	/** 
	* @功能描述: 批量新增物流对应城市信息
	* @param cityList       
	*/
	void batchSave(List<SupplierAreaCity> cityList);

	/** 
	* @功能描述: 查找物流信息中包含的城市列表
	* @param id 物流信息id
	* @return       
	*/
	List<Long> findBySupplierAreaId(Long id);

	/** 
	* @功能描述: 根据物流信息id删除
	* @param supplierAreaId  物流信息id 
	*/
	void deleteBySupplierAreaId(Long supplierAreaId);

	/** 
	* @功能描述: 根据物流信息id删除(批量)
	* @param ids 物流信息id集合      
	*/
	void deleteBySupplierAreaIdList(List<Long> ids);

}
