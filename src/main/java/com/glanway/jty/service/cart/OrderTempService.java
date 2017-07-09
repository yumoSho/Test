/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: OrderTempService.java
 * PackageName: com.glanway.jty.service.cart
 * Date: 2016/5/510:49
 **/
package com.glanway.jty.service.cart;

import com.glanway.jty.entity.cart.OrderTemp;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.vo.OrderTempVo;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>名称: </p>
 * <p>说明: TODO</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/510:49
 * @version: 1.0
 */
public interface OrderTempService extends BaseService<OrderTemp,Long>{
    /**
     * <p>名称：根据会员Id删除</p>
     * <p>描述：删除某个会员的所有记录</p>
     * @author：tianxuan
     * @param memberId  会员ID
     */
    void deleteByMemberId(Long memberId);

    /**
     * <p>名称：查询临时订单商品</p>
     * <p>描述：查询某会员的所有购买商品</p>
     * @author：tianxuan
     * @param memberId  会员Id
     * @return
     */
    OrderTempVo findAllByMemberId(Long memberId, Long addressId, Integer orderFrom);

    /**
     * <p>名称：立即购买商品价格计算</p>
     * <p>描述：立即购买商品价格计算（计算价格等）</p>
     * @author：tianxuan
     * @param goodsId  商品Id
     * @param buyCount  购买数量
     * @param memberId  会员Id
     * @return
     */
    void initTempGoods(Long cartId, long goodsId, int buyCount,Integer goodsFrom,Long otherId, Long memberId, HttpSession session);


    /**
     * <p>名称：立即购买商品价格计算</p>
     * <p>描述：立即购买商品价格计算（计算价格等）</p>
     * @author：tianxuan
     * @param goodsId  商品Id
     * @param buyCount  购买数量
     * @param memberId  会员Id
     * @return
     */
    void initTempGoodsByGoods(long goodsId, int buyCount,Integer goodsFrom,Long otherId, Long memberId, HttpSession session);

    /**
     * <p>名称：从购物车结算商品</p>
     * <p>描述：从购物车结算商品</p>
     * @author：tianxuan
     * @param cartIds
     * @param memberId
     */
    void initTempGoodsByCart(Long[] cartIds, Long memberId, HttpSession session);

    /**
     * 配件购买
     * @param
     * @param memberId
     */
     void initTempGoodsByPeiJian(Long[] goodsIds,Integer[] buyCountArr, Integer goodsFroms, Long otherId, Long memberId, HttpSession session) ;

    /**
     * 来自套餐
     * @param tcId  套餐id
     * @param memberId
     * @param session
     */
     void initTempGoodsByTaoCan(Long tcId ,Long memberId, HttpSession session) ;

    /**
     * <p>名称：修改购买数量</p>
     * <p>描述：修改购买数量</p>
     * @author：tianxuan
     * @param id
     * @param type
     * @param buyCount
     */
    void changeBuyCount(Long id, Integer type, Integer buyCount);

    /**
     * <p>名称：计算某用户的总购买数量和总价钱</p>
     * <p>描述：计算某用户的总购买数量和总价钱</p>
     * @author：tianxuan
     * @param memberId
     * @return
     */
    Map<String,Object> countPrce(Long memberId);
}
