package com.glanway.jty.dao.marketing;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.marketing.NewGoods;

/**
 * <p>名称：NewGoodsDao</p>
 * <p>描述：爆品推荐</p>
 * @author：LiuJC
 */
public interface NewGoodsDao extends BaseDao<NewGoods, Long> {
    void deleteAll();
}
