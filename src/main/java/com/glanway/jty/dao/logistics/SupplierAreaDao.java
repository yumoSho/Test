package com.glanway.jty.dao.logistics;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.logistics.SupplierArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/** 
* @文件名: SupplierAreaDao.java
* @功能描述: 物流信息管理DAO
* @author SunF
* @date 2016年4月5日 下午3:25:27 
*  
*/
public interface SupplierAreaDao extends BaseDao<SupplierArea, Long>{

	/** 
	* @功能描述: 查看某供应商下物流区域信息数量
	* @param paramsMap
	* @return       
	*/
	int countBySupplierId(Map<String, Object> paramsMap);

	/** 
	* @功能描述: 查看某供应商下物流区域信息列表
	* @param paramsMap
	* @return       
	*/
	List<SupplierArea> findManyBySupplierId(Map<String, Object> paramsMap);

	/** 
	* @功能描述: 返回物流信息列表
	* @param id
	* @return       
	*/
	List<SupplierArea> findList(Long id);

	/** 
	* @功能描述: 保存数据，并返回id
	* @param supplierArea
	* @return       
	*/
	Long saveAndReturnId(SupplierArea supplierArea);

	/** 
	* @功能描述: 批量删除物流信息（标记删除）
	* @param ids id      
	*/
	void batchDelete(List<Long> ids);

	/**
	 * <p>名称：获取供应商价格</p> 
	 * <p>描述：根据供应商和城市代码获取该供应商设置的价格TODO</p>
	 * @author：Sun.Fan
	 * @param supplierId 供应商编号
	 * @param cityCode 城市编号
	 * @return
	 */
	SupplierArea getPriceBySupplierIdAndCity(@Param("cityCode") String cityCode);

}
