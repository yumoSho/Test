package com.glanway.jty.service.marketing;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.marketing.ProductPackage;
import com.glanway.jty.service.BaseService;

import java.util.List;
import java.util.Map;

public interface ProductPackagesService extends BaseService<ProductPackage, Long> {
    /**
     * 保存套餐及详细
     * @param productPackage
     * @param goodsIds
     * @param productIds
     * @return
     */
    String savePackages(ProductPackage productPackage, String goodsIds, String productIds);

    String updatePackages(ProductPackage productPackage, String goodsIds, String productIds);


    /**
     * 删除套餐及详细
     * @param packagesID
     */
    boolean deletePackages(Long packagesID);

    boolean deletePackagesByIds(Long[] packageIds);

    Page<ProductPackage> listAllPryGoods(Filters baseFilters,    /* 产品条件过滤器 */
                                         Pageable pageable);
    ProductPackage getPackageById(Long id);

    /**
     * <p>名称：</p>
     * <p>描述：更具商品商品id获取套餐 或者 配件</p>
     * @author：LiuJC
     * @param goodsId 商品id
     * @param Type   PACKAGE_TYPE_PACKAGE = 0;//配件 PACKAGE_TYPE_ACCESSOY = 1;//配件
     * @return
     */
    List<ProductPackage> findProductPackageByProductGoodsId(Long goodsId,Integer Type);

}
