package com.glanway.jty.service.marketing;

import com.glanway.jty.entity.marketing.NewGoods;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * <p>名称：NewGoodsService</p>
 * <p>描述：爆品推荐</p>
 * @author：LiuJC
 */
public interface NewGoodsService extends BaseService<NewGoods, Long> {
    void saveOrUpdate(List<NewGoods> newGoods);
}
