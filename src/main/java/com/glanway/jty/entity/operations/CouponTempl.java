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

/**
 * <p>名称: </p>
 * <p>说明: 优惠券模板</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/6/2110:04
 * @version: 1.0
 */
public class CouponTempl {
    private Long id;

    /**@Fields discount : 优惠额度 */
    private BigDecimal discount = new BigDecimal(0);

    /**@Fields minPrice : 订单最小金额 */
    private BigDecimal minPrice = new BigDecimal(0);

    /**@Fields autoSend : 自动发送 */
    private Boolean autoSend;

    /**@Fields autoMinPrice : 自动发放最低金额 */
    private BigDecimal autoMinPrice = new BigDecimal(0);

    /**@Fields autoMaxPrice : 自动发放最大金额 */
    private BigDecimal autoMaxPrice = new BigDecimal(0);

    /**@Fields name : 模板名称 */
    private String name;

    /**@Fields deleted : 是否删除 */
    private Boolean deleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
