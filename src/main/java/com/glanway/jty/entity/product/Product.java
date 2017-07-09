package com.glanway.jty.entity.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.logistics.HatProvince;
import org.apache.uima.alchemy.ts.entity.City;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 产品实体
 */
public class Product extends BaseEntity {
    /**
     * 产品标题
     */
    private String title;
    /**
     * 产品简介/卖点
     */
    private String intro;
    /**
     * 产品编号
     */
    private String sn;
    /**
     * 产品分类
     */
    private Category category;
    /**
     * 产品模型
     */
    private Model model;
    /**
     * 产品品牌
     */
    private Brand brand;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 配送区域
     */
    private List<HatProvince> areas;
    /**
     * 虚拟销量
     */
    private Long sales;
    /**
     * 真实销量
     */
    private Long realSales;
    /**
     * 促销信息
     */
    private String promotionalInfo;
    /**
     * 服务
     */
    private String service;
    /**
     * 温馨提示
     */
    private String tip;
    /**
     * 标签
     */
    private Label label;
    /**
     * 产品主图
     */
    private String image;
    /**
     * 产品图片
     */
    private List<ProductImg> productImgs;
    /**
     * 属性值实体集合
     */
    private List<AttributeValue> attributeValues;
    /**
     * 参数值实体集合
     */
    private List<ParameterValue> parameterValues;
    /**
     * 是否在售/上架
     */
    private Boolean isPutaway;
    /**
     * 上架时间
     */
    private Date registerDate;
    /**
     * 是否已删除
     */
    private Boolean deleted = false;
    /**
     * 是否开启规格
     */
    private Boolean enableSpecs = false;
    /**
     * 单品实体集合
     */
    @JsonBackReference
    private List<Goods> goods;
    /**
     * 下架时间
     */
    private Date salesOffDate;
    /**
     * 库存
     */
    private Long stock;
    /**
     * 警戒库存
     */
    private Long alertStock;
    /**
     * pc 端简介
     */
    private String detail;
    /**
     * mobile 端简介
     */
    private String mobileDetail;
    /**
     * SEO标题
     */
    private String seoTitle;
    /**
     * SEO关键子
     */
    private String  seoKeyword;
    /**
     * SEO描述
     */
    private String seoDescription;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public Long getRealSales() {
        return realSales;
    }

    public void setRealSales(Long realSales) {
        this.realSales = realSales;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ProductImg> getProductImgs() {
        return productImgs;
    }

    public void setProductImgs(List<ProductImg> productImgs) {
        this.productImgs = productImgs;
    }

    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public List<ParameterValue> getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(List<ParameterValue> parameterValues) {
        this.parameterValues = parameterValues;
    }

    public Boolean getIsPutaway() {
        return isPutaway;
    }

    public void setIsPutaway(Boolean isPutaway) {
        this.isPutaway = isPutaway;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getEnableSpecs() {
        return enableSpecs;
    }

    public void setEnableSpecs(Boolean enableSpecs) {
        this.enableSpecs = enableSpecs;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public Date getSalesOffDate() {
        return salesOffDate;
    }

    public void setSalesOffDate(Date salesOffDate) {
        this.salesOffDate = salesOffDate;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getAlertStock() {
        return alertStock;
    }

    public void setAlertStock(Long alertStock) {
        this.alertStock = alertStock;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMobileDetail() {
        return mobileDetail;
    }

    public void setMobileDetail(String mobileDetail) {
        this.mobileDetail = mobileDetail;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
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

    public String getPromotionalInfo() {
        return promotionalInfo;
    }

    public void setPromotionalInfo(String promotionalInfo) {
        this.promotionalInfo = promotionalInfo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public List<HatProvince> getAreas() {
        return areas;
    }

    public void setAreas(List<HatProvince> areas) {
        this.areas = areas;
    }
}
