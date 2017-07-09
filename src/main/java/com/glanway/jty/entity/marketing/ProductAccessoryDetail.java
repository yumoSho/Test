package com.glanway.jty.entity.marketing;

import com.glanway.gone.entity.AuditEntity;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.entity.product.Product;

import java.io.Serializable;
/**
 * <p>名称：ProductAccessoryDetail</p>
 * <p>描述：配件详情</p>
 * @author：LiuJC
 */
public class ProductAccessoryDetail extends AuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;
	
    /**
     * 商品配件组合详情ID
     */
	private String id;
	
	/**
	 * 配件实体对象
	 */
    private String accessoryId;
	
	/**
	 * 商品实体对象
	 */
	private Product product;
	
	/**
	 * 单品实体对象
	 */
	private Goods goods;
	
	/**
	 * 是否主要配件
	 */
	private Boolean isMain = false;
	
	/**
	 * 是否已删除
	 */
	private Boolean deleted = false;

    public String getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(String accessoryId) {
        this.accessoryId = accessoryId;
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

	public String getId() {
		return this.id;
	}
	
	public void setId(String value) {
		this.id = value;
	}
	
	public Boolean getIsMain() {
		return this.isMain;
	}
	
	public void setIsMain(Boolean value) {
		this.isMain = value;
	}
	
	public Boolean getDeleted() {
		return this.deleted;
	}
	
	public void setDeleted(Boolean value) {
		this.deleted = value;
	}
	
}
