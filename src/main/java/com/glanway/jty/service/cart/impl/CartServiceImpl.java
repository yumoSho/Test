/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: CartServiceImpl.java
 * PackageName: com.glanway.jty.service.cart.impl
 * Date: 2016/5/714:58
 **/
package com.glanway.jty.service.cart.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.jty.dao.cart.CartDao;
import com.glanway.jty.entity.cart.Cart;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.SpecValue;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.cart.CartService;
import com.glanway.jty.service.personalcenter.CollectService;
import com.glanway.jty.service.product.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>名称:购物车Service </p>
 * <p>说明: 购物车Service</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/5/714:58
 * @version: 1.0
 */
@Service
@Transactional
public class CartServiceImpl extends BaseServiceImpl<Cart, Long> implements CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CollectService collectService;

    /**
     * <p>名称：保存</p>
     * <p>描述：保存</p>
     * @param goodsId
     * @param buyCount
     * @param memberId
     * @author：tianxuan
     * @see CartService#saveCart(Long, Integer, Integer goodsFrom,Long otherId,Long)
     */
    @Override
    public Cart saveCart(Long goodsId, Integer buyCount,Integer goodsFrom,Long otherId, Long memberId) {
        if (buyCount < 1) {
            throw new CustomException("参数不合法");
        }
        Map<String,Object> param =  new HashMap<>();
        param.put("goodsId",goodsId);
        param.put("memberId",memberId);


        List<Goods> goodsList = goodsService.findMany(Filters.create().eq("id", goodsId), Sort.create());
        Goods goods = goodsList.get(0);
        Cart cart = new Cart();
        cart.setMemberId(memberId);
        cart.setGoodsId(goodsId);
        cart.setGoodsName(goods.getTitle());
        cart.setGoodsImg(goods.getImage());
        cart.setSelected(Boolean.TRUE);
        cart.setGoodsFrom(goodsFrom);
        cart.setOtherId(otherId);
        List<SpecValue> specValues = goods.getSpecValues();
        if (null == specValues) {
            specValues = new ArrayList<>();
        }
         final StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < specValues.size() &&i < 2) {
            stringBuffer.setLength(0);
            SpecValue sv = specValues.get(i);
            stringBuffer.append(sv.getSpec().getName() + ":" + sv.getName()).append(" ");
            if(i == 0) {
                cart.setSpecOne(stringBuffer.toString());
            }else {
                cart.setSpecOne(stringBuffer.toString());
            }
            i++;
        }
        List<Cart> cts = cartDao.findByParam(param);
        Cart oldCart = null;
        for(Cart ct : cts){
            Integer gf = ct.getGoodsFrom();
            Integer gf2 = cart.getGoodsFrom();
            gf =  null == gf ? 0  : gf;
            gf2 = null == gf2 ? 0 : gf2;
            if(gf == gf2){
                oldCart = ct;
            }
        }
        if(null != oldCart){
            cart.setId(oldCart.getId());
            cart.setBuyCount(buyCount + oldCart.getBuyCount());
            cartDao.update(cart);
        }else{
            cart.setBuyCount(buyCount);
            cartDao.save(cart);
        }
        return cart;
    }

    /**
     * <p>名称：批量添加购物车</p>
     * <p>描述：批量添加购物车</p>
     * @author：tianxuan
     * @param goodsIds  商品id集合
     * @param buyCount 购买数量
     * @param memberId 会员Id
     * @return
     */
    public List<Long> saveCartBatch(Long[] goodsIds, Integer buyCount, Integer[] goodsFrom,Long[] otherId,Long memberId) {
        List<Long>  cartIds = new ArrayList<>();
        cartDao.cancelSelect(memberId);
        if(null == goodsIds){
            throw new CustomException("参数不合法");
        }
        for(int i=0; i<goodsIds.length;i++){
            Long goodsId = goodsIds[i];
            Integer goodsFromTmp = null;
            Long otherIdTmp = null;
            if(null != goodsFrom && goodsFrom.length > i ){
                goodsFromTmp = goodsFrom[i];
                if(i < otherId.length){
                    otherIdTmp = otherId[i];
                }
            }
            Cart cart = this.saveCart(goodsId,buyCount,goodsFromTmp,otherIdTmp,memberId);
            cartIds.add(cart.getId());
        }
        return cartIds;
    }

    /**
     * <p>名称：保存到购物车</p>
     * <p>描述：从cookie 中将商品 存入数据库中</p>
     * @author：tianxuan
     * @param cartList
     * @param memberId
     * @see CartService#saveCartBatch(List, Long)
     */
    public void saveCartBatch(List<Cart> cartList, Long memberId) {
        cartDao.cancelSelect(memberId);
        if(null == cartList){
            throw new CustomException("参数不合法");
        }
        for(Cart cart :cartList){
           this.saveCart(cart.getGoodsId(),cart.getBuyCount(),cart.getGoodsFrom(),cart.getOtherId(),memberId);
        }
    }
    /**
     * <p>名称：查询购物车列表</p>
     * <p>描述：查询购物车列表</p>
     * @param memberId
     * @return
     * @author：tianxuan
     */
    @Override
    public Map<String, Object> findAllByMemberId(Long memberId, HttpSession session) {
        Map<String, Object> rtMap = new HashMap<>();
        //购物车list
        List<Cart> cartList = cartDao.findAllByMemberId(memberId);
        //购物车中的 商品Id 集合
        List<Long> goodsIdList = new ArrayList<>();
        //转为 以 goodsId 为 kyemap
        Map<Long, List<Cart>> cartMap = new HashMap<>();
        //转为以Id 为key 的 map
        Map<Long, Cart> cartMapById = new HashMap<>();
        /*为map赋值*/
        for (Cart cart : cartList) {
            Long goodsId = cart.getGoodsId();
            goodsIdList.add(goodsId);
            List<Cart> carts;
            if(cartMap.containsKey(goodsId)){
                carts = cartMap.get(goodsId);
            }else {
                 carts = new ArrayList<>();
            }
            carts.add(cart);
            cartMap.put(goodsId, carts);
            cartMapById.put(cart.getId(), cart);
        }

        //购物车中的所有商品
        List<Goods> goodsList = goodsService.findMany(Filters.create().eq("id", goodsIdList.toArray()), Sort.create());
        /*获取商品的最新价格 及 选中商品的总数量 和 总价格*/
        Integer totalBuyCount = 0;  //选中的商品总数量
        BigDecimal totalPrice = new BigDecimal(0); //选中商品总价格
        for (Goods goods : goodsList) {
            if(!goods.getProduct().getIsPutaway()){
               continue;
            }
            Long goodsId = goods.getId();
            if (cartMap.containsKey(goodsId)) {
                //该商品的库存数量
                Integer inventoryNums = goodsService.getStock(goodsId);
                List<Cart> carts = cartMap.get(goodsId);
                if(carts.size() < 2 ){
                    Cart cart = carts.get(0);
                    //计算商品价格
                    goodsService.calcMemberPriceAtEveryOne(goods,cart.getGoodsFrom(),cart.getOtherId(),session);
                    cart.setInventory(inventoryNums);
                    cart.setGoodsName(goods.getTitle());
                    cart.setGoodsImg(goods.getImage());
                    //商品促销价（未作活动的商品促销价就是 会员折扣后的价格，即就是最终交易价）
                    BigDecimal price = goods.getPromotePrice();
                    BigDecimal singleTotalPrice = price.multiply(new BigDecimal(cart.getBuyCount())).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                    cart.setPrice(price); //单价
                    cart.setTotalPrice(singleTotalPrice);//总价
                    cart.setIsLive(true);

                /*计算选中商品总价 和 总数量*/
                    if(cart.getSelected()){
                        totalBuyCount += cart.getBuyCount();
                        totalPrice = totalPrice.add(cart.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                    }
                }else if (carts.size() > 1){
                    for(int i=0; i<carts.size();i++) {
                        Cart cart = carts.get(i);
                        //计算商品价格
                        goodsService.calcMemberPriceAtEveryOne(goods,cart.getGoodsFrom(),cart.getOtherId(),session);
                        cart.setInventory(inventoryNums);
                        cart.setGoodsName(goods.getTitle());
                        cart.setGoodsImg(goods.getImage());
                        //商品促销价（未作活动的商品促销价就是 会员折扣后的价格，即就是最终交易价）
                        BigDecimal price = goods.getPromotePrice();
                        BigDecimal singleTotalPrice = price.multiply(new BigDecimal(cart.getBuyCount())).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                        cart.setPrice(price); //单价
                        cart.setTotalPrice(singleTotalPrice);//总价
                        cart.setIsLive(true);

                /*计算选中商品总价 和 总数量*/
                        if(cart.getSelected()){
                            totalBuyCount += cart.getBuyCount();
                            totalPrice = totalPrice.add(cart.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                        }
                    }
                }

            }
        }
        rtMap.put("cartList",cartList);
        rtMap.put("totalBuyCount",totalBuyCount);
        rtMap.put("totalPrice",totalPrice);
        return rtMap;
    }

    /**
     * <p>名称：选中商品操作</p>
     * <p>描述：选中商品操作</p>
     * @author：tianxuan
     * @param ids
     * @param memberId
     * @see CartService#selectGoods(Long[], Long)
     */
    @Override
    public void selectGoods(Long[] ids, Long memberId) {
        Map<String,Object> param = new HashMap<>();
        param.put("ids",ids);
        param.put("memberId",memberId);
        cartDao.cancelSelect(memberId);//取消所有选中
        if(null != ids && ids.length>0){
            cartDao.selectGoods(param);  //从新设置选中
        }
    }

    /**
     * <p>名称：修改购买数量</p>
     * <p>描述：修改购买数量</p>
     *
     * @param id
     * @param type
     * @param buyCount
     * @author：tianxuan
     * @see CartService#changeBuyCount(Long, Integer, Integer)
     */
    @Override
    public void changeBuyCount(Long id, Integer type, Integer buyCount) {
        Cart cart = cartDao.find(id);
        // 查询商品的的库存
        Integer inventoryNums = goodsService.getStock(cart.getGoodsId());
        Integer _buyCount = cart.getBuyCount();
        if (1 == type) {
            _buyCount += 1;
            if (_buyCount > inventoryNums) {
//                throw new CustomException("商品数量不能大于" + inventoryNums);
                throw new CustomException("商品数量超限");
            }
        } else if (2 == type) {
            if (_buyCount <= 1) {
//                throw new CustomException("商品数量不能小于1");
                throw new CustomException("商品数量超限");
            }
            _buyCount -= 1;
        } else if (3 == type) {
            if (buyCount > inventoryNums || buyCount < 1) {
//                throw new CustomException("商品数量必须大于 0 小于 " + inventoryNums);
                throw new CustomException("商品数量超限");
            }
            _buyCount = buyCount;
        } else {
            throw new CustomException("参数错误");
        }

        cart.setBuyCount(_buyCount);
        cartDao.update(cart);
    }


    /**
     * <p>名称：</p>
     * <p>描述：移入收藏夹</p>
     * @author：tianxuan
     * @param ids
     */
    @Override
    public void moveToFavorite(Long[] ids, Long memberId) {
        for(Long id : ids){
            Cart cart = cartDao.find(id);
            collectService.addCollect(memberId,cart.getGoodsId(),cart.getGoodsFrom(),cart.getOtherId());
            cartDao.delete(id);
        }
    }
    /**
     * <p>名称：删除购物车记录</p>
     * <p>描述：根据goodsId 删除购物车记录</p>
     * @author：tianxuan
     * @param
     */
    @Override
    public void deleteByGoodsId(List<Long> goodsList,Long memberId) {
        Map<String,Object> param = new HashMap<>();
        param.put("cartIds",goodsList.toArray());
        param.put("memberId",memberId);
        cartDao.deleteByGoodsId(param);
    }

    /**
     * <p>名称：获取所有的购买数量</p>
     * <p>描述：获取所有的购买数量</p>
     * @author：tianxuan
     * @param param
     * @return
     */
    @Override
    public Integer totalBuyCount(Map<String, Object> param) {
        return cartDao.totalBuyCount(param);
    }

    @Override
    public void clear(Long memberId) {
        cartDao.clear(memberId);
    }
}
