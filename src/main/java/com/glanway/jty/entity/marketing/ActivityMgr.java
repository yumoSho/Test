/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: jty
 * FileName: ActivityMgr.java
 * PackageName: com.glanway.jty.entity.activity
 * Date: 2016年4月29日下午5:48:34
 **/
package com.glanway.jty.entity.marketing;

import com.glanway.jty.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * <p>名称：活动管理</p>
 * <p>描述：活动管理</p>
 * @author：LiuJC
 * @date：2016/5/13 14:04
 * @version: 1.0
 */
public class ActivityMgr extends BaseEntity {

    /**@Fields  : 活动名称 */
    private String activityName;

    /**@Fields  : 活动图片名称名称 */
    private String activityImgPath;

    /**@Fields  : 活动banner名称名称 */
    private String activityBannerPath;

    /**@Fields  : 活动开始时间 */
    private Date startDate;

    /**@Fields  : 活动结束时间 */
    private Date endDate;

    /**@Fields  : 排序 */
    private Integer sort;

    /**@Fields  : 是否已删除 */
    private Boolean  deleted = Boolean.FALSE;

    /**@Fields  : 活动折扣  <p style="color:red">*仅针对折扣类型；</p> */
    private BigDecimal discount;

    /**@Fields  : 活动商品集合 */
    private List<ActivityGoods> activityGoodses;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityImgPath() {
        return activityImgPath;
    }

    public void setActivityImgPath(String activityImgPath) {
        this.activityImgPath = activityImgPath;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public List<ActivityGoods> getActivityGoodses() {
        return activityGoodses;
    }

    public void setActivityGoodses(List<ActivityGoods> activityGoodses) {
        this.activityGoodses = activityGoodses;
    }

    public String getActivityBannerPath() {
        return activityBannerPath;
    }

    public void setActivityBannerPath(String activityBannerPath) {
        this.activityBannerPath = activityBannerPath;
    }
}
