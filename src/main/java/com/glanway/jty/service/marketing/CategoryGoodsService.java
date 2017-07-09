package com.glanway.jty.service.marketing;

import com.glanway.jty.entity.marketing.CategoryGoods;
import com.glanway.jty.entity.marketing.NewGoods;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * <p>名称：CategoryGoodsService</p>
 * <p>描述 ：楼层推荐商品推荐</p>
 * @author：LiuJC
 */
public interface CategoryGoodsService extends BaseService<CategoryGoods, Long> {
    void saveOrUpdate(List<CategoryGoods> newGoods);

    void deleteByCategoryGoodsCommendId(Long id);

    void deleteGoodsCommendId(Long id);
}
