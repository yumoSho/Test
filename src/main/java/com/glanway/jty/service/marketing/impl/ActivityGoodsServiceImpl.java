/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: hg
 * FileName: ActivityGoodsServiceImpl
 * PackageName: com.glanway.hg.service.activity.impl
 * Date: 2016年4月29日下午5:48:34
 **/
package com.glanway.jty.service.marketing.impl;

import com.glanway.jty.dao.marketing.ActivityGoodsDao;
import com.glanway.jty.entity.marketing.ActivityGoods;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.ActivityGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>名称：活动管理商品</p>
 * <p>描述：活动管理商品</p>
 * @author：LiuJC
 * @date：2016/5/13 14:04
 * @version: 1.0
 */
@Service
@Transactional
public class ActivityGoodsServiceImpl extends BaseServiceImpl<ActivityGoods, Long> implements ActivityGoodsService {
    @Autowired
    private ActivityGoodsDao activityGoodsDao;

    /**
     * <p>名称：通过活动id 删除活动商品</p>
     * <p>描述：通过活动id 删除活动商品</p>
     * @author：LiuJC
     * @param id 活动id
     */
    @Override
    public void deleteByActivityId(Long id) {
        activityGoodsDao.deleteByActivityId(id);
    }

    @Override
    public ActivityGoods calcPrice(Long id,Long goodsId) {
        return activityGoodsDao.calcPrice(id,goodsId);
    }
}


