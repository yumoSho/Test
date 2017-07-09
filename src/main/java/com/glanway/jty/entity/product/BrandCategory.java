package com.glanway.jty.entity.product;


import com.glanway.jty.entity.BaseEntity;

/**
 * 品牌分类表（三方表）
 */
public class BrandCategory extends BaseEntity{
    /**
     * 品牌
     */
	private Brand brand;
    /**
     * 分类
     */
	private Category category;
    /**
     * 是否已删除
     */
    private Boolean deleted;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
