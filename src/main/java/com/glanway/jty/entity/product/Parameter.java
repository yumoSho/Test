package com.glanway.jty.entity.product;

import com.glanway.jty.entity.BaseEntity;

import java.util.List;

/**
 * 参数
 */
public class Parameter extends BaseEntity {
    /**
     * @Column
     */
    private String name;
    /**
     * @Column
     */
    private Integer sort;
    /**
     * 是否已删除
     * @Column
     */
    private Boolean deleted = false;

    /**
     * 上级参数
     * @ManyToOne optional=true
     */
    private Parameter parent;
    /**
     * 子参数
     */
    private List<Parameter> children;
    /**
     * 所属模型
     * @ManyToOne
     */
    private Model model;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Parameter getParent() {
        return parent;
    }

    public void setParent(Parameter parent) {
        this.parent = parent;
    }

    public List<Parameter> getChildren() {
        return children;
    }

    public void setChildren(List<Parameter> children) {
        this.children = children;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
