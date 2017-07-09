/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: sh
 * FileName: couponTempl.java
 * PackageName: com.glanway.jty.entity.operations
 * Date: 2016/6/2110:04
 **/
package com.glanway.jty.entity.operations;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>名称: </p>
 * <p>说明: 优惠券</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/6/2110:04
 * @version: 1.0
 */
public class Coupon {
    public static final int STATUS_WFF = 1;//未发放
    public static final int STATUS_YFF = 2;//已发放
    public static final int STATUS_YSY = 3;//已使用



    private Long id;

    /**@Fields storeId : 店铺ID */
    private Long storeId;
    private String  storeName;
    
    /**@Fields memberId : 会员ID */
    private Long memberId;
    private String memberName;

    private Long templateId;
    
    /**@Fields couponName : 优惠券名称 */
    private String couponName;

    /**@Fields amount : 数量 */
    private Integer amount;

    /**@Fields beginDate : 有效开始时间 */
    private Date beginDate;


    /**@Fields endDatge : 有效结束时间 */
    private Date endDate;

    /**@Fields code : 优惠券码 */
    private String  code;

    /**@Fields createdDate : 生成时间 */
    private Date createdDate;

    /**@Fields endDatge : 发放时间 */
    private Date grantDate;

    /**@Fields status : 状态 */
    private Integer status;

    /**@Fields discount : 优惠额度 */
    private BigDecimal discount;

    /**@Fields minPrice : 订单最小金额 */
    private BigDecimal minPrice;

    /**@Fields autoSend : 自动发送 */
    private Boolean autoSend;

    /**@Fields autoMinPrice : 自动发放最低金额 */
    private BigDecimal autoMinPrice;

    /**@Fields autoMaxPrice : 自动发放最大金额 */
    private BigDecimal autoMaxPrice;

    /**@Fields deleted : 是否删除 */
    private Boolean deleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Boolean getAutoSend() {
        return autoSend;
    }

    public void setAutoSend(Boolean autoSend) {
        this.autoSend = autoSend;
    }

    public BigDecimal getAutoMinPrice() {
        return autoMinPrice;
    }

    public void setAutoMinPrice(BigDecimal autoMinPrice) {
        this.autoMinPrice = autoMinPrice;
    }

    public BigDecimal getAutoMaxPrice() {
        return autoMaxPrice;
    }

    public void setAutoMaxPrice(BigDecimal autoMaxPrice) {
        this.autoMaxPrice = autoMaxPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}


