package com.glanway.jty.entity.product;


import com.glanway.jty.entity.BaseEntity;

/**
 * 属性值
 */
public class AttributeValue extends BaseEntity {
    /**
     * 属性值
     * @Column
     */
    private String value;
    /**
     * 属性值编码
     * @Column
     */
    private String code;
    /**
     * 属性值排序
     * @Column
     */
    private Integer sort;
    /**
     * 是否删除
     * @Column
     */
    private Boolean deleted = false;
    /**
     * 所属属性
     * @ManyToOne
     */
    private Attribute attribute;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
