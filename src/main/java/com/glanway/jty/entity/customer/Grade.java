package com.glanway.jty.entity.customer;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;

import java.util.Date;
import java.util.List;

/**
 *  会员等级实体
 *  @author  tianxuan
 *  @Time     2016/4/1
 *  @version 1.0
 */
public class Grade extends BaseEntity{

    private String name;//名称

    private Boolean deleted; //是否删除

    private List<GradeDetail> details;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<GradeDetail> getDetails() {
        return details;
    }

    public void setDetails(List<GradeDetail> details) {
        this.details = details;
    }
}
