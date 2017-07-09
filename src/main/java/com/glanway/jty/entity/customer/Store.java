package com.glanway.jty.entity.customer;


import java.util.List;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.product.Label;

/**
 * 银行实体
 *  @author  tianxuan
 *  @Time     2016/4/1
 *  @version 1.0
 */
public class Store extends BaseEntity{
    public static final int CODE_LENGTH = 6;  //后面的长度为2
    public static final String PREFIX = "L";  //后面的长度为2

    /**@Fields name : 店铺名称 */
    private String name;

    private String code;//店铺代码

    private String logo;//店铺logo

    private String recommendImg;//店铺推荐图

    private Boolean recommend;//人气推荐

    private String phone;//手机

    private String privinceCode;//省

    private String cityCode;//市

    private String areaCode;//区

    private String address;//详细地址

    private String email;//邮箱

    private String siteUrl;//域名

    private String intro;//简介

    private String detailedIntro;//详细介绍

    private String contact;//联系人

    private Boolean deleted;//是否删除

    private List<Label> labelList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRecommendImg() {
        return recommendImg;
    }

    public void setRecommendImg(String recommendImg) {
        this.recommendImg = recommendImg;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDetailedIntro() {
        return detailedIntro;
    }

    public void setDetailedIntro(String detailedIntro) {
        this.detailedIntro = detailedIntro;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<Label> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Label> labelList) {
        this.labelList = labelList;
    }
}

