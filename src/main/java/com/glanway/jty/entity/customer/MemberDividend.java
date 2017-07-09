package com.glanway.jty.entity.customer;

import com.glanway.jty.entity.product.Category;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/8/2.
 * 会员分红设置
 */
public class MemberDividend {
    private Long id; //id

    private Category category;//分类
    private Long categoryId;//分类Id

    private BigDecimal discount;//折扣

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
