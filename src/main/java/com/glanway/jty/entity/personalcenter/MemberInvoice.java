/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: MemberInvoice.java
 * PackageName: com.glanway.hg.entity.personalcenter
 * Date: 2016/5/413:43
 **/
package com.glanway.jty.entity.personalcenter;

/**
 * <p>名称: 会员发票</p>
 * <p>说明: 记录会员发票信息</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/413:43
 * @version: 1.0
 */
public class MemberInvoice {
    /**@Fields id : id */
    private Long id;

    /**@Fields memberId : 会员ID */
    private Long memberId;

    /**@Fields type : 发票类型 1:普通 2：增值税发票 */
    private Integer type;

    /**@Fields title : 抬头 */
    private String title;

    /**@Fields content : 发票内容 存储 数据字典的key */
    private String content;

    /**@Fields unitName : 单位名称 */
    private String unitName;

    /**@Fields personCode : 纳税人识别码 */
    private String personCode;


    /**@Fields address : 注册地址 */
    private String address;

    /**@Fields telphone : 注册电话 */
    private String telphone;

    /**@Fields bankName : 开户银行 */
    private String bankName;

    /**@Fields bankAccout : 银行账户 */
    private String bankAccout;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
