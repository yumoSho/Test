package com.glanway.jty.entity.perm;

public class SimpleDataTree {
	private Long id;
	private Long pId;
	private String name;
	private Boolean checked = false;
	private Boolean open = false;
	private Boolean isParent = false;
	private Boolean nocheck = false;
	/**
	 *	请不要删，赵创
	 *  用于存放数据库记录的id(田轩)
	 */
	private Long modelId;

    private String modelName;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
	 * @return the nocheck
	 */
	public Boolean getNocheck() {
		return nocheck;
	}

	/**
	 * @param nocheck the nocheck to set
	 */
	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}

	/**
	 * @return the open
	 */
	public Boolean getOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(Boolean open) {
		this.open = open;
	}

	/**
	 * @return the isParent
	 */
	public Boolean getIsParent() {
		return isParent;
	}

	/**
	 * @param isParent the isParent to set
	 */
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the pId
	 */
	public Long getpId() {
		return pId;
	}
	/**
	 * @param pId the pId to set
	 */
	public void setpId(Long pId) {
		this.pId = pId;
	}
	/**
	 * @return the checked
	 */
	public Boolean getChecked() {
		return checked;
	}
	/**
	 * @param checked the checked to set
	 */
	public void setChecked(Boolean checked) {
		this.checked = checked;
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

}
