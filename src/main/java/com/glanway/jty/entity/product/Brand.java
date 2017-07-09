package com.glanway.jty.entity.product;


import com.glanway.jty.entity.BaseEntity;

/**
 * 品牌实体
 */
public class Brand extends BaseEntity {
    /**
     * 品牌名称
     * @Column
     */
    private String name;
    /**
     * 品牌编码
     */
    private String brandCode;
    /**
     * 品牌别名
     * @Column
     */
    private String alias;
    /**
     * 品牌 logo
     * @Column
     */
    private String logo;
    /**
     * 排序
     * @Column
     */
    private String sort;

    /**
     * 是否推荐
     * @Column
     */
    private Boolean recommend = false;

    /**
     * 是否启用
     * @Column
     */
    private Boolean enabled = false;
    /**
     * 是否删除
     * @Column
     */
    private Boolean deleted = false;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        if (id != null ? !id.equals(brand.id) : brand.id != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
