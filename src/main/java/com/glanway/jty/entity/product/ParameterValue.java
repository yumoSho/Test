package com.glanway.jty.entity.product;

import com.glanway.jty.entity.BaseEntity;

/**
 * 产品参数值
 */
public class ParameterValue extends BaseEntity{
    /**
     * 参数值
     * @Column
     */
    private String value;
    /**
     * @Column 排序
     */
    private Integer sort;
    /**
     * @Column
     */
    private Boolean deleted = false;

    /**
     * 所属参数
     * @see Parameter
     * @ManyToOne
     */
    private Parameter parameter;
    /**
     * 所属产品
     * @see Product
     * @OneToOne
     */
    private Product product;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
