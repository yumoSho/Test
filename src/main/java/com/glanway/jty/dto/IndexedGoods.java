package com.glanway.jty.dto;

import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.product.AttributeValue;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.entity.product.Label;
import com.glanway.jty.entity.product.ProductImg;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 索引类
 */
public class IndexedGoods {
    private Long id;
    private String sn;
    private String title;
    private String intro;
    private String image;
    private BigDecimal price;//销售价格
    private Long sales;         // 销量
    private Date onSellDate;   // 上架时间
    private BigDecimal weight;//重量
    private Long productId;
    private String productTitle;
    private Long brandId;
    private String brandName;
    private Label label; //商品标签
    private List<Category> cats;     // 分类
    private List<ProductImg> imgs; // 商品图片
    private List<HatProvince> provinces;//银行
    private List<AttributeValue> propVals;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Date getOnSellDate() {
        return onSellDate;
    }

    public void setOnSellDate(Date onSellDate) {
        this.onSellDate = onSellDate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public List<Category> getCats() {
        return cats;
    }

    public void setCats(List<Category> cats) {
        this.cats = cats;
    }

    public List<ProductImg> getImgs() {
        return imgs;
    }

    public void setImgs(List<ProductImg> imgs) {
        this.imgs = imgs;
    }

    public List<HatProvince> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<HatProvince> provinces) {
        this.provinces = provinces;
    }

    public List<AttributeValue> getPropVals() {
        return propVals;
    }

    public void setPropVals(List<AttributeValue> propVals) {
        this.propVals = propVals;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
