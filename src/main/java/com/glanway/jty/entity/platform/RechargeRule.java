package com.glanway.jty.entity.platform;

import com.glanway.jty.entity.BaseEntity;

/**
 * Created by Administrator on 2016/7/26.
 * 重置返利规则实体
 */
public class RechargeRule extends BaseEntity{

    /*充值金额*/
    private Integer money;

    /*返现比例*/
    private Integer discount;

    /*返现金额*/
    private Integer rtMoney;

    private Integer sortNum;

    private Boolean deleted;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getRtMoney() {
        return rtMoney;
    }

    public void setRtMoney(Integer rtMoney) {
        this.rtMoney = rtMoney;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
