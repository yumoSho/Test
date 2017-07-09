package com.glanway.jty.entity.product;


import com.glanway.jty.entity.BaseEntity;

/**
 * 规格值
 *
 */
public class SpecValue extends BaseEntity {
    /**
     * 规格值名称
     */
    private String name;
    /**
     * 规格值别名
     */
    private String alias;
    /**
     * 规格值编码
     */
    private String code;
    /**
     * 规格值排序
     */
    private Integer sort;
    /**
     * 是否已删除
     */
    private Boolean deleted=false;
    /**
     * 所属规格
     */
    private Spec spec;

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

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }
}
