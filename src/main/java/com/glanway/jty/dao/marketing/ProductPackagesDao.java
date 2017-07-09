package com.glanway.jty.dao.marketing;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.marketing.ProductPackage;

import java.util.List;
import java.util.Map;

public interface ProductPackagesDao extends BaseDao<ProductPackage, Long> {

    List<ProductPackage> listAllPryGoods(Map<String, Object> params);

    ProductPackage getPackageById(Long id);

    List<ProductPackage> findProductPackageByProductGoodsId(Map<String,Object> map);



}
