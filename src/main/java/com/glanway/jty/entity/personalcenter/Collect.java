package com.glanway.jty.entity.personalcenter;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.product.Goods;

/** 
 * @author  liujichao
 * @date 创建时间：2015年11月07日 上午10:18:00
 * 用户商品收藏
 * @version 1.0  
 * @since  
 */
public class Collect extends BaseEntity {
	
	/**
	 * 会员编号
	 */
	private Member member;
	/**
	 * 收藏商品
	 */
	private Goods goods;
	/**
	 * 来源
	 */
	private Integer goodsFrom;
	/**
	 * 来源ID
	 */
	private Long otherId;
	/**
	 * 是否删除  0 未删除  1 已删除
	 */
	private Boolean deleted = Boolean.FALSE;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getGoodsFrom() {
		if(goodsFrom==null){
			return 0;
		}
		return goodsFrom;
	}

	public void setGoodsFrom(Integer goodsFrom) {
		this.goodsFrom = goodsFrom;
	}

	public Long getOtherId() {
		if(otherId == null){
			return 0L;
		}
		return otherId;
	}

	public void setOtherId(Long otherId) {
		this.otherId = otherId;
	}
}
