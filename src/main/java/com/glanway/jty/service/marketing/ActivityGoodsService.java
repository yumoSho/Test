/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: jty
 * FileName: ActivityGoodsService.java
 * PackageName: com.glanway.jty.service.activity
 * Date: 2016年4月29日下午5:48:34
 **/
package com.glanway.jty.service.marketing;

import com.glanway.jty.entity.marketing.ActivityGoods;
import com.glanway.jty.service.BaseService;

/**
 * <p>名称：活动商品service接口</p>
 * <p>描述：活动管理</p>
 * @author：LiuJC
 * @date：2016/5/13 14:04
 * @version: 1.0
 */
public interface ActivityGoodsService extends BaseService<ActivityGoods,Long> {

    /**
     * <p>名称：通过活动id 删除活动商品</p>
     * <p>描述：通过活动id 删除活动商品</p>
     * @author：LiuJC
     * @param id 活动id
     */
    void deleteByActivityId(Long id);


    ActivityGoods calcPrice(Long id,Long goodsId);
}
