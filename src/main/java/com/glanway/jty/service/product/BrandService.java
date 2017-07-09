package com.glanway.jty.service.product;


import com.glanway.jty.entity.product.Brand;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * 品牌service
 */
public interface BrandService extends BaseService<Brand,Long> {

    /**
     *
     * <p>名称：getEnableBrand</p>
     * <p>描述：获得启用品牌</p>
     * @author：LiuJC
     * @return List<Brand> 品牌集合
     */
    List<Brand> getEnableBrand();

    /**
     * <p>名称：brandNextId</p>
     * <p>描述：获取下个ID值</p>
     * @author：LiuJC
     * @return
     */
    Long brandNextId();

    /**
     *
     * <p>名称：findTopBrandRecommend</p>
     * <p>描述：获取顶级分类(及子分类) 所管理的品牌</p>
     * @author：LiuJC
     * @param  offset
     * @param  maxResult
     * @param  topCategoryId 顶级分类Id
     */
    List<Brand> findTopBrandRecommend(Integer offset, Integer maxResult, Long topCategoryId);
}
