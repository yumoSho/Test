package com.glanway.jty.service.personalcenter;

import com.glanway.jty.entity.personalcenter.Collect;
import com.glanway.jty.service.BaseService;

public interface CollectService extends BaseService<Collect, Long> {

    /**
     * 加入收藏
     * */
    void addCollect(Long memberId, Long goodsId,Integer goodsFrom,Long otherId);

}
