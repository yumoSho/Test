package com.glanway.jty.entity.product;
import com.glanway.jty.entity.BaseEntity;

/**
 * 产品图片
 */
public class ProductImg extends BaseEntity {

	/**
     * 图片路径
     */
    private String path;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 是否已删除
     */
    private Boolean deleted=Boolean.FALSE;
    /**
     * 图片所属的产品
     */
    private Product product;
    /**
     * 图片所属的商品
     */
    private Goods goods;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
