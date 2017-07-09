package com.glanway.jty.entity.product;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.marketing.ActivityGoods;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品实体 即单品实体
 *
 */
public class Goods extends BaseEntity {
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
     * 库存
     */
    private Long stock;
    /**
     * 警戒库存
     */
    private Long alertStock;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     * 促销价
     */
    private BigDecimal promotePrice;
    /**
     * 主图
     */
    private String image;
    /**
     * 图片
     */
    private List<ProductImg> productImgs;
    /**
     * 是否已删除
     */
    private Boolean deleted;
    /**
     * 规格值
     */
    private List<SpecValue> specValues;
    /**
     * 规格:规格值字符串, 用于 SKU 选择  这里我作为具体规格值的id使用，在加入购物车中
     * @Column
     */
    private String svStr;
    /**
     * 所属商品
     * @ManyToOne
     */
    @JsonUnwrapped(prefix = "product.")
    private Product product;

    /**
     * 是否默认
     */
    private Boolean isDefault;

    /**
     * 商品活动
     */
    private ActivityGoods activityGoods;
    
    /**
     * 订单详情ID，仅用于封装数据，T_Goods表中并无此字段
     */
    private Long tempOrderDetailId;

    private Integer goodsFrom;

    private Long otherId;

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<SpecValue> getSpecValues() {
        return specValues;
    }

    public void setSpecValues(List<SpecValue> specValues) {
        this.specValues = specValues;
    }

    public String getSvStr() {
        return svStr;
    }

    public void setSvStr(String svStr) {
        this.svStr = svStr;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(BigDecimal promotePrice) {
        this.promotePrice = promotePrice;
    }

    public ActivityGoods getActivityGoods() {
        return activityGoods;
    }

    public void setActivityGoods(ActivityGoods activityGoods) {
        this.activityGoods = activityGoods;
    }

	public Long getTempOrderDetailId() {
		return tempOrderDetailId;
	}

	public void setTempOrderDetailId(Long tempOrderDetailId) {
		this.tempOrderDetailId = tempOrderDetailId;
	}

    public Integer getGoodsFrom() {
        return goodsFrom;
    }

    public void setGoodsFrom(Integer goodsFrom) {
        this.goodsFrom = goodsFrom;
    }

    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }
}
