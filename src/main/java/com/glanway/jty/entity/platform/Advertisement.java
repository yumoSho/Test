package com.glanway.jty.entity.platform;


import com.glanway.jty.entity.BaseEntity;

/**
 * 广告实体类
 * @author Songzhe
 *
 */
public class Advertisement extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 广告标题
	 */
	private String title;
	
	/**
	 * 广告图片路径
	 */
	private String image;
	
	/**
	 * 是否可用
	 */
	private Integer isShow;
	
	/**
	 * 访问设备类型
	 */
	private long deviceType;
	
	/**
	 * 是否删除
	 */
	private int deleted;
	
	/**
	 * 创建人姓名
	 */
	private String createByUsername; 
	
	/**
	 * 排序编号
	 */
	private String sortNum;

	
	/**
	 * 预留字段
	 * 已给广告链接
	 */
	private String field1;

	/**
	 * 位置
	 */
	private String pos;

	/*位置名称*/
	private String posName;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public long getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(long deviceType) {
		this.deviceType = deviceType;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getCreateByUsername() {
		return createByUsername;
	}
	public void setCreateByUsername(String createByUsername) {
		this.createByUsername = createByUsername;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getSortNum() {
		return sortNum;
	}
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}
}
