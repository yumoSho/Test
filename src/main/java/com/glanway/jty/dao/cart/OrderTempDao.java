/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: OrderTempDao.java
 * PackageName: com.glanway.jty.dao.cart
 * Date: 2016/5/510:12
 **/
package com.glanway.jty.dao.cart;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.cart.OrderTemp;

import java.util.List;
import java.util.Map;

/**
 * <p>名称: 提交订单临时表</p>
 * <p>说明: 提交订单临时表</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/510:12
 * @version: 1.0
 */
public interface OrderTempDao extends BaseDao<OrderTemp,Long> {
    /**
     * <p>名称：根据会员Id删除</p>
     * <p>描述：删除某个会员的所有记录</p>
     * @author：tianxuan
     * @param memberId
     */
    void deleteByMemberId(Long memberId);

    /**
     * <p>名称：查询临时订单商品</p>
     * <p>描述：查询某会员的所有购买商品</p>
     * @author：tianxuan
     * @param memberId  会员Id
     * @return
     */
    List<OrderTemp> findAllByMemberId(Long memberId);

    /**
     * <p>名称：计算某用户的总购买数量和总价钱</p>
     * <p>描述：计算某用户的总购买数量和总价钱</p>
     * @author：tianxuan
     * @param memberId
     * @return
     */
    Map<String,Object> countPrce(Long memberId);
}
