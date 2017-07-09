package com.glanway.jty.entity.product;

import com.glanway.jty.entity.BaseEntity;

import java.util.List;

/**
 * 分类
 */
public class Category extends BaseEntity {
    /**
     * 分类名称
     *
     * @Column
     */
    private String name;
    /**
     * 分类编号
     */
    private String categoryCode;

    /**
     * 名称高亮色
     *
     * @Column
     */
    private String color;

    /**
     * 分类图片
     * */
    private String picture;

    /**
     * 分类树的路径
     */
    private String path;

    /**
     * 分类树的名称路径
     */
    private String PathNames;
    /**
     * 是否可见/显示
     *
     * @Column
     */
    private Boolean visible;
    /**
     * 排序
     *
     * @Column
     */
    private Integer sort;

    /**
     * @OneToOne
     */
    private Model model;
    /**
     * 是否删除
     *
     * @Column
     */
    private Boolean deleted = false;
    /**
     * 关联品牌集合
     *
     * @OneToMany optional=true
     */
    private List<Brand> brands;

    /**
     * 子分类
     */
    private List<Category> categories;

    /**
     * 父级分类
     * @return
     */
    private Category parent;

    private List<Category> children;

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathNames() {
        return PathNames;
    }

    public void setPathNames(String pathNames) {
        PathNames = pathNames;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
