package com.glanway.jty.entity.marketing;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.glanway.gone.entity.AuditEntity;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.Product;

import java.io.Serializable;
import java.util.List;

/**
 * 商品配件
 */
public class ProductAccessory extends AuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;
	
	/**
	 * 配件组合名称
	 */
	private String name;
	
	/**
	 * 商品搭配详情实体集合
	 */
	private List<ProductAccessoryDetail> productAccessoryDetails;

    private Product primaryProduct;

    @JsonUnwrapped(prefix = "primaryGoods.")
    private Goods primaryGoods;
    /**
     * 是否已删除
     */
    private Boolean enable;

    /**
     * 新加入
     */
    private Double zprice ;

    public Double getZprice() {
        return zprice;
    }

    public void setZprice(Double zprice) {
        this.zprice = zprice;
    }

    private Boolean deleted = false;
	
	public List<ProductAccessoryDetail> getProductAccessoryDetails() {
		return productAccessoryDetails;
	}

	public void setProductAccessoryDetails(
			List<ProductAccessoryDetail> productAccessoryDetails) {
		this.productAccessoryDetails = productAccessoryDetails;
	}


	public String getName() {
		return this.name;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public Boolean getDeleted() {
		return this.deleted;
	}
	
	public void setDeleted(Boolean value) {
		this.deleted = value;
	}

    public Goods getPrimaryGoods() {
        return primaryGoods;
    }

    public void setPrimaryGoods(Goods primaryGoods) {
        this.primaryGoods = primaryGoods;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Product getPrimaryProduct() {
        return primaryProduct;
    }

    public void setPrimaryProduct(Product primaryProduct) {
        this.primaryProduct = primaryProduct;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
