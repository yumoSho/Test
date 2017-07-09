/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: DeliveryAddress.java
 * PackageName: com.glanway.hg.entity.personalcenter
 * Date: 2016/5/313:53
 **/
package com.glanway.jty.entity.personalcenter;

/**
 * <p>名称: 收货地址</p>
 * <p>说明: 收货地址 实体</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/313:53
 * @version: 1.0
 */
public class DeliveryAddress {
    /**@Fields id : id */
    private Long id;

    /**@Fields privinceCode : 省 编码 */
    private String privinceCode;

    /**@Fields cityCode : 市编码 */
    private String cityCode;

    /**@Fields areaCode : 区编码 */
    private String areaCode;

    /**@Fields address : 详细地址 */
    private String address;

    /**@Fields consignee : 收件人 */
    private String consignee;

    /**@Fields consigneePhone : 手机号码 */
    private String consigneePhone;

    /**@Fields telephone : 固定电话 */
    private String telephone;

    /**@Fields isDefault : 是否默认收货地址 */
    private Boolean isDefault = false;

    /**@Fields deleted : 是否删除 */
    private Boolean deleted;


    /**@Fields memberId : 所属用户 */
    private Long memberId;

    private String fieldOne;

    private String fieldTwo;

    private String fieldThree;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivinceCode() {
        return privinceCode;
    }

    public void setPrivinceCode(String privinceCode) {
        this.privinceCode = privinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getFieldOne() {
        return fieldOne;
    }

    public void setFieldOne(String fieldOne) {
        this.fieldOne = fieldOne;
    }

    public String getFieldTwo() {
        return fieldTwo;
    }

    public void setFieldTwo(String fieldTwo) {
        this.fieldTwo = fieldTwo;
    }

    public String getFieldThree() {
        return fieldThree;
    }

    public void setFieldThree(String fieldThree) {
        this.fieldThree = fieldThree;
    }
}
