package com.glanway.jty.service.logistics;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.logistics.SupplierArea;
import com.glanway.jty.service.BaseService;

import java.math.BigDecimal;
import java.util.List;

/** 
* @文件名: SupplierAreaService.java
* @功能描述:  物流信息管理业务层 - 接口
* @author SunF
* @date 2016年4月5日 下午5:26:02 
*  
*/
public interface SupplierAreaService extends BaseService<SupplierArea, Long>{
	String SUPPLIER_ID = "supplierId";

	/** 
	* @功能描述: 物流信息管理批量删除
	* @param ids id集合
	* @return      
	*/
	boolean batchDelete(Long[] ids);

	/** 
	* @功能描述: 根据供应商ID获取物流信息列表
	* @说明：先判断用户类型，若为供应商用户，则只获取该供应商物流信息列表，若为红果用户，则获取全部
	* @param filters
	* @param pageable
	* @return       
	*/
	Page<SupplierArea> findPageBySupplierId(Filters filters, Pageable pageable);

	/** 
	* @功能描述: 保存物流信息以及城市地址
	* @param supplierArea       
	*/
	void saveInfo(SupplierArea supplierArea);

	/** 
	* @功能描述: 返回物流信息列表
	* @param id
	* @return       
	*/
	List<SupplierArea> findList(Long id);

	/** 
	* @功能描述: 查找物流信息中包含的城市列表
	* @param id 物流信息id
	* @return   包含城市的id
	*/
	List<Long> listSupplierAreaCity(Long id);

	/** 
	* @功能描述: 更新物流信息以及城市地址
	* @param supplierArea       
	*/
	void updateInfo(SupplierArea supplierArea);
	
	/**
	 * <p>名称：获取运单价格</p> 
	 * <p>描述：根据供应商和城市代码获取该供应商设置的价格</p>
	 * @author：Sun.Fan
	 * @param supplierId 供应商id
	 * @param cityCode 城市编号
	 * @return
	 */
	BigDecimal getPriceBySupplierIdAndCity(String cityCode);


}
