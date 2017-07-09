package com.glanway.jty.dao.product;

import com.glanway.gone.dao.MyBatisMapper;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.ProductImg;


/**
 * 产品商品图片DAO
 */
@MyBatisMapper
public interface ProductImageDao extends BaseDao<ProductImg, Long> {
    /**
     * 删除单品图片
     */
    void deleteGoodsImage(Long goodsId);

    /**
     * 通过产品id删除图片
     * @param pid
     */
    void deleteProductImage(Long pid);

}