/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: hg
 * FileName: ActivityGoodsDao
 * PackageName: com.glanway.hg.dao.activity
 * Date: 2016年4月29日下午5:48:34
 **/
package com.glanway.jty.dao.marketing;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.marketing.DiscountGoods;

/**
 * <p>名称：限时低价商品</p>
 * <p>描述：限时低价商品</p>
 * @author：LiuJC
 * @date：2016/5/13 14:04
 * @version: 1.0
 */
public interface DiscountGoodsDao extends BaseDao<DiscountGoods,Long> {

    void deleteAll();

}
