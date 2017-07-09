package com.glanway.jty.dao.marketing;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.marketing.ProductPackageDetail;

import java.util.List;

/**
 * Created by ASUS on 2014/11/7.
 */
public interface ProductPackagesDetailDao extends BaseDao<ProductPackageDetail, Long> {


    /**
     * 通过套餐的ID删除该套餐下的所有套餐详细
     *
     * @param packagesId
     */
    public void deleteByPackagesId(Long packagesId);

    /**
     * 痛过packageID 拿到配件商品的id
     * @param packagesId
     * @return
     */
    List<ProductPackageDetail> findByPachagesId(Long packageId);
}
