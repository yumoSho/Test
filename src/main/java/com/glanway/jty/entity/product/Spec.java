package com.glanway.jty.entity.product;



import com.glanway.jty.entity.BaseEntity;

import java.util.List;

/**
 * 规格
 */
public class Spec extends BaseEntity {
	/**
     * 名称
     * @Column
     */
    private String name;
    /**
     * 规格编号
     */
    private String specCode;
    /**
     * 别名
     * @Column
     */
    private String alias;
    /**
     * 排序
     * @Column
     */
    private Integer sort;
    /**
     * 规格属性值集合
     */
    private List<SpecValue> specValues;
    /**
     * @Column
     */
    private Boolean deleted = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<SpecValue> getSpecValues() {
        return specValues;
    }

    public void setSpecValues(List<SpecValue> specValues) {
        this.specValues = specValues;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
