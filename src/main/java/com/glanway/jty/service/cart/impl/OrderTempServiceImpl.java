/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: OrderTempServiceImpl.java
 * PackageName: com.glanway.jty.service.cart.impl
 * Date: 2016/5/510:50
 **/
package com.glanway.jty.service.cart.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.cart.CartDao;
import com.glanway.jty.dao.cart.OrderTempDao;
import com.glanway.jty.dao.marketing.ProductPackagesDao;
import com.glanway.jty.dao.marketing.ProductPackagesDetailDao;
import com.glanway.jty.dao.personalcenter.DeliveryAddressDao;
import com.glanway.jty.entity.cart.Cart;
import com.glanway.jty.entity.cart.OrderTemp;
import com.glanway.jty.entity.marketing.ProductPackage;
import com.glanway.jty.entity.marketing.ProductPackageDetail;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.cart.OrderTempService;
import com.glanway.jty.service.logistics.SupplierAreaService;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.utils.CacheUtil;
import com.glanway.jty.vo.OrderTempVo;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>名称: 提交订单临时表 service</p>
 * <p>说明: 提交订单临时表 service</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/5/510:50
 * @version: 1.0
 */
@Service
@Transactional
public class OrderTempServiceImpl extends BaseServiceImpl<OrderTemp, Long> implements OrderTempService {

    @Autowired
    private OrderTempDao orderTempDao;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SupplierAreaService supplierAreaService;
    @Autowired
    private DeliveryAddressDao deliveryAddressDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    ProductPackagesDao productPackagesDao;
    @Autowired
    ProductPackagesDetailDao productPackagesDetailDao;
    @Autowired
    CacheUtil cacheUtil;

    /**
     * <p>名称：</p>
     * <p>描述：根据会员ID删除</p>
     *
     * @param memberId 会员ID
     * @author：tianxuan
     * @see OrderTempService#deleteByMemberId(Long)
     */
    @Override
    public void deleteByMemberId(Long memberId) {
        orderTempDao.deleteByMemberId(memberId);
    }

    /**
     * <p>名称：查询临时订单商品</p>
     * <p>描述：查询某会员的所有购买商品</p>
     *
     * @param memberId 会员Id
     * @return
     * @author：tianxuan
     * @see
     */
    @Override
    public OrderTempVo findAllByMemberId(Long memberId,Long addressId, Integer orderFrom) {
        OrderTempVo orderTempVo = new OrderTempVo();
        List<OrderTemp> orderTempList = orderTempDao.findAllByMemberId(memberId);
        DeliveryAddress deliveryAddress;
        if(null != addressId){
            deliveryAddress = deliveryAddressDao.find(addressId);
        }else{
            deliveryAddress = deliveryAddressDao.findOne(ImmutableMap.<String, Object>of("memberId",memberId));
        }

        /*计算总价、总数量和运费*/
        BigDecimal totalFreight = new BigDecimal(-1);
        if(null != deliveryAddress){
            BigDecimal freight = supplierAreaService.getPriceBySupplierIdAndCity(deliveryAddress.getCityCode()) ;
            totalFreight = (null != freight ? freight : totalFreight);
        }
        orderTempVo.setTotalFreight(totalFreight.setScale(2, BigDecimal.ROUND_HALF_DOWN));

        orderTempVo.setGoodsList(orderTempList);//本次订单商品
        //总价与总数量查询的map
        Map<String,Object> totalMap = this.countPrce(memberId);
        if(null != totalMap){
            //购买总数量
            orderTempVo.setTotalNum(((BigDecimal)totalMap.get("totalNum")).intValue());
            BigDecimal goodsFrom = (BigDecimal)totalMap.get("goodsFrom");
            if(null == goodsFrom || goodsFrom.doubleValue() < 1){
                orderTempVo.setCanUseCoupon(Boolean.TRUE);
            }else {
                orderTempVo.setCanUseCoupon(Boolean.FALSE);
            }
            //商品总价
            if(Constants.ORDER_FROM_TC != orderFrom){
                orderTempVo.setTotalPrice((BigDecimal)totalMap.get("totalPrice"));
            }else {
                cacheUtil.setCache("common.cache");
                 Long tcId = Long.valueOf(cacheUtil.getCacheValue("MEMBER_CT_ID_" + memberId).toString());
                ProductPackage productPackage = productPackagesDao.getPackageById(tcId);
                orderTempVo.setTotalPrice(productPackage.getSaveMoney());
                cacheUtil.removeCacheValue("MEMBER_CT_ID" + memberId);//从cache中去掉 套餐id
            }

            BigDecimal total =  orderTempVo.getTotalPrice();
            orderTempVo.setCanDelivery(Boolean.FALSE);
            orderTempVo.setBaoYou(Boolean.FALSE);

//                if((orderTempList.size() < 5) && (total.doubleValue() < 100)){
                //满100元包邮
                if(total.doubleValue() < 100){
                    if(totalFreight.doubleValue() > -1){
                        orderTempVo.setCanDelivery(Boolean.TRUE);
                    }
                    total = total.add(orderTempVo.getTotalFreight());
                }else if(totalFreight.doubleValue() > -1){
                    orderTempVo.setCanDelivery(Boolean.TRUE);
                    orderTempVo.setBaoYou(Boolean.TRUE);
                }

            orderTempVo.setTotalPriceAndTotalFreight(total.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        return orderTempVo;
    }

    /**
     * <p>名称：初始化要购买的商品信息</p>
     * <p>描述：初始化要购买的商品信息（计算价格等）</p>
     *
     * @param goodsId  商品id
     * @param buyCount 购买数量
     * @param memberId 会员Id
     * @return
     * @author：tianxuan
     * @see OrderTempService#(long, int, Long)
     */
    @Override
    public void initTempGoods(Long cartId, long goodsId, int buyCount, Integer goodsFrom,Long otherId, Long memberId, HttpSession session) {
        List<Goods> goodsList = goodsService.findMany(Filters.create().eq("id", goodsId), Sort.create());
        Goods goods = goodsList.get(0);
        //该商品的库存数量
        Integer inventoryNums = goodsService.getStock(goodsId);
        if(buyCount > inventoryNums){
            throw new CustomException("商品数量超限");
        }

        // 计算goods的真实价格（调用GoodService方法）
        goodsService.calcMemberPriceAtEveryOne(goods,goodsFrom,otherId,session);

        OrderTemp orderTemp = new OrderTemp();
        orderTemp.setCartId(cartId);
        orderTemp.setGoodsFrom(goods.getGoodsFrom());
        orderTemp.setOtherId(goods.getOtherId());
        orderTemp.setMemberId(memberId);
        orderTemp.setGoodsName(goods.getTitle());
        orderTemp.setGoodsImg(goods.getImage());
        orderTemp.setGoodsId(goods.getId());
        orderTemp.setBuyCount(buyCount);
        // 计算商品价格
        BigDecimal price = goods.getPromotePrice();
        orderTemp.setPrice(price);//现价
        orderTemp.setOriginalPrice(goods.getPrice());
        BigDecimal totalPrice = price.multiply(new BigDecimal(buyCount)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        orderTemp.setTotalPrice(totalPrice);
        orderTempDao.save(orderTemp);
    }


    @Override
    public void initTempGoodsByGoods(long goodsId, int buyCount,Integer goodsFrom,Long otherId, Long memberId, HttpSession session) {
        orderTempDao.deleteByMemberId(memberId);//删除之前的旧数据
        this.initTempGoods(null,goodsId,buyCount,goodsFrom,otherId,memberId,session);
    }

    /**
     * <p>名称：从购物车结算商品</p>
     * <p>描述：从购物车结算商品</p>
     * @author：tianxuan
     * @param cartIds
     * @param memberId
     */
    @Override
    public void initTempGoodsByCart(Long[] cartIds, Long memberId , HttpSession session) {
        orderTempDao.deleteByMemberId(memberId);//删除之前的旧数据
        for(Long id :cartIds){
            Cart cart = cartDao.find(id);
            this.initTempGoods(cart.getId(),cart.getGoodsId(),cart.getBuyCount(),cart.getGoodsFrom(), cart.getOtherId(),memberId,session);
        }
    }

    /**
     * 配件立即购买
     * @param
     * @param memberId
     */
    @Override
    public void initTempGoodsByPeiJian(Long[] goodsIds,Integer[] buyCountArr,Integer goodsFroms, Long otherId,Long memberId, HttpSession session) {
        orderTempDao.deleteByMemberId(memberId);//删除之前的旧数据
        for(int i = 0 ;i <  goodsIds.length ; i++){
            Long id = goodsIds[i];  //商品id
            Integer buyCount = 1;
            if (null  != buyCount && (i==0 || buyCountArr.length > 1)){
                buyCount = buyCountArr[i];
            }
            Integer goodsFrom = 0;
            Long oid = null;
            if(0 == i){
                goodsFrom =goodsFroms;
                oid = otherId;
            }
            this.initTempGoods(null,id, buyCount ,goodsFrom,oid,memberId,session);
        }
    }

    /**
     * 套餐购买
     * @param tcId  套餐id
     * @param memberId
     * @param session
     */
    @Override
    public void initTempGoodsByTaoCan(Long tcId, Long memberId, HttpSession session) {
        orderTempDao.deleteByMemberId(memberId);//删除之前的旧数据
        ProductPackage productPackage = productPackagesDao.getPackageById(tcId);
        List<ProductPackageDetail> packageDetailList = productPackage.getPackageDetails();
        this.initTempGoods(null,productPackage.getPrimaryGoods().getId(), 1 , Constants.GOODS_FROM_TC,tcId,memberId,session);
        for(ProductPackageDetail detail :  packageDetailList){
            this.initTempGoods(null,detail.getGoods().getId(), 1 , Constants.GOODS_FROM_TC,tcId,memberId,session);
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
     * @see OrderTempService#changeBuyCount(Long, Integer, Integer)
     */
    @Override
    public void changeBuyCount(Long id, Integer type, Integer buyCount) {
        OrderTemp orderTemp = orderTempDao.find(id);
        // 查询商品的的库存
        Integer inventoryNums = goodsService.getStock(orderTemp.getGoodsId());
        Integer _buyCount = orderTemp.getBuyCount();
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

        BigDecimal price = orderTemp.getPrice();
        BigDecimal totalPrice = price.multiply(new BigDecimal(_buyCount)).setScale(0, BigDecimal.ROUND_HALF_DOWN);
        orderTemp.setTotalPrice(totalPrice);
        orderTemp.setBuyCount(_buyCount);
        orderTempDao.update(orderTemp);
    }

    /**
     * <p>名称：计算某用户的总购买数量和总价钱</p>
     * <p>描述：计算某用户的总购买数量和总价钱</p>
     *
     * @param memberId
     * @return
     * @author：tianxuan
     * @see OrderTempService#countPrce(Long)
     */
    @Override
    public Map<String, Object> countPrce(Long memberId) {
        return orderTempDao.countPrce(memberId);
    }
}
