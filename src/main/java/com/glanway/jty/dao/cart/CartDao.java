/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: CartDao.java
 * PackageName: com.glanway.jty.dao.cart
 * Date: 2016/5/714:14
 **/
package com.glanway.jty.dao.cart;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.cart.Cart;

import java.util.List;
import java.util.Map;

/**
 * <p>名称: 购物车Dao</p>
 * <p>说明: 购物车Dao</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/714:14
 * @version: 1.0
 */
public interface CartDao extends BaseDao<Cart,Long> {

    /**
     * <p>名称：查询购物车列表</p>
     * <p>描述：查询某会员的所有购物车商品</p>
     * @author：tianxuan
     * @param memberId  会员Id
     * @return
     */
    List<Cart> findAllByMemberId(Long memberId);

    List<Cart> findByParam(Map param);

    /**
     * <p>名称：所有选中的id</p>
     * <p>描述：所有选中的id</p>
     * @author：tianxuan
     * @param memberId
     * @return
     */
    List<Long> findSelectedIdList(Long memberId);

    /**
     * <p>名称：取消所有选中商品</p>
     * <p>描述：取消所有选中商品</p>
     * @author：tianxuan
     * @param memberId
     */
    void cancelSelect(Long memberId);

    /**
     * <p>名称：</p>
     * <p>描述：选中商品</p>
     * @author：tianxuan
     * @param ids
     */
    void selectGoods(Map<String, Object> param);

    /**
     * <p>名称：删除购物车记录</p>
     * <p>描述：根据goodsId 删除购物车记录</p>
     * @author：tianxuan
     * @param param
     */
    void deleteByGoodsId(Map<String, Object> param);

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
