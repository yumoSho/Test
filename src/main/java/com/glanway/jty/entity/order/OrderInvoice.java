/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p>
 * ProjectName: sh
 * FileName: OrderInvoice.java
 * PackageName: com.glanway.jty.entity.order
 * Date: 2016/6/20 11:09
 **/
package com.glanway.jty.entity.order;

import com.glanway.jty.entity.personalcenter.MemberInvoice;

/**
 * <p>名称: </p>
 * <p>说明: TODO</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：kiah
 * @date：2016/6/20 11:09
 * @version: 1.0
 */
public class OrderInvoice {
    private long id;//ID

    private long orderId;//订单ID

    private String orderCode;//订单号

    private Integer type;//发票类型 0:不开发票；1：普通发票；2：增值税发票

    private String typeName;//类型名称

    private String title;//发票类型

    private String content;//发票内容

    private String unit;//单位名称

    private String personCode;//纳锐人识别码

    private String address;//注册地址

    private String telphone;//注册电话

    private String bankName;//开户银行

    private String bankAccout;//银行账号

    public OrderInvoice(){}

    public OrderInvoice(MemberInvoice memberInvoice){
        if(null == memberInvoice){
            this.type = 0 ;
            typeName = "不开发票";
        }else{
            this.type = memberInvoice.getType();
            this.title = memberInvoice.getTitle();
            String typeName = "不开发票";
            if(type == 1){
                typeName = "普通发票";
            }else{
                typeName = "增值税";
            }
            this.typeName = typeName;
            this.content = memberInvoice.getContent();
            this.unit = memberInvoice.getUnitName();
            this.personCode = memberInvoice.getPersonCode();
            this.address = memberInvoice.getAddress();
            this.telphone = memberInvoice.getTelphone();
            this.bankName = memberInvoice.getBankName();
            this.bankAccout = memberInvoice.getBankAccout();
        }
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getPersonCode() {
        return personCode;
    }
    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTelphone() {
        return telphone;
    }
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankAccout() {
        return bankAccout;
    }
    public void setBankAccout(String bankAccout) {
        this.bankAccout = bankAccout;
    }
}
