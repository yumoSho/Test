package com.glanway.jty.entity.perm;

import com.glanway.jty.entity.BaseEntity;

/**
 * 
 * @author zhuhaodong
 *
 */
public class Role extends BaseEntity {
	/**
	 * 角色名称
	 */
	private String name;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 删除标识
	 */
	private Boolean deleted;

    private String auditPerm;


    public String getAuditPerm() {
        return auditPerm;
    }

    public void setAuditPerm(String auditPerm) {
        this.auditPerm = auditPerm;
    }

    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
