/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: CartService.java
 * PackageName: com.glanway.jty.service.cart
 * Date: 2016/5/714:56
 **/
package com.glanway.jty.service.cart;

import com.glanway.jty.entity.cart.Cart;
import com.glanway.jty.service.BaseService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>名称: 购物车service</p>
 * <p>说明: 购物车service</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/714:56
 * @version: 1.0
 */
public interface CartService extends BaseService<Cart,Long> {

    /**
     * <p>名称：保存</p>
     * <p>描述：保存</p>
     * @param goodsId
     * @param buyCount
     * @param memberId
     * @author：tianxuan
     */
    Cart saveCart(Long goodsId, Integer buyCount,Integer goodsFrom,Long otherId, Long memberId);

    /**
     * <p>名称：批量添加购物车</p>
     * <p>描述：批量添加购物车</p>
     * @author：tianxuan
     * @param goodsIds  商品id集合
     * @param buyCount 购买数量
     * @param memberId huiyuanID
     * @return
     */
    List<Long> saveCartBatch(Long[] goodsIds, Integer buyCount,Integer[] goodsFrom,Long[] otherId, Long memberId);

    /**
     * <p>名称：保存到购物车</p>
     * <p>描述：从cookie 中将商品 存入数据库中</p>
     * @author：tianxuan
     * @param cartList
     * @param memberId
     */
    void saveCartBatch(List<Cart> cartList, Long memberId);
    /**
     * <p>名称：查询购物车列表</p>
     * <p>描述：查询购物车列表</p>
     * @param memberId 会员ID
     * @return
     * @author：tianxuan
     */
    Map<String,Object> findAllByMemberId(Long memberId,HttpSession session);

    /**
     * <p>名称：选中商品操作</p>
     * <p>描述：选中商品操作</p>
     * @author：tianxuan
     * @param ids
     * @param memberId
     */
    void selectGoods(Long[] ids, Long memberId);

    /**
     * 购物侧商品数量修改
     * @param id
     * @param type
     * @param buyCount
     */
    void changeBuyCount(Long id, Integer type, Integer buyCount);

    /**
     * <p>名称：</p>
     * <p>描述：移入收藏夹</p>
     * @author：tianxuan
     * @param ids
     */
    void moveToFavorite(Long[] ids, Long memberId);

    /**
     * <p>名称：删除购物车记录</p>
     * <p>描述：根据goodsId 删除购物车记录</p>
     * @author：tianxuan
     * @param
     */
    void deleteByGoodsId(List<Long> goodsList, Long memberId);

    /**
     * <p>名称：获取所有的购买数量</p>
     * <p>描述：获取所有的购买数量</p>
     * @author：tianxuan
     * @param param
     * @return
     */
    Integer  totalBuyCount(Map<String, Object> param);

    /**
     * 清空购物车
     * @param memberId
     */
    void clear(Long memberId);
}
