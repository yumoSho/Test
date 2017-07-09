package com.glanway.jty.entity.product;

import com.glanway.jty.entity.BaseEntity;

import java.io.Serializable;

/**
 *
 * 文件导入实体
 */
public class FileImport extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 客户文件名
     */
    private String title;

    /**
     * 服务器文件名
     */
    private String name;

    /**
     * 文件未导入错误信息
     */
    private String error;
    /**
     * 总条数
     */
    private Long total;

    /**
     * 状态
     * 失败0 成功1
     */
    private Boolean status;
    /**
     * 删除标识符
     */
    private Boolean deleted = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
