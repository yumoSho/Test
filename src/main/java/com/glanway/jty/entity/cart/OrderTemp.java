/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: OrderTemp.java
 * PackageName: com.glanway.jty.entity.cart
 * Date: 2016/5/510:14
 **/
package com.glanway.jty.entity.cart;

import java.math.BigDecimal;

/**
 * <p>名称: 提交订单临时表</p>
 * <p>说明: 提交订单临时表</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/510:14
 * @version: 1.0
 */
public class OrderTemp {
    /**@Fields id : id */
    private Long id;

    /**@Fields goodsId : 商品Id */
    private Long goodsId;

    /**@Fields goodsName : 商品名称 */
    private String goodsName;

    /**@Fields goodsImg : 商品图片 */
    private String goodsImg;

    /**@Fields price : 单价（现价） */
    private BigDecimal price;

    /**@Fields price : 单价（原价） */
    private BigDecimal originalPrice;

    /**@Fields buyCount : 购买数量 */
    private Integer buyCount;

    /**@Fields totalPrice : 总价 */
    private BigDecimal totalPrice;

    /**@Fields memberId : 会员ID */
    private Long memberId;

    /**@Fields supplierId : 套餐Id */
    private Long supplierId;

    /**@Fields supplierId : 供应商名称 */

    /**@Fields inventory : 商品库存数，数据库中没有该字段 */
    private Integer inventory;

    private String supplierName;

    /**@Fields goodsFrom : 商品从哪里来： 0或null 没有任何折扣的普通商品，
     * 1：会员折扣商品 2：活动商品 3：限时抢购商品 4:套餐商品  */
    private Integer goodsFrom;

    /**@Fields otherId : 如果是活动商品就存下活动的id */
    private Long otherId;

    /**
     * 购物车Id
     */
    private Long cartId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getGoodsFrom() {
        return goodsFrom;
    }

    public void setGoodsFrom(Integer goodsFrom) {
        this.goodsFrom = goodsFrom;
    }

    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }
}
