package com.glanway.jty.entity.marketing;

import com.glanway.gone.entity.AuditEntity;
import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.Product;

import java.io.Serializable;
import java.util.Date;

public class ProductPackageDetail extends BaseEntity  {
    private static final long serialVersionUID = 1L;

	private Long packageId;

	private Product product;

	private Goods goods;

	private Boolean deleted;

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
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

	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean value) {
		this.deleted = value;
	}

}
