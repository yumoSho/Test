package com.glanway.jty.service.marketing;

import com.glanway.jty.entity.marketing.ProductPackageDetail;
import com.glanway.jty.service.BaseService;

/**
 * Created by ASUS on 2014/11/7.
 */
public interface ProductPackagesDetailService extends BaseService<ProductPackageDetail, Long> {
    /**
     * 保存套餐详细数据
     * @param packagesId
     * @param goodsIds
     * @param productIds
     */
    public void saveProductPackagesDetail(Long packagesId, String goodsIds, String productIds);

    /**
     * 通过套餐ID删除该套餐下的所有套餐详细数据
     * @param packagesId
     */
    public void deleteByPackagesId(Long packagesId);
}
