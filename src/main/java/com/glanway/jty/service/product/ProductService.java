package com.glanway.jty.service.product;


import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.product.Product;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * 产品Service
 */
public interface ProductService extends BaseService<Product, Long> {

    void saveOrUpdate(Product product);

    Page<Product> findPage(
            Filters baseFilters,    /* 产品条件过滤器 */
            Filters brandFilters,   /* 品牌条件过滤器 */
            Filters modelFilters,   /* 模型条件过滤器 */
            Filters categoryFilters,/* 分类条件过滤器 */
            Filters labelFilters,/* 供应商条件过滤器 */
            Pageable pageable
    );
    /**
     * 是否是单品
     * @return
     */
    public boolean isSingleProduct(Long productId);
    /**
     * 商品下架
     * @param ids
     */
    void offLoading(Long[] ids);

    /**
     * 商品上架
     * @param ids
     */
    void onSell(Long[] ids);


    public void save(List<Product> products);
}
