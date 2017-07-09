package com.glanway.jty.dao.marketing;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.marketing.CategoryGoods;
import com.glanway.jty.entity.marketing.CategoryGoodsCommend;

import java.util.List;
import java.util.Map;

/**
 * <p>名称：CategoryGoodsCommendDao</p>
 * <p>描述：楼层推荐</p>
 * @author：LiuJC
 */
public interface CategoryGoodsCommendDao extends BaseDao<CategoryGoodsCommend, Long> {
    /**
     * <p>名称：findDetail</p>
     * <p>描述：详细信息</p>
     * @author：LiuJC
     * @param id
     * @return
     */
    CategoryGoodsCommend  findDetail(Long id);


    List<CategoryGoodsCommend> findMany2(Map<String,?> map);
}
