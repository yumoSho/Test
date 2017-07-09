package com.glanway.jty.entity.operations;

import java.util.Date;

/**
 * <p>名称: 二维码管理实体</p>
 * <p>说明: </p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/8/317:12
 * @version: 1.0
 */
public class QRcode {
    private Long id;

    /**@Fields name : 二维码名称 */
    private String name;
    /**@Fields link : 链接地址 */
    private String link;
    /**@Fields visitorCount : 访问数量 */
    private int visitorCount;
    /**@Fields deleted : 标记删除 */
    private Boolean deleted;

    /**@Fields createdDate : 创建时间 */
    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
