package com.glanway.jty.dao.product;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.BrandCategory;

/**
 * 品牌分类DAO
 */
public interface BrandCategoryDao extends BaseDao<BrandCategory, Long> {

    /**
     * 通过分类id 来删除品牌分类关系
     * @param cid 分类ID
     */
    void deleteBrandCateByid(Long cid);
}