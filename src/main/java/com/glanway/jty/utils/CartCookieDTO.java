package com.glanway.jty.utils;

/**
 * Project Name：
 * Description：     购物车 Cookie
 * Created By：      Argevolle
 * Created Date：    2015/1/24 11:38
 * Modified By：
 * Modified Date：
 * Modified Remark：
 * Version：         v 0.1
 */
public class CartCookieDTO {

    private Long id;

    private Long num;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
