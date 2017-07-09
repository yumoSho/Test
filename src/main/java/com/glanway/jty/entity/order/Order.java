/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p>
 * ProjectName: sh
 * FileName: Order.java
 * PackageName: com.glanway.jty.entity.order
 * Date: 2016/6/20 11:06
 **/
package com.glanway.jty.entity.order;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.customer.Member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>名称: </p>
 * <p>说明: 订单</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：kiah
 * @date：2016/6/20 11:06
 * @version: 1.0
 */
public class Order extends BaseEntity {
    private String code;//订单号

    private Integer status;//订单状态

    private Double price;//订单金额

    private BigDecimal discount;//优惠金额

    private Double payPrice;//支付金额

    private Date payDate;//支付时间

    private String psDate;//配送时间

    private Date finishDate;//交易成功时间

    private Date deliveryDate;//发货时间

    private Date receiveDate;//收货时间

    private String payment;//支付方式

    private String bank;//银行卡号

    private String source;//订单来源

    private Long storeId;//店铺ID

    private String storeName;//店铺名称

    private Long memberId;//会员ID

    private String memberName;//会员名称

    private String receiver;//收货人

    private String contact;//联系方式

    private String address;//收货地址

    private Long deliverId;//物流区域ID

    private Double deliverPrice;//物流费用

    private boolean deleted = false;//是否删除

    private String deliverCompany;//物流公司

    private String deliverCompanyCode;//物流公司编码

    private String deliverCode;//物流单号

    private List<OrderDetail> orderDetails;//订单详情

    private Member member;//会员

    /**
     * @Fields groupCode : 订单所属 组编号
     */
    private String groupCode;

    private Double rate;//费率

    /**
     * @Fields couponId : 优惠券Id
     */
    private Long couponId;

    /**
     * @Fields couponName : 优惠券名称
     */
    private String couponName;

    /**@Fields remark : 备注 */
    private String remark;

    //套餐
    private Long otherId;


    public String getDeliverCompany() {
        return deliverCompany;
    }

    public void setDeliverCompany(String deliverCompany) {
        this.deliverCompany = deliverCompany;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Long deliverId) {
        this.deliverId = deliverId;
    }

    public Double getDeliverPrice() {
        return deliverPrice;
    }

    public void setDeliverPrice(Double deliverPrice) {
        this.deliverPrice = deliverPrice;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeliverCode() {
        return deliverCode;
    }

    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }


    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getDeliverCompanyCode() {
        return deliverCompanyCode;
    }

    public void setDeliverCompanyCode(String deliverCompanyCode) {
        this.deliverCompanyCode = deliverCompanyCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPsDate() {
        return psDate;
    }

    public void setPsDate(String psDate) {
        this.psDate = psDate;
    }


    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }
}
