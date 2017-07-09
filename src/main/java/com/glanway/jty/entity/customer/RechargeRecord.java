package com.glanway.jty.entity.customer;

import com.glanway.jty.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  充值记录
 *  @author  tianxuan
 *  @Time     2016/4/1
 *  @version 1.0
 */
public class RechargeRecord {
    private Long id;//id

    private Long memberId;//会员ID
    private Member member;

    private BigDecimal money;//充值金额

    private BigDecimal rgMoney;//返利金额

    private Date createdDate;//充值时间

    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getRgMoney() {
        return rgMoney;
    }

    public void setRgMoney(BigDecimal rgMoney) {
        this.rgMoney = rgMoney;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
