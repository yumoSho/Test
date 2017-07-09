/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: OrderTempVo.java
 * PackageName: com.glanway.hg.vo
 * Date: 2016/5/614:19
 **/
package com.glanway.jty.vo;

import com.glanway.jty.entity.cart.OrderTemp;
import com.glanway.jty.entity.operations.Coupon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>名称: </p>
 * <p>说明: 提交订单临时数据</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/614:19
 * @version: 1.0
 */
public class OrderTempVo {
    /**@Fields totalPrice : 订单总价 */
    private BigDecimal totalPriceAndTotalFreight;

    /**@Fields totalPrice : 商品总价 */
    private BigDecimal totalPrice;

    /**@Fields totalFreight : 总运费 */
    private BigDecimal totalFreight;

    /**@Fields supplierMap : 每一个供应商的商品信息 */
    private Map<Long,Map<String,Object>>  listMap;

    private List<OrderTemp> goodsList;

    /**@Fields totalNum : 商品总数量 */
    private Integer totalNum;

    /**@Fields totalNum : 优惠券列表 */
    private List<Coupon> couponList;

    /**@Fields canDelivery : 能否送货 */
    private Boolean canDelivery;

    /**@Fields baoYou : 包邮 */
    private Boolean baoYou;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**@Fields canUseCoupon : 能否使用优惠券  true :可以  false： 不可以 */
    public Boolean canUseCoupon;

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(BigDecimal totalFreight) {
        this.totalFreight = totalFreight;
    }

    public Map<Long, Map<String, Object>> getListMap() {
        return listMap;
    }

    public void setListMap(Map<Long, Map<String, Object>> listMap) {
        this.listMap = listMap;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public BigDecimal getTotalPriceAndTotalFreight() {
        return totalPriceAndTotalFreight;
    }

    public void setTotalPriceAndTotalFreight(BigDecimal totalPriceAndTotalFreight) {
        this.totalPriceAndTotalFreight = totalPriceAndTotalFreight;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public List<OrderTemp> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderTemp> goodsList) {
        this.goodsList = goodsList;
    }

    public Boolean getCanDelivery() {
        return canDelivery;
    }

    public void setCanDelivery(Boolean canDelivery) {
        this.canDelivery = canDelivery;
    }

    public Boolean getCanUseCoupon() {
        return canUseCoupon;
    }

    public void setCanUseCoupon(Boolean canUseCoupon) {
        this.canUseCoupon = canUseCoupon;
    }

    public Boolean getBaoYou() {
        return baoYou;
    }

    public void setBaoYou(Boolean baoYou) {
        this.baoYou = baoYou;
    }
}
