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

import java.util.List;

/**
 * <p>名称: </p>
 * <p>说明: 分类商品推荐</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/8/114:27
 * @version: 1.0
 */
public class CategoryGoodsCommend extends BaseEntity implements Comparable<CategoryGoodsCommend>{

    private Category category;

    private String pic;

    private Integer floor;

    private Integer sort;

    private List<CategoryGoods> categoryGoodses;

    private Boolean deleted = Boolean.FALSE;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public List<CategoryGoods> getCategoryGoodses() {
        return categoryGoodses;
    }

    public void setCategoryGoodses(List<CategoryGoods> categoryGoodses) {
        this.categoryGoodses = categoryGoodses;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public int compareTo(CategoryGoodsCommend o) {
        return this.getSort().compareTo(o.sort);
    }

}
