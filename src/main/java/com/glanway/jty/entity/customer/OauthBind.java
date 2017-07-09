package com.glanway.jty.entity.customer;

import com.glanway.jty.entity.BaseEntity;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 第三方登录绑定信息实体
 *  @author  SongZhe
 *  @Time     2016/10/9
 *  @version 1.0
 */
public class OauthBind extends BaseEntity{

    private Long memberId;
    private String channel;
    private String openid;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
