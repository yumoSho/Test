package com.glanway.jty.entity.marketing;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.product.Goods;

/**
 * 爆品推荐
 * @author liujichao
 *
 */
public class NewGoods extends BaseEntity {
	/**
	 * 商品
	 */
	private Goods goods;
	/**
	 * 排序
	 */
	private Integer sort;
    /**
     * 是否删除  0 未删除  1 已删除
     */
	private Boolean deleted= Boolean.FALSE;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
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
}
