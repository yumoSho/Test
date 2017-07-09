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
import com.glanway.jty.entity.marketing.ActivityGoods;
import org.apache.ibatis.annotations.Param;

/**
 * <p>名称：活动商品</p>
 * <p>描述：活动商品</p>
 * @author：LiuJC
 * @date：2016/5/13 14:04
 * @version: 1.0
 */
public interface ActivityGoodsDao extends BaseDao<ActivityGoods,Long> {
    /**
    * 通过活动id 删除活动商品
    * */
    /**
     * <p>名称：通过活动id 删除活动商品</p>
     * <p>描述：通过活动id 删除活动商品</p>
     * @author：LiuJC
     * @param id 活动管理id
     */
    void deleteByActivityId(Long id);

    ActivityGoods calcPrice(@Param("id")Long id,@Param("goodsId")Long goodsId);

}
