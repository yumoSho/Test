package com.glanway.jty.entity.product;

import com.glanway.jty.entity.BaseEntity;

import java.util.List;

/**
 * 模型
 */
public class Model extends BaseEntity {
    /**
     * 产品模型名称
     * @Column
     */
    private String name;
    /**
     * 模型编号
     */
    private String modelCode;

    /**
     * 产品模型别名
     * @Column
     */
    private String alias;
    /**
     * 是否启用属性
     * @Column
     */
    private Boolean useAttribute = false;
    /**
     * 是否启用规格
     * @Column
     */
    private Boolean useSpec = false;
    /**
     * 是否启用参数
     * @Column
     */
    private Boolean useParameter = false;

    /**
     * @OneToMany fetch=eager optional=true
     */
    private List<Attribute> attributes;
    /**
     * @OneToMany fetch=eager optional=true
     */
    private List<Parameter> parameters;
    /**
     * @OneToMany fetch=eager optional=true
     */
    private List<ModelSpec> modelSpecs;

    private Boolean deleted = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getUseAttribute() {
        return useAttribute;
    }

    public void setUseAttribute(Boolean useAttribute) {
        this.useAttribute = useAttribute;
    }

    public Boolean getUseSpec() {
        return useSpec;
    }

    public void setUseSpec(Boolean useSpec) {
        this.useSpec = useSpec;
    }

    public Boolean getUseParameter() {
        return useParameter;
    }

    public void setUseParameter(Boolean useParameter) {
        this.useParameter = useParameter;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }


    public List<ModelSpec> getModelSpecs() {
        return modelSpecs;
    }

    public void setModelSpecs(List<ModelSpec> modelSpecs) {
        this.modelSpecs = modelSpecs;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
