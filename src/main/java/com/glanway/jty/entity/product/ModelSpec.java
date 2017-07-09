package com.glanway.jty.entity.product;

import com.glanway.jty.entity.BaseEntity;

/**
 * 模型规格关系
 */
public class ModelSpec extends BaseEntity {
    /**
     * @OneToOne
     * 模型
     */
    private Model model;
    /**
     * @OneToOne
     * 规格
     */
    private Spec spec;
    /**
     * @Column
     * 排序
     */
    private Integer sort;
    /**
     * @Column
     * 是否已删除
     */
    private Boolean deleted = false;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
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
}
