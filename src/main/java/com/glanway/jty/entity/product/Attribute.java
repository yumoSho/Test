package com.glanway.jty.entity.product;

import com.glanway.jty.entity.BaseEntity;

import java.util.List;

/**
 * 产品属性
 */
public class Attribute extends BaseEntity {
    /**
     * 属性名称
     */
    private String name;
    /**
     * 属性别名
     */
    private String alias;
    /**
     * 显示类型
     * @Column
     */
    private Integer displayType;
    /**
     * 是否可见
     * @Column
     */
        private Boolean visible;
    /**
     * 是否基础属性
     * @Column
     */
    private Boolean isBase;
    /**
     * 排序
     * @Column
     */
    private Integer sort;
    /**
     * 是否删除
     * @Column
     */
    private Boolean deleted = false;
    /**
     * 所属模型
     * @ManyToOne
     */
    private Model model;
    /**
     * 属性值
     */
    private List<AttributeValue> attributeValues;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getIsBase() {
        return isBase;
    }

    public void setIsBase(Boolean isBase) {
        this.isBase = isBase;
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
