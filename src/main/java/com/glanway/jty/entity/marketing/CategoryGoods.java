/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: jintianyuan
 * FileName: CategoryGoodsCommendController.java
 * PackageName: com.glanway.jty.entity.marketing
 * Date: 2016/8/114:27
 **/
package com.glanway.jty.entity.marketing;/**
 * Created by chao on 2016/8/1.
 */

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.entity.product.Goods;

/**
 * <p>名称: </p>
 * <p>说明: 分类商品推荐</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：liuJC
 * @date：2016/8/114:27
 * @version: 1.0
 */
public class CategoryGoods extends BaseEntity{

    private CategoryGoodsCommend categoryGoodsCommend ;

    private Goods goods;

    private Integer sort;

    private Boolean deleted = Boolean.FALSE;

    public CategoryGoodsCommend getCategoryGoodsCommend() {
        return categoryGoodsCommend;
    }

    public void setCategoryGoodsCommend(CategoryGoodsCommend categoryGoodsCommend) {
        this.categoryGoodsCommend = categoryGoodsCommend;
    }

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
