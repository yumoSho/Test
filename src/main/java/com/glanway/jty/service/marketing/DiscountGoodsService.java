/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: hg
 * FileName: ActivityGoodsService.java
 * PackageName: com.glanway.hg.service.activity
 * Date: 2016年4月29日下午5:48:34
 **/
package com.glanway.jty.service.marketing;


import com.glanway.jty.entity.marketing.DiscountGoods;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * <p>名称：活动商品service接口</p>
 * <p>描述：活动管理</p>
 * @author：LiuJC
 * @date：2016/5/13 14:04
 * @version: 1.0
 */
public interface DiscountGoodsService extends BaseService<DiscountGoods,Long> {


    void saveOrUpdate(List<DiscountGoods> newGoods);
}
