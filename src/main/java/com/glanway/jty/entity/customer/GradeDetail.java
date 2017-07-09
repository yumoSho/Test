package com.glanway.jty.entity.customer;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.product.Category;

import java.math.BigDecimal;

/**
 *  会员等级实体
 *  @author  tianxuan
 *  @Time     2016/4/1
 *  @version 1.0
 */
public class GradeDetail extends BaseEntity{

    private Long gradeId; //会员等级Id
    private Grade grade;

    private Category category;//分类
    private Long categoryId;//分类Id

    private BigDecimal discount;//折扣

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

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
}
