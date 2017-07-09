package com.glanway.jty.service.product;


import com.glanway.jty.entity.product.BrandCategory;
import com.glanway.jty.service.BaseService;

/**
 * 品牌分类Service
 */
public interface BrandCategoryService extends BaseService<BrandCategory, Long> {

    /**
     * 通过分类id 删除 品牌分类关系
     * @param cid  分类ID
     */
    void deleteBrandCateById(Long cid);


}
