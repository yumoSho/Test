package com.glanway.jty.entity.platform;

import com.glanway.jty.entity.BaseEntity;

/**
 * 友情链接实体类
 * @author songzhe
 *
 */
public class Link extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 友情链接标题
	 */
	private String title;
	
	/**
	 * 链接地址
	 */
	private String url;
	
	/**
	 * 排序值
	 */
	private String sortNum;

	
	/**
	 * 是否删除
	 */
	private int deleted;
	
	/**
	 * 操作用户名
	 */
	private String createByUsername;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSortNum() {
		return sortNum;
	}
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
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
	
	
	 
	
}
