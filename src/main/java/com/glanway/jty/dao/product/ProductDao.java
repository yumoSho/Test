package com.glanway.jty.dao.product;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.product.Product;

import java.util.Map;


/**
 * 产品DAO
 */
public interface ProductDao extends BaseDao<Product, Long> {
    String PRODUCT_FILTERS_PROP = "_product_filters";

    String BRAND_FILTERS_PROP = "_brand_filters";

    String MODEL_FILTERS_PROP = "_model_filters";

    String CATEGORY_FILTERS_PROP = "_category_filters";

    String LABEL_FILTERS_PROP = "_label_filters";
    /**
     * 是否是单品
     * @param productId 产品id
     * @return
     */
    int isSingleProduct(Long productId);
    /**
     * 下架商品
     * @param ids
     */
    void offLoding(Long[] ids);

    /**
     * 上架商品
     * @param ids
     */
    void onSell(Long[] ids);

    /**
     *
     */
    void deletedAreasByProductId(Long id);

    void addProductAreas(Map<String,Object> hp);


}