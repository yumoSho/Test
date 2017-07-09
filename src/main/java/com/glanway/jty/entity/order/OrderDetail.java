/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p>
 * ProjectName: sh
 * FileName: OrderDetail.java
 * PackageName: com.glanway.jty.entity.order
 * Date: 2016/6/20 11:08
 **/
package com.glanway.jty.entity.order;

import java.math.BigDecimal;

/**
 * <p>名称: </p>
 * <p>说明: TODO</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：kiah
 * @date：2016/6/20 11:08
 * @version: 1.0
 */
public class OrderDetail {

    private long id;//ID

    private int seqno;//序号

    private long orderId;//订单ID

    private String orderCode;//订单号

    private long goodsId;//商品ID

    private String goodsCode;//商品编号

    private String goodsName;//商品名称

    private String goodsSpec;//商品规格

    private int goodsNum;//商品数量

    private String goodsImage;//商品图片

    private Double goodsPrice;//商品单价

    private Double safePrice;//避险价

    private Double addPrice;//加价

    private Double sellPrice;//交易价(折后价)

    private BigDecimal costPrice;//成本价

    private BigDecimal discount;//折扣金额

    private boolean deleted = false;//是否删除

    private int returnedNum;//已申请退换货数量

    private Boolean commented;//是否已评论

    /**@Fields goodsFrom : 商品从哪里来： 0或null 没有任何折扣的普通商品，
     * 1：会员折扣商品 2：活动商品 3：限时抢购商品 4:套餐商品  */
    private Integer goodsFrom;

    public int getReturnedNum() {
        return returnedNum;
    }

    public void setReturnedNum(int returnedNum) {
        this.returnedNum = returnedNum;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getSeqno() {
        return seqno;
    }
    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
    public String getGoodsCode() {
        return goodsCode;
    }
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }
    public String getGoodsSpec() {
        return goodsSpec;
    }
    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }
    public int getGoodsNum() {
        return goodsNum;
    }
    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }
    public Double getGoodsPrice() {
        return goodsPrice;
    }
    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    public Double getSafePrice() {
        return safePrice;
    }
    public void setSafePrice(Double safePrice) {
        this.safePrice = safePrice;
    }
    public Double getAddPrice() {
        return addPrice;
    }
    public void setAddPrice(Double addPrice) {
        this.addPrice = addPrice;
    }
    public Double getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Boolean getCommented() {
        return commented;
    }

    public void setCommented(Boolean commented) {
        this.commented = commented;
    }


    public Integer getGoodsFrom() {
        return goodsFrom;
    }

    public void setGoodsFrom(Integer goodsFrom) {
        this.goodsFrom = goodsFrom;
    }
}
