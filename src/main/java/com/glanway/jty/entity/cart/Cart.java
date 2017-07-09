/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: Cart.java
 * PackageName: com.glanway.jty.entity.cart
 * Date: 2016/5/714:07
 **/
package com.glanway.jty.entity.cart;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>名称: 购物车</p>
 * <p>说明: 购物车</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/714:07
 * @version: 1.0
 */
public class Cart {
   /**@Fields id : id */
    private Long id;

    /**@Fields GOODS_ID : 商品Id */
    private Long goodsId;

    /**@Fields GOODS_NAME: 商品名称 */
    private String goodsName;

    /**@Fields GOODS_IMG : 商品图片 */
    private String goodsImg;

    /**@Fields BUY_COUNT : 购买数量 */
    private Integer buyCount;

    /**@Fields PRICE : 单价 数据库无此字段*/
    private BigDecimal price;

    /**@Fields TOTAL_PRICE : 总价 数据库无此字段*/
    private BigDecimal totalPrice;

    /**@Fields MEMBER_ID : 会员Id */
    private Long memberId;

    /**@Fields CREATED_DATE : 创建时间 */
    private Date createdDate;

    /**@Fields SELECTED : 是否选中 */
    private Boolean selected = false;

    /**@Fields isLive : 是否可用，数据库中没有该字段 */
    private Boolean isLive = false;

    /**@Fields inventory : 商品库存数，数据库中没有该字段 */
    private Integer inventory;

    /**@Fields specOne : 描述 属性一*/
    private String specOne;

    private String specTwo;

    /**@Fields goodsFrom : 商品从哪里来： 0或null 没有任何折扣的普通商品，
     * 1：会员折扣商品 2：活动商品 3：限时抢购商品 4:套餐商品  */
    private Integer goodsFrom;

    /**@Fields otherId : 如果是活动商品就存下活动的id */
    private Long otherId;

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

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getSpecOne() {
        return specOne;
    }

    public void setSpecOne(String specOne) {
        this.specOne = specOne;
    }

    public String getSpecTwo() {
        return specTwo;
    }

    public void setSpecTwo(String specTwo) {
        this.specTwo = specTwo;
    }

    public Integer getGoodsFrom() {
        if(this.goodsFrom == null){
            return 0;
        }
        return goodsFrom;
    }

    public void setGoodsFrom(Integer goodsFrom) {
        this.goodsFrom = goodsFrom;
    }

    public Long getOtherId() {
        if(this.otherId == null){
            return 0L;
        }
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }
}
