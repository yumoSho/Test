package com.glanway.jty.entity.marketing;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductPackage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static  final Integer  PACKAGE_TYPE_PACKAGE = 0;//配件
    public static  final Integer  PACKAGE_TYPE_ACCESSOY = 1;//配件

	private String name;	 //套餐名称

	private BigDecimal saveMoney;     //优惠金额

    @JsonUnwrapped(prefix = "primaryGoods.")
    private Goods primaryGoods;      //主单品

    private Product primaryProduct;

    private List<ProductPackageDetail> packageDetails;		//套餐详情

    private Boolean enable;		 //是否启用

	private Boolean deleted;

    private Integer type;

    private BigDecimal originalPrice;

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

    public BigDecimal getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(BigDecimal saveMoney) {
        this.saveMoney = saveMoney;
    }

    public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean value) {
		this.deleted = value;
	}

    public Product getPrimaryProduct() {
        return primaryProduct;
    }

    public void setPrimaryProduct(Product primaryProduct) {
        this.primaryProduct = primaryProduct;
    }

    public Goods getPrimaryGoods() {
        return primaryGoods;
    }

    public void setPrimaryGoods(Goods primaryGoods) {
        this.primaryGoods = primaryGoods;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<ProductPackageDetail> getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(List<ProductPackageDetail> packageDetails) {
        this.packageDetails = packageDetails;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
