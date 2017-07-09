package com.glanway.jty.dao.marketing;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.marketing.CategoryGoods;
import com.glanway.jty.entity.marketing.CategoryGoodsCommend;
import com.glanway.jty.entity.marketing.NewGoods;

/**
 * <p>名称：CategoryGoodsDao</p>
 * <p>描述：楼层推荐</p>
 * @author：LiuJC
 */
public interface CategoryGoodsDao extends BaseDao<CategoryGoods, Long> {
    void deleteAll();

    void deleteByCategoryGoodsCommendId(Long id);

    void deleteGoodsCommendId(Long id);

}
