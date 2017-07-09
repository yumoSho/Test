package com.glanway.jty.entity;

import java.io.Serializable;
import java.util.Date;

import com.glanway.gone.entity.Auditable;
import com.glanway.gone.entity.Idable;

/**
 * 基础实体
 *
 * @author vacoor
 */
public abstract class BaseEntity implements Idable<Long>, Auditable<Long, Long>, Serializable {
	private static final long serialVersionUID = 4364938670197477363L;
	
	/** @Id **/
    protected Long id;
    /** @Column */
    protected Long createdBy;
    /** @Column */
    protected Date createdDate;
    /** @Column */
    protected Long lastModifiedBy;
    /** @Column */
    protected Date lastModifiedDate;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (null != other && this.getClass() == other.getClass()) {
            BaseEntity that = (BaseEntity) other;
            Serializable id = this.getId();
            Serializable thatId = that.getId();
            return null != id && null != thatId && (id == thatId || id.equals(thatId));
        } else {
            return false;
        }
    }

    public int hashCode() {
        byte result = 1;
        Long id = this.getId();
        return 31 * result + (id == null ? 0 : id.hashCode());
    }
}
