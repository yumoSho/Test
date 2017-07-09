package com.glanway.jty.service.logistics.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.dao.logistics.SupplierAreaCityDao;
import com.glanway.jty.dao.logistics.SupplierAreaDao;
import com.glanway.jty.entity.logistics.SupplierArea;
import com.glanway.jty.entity.logistics.SupplierAreaCity;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.logistics.SupplierAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/** 
* @文件名: SupplierAreaServiceImpl.java
* @功能描述:  物流信息管理业务层 - 实现
* @author SunF
* @date 2016年4月5日 下午5:25:51 
*  
*/
@Service
public class SupplierAreaServiceImpl extends BaseServiceImpl<SupplierArea, Long> implements SupplierAreaService{

	@Autowired
	private SupplierAreaDao supplierAreaDao;
	@Autowired
	private SupplierAreaCityDao supplierAreaCityDao;
	
	@Override
	public boolean batchDelete(Long[] ids) {
		//标记删除物流信息
		List<Long> idList = Arrays.asList(ids);
		supplierAreaDao.batchDelete(idList);
		supplierAreaCityDao.deleteBySupplierAreaIdList(idList);
		return true;
	}

	@Override
	public Page<SupplierArea> findPageBySupplierId(Filters filter, Pageable pageable) {
        Map<String, Object> paramsMap = super.createParamsMapByFilters(filter);
        User user = super.getCurrentUserBean();
		Integer userType = user.getUserType();  //当前用户的类型
		if(User.TYPE_SUPPLIER == userType){     //供应商用户，只能看到自己
			paramsMap.put(SUPPLIER_ID, user.getCustomerId());
		}
	    if (null != pageable) {
            paramsMap.put(BaseDao.OFFSET_PROP, pageable.getOffset());
            paramsMap.put(BaseDao.MAX_RESULTS_PROP, pageable.getPageSize());

            Sort sort = pageable.getSort();
            if (null != sort) {
                paramsMap.put(BaseDao.SORT_PROP, new IterateNamingTransformSort(sort));
            }
        }
        
        int total = supplierAreaDao.countBySupplierId(paramsMap);
        List<SupplierArea> data = total > 0 ? supplierAreaDao.findManyBySupplierId(paramsMap) : Collections.<SupplierArea>emptyList();
        
        return new SimplePage<SupplierArea>(pageable, data, total);
        
	}

	@Override
	public void saveInfo(SupplierArea supplierArea) {
		Date date = new Date();
		Long userId = super.getCurrentUserId();
		supplierArea.setCreatedBy(userId);
		supplierArea.setCreatedDate(date);
		supplierArea.setDeleted(Constants.SYS_DELETED_FALSE);
		supplierAreaDao.saveAndReturnId(supplierArea); //自动返填自增的主键id值
		
		Long supplierAreaId = supplierArea.getId();
		List<SupplierAreaCity> sacList = supplierArea.getHatCityList(); //已选城市信息
		this.saveSupplierAreaCity(sacList, supplierAreaId);
	}

	@Override
	public List<SupplierArea> findList(Long id) {
		return supplierAreaDao.findList(id);
	}

	@Override
	public List<Long> listSupplierAreaCity(Long id) {
		return supplierAreaCityDao.findBySupplierAreaId(id);
	}

	@Override
	public void updateInfo(SupplierArea supplierArea) {
		Long supplierAreaId = supplierArea.getId();
		Long userId = super.getCurrentUserId();
		Date date = new Date();
		
		supplierArea.setLastModifiedBy(userId);
		supplierArea.setLastModifiedDate(date);
		supplierAreaDao.update(supplierArea);
		
		//包含的城市列表
		List<Long> cityList = supplierAreaCityDao.findBySupplierAreaId(supplierAreaId);
		if(null != cityList && cityList.size() > 0){
			//批量删除之前的城市信息
			supplierAreaCityDao.deleteBySupplierAreaId(supplierAreaId);
		}
		//创建新的城市信息
		List<SupplierAreaCity> sacList = supplierArea.getHatCityList();
		this.saveSupplierAreaCity(sacList, supplierAreaId);
		
	}

	/** 
	* @功能描述:  插入城市信息列表
	* @param sacList 物流城市信息列表
	* @param supplierAreaId  物流信息id     
	*/
	private void saveSupplierAreaCity(List<SupplierAreaCity> sacList, Long supplierAreaId) {
		if(null != sacList && sacList.size() > 0){
			Date date = new Date();
			Long userId = super.getCurrentUserId();
			List<SupplierAreaCity> cityList = new ArrayList<SupplierAreaCity>();
			for(SupplierAreaCity sac : sacList){
				if(null == sac.getHatCity() || null == sac.getHatCity().getId()){
					continue;
				}
				sac.setSupplierAreaId(supplierAreaId);
				sac.setCreatedBy(userId);
				sac.setCreatedDate(date);
				sac.setDeleted(Constants.SYS_DELETED_FALSE);
				cityList.add(sac);
			}
			supplierAreaCityDao.batchSave(cityList);
		}
	}

	/*(non-Javadoc) 
	 * <p>名称: getPriceBySupplierIdAndCity</p> 
	 * <p>描述: 根据供应商和城市代码获取该供应商设置的价格</p> 
	 * <p>author：Sun.Fan</p> 
	 * @param supplierId 供应商id
	 * @param cityCode 城市编码
	 * @return 
	 * @see com.glanway.jty.service.logistics.SupplierAreaService#getPriceBySupplierIdAndCity(java.lang.Long, java.lang.String) 
	 */ 
	@Override
	public BigDecimal getPriceBySupplierIdAndCity(String cityCode) {
		SupplierArea supplierArea = supplierAreaDao.getPriceBySupplierIdAndCity(cityCode);
		return (null != supplierArea) ? supplierArea.getPrice() : null;
	}


}
