package com.glanway.jty.service.marketing;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.marketing.ProductAccessory;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * 商品配件
 */
public interface ProductAccessoryService extends BaseService<ProductAccessory, Long> {

    /**
     * 配件套餐及详细
     * @param productAccessory
     * @param goodsIds
     * @param productIds
     * @return
     */
    String saveAccessory(ProductAccessory productAccessory, String goodsIds, String productIds);

    String updateAccessory(ProductAccessory productAccessory, String goodsIds, String productIds);

    /**
     * 删除配件及详细
     * @param accessoryID
     */
    boolean deleteAccessory(String accessoryID);

    boolean deleteAccessoryByIds(String[] accessoryIds);

    Page<ProductAccessory> listAllPryGoods(Filters baseFilters,    /* 产品条件过滤器 */
                                           Pageable pageable);

    ProductAccessory getAccessoryById(String id);

    List<ProductAccessory> getAccessoryByPryGoodsId(String pryGoodsId);

    boolean checkIfExist(String pryGoodsId);

    ProductAccessory getByPrimaryGoodsId(String websiteId, String goodsId);

    Boolean checkAccessory(String[] goodsIds, String promoteId);
}
