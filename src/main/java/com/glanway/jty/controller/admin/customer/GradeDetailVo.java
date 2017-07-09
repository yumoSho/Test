package com.glanway.jty.controller.admin.customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class GradeDetailVo {

    private List<Long> categoryId = new ArrayList<Long>();
    private List<String> discount = new ArrayList<String>();

    public List<String> getDiscount() {
        return discount;
    }

    public void setDiscount(List<String> discount) {
        this.discount = discount;
    }

    public List<Long> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<Long> categoryId) {
        this.categoryId = categoryId;
    }
}
