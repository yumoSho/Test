/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: hg
 * FileName: ActivityGoods.java
 * PackageName: com.glanway.hg.entity.activity
 * Date: 2016年4月29日下午5:48:34
 **/
package com.glanway.jty.entity.marketing;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.product.Goods;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>名称：限时低价商品</p>
 * <p>描述：限时低价商品</p>
 * @author：LiuJC
 * @date：2016/5/13 14:04
 * @version: 1.0
 */
public class DiscountGoods extends BaseEntity {
    /**@Fields  : 商品 */
    private Goods goods;
    /**@Fields  : 排序 */
    private Integer sort;
    /**@Fields  : 活动价 */
    private BigDecimal discount;
    /**@Fields  : 是否已删除 */
    private Boolean deleted = Boolean.FALSE;
    /**@Fields  : 活动开始时间 */
    private Date startDate;
    /**@Fields  : 活动结束时间 */
    private Date endDate;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
