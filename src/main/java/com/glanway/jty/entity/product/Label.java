/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: jintianyuan
 * FileName: Label.java
 * PackageName: com.glanway.jty.entity.product
 * Date: 2016/7/2610:53
 **/
package com.glanway.jty.entity.product;/**
 * Created by LiuJC on 2016/7/26.
 */

import com.glanway.jty.entity.BaseEntity;

/**
 * <p>名称: Label</p>
 * <p>说明: 商品标签</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：LiuJC
 * @date：2016/7/2610:53
 * @version: 1.0
 */
public class Label extends BaseEntity {
    /*标签名称      */
    private String labelName;
    /*标签code*/
    private String labelCode;
    /*标签图片*/
    private String labelPath;
    /*标签是否禁用*/
    private Boolean disabled = Boolean.FALSE;
    /*标签排序*/
    private Integer sort;
    /*标签是否已删除*/
    private Boolean deleted  = Boolean.FALSE;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public String getLabelPath() {
        return labelPath;
    }

    public void setLabelPath(String labelPath) {
        this.labelPath = labelPath;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
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
